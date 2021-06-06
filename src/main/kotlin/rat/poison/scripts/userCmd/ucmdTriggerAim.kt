package rat.poison.scripts.userCmd

import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.scripts.aim.*
import rat.poison.settings.*
import rat.poison.utils.common.Vector
import rat.poison.utils.common.finalize
import rat.poison.utils.generalUtil.has
import rat.poison.utils.keybindEval
import rat.poison.utils.randInt
import rat.poison.utils.writeAim

private val meAng = Vector()
private val boneVec2 = Vector()
private var destBone = -1

fun ucmdTriggerAim(silent: Boolean, ent: Long): Boolean {
    if (!curSettings.bool["ENABLE_AIM"]) {
        keybindEval("FORCE_AIM_KEY")
        return false
    }

    if (!canSetCmdAngles) {
        keybindEval("FORCE_AIM_KEY")
        return false
    }

    val canFire = meCurWepEnt.canFire()
    if (meCurWep.grenade || meCurWep.knife || meCurWep.miscEnt || meCurWep == Weapons.ZEUS_X27 || meCurWep.bomb || meCurWep == Weapons.NONE) { //Invalid for aimbot
        keybindEval("FORCE_AIM_KEY")
        return false
    }

    //TODO                                 didShoot &&
    if (AIM_ONLY_ON_SHOT && (!canFire || (!meCurWep.automatic && !AUTOMATIC_WEAPONS))) { //Onshot
        keybindEval("FORCE_AIM_KEY")
        return false
    }

    if (meCurWep.sniper && !me.isScoped() && ENABLE_SCOPED_ONLY) { //Scoped only
        keybindEval("FORCE_AIM_KEY")
        return false
    }

    if (!ent.canShoot()) {
        keybindEval("FORCE_AIM_KEY")
        return false
    }

    val pressedForceAimKey = keybindEval("FORCE_AIM_KEY")

    if (meCurWep.rifle || meCurWep.smg) {
        if (me.shotsFired() < AIM_AFTER_SHOTS) {
            return false
        }
    }

    val currentAngle = clientState.angle(meAng)

    var aB = AIM_BONE

    if (pressedForceAimKey) {
        aB = FORCE_AIM_BONE
    }

    if (aB.isEmpty()) return false

    val findNearest = aB.has { it == NEAREST_BONE }
    val findRandom = aB.has { 0 > it as Int }

    if (findNearest) {
        val nB = ent.nearestBone()
        if (nB != INVALID_NEAREST_BONE) destBone = nB
    } else if (findRandom) {
        destBone = 5 + randInt(0, 3)
    } else {
        destBone = aB[0]
    }

    var perfect = false
    if (canPerfect) {
        if (randInt(101) <= PERFECT_AIM_CHANCE) {
            perfect = true
        }
    }

    val bonePosition = ent.bones(destBone, boneVec2)

    val destinationAngle = realCalcAngle(me, bonePosition)

    if (silent) {
        cmdSetAngles(destinationAngle)
    } else {
        if (!perfect) {
            destinationAngle.finalize(currentAngle, (1F - AIM_SMOOTHNESS / 100F))

            writeAim(currentAngle, destinationAngle, AIM_SMOOTHNESS)
        } else {
            writeAim(currentAngle, destinationAngle, 1)
        }
    }

    return true
}