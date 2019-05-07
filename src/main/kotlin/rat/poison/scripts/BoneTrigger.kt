package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import org.jire.arrowhead.keyReleased
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.game.entity.position
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets.dwForceAttack
import rat.poison.scripts.aim.findTarget
import rat.poison.settings.*
import rat.poison.utils.*
import rat.poison.App.haveTarget
import rat.poison.curSettings
import rat.poison.opened
import rat.poison.scripts.aim.boneTrig
import rat.poison.strToBool
import rat.poison.ui.bTrigTab
import rat.poison.ui.mainTabbedPane

private val onBoneTriggerTarget = every(4) {
	if (opened && haveTarget) {
		if (DANGER_ZONE) {
			mainTabbedPane.disableTab(bTrigTab, true)
		} else {
			mainTabbedPane.disableTab(bTrigTab, false)

			if (curSettings["ENABLE_BONE_TRIGGER"]!!.strToBool()) {
				val currentAngle = clientState.angle()
				val position = me.position()
				if (findTarget(position, currentAngle, false, curSettings["BONE_TRIGGER_FOV"].toString().toInt(), -2) >= 0) {
					//boneTrig = curSettings["AIM_ON_BONE_TRIGGER"]!!.strToBool()
					boneTrigger()
				} else { boneTrig = false }
			} else { boneTrig = false }
		}
	}
}

fun boneTrigger() {
	if ((keyReleased(curSettings["FIRE_KEY"].toString().toInt()) && curSettings["BONE_TRIGGER_ENABLE_KEY"]!!.strToBool() && keyPressed(curSettings["BONE_TRIGGER_KEY"].toString().toInt())) || (keyReleased(FIRE_KEY) && !curSettings["BONE_TRIGGER_ENABLE_KEY"]!!.strToBool())) {
		clientDLL[dwForceAttack] = 5.toByte() //Mouse press
		Thread.sleep(randLong(16))
		clientDLL[dwForceAttack] = 4.toByte() //Mouse release
		//Thread.sleep(BONE_TRIGGER_SHOT_DELAY.toLong() /*+ randLong(16)*/)
	}
}