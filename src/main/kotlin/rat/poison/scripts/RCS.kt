package rat.poison.scripts

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import rat.poison.curSettings
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.game.entity.punch
import rat.poison.game.entity.shotsFired
import rat.poison.game.entity.weapon
import rat.poison.game.entity.weaponEntity
import rat.poison.game.me
import rat.poison.game.setAngle
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.normalize

private val lastPunch = Vector2()
private val newPunch = Vector2()
private val playerPunch = Vector3()

fun rcs() = every(4) {
	if (me <= 0 || !curSettings["ENABLE_RCS"].strToBool()) return@every

	val weaponEntity = me.weaponEntity()
	val weapon = me.weapon(weaponEntity)
	if (!weapon.automatic) { lastPunch.set(0F, 0F); return@every }
	val shotsFired = me.shotsFired()
	val p = me.punch()

	val forceSet = (shotsFired == 0 && !lastPunch.isZero)

	if (forceSet || /*!finishPunch ||*/ shotsFired > 1) {
		if (lastPunch.isZero) {
			lastPunch.set(p.x, p.y)
		}

		playerPunch.set(p.x, p.y, p.z)
		newPunch.set(playerPunch.x - lastPunch.x, playerPunch.y - lastPunch.y)
		newPunch.scl(1F + curSettings["RCS_SMOOTHING_Y"].toFloat(), 1F + curSettings["RCS_SMOOTHING_X"].toFloat())

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