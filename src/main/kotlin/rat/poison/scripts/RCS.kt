

package rat.poison.scripts

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.scripts.aim.bone
import rat.poison.settings.*
import rat.poison.utils.every
import rat.poison.utils.normalize
import rat.poison.utils.randDouble

private val lastPunch = Vector2()
private val newPunch = Vector2()
private val playerPunch = Vector3()

fun rcs() = every(RCS_MIN_DURATION, RCS_MAX_DURATION) {
	if (me <= 0 || !ENABLE_RCS) return@every
	val weaponEntity = me.weaponEntity()
	val weapon = me.weapon(weaponEntity)
	if (!weapon.automatic) return@every
	val shotsFired = me.shotsFired()
	val forceSet = shotsFired == 0 && !lastPunch.isZero
	if (forceSet || shotsFired > 0 || weaponEntity.bullets() < 1) {
		val p = me.punch()
		playerPunch.set(p.x.toFloat(), p.y.toFloat(), p.z.toFloat())
		newPunch.set(playerPunch.x - lastPunch.x, playerPunch.y - lastPunch.y)
		newPunch.scl((if (RCS_MAX > RCS_MIN) randDouble(RCS_MIN, RCS_MAX) else RCS_MIN).toFloat(),
				(if (RCS_MAX > RCS_MIN) randDouble(RCS_MIN, RCS_MAX) else RCS_MIN).toFloat())
		
		val angle = clientState.angle()
		angle.apply {
			x -= newPunch.x
			y -= newPunch.y
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
		else -> HEAD_BONE
	})
}