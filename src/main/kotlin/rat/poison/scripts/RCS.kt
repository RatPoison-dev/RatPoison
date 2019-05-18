package rat.poison.scripts

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.scripts.aim.bone
import rat.poison.settings.*
import rat.poison.strToBool
import rat.poison.utils.*

private val lastPunch = Vector2()
private val newPunch = Vector2()
private val playerPunch = Vector3()

fun rcs() = every(1) {
	if (me <= 0 || !curSettings["ENABLE_RCS"]!!.strToBool()) return@every
	val weaponEntity = me.weaponEntity()
	val weapon = me.weapon(weaponEntity)
	if (!weapon.automatic) return@every
	val shotsFired = me.shotsFired()
	val p = me.punch()

	val forceSet = (shotsFired == 0 && !lastPunch.isZero)
	val finishPunch = true

	if (forceSet || !finishPunch || shotsFired > 1) { //Fixes aim jumping down
		if (lastPunch.isZero) {
			lastPunch.set(p.x.toFloat(), p.y.toFloat())
		}
		playerPunch.set(p.x.toFloat(), p.y.toFloat(), p.z.toFloat())
		newPunch.set(playerPunch.x - lastPunch.x, playerPunch.y - lastPunch.y)
		newPunch.scl(1F+curSettings["RCS_SMOOTHING"].toString().toFloat(), 1F+curSettings["RCS_SMOOTHING"].toString().toFloat())

		val angle = clientState.angle()
		angle.apply {
			x -= newPunch.x
			y -= newPunch.y
			normalize()
		}
		clientState.setAngle(angle)
		lastPunch.x = playerPunch.x
		lastPunch.y = playerPunch.y

		if (!curSettings["RCS_RETURNAIM"]!!.strToBool() && forceSet) {
			lastPunch.set(0F, 0F)
		}
	}

	bone.set(when {
		shotsFired >= SHIFT_TO_BODY_SHOTS -> BODY_BONE
		shotsFired >= SHIFT_TO_SHOULDER_SHOTS -> SHOULDER_BONE
		else -> curSettings["AIM_BONE"].toString().toInt()
	})
}