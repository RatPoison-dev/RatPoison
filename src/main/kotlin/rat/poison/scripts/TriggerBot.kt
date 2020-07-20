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
import rat.poison.scripts.aim.boneTrig
import rat.poison.scripts.aim.findTarget
import rat.poison.scripts.aim.inMyTeam
import rat.poison.settings.AIM_KEY
import rat.poison.settings.DANGER_ZONE
import rat.poison.settings.MENUTOG
import rat.poison.utils.every
import rat.poison.utils.extensions.uint
import rat.poison.utils.generalUtil.strToBool

var callingInShot = false
var triggerInShot = false
private var inDelay = false

fun boneTrigger() = every(10) {
    if (DANGER_ZONE || me.dead() || MENUTOG) {
        callingInShot = false
        return@every
    }

    val wep = me.weapon()

    if (!wep.gun) return@every

    var prefix = ""

    when {
        wep.pistol -> { prefix = "PISTOL_" }
        wep.rifle -> { prefix = "RIFLE_" }
        wep.shotgun -> { prefix = "SHOTGUN_" }
        wep.sniper -> { prefix = "SNIPER_" }
        wep.smg -> { prefix = "SMG_" }
    }

    //FIRST-SHOT & BETWEEN-SHOTS
    val initDelay = curSettings[prefix + "TRIGGER_INIT_SHOT_DELAY"].toInt()
    val shotDelay = curSettings[prefix + "TRIGGER_PER_SHOT_DELAY"].toInt()
    if (curSettings["ENABLE_TRIGGER"].strToBool() && !inDelay) {
        val bFOV: Float; val bINCROSS: Boolean; val bINFOV: Boolean; val bAIMBOT: Boolean; val bBACKTRACK: Boolean

        if (wep.gun) { //Not 100% this applies to every 'gun'
            if (!curSettings[prefix + "TRIGGER"].strToBool()) return@every

            bFOV = curSettings[prefix + "TRIGGER_FOV"].toFloat()
            bINCROSS = curSettings[prefix + "TRIGGER_INCROSS"].strToBool()
            bINFOV = curSettings[prefix + "TRIGGER_INFOV"].strToBool()
            bAIMBOT = curSettings[prefix + "TRIGGER_AIMBOT"].strToBool()
            bBACKTRACK = curSettings[prefix + "TRIGGER_BACKTRACK"].strToBool() && curSettings["ENABLE_BACKTRACK"].strToBool()

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
                                        bTrigShoot(initDelay, shotDelay, bAIMBOT, false)
                                        return@every
                                    }
                                }
                            }

                            var canFOV = false
                            if (bINFOV) { //If in FOV setting is true
                                val currentAngle = clientState.angle()
                                val position = me.position()
                                val target = findTarget(position, currentAngle, false, bFOV, -2)
                                if (target > 0) {
                                    if (!target.dead() && !target.isProtected()) {
                                        canFOV = true
                                    }
                                }
                            }

                            if (bBACKTRACK) { //If backtrack setting is true
                                if (bestBacktrackTarget > 0) {
                                    if (!bestBacktrackTarget.dead() && !bestBacktrackTarget.isProtected()) {
                                        bTrigShoot(initDelay, shotDelay, false, true, canFOV)
                                        return@every
                                    }
                                }
                            }

                            if (canFOV) {
                                bTrigShoot(initDelay, shotDelay, bAIMBOT, false)
                                return@every
                            }

                            //If not called finish
                            //This prevents speed ups when losing target, canFire, etc...
                            triggerInShot = false
                            callingInShot = false
                        }
                    }
                }
            }
        }
    } else {
        boneTrig = false
    }
}

//Initial shot delay
fun bTrigShoot(initDelay: Int, shotDelay: Int, aimbot: Boolean = false, backtrack: Boolean = false, backtrackFallback: Boolean = false) {
    if (!inDelay) {
        if (!callingInShot) {
            callingInShot = true
            if (initDelay > 0) {
                Thread(Runnable {
                    inDelay = true
                    Thread.sleep(initDelay.toLong())
                    inDelay = false
                    triggerInShot = true
                    triggerShoot(aimbot, backtrack, backtrackFallback)
                }).start()
            } else {
                triggerInShot = true
                triggerShoot(aimbot, backtrack, backtrackFallback)
            }
        } else if (!triggerInShot) { //else if trigger in shot
            triggerInShot = true

            if (shotDelay > 0) {
                Thread(Runnable {
                    inDelay = true
                    Thread.sleep(shotDelay.toLong())
                    inDelay = false
                    triggerShoot(aimbot, backtrack, backtrackFallback)
                }).start()
            } else {
                triggerShoot(aimbot, backtrack, backtrackFallback)
            }
        }
    }
}

private fun triggerShoot(aimbot: Boolean = false, backtrack: Boolean = false, backtrackFallback: Boolean = false) {
    boneTrig = aimbot
    var didBacktrack = false

    //Can combine statements? sippin dum dum juice rn
    if (backtrack) {
        didBacktrack = attemptBacktrack()
    } else {
        clientDLL[dwForceAttack] = 6 //HandleFireKey.kt
    }

    if (!didBacktrack && backtrackFallback) {
        clientDLL[dwForceAttack] = 6 //HandleFireKey.kt
    }

    //Fuck a beat i was tryna beat my meat
    //thread sleep here 10

    boneTrig = false
    triggerInShot = false
}
