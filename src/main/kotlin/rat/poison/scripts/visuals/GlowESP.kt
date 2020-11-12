package rat.poison.scripts.visuals

import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.overlay.glowTime
import rat.poison.scripts.aim.findTarget
import rat.poison.scripts.aim.meCurWep
import rat.poison.scripts.aim.target
import rat.poison.scripts.bombState
import rat.poison.settings.DANGER_ZONE
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.strToColor
import java.util.concurrent.TimeUnit
import kotlin.system.measureNanoTime

internal fun glowEspEvery() = every(100, true, inGameCheck = true) {
	if (!curSettings["GLOW_ESP"].strToBool() || !curSettings["ENABLE_ESP"].strToBool()) return@every

	glowTime = TimeUnit.NANOSECONDS.convert(measureNanoTime {
		val currentAngle = clientState.angle()
		val position = me.position()

		if (!meCurWep.knife && meCurWep != Weapons.ZEUS_X27) {
			if (curSettings["ENABLE_AIM"].strToBool()) {
				if (curSettings["GLOW_SHOW_TARGET"].strToBool() && target == -1L) {
					val curTarg = findTarget(position, currentAngle, false, visCheck = !curSettings["FORCE_AIM_THROUGH_WALLS"].strToBool())
					espTARGET = if (curTarg > 0) {
						curTarg
					} else {
						-1
					}
				} else if (curSettings["GLOW_SHOW_TARGET"].strToBool()) {
					espTARGET = target
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
		forEntities {
			val entity = it.entity
			if (entity <= 0 || me == entity || entity.dormant()) return@forEntities

			val glowAddress = it.glowAddress
			if (glowAddress <= 0) return@forEntities

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
					color = when (curSettings["GLOW_BOMB_ADAPTIVE"].strToBool()) {
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
				if (curSettings["GLOW_SMOKE_CHECK"].strToBool()) {
					if (lineThroughSmoke(entity)) {
						glowAddress.glow(curSettings[color].strToColor(), -1)
						return@forEntities
					}
				}

				if (color == "GLOW_HEALTH") {
					glowAddress.glow(Color((255 - 2.55 * health).toInt(), (2.55 * health).toInt(), 0, 1.0), glowType)
				} else {
					glowAddress.glow(curSettings[color].strToColor(), glowType)
				}
			} else {
				glowAddress.glow(Color(255, 255, 255, 1.0), -1)
			}

			return@forEntities
		}
	}, TimeUnit.NANOSECONDS)
}