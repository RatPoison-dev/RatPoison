package rat.poison.scripts.userCmd

import rat.poison.curSettings
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.clientState
import rat.poison.game.entity.dead
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets
import rat.poison.game.offsets.EngineOffsets
import rat.poison.game.offsets.EngineOffsets.dwClientStateNetChannel
import rat.poison.scripts.attemptBacktrack
import rat.poison.scripts.misc.gvars
import rat.poison.scripts.misc.sendPacket
import rat.poison.settings.MENUTOG
import rat.poison.utils.Structs.*
import rat.poison.utils.common.*
import java.lang.Thread.yield

var lastCMDNumber = 0
var chokedCommands = 0

private const val inputMemorySize = 253
private val inputMemory = threadLocalPointer(inputMemorySize)

var meDead = true

var silentHaveTarget = false

var curTime = 0F

var trigEnt = 0L
var trigQueuedShotTime = -1F

var aimTargetSwapTime = -1F

fun handleUCMD() = every(1, true) {
    meDead = me.dead()

    curTime = gvars.curTime

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

        loop@while (true) {
            val curCMDNumber = csgoEXE.int(pNetChannel + 0x18) //find out what 0x18 supposd b
            chokedCommands = csgoEXE.int(clientState + EngineOffsets.dwClientState_ChokedCommands) - 1 //TODO is this supposed to be - 1?
            if (chokedCommands < 0) chokedCommands = 0

            //Aimbot : Do all the stuff, priority 1
            if (curSettings.bool["UCMD_SILENT_AIM"]) {
                ucmdAim(true)
            }

            //Trigger : Do all the stuff, priority 2
            if (curSettings.bool["UCMD_HANDLE_TRIGGER"]) {
                ucmdTrigger(oldUserCMD)
            }

            if (curCMDNumber >= currentCommandNumber || chokedCommands >= 7) {
                val oldUserCMDptr = input.pCommands + ((currentCommandNumber-1) % 150) * 0x64
                val verifiedOldUserCMDptr = input.pVerifiedCommands + ((currentCommandNumber-1) % 150) * 0x68

                val oldUserCmdMemory = oldUserCmdMemory.get()
                csgoEXE.read(oldUserCMDptr, oldUserCmdMemory, oldUserCmdMemorySize)
                memToUserCMD(oldUserCmdMemory, oldUserCMD)

                //Backtrack : Do all the stuff, priority 3
                attemptBacktrack(oldUserCMD)

                sendUserCMD(oldUserCMD, oldUserCMDptr, verifiedOldUserCMDptr)
                sendPacket(true)

                Thread.sleep(8) //pas one down s

                break@loop
            }

            yield()
        }
    }
}

private const val oldUserCmdMemorySize = 100
private val oldUserCmdMemory = threadLocalPointer(oldUserCmdMemorySize)
private val oldUserCMD = UserCMD()
var nextCMD = UserCMD()

var shouldSendNextCMD = false

fun sendUserCMD(userCMD: UserCMD, oldPtr: Int, oldVerifiedPtr: Int) {
    if (!canSetCmdAngles) { //Queued
        userCMD.vecViewAngles = nextCMD.vecViewAngles
        canSetCmdAngles = true

        shouldSendNextCMD = true
    }

    //Aim Key
    if (curSettings.bool["UCMD_HANDLE_FIRE_KEY"]) {
        if (keyPressed(1) && !meDead && !inBackground && inGame && !MENUTOG) {
            if (curSettings.bool["UCMD_SILENT_AIM"]) {
                if (curSettings.bool["UCMD_SILENT_REQUIRE_TARGET"] && !silentHaveTarget) {
                    //Nothin
                } else {
                    //Shoot
                    cmdShoot(userCMD)

                    shouldSendNextCMD = true
                }
            } else {
                //Shoot
                cmdShoot(userCMD)

                shouldSendNextCMD = true
            }
        }
    }

    //Trigger
    if (trigQueuedShotTime > 0 && curTime >= trigQueuedShotTime) {
        ucmdTriggerAim(curSettings.bool["UCMD_SILENT_AIM"], trigEnt)
        cmdShoot(userCMD)
        trigQueuedShotTime = -1F
        shouldSendNextCMD = true
    }

    if (shouldSendNextCMD) {
        userCMDToMem(oldPtr, userCMD)
        userCMDToMem(oldVerifiedPtr, userCMD)

        shouldSendNextCMD = false
    }

    nextCMD.reset()
    silentHaveTarget = false
}

//yuh yuh yah fuck this tho ong fr fr breezy
var canSetCmdAngles = true
fun cmdSetAngles(viewAngles: Vector) {
    if (canSetCmdAngles) {
        nextCMD.vecViewAngles = viewAngles

        silentHaveTarget = true

        canSetCmdAngles = false
    }
}

fun cmdShoot(cmd: UserCMD) {
    cmd.iButtons = cmd.iButtons or 1
}