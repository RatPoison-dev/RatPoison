package rat.poison.scripts.aim

import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.settings.*
import rat.poison.utils.*
import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.strToBool
import java.lang.Math.toRadians
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.abs
import kotlin.math.sin

val target = AtomicLong(-1)
val bone = AtomicInteger(AIM_BONE)
val perfect = AtomicBoolean() //Perfect Aim boolean check, only for path aim
var boneTrig = false

internal fun reset() {
	target.set(-1L)
	perfect.set(false)
}

internal fun findTarget(position: Angle, angle: Angle, allowPerfect: Boolean,
						lockFOV: Int = curSettings["AIM_FOV"]!!.toInt(), BONE: Int = curSettings["AIM_BONE"]!!.toInt()): Player {
	var closestFOV = Double.MAX_VALUE
	var closestDelta = Double.MAX_VALUE
	var closestPlayer = -1L

	forEntities(ccsPlayer) result@{
		val entity = it.entity
		if (entity <= 0 || entity == me || !entity.canShoot()) {
			return@result false
		}

		if (BONE == -3) { //Knife bot bone
			for (i in 3..8) {
				val arr = calcTarget(closestDelta, entity, position, angle, lockFOV, i)

				if (arr[0] != -1.0) {
					closestFOV = arr[0] as Double
					closestDelta = arr[1] as Double
					closestPlayer = arr[2] as Long
				}
			}
		} else if (BONE == -2) { //Trigger bot bone
			if (curSettings["BONE_TRIGGER_HB"]!!.strToBool() && curSettings["BONE_TRIGGER_BB"]!!.strToBool()) {
				for (i in 3..8) //Check all body bones & head bone
				{
					val arr = calcTarget(closestDelta, entity, position, angle, lockFOV, i)

					if (arr[0] != -1.0) {
						closestFOV = arr[0] as Double
						closestDelta = arr[1] as Double
						closestPlayer = arr[2] as Long
					}
				}
			} else if (curSettings["BONE_TRIGGER_BB"]!!.strToBool()) {
				for (i in 3..7) { //Check all body bones
					val arr = calcTarget(closestDelta, entity, position, angle, lockFOV, i)

					if (arr[0] != -1.0) {
						closestFOV = arr[0] as Double
						closestDelta = arr[1] as Double
						closestPlayer = arr[2] as Long
					}
				}
			} else {
				val arr = calcTarget(closestDelta, entity, position, angle, lockFOV, HEAD_BONE)

				if (arr[0] != -1.0) {
					closestFOV = arr[0] as Double
					closestDelta = arr[1] as Double
					closestPlayer = arr[2] as Long
				}
			}
		} else {
			if (BONE == -1) { //Nearest bone check
				val arr = calcTarget(closestDelta, entity, position, angle, lockFOV, entity.nearestBone())

				if (arr[0] != -1.0) {
					closestFOV = arr[0] as Double
					closestDelta = arr[1] as Double
					closestPlayer = arr[2] as Long
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
	if (curSettings["PERFECT_AIM"]!!.strToBool() && allowPerfect && closestFOV <= curSettings["PERFECT_AIM_FOV"]!!.toInt() && randInt(100 + 1) <= curSettings["PERFECT_AIM_CHANCE"]!!.toInt()) {
		perfect.set(true)
	}

	return closestPlayer
}

internal fun calcTarget(calcClosestDelta: Double, entity: Entity, position: Angle, angle: Angle, lockFOV: Int = curSettings["AIM_FOV"]!!.toInt(), BONE: Int): MutableList<Any> {
	val retList = mutableListOf(-1.0, 0.0, 0L)

	val ePos: Angle = entity.bones(BONE)
	val distance = position.distanceTo(ePos)

	val dest = calculateAngle(me, ePos)

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

internal fun Entity.inMyTeam() =
		!curSettings["TEAMMATES_ARE_ENEMIES"]!!.strToBool() && if (DANGER_ZONE) {
			me.survivalTeam().let { it > -1 && it == this.survivalTeam() }
		} else me.team() == team()

internal fun Entity.canShoot() = (if (DANGER_ZONE) { true } else { spotted() }
		&& !dormant()
		&& !dead()
		&& !inMyTeam()
		&& !isProtected()
		&& !me.dead())

internal inline fun <R> aimScript(duration: Int, crossinline precheck: () -> Boolean,
								  crossinline doAim: (destinationAngle: Angle,
													  currentAngle: Angle, aimSpeed: Int) -> R) = every(duration) {
	try {
		if (!precheck()) return@every
		if (!curSettings["ENABLE_AIM"]!!.strToBool()) return@every

		if (!me.weaponEntity().canFire() && !me.weapon().automatic) { //Aim after shoot
			reset()
			return@every
		}

		if (me.weapon().sniper && !me.isScoped() && curSettings["ENABLE_SCOPED_ONLY"]!!.strToBool()) {
			reset()
			return@every
		}

		val aim = curSettings["ACTIVATE_FROM_AIM_KEY"]!!.strToBool() && keyPressed(curSettings["AIM_KEY"]!!.toInt())
		val forceAim = keyPressed(curSettings["FORCE_AIM_KEY"]!!.toInt())
		val haveAmmo = me.weaponEntity().bullets() > 0

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
				reset()
				return@every
			}
			target.set(currentTarget)
		}

		val aB = curSettings["AIM_BONE"]!!.toInt()

		if (aB == -1) { //Nearest bone check
			val nearestBone = currentTarget.nearestBone()

			if (nearestBone != -1) {
				bone.set(nearestBone)
			}
		} else {
			bone.set(aB)
		}

		if (currentTarget == me || !currentTarget.canShoot()) {
			//Thread.sleep(500)
			reset()
			return@every
		} else {
			val bonePosition = currentTarget.bones(bone.get())

			val destinationAngle = calculateAngle(me, bonePosition) //Rename to current angle

			if (!perfect.get()) {
				destinationAngle.finalize(currentAngle, (1.1 - curSettings["AIM_SMOOTHNESS"]!!.toDouble() / 5))
			}

			val aimSpeed = curSettings["AIM_SPEED"]!!.toInt()
			doAim(destinationAngle, currentAngle, aimSpeed)
		}
	} catch (e: Exception) {println(e)}
}