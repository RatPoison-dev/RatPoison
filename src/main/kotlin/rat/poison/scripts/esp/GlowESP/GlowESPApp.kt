package rat.poison.scripts.esp.GlowESP

import rat.poison.*
import rat.poison.game.*
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.*
import rat.poison.game.netvars.NetVarOffsets.m_iDefaultFov
import rat.poison.game.netvars.NetVarOffsets.m_iFOV
import rat.poison.game.netvars.NetVarOffsets.m_zoomLevel
import rat.poison.scripts.aim.findTarget
import rat.poison.scripts.aim.target
import rat.poison.scripts.esp.glow
import rat.poison.scripts.esp.glowTarget
import rat.poison.settings.*

internal fun glowEspApp() = App {
	if (!curSettings["GLOW_ESP"].strToBool() || !curSettings["ENABLE_ESP"].strToBool() || me.dead()) return@App

	val currentAngle = clientState.angle()
	val position = me.position()
	val meWep = me.weapon()

	glowTarget.set(-1L)

	if (!meWep.knife && meWep != Weapons.ZEUS_X27) {
		if (curSettings["ENABLE_AIM"].strToBool()) {
			if (curSettings["GLOW_SHOW_TARGET"].strToBool() && target == -1L) {
				val curTarg = findTarget(position, currentAngle, false, visCheck = !curSettings["FORCE_AIM_THROUGH_WALLS"].strToBool())
				if (curTarg >= 0) {
					glowTarget.set(curTarg)
				}
			} else if (curSettings["GLOW_SHOW_TARGET"].strToBool()) {
				glowTarget.set(target)
			}
		}
	}

	val bomb: Entity = entityByType(EntityType.CC4)?.entity ?: -1L
	val bEnt = bomb.carrier()

	val meTeam = me.team()
	forEntities body@{
		val entity = it.entity
		if (entity <= 0 || me == entity || entity.dormant()) return@body false

		val glowAddress = it.glowAddress
		if (glowAddress <= 0) return@body false

		var color = ""

		when (it.type) {
			EntityType.CCSPlayer -> {
				if (entity.dead()) return@body false

				val entityTeam = entity.team()
				val team = !DANGER_ZONE && meTeam == entityTeam

				if (curSettings["GLOW_SHOW_TARGET"].strToBool() && it.entity == glowTarget.get() && glowTarget.get() != -1L) {
					color = "GLOW_HIGHLIGHT_COLOR"
				} else if (curSettings["GLOW_SHOW_ENEMIES"].strToBool() && !team) {
					color = when (bEnt >= 0 && bEnt == entity && curSettings["GLOW_SHOW_BOMB_CARRIER"].strToBool()) {
						true -> "GLOW_BOMB_CARRIER_COLOR"
						false -> "GLOW_ENEMY_COLOR"
					}
				} else if (curSettings["GLOW_SHOW_TEAM"].strToBool() && team) {
					color = when (bEnt >= 0 && bEnt == entity && curSettings["GLOW_SHOW_BOMB_CARRIER"].strToBool()) {
						true -> "GLOW_BOMB_CARRIER_COLOR"
						false -> "GLOW_TEAM_COLOR"
					}
				}
			}

			EntityType.CPlantedC4, EntityType.CC4 -> if (curSettings["GLOW_SHOW_BOMB"].strToBool()) {
				color = "GLOW_BOMB_COLOR"
			}

			else ->
				if (curSettings["GLOW_SHOW_WEAPONS"].strToBool() && it.type.weapon) {
					color = "GLOW_WEAPON_COLOR"
				} else if (curSettings["GLOW_SHOW_GRENADES"].strToBool() && it.type.grenade)
					color = "GLOW_GRENADE_COLOR"
		}

		if (color != "") {
			glowAddress.glow(curSettings[color].strToColor(), entity.spotted())
		}

		return@body false
	}
}