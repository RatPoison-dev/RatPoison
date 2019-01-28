package rat.poison.scripts.esp

import rat.poison.game.CSGO
import rat.poison.game.Color
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets
import rat.poison.game.offsets.ClientOffsets
import rat.poison.game.offsets.EngineOffsets
import rat.poison.utils.extensions.uint
import java.lang.Float.floatToIntBits

internal fun disableEsp() {
    val cWhite = Color(255, 255, 255, 1.0)

    val clientVModEnt = CSGO.csgoEXE.uint(CSGO.clientDLL.address + ClientOffsets.dwEntityList + (((CSGO.csgoEXE.uint(CSGO.csgoEXE.uint(CSGO.clientDLL.address + ClientOffsets.dwLocalPlayer)+ NetVarOffsets.m_hViewModel)) and 0xFFF) - 1) * 16)
    CSGO.csgoEXE[clientVModEnt + 0x70] = Color(255, 0, 0, 1.0).red.toByte()
    CSGO.csgoEXE[clientVModEnt + 0x71] = Color(0, 255, 0, 1.0).green.toByte()
    CSGO.csgoEXE[clientVModEnt + 0x72] = Color(0, 0, 255, 1.0).blue.toByte()
    CSGO.engineDLL[EngineOffsets.dwModelAmbientMin] = floatToIntBits(0F) xor (CSGO.engineDLL.address + EngineOffsets.dwModelAmbientMin - 0x2C).toInt()

    forEntities body@ {
        val entity = it.entity
        if (entity <= 0 || me == entity) return@body false

        val glowAddress = it.glowAddress
        if (glowAddress <= 0) return@body false

        glowAddress.glow(cWhite)
        entity.chams(cWhite)

        return@body false
    }
}

private fun Entity.glow(color: Color) {
    CSGO.csgoEXE[this + 0x4] = color.red / 255F
    CSGO.csgoEXE[this + 0x8] = color.green / 255F
    CSGO.csgoEXE[this + 0xC] = color.blue / 255F
    CSGO.csgoEXE[this + 0x10] = color.alpha.toFloat()//color.alpha.toFloat()
    CSGO.csgoEXE[this + 0x24] = false //Render When Occluded

    CSGO.csgoEXE[this + 0x26] = false //Full Bloom Render
}

private fun Entity.chams(color: Color) {
    CSGO.csgoEXE[this + 0x70] = color.red.toByte()
    CSGO.csgoEXE[this + 0x71] = color.green.toByte()
    CSGO.csgoEXE[this + 0x72] = color.blue.toByte()
}