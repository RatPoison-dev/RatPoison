package rat.poison.scripts

import rat.poison.appless
import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.game.offsets.ClientOffsets
import rat.poison.game.offsets.EngineOffsets
import rat.poison.scripts.aim.*
import rat.poison.scripts.userCmd.userCmdAim
import rat.poison.settings.*
import rat.poison.utils.Structs.*
import rat.poison.utils.common.*
import rat.poison.utils.keybindEval
import java.lang.Thread.yield

var lastCMDNumber = 0
var chokedCommands = 0

private const val inputMemorySize = 253
private val inputMemory = threadLocalPointer(inputMemorySize)

fun handleUCMD() = every(1) {
    if (!curSettings.bool["USER_CMD"]) return@every

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
            chokedCommands = CSGO.csgoEXE.int(clientState + EngineOffsets.dwClientState_ChokedCommands) - 1
            if (chokedCommands < 0) chokedCommands = 0

            //Aimbot : Do all the stuff, priority 1
            if (curSettings.bool["SILENT_AIM"]) {
                userCmdAim()
            }

            //Trigger : Do all the stuff, priority 2

            //Backtrack : Do all the stuff, priority 3

            //Slidewalk

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
    val oldUserCmdMemory = oldUserCmdMemory.get()
    CSGO.csgoEXE.read(oldPtr, oldUserCmdMemory, oldUserCmdMemorySize)
    val oldUserCMD = memToUserCMD(oldUserCmdMemory, oldUserCMD)

    var shouldSendCmd = false

    if (!canSetCmdAngles) { //Queued
        oldUserCMD.vecViewAngles = nextCMD.vecViewAngles
        canSetCmdAngles = true

        shouldSendCmd = true
    }

    //Aim Key
    if (keyPressed(1) && !(!inFullscreen && ((MENUTOG && !appless) || (me > 0L && meDead) || inBackground))) {
        //Shoot
        oldUserCMD.iButtons = oldUserCMD.iButtons or 1
    }

    if (shouldSendCmd) {
        userCMDToMem(oldPtr, oldUserCMD)
        userCMDToMem(oldVerifiedPtr, oldUserCMD)
    }

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