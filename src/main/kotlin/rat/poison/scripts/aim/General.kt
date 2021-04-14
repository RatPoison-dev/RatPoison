package rat.poison.scripts.aim

import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.settings.*
import rat.poison.utils.*
import rat.poison.utils.common.*
import rat.poison.utils.generalUtil.*
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

class FindTargetResult(var player: Player = -1L, var bone: Int = -1) {
	fun reset() {
		player = -1L
		bone = -1
	}
}

val findTargetResult = ThreadLocal.withInitial { FindTargetResult() }

private const val id = "findtarget"
private val forEnts = arrayOf(EntityType.CCSPlayer)

fun aimFindTarget(position: Angle, angle: Angle, allowPerfect: Boolean, lockFOV: Float = AIM_FOV, BONE: String = curSettings["AIM_BONE"], visCheck: Boolean = true, teamCheck: Boolean = true): FindTargetResult {
	val result = findTargetResult.get()
	result.reset()
	var closestFOV = Float.MAX_VALUE
	var closestDelta = Float.MAX_VALUE
	var closestPlayer = -1L
	var closestBone = -1
	var forceSpecificBone = -1

	val bones = BONE.stringToIntList()
	val findNearest = bones.has { it == NEAREST_BONE }
	val findRandom = bones.has { 0 > it as Int }

	forEntities(forEnts, identifier = id) {
		val entity = it.entity
		if (entity <= 0 || entity == me || !entity.canShoot(visCheck, teamCheck)) {
			return@forEntities
		}
		if (findNearest) {
			val nB = entity.nearestBone()
			if (nB != INVALID_NEAREST_BONE) forceSpecificBone = nB
		}
		else if (findRandom) {
			forceSpecificBone = 5 + randInt(0, 3)
		}
		if (forceSpecificBone == -1) {
			for (i in 0 until bones.size) {
				val bone = bones[i]
				val arr = calcTarget(closestDelta, entity, position, angle, lockFOV, bone)

				val fov = arr.fov

				if (fov > 0F) {
					closestFOV = fov
					closestDelta = arr.delta
					closestPlayer = arr.player
					closestBone = bone
				}
			}
		}
		else {
			val arr = calcTarget(closestDelta, entity, position, angle, lockFOV, forceSpecificBone)

			val fov = arr.fov

			if (fov > 0F) {
				closestFOV = fov
				closestDelta = arr.delta
				closestPlayer = arr.player
				closestBone = forceSpecificBone
			}
		}

	}

	if (closestDelta == Float.MAX_VALUE || closestDelta < 0 || closestPlayer < 0) return result

	val randInt = randInt(1, 100)

	if (PERFECT_AIM && allowPerfect && closestFOV <= PERFECT_AIM_FOV && randInt <= PERFECT_AIM_CHANCE) {
		canPerfect = true
	}
	result.player = closestPlayer
	result.bone = closestBone

	return result
}

private val thisCatJustJ = ThreadLocal.withInitial { mutableListOf<Int>() }
fun findTarget(position: Angle, angle: Angle, allowPerfect: Boolean,
				  lockFOV: Float = AIM_FOV, BONE: String = curSettings["AIM_BONE"], visCheck: Boolean = true, teamCheck: Boolean = true): Long {
	var closestFOV = Float.MAX_VALUE
	var closestDelta = Float.MAX_VALUE
	var closestPlayer = -1L
	var forceSpecificBone = -1

	val bones = BONE.stringToIntList()
	val findNearest = bones.has { it == NEAREST_BONE }
	val findRandom = bones.has { 0 > it as Int }

	forEntities(forEnts, identifier = id) {
		val entity = it.entity
		if (entity <= 0 || entity == me || !entity.canShoot(visCheck, teamCheck)) {
			return@forEntities
		}

		if (findNearest) {
			val nB = entity.nearestBone()
			if (nB != INVALID_NEAREST_BONE) forceSpecificBone = nB
		}
		else if (findRandom) {
			forceSpecificBone = 5 + randInt(0, 3)
		}

		if (forceSpecificBone == -1) {
			for (i in 0 until bones.size) {
				val bone = bones[i]
				val arr = calcTarget(closestDelta, entity, position, angle, lockFOV, bone)

				val fov = arr.fov

				if (fov > 0F) {
					closestFOV = fov
					closestDelta = arr.delta
					closestPlayer = arr.player
				}
			}
		}
		else {
			val arr = calcTarget(closestDelta, entity, position, angle, lockFOV, forceSpecificBone)

			val fov = arr.fov

			if (fov > 0F) {
				closestFOV = fov
				closestDelta = arr.delta
				closestPlayer = arr.player
			}
		}

	}

	if (closestDelta == Float.MAX_VALUE || closestDelta < 0 || closestPlayer < 0) return -1

	val randInt = randInt(1, 100)

	if (PERFECT_AIM && allowPerfect && closestFOV <= PERFECT_AIM_FOV && randInt <= PERFECT_AIM_CHANCE) {
		canPerfect = true
	}

	return closestPlayer
}

data class CalcTargetResult(var fov: Float = -1F, var delta: Float = -1F, var player: Player = -1L) {
	fun reset() {
		fov = -1F
		delta -1F
		player = -1L
	}
}
val calcTargetResult = ThreadLocal.withInitial {CalcTargetResult()}
private val boneVec = ThreadLocal.withInitial { Vector() }
private val ang = ThreadLocal.withInitial { Vector() }

fun calcTarget(calcClosestDelta: Float, entity: Entity, position: Angle, curAngle: Angle, lockFOV: Float = AIM_FOV, BONE: Int, ovrStatic: Boolean = false): CalcTargetResult {
	val result = calcTargetResult.get()
	result.reset()
	val ang = ang.get()
	val boneVec = boneVec.get()
	var ePos: Angle = entity.bones(BONE, boneVec)

	if (ovrStatic) {
		ePos = position
	}

	if (curSettings["FOV_TYPE"].replace("\"", "") == "DISTANCE" && !ovrStatic) {
		val distance = position.distanceTo(ePos)

		val calcAng = getCalculatedAngle(me, ePos)

		val pitchDiff = abs(curAngle.x - calcAng.x)
		var yawDiff = abs(curAngle.y - calcAng.y)

		if (yawDiff > 180f) {
			yawDiff = 360f - yawDiff
		}

		val fov = abs(sin(toRadians(yawDiff.toDouble())) * distance)
		val delta = abs((sin(toRadians(pitchDiff.toDouble())) + sin(toRadians(yawDiff.toDouble()))) * distance)

		if (delta <= lockFOV && delta <= calcClosestDelta) {
			result.fov = fov.toFloat()
			result.delta = delta.toFloat()
			result.player = entity
		}
	} else {
		val calcAng = realCalcAngle(me, ePos)

		val delta = ang.set(curAngle.x - calcAng.x, curAngle.y - calcAng.y, 0F)
		delta.normalize()

		val fov = sqrt(delta.x.pow(2F) + delta.y.pow(2F))

		if (fov <= lockFOV && fov <= calcClosestDelta) {
			result.fov = fov
			result.delta = fov
			result.player = entity
		}
	}

	return result
}

fun Entity.inMyTeam() =
		!curSettings.bool["TEAMMATES_ARE_ENEMIES"] && if (DANGER_ZONE) {
			me.survivalTeam().let { it > -1 && it == this.survivalTeam() }
		} else meTeam == team()

fun Entity.canShoot(visCheck: Boolean = true, teamCheck: Boolean = true) = ((if (DANGER_ZONE) { true } else if (visCheck) { spotted() || (curSettings.bool["TEAMMATES_ARE_ENEMIES"] && team() == meTeam || !teamCheck) } else { true })
		&& !dormant()
		&& !dead()
		&& (!inMyTeam() || !teamCheck)
		&& !isProtected()
		&& !meDead)

const val INVALID_NEAREST_BONE = 999
private val meAng = Vector()
private val mePos = Vector()
private val boneVec2 = Vector()
internal inline fun <R> aimScript(duration: Int, crossinline precheck: () -> Boolean,
								  crossinline doAim: (destinationAngle: Angle,
													  currentAngle: Angle, smoothing: Int) -> R) = every(duration) {
	if (!precheck()) return@every
	if (!curSettings.bool["ENABLE_AIM"]) return@every

	val canFire = meCurWepEnt.canFire()
	if (meCurWep.grenade || meCurWep.knife || meCurWep.miscEnt || meCurWep == Weapons.ZEUS_X27 || meCurWep.bomb || meCurWep == Weapons.NONE) { //Invalid for aimbot
		reset()
		return@every
	}

	if (AIM_ONLY_ON_SHOT && (!canFire || (didShoot && !meCurWep.automatic && !AUTOMATIC_WEAPONS))) { //Onshot
		reset(false)
		return@every
	}

	if (meCurWep.sniper && !me.isScoped() && ENABLE_SCOPED_ONLY) { //Scoped only
		reset()
		return@every
	}

	val aim = curSettings.bool["ACTIVATE_FROM_AIM_KEY"] && keyPressed(AIM_KEY)
	val pressedForceAimKey = keybindEval("FORCE_AIM_KEY")
	val forceAim = pressedForceAimKey || curSettings.bool["FORCE_AIM_ALWAYS"]
	val haveAmmo = meCurWepEnt.bullets() > 0

	val pressed = ((aim || boneTrig) && !MENUTOG && haveAmmo) || forceAim

	if (!pressed) {
		reset()
		return@every
	}

	if (meCurWep.rifle || meCurWep.smg) {
		if (me.shotsFired() < AIM_AFTER_SHOTS) {
			reset()
			return@every
		}
	}

	var currentTarget = target

	val currentAngle = clientState.angle(meAng)
	val position = me.position(mePos)
	val shouldVisCheck = !(forceAim && curSettings.bool["FORCE_AIM_THROUGH_WALLS"])

	var aB = curSettings["AIM_BONE"]

	if (pressedForceAimKey) {
		aB = curSettings["FORCE_AIM_BONE"]
	}

	val findTargetResList = aimFindTarget(position, currentAngle, aim, BONE = aB, visCheck = shouldVisCheck)
	val bestTarget = findTargetResList.player //Try to find new target
	val bestBone = findTargetResList.bone

	if (currentTarget <= 0) { //If target is invalid from last run
		currentTarget = bestTarget //Try to find new target

		if (currentTarget <= 0) { //End if we don't, can't loop because of thread blocking
			reset()
			return@every
		}
		target = currentTarget
	}
	destBone = bestBone

	//Set destination bone for calculating aim

	if (bestTarget <= 0 && !curSettings.bool["HOLD_AIM"] || bestTarget.dead()) {
		reset()
		return@every
	}

	var perfect = false
	if (canPerfect) {
		if (randInt(100+1) <= PERFECT_AIM_CHANCE) {
			perfect = true
		}
	}

	val swapTarget = (bestTarget > 0 && currentTarget != bestTarget) && !curSettings.bool["HOLD_AIM"] && (meCurWep.automatic || AUTOMATIC_WEAPONS)

	if (swapTarget || !currentTarget.canShoot(shouldVisCheck)) {
		reset()
		Thread.sleep(curSettings.int["AIM_TARGET_SWAP_DELAY"].toLong())
	} else {
		val bonePosition = currentTarget.bones(destBone, boneVec2)

		//bonePosition += currentTarget.positionNextTick()

		val destinationAngle = realCalcAngle(me, bonePosition)//getCalculatedAngle(me, bonePosition) //Rename to current rat.poison.game.angle

		if (!perfect) {
			destinationAngle.finalize(currentAngle, (1F - AIM_SMOOTHNESS / 100.1F))

			doAim(destinationAngle, currentAngle, AIM_SMOOTHNESS)
		} else {
			doAim(destinationAngle, currentAngle, 1)
		}
	}
}