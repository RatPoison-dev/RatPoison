package rat.poison.scripts.aim

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.settings.*
import rat.poison.utils.*
import rat.poison.utils.generalUtil.strToBool
import java.lang.Math.toRadians
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

var target = -1L
var canPerfect = false
var boneTrig = false
var destBone = -1

fun reset(resetTarget: Boolean = true) {
	destBone = -5
	if (resetTarget) {
		target = -1L
	}
	canPerfect = false
}

fun findTarget(position: Angle, angle: Angle, allowPerfect: Boolean,
			   lockFOV: Float = curSettings["AIM_FOV"].toFloat(), BONE: Int = curSettings["AIM_BONE"].toInt(), visCheck: Boolean = true): Player {
	var closestFOV = Float.MAX_VALUE
	var closestDelta = Float.MAX_VALUE
	var closestPlayer = -1L

	forEntities(EntityType.CCSPlayer) {
		val entity = it.entity

		if (entity <= 0 || entity == me || !entity.canShoot(visCheck)) {
			return@forEntities
		}

		val arr = if (BONE < 0) {
			calcTarget(closestDelta, entity, position, angle, lockFOV, entity.nearestBone())
		} else {
			calcTarget(closestDelta, entity, position, angle, lockFOV, BONE)
		}

		val fov = arr[0] as Float

		if (fov > 0F) {
			closestFOV = fov
			closestDelta = arr[1] as Float
			closestPlayer = arr[2] as Long
		}

		return@forEntities
	}

	if (closestDelta == Float.MAX_VALUE || closestDelta < 0 || closestPlayer < 0) return -1

	val randInt = randInt(1, 100)

	if (curSettings["PERFECT_AIM"].strToBool() && allowPerfect && closestFOV <= curSettings["PERFECT_AIM_FOV"].toFloat() && randInt <= curSettings["PERFECT_AIM_CHANCE"].toInt()) {
		canPerfect = true
	}

	return closestPlayer
}

fun calcTarget(calcClosestDelta: Float, entity: Entity, position: Angle, curAngle: Angle, lockFOV: Float = curSettings["AIM_FOV"].toFloat(), BONE: Int, ovrStatic: Boolean = false): MutableList<Any> {
	val retList = mutableListOf(-1F, 0F, 0L)

	var ePos: Angle = entity.bones(BONE)

	if (ovrStatic) {
		ePos = position
	}

	if (curSettings["FOV_TYPE"].replace("\"", "") == "DISTANCE" && !ovrStatic) {
		val distance = position.distanceTo(ePos)

		val dest = getCalculatedAngle(me, ePos)

		val pitchDiff = abs(curAngle.x - dest.x)
		var yawDiff = abs(curAngle.y - dest.y)

		if (yawDiff > 180f) {
			yawDiff = 360f - yawDiff
		}

		val fov = abs(sin(toRadians(yawDiff.toDouble())) * distance)
		val delta = abs((sin(toRadians(pitchDiff.toDouble())) + sin(toRadians(yawDiff.toDouble()))) * distance)

		if (delta <= lockFOV && delta <= calcClosestDelta) {
			retList[0] = fov.toFloat()
			retList[1] = delta.toFloat()
			retList[2] = entity
		}
	} else {
		val calcAng = realCalcAngle(me, ePos)

		val delta = Angle(curAngle.x - calcAng.x, curAngle.y - calcAng.y, 0F)
		delta.normalize()

		val fov = sqrt(delta.x.pow(2F) + delta.y.pow(2F))

		if (fov <= lockFOV && fov <= calcClosestDelta) {
			retList[0] = fov
			retList[1] = fov
			retList[2] = entity
		}
	}

	return retList.toMutableList()
}

fun Entity.inMyTeam() =
		!curSettings["TEAMMATES_ARE_ENEMIES"].strToBool() && if (DANGER_ZONE) {
			me.survivalTeam().let { it > -1 && it == this.survivalTeam() }
		} else me.team() == team()

fun Entity.canShoot(visCheck: Boolean = true) = ((if (DANGER_ZONE) { true } else if (visCheck) { spotted() || (curSettings["TEAMMATES_ARE_ENEMIES"].strToBool() && team() == me.team()) } else { true })
		&& !dormant()
		&& !dead()
		&& !inMyTeam()
		&& !isProtected()
		&& !meDead)

internal inline fun <R> aimScript(duration: Int, crossinline precheck: () -> Boolean,
								  crossinline doAim: (destinationAngle: Angle,
													  currentAngle: Angle, aimSpeed: Int, aimSpeedDivisor: Int) -> R) = every(duration) {
	if (!precheck()) return@every
	if (!curSettings["ENABLE_AIM"].strToBool()) return@every

	val canFire = meCurWepEnt.canFire()

	if (meCurWep.grenade || meCurWep.knife || meCurWep.miscEnt || meCurWep == Weapons.ZEUS_X27 || meCurWep.bomb) { //Invalid for aimbot
		reset()
		return@every
	}

	if (curSettings["AIM_ONLY_ON_SHOT"].strToBool() && (!canFire || (didShoot && !meCurWep.automatic && !curSettings["AUTOMATIC_WEAPONS"].strToBool()))) { //Onshot
		reset(false)
		return@every
	}

	if (meCurWep.sniper && !me.isScoped() && curSettings["ENABLE_SCOPED_ONLY"].strToBool()) { //Scoped only
		reset()
		return@every
	}

	val aim = curSettings["ACTIVATE_FROM_AIM_KEY"].strToBool() && keyPressed(AIM_KEY)
	val forceAim = (keyPressed(curSettings["FORCE_AIM_KEY"].toInt()) || curSettings["FORCE_AIM_ALWAYS"].strToBool())
	val haveAmmo = meCurWepEnt.bullets() > 0

	val pressed = ((aim || boneTrig) && !MENUTOG && haveAmmo &&
			(if (meCurWep.rifle || meCurWep.smg) {
				me.shotsFired() >= curSettings["AIM_AFTER_SHOTS"].toInt()
			} else {
				true
			})) || forceAim

	if (!pressed) {
		reset()
		return@every
	}

	var currentTarget = target

	val currentAngle = clientState.angle()
	val position = me.position()
	val shouldVisCheck = !(forceAim && curSettings["FORCE_AIM_THROUGH_WALLS"].strToBool())

	var aB = curSettings["AIM_BONE"].toInt()

	if (keyPressed(curSettings["FORCE_AIM_BONE_KEY"].toInt())) {
		aB = curSettings["FORCE_AIM_BONE"].toInt()
	}

	val bestTarget = findTarget(position, currentAngle, aim,
			BONE = if (aB == RANDOM_BONE) { destBone = 5 + randInt(0, 3); destBone } else { destBone = aB; aB },
			visCheck = shouldVisCheck) //Try to find new target

	if (currentTarget <= 0) { //If target is invalid from last run
		currentTarget = findTarget(position, currentAngle, aim,
				BONE = if (aB == RANDOM_BONE) { destBone = 5 + randInt(0, 3); destBone } else { destBone = aB; aB },
				visCheck = shouldVisCheck) //Try to find new target
		if (currentTarget <= 0) { //End if we don't, can't loop because of thread blocking
			reset()
			return@every
		}
		target = currentTarget
	}

	//Set destination bone for calculating aim
	if (aB == NEAREST_BONE) { //Nearest bone check
		val nearestBone = currentTarget.nearestBone()

		if (nearestBone != -999) {
			destBone = nearestBone
		} else {
			reset()
			return@every
		}
	}

	if (bestTarget <= 0 && !curSettings["HOLD_AIM"].strToBool()) {
		reset()
		return@every
	}

	var perfect = false
	if (canPerfect) {
		if (randInt(100+1) <= curSettings["PERFECT_AIM_CHANCE"].toInt()) {
			perfect = true
		}
	}

	val swapTarget = (bestTarget > 0 && currentTarget != bestTarget) && !curSettings["HOLD_AIM"].strToBool() && (meCurWep.automatic || curSettings["AUTOMATIC_WEAPONS"].strToBool())

	if (!currentTarget.canShoot(shouldVisCheck) || swapTarget) {
		reset()
		Thread.sleep(curSettings["AIM_TARGET_SWAP_DELAY"].toInt().toLong())
	} else {
		val bonePosition = currentTarget.bones(destBone)

		val destinationAngle = getCalculatedAngle(me, bonePosition) //Rename to current angle

		if (!perfect) {
			destinationAngle.finalize(currentAngle, (1.1F - curSettings["AIM_SMOOTHNESS"].toFloat() / 5F)) //10.0 is max smooth value

			val aimSpeed = curSettings["AIM_SPEED"].toInt()

			val aimSpeedDivisor = if (curSettings["AIM_ADVANCED"].strToBool()) curSettings["AIM_SPEED_DIVISOR"].toInt() else 1
			doAim(destinationAngle, currentAngle, aimSpeed, aimSpeedDivisor)
		} else {
			doAim(destinationAngle, currentAngle, 1, 1)
		}
	}
}