package rat.poison.scripts.esp

import rat.poison.game.CSGO.clientDLL
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.CSGO.engineDLL
import rat.poison.game.Color
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.m_hViewModel
import rat.poison.game.offsets.ClientOffsets.dwEntityList
import rat.poison.game.offsets.ClientOffsets.dwLocalPlayer
import rat.poison.game.offsets.EngineOffsets.dwModelAmbientMin
import rat.poison.settings.*
import rat.poison.utils.every
import rat.poison.utils.extensions.uint
import java.lang.Float.floatToIntBits

//Change for entities to for entities ccsplayer

internal fun chamsEsp() = every(256) {
    if ((!CHAMS_ESP || !ENABLE_ESP)) return@every

    val myTeam = me.team()

    val brightnessCounter : Int

    if (CHAMS_BRIGHTNESS > 0) {
        brightnessCounter = (255F / (CHAMS_BRIGHTNESS / 10F)).toInt()
    } else {
        brightnessCounter = 255
    }

    //Edit playermodel to counter weapon brightness
    val clientVModEnt = csgoEXE.uint(clientDLL.address + dwEntityList + (((csgoEXE.uint(csgoEXE.uint(clientDLL.address + dwLocalPlayer) + m_hViewModel)) and 0xFFF) - 1) * 16)

    //Set VMod
    csgoEXE[clientVModEnt + 0x70] = brightnessCounter.toByte()
    csgoEXE[clientVModEnt + 0x71] = brightnessCounter.toByte()
    csgoEXE[clientVModEnt + 0x72] = brightnessCounter.toByte()

    forEntities body@ {
        val entity = it.entity
        if (entity <= 0 || entity == dwLocalPlayer || me == entity) return@body false

        val glowAddress = it.glowAddress
        if (glowAddress <= 0) return@body false

        //Set Cvar
        engineDLL[dwModelAmbientMin] = floatToIntBits(CHAMS_BRIGHTNESS.toFloat()) xor (engineDLL.address + dwModelAmbientMin - 0x2C).toInt()

        //Not exhaustive @warning
        if (it.type == EntityType.CCSPlayer) {
            if (entity.dead() || entity == me || entity.dormant()) return@body false

            val entityTeam = entity.team()
            val team = !DANGER_ZONE && myTeam == entityTeam

            if (CHAMS_SHOW_ENEMIES && !team) {
                if (CHAMS_SHOW_HEALTH) {
                    entity.chams(Color((255 - 2.55 * entity.health()).toInt(), (2.55 * entity.health()).toInt(), 0, 1.0))
                } else {
                    entity.chams(ENEMY_COLOR)
                }
            } else if (!CHAMS_SHOW_ENEMIES) {
                entity.chams(Color(brightnessCounter, brightnessCounter, brightnessCounter, 1.0))
            } else if (CHAMS_SHOW_TEAM && team) {
                entity.chams(TEAM_COLOR)
            }
            else {
                entity.chams(Color(brightnessCounter, brightnessCounter, brightnessCounter, 1.0))
            }
        }
        return@body false
    }
}

private fun Entity.chams(color: Color) {
    csgoEXE[this + 0x70] = color.red.toByte()
    csgoEXE[this + 0x71] = color.green.toByte()
    csgoEXE[this + 0x72] = color.blue.toByte()
}