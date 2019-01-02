/*
 * Charlatano: Free and open-source (FOSS) cheat for CS:GO/CS:CO
 * Copyright (C) 2017 - Thomas G. P. Nappo, Jonathan Beaudoin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.charlatano.scripts.esp

import com.charlatano.game.CSGO.csgoEXE
import com.charlatano.game.Color
import com.charlatano.game.entity.*
import com.charlatano.game.forEntities
import com.charlatano.game.me
import com.charlatano.settings.*
import com.charlatano.utils.every

internal fun chamsEsp() = every(4) {
    if (!CHAMS_ESP) {

        return@every
    }

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