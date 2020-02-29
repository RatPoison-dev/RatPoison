package rat.poison.scripts.aim

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.settings.*
import rat.poison.strToBool
import rat.poison.utils.*
import java.lang.Math.toRadians
import kotlin.math.abs
import kotlin.math.sin

var target = -1L
var perfect = false
var boneTrig = false
var destBone = -1

fun reset() {
	destBone = -5
	target = -1L
	perfect = false
}

fun findTarget(position: Angle, angle: Angle, allowPerfect: Boolean,
                        lockFOV: Int = curSettings["AIM_FOV"].toInt(), BONE: Int = curSettings["AIM_BONE"].toInt(), visCheck: Boolean = true): Player {
	var closestFOV = Double.MAX_VALUE
	var closestDelta = Double.MAX_VALUE
	var closestPlayer = -1L

	forEntities result@{
		val entity = it.entity
		if (entity <= 0 || entity == me || !entity.canShoot(visCheck)) {
			return@result false
		}

		if (it.type != EntityType.CCSPlayer) {
			return@result false
		}



		if (BONE == -3) { //Knife bot bone
			for (i in 3..8) {
				val arr = calcTarget(closestDelta, entity, position, angle, lockFOV, entity.nearestBone())

				if (arr[0] != -1.0) {
					closestFOV = arr[0] as Double
					closestDelta = arr[1] as Double
					closestPlayer = arr[2] as Long
				}
			}
		} else if (BONE == -2) { //Trigger bot bone
			for (i in 3..8) {
				val arr = calcTarget(closestDelta, entity, position, angle, lockFOV, i)

				if (arr[0] != -1.0) {
					closestFOV = arr[0] as Double
					closestDelta = arr[1] as Double
					closestPlayer = arr[2] as Long
				}
			}
		} else {
			if (BONE == NEAREST_BONE) { //Nearest bone check
				val nB = entity.nearestBone()
				if (nB != -999) {
					val arr = calcTarget(closestDelta, entity, position, angle, lockFOV, entity.nearestBone())

					if (arr[0] != -1.0) {
						closestFOV = arr[0] as Double
						closestDelta = arr[1] as Double
						closestPlayer = arr[2] as Long
					}
				}
			} else {
				val arr = calcTarget(closestDelta, entity, position, angle, lockFOV, BONE)

				if (arr[0] != -1.0) {
					closestFOV = arr[0] as Double
					closestDelta = arr[1] as Double
					closestPlayer = arr[2] as Long
				}
			}
		}
		return@result false
	}

	if (closestDelta == Double.MAX_VALUE || closestDelta < 0 || closestPlayer < 0) return -1

	val randInt = randInt(100+1)

	if (curSettings["PERFECT_AIM"].strToBool() && allowPerfect && closestFOV <= curSettings["PERFECT_AIM_FOV"].toInt() && randInt <= curSettings["PERFECT_AIM_CHANCE"].toInt()) {
		perfect = true
	}

	return closestPlayer
}

fun calcTarget(calcClosestDelta: Double, entity: Entity, position: Angle, angle: Angle, lockFOV: Int = curSettings["AIM_FOV"].toInt(), BONE: Int): MutableList<Any> {
	val retList = mutableListOf(-1.0, 0.0, 0L)

	val ePos: Angle = entity.bones(BONE)
	val distance = position.distanceTo(ePos)

	val dest = getCalculatedAngle(me, ePos)

	val pitchDiff = abs(angle.x - dest.x)
	var yawDiff = abs(angle.y - dest.y)

	if (yawDiff > 180f) {
		yawDiff = 360f - yawDiff
	}

	val fov = abs(sin(toRadians(yawDiff)) * distance)
	val delta = abs((sin(toRadians(pitchDiff)) + sin(toRadians(yawDiff))) * distance)

	if (delta <= lockFOV && delta <= calcClosestDelta) {
		retList[0] = fov
		retList[1] = delta
		retList[2] = entity
	}

	return retList.toMutableList()
}

fun Entity.inMyTeam() =
		!curSettings["TEAMMATES_ARE_ENEMIES"].strToBool() && if (DANGER_ZONE) {
			me.survivalTeam().let { it > -1 && it == this.survivalTeam() }
		} else me.team() == team()

fun Entity.canShoot(visCheck: Boolean = true) = ((if (DANGER_ZONE) { true } else if (visCheck) { spotted() } else { true })
		&& !dormant()
		&& !dead()
		&& !inMyTeam()
		&& !isProtected()
		&& !me.dead())

internal inline fun <R> aimScript(duration: Int, crossinline precheck: () -> Boolean,
								  crossinline doAim: (destinationAngle: Angle,
													  currentAngle: Angle, aimSpeed: Int, aimSpeedDivisor: Int) -> R) = every(duration) {
	try {
		if (!precheck()) return@every
		if (!curSettings["ENABLE_AIM"].strToBool()) return@every

		val meWep = me.weapon()
		val meWepEnt = me.weaponEntity()

		if (meWep.grenade || meWep.knife || meWep == Weapons.ZEUS_X27) {
			reset()
			return@every
		}

		if (!meWepEnt.canFire() && !meWep.automatic && !meWep.pistol && !meWep.shotgun && !meWep.sniper && !meWep.smg) { //Aim after shoot
			reset()
			return@every
		}

		if (meWep.sniper && !me.isScoped() && curSettings["ENABLE_SCOPED_ONLY"].strToBool()) { //Scoped only
			reset()
			return@every
		}

		val aim = curSettings["ACTIVATE_FROM_AIM_KEY"].strToBool() && keyPressed(AIM_KEY)
		val forceAim = (keyPressed(curSettings["FORCE_AIM_KEY"].toInt()) || curSettings["FORCE_AIM_ALWAYS"].strToBool())
		val haveAmmo = meWepEnt.bullets() > 0

		val pressed = ((aim || boneTrig) && !MENUTOG && haveAmmo &&
				(if (meWep.rifle || meWep.smg) {
					(me.shotsFired() > curSettings["AIM_AFTER_SHOTS"].toInt())
				} else {
					true
				})) || forceAim

		var currentTarget = target

		if (!pressed) {
			reset()
			return@every
		}

		val currentAngle = clientState.angle()
		val position = me.position()
		val shouldVisCheck = !(forceAim && curSettings["FORCE_AIM_THROUGH_WALLS"].strToBool())

		val aB = curSettings["AIM_BONE"].toInt()

		if (currentTarget < 0) { //If target is invalid from last run
			currentTarget = findTarget(position, currentAngle, aim,
					BONE = if (aB == RANDOM_BONE) { destBone = 5 + randInt(0, 4); destBone } else { destBone = aB; aB },/*if (aB == RANDOM_BONE) destBone else aB,*/
					visCheck = shouldVisCheck) //Try to find new target
			if (currentTarget < 0) { //End if we don't, can't loop because of thread blocking
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

		if (currentTarget == me || !currentTarget.canShoot(shouldVisCheck)) {
			reset()
			Thread.sleep(curSettings["AIM_TARGET_SWAP_DELAY"].toInt().toLong())
		} else {
			val bonePosition = currentTarget.bones(destBone)

			val destinationAngle = getCalculatedAngle(me, bonePosition) //Rename to current angle

			if (!perfect) {
				destinationAngle.finalize(currentAngle, (1.1 - curSettings["AIM_SMOOTHNESS"].toDouble() / 5.0)) //10.0 is max smooth value
			}

			val aimSpeed = curSettings["AIM_SPEED"].toInt()
			val aimSpeedDivisor = if (curSettings["AIM_ADVANCED"].strToBool()) curSettings["AIM_SPEED_DIVISOR"].toInt() else 1
			doAim(destinationAngle, currentAngle, aimSpeed, aimSpeedDivisor)
		}
	} catch (e: Exception) { e.printStackTrace() }
}