package rat.poison.scripts

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.scripts.aim.bone
import rat.poison.settings.*
import rat.poison.utils.*

private val lastPunch = Vector2()
private val newPunch = Vector2()
private val playerPunch = Vector3()

fun rcs() = every(1) {
	if (me <= 0 || !ENABLE_RCS) return@every
	val weaponEntity = me.weaponEntity()
	val weapon = me.weapon(weaponEntity)
	if (!weapon.automatic) return@every
	val shotsFired = me.shotsFired()
	val p = me.punch()

	val forceSet : Boolean
	val finishPunch : Boolean

	if (RCS_RETURNAIM) {
		forceSet = false//(shotsFired == 0 && !lastPunch.isZero)
		finishPunch = (p.x != 0.0 && p.y != 0.0)
	}
	else
	{
		forceSet = (shotsFired == 0 && !lastPunch.isZero)
		finishPunch = false
	}
	if (forceSet || finishPunch || shotsFired > 1) { //Fixes aim jumping down
		playerPunch.set(p.x.toFloat(), p.y.toFloat(), p.z.toFloat())
		newPunch.set(playerPunch.x - lastPunch.x, playerPunch.y - lastPunch.y)
		newPunch.scl(2F, 2F)

		val angle = clientState.angle()
		angle.apply {
			x -= newPunch.x*RCS_SMOOTHING
			y -= newPunch.y*RCS_SMOOTHING
			normalize()
		}
		clientState.setAngle(angle)
		lastPunch.x = playerPunch.x
		lastPunch.y = playerPunch.y

		if (forceSet) {
			lastPunch.set(0F, 0F)
		}
	}

	bone.set(when {
		shotsFired >= SHIFT_TO_BODY_SHOTS -> BODY_BONE
		shotsFired >= SHIFT_TO_SHOULDER_SHOTS -> SHOULDER_BONE
		else -> AIM_BONE
	})
}