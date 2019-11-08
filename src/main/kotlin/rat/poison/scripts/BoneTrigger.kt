package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import org.jire.arrowhead.keyReleased
import rat.poison.App.haveTarget
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.game.me
import rat.poison.scripts.aim.findTarget
import rat.poison.settings.*
import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.*
import rat.poison.game.entity.bullets
import rat.poison.game.entity.position
import rat.poison.game.netvars.NetVarOffsets.iCrossHairID
import rat.poison.game.offsets.ClientOffsets
import rat.poison.opened
import rat.poison.robot
import rat.poison.scripts.aim.boneTrig
import rat.poison.scripts.aim.canShoot
import rat.poison.scripts.aim.inMyTeam
import rat.poison.strToBool
import rat.poison.ui.mainTabbedPane
import rat.poison.utils.every
import rat.poison.utils.extensions.uint
import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.event.InputEvent.BUTTON1_DOWN_MASK

private var callingInShot = false
private var inShot = false

fun boneTrigger() = every(10) {
    if (DANGER_ZONE || me.dead()) {
        return@every
    }

    boneTrig = false

    if (curSettings["ENABLE_TRIGGER"].strToBool()) {
        val wep = me.weapon()

        var bFOV = 0
        var bDELAY = 0
        var bINCROSS = false
        var bINFOV = false
        var bAIMBOT = false

        //Change to one val, why have 5?
        var bTrigPistols = false
        var bTrigRifles = false
        var bTrigShotguns = false
        var bTrigSnipers = false
        var bTrigSmgs = false

        when {
            wep.pistol -> { bFOV = curSettings["PISTOL_TRIGGER_FOV"].toInt(); bDELAY = curSettings["PISTOL_TRIGGER_SHOT_DELAY"].toInt(); bINCROSS = curSettings["PISTOL_TRIGGER_INCROSS"].strToBool(); bINFOV = curSettings["PISTOL_TRIGGER_INFOV"].strToBool(); bAIMBOT = curSettings["PISTOL_TRIGGER_AIMBOT"].strToBool(); if (curSettings["PISTOL_TRIGGER"].strToBool()) bTrigPistols = true; }
            wep.rifle -> { bFOV = curSettings["RIFLE_TRIGGER_FOV"].toInt(); bDELAY = curSettings["RIFLE_TRIGGER_SHOT_DELAY"].toInt(); bINCROSS = curSettings["RIFLE_TRIGGER_INCROSS"].strToBool(); bINFOV = curSettings["RIFLE_TRIGGER_INFOV"].strToBool(); bAIMBOT = curSettings["RIFLE_TRIGGER_AIMBOT"].strToBool(); if (curSettings["RIFLE_TRIGGER"].strToBool()) bTrigRifles = true; }
            wep.shotgun -> { bFOV = curSettings["SHOTGUN_TRIGGER_FOV"].toInt(); bDELAY = curSettings["SHOTGUN_TRIGGER_SHOT_DELAY"].toInt(); bINCROSS = curSettings["SHOTGUN_TRIGGER_INCROSS"].strToBool(); bINFOV = curSettings["SHOTGUN_TRIGGER_INFOV"].strToBool(); bAIMBOT = curSettings["SHOTGUN_TRIGGER_AIMBOT"].strToBool(); if (curSettings["SHOTGUN_TRIGGER"].strToBool()) bTrigShotguns = true; }
            wep.sniper -> { bFOV = curSettings["SNIPER_TRIGGER_FOV"].toInt(); bDELAY = curSettings["SNIPER_TRIGGER_SHOT_DELAY"].toInt(); bINCROSS = curSettings["SNIPER_TRIGGER_INCROSS"].strToBool(); bINFOV = curSettings["SNIPER_TRIGGER_INFOV"].strToBool(); bAIMBOT = curSettings["SNIPER_TRIGGER_AIMBOT"].strToBool(); if (curSettings["SNIPER_TRIGGER"].strToBool() && ((me.isScoped() && curSettings["ENABLE_SCOPED_ONLY"].strToBool()) || (!curSettings["ENABLE_SCOPED_ONLY"].strToBool()))) bTrigSnipers = true; }
            wep.smg -> { bFOV = curSettings["SMG_TRIGGER_FOV"].toInt(); bDELAY = curSettings["SMG_TRIGGER_SHOT_DELAY"].toInt(); bINCROSS = curSettings["SMG_TRIGGER_INCROSS"].strToBool(); bINFOV = curSettings["SMG_TRIGGER_INFOV"].strToBool(); bAIMBOT = curSettings["SMG_TRIGGER_AIMBOT"].strToBool(); if (curSettings["SMG_TRIGGER"].strToBool()) bTrigSmgs = true; }
        }

        if (bTrigPistols || bTrigRifles || bTrigShotguns || bTrigSnipers || bTrigSmgs) {
            val wepEnt = me.weaponEntity()
            if (!me.weapon().knife && wepEnt.bullets() > 0) {
                if (wep.automatic || (!wep.automatic && wepEnt.canFire())) {
                    if (keyReleased(AIM_KEY)) {
                        if (bINCROSS) {
                            val inCross = csgoEXE.uint(me + iCrossHairID)
                            if (inCross > 0) {
                                val ent = CSGO.clientDLL.uint(ClientOffsets.dwEntityList + (inCross * 0x10) - 0x10)
                                if (!ent.inMyTeam() && !ent.isProtected() && !ent.dead()) {
                                    if ((curSettings["TRIGGER_ENABLE_KEY"].strToBool() && keyPressed(curSettings["TRIGGER_KEY"].toInt())) || !curSettings["TRIGGER_ENABLE_KEY"].strToBool()) {
                                        bTrigShoot(bDELAY, bAIMBOT)
                                        return@every
                                    }
                                }
                            }
                        }

                        if (bINFOV) {
                            val currentAngle = clientState.angle()
                            val position = me.position()
                            val target = findTarget(position, currentAngle, false, bFOV, -2)
                            if (target >= 0) {
                                if (!target.dead() && !target.isProtected()) {
                                    if ((curSettings["TRIGGER_ENABLE_KEY"].strToBool() && keyPressed(curSettings["TRIGGER_KEY"].toInt())) || !curSettings["TRIGGER_ENABLE_KEY"].strToBool()) {
                                        bTrigShoot(bDELAY, bAIMBOT)
                                        return@every
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        callingInShot = false
        inShot = false
    }
}

//Initial shot delay
fun bTrigShoot(delay: Int, aimbot: Boolean = false) {
    if (!callingInShot) {
        callingInShot = true
        Thread(Runnable {
            Thread.sleep(delay.toLong())
            inShot = true
        }).start()
    }

    if (inShot) {
        //Switch me.weapon() categ -> doesn't work
        robot.mousePress(BUTTON1_DOWN_MASK)
        robot.mouseRelease(BUTTON1_DOWN_MASK)

        boneTrig = aimbot
    }
}