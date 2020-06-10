package rat.poison.scripts.esp.GlowESP

import rat.poison.*
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.scripts.aim.findTarget
import rat.poison.scripts.aim.target
import rat.poison.scripts.esp.glow
import rat.poison.scripts.esp.glowTarget
import rat.poison.settings.DANGER_ZONE
import java.util.concurrent.TimeUnit
import kotlin.system.measureNanoTime

internal fun glowEspApp() = App {
	glowTime = TimeUnit.NANOSECONDS.convert(measureNanoTime {
		if (!curSettings["GLOW_ESP"].strToBool() || !curSettings["ENABLE_ESP"].strToBool()) return@App

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

		val showTarget = curSettings["GLOW_SHOW_TARGET"].strToBool()
		val showEnemies = curSettings["GLOW_SHOW_ENEMIES"].strToBool()
		val showTeam = curSettings["GLOW_SHOW_TEAM"].strToBool()
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

			var color = Color(0, 0, 0)

			when (it.type) {
				EntityType.CCSPlayer -> {
					if (entity.dead()) return@body false

					val entityTeam = entity.team()
					val team = !DANGER_ZONE && meTeam == entityTeam

					if (showTarget && it.entity == glowTarget.get() && glowTarget.get() != -1L) {
						color = curSettings["GLOW_HIGHLIGHT_COLOR"].strToColor()
					} else if (showEnemies && !team) {
						color = when (bEnt >= 0 && bEnt == entity && showBombCarrier) {
							true -> curSettings["GLOW_BOMB_CARRIER_COLOR"].strToColor()
							false -> when (curSettings["GLOW_SHOW_HEALTH"].strToBool()) {
								true -> Color((255 - 2.55 * entity.health()).toInt(), (2.55 * entity.health()).toInt(), 0, 1.0)
								else -> curSettings["GLOW_ENEMY_COLOR"].strToColor()
							}
						}
					} else if (showTeam && team) {
						color = when (bEnt >= 0 && bEnt == entity && showBombCarrier) {
							true -> curSettings["GLOW_BOMB_CARRIER_COLOR"].strToColor()
							false -> when (curSettings["GLOW_SHOW_HEALTH"].strToBool()) {
								true -> Color((255 - 2.55 * entity.health()).toInt(), (2.55 * entity.health()).toInt(), 0, 1.0)
								else -> curSettings["GLOW_TEAM_COLOR"].strToColor()
							}
						}
					}
				}

				EntityType.CPlantedC4, EntityType.CC4 -> if (showBomb) {
					color = curSettings["GLOW_BOMB_COLOR"].strToColor()
				}

				else ->
					if (showWeapons && it.type.weapon) {
						color = curSettings["GLOW_WEAPON_COLOR"].strToColor()
					} else if (showGrenades && it.type.grenade)
						color = curSettings["GLOW_GRENADE_COLOR"].strToColor()
			}

			if (color != Color(0,0,0)) {
				glowAddress.glow(color, entity.spotted())
			}

			return@body false
		}
	}, TimeUnit.NANOSECONDS)
}