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

internal fun disableAllEsp() {
    val cWhite = Color(255, 255, 255, 1.0)

    val clientVModEnt = CSGO.csgoEXE.uint(CSGO.clientDLL.address + ClientOffsets.dwEntityList + (((CSGO.csgoEXE.uint(CSGO.csgoEXE.uint(CSGO.clientDLL.address + ClientOffsets.dwLocalPlayer)+ NetVarOffsets.m_hViewModel)) and 0xFFF) - 1) * 16)
    CSGO.csgoEXE[clientVModEnt + 0x70] = 255.toByte()
    CSGO.csgoEXE[clientVModEnt + 0x71] = 255.toByte()
    CSGO.csgoEXE[clientVModEnt + 0x72] = 255.toByte()
    CSGO.engineDLL[EngineOffsets.dwModelAmbientMin] = floatToIntBits(0F) xor (CSGO.engineDLL.address + EngineOffsets.dwModelAmbientMin - 0x2C).toInt()

    forEntities body@ {
        val entity = it.entity
        val type = it.type
        val glowAddress = it.glowAddress
        if (entity <= 0 || me == entity || glowAddress <= 0) return@body false

        if (type != EntityType.NULL) {
            when (type) {
                EntityType.CCSPlayer -> {
                    glowAddress.glow(cWhite); entity.chams(cWhite)
                }
                EntityType.CPlantedC4 -> glowAddress.glow(cWhite)
                EntityType.CC4 -> glowAddress.glow(cWhite)
                else -> {
                    if (type.weapon || type.grenade) {
                        glowAddress.glow(cWhite)
                    }
                }
            }
        }

        return@body false
    }
}

private fun Entity.glow(color: Color) {
    CSGO.csgoEXE[this + 0x4] = color.red / 255F
    CSGO.csgoEXE[this + 0x8] = color.green / 255F
    CSGO.csgoEXE[this + 0xC] = color.blue / 255F
    CSGO.csgoEXE[this + 0x10] = color.alpha.toFloat()
    CSGO.csgoEXE[this + 0x24] = false
    CSGO.csgoEXE[this + 0x26] = false
}

private fun Entity.chams(color: Color) {
    CSGO.csgoEXE[this + 0x70] = color.red.toByte()
    CSGO.csgoEXE[this + 0x71] = color.green.toByte()
    CSGO.csgoEXE[this + 0x72] = color.blue.toByte()
}