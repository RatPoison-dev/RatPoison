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

internal fun chamsEsp() = every(500) {
    if ((!CHAMS_ESP || !ENABLE_ESP)) return@every

    val myTeam = me.team()

    forEntities body@ {
        val entity = it.entity
        if (entity <= 0 || entity == dwLocalPlayer || me == entity) return@body false

        val glowAddress = it.glowAddress
        if (glowAddress <= 0) return@body false

        //Edit playermodel to counter weapon brightness
        val ClientVModEnt = csgoEXE.uint(clientDLL.address + dwEntityList + (((csgoEXE.uint(csgoEXE.uint(clientDLL.address + dwLocalPlayer)+ m_hViewModel)) and 0xFFF) - 1) * 16)

        try {
            (255F / (CHAMS_BRIGHTNESS/10F)) //Should cause an error in try instead of writing invalids, might fix crash error
            csgoEXE[ClientVModEnt + 0x70] = Color((255F / (CHAMS_BRIGHTNESS/10F)).toInt(), 0, 0, 1.0).red.toByte()
            csgoEXE[ClientVModEnt + 0x71] = Color(0, (255F / (CHAMS_BRIGHTNESS/10F)).toInt(), 0, 1.0).green.toByte()
            csgoEXE[ClientVModEnt + 0x72] = Color(0, 0, (255F / (CHAMS_BRIGHTNESS/10F)).toInt(), 1.0).blue.toByte()
        }
        catch (ex: ArithmeticException) { //Divide by 0 catch ? //might be in wrong location, but hasn't happened since, guessing chamsbrightness/10 rounds down to 0
            csgoEXE[ClientVModEnt + 0x70] = Color(255, 0, 0, 1.0).red.toByte()
            csgoEXE[ClientVModEnt + 0x71] = Color(0, 255, 0, 1.0).green.toByte()
            csgoEXE[ClientVModEnt + 0x72] = Color(0, 0, 255, 1.0).blue.toByte()
        }

        //Set cvar
        engineDLL[dwModelAmbientMin] = floatToIntBits(CHAMS_BRIGHTNESS.toFloat()) xor (engineDLL.address + dwModelAmbientMin - 0x2C).toInt()

        //Not exhaustive @warning
        when (it.type) {
            EntityType.CCSPlayer -> {
                if (entity.dead() || entity == me || (!SHOW_DORMANT && entity.dormant())) return@body false

                val entityTeam = entity.team()
                val team = !DANGER_ZONE && myTeam == entityTeam

                if (SHOW_ENEMIES && !team) {
                    if (CHAMS_SHOW_HEALTH) {
                        entity.chams(Color((255 - 2.55*entity.health()).toInt(), (2.55*entity.health()).toInt(), 0, 1.0))
                    }
                    else {
                        entity.chams(CHAMS_ESP_COLOR)
                    }
                } else if (SHOW_TEAM && team) {
                    entity.chams(TEAM_COLOR)
                }
            }

            EntityType.CPlantedC4, EntityType.CC4 -> if (SHOW_BOMB) {
                entity.chams(BOMB_COLOR)
            }

            else ->
                if (SHOW_WEAPONS && it.type.weapon)
                    entity.chams(WEAPON_COLOR)
                else if (SHOW_GRENADES && it.type.grenade)
                    entity.chams(GRENADE_COLOR)
        }
        return@body false
    }
}

private fun Entity.chams(color: Color) {
    if (CHAMS_ESP) {
        csgoEXE[this + 0x70] = color.red.toByte()
        csgoEXE[this + 0x71] = color.green.toByte()
        csgoEXE[this + 0x72] = color.blue.toByte()
    }
}