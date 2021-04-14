package rat.poison.scripts

import rat.poison.game.CSGO
import rat.poison.game.clientState
import rat.poison.game.offsets.ClientOffsets
import rat.poison.game.offsets.EngineOffsets
import rat.poison.utils.Structs.*
import rat.poison.utils.common.Vector
import rat.poison.utils.common.every
import rat.poison.utils.common.threadLocalPointer

var lastCMDNumber = 0

fun handleUCMD() = every(1) {
//    val lastSequenceNumber = CSGO.csgoEXE.int(clientState + EngineOffsets.dwClientState_LastOutgoingCommand)
//    val wantedSequenceNumber = lastSequenceNumber + 2
//
//    val inputMemory = inputMemory.get()
//    CSGO.csgoEXE.read(CSGO.clientDLL.address + ClientOffsets.dwInput, inputMemory, inputMemorySize)
//    val input = memToInput(inputMemory)
//
//    val pUCMD = input.pCommands + (lastSequenceNumber % 150) * 0x64
//
//    var curCMDNumber = CSGO.csgoEXE.int(pUCMD + 0x4)
//
//    while (curCMDNumber < wantedSequenceNumber) {
//        curCMDNumber = CSGO.csgoEXE.int(pUCMD + 0x4)
//
//
//    }
}






private const val inputMemorySize = 253
private val inputMemory = threadLocalPointer(inputMemorySize)

private const val oldUserCmdMemorySize = 100
private val oldUserCmdMemory = threadLocalPointer(oldUserCmdMemorySize)

private const val newUserCmdMemorySize = 100
private val newUserCMDMemory = threadLocalPointer(newUserCmdMemorySize)

private val oldUserCMD = UserCMD()
private val newUserCMD = UserCMD()

fun shootSilent(viewAngles: Vector) {
    sendPacket(false)

    val iDesiredCmdNumber = CSGO.csgoEXE.int(clientState + EngineOffsets.dwClientState_LastOutgoingCommand) + 2

    val inputMemory = inputMemory.get()
    CSGO.csgoEXE.read(CSGO.clientDLL.address + ClientOffsets.dwInput, inputMemory, inputMemorySize)
    val input = memToInput(inputMemory)

    val pOldUCMD = input.pCommands + ((iDesiredCmdNumber - 1) % 150) * 0x64
    val pVerifiedOldUCMD = input.pVerifiedCommands + ((iDesiredCmdNumber - 1) % 150) * 0x68

    val pUCMD = input.pCommands + (iDesiredCmdNumber % 150) * 0x64
    while (CSGO.csgoEXE.int(pUCMD + 0x4) < iDesiredCmdNumber) {
        Thread.yield()
    }

    //Check invalid?
    val oldUserCmdMemory = oldUserCmdMemory.get()
    val newUserCmdMemory = newUserCMDMemory.get()

    CSGO.csgoEXE.read(pOldUCMD, oldUserCmdMemory, oldUserCmdMemorySize)
    CSGO.csgoEXE.read(pUCMD, newUserCmdMemory, newUserCmdMemorySize)

    val oldUserCMD = memToUserCMD(oldUserCmdMemory, oldUserCMD)
    //val newUserCMD = memToUserCMD(newUserCmdMemory, newUserCMD)

    oldUserCMD.vecViewAngles = viewAngles
    oldUserCMD.iButtons = oldUserCMD.iButtons or 1 // << 1 =|= IN_ATTACK

    userCMDToMem(pOldUCMD, oldUserCMD)
    userCMDToMem(pVerifiedOldUCMD, oldUserCMD)

    sendPacket(true)
}