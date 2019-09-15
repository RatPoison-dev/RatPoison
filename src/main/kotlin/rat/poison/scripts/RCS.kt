package rat.poison.scripts

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import rat.poison.App
import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.strToBool
import rat.poison.utils.*

private val lastPunch = Vector2()
private val newPunch = Vector2()
private val playerPunch = Vector3()

fun rcs() = every(10) {
	if (me <= 0 || !curSettings["ENABLE_RCS"].strToBool()) return@every

	val weaponEntity = me.weaponEntity()
	val weapon = me.weapon(weaponEntity)
	if (!weapon.automatic) { lastPunch.set(0F, 0F); return@every }
	val shotsFired = me.shotsFired()
	val p = me.punch()

	val forceSet = (shotsFired == 0 && !lastPunch.isZero)
	val finishPunch = true

	if (forceSet || !finishPunch || shotsFired > 1) {
		if (lastPunch.isZero) {
			lastPunch.set(p.x.toFloat(), p.y.toFloat())
		}
		playerPunch.set(p.x.toFloat(), p.y.toFloat(), p.z.toFloat())
		newPunch.set(playerPunch.x - lastPunch.x, playerPunch.y - lastPunch.y)
		newPunch.scl(1F+ curSettings["RCS_SMOOTHING_Y"].toFloat(), 1F+ curSettings["RCS_SMOOTHING_X"].toFloat())

		val angle = clientState.angle()
		angle.apply {
			x -= newPunch.x
			y -= newPunch.y
			normalize()
		}
		clientState.setAngle(angle)
		lastPunch.x = playerPunch.x
		lastPunch.y = playerPunch.y

		if (!curSettings["RCS_RETURNAIM"].strToBool() && forceSet) {
			lastPunch.set(0F, 0F)
		}
	}
}