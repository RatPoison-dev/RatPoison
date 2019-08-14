package rat.poison.scripts

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jire.arrowhead.keyPressed
import org.jire.arrowhead.keyReleased
import rat.poison.App.haveTarget
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets.dwForceAttack
import rat.poison.scripts.aim.findTarget
import rat.poison.settings.*
import rat.poison.utils.*
import rat.poison.curSettings
import rat.poison.game.entity.position
import rat.poison.game.entity.weapon
import rat.poison.opened
import rat.poison.scripts.aim.boneTrig
import rat.poison.scripts.aim.canShoot
import rat.poison.strToBool
import rat.poison.ui.bTrigTab
import rat.poison.ui.mainTabbedPane

private val onBoneTriggerTarget = every(4) {
    if (curSettings["MENU"]!!.strToBool() && opened && haveTarget) {
        if (DANGER_ZONE) {
            mainTabbedPane.disableTab(bTrigTab, true)
        } else {
            mainTabbedPane.disableTab(bTrigTab, false)
        }
    }

    boneTrig = false

    if (curSettings["ENABLE_BONE_TRIGGER"]!!.strToBool()) {
        if (!me.weapon().knife) {
            if (keyReleased(curSettings["AIM_KEY"]!!.toInt())) {
                val currentAngle = clientState.angle()
                val position = me.position()
                val target = findTarget(position, currentAngle, false, curSettings["BONE_TRIGGER_FOV"]!!.toInt(), -2)
                if (target >= 0 && target.canShoot()) {
                    if ((curSettings["BONE_TRIGGER_ENABLE_KEY"]!!.strToBool() && keyPressed(curSettings["BONE_TRIGGER_KEY"]!!.toInt())) || !curSettings["BONE_TRIGGER_ENABLE_KEY"]!!.strToBool()) {
                        boneTrig = true
                        boneTrig = curSettings["AIM_ON_BONE_TRIGGER"]!!.strToBool()
                        boneTrigger()
                    }
                }
            }
//            } else {//bandaid
//                clientDLL[dwForceAttack] = 6.toByte()
//            }
        }
    }
}

var inShot = false

fun boneTrigger() {
    if (!inShot) {
        inShot = true
        val delay = curSettings["BONE_TRIGGER_SHOT_DELAY"]!!.toLong()
        if (delay > 0) {
            GlobalScope.launch {
                delay(delay)
                clientDLL[dwForceAttack] = 6.toByte() //--Mouse press
                inShot = false
            }
        } else {
            clientDLL[dwForceAttack] = 6.toByte() //--Mouse press
            inShot = false
        }
    }
}