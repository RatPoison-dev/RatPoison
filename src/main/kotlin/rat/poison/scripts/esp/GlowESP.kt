package rat.poison.scripts.esp

import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.Color
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.settings.*
import rat.poison.utils.every

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
					glowAddress.glow(ENEMY_COLOR, entity.spotted())
				} else if (SHOW_TEAM && team) {
					glowAddress.glow(TEAM_COLOR, entity.spotted())
				}
			}
			EntityType.CPlantedC4, EntityType.CC4 -> if (SHOW_BOMB) {
				glowAddress.glow(BOMB_COLOR, entity.spotted())
			}
			else ->
				if (SHOW_WEAPONS && it.type.weapon)
					glowAddress.glow(WEAPON_COLOR, entity.spotted())
				else if (SHOW_GRENADES && it.type.grenade)
					glowAddress.glow(GRENADE_COLOR, entity.spotted())
		}
		return@body false
	}
	if (FLICKER_FREE_GLOW) {Thread.sleep(256)}
}

private fun Entity.glow(color: Color, model: Boolean) {
	csgoEXE[this + 0x4] = color.red / 255F
	csgoEXE[this + 0x8] = color.green / 255F
	csgoEXE[this + 0xC] = color.blue / 255F
	csgoEXE[this + 0x10] = color.alpha.toFloat()
	csgoEXE[this + 0x24] = true
	if (MODEL_AND_GLOW)
		csgoEXE[this + 0x2C] = model
	else
		csgoEXE[this + 0x2C] = MODEL_ESP
}