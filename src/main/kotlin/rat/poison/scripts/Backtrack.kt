package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.*
import rat.poison.game.*
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.CSGO.engineDLL
import rat.poison.game.entity.*
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.game.entity.dead
import rat.poison.game.entity.dormant
import rat.poison.game.entity.team
import rat.poison.game.forEntities
import rat.poison.game.netvars.NetVarOffsets.flSimulationTime
import rat.poison.game.offsets.ClientOffsets.dwIndex
import rat.poison.game.offsets.ClientOffsets.dwInput
import rat.poison.game.offsets.EngineOffsets.dwClientState_LastOutgoingCommand
import rat.poison.game.offsets.EngineOffsets.dwGlobalVars
import rat.poison.scripts.aim.calcTarget
import rat.poison.scripts.aim.findTarget
import rat.poison.utils.Angle
import rat.poison.utils.Structs.*
import rat.poison.utils.every
import rat.poison.utils.extensions.uint
import rat.poison.utils.notInGame
import kotlin.math.abs

var btRecords = Array(64) { Array(12) { backtrackTab() } }
data class backtrackTab(var simtime: Float = 0f, var neckPos: Angle = Angle(), var chestPos: Angle = Angle(),
                        var stomachPos: Angle = Angle(), var pelvisPos: Angle = Angle(), var alpha: Float = 100f)

private var enableNeck = false
private var enableChest = false
private var enableStomach = false
private var enablePelvis = false
private var bestTarget = -1L

fun sendPacket(bool: Boolean) { //move outta here
    val byte = if (bool) 1.toByte() else 0.toByte()
    engineDLL[0xD409A] = byte
}

fun backtrack() = every(4) {
    if (notInGame || !curSettings["ENABLE_BACKTRACK"].strToBool() || me <= 0) {
        btRecords = Array(64) { Array(13) { backtrackTab() } }
        if (engineDLL.byte(0xD409A) == 0.toByte()) {
            sendPacket(true)
        }
        return@every
    }
    constructRecords()

    if (!me.weaponEntity().canFire()) return@every

    if (curSettings["ENABLE_BACKTRACK_ON_KEY"].strToBool() && !keyPressed(curSettings["BACKTRACK_KEY"].toInt())) return@every

    //Get/set vars
    val meWep = me.weapon()
    var prefix = ""
    when {
        meWep.pistol -> { prefix = "PISTOL_" }
        meWep.rifle -> { prefix = "RIFLE_" }
        meWep.shotgun -> { prefix = "SHOTGUN_" }
        meWep.sniper -> { prefix = "SNIPER_" }
        meWep.smg -> { prefix = "SMG_" }
    }

    if (meWep.gun) { //Not 100% this applies to every 'gun'
        enableNeck = curSettings[prefix + "BACKTRACK_NECK"].strToBool()
        enableChest = curSettings[prefix + "BACKTRACK_CHEST"].strToBool()
        enableStomach = curSettings[prefix + "BACKTRACK_STOMACH"].strToBool()
        enablePelvis = curSettings[prefix + "BACKTRACK_PELVIS"].strToBool()
    }

    val curSequenceNumber = csgoEXE.int(clientState + dwClientState_LastOutgoingCommand) + 1
    sendPacket(false)

    val input = memToInput(csgoEXE.read(clientDLL.address + dwInput, 253)!!)

    val userCMDptr = input.pCommands + (curSequenceNumber % 150) * 0x64
    val verifiedUserCMDptr = input.pVerifiedCommands + (curSequenceNumber % 150) * 0x68
    val oldUserCMDptr = input.pCommands + ((curSequenceNumber - 1) % 150) * 0x64

    while (csgoEXE.int(userCMDptr + 0x4) < curSequenceNumber) {
        Thread.sleep(1)
    }

    //Check invalid?
    val oldUserCMD = memToUserCMD(csgoEXE.read(oldUserCMDptr, 100)!!)
    var userCMD = memToUserCMD(csgoEXE.read(userCMDptr, 100)!!)

    userCMD = fixUserCMD(userCMD, oldUserCMD)

    val bestTime = bestSimTime()

    if (bestTime == -1f) {
        sendPacket(true)
        return@every
    }

    userCMD.iButtons = userCMD.iButtons or 1 // << 1 =|= IN_ATTACK
    userCMD.iTickCount = timeToTicks(bestTime)

    userCMDToMem(userCMDptr, userCMD)
    userCMDToMem(verifiedUserCMDptr, userCMD)

    sendPacket(true)
}

fun constructRecords() {
    var bestFov = 5.0
    forEntities(ccsPlayer) {
        val ent = it.entity

        if (!ent.dead() && !ent.dormant()) {
            val pos = ent.bones(6)

            val fov = calcTarget(bestFov, bestTarget, pos, clientState.angle(), 10, 6, ovrStatic = true)[0] as Double

            if (fov < bestFov && fov > 0) {
                bestFov = fov
                bestTarget = ent
            }
        }

        return@forEntities false
    }

    if (bestFov == 5.0) {
        bestTarget = -1L
    }

    forEntities(ccsPlayer) {
        val ent = it.entity

        if (ent == me || ent.dormant() || ent.team() == me.team() || ent.dead()) {
            return@forEntities false
        }

        val entSimTime = csgoEXE.float(ent + flSimulationTime)

        val entID = (csgoEXE.uint(ent + dwIndex) - 1).toInt()
        val tick = getGlobalVars().tickCount % 13

        if (entID in 0..63 && tick < 12) { //clamp that bitch error prone on player join/leave
            val record = btRecords[entID][tick]

            val neckPos: Angle; val chestPos: Angle; val stomachPos: Angle; val pelvisPos: Angle

            if (enableNeck) { neckPos = ent.bones(7); record.neckPos = neckPos }
            if (enableChest) { chestPos = ent.bones(6); record.chestPos = chestPos }
            if (enableStomach) { stomachPos = ent.bones(5); record.stomachPos = stomachPos }
            if (enablePelvis) { pelvisPos = ent.bones(0); record.pelvisPos = pelvisPos }

            record.alpha = 100f
            record.simtime = entSimTime

            btRecords[entID][tick] = record
        }

        return@forEntities false
    }
}

fun bestSimTime(): Float {
    if (bestTarget <= 0L) {
        return -1f
    }

    var tmp = Double.MAX_VALUE
    var best = -1f
    val targetID = (csgoEXE.uint(bestTarget + dwIndex)-1).toInt()
    val clientAngle = clientState.angle()
    val meTime = csgoEXE.float(me + flSimulationTime)
    val maxFov = curSettings["BACKTRACK_FOV"].toFloat()

    for (t in 0 until 12) {
        if (!isValidTick(timeToTicks(btRecords[targetID][t].simtime))) {
            continue
        }

        for (i in 1 until 5) {
            var pos = Angle()
            when (i) {
                1 -> pos = btRecords[targetID][t].neckPos
                2 -> pos = btRecords[targetID][t].chestPos
                3 -> pos = btRecords[targetID][t].stomachPos
                4 -> pos = btRecords[targetID][t].pelvisPos
            }

            if (pos != Angle()) {
                val fov = calcTarget(tmp, bestTarget, pos, clientAngle, 10, 6, ovrStatic = true)[0] as Double

                if (tmp > fov && btRecords[targetID][t].simtime > meTime - 1 && fov > 0 && fov < maxFov) {
                    tmp = fov
                    best = btRecords[targetID][t].simtime

                    if (!curSettings["BACKTRACK_PREFER_ACCURATE"].strToBool()) {
                        return best
                    }
                }
            }
        }
    }

    return best
}

fun isValidTick(tick: Int): Boolean {
    val gvars = getGlobalVars()
    val delta = gvars.tickCount - tick
    val deltaTime = delta * gvars.intervalPerTick
    val max = curSettings["BACKTRACK_MS"].toFloat()/1000f

    return abs(deltaTime) <= max
}

fun timeToTicks(time: Float): Int {
    return (.5f + time / getGlobalVars().intervalPerTick).toInt()
}

fun getGlobalVars(): GlobalVars {
    return memToGlobalVars(csgoEXE.read(engineDLL.address + dwGlobalVars, 64)!!)
}