package rat.poison.scripts.esp.GlowESP

import rat.poison.App
import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.glowTime
import rat.poison.scripts.aim.findTarget
import rat.poison.scripts.aim.target
import rat.poison.scripts.esp.glow
import rat.poison.scripts.esp.glowTarget
import rat.poison.settings.DANGER_ZONE
import rat.poison.utils.varUtil.strToBool
import rat.poison.utils.varUtil.strToColor
import java.util.concurrent.TimeUnit
import kotlin.system.measureNanoTime

internal fun glowEspApp() = App {
	glowTime = TimeUnit.NANOSECONDS.convert(measureNanoTime {
		if (!curSettings["GLOW_ESP"].strToBool() || !curSettings["ENABLE_ESP"].strToBool()) return@App

		val currentAngle = clientState.angle()
		val position = me.position()
		val meWep = me.weapon()

		glowTarget = -1L

		if (!meWep.knife && meWep != Weapons.ZEUS_X27) {
			if (curSettings["ENABLE_AIM"].strToBool()) {
				if (curSettings["GLOW_SHOW_TARGET"].strToBool() && target == -1L) {
					val curTarg = findTarget(position, currentAngle, false, visCheck = !curSettings["FORCE_AIM_THROUGH_WALLS"].strToBool())
					if (curTarg >= 0) {
						glowTarget = curTarg
					}
				} else if (curSettings["GLOW_SHOW_TARGET"].strToBool()) {
					glowTarget = target
				}
			}
		}

		val bomb: Entity = entityByType(EntityType.CC4)?.entity ?: -1L
		val bEnt = bomb.carrier()

		val showTarget = curSettings["GLOW_SHOW_TARGET"].strToBool()
		val showEnemies = curSettings["GLOW_SHOW_ENEMIES"].strToBool()
		val showTeam = curSettings["GLOW_SHOW_TEAM"].strToBool()
		val glowHealth = curSettings["GLOW_SHOW_HEALTH"].strToBool()
		val showBomb = curSettings["GLOW_SHOW_BOMB"].strToBool()
		val showBombCarrier = curSettings["GLOW_SHOW_BOMB_CARRIER"].strToBool()
		val showWeapons = curSettings["GLOW_SHOW_WEAPONS"].strToBool()
		val showGrenades = curSettings["GLOW_SHOW_GRENADES"].strToBool()

		val meTeam = me.team()
		forEntities body@{
			val entity = it.entity
			if (entity <= 0 || me == entity || entity.dormant()) return@body false

			val glowAddress = it.glowAddress
			if (glowAddress <= 0) return@body false

			var health = 0
			var color = ""

			when (it.type) {
				EntityType.CCSPlayer -> {
					if (entity.dead()) return@body false

					val entityTeam = entity.team()
					val team = !DANGER_ZONE && meTeam == entityTeam
					health = entity.health()

					if (showTarget && it.entity == glowTarget && glowTarget != -1L) {
						color = "GLOW_HIGHLIGHT_COLOR"
					} else if (showEnemies && !team) {
						color = when (bEnt >= 0 && bEnt == entity && showBombCarrier) {
							true -> "GLOW_BOMB_CARRIER_COLOR"
							else -> when (glowHealth) {
								true -> "GLOW_HEALTH"
								else -> "GLOW_ENEMY_COLOR"
							}
						}
					} else if (showTeam && team) {
						color = when (bEnt >= 0 && bEnt == entity && showBombCarrier) {
							true -> "GLOW_BOMB_CARRIER_COLOR"
							else -> when (glowHealth) {
								true -> "GLOW_HEALTH"
								else -> "GLOW_TEAM_COLOR"
							}
						}
					}
				}

				EntityType.CPlantedC4, EntityType.CC4 -> if (showBomb) {
					color = "GLOW_BOMB_COLOR"
				}

				else ->
					if (showWeapons && it.type.weapon) {
						color = "GLOW_WEAPON_COLOR"
					} else if (showGrenades && it.type.grenade) {
						color = "GLOW_GRENADE_COLOR"
					}
			}

			if (color != "") {
				if (color == "GLOW_HEALTH") {
					glowAddress.glow(Color((255 - 2.55 * health).toInt(), (2.55 * health).toInt(), 0, 1.0), entity.spotted())
				} else {
					glowAddress.glow(curSettings[color].strToColor(), entity.spotted())
				}
			}

			return@body false
		}
	}, TimeUnit.NANOSECONDS)
}