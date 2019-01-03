

package rat.plague.scripts.esp

import rat.plague.game.CSGO.csgoEXE
import rat.plague.game.Color
import rat.plague.game.entity.*
import rat.plague.game.forEntities
import rat.plague.game.me
import rat.plague.settings.*
import rat.plague.utils.every

internal fun glowEsp() = every(4) {
	if (!GLOW_ESP || !ENABLE_ESP) return@every
	
	val myTeam = me.team()
	
	forEntities body@ {
		val entity = it.entity
		if (entity <= 0 || me == entity) return@body false
		
		val glowAddress = it.glowAddress
		if (glowAddress <= 0) return@body false
		
		when (it.type) {
			EntityType.CCSPlayer -> {
				if (entity.dead() || (!SHOW_DORMANT && entity.dormant())) return@body false
				
				val entityTeam = entity.team()
				val team = !DANGER_ZONE && myTeam == entityTeam
				if (SHOW_ENEMIES && !team) {
					glowAddress.glow(ENEMY_COLOR)
				} else if (SHOW_TEAM && team) {
					glowAddress.glow(TEAM_COLOR)
				}
			}
			EntityType.CPlantedC4, EntityType.CC4 -> if (SHOW_BOMB) {
				glowAddress.glow(BOMB_COLOR)
			}
			else ->
				if (SHOW_WEAPONS && it.type.weapon) glowAddress.glow(WEAPON_COLOR)
				else if (SHOW_GRENADES && it.type.grenade) glowAddress.glow(GRENADE_COLOR)
		}
		
		return@body false
	}
}

private fun Entity.glow(color: Color) {
	csgoEXE[this + 0x4] = color.red / 255F
	csgoEXE[this + 0x8] = color.green / 255F
	csgoEXE[this + 0xC] = color.blue / 255F
	csgoEXE[this + 0x10] = color.alpha.toFloat()
	csgoEXE[this + 0x24] = true
	csgoEXE[this + 0x2C] = MODEL_ESP
}