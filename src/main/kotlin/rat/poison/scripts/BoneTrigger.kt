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
import rat.poison.scripts.aim.boneTrig

private val onBoneTriggerTarget = every(1) {
	if (ENABLE_BONE_TRIGGER)
	{
		if (BONE_TRIGGER_HB) { //Head bone check
			if (findTarget(me.position(), clientState.angle(), false, BONE_TRIGGER_FOV, -2) >= 0) {
				if (AIM_ON_BONE_TRIGGER)
				{
					boneTrig = true
				}
				boneTrigger()
			}
			else
			{
				boneTrig = false
			}
		}
	}
}

fun boneTrigger() {
	if ((keyReleased(FIRE_KEY) && BONE_TRIGGER_ENABLE_KEY && keyPressed(BONE_TRIGGER_KEY)) || (keyReleased(FIRE_KEY) && !BONE_TRIGGER_ENABLE_KEY)) {
		clientDLL[dwForceAttack] = 5.toByte() //Mouse press
		Thread.sleep(randLong(16))
		clientDLL[dwForceAttack] = 4.toByte() //Mouse release
		//Thread.sleep(BONE_TRIGGER_SHOT_DELAY.toLong() /*+ randLong(16)*/) //Make sure we can shoot an accurate shot
	}
}