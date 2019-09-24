package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import org.jire.arrowhead.keyReleased
import rat.poison.App
import rat.poison.App.haveTarget
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets.dwForceAttack
import rat.poison.scripts.aim.findTarget
import rat.poison.settings.*
import rat.poison.curSettings
import rat.poison.game.Weapons
import rat.poison.game.entity.*
import rat.poison.game.entity.bullets
import rat.poison.game.entity.position
import rat.poison.opened
import rat.poison.scripts.aim.boneTrig
import rat.poison.scripts.aim.canShoot
import rat.poison.strToBool
import rat.poison.ui.bTrigTab
import rat.poison.ui.mainTabbedPane
import rat.poison.utils.every
import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.event.KeyEvent

private val robot = Robot().apply { this.autoDelay = 0 }
private var callingInShot = false
private var inShot = false

fun boneTrigger() = every(10) {
    if (curSettings["MENU"].strToBool() && opened && haveTarget) {
        if (DANGER_ZONE) {
            mainTabbedPane.disableTab(bTrigTab, true)
        } else {
            mainTabbedPane.disableTab(bTrigTab, false)
        }
    }

    if (DANGER_ZONE) {
        return@every
    }

    boneTrig = false

    if (curSettings["ENABLE_BONE_TRIGGER"].strToBool()) {
    val wep = me.weapon()

    var bFOV = 0
    //Change to one val, why have 5?
    var bTrigPistols = false
    var bTrigRifles = false
    var bTrigShotguns = false
    var bTrigSnipers = false
    var bTrigSmgs = false

    when {
        wep.pistol -> { bFOV = curSettings["BONE_TRIGGER_PISTOLS_FOV"].toInt(); if (curSettings["BONE_TRIGGER_PISTOLS"].strToBool()) bTrigPistols = true; }
        wep.rifle -> { bFOV = curSettings["BONE_TRIGGER_RIFLES_FOV"].toInt(); if (curSettings["BONE_TRIGGER_RIFLES"].strToBool()) bTrigRifles = true; }
        wep.shotgun -> { bFOV = curSettings["BONE_TRIGGER_SHOTGUNS_FOV"].toInt(); if (curSettings["BONE_TRIGGER_SHOTGUNS"].strToBool()) bTrigShotguns = true; }
        wep.sniper -> { bFOV = curSettings["BONE_TRIGGER_SNIPERS_FOV"].toInt(); if (curSettings["BONE_TRIGGER_SNIPERS"].strToBool() && ((me.isScoped() && curSettings["ENABLE_SCOPED_ONLY"].strToBool()) || (!curSettings["ENABLE_SCOPED_ONLY"].strToBool()))) bTrigSnipers = true; }
        wep.smg -> { bFOV = curSettings["BONE_TRIGGER_SMGS_FOV"].toInt(); if (curSettings["BONE_TRIGGER_SMGS"].strToBool()) bTrigSmgs = true; }
    }

        if (bTrigPistols || bTrigRifles || bTrigShotguns || bTrigSnipers || bTrigSmgs) {
            val wepEnt = me.weaponEntity()
            if (!me.weapon().knife && wepEnt.bullets() > 0) { //Prevents snap trigger preventing spamming
                if (wep.automatic || (!wep.automatic && wepEnt.canFire())) {
                    if (keyReleased(AIM_KEY)) {
                        val currentAngle = clientState.angle()
                        val position = me.position()
                        val target = findTarget(position, currentAngle, false, bFOV, -2)
                        if (target >= 0 && target.canShoot()) {
                            if ((curSettings["BONE_TRIGGER_ENABLE_KEY"].strToBool() && keyPressed(curSettings["BONE_TRIGGER_KEY"].toInt())) || !curSettings["BONE_TRIGGER_ENABLE_KEY"].strToBool()) {
                                bTrigShoot()
                                return@every
                            }
                        }
                    }
                }
            }
            callingInShot = false
            inShot = false
        }
    }
}

//Initial shot delay
fun bTrigShoot() {
    if (!callingInShot) {
        callingInShot = true
        Thread(Runnable {//Coroutine is slow to initialize, bad for X delay
            Thread.sleep(curSettings["BONE_TRIGGER_SHOT_DELAY"].toLong())
            inShot = true
        }).start()
    }

    if (inShot) {
        //Switch me.weapon() categ -> doesn't work

        boneTrig = (me.weapon().pistol && curSettings["BONE_TRIGGER_PISTOLS_AIMBOT"].strToBool()) ||
                (me.weapon().rifle && curSettings["BONE_TRIGGER_RIFLES_AIMBOT"].strToBool()) ||
                (me.weapon().shotgun && curSettings["BONE_TRIGGER_SHOTGUNS_AIMBOT"].strToBool()) ||
                (me.weapon().smg && curSettings["BONE_TRIGGER_SMGS_AIMBOT"].strToBool()) ||
                (me.weapon().sniper && curSettings["BONE_TRIGGER_SNIPERS_AIMBOT"].strToBool())

        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
        Thread.sleep(25)
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
    }

    //inShot = false
}