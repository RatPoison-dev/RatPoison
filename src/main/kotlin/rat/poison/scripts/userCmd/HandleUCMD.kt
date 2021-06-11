package rat.poison.scripts.userCmd

import kotlinx.coroutines.Dispatchers
import rat.poison.curSettings
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.clientState
import rat.poison.game.entity.dead
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets
import rat.poison.game.offsets.EngineOffsets
import rat.poison.game.offsets.EngineOffsets.dwClientStateNetChannel
import rat.poison.scripts.attemptBacktrack
import rat.poison.scripts.misc.gvars
import rat.poison.scripts.misc.sendPacket
import rat.poison.utils.Structs.*
import rat.poison.utils.common.*
import kotlinx.coroutines.*
import kotlinx.coroutines.launch
import rat.poison.dbgLog
import rat.poison.scripts.aim.meCurWep
import rat.poison.settings.TRIGGER_USE_AIMBOT

var lastCMDTime = 0F
var lastCMDNumber = 0
var lastCalcTime = 0L
var chokedCommands = 0

private const val inputMemorySize = 253
private val inputMemory = threadLocalPointer(inputMemorySize)

var meDead = true

var silentHaveTarget = false

var curTime = 0F

var trigEnt = 0L
var trigQueuedShotTime = -1F

var aimTargetSwapTime = -1F

fun handleUCMD() = CoroutineScope(Dispatchers.Default).launch {
    every@ while (true) {
        meDead = me.dead()

        curTime = gvars.curTime

        //TODO prechecks
        if (meDead || !inGame || inBackground) continue

        if (!curSettings.bool["USER_CMD"]) continue

        val inputMemory = inputMemory.get()
        val pNetChannel = csgoEXE.int(clientState + dwClientStateNetChannel)
        val lastCommand = csgoEXE.int(pNetChannel + 0x18)//csgoEXE.int(clientState + EngineOffsets.dwClientState_LastOutgoingCommand)

        if (lastCMDNumber != lastCommand) {
            //println("Real Last: $lastCommand Logged Last: $lastCMDNumber Last Time: $lastCMDTime Delayed by: ${gvars.curTime - lastCMDTime} Thread Delay: ${lastCalcTime}")

            sendPacket(false)

            val currentCommandNumber = csgoEXE.int(clientState + EngineOffsets.dwClientState_LastOutgoingCommand) + 2

            csgoEXE.read(clientDLL.address + ClientOffsets.dwInput, inputMemory, inputMemorySize)

            val input = memToInput(inputMemory)
            //val pUCMD = input.pCommands + (currentCommandNumber % 150) * 0x64

            //perhaps IO dispatcher...
            //TODO this nextCMD bs is retarded ong ong
            val aim = async {
                //Aimbot : Do all the stuff, priority 1
                if (curSettings.bool["UCMD_SILENT_AIM"]) {
                    ucmdAim(true)
                }
            }

            val trigger = async {
                //Trigger : Do all the stuff, priority 2
                if (curSettings.bool["UCMD_HANDLE_TRIGGER"]) {
                    ucmdTrigger(null)
                }
            }

            val backtrack = async{
                //Backtrack : Do all the stuff, priority 3
                if (curSettings.bool["UCMD_HANDLE_BACKTRACK"]) {
                    attemptBacktrack(null)
                }
            }

            loop@ while (true) {
                val curCMDNumber = csgoEXE.int(pNetChannel + 0x18) //find out what 0x18 supposd b
                chokedCommands = csgoEXE.int(clientState + EngineOffsets.dwClientState_ChokedCommands) - 1  //TODO is this supposed to be - 1?
                if (chokedCommands < 0) chokedCommands = 0

                if (chokedCommands >= 7) {
                    //TODO timeout fallback sillytime
                    lastCMDNumber = curCMDNumber

                    break@loop
                } else if (curCMDNumber >= currentCommandNumber) {
                    if (curCMDNumber > currentCommandNumber) {
                        dbgLog("CMD Dif: ${curCMDNumber - currentCommandNumber} want: $currentCommandNumber got: $curCMDNumber")
                    }

                    val cur = input.pCommands + ((lastCMDNumber - 1) % 150) * 0x64 //perhaps...
                    val oldUserCMDptr = input.pCommands + ((currentCommandNumber - 1) % 150) * 0x64
                    val verifiedOldUserCMDptr = input.pVerifiedCommands + ((currentCommandNumber - 1) % 150) * 0x68

                    val curcmd = UserCMD()

                    lastCMDNumber = curCMDNumber

                    val oldUserCmdMemory = oldUserCmdMemory.get()
                    val curUserCmdMemory = curUserCmdMemory.get()

                    csgoEXE.read(oldUserCMDptr, oldUserCmdMemory, oldUserCmdMemorySize)
                    csgoEXE.read(cur, curUserCmdMemory, curUserCmdMemorySize)

                    //perhaps read, copy current, then await...

                    memToUserCMD(oldUserCmdMemory, oldUserCMD)
                    memToUserCMD(curUserCmdMemory, curcmd)

                    aim.await(); trigger.await(); backtrack.await()

                    sendUserCMD(oldUserCMD, oldUserCMDptr, verifiedOldUserCMDptr)

                    break@loop
                }

                delay(1)
            }

            sendPacket(true)
            resetCMD()

            lastCMDTime = gvars.curTime

            val decimal = lastCMDTime - lastCMDTime.toInt()

            delay(kotlin.math.floor(decimal % 15.625).toLong())

            //delay(10)
        }
    }
}

private const val oldUserCmdMemorySize = 100
private val oldUserCmdMemory = threadLocalPointer(oldUserCmdMemorySize)
private val oldUserCMD = UserCMD()

private const val curUserCmdMemorySize = 100
private val curUserCmdMemory = threadLocalPointer(curUserCmdMemorySize)

var nextCMD = UserCMD() //Used to queue...

var shouldSendNextCMD = false

private fun resetCMD() {
    nextCMD.reset()
    shouldSendNextCMD = false
    silentHaveTarget = false
}

//const val button_in_forward = (1 shl 3)
//const val button_in_back = (1 shl 4)
//const val button_in_move_left = (1 shl 9)
//const val button_in_move_right = (1 shl 10)

var ap = false

fun sendUserCMD(userCMD: UserCMD, oldPtr: Int, oldVerifiedPtr: Int) = CoroutineScope(Dispatchers.Unconfined).launch {
    if (!canSetCmdAngles) { //Queued
        if (!nextCMD.vecViewAngles.isZero()) { //TODO there is an error somewhere...
            userCMD.vecViewAngles = nextCMD.vecViewAngles
            canSetCmdAngles = true

            shouldSendNextCMD = true
        }
    }

    //Buttons
    userCMD.iButtons = userCMD.iButtons or nextCMD.iButtons

    //Aim Key
    if (curSettings.bool["UCMD_HANDLE_FIRE_KEY"]) {
        updateCursorEnable()
        if (!meDead && inGame && !cursorEnable) {
            if (keyPressed(1)) {
                if (curSettings.bool["UCMD_SILENT_AIM"]) {
                    if (curSettings.bool["UCMD_SILENT_REQUIRE_TARGET"] && !silentHaveTarget) {
                        //Nothin
                    } else {
                        //Shoot
                        cmdShoot(userCMD)

                        shouldSendNextCMD = true
                    }
                } else {
                    if (!ap) {
                        //Shoot
                        cmdShoot(userCMD)

                        shouldSendNextCMD = true

                        if (!meCurWep.automatic || userCMD.iButtons and 1 == 1) {
                            ap = true
                        }
                    }
                }
            } else {
                ap = false
            }
        }
    } else {
        ap = false
    }

    //Trigger
    if (trigQueuedShotTime > 0 && curTime >= trigQueuedShotTime) {
        launch {
            repeat(15) {
                if (TRIGGER_USE_AIMBOT) {
                    ucmdTriggerAim(curSettings.bool["UCMD_SILENT_AIM"], trigEnt)
                }
                delay(1)
            }
        }
        cmdShoot(userCMD)
        trigQueuedShotTime = -1F
        shouldSendNextCMD = true
    }

    //Backtrack
    if (nextCMD.iTickCount != 0) {
        userCMD.iTickCount = nextCMD.iTickCount
    }

    //Fast Stop
//    if (curSettings.bool["UCMD_FAST_STOP"]) {
//        if (userCMD.iButtons and 1 == 1) {
//        }
//    }

    //Slide
//    if (userCMD.flForwardmove > 0) {
//        userCMD.iButtons = userCMD.iButtons or button_in_back
//        userCMD.iButtons = userCMD.iButtons and button_in_forward.inv()
//
//        shouldSendNextCMD = true
//    }
//
//    if (userCMD.flForwardmove < 0) {
//        userCMD.iButtons = userCMD.iButtons or button_in_forward
//        userCMD.iButtons = userCMD.iButtons and button_in_back.inv()
//
//        shouldSendNextCMD = true
//    }
//
//    if (userCMD.flSidemove > 0) {
//        userCMD.iButtons = userCMD.iButtons or button_in_move_left
//        userCMD.iButtons = userCMD.iButtons and button_in_move_right.inv()
//
//        shouldSendNextCMD = true
//    }
//
//    if (userCMD.flSidemove < 0) {
//        userCMD.iButtons = userCMD.iButtons or button_in_move_right
//        userCMD.iButtons = userCMD.iButtons and button_in_move_left.inv()
//
//        shouldSendNextCMD = true
//    }

    if (shouldSendNextCMD) {
        userCMDToMem(oldPtr, userCMD)
        userCMDToMem(oldVerifiedPtr, userCMD)
    }

    //If we don't catch it just wasn't meant to be....
    resetCMD()
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

fun cmdShoot(cmd: UserCMD?) {
    if (cmd != null) {
        cmd.iButtons = cmd.iButtons or 1
    } else {
        nextCMD.iButtons = nextCMD.iButtons or 1
    }

    shouldSendNextCMD = true
}