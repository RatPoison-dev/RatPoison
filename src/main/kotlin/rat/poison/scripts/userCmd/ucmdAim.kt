package rat.poison.scripts.userCmd

import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.scripts.aim.*
import rat.poison.settings.*
import rat.poison.utils.common.Vector
import rat.poison.utils.common.finalize
import rat.poison.utils.common.keyPressed
import rat.poison.utils.keybindEval
import rat.poison.utils.randInt
import rat.poison.utils.writeAim

private val meAng = Vector()
private val mePos = Vector()
private val boneVec2 = Vector()

fun ucmdAim(silent: Boolean = false, trigger: Boolean = false, trigEnt: Long = 0L): Boolean {
    if (!curSettings.bool["ENABLE_AIM"]) return false

    if (!canSetCmdAngles) return false

    if (!trigger) {
        if (aimTargetSwapTime > 0) {
            if (curTime >= aimTargetSwapTime) {
                aimTargetSwapTime = -1F
            } else {
                return false
            }
        }
    }

    val canFire = meCurWepEnt.canFire()
    if (meCurWep.grenade || meCurWep.knife || meCurWep.miscEnt || meCurWep == Weapons.ZEUS_X27 || meCurWep.bomb || meCurWep == Weapons.NONE) { //Invalid for aimbot
        reset()
        return false
    }

    //TODO                                 didShoot &&
    if (AIM_ONLY_ON_SHOT && (!canFire || (!meCurWep.automatic && !AUTOMATIC_WEAPONS))) { //Onshot
        reset(false)
        return false
    }

    if (meCurWep.sniper && !me.isScoped() && ENABLE_SCOPED_ONLY) { //Scoped only
        reset()
        return false
    }

    val aim = curSettings.bool["ACTIVATE_FROM_AIM_KEY"] && keyPressed(AIM_KEY)
    val pressedForceAimKey = keybindEval("FORCE_AIM_KEY")
    val pressedForceAimBoneKey = keybindEval("FORCE_AIM_BONE_KEY")
    val haveAmmo = meCurWepEnt.bullets() > 0

    val pressed = ((aim || trigger) && !MENUTOG && haveAmmo) || pressedForceAimKey

    if (!pressed) {
        reset()
        return false
    }

    if (meCurWep.rifle || meCurWep.smg) {
        if (me.shotsFired() < AIM_AFTER_SHOTS) {
            reset()
            return false
        }
    }

    var currentTarget = target

    val currentAngle = clientState.angle(meAng)
    val position = me.position(mePos)
    val shouldVisCheck = !(pressedForceAimKey && curSettings.bool["FORCE_AIM_THROUGH_WALLS"])

    var aB = AIM_BONE

    if (pressedForceAimBoneKey) {
        aB = FORCE_AIM_BONE
    }

    val findTargetResList = aimFindTarget(position, currentAngle, aim, BONE = aB, visCheck = shouldVisCheck)
    val bestTarget = findTargetResList.player //Try to find new target
    val bestBone = findTargetResList.bone

    if (currentTarget <= 0) { //If target is invalid from last run
        currentTarget = bestTarget //Try to find new target

        if (currentTarget <= 0) { //End if we don't, can't loop because of thread blocking
            reset()
            return false
        }
        target = currentTarget
    }
    destBone = bestBone

    //Set destination bone for calculating aim

    if (bestTarget <= 0 && !curSettings.bool["HOLD_AIM"] || bestTarget.dead()) {
        reset()
        return false
    }

    var perfect = false
    if (canPerfect) {
        if (randInt(101) <= PERFECT_AIM_CHANCE) {
            perfect = true
        }
    }

    if (!trigger) {
        ////NORMAL AIM

        val swapTarget = (bestTarget > 0 && currentTarget != bestTarget) && !curSettings.bool["HOLD_AIM"] && (meCurWep.automatic || AUTOMATIC_WEAPONS)

        if (swapTarget) {
            reset()
            return false
        } else if (!currentTarget.canShoot(shouldVisCheck)) {
            aimTargetSwapTime = curTime + curSettings.int["AIM_TARGET_SWAP_DELAY"] / 1000F
            reset()
            return false
        } else {
            val bonePosition = currentTarget.bones(destBone, boneVec2)

            val destinationAngle = realCalcAngle(me, bonePosition)

            if (silent) {
                silentHaveTarget = true
                cmdSetAngles(destinationAngle)
            } else {
                silentHaveTarget = false

                if (!perfect) {
                    destinationAngle.finalize(currentAngle, (1F - AIM_SMOOTHNESS / 100.1F))

                    writeAim(destinationAngle, currentAngle, AIM_SMOOTHNESS)
                } else {
                    writeAim(destinationAngle, currentAngle, 1)
                }
            }

            return true
        }
    } else {
        val bonePosition = currentTarget.bones(destBone, boneVec2)

        val destinationAngle = realCalcAngle(me, bonePosition)

        if (destinationAngle.isZero()) {
            println("trigger destination is zero brosephhhh")
        }

        if (currentAngle.isZero()) {
            println("trigger destination current angleio is zero bresphsphsph")
        }

        if (silent) {
            silentHaveTarget = true
            cmdSetAngles(destinationAngle)
        } else {
            silentHaveTarget = false

            if (!perfect) {
                //destinationAngle.finalize(currentAngle, (1.001F - AIM_SMOOTHNESS / 100F))

                writeAim(destinationAngle, currentAngle, 1)
            } else {
                writeAim(destinationAngle, currentAngle, 1)
            }
        }

        return true
    }
}