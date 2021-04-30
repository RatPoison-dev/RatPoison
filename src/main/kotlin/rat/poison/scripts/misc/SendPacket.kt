package rat.poison.scripts.misc

import rat.poison.game.CSGO
import rat.poison.game.offsets.EngineOffsets

fun sendPacket(bool: Boolean) { //move outta here
    val byte = if (bool) 1.toByte() else 0.toByte()
    CSGO.engineDLL[EngineOffsets.dwbSendPackets] = byte //Bitch ass lil coder signature wont work
}