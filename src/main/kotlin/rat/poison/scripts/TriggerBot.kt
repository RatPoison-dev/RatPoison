package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.*
import rat.poison.game.netvars.NetVarOffsets.iCrossHairID
import rat.poison.game.offsets.ClientOffsets
import rat.poison.game.offsets.ClientOffsets.dwForceAttack
import rat.poison.scripts.aim.*
import rat.poison.settings.AIM_KEY
import rat.poison.settings.DANGER_ZONE
import rat.poison.settings.MENUTOG
import rat.poison.utils.Vector
import rat.poison.utils.every
import rat.poison.utils.extensions.uint
import rat.poison.utils.generalUtil.safeToInt
import rat.poison.utils.inGame
import rat.poison.utils.keyPressed

var inTrigger = false
private var triggerShots = 0
private val meAngle = Vector()
private val mePosition = Vector()
fun triggerBot() = every(5, inGameCheck = true) {
    //Don't run if not needed
    if (DANGER_ZONE || meDead || !inGame || MENUTOG || !meCurWep.gun || !curSettings.bool["ENABLE_TRIGGER"] || !haveAimSettings) { //Precheck
        inTrigger = false
        triggerShots = 0
        return@every
    }

    inTrigger = false //go and do the 2 step

    val initDelay = curSettings.int["TRIGGER_INIT_SHOT_DELAY"]
    val shotDelay = curSettings.int["TRIGGER_PER_SHOT_DELAY"]
    val bFOV = curSettings.float["TRIGGER_FOV"]
    val bINFOV = curSettings.bool["TRIGGER_USE_FOV"]
    val bINCROSS = curSettings.bool["TRIGGER_USE_INCROSS"]
    val bAIMBOT = curSettings.bool["TRIGGER_USE_AIMBOT"]
    val bBACKTRACK = curSettings.bool["TRIGGER_USE_BACKTRACK"]

    if (curSettings.bool["TRIGGER_BOT"]) { //If trigger is enabled for current weapon
        //Scope check
        if (meCurWep.sniper) { //If we are holding a sniper
            if ((curSettings.bool["ENABLE_SCOPED_ONLY"] && !curWepOverride) || (curWepOverride && curWepSettings.tScopedOnly)) { //Scoped only check
                if (!me.isScoped()) {
                    //Reset
                    inTrigger = false
                    triggerShots = 0
                    return@every
                }
            }
        }

        val useDelay = if (triggerShots > 0) { shotDelay } else { initDelay }

        //Trigger precheck
        if (meCurWepEnt.bullets() <= 0 || keyPressed(AIM_KEY)) { //Can shoot check???
            inTrigger = false
            triggerShots = 0
            return@every
        }

        //Trigger key check
        if (curSettings.bool["TRIGGER_ENABLE_KEY"] && !keyPressed(curSettings["TRIGGER_KEY"].safeToInt("Trig key"))) {
            inTrigger = false
            triggerShots = 0
            return@every
        }

        if (bINCROSS) { //If we should check in cross
            val iC = getIncross(me)
            if (iC > 0) {
                //Shoot
                trigQueueShot(useDelay, bAIMBOT, backtrack = false, backtrackFallback = false)
                return@every
            }
            else {
                boneTrig = bAIMBOT
            }
        }

        var canFOV = false
        if (bINFOV) { //If we should check in fov
            val currentAngle = clientState.angle(meAngle)
            val position = me.position(mePosition)
            val target = findTarget(position, currentAngle, false, bFOV, "-2").player
            if (target > 0) {
                if (!target.dead() && !target.isProtected()) {
                    canFOV = true
                }
            }
        }

        if (bBACKTRACK) { //If we should check backtrack
            if (bestBacktrackTarget > 0) {
                if (bestBacktrackTarget.canShoot()) {
                    //Shoot
                    trigQueueShot(useDelay, bAIMBOT, bBACKTRACK, canFOV)
                    return@every
                }
            }
        }

        if (canFOV) { //Cant backtrack
            trigQueueShot(useDelay, bAIMBOT, backtrack = false, backtrackFallback = false)
            return@every
        }

        //If not in cross, not in backtrack, and not in fov
        //Reset
        inTrigger = false
        triggerShots = 0
    }
}

private fun trigQueueShot(delay: Int, aimbot: Boolean = false, backtrack: Boolean = false, backtrackFallback: Boolean = false) {
    inTrigger = true

    if (delay > 0) {
        Thread.sleep(delay.toLong())
    }

    //Trigger key check
    if (curSettings.bool["TRIGGER_ENABLE_KEY"] && !keyPressed(curSettings.int["TRIGGER_KEY"])) {
        inTrigger = false
        triggerShots = 0
        return
    }

    triggerShots++
    triggerShoot(aimbot, backtrack, backtrackFallback)
}

private fun triggerShoot(aimbot: Boolean = false, backtrack: Boolean = false, backtrackFallback: Boolean = false) {
    var didBacktrack = false

    if (backtrack) {
        didBacktrack = attemptBacktrack()
        boneTrig = didBacktrack
    }

    if (!backtrack || (backtrack && !didBacktrack && backtrackFallback)) {
        boneTrig = aimbot
        clientDLL[dwForceAttack] = 6 //HandleFireKey.kt
    }

    Thread.sleep(10)
    //boneTrig = false
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
