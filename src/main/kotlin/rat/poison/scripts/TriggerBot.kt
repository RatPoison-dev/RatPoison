package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import org.jire.arrowhead.keyReleased
import rat.poison.curSettings
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.game.entity.*
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.iCrossHairID
import rat.poison.game.offsets.ClientOffsets
import rat.poison.game.offsets.ClientOffsets.dwForceAttack
import rat.poison.game.offsets.ClientOffsets.dwIndex
import rat.poison.game.realCalcAngle
import rat.poison.scripts.aim.boneTrig
import rat.poison.scripts.aim.findTarget
import rat.poison.scripts.aim.inMyTeam
import rat.poison.settings.AIM_KEY
import rat.poison.settings.DANGER_ZONE
import rat.poison.utils.every
import rat.poison.utils.extensions.uint
import rat.poison.utils.generalUtil.strToBool

var callingInShot = false
var triggerInShot = false

fun boneTrigger() = every(10) {
    if (DANGER_ZONE || me.dead()) {
        callingInShot = false
        return@every
    }

    //FIRST-SHOT or BETWEEN-SHOTS
    val delayType = curSettings["TRIGGER_DELAY_TYPE"]
    if (curSettings["ENABLE_TRIGGER"].strToBool() && ((!callingInShot && delayType == "BETWEEN-SHOTS") || (delayType == "FIRST-SHOT"))) {
        val wep = me.weapon()

        val bFOV: Int; val bDELAY: Int; val bINCROSS: Boolean; val bINFOV: Boolean; val bAIMBOT: Boolean; val bBACKTRACK: Boolean

        var prefix = ""

        when {
            wep.pistol -> { prefix = "PISTOL_" }
            wep.rifle -> { prefix = "RIFLE_" }
            wep.shotgun -> { prefix = "SHOTGUN_" }
            wep.sniper -> { prefix = "SNIPER_" }
            wep.smg -> { prefix = "SMG_" }
        }

        if (wep.gun) { //Not 100% this applies to every 'gun'
            if (!curSettings[prefix + "TRIGGER"].strToBool()) return@every

            bFOV = curSettings[prefix + "TRIGGER_FOV"].toInt()
            bDELAY = curSettings[prefix + "TRIGGER_SHOT_DELAY"].toInt()
            bINCROSS = curSettings[prefix + "TRIGGER_INCROSS"].strToBool()
            bINFOV = curSettings[prefix + "TRIGGER_INFOV"].strToBool()
            bAIMBOT = curSettings[prefix + "TRIGGER_AIMBOT"].strToBool()
            bBACKTRACK = curSettings[prefix + "TRIGGER_BACKTRACK"].strToBool()

            if (wep.sniper) { //Scope check
                if (!(curSettings["SNIPER_TRIGGER"].strToBool() && ((me.isScoped() && curSettings["ENABLE_SCOPED_ONLY"].strToBool()) || (!curSettings["ENABLE_SCOPED_ONLY"].strToBool())))) {
                    return@every
                }
            }

            val wepEnt = me.weaponEntity()
            if (!me.weapon().knife && wepEnt.bullets() > 0) {
                if (wep.automatic || (!wep.automatic && wepEnt.canFire())) {
                    if (keyReleased(AIM_KEY)) { //If we aren't already shooting
                        if ((curSettings["TRIGGER_ENABLE_KEY"].strToBool() && keyPressed(curSettings["TRIGGER_KEY"].toInt())) || !curSettings["TRIGGER_ENABLE_KEY"].strToBool()) {
                            if (bINCROSS) { //If InCross setting is true
                                val inCross = csgoEXE.uint(me + iCrossHairID)
                                if (inCross > 0) {
                                    val ent = clientDLL.uint(ClientOffsets.dwEntityList + (inCross * 0x10) - 0x10)
                                    if (!ent.inMyTeam() && !ent.isProtected() && !ent.dead()) {
                                        bTrigShoot(bDELAY, bAIMBOT, false, delayType)
                                        return@every
                                    }
                                }
                            }

                            if (bINFOV) { //If in FOV setting is true
                                val currentAngle = clientState.angle()
                                val position = me.position()
                                val target = findTarget(position, currentAngle, false, bFOV, -2)
                                if (target > 0) {
                                    if (!target.dead() && !target.isProtected()) {
                                        bTrigShoot(bDELAY, bAIMBOT, false, delayType)
                                        return@every
                                    }
                                }
                            }

                            if (bBACKTRACK) { //If backtrack setting is true
                                if (bestBacktrackTarget > 0 && bBACKTRACK) {
                                    if (!bestBacktrackTarget.dead() && !bestBacktrackTarget.isProtected()) {
                                        println("attemptin a backtrackin")
                                        bTrigShoot(bDELAY, bAIMBOT, true, delayType)
                                        return@every
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //callingInShot = false
        triggerInShot = false
    } else {
        boneTrig = false
        triggerInShot = false
    }
}

//Initial shot delay
fun bTrigShoot(delay: Int, aimbot: Boolean = false, backtrack: Boolean = false, delayType: String = "") {
    if (!callingInShot) {
        println("calling in shot true bro")
        callingInShot = true
        if (delay > 0) {
            Thread(Runnable {
                Thread.sleep(delay.toLong())
                triggerInShot = true
                triggerShoot(aimbot, backtrack)
            }).start()
        } else {
            triggerInShot = true
            triggerShoot(aimbot, backtrack)
        }
    } else if (delayType == "FIRST-SHOT") {
        triggerInShot = true
        triggerShoot(aimbot, backtrack)
    }
}

private fun triggerShoot(aimbot: Boolean = false, backtrack: Boolean = false) {
    boneTrig = aimbot

    var didBacktrack = false

    if (backtrack) {
        didBacktrack = attemptBacktrack()
    }

    if (!didBacktrack) {
        clientDLL[dwForceAttack] = 6
        Thread.sleep(10)
    } else {
        Thread.sleep(10)
    }

    boneTrig = false
    callingInShot = false
}
