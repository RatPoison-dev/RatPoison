package rat.poison.scripts

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import rat.poison.curSettings
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.game.entity.punch
import rat.poison.game.entity.shotsFired
import rat.poison.game.me
import rat.poison.game.setAngle
import rat.poison.scripts.aim.meCurWep
import rat.poison.scripts.aim.meDead
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.normalize

private val lastAppliedRCS = Vector2()

fun rcs() = every(4, inGameCheck = true) {
	if (me <= 0 || !curSettings["ENABLE_RCS"].strToBool() || meDead) return@every

	val weapon = meCurWep
	if (!weapon.automatic) { lastAppliedRCS.set(0F, 0F); return@every }
	val shotsFired = me.shotsFired()
	val p = me.punch()

	val forceSet = (shotsFired == 0 && !lastAppliedRCS.isZero)

	if (forceSet || /*!finishPunch ||*/ shotsFired > 1) {
		if (curSettings["RCS_TYPE"] == "STABLE") {
			if (lastAppliedRCS.isZero) {
				lastAppliedRCS.set(p.x * 2, p.y * 2)
			}

			val realPunch = Vector2(p.x * 2, p.y * 2)

			val punchToApply = Vector2(realPunch.x - lastAppliedRCS.x, realPunch.y - lastAppliedRCS.y)
			punchToApply.scl(curSettings["RCS_SMOOTHING_Y"].toFloat(), curSettings["RCS_SMOOTHING_X"].toFloat())

			val angle = clientState.angle()
			angle.apply {
				x -= punchToApply.x
				y -= punchToApply.y
				normalize()
			}

			clientState.setAngle(angle)

			lastAppliedRCS.x += punchToApply.x
			lastAppliedRCS.y += punchToApply.y

			if (!curSettings["RCS_RETURNAIM"].strToBool() && forceSet) {
				lastAppliedRCS.set(0F, 0F)
			}
		} else {
			if (lastAppliedRCS.isZero) {
				lastAppliedRCS.set(p.x, p.y)
			}

			val playerPunch = Vector3(p.x, p.y, p.z) //Set playerPunch to current punch

			val punchToApply = Vector2((playerPunch.x - lastAppliedRCS.x), (playerPunch.y - lastAppliedRCS.y)) //Set to our current punch and what our last punch was
			punchToApply.scl(1F + curSettings["RCS_SMOOTHING_Y"].toFloat(), 1F + curSettings["RCS_SMOOTHING_X"].toFloat())

			val angle = clientState.angle()
			angle.apply {
				x -= punchToApply.x
				y -= punchToApply.y
				normalize()
			}

			clientState.setAngle(angle)
			lastAppliedRCS.x = playerPunch.x
			lastAppliedRCS.y = playerPunch.y

			if (!curSettings["RCS_RETURNAIM"].strToBool() && forceSet) {
				lastAppliedRCS.set(0F, 0F)
			}
		}
	}
}