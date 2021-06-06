package rat.poison.scripts.userCmd

import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.game.entity.*
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets
import rat.poison.game.offsets.ClientOffsets
import rat.poison.scripts.aim.*
import rat.poison.scripts.bestBacktrackTarget
import rat.poison.settings.*
import rat.poison.utils.Structs.UserCMD
import rat.poison.utils.common.Vector
import rat.poison.utils.common.inGame
import rat.poison.utils.common.keyPressed
import rat.poison.utils.extensions.uint
import rat.poison.utils.keybindEval

private fun reset() {
    trigEnt = 0L
    triggerShots = 0
    trigQueuedShotTime = -1F
}

private var triggerShots = 0
private val meAngle = Vector()
private val mePosition = Vector()

fun ucmdTrigger(userCMD: UserCMD?): Boolean {
    val initDelay = TRIGGER_INIT_SHOT_DELAY
    val shotDelay = TRIGGER_PER_SHOT_DELAY
    val bFOV = TRIGGER_FOV
    val bINFOV = TRIGGER_USE_FOV
    val bINCROSS = TRIGGER_USE_INCROSS
    //val bAIMBOT = TRIGGER_USE_AIMBOT
    val bBACKTRACK = TRIGGER_USE_BACKTRACK

    ////Currently waiting for queued shot
    if (trigQueuedShotTime > 0) {
        keybindEval("TRIGGER_KEY")
        return false
    }

    ////PRECHECK
    if (DANGER_ZONE || meDead || !inGame || MENUTOG || !meCurWep.gun || !curSettings.bool["ENABLE_TRIGGER"] || !haveAimSettings) {
        reset()
        keybindEval("TRIGGER_KEY")
        return false
    }

    //Trigger key check
    if (curSettings.bool["TRIGGER_ENABLE_KEY"] && !keybindEval("TRIGGER_KEY")) {
        reset()
        return false
    }

    if (TRIGGER_BOT) { //If trigger is enabled for current weapon
        //Scope check
        if (meCurWep.sniper) { //If we are holding a sniper
            if ((ENABLE_SCOPED_ONLY && !curWepOverride) || (curWepOverride && curWepSettings.tScopedOnly)) { //Scoped only check
                if (!me.isScoped()) {
                    reset()
                    return false
                }
            }
        }

        val useDelay = if (triggerShots > 0) { shotDelay } else { initDelay }

        ////PRECHECK
        if (meCurWepEnt.bullets() <= 0 || keyPressed(AIM_KEY)) { //Can shoot check???
            triggerShots = 0
            reset()
            return false
        }

        ////INCROSS
        if (bINCROSS) {
            val iC = getIncross(me)
            if (iC > 0) {
                //Shoot
                trigQueueShot(useDelay)
                trigEnt = iC
                return true
            }
        }

        ////CHECK INFOV
        var canFOV = false
        var fovTarget = 0L
        if (bINFOV) { //If we should check in fov
            val currentAngle = clientState.angle(meAngle)
            val position = me.position(mePosition)
            val target = findTarget(position, currentAngle, false, bFOV)
            if (target > 0L) {
                if (target.canShoot()) {
                    canFOV = true
                    fovTarget = target
                }
            }
        }

        //BACKTRACK
        if (bBACKTRACK) { //If we should check backtrack
            if (bestBacktrackTarget.canShoot()) {
                //Shoot
                trigQueueShot(useDelay)
                trigEnt = bestBacktrackTarget
                return true
            }
        }

        //INFOV
        if (canFOV) {
            trigQueueShot(useDelay)
            trigEnt = fovTarget
            return true
        }

        //If not in cross, not in backtrack, and not in fov
        reset()
    }

    return false
}

private fun trigQueueShot(delay: Int) {
    trigQueuedShotTime = curTime + delay/1000F

    triggerShots++
}

private fun getIncross(ent: Entity): Entity {
    val inCross = CSGO.csgoEXE.uint(ent + NetVarOffsets.iCrossHairID)

    if (inCross > 0) {
        val entity = CSGO.clientDLL.uint(ClientOffsets.dwEntityList + (inCross * 0x10) - 0x10)
        if (!entity.inMyTeam() && !entity.isProtected() && !entity.dead()) {
            return entity
        }
    }

    return 0L
}