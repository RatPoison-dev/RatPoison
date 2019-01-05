package rat.poison.scripts.esp

import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.CSGO.engineDLL
import rat.poison.game.Color
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.game.offsets.EngineOffsets.dwModelAmbientMin
import rat.poison.settings.*
import rat.poison.utils.every

internal fun chamsEsp() = every(4) {
    if ((!CHAMS_ESP || !ENABLE_ESP)) return@every

    val myTeam = me.team()

    forEntities body@ {
        val entity = it.entity
        if (entity <= 0 || me == entity) return@body false

        val glowAddress = it.glowAddress
        if (glowAddress <= 0) return@body false

        //Not exhaustive @warning
        when (it.type) {
            EntityType.CCSPlayer -> {
                if (entity.dead() || (!SHOW_DORMANT && entity.dormant())) return@body false

                val entityTeam = entity.team()
                val team = !DANGER_ZONE && myTeam == entityTeam

                if (SHOW_ENEMIES && !team) {
                    entity.chams(CHAMS_ESP_COLOR)
                } else if (SHOW_TEAM && team) {
                    entity.chams(TEAM_COLOR)
                }
            }

            EntityType.CPlantedC4, EntityType.CC4 -> if (SHOW_BOMB) {
                entity.chams(BOMB_COLOR)
            }
        }
        return@body false
    }
}

private fun Entity.chams(color: Color) {
    if (CHAMS_ESP) {
        csgoEXE[this + 0x70] = color.red.toByte()
        csgoEXE[this + 0x71] = color.green.toByte()
        csgoEXE[this + 0x72] = color.blue.toByte()

        //Chams brightness, possibly move to less looped call location
        engineDLL[dwModelAmbientMin] = CHAMS_BRIGHTNESS.toFloat().hashCode() xor (engineDLL.address + dwModelAmbientMin - 0x2C).toInt()
    }
}