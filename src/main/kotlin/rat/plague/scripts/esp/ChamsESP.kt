package rat.plague.scripts.esp

import rat.plague.game.CSGO.csgoEXE
import rat.plague.game.Color
import rat.plague.game.entity.*
import rat.plague.game.forEntities
import rat.plague.game.me
import rat.plague.settings.*
import rat.plague.utils.every

internal fun chamsEsp() = every(4) {
    if (!CHAMS_ESP || !ENABLE_ESP) return@every

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
        csgoEXE[this + 0x73] = color.alpha.toByte()
    }
}