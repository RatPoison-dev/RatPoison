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
import rat.poison.strToBool
import rat.poison.ui.bTrigTab
import rat.poison.ui.mainTabbedPane

private const val SwingDistance = 96f
private const val StabDistance = 64f

private val onBoneTriggerTarget = every(4) {
    if (curSettings["MENU"]!!.strToBool() && opened && haveTarget) {
        if (DANGER_ZONE) {
            mainTabbedPane.disableTab(bTrigTab, true)
        } else {
            mainTabbedPane.disableTab(bTrigTab, false)

            if (!me.weapon().knife) {
                if (curSettings["ENABLE_BONE_TRIGGER"]!!.strToBool()) {
                    val currentAngle = clientState.angle()
                    val position = me.position()
                    if (findTarget(position, currentAngle, false, curSettings["BONE_TRIGGER_FOV"]!!.toInt(), -2) >= 0) {
                        if ((keyReleased(curSettings["FIRE_KEY"]!!.toInt()) && curSettings["BONE_TRIGGER_ENABLE_KEY"]!!.strToBool() && keyPressed(curSettings["BONE_TRIGGER_KEY"]!!.toInt())) || (keyReleased(FIRE_KEY) && !curSettings["BONE_TRIGGER_ENABLE_KEY"]!!.strToBool())) {
                            boneTrig = curSettings["AIM_ON_BONE_TRIGGER"]!!.strToBool()
                            boneTrigger()
                        }
                    } else {
                        boneTrig = false
                    }
                } else {
                    boneTrig = false
                }
            } else {
                boneTrig = false
            }
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
                clientDLL[dwForceAttack] = 5.toByte() //Mouse press
                delay(24)
                clientDLL[dwForceAttack] = 4.toByte() //Mouse release
                inShot = false
            }
        }
        else {
            clientDLL[dwForceAttack] = 5.toByte() //Mouse press
            Thread.sleep(24)
            clientDLL[dwForceAttack] = 4.toByte() //Mouse release
            inShot = false
        }
    }
}