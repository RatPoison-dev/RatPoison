package rat.poison.scripts.userCmd

import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.*
import rat.poison.game.offsets.ClientOffsets
import rat.poison.game.offsets.EngineOffsets
import rat.poison.game.offsets.EngineOffsets.dwClientStateNetChannel
import rat.poison.scripts.aim.*
import rat.poison.scripts.attemptBacktrack
import rat.poison.scripts.misc.sendPacket
import rat.poison.settings.*
import rat.poison.utils.Structs.*
import rat.poison.utils.common.*
import java.lang.Thread.yield

var lastCMDNumber = 0
var chokedCommands = 0

private const val inputMemorySize = 253
private val inputMemory = threadLocalPointer(inputMemorySize)

var meDead = true

var silentHaveTarget = false

fun handleUCMD() = every(1, true) {
    meDead = me.dead()

    //TODO prechecks
    if (meDead || !inGame || inBackground) return@every

    if (!curSettings.bool["USER_CMD"]) return@every

    val inputMemory = inputMemory.get()
    val lastCommand = csgoEXE.int(clientState + EngineOffsets.dwClientState_LastOutgoingCommand)

    if (lastCMDNumber != lastCommand) {
        sendPacket(false)

        val currentCommandNumber = csgoEXE.int(clientState + EngineOffsets.dwClientState_LastOutgoingCommand) + 2

        csgoEXE.read(clientDLL.address + ClientOffsets.dwInput, inputMemory, inputMemorySize)

        val input = memToInput(inputMemory)
        //val pUCMD = input.pCommands + (currentCommandNumber % 150) * 0x64
        val pNetChannel = csgoEXE.int(clientState + dwClientStateNetChannel)

        second@while (true) {
            val curCMDNumber = csgoEXE.int(pNetChannel + 0x18) //find out what 0x18 supposd b
            chokedCommands = csgoEXE.int(clientState + EngineOffsets.dwClientState_ChokedCommands) - 1 //TODO is this supposed to be - 1?
            if (chokedCommands < 0) chokedCommands = 0

            //Aimbot : Do all the stuff, priority 1
            if (curSettings.bool["SILENT_AIM"]) {
                silentHaveTarget = ucmdAim()
            }

            if (curCMDNumber >= currentCommandNumber || chokedCommands >= 7) {
                val oldUserCMDptr = input.pCommands + ((currentCommandNumber-1) % 150) * 0x64
                val verifiedOldUserCMDptr = input.pVerifiedCommands + ((currentCommandNumber-1) % 150) * 0x68

                val oldUserCmdMemory = oldUserCmdMemory.get()
                csgoEXE.read(oldUserCMDptr, oldUserCmdMemory, oldUserCmdMemorySize)
                memToUserCMD(oldUserCmdMemory, oldUserCMD)

                //Trigger : Do all the stuff, priority 2

                //Backtrack : Do all the stuff, priority 3
                attemptBacktrack(oldUserCMD)

                sendUserCMD(oldUserCMD, oldUserCMDptr, verifiedOldUserCMDptr)
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

fun sendUserCMD(userCmd: UserCMD, oldPtr: Int, oldVerifiedPtr: Int) {
    var shouldSendCmd = false

    if (!canSetCmdAngles) { //Queued
        userCmd.vecViewAngles = nextCMD.vecViewAngles
        canSetCmdAngles = true

        shouldSendCmd = true
    }

    //Aim Key
    if (keyPressed(1) && !meDead && !inBackground && inGame) {
        //Shoot
        userCmd.iButtons = userCmd.iButtons or 1

        if (curSettings.bool["SILENT_AIM"] && curSettings.bool["SILENT_REQUIRE_TARGET"] && !silentHaveTarget) {
            return
        }

        shouldSendCmd = true
    }

    if (shouldSendCmd) {
        userCMDToMem(oldPtr, userCmd)
        userCMDToMem(oldVerifiedPtr, userCmd)
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