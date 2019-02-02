package rat.poison.scripts.aim

import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.settings.*
import rat.poison.utils.*
import org.jire.arrowhead.keyPressed
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

val target = AtomicLong(-1)
val bone = AtomicInteger(AIM_BONE)
val perfect = AtomicBoolean() // only applicable for safe aim

internal fun reset() {
	target.set(-1L)
	//bone.set(AIM_BONE)
	perfect.set(false)
}

internal fun findTarget(position: Angle, angle: Angle, allowPerfect: Boolean,
                        lockFOV: Int = AIM_FOV, BONE: Int = AIM_BONE,
                        yawOnly: Boolean): Player {
	var closestFOV = Double.MAX_VALUE
	var closestDelta = Double.MAX_VALUE
	var closestPlayer = -1L
	
	forEntities(ccsPlayer) result@{
		val entity = it.entity
		if (entity <= 0 || entity == me || !entity.canShoot()) {
			return@result false
		}
		
		val ePos: Angle = entity.bones(BONE)
		val distance = position.distanceTo(ePos)
		
		val dest = calculateAngle(me, ePos)
		
		val pitchDiff = Math.abs(angle.x - dest.x)
		val yawDiff = Math.abs(angle.y - dest.y)
		val fov = Math.abs(Math.sin(Math.toRadians(yawDiff)) * distance)
		val delta = Math.abs((Math.sin(Math.toRadians(pitchDiff)) + Math.sin(Math.toRadians(yawDiff))) * distance)
		
		if (if (yawOnly) fov <= lockFOV && delta < closestDelta else delta <= lockFOV && delta <= closestDelta) {
			closestFOV = fov
			closestDelta = delta
			closestPlayer = entity
			
			return@result true
		} else {
			return@result false
		}
	}
	
	if (closestDelta == Double.MAX_VALUE || closestDelta < 0 || closestPlayer < 0) return -1
	
	if (PERFECT_AIM && allowPerfect && closestFOV <= PERFECT_AIM_FOV && randInt(100 + 1) <= PERFECT_AIM_CHANCE) {
		perfect.set(true)
	}
	
	return closestPlayer
}

internal fun Entity.inMyTeam() =
		!TEAMMATES_ARE_ENEMIES && if (DANGER_ZONE) {
			me.survivalTeam().let { it > -1 && it == this.survivalTeam() }
		} else me.team() == team()

internal fun Entity.canShoot() = spotted()
		&& !dormant()
		&& !dead()
		&& !inMyTeam()
		&& !me.dead()

internal inline fun <R> aimScript(duration: Int, crossinline precheck: () -> Boolean,
                                  crossinline doAim: (destinationAngle: Angle,
                                                      currentAngle: Angle, aimSpeed: Int) -> R) = every(duration) {
	if (!precheck()) return@every
	if (!me.weaponEntity().canFire()) {
		reset()
		return@every
	}
	
	val aim = ACTIVATE_FROM_FIRE_KEY && keyPressed(FIRE_KEY)
	val forceAim = keyPressed(FORCE_AIM_KEY)
	val boneTrig = ENABLE_BONE_TRIGGER && AIM_ON_BONE_TRIGGER && findTarget(me.position(), clientState.angle(), false, BONE_TRIGGER_FOV, BONE_TRIGGER_BONE, false) >= 0
	val haveAmmo = me.weaponEntity().bullets() > 0

    bone.set(AIM_BONE)

	if (boneTrig) {
		bone.set(BONE_TRIGGER_BONE)
	}

	val pressed = (aim or forceAim or boneTrig) && !MENUTOG && haveAmmo
	var currentTarget = target.get()
	
	if (!pressed) {
		reset()
		return@every
	}
	
	val weapon = me.weapon()
	if (!weapon.pistol && !weapon.automatic && !weapon.shotgun && !weapon.sniper) {
		reset()
		return@every
	}
	
	val currentAngle = clientState.angle()
	
	val position = me.position()
	if (currentTarget < 0) {
		currentTarget = findTarget(position, currentAngle, aim, yawOnly = true)
		if (currentTarget < 0) {
			return@every
		}
		target.set(currentTarget)
	}
	
	if (currentTarget == me || !currentTarget.canShoot()) {
		reset()
		
		if (TARGET_SWAP_MAX_DELAY > 0) {
			Thread.sleep(randLong(TARGET_SWAP_MIN_DELAY, TARGET_SWAP_MAX_DELAY))
		}
	} else { /*if (/*currentTarget.onGround() &&*/ me.onGround())*/
		val bonePosition = currentTarget.bones(bone.get())
		
		val destinationAngle = calculateAngle(me, bonePosition)
		if (AIM_ASSIST_MODE && !perfect.get()) destinationAngle.finalize(currentAngle, AIM_ASSIST_STRICTNESS / 100.0)
		
		val aimSpeed = AIM_SPEED_MIN + randInt(AIM_SPEED_MAX - AIM_SPEED_MIN)
		doAim(destinationAngle, currentAngle, aimSpeed)
	}
}