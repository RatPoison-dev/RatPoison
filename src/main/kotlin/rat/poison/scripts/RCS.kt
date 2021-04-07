package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.game.entity.bullets
import rat.poison.game.entity.punch
import rat.poison.game.entity.shotsFired
import rat.poison.game.me
import rat.poison.game.setAngle
import rat.poison.scripts.aim.meCurWep
import rat.poison.scripts.aim.meCurWepEnt
import rat.poison.scripts.aim.meDead
import rat.poison.utils.common.Vector
import rat.poison.utils.common.every
import rat.poison.utils.common.normalize

private val lastAppliedRCS = Vector()
private val punchVec = Vector()
private val realPunchVec = Vector()
private var realPunchX = 0F
private var realPunchY = 0F
private val meAng = Vector()
private val vecSub = Vector()

fun rcs() = every(15, inGameCheck = true) {
	if (me <= 0 || !curSettings.bool["ENABLE_RCS"] || meDead) return@every

	val weapon = meCurWep
	if (!weapon.automatic) { lastAppliedRCS.set(0F, 0F); return@every }
	val shotsFired = me.shotsFired()
	val p = me.punch(punchVec)

	val isZero = lastAppliedRCS.invalid()
	val forceSet = (shotsFired == 0 && !isZero || meCurWepEnt.bullets() <= 0)

	if (forceSet || /*!finishPunch ||*/ shotsFired > 1) {
		realPunchX = p.x * 2
		realPunchY = p.y * 2
		if (curSettings["RCS_TYPE"] == "STABLE") {
			if (isZero) {
				lastAppliedRCS.set(realPunchX, realPunchY)
			}

			val realPunch = realPunchVec.set(realPunchX, realPunchY)

			val punchToApply = realPunch - lastAppliedRCS
			punchToApply.scl(curSettings.float["RCS_SMOOTHING_X"], curSettings.float["RCS_SMOOTHING_Y"])

			val angle = clientState.angle(meAng)
			angle.apply {
				x -= punchToApply.x
				y -= punchToApply.y
				normalize()
			}

			clientState.setAngle(angle)

			lastAppliedRCS.x += punchToApply.x
			lastAppliedRCS.y += punchToApply.y

			if (!curSettings.bool["RCS_RETURNAIM"] && forceSet) {
				lastAppliedRCS.set(0F, 0F)
			}
		} else {
			if (isZero) {
				lastAppliedRCS.set(p.x, p.y)
			}

			vecSub.set(
					p //Set playerPunch to current punch
							.x - lastAppliedRCS.x, p //Set playerPunch to current punch
					.y - lastAppliedRCS.y
			)
			vecSub.scl(1F + curSettings.float["RCS_SMOOTHING_Y"], 1F + curSettings.float["RCS_SMOOTHING_X"])

			val angle = clientState.angle(meAng)
			angle.apply {
				x -= vecSub.x
				y -= vecSub.y
				normalize()
			}

			clientState.setAngle(angle)
			lastAppliedRCS.x = p //Set playerPunch to current punch
					.x
			lastAppliedRCS.y = p //Set playerPunch to current punch
					.y

			if (!curSettings.bool["RCS_RETURNAIM"] && forceSet) {
				lastAppliedRCS.set(0F, 0F)
			}
		}
	}
}