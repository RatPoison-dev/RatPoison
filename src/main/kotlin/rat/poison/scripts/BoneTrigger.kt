package rat.poison.scripts

import rat.poison.game.CSGO.clientDLL
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.game.entity.position
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets.dwForceAttack
import rat.poison.scripts.aim.findTarget
import rat.poison.settings.*
import rat.poison.utils.*
import org.jire.arrowhead.keyPressed
import org.jire.arrowhead.keyReleased
import rat.poison.game.entity.punch

private val onBoneTriggerTarget = hook(1) {
	if (ENABLE_BONE_TRIGGER)
		findTarget(me.position(), clientState.angle(), false, BONE_TRIGGER_FOV, BONE_TRIGGER_BONE, false) >= 0
	else false
}

fun boneTrigger() = onBoneTriggerTarget {

	//if (me.punch().x == 0.0 && me.punch().y == 0.00) {
		if ((keyReleased(FIRE_KEY) && BONE_TRIGGER_ENABLE_KEY && keyPressed(BONE_TRIGGER_KEY)) || (keyReleased(FIRE_KEY) && !BONE_TRIGGER_ENABLE_KEY)) {
			if (LEAGUE_MODE) mouse(MOUSEEVENTF_LEFTDOWN) else clientDLL[dwForceAttack] = 5.toByte() //Mouse press
			Thread.sleep(randLong(16))
			if (LEAGUE_MODE) mouse(MOUSEEVENTF_LEFTUP) else clientDLL[dwForceAttack] = 4.toByte() //Mouse release
			//Thread.sleep(BONE_TRIGGER_SHOT_DELAY.toLong() /*+ randLong(16)*/) //Make sure we can shoot an accurate shot
		}
	//}
}