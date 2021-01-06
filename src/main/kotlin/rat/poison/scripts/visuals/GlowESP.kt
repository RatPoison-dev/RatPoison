package rat.poison.scripts.visuals

import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.overlay.glowTime
import rat.poison.scripts.aim.findTarget
import rat.poison.scripts.aim.meCurWep
import rat.poison.scripts.aim.meDead
import rat.poison.scripts.aim.target
import rat.poison.scripts.bombState
import rat.poison.settings.DANGER_ZONE
import rat.poison.utils.MedPriority

import java.util.concurrent.TimeUnit
import kotlin.system.measureNanoTime

internal fun glowEspEvery() = MedPriority.every(100, true, inGameCheck = true) {
	if (!curSettings.bool["GLOW_ESP"] || !curSettings.bool["ENABLE_ESP"] || (curSettings.bool["GLOW_ESP_DEAD"] && !meDead)) return@every

	glowTime = TimeUnit.NANOSECONDS.convert(measureNanoTime {
		val currentAngle = clientState.angle()
		val position = me.position()

		if (!meCurWep.knife && meCurWep != Weapons.ZEUS_X27) {
			if (curSettings.bool["ENABLE_AIM"]) {
				if (curSettings.bool["GLOW_SHOW_TARGET"] && target == -1L) {
					val curTarg = findTarget(position, currentAngle, false, visCheck = !curSettings.bool["FORCE_AIM_THROUGH_WALLS"])
					espTARGET = if (curTarg > 0) {
						curTarg
					} else {
						-1
					}
				} else if (curSettings.bool["GLOW_SHOW_TARGET"]) {
					espTARGET = target
				}
			}
		}
		currentAngle.release()
		position.release()

		val bomb: Entity = entityByType(EntityType.CC4)?.entity ?: -1L
		val bEnt = bomb.carrier()

		val showTarget = curSettings.bool["GLOW_SHOW_TARGET"]
		val showEnemies = curSettings.bool["GLOW_SHOW_ENEMIES"]
		val showTeam = curSettings.bool["GLOW_SHOW_TEAM"]
		val glowHealth = curSettings.bool["GLOW_SHOW_HEALTH"]
		val showBomb = curSettings.bool["GLOW_SHOW_BOMB"]
		val showBombCarrier = curSettings.bool["GLOW_SHOW_BOMB_CARRIER"]
		val showWeapons = curSettings.bool["GLOW_SHOW_WEAPONS"]
		val showGrenades = curSettings.bool["GLOW_SHOW_GRENADES"]

		val meTeam = me.team()
		forEntities {
			val entity = it.entity
			if (entity <= 0 || me == entity || entity.dormant()) return@forEntities

			val glowAddress = it.glowAddress
			if (glowAddress <= 0) return@forEntities

			if (curSettings.bool["GLOW_ESP_AUDIBLE"] && !inFootsteps(entity)) return@forEntities

			var health = 0
			var color = "NIL"
			var glowType = -1

			when (it.type) {
				EntityType.CCSPlayer -> {
					if (entity.dead()) return@forEntities

					val entityTeam = entity.team()
					val onTeam = !DANGER_ZONE && meTeam == entityTeam
					health = entity.health()

					if (showTarget && entity == espTARGET && espTARGET != -1L) {
						glowType = curSettings["GLOW_TARGET_TYPE"].toGlowNum()
						color = "GLOW_TARGET_COLOR"
					} else if (showEnemies && !onTeam) {
						glowType = curSettings["GLOW_ENEMY_TYPE"].toGlowNum()
						color = when (bEnt > 0 && bEnt == entity && showBombCarrier) {
							true -> { glowType = curSettings["GLOW_BOMB_CARRIER_TYPE"].toGlowNum(); "GLOW_BOMB_CARRIER_COLOR" }
							else -> when (glowHealth) {
								true -> "GLOW_HEALTH"
								else -> "GLOW_ENEMY_COLOR"
							}
						}
					} else if (showTeam && onTeam) {
						glowType = curSettings["GLOW_TEAMMATE_TYPE"].toGlowNum()
						color = when (bEnt > 0 && bEnt == entity && showBombCarrier) {
							true -> { glowType = curSettings["GLOW_BOMB_CARRIER_TYPE"].toGlowNum(); "GLOW_BOMB_CARRIER_COLOR" }
							else -> when (glowHealth) {
								true -> "GLOW_HEALTH"
								else -> "GLOW_TEAM_COLOR"
							}
						}
					}
				}

				EntityType.CPlantedC4, EntityType.CC4 -> if (showBomb) {
					glowType = curSettings["GLOW_BOMB_TYPE"].toGlowNum()
					color = when (curSettings.bool["GLOW_BOMB_ADAPTIVE"]) {
						true -> if (((bombState.timeLeftToExplode > 10) || (bombState.gettingDefused && bombState.canDefuse)) && bombState.planted) {
							"GLOW_BOMB_ADAPTIVE_CAN_DEFUSE"
						} else if (((bombState.timeLeftToExplode < 5) || (bombState.gettingDefused && !bombState.canDefuse)) && bombState.planted) {
							"GLOW_BOMB_ADAPTIVE_CANT_DEFUSE"
						} else if (bombState.planted && bombState.timeLeftToExplode < 10 && bombState.timeLeftToExplode > 5) {
							"GLOW_BOMB_ADAPTIVE_LITTLE_TIME"
						} else {
							"GLOW_BOMB_COLOR"
						}
						false -> "GLOW_BOMB_COLOR"
					}
				}

				else ->
					if (showWeapons && it.type.weapon) {
						glowType = curSettings["GLOW_WEAPON_TYPE"].toGlowNum()
						color = "GLOW_WEAPON_COLOR"
					} else if (showGrenades && it.type.grenade) {
						glowType = curSettings["GLOW_GRENADE_TYPE"].toGlowNum()
						color = "GLOW_GRENADE_COLOR"
					}
			}

			if (color != "NIL") {
				if (curSettings.bool["GLOW_SMOKE_CHECK"] && lineThroughSmoke(entity)) {
					glowAddress.glow(curSettings.color[color], -1)
					return@forEntities
				}

				if (color == "GLOW_HEALTH") {
					glowAddress.glow(Color((255 - 2.55 * health).toInt(), (2.55 * health).toInt(), 0, 1.0), glowType)
				} else {
					glowAddress.glow(curSettings.color[color], glowType)
				}
			} else {
				glowAddress.glow(DEFAULT_GLOW_COLOR, -1)
			}

			return@forEntities
		}
	}, TimeUnit.NANOSECONDS)
}

private val DEFAULT_GLOW_COLOR = Color(255, 255, 255, 1.0)