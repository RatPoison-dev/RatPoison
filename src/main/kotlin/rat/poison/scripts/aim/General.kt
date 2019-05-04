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
var boneTrig = false

internal fun reset() {
	target.set(-1L)
	perfect.set(false)
}

var closestFOV = Double.MAX_VALUE
var closestDelta = Double.MAX_VALUE
var closestPlayer = -1L

internal fun findTarget(position: Angle, angle: Angle, allowPerfect: Boolean,
						lockFOV: Int = AIM_FOV, BONE: Int = AIM_BONE): Player {
	closestFOV = Double.MAX_VALUE
	closestDelta = Double.MAX_VALUE
	closestPlayer = -1L

	forEntities(ccsPlayer) result@{
		val entity = it.entity
		if (entity <= 0 || entity == me || !entity.canShoot()) {
			return@result false
		}

		if (BONE == -2)
		{
			if (BONE_TRIGGER_HB && BONE_TRIGGER_BB)
			{
				for (i in 3..8)
				{
					calcTarget(entity, position, angle, lockFOV, i)
				}
			}
			else if (BONE_TRIGGER_BB)
			{
				for (i in 3..7)
				{
					calcTarget(entity, position, angle, lockFOV, i)
				}
			}
			else
			{
				calcTarget(entity, position, angle, lockFOV, HEAD_BONE)
			}
		}
		else
		{
			if (BONE == BODY_BONE)
			{
				calcTarget(entity, position, angle, lockFOV, BODY_BONE)
			}
			else
			{
				calcTarget(entity, position, angle, lockFOV, HEAD_BONE)
			}
		}
		return@result false
	}

	if (closestDelta == Double.MAX_VALUE || closestDelta < 0 || closestPlayer < 0) return -1
	
	if (PERFECT_AIM && allowPerfect && closestFOV <= PERFECT_AIM_FOV && randInt(100 + 1) <= PERFECT_AIM_CHANCE) {
		perfect.set(true)
	}
	
	return closestPlayer
}

internal fun calcTarget(entity: Entity, position: Angle, angle: Angle, lockFOV: Int = AIM_FOV, BONE: Int = HEAD_BONE) {
	val ePos: Angle = entity.bones(BONE)
	val distance = position.distanceTo(ePos)

	val dest = calculateAngle(me, ePos)

	val pitchDiff = Math.abs(angle.x - dest.x)
	val yawDiff = Math.abs(angle.y - dest.y)
	val fov = Math.abs(Math.sin(Math.toRadians(yawDiff)) * distance)
	val delta = Math.abs((Math.sin(Math.toRadians(pitchDiff)) + Math.sin(Math.toRadians(yawDiff))) * distance)

	if (delta <= lockFOV && delta <= closestDelta) {
		closestFOV = fov
		closestDelta = delta
		closestPlayer = entity
	}
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

	val aim = ACTIVATE_FROM_FIRE_KEY && keyPressed(FIRE_KEY)
	val forceAim = keyPressed(FORCE_AIM_KEY)
	val haveAmmo = me.weaponEntity().bullets() > 0

    bone.set(AIM_BONE)

	val pressed = (aim || forceAim || boneTrig) && !MENUTOG && haveAmmo
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
		currentTarget = findTarget(position, currentAngle, aim)
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
		
		val aimSpeed = AIM_SPEED
		doAim(destinationAngle, currentAngle, aimSpeed)
	}
}