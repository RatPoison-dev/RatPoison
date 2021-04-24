package rat.poison.scripts.userCmd

import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.scripts.aim.*
import rat.poison.scripts.cmdSetAngles
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

fun userCmdAim() {
    if (!curSettings.bool["ENABLE_AIM"]) return

    val canFire = meCurWepEnt.canFire()
    if (meCurWep.grenade || meCurWep.knife || meCurWep.miscEnt || meCurWep == Weapons.ZEUS_X27 || meCurWep.bomb || meCurWep == Weapons.NONE) { //Invalid for aimbot
        reset()
        return
    }

    if (AIM_ONLY_ON_SHOT && (!canFire || (didShoot && !meCurWep.automatic && !AUTOMATIC_WEAPONS))) { //Onshot
        reset(false)
        return
    }

    if (meCurWep.sniper && !me.isScoped() && ENABLE_SCOPED_ONLY) { //Scoped only
        reset()
        return
    }

    val aim = curSettings.bool["ACTIVATE_FROM_AIM_KEY"] && keyPressed(AIM_KEY)
    val pressedForceAimKey = keybindEval("FORCE_AIM_KEY")
    val haveAmmo = meCurWepEnt.bullets() > 0

    val pressed = ((aim || boneTrig) && !MENUTOG && haveAmmo) || pressedForceAimKey

    if (!pressed) {
        reset()
        return
    }

    if (meCurWep.rifle || meCurWep.smg) {
        if (me.shotsFired() < AIM_AFTER_SHOTS) {
            reset()
            return
        }
    }

    var currentTarget = target

    val currentAngle = clientState.angle(meAng)
    val position = me.position(mePos)
    val shouldVisCheck = !(pressedForceAimKey && curSettings.bool["FORCE_AIM_THROUGH_WALLS"])

    var aB = AIM_BONE

    if (pressedForceAimKey) {
        aB = FORCE_AIM_BONE
    }

    val findTargetResList = aimFindTarget(position, currentAngle, aim, BONE = aB, visCheck = shouldVisCheck)
    val bestTarget = findTargetResList.player //Try to find new target
    val bestBone = findTargetResList.bone

    if (currentTarget <= 0) { //If target is invalid from last run
        currentTarget = bestTarget //Try to find new target

        if (currentTarget <= 0) { //End if we don't, can't loop because of thread blocking
            reset()
            return
        }
        target = currentTarget
    }
    destBone = bestBone

    //Set destination bone for calculating aim

    if (bestTarget <= 0 && !curSettings.bool["HOLD_AIM"] || bestTarget.dead()) {
        reset()
        return
    }
//
//    var perfect = false
//    if (canPerfect) {
//        if (randInt(100+1) <= PERFECT_AIM_CHANCE) {
//            perfect = true
//        }
//    }

    val swapTarget = (bestTarget > 0 && currentTarget != bestTarget) && !curSettings.bool["HOLD_AIM"] && (meCurWep.automatic || AUTOMATIC_WEAPONS)

    if (swapTarget) {
        reset()
        return
    } else if (!currentTarget.canShoot(shouldVisCheck)) {
        Thread.sleep(curSettings.int["AIM_TARGET_SWAP_DELAY"].toLong()) //tested cool breezy
        reset()
        return
    } else {
        val bonePosition = currentTarget.bones(destBone, boneVec2)

        val destinationAngle = realCalcAngle(me, bonePosition)

        cmdSetAngles(destinationAngle)
    }
}