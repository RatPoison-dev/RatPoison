package rat.poison.scripts

import org.jire.arrowhead.keyPressed
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
import rat.poison.safeToInt
import rat.poison.scripts.aim.*
import rat.poison.settings.AIM_KEY
import rat.poison.settings.DANGER_ZONE
import rat.poison.settings.MENUTOG
import rat.poison.utils.every
import rat.poison.utils.extensions.uint
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.notInGame


var inTrigger = false
private var triggerShots = 0

fun triggerBot() = every(5) {
    //Don't run if not needed
    if (DANGER_ZONE || me.dead() || notInGame || MENUTOG || !me.weapon().gun || !curSettings["ENABLE_TRIGGER"].strToBool()) { //Precheck
        inTrigger = false
        triggerShots = 0
        return@every
    }

    inTrigger = false //go and do the 2 step

    val initDelay = if (curWepOverride) curWepSettings.tBTrigInitDelay else curSettings[curWepCategory + "_TRIGGER_INIT_SHOT_DELAY"].safeToInt("Trig init shot $curWepCategory found ${curSettings[curWepCategory + "_TRIGGER_INIT_SHOT_DELAY"]} invalid")
    val shotDelay = if (curWepOverride) curWepSettings.tBTrigPerShotDelay else curSettings[curWepCategory + "_TRIGGER_PER_SHOT_DELAY"].safeToInt("Trig per shot $curWepCategory found ${curSettings[curWepCategory + "_TRIGGER_PER_SHOT_DELAY"]} invalid")
    val bFOV = curSettings["TRIGGER_FOV"].toFloat()
    val bINFOV = curSettings["TRIGGER_USE_FOV"].strToBool()
    val bINCROSS = curSettings["TRIGGER_USE_INCROSS"].strToBool()
    val bAIMBOT = curSettings["TRIGGER_USE_AIMBOT"].strToBool()
    val bBACKTRACK = curSettings["TRIGGER_USE_BACKTRACK"].strToBool()

    if (curSettings[curWepCategory + "_TRIGGER"].strToBool() || (curWepOverride && curWepSettings.tBoneTrig)) { //If trigger is enabled for current weapon
        //Scope check
        if (curWepCategory == "SNIPER") { //If we are holding a sniper
            if ((curSettings["ENABLE_SCOPED_ONLY"].strToBool() && !curWepOverride) || (curWepOverride && curWepSettings.tScopedOnly)) { //Scoped only check
                if (!me.isScoped()) {
                    //Reset
                    inTrigger = false
                    triggerShots = 0
                    return@every
                }
            }
        }

        val useDelay = if (triggerShots > 0) { initDelay } else { shotDelay }

        val wepEnt = me.weaponEntity()
        //Trigger precheck
        if (wepEnt.bullets() <= 0 || keyPressed(AIM_KEY) || !wepEnt.canFire()) { //Can shoot check???
            inTrigger = false
            triggerShots = 0
            return@every
        }

        //Trigger key check
        if (curSettings["TRIGGER_ENABLE_KEY"].strToBool() && !keyPressed(curSettings["TRIGGER_KEY"].safeToInt("Trig key"))) {
            inTrigger = false
            triggerShots = 0
            return@every
        }

        if (bINCROSS) { //If we should check in cross
            val iC = getIncross(me)
            if (iC > 0) {
                //Shoot
                trigShoot(useDelay, bAIMBOT, backtrack = false, backtrackFallback = false)
                return@every
            }
        }

        var canFOV = false
        if (bINFOV) { //If we should check in fov
            val currentAngle = clientState.angle()
            val position = me.position()
            val target = findTarget(position, currentAngle, false, bFOV, -2)
            if (target > 0) {
                if (!target.dead() && !target.isProtected()) {
                    canFOV = true
                }
            }
        }

        if (bBACKTRACK) { //If we should check backtrack
            if (bestBacktrackTarget > 0) {
                if (!bestBacktrackTarget.dead() && !bestBacktrackTarget.isProtected()) {
                    //Shoot
                    trigShoot(useDelay, bAIMBOT, bBACKTRACK, canFOV)
                    return@every
                }
            }
        }

        if (canFOV) { //Cant backtrack
            trigShoot(useDelay, bAIMBOT, backtrack = false, backtrackFallback = false)
        }

        //If not in cross, not in backtrack, and not in fov
        //Reset
        triggerShots = 0
    }
}

private fun trigShoot(delay: Int, aimbot: Boolean = false, backtrack: Boolean = false, backtrackFallback: Boolean = false) {
    inTrigger = true
    triggerShots++

    if (delay > 0) {
        Thread.sleep(delay.toLong())
    }

    triggerShoot(aimbot, backtrack, backtrackFallback)
}

private fun triggerShoot(aimbot: Boolean = false, backtrack: Boolean = false, backtrackFallback: Boolean = false) {
    boneTrig = aimbot// && !backtrack
    var didBacktrack = false

    if (backtrack) {
        didBacktrack = attemptBacktrack()
        boneTrig = didBacktrack
    }

    if (!backtrack || (backtrack && !didBacktrack && backtrackFallback)) {
        boneTrig = aimbot
        clientDLL[dwForceAttack] = 6 //HandleFireKey.kt
    }
}

private fun getIncross(ent: Entity): Entity {
    val inCross = csgoEXE.uint(ent + iCrossHairID)

    if (inCross > 0) {
        val entity = clientDLL.uint(ClientOffsets.dwEntityList + (inCross * 0x10) - 0x10)
        if (!entity.inMyTeam() && !entity.isProtected() && !entity.dead()) {
            return entity
        }
    }

    return 0L
}
