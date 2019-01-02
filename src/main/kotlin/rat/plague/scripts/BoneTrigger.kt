

package rat.plague.scripts

import rat.plague.game.CSGO.clientDLL
import rat.plague.game.angle
import rat.plague.game.clientState
import rat.plague.game.entity.position
import rat.plague.game.me
import rat.plague.game.offsets.ClientOffsets.dwForceAttack
import rat.plague.scripts.aim.findTarget
import rat.plague.settings.*
import rat.plague.utils.*
import org.jire.arrowhead.keyPressed
import org.jire.arrowhead.keyReleased

private val onBoneTriggerTarget = hook(1) {
	if (ENABLE_BONE_TRIGGER)
		findTarget(me.position(), clientState.angle(), false, BONE_TRIGGER_FOV, BONE_TRIGGER_BONE, false) >= 0
	else false
}

fun boneTrigger() = onBoneTriggerTarget {

	if ((keyReleased(FIRE_KEY) && BONE_TRIGGER_ENABLE_KEY && keyPressed(BONE_TRIGGER_KEY)) || (keyReleased(FIRE_KEY) && !BONE_TRIGGER_ENABLE_KEY)) {
		if (LEAGUE_MODE) mouse(MOUSEEVENTF_LEFTDOWN) else clientDLL[dwForceAttack] = 5.toByte() //Mouse press
		Thread.sleep(BONE_TRIGGER_SHOT_DELAY + randLong(16))
		if (LEAGUE_MODE) mouse(MOUSEEVENTF_LEFTUP) else clientDLL[dwForceAttack] = 4.toByte() //Mouse release
		Thread.sleep(BONE_TRIGGER_SHOT_DELAY + randLong(16))
	}
}