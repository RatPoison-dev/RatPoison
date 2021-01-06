package rat.poison.scripts.aim

import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.settings.*
import rat.poison.utils.*
import java.lang.Math.toRadians
import kotlin.math.*

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

fun findTarget(
	position: Vector, angle: Vector, allowPerfect: Boolean,
	lockFOV: Float = curSettings.float["AIM_FOV"], BONE: Int = curSettings.int["AIM_BONE"], visCheck: Boolean = true
): Player {
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
		
		val fov = arr.fov
		
		if (fov > 0F) {
			closestFOV = fov
			closestDelta = arr.closestDelta
			closestPlayer = arr.closestPlayer
		}
		
		return@forEntities
	}
	
	if (closestDelta == Float.MAX_VALUE || closestDelta < 0 || closestPlayer < 0) return -1
	
	val randInt = randInt(1, 100)
	
	if (curSettings.bool["PERFECT_AIM"] && allowPerfect && closestFOV <= curSettings.float["PERFECT_AIM_FOV"] && randInt <= curSettings.int["PERFECT_AIM_CHANCE"]) {
		canPerfect = true
	}
	
	return closestPlayer
}

class CalcTargetResult(var fov: Float = -1F, var closestDelta: Float = 0F, var closestPlayer: Long = 0L) {
	fun reset() {
		fov = -1F
		closestDelta = 0F
		closestPlayer = 0L
	}
}

val calcTargetResult = ThreadLocal.withInitial { CalcTargetResult() }

fun calcTarget(
	calcClosestDelta: Float,
	entity: Entity,
	position: Vector,
	curAngle: Vector,
	lockFOV: Float = curSettings.float["AIM_FOV"],
	BONE: Int,
	ovrStatic: Boolean = false
): CalcTargetResult {
	val result = calcTargetResult.get()
	result.reset()
	
	val ePos: Vector = if (ovrStatic) position else entity.bones(BONE)
	
	if (curSettings["FOV_TYPE"].replace("\"", "") == "DISTANCE" && !ovrStatic) {
		val distance = position.distanceTo(ePos)
		
		val dest = getCalculatedAngle(me, ePos)
		
		val pitchDiff = abs(curAngle.x - dest.x)
		var yawDiff = abs(curAngle.y - dest.y)
		
		dest.release()
		
		if (yawDiff > 180f) {
			yawDiff = 360f - yawDiff
		}
		
		val fov = abs(sin(toRadians(yawDiff.toDouble())) * distance)
		val delta = abs((sin(toRadians(pitchDiff.toDouble())) + sin(toRadians(yawDiff.toDouble()))) * distance)
		
		if (delta <= lockFOV && delta <= calcClosestDelta) {
			result.fov = fov.toFloat()
			result.closestDelta = delta.toFloat()
			result.closestPlayer = entity
		}
	} else {
		val calcAng = realCalcAngle(me, ePos)
		val delta = Angle(curAngle.x - calcAng.x, curAngle.y - calcAng.y).normalize()
		calcAng.release()
		val fov = sqrt(delta.x.pow(2F) + delta.y.pow(2F))
		delta.release()
		if (fov <= lockFOV && fov <= calcClosestDelta) {
			result.fov = fov
			result.closestDelta = fov
			result.closestPlayer = entity
		}
	}
	if (!ovrStatic) ePos.release()
	
	return result
}

fun Entity.inMyTeam() =
	!curSettings.bool["TEAMMATES_ARE_ENEMIES"] && if (DANGER_ZONE) {
		me.survivalTeam().let { it > -1 && it == this.survivalTeam() }
	} else me.team() == team()

fun Entity.canShoot(visCheck: Boolean = true) = ((if (DANGER_ZONE) {
	true
} else if (visCheck) {
	spotted() || (curSettings.bool["TEAMMATES_ARE_ENEMIES"] && team() == me.team())
} else {
	true
})
		&& !dormant()
		&& !dead()
		&& !inMyTeam()
		&& !isProtected()
		&& !meDead)

internal inline fun <R> aimScript(
	threadGroupName: String,
	duration: Long, crossinline precheck: () -> Boolean,
	crossinline doAim: (
		destinationAngle: Angle,
		currentAngle: Vector, aimSpeed: Int, aimSpeedDivisor: Int
	) -> R
) = IsolatedPriority(threadGroupName).every(duration) {
	if (!precheck()) return@every
	if (!curSettings.bool["ENABLE_AIM"]) return@every
	
	val canFire = meCurWepEnt.canFire()
	if (meCurWep.grenade || meCurWep.knife || meCurWep.miscEnt || meCurWep == Weapons.ZEUS_X27 || meCurWep.bomb || meCurWep == Weapons.NONE) { //Invalid for aimbot
		reset()
		return@every
	}
	
	if (curSettings.bool["AIM_ONLY_ON_SHOT"] && (!canFire || (didShoot && !meCurWep.automatic && !curSettings.bool["AUTOMATIC_WEAPONS"]))) { //Onshot
		reset(false)
		return@every
	}
	
	if (meCurWep.sniper && !me.isScoped() && curSettings.bool["ENABLE_SCOPED_ONLY"]) { //Scoped only
		reset()
		return@every
	}
	
	val aim = curSettings.bool["ACTIVATE_FROM_AIM_KEY"] && keyPressed(AIM_KEY)
	val forceAim = (keyPressed(curSettings.int["FORCE_AIM_KEY"]) || curSettings.bool["FORCE_AIM_ALWAYS"])
	val haveAmmo = meCurWepEnt.bullets() > 0
	
	val pressed = ((aim || boneTrig) && !MENUTOG && haveAmmo) || forceAim
	
	if (!pressed) {
		reset()
		return@every
	}
	
	if (meCurWep.rifle || meCurWep.smg) {
		if (me.shotsFired() < curSettings.int["AIM_AFTER_SHOTS"]) {
			reset()
			return@every
		}
	}
	
	var currentTarget = target
	
	val currentAngle = clientState.angle()
	val position = me.position()
	try {
		val shouldVisCheck = !(forceAim && curSettings.bool["FORCE_AIM_THROUGH_WALLS"])
		
		var aB = curSettings.int["AIM_BONE"]
		
		if (keyPressed(curSettings.int["FORCE_AIM_BONE_KEY"])) {
			aB = curSettings.int["FORCE_AIM_BONE"]
		}
		
		val bestTarget = findTarget(
			position, currentAngle, aim,
			BONE = if (aB == RANDOM_BONE) {
				destBone = 5 + randInt(0, 3); destBone
			} else {
				destBone = aB; aB
			},
			visCheck = shouldVisCheck
		) //Try to find new target
		
		if (currentTarget <= 0) { //If target is invalid from last run
			currentTarget = findTarget(
				position, currentAngle, aim,
				BONE = if (aB == RANDOM_BONE) {
					destBone = 5 + randInt(0, 3); destBone
				} else {
					destBone = aB; aB
				},
				visCheck = shouldVisCheck
			) //Try to find new target
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
		
		if (bestTarget <= 0 && !curSettings.bool["HOLD_AIM"]) {
			reset()
			return@every
		}
		
		var perfect = false
		if (canPerfect) {
			if (randInt(100 + 1) <= curSettings.int["PERFECT_AIM_CHANCE"]) {
				perfect = true
			}
		}
		
		val swapTarget =
			(bestTarget > 0 && currentTarget != bestTarget) && !curSettings.bool["HOLD_AIM"] && (meCurWep.automatic || curSettings.bool["AUTOMATIC_WEAPONS"])
		
		if (!currentTarget.canShoot(shouldVisCheck) || swapTarget) {
			reset()
			it.delayed(curSettings.int["AIM_TARGET_SWAP_DELAY"].toLong())
		} else {
			val bonePosition = currentTarget.bones(destBone)
			val destinationAngle = getCalculatedAngle(me, bonePosition) //Rename to current angle
			bonePosition.release()
			
			if (!perfect) {
				destinationAngle.finalize(
					currentAngle,
					(1.1F - curSettings.float["AIM_SMOOTHNESS"] / 5F)
				) //10.0 is max smooth value
				
				val aimSpeed = curSettings.int["AIM_SPEED"]
				
				val aimSpeedDivisor = if (curSettings.bool["AIM_ADVANCED"]) curSettings.int["AIM_SPEED_DIVISOR"] else 1
				doAim(
					destinationAngle,
					currentAngle,
					aimSpeed / max(1, CSGO.CACHE_EXPIRE_MILLIS.toInt() / 4),
					aimSpeedDivisor
				)
			} else {
				doAim(destinationAngle, currentAngle, 1, 1)
			}
			destinationAngle.release()
		}
	} finally {
		currentAngle.release()
		position.release()
	}
}