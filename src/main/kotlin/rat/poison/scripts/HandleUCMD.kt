package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.game.offsets.ClientOffsets
import rat.poison.game.offsets.EngineOffsets
import rat.poison.scripts.aim.*
import rat.poison.settings.*
import rat.poison.utils.Structs.*
import rat.poison.utils.common.*
import rat.poison.utils.extensions.uint
import rat.poison.utils.keybindEval
import rat.poison.utils.randInt
import rat.poison.utils.writeAim
import java.lang.Thread.yield

var lastCMDNumber = 0

private const val inputMemorySize = 253
//private val inputMemory = threadLocalPointer(inputMemorySize)

private val inputMemory = threadLocalPointer(inputMemorySize)

fun handleUCMD() = every(1) {
    val inputMemory = inputMemory.get()



    val lastCommand = CSGO.csgoEXE.int(clientState + EngineOffsets.dwClientState_LastOutgoingCommand)

    if (lastCMDNumber != lastCommand) {
        sendPacket(false)

        val currentCommandNumber = CSGO.csgoEXE.int(clientState + EngineOffsets.dwClientState_LastOutgoingCommand) + 2

        CSGO.csgoEXE.read(CSGO.clientDLL.address + ClientOffsets.dwInput, inputMemory, inputMemorySize)
        val input = memToInput(inputMemory)
        val pUCMD = input.pCommands + (currentCommandNumber % 150) * 0x64

        second@while (true) {
            val curCMDNumber = CSGO.csgoEXE.int(pUCMD + 0x4)
            val chokedCommands = CSGO.csgoEXE.uint(clientState + EngineOffsets.dwClientState_ChokedCommands) - 1

            //Aimbot : Do all the stuff, priority 1
            userCMDAim()

            //Trigger : Do all the stuff, priority 2

            //Backtrack : Do all the stuff, priority 3

            if (curCMDNumber >= currentCommandNumber || chokedCommands >= 4) {
                val oldUserCMDptr = input.pCommands + ((currentCommandNumber-1) % 150) * 0x64
                val verifiedOldUserCMDptr = input.pVerifiedCommands + ((currentCommandNumber-1) % 150) * 0x68

                sendUserCMD(currentCommandNumber, oldUserCMDptr, verifiedOldUserCMDptr)

                sendPacket(true)

                Thread.sleep(10) //pas one down s

                break@second
            }

            yield()
        }
    }
}

private const val oldUserCmdMemorySize = 100
private val oldUserCmdMemory = threadLocalPointer(oldUserCmdMemorySize)
private val oldUserCMD = UserCMD()
var nextCMD = UserCMD()

fun sendUserCMD(commandNumber: Int, oldPtr: Int, oldVerifiedPtr: Int) {
    //println("sending new cmd $commandNumber")

    val oldUserCmdMemory = oldUserCmdMemory.get()
    CSGO.csgoEXE.read(oldPtr, oldUserCmdMemory, oldUserCmdMemorySize)
    val oldUserCMD = memToUserCMD(oldUserCmdMemory, oldUserCMD)

    if (!canSetCmdAngles) { //Queued
        oldUserCMD.vecViewAngles = nextCMD.vecViewAngles
    }

    if (keyPressed(1)) { //TODO kfkfkfkfkfkfkfkfkfkfk fkfkf fk deyubg
        oldUserCMD.iButtons = oldUserCMD.iButtons or 1
    }

    //oldUserCMD.iButtons = nextCMD.iButtons // << 1 =|= IN_ATTACK

    userCMDToMem(oldPtr, oldUserCMD)
    userCMDToMem(oldVerifiedPtr, oldUserCMD)

    canSetCmdAngles = true
    nextCMD.reset()
}

//yuh yuh yah fuck this tho ong fr fr breezy
var canSetCmdAngles = true
fun cmdSetAngles(viewAngles: Vector) {
    if (canSetCmdAngles) {
        nextCMD.vecViewAngles = viewAngles

        canSetCmdAngles = false
    }
}

private val meAng = Vector()
private val mePos = Vector()
private val boneVec2 = Vector()

fun userCMDAim() {
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
    val forceAim = pressedForceAimKey || curSettings.bool["FORCE_AIM_ALWAYS"]
    val haveAmmo = meCurWepEnt.bullets() > 0

    val pressed = ((aim || boneTrig) && !MENUTOG && haveAmmo) || forceAim

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
    val shouldVisCheck = !(forceAim && curSettings.bool["FORCE_AIM_THROUGH_WALLS"])

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

//    var perfect = false
//    if (canPerfect) {
//        if (randInt(100+1) <= PERFECT_AIM_CHANCE) {
//            perfect = true
//        }
//    }

    val swapTarget = (bestTarget > 0 && currentTarget != bestTarget) && !curSettings.bool["HOLD_AIM"] && (meCurWep.automatic || AUTOMATIC_WEAPONS)

    if (swapTarget || !currentTarget.canShoot(shouldVisCheck)) {
        return
    } else {
        val bonePosition = currentTarget.bones(destBone, boneVec2)

        val destinationAngle = realCalcAngle(me, bonePosition)

        cmdSetAngles(destinationAngle)
    }
}






//
//private const val oldUserCmdMemorySize = 100
//private val oldUserCmdMemory = threadLocalPointer(oldUserCmdMemorySize)
//
//private const val newUserCmdMemorySize = 100
//private val newUserCMDMemory = threadLocalPointer(newUserCmdMemorySize)
//
//private val oldUserCMD = UserCMD()
//private val newUserCMD = UserCMD()
//
//fun shootSilent(viewAngles: Vector) {
//    sendPacket(false)
//
//    val iDesiredCmdNumber = CSGO.csgoEXE.int(clientState + EngineOffsets.dwClientState_LastOutgoingCommand) + 2
//
//    val inputMemory = inputMemory.get()
//    CSGO.csgoEXE.read(CSGO.clientDLL.address + ClientOffsets.dwInput, inputMemory, inputMemorySize)
//    val input = memToInput(inputMemory)
//
//    val pOldUCMD = input.pCommands + ((iDesiredCmdNumber - 1) % 150) * 0x64
//    val pVerifiedOldUCMD = input.pVerifiedCommands + ((iDesiredCmdNumber - 1) % 150) * 0x68
//
//    val pUCMD = input.pCommands + (iDesiredCmdNumber % 150) * 0x64
//    while (CSGO.csgoEXE.int(pUCMD + 0x4) < iDesiredCmdNumber) {
//        Thread.yield()
//    }
//
//    //Check invalid?
//    val oldUserCmdMemory = oldUserCmdMemory.get()
//    val newUserCmdMemory = newUserCMDMemory.get()
//
//    CSGO.csgoEXE.read(pOldUCMD, oldUserCmdMemory, oldUserCmdMemorySize)
//    CSGO.csgoEXE.read(pUCMD, newUserCmdMemory, newUserCmdMemorySize)
//
//    val oldUserCMD = memToUserCMD(oldUserCmdMemory, oldUserCMD)
//    //val newUserCMD = memToUserCMD(newUserCmdMemory, newUserCMD)
//
//    oldUserCMD.vecViewAngles = viewAngles
//    oldUserCMD.iButtons = oldUserCMD.iButtons or 1 // << 1 =|= IN_ATTACK
//
//    userCMDToMem(pOldUCMD, oldUserCMD)
//    userCMDToMem(pVerifiedOldUCMD, oldUserCMD)
//
//    sendPacket(true)
//}