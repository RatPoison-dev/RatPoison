package rat.poison.scripts.visuals

import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.scripts.aim.findTarget
import rat.poison.scripts.aim.target
import rat.poison.scripts.bombState
import rat.poison.settings.DANGER_ZONE
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.strToColor

internal fun glowEspEvery() = every(100, true) {
	if (!curSettings["GLOW_ESP"].strToBool() || !curSettings["ENABLE_ESP"].strToBool()) return@every

	val currentAngle = clientState.angle()
	val position = me.position()
	val meWep = me.weapon()

	if (!meWep.knife && meWep != Weapons.ZEUS_X27) {
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
	val showDefuseKits = curSettings["GLOW_SHOW_DEFUSE_KITS"].strToBool()

	val meTeam = me.team()
	forEntities {
		val isPlayer = it.type == EntityType.CCSPlayer
		val isWeapon = it.type.weapon
		val isDefuseKit = it.type == EntityType.CEconEntity
		val isGrenade = it.type.grenade
		val isBomb = (it.type == EntityType.CPlantedC4) || (it.type == EntityType.CC4)
		val ent = it.entity
		if (!isPlayer && !isWeapon && !isDefuseKit) return@forEntities


		if (curSettings["BOX_SMOKE_CHECK"].strToBool()) {
			if (lineThroughSmoke(ent)) {
				return@forEntities
			}
		}

		val onTeam = ent.team() == meTeam
		if (isPlayer && !DANGER_ZONE && (ent == me || ent.dormant() || ent.dead() || (!showEnemies && !onTeam) || (!showTeam && onTeam))) return@forEntities
		if (isWeapon && !showWeapons) return@forEntities
		if (isDefuseKit && !showDefuseKits) return@forEntities
		if (isGrenade && !showGrenades) return@forEntities

		val glowAddress = it.glowAddress
		if (glowAddress <= 0) return@forEntities

		var health = 0
		var color = "NIL"
		var glowType = -1

		if (isPlayer) {
			if (showTarget && ent == espTARGET && espTARGET != -1L) {
				glowType = curSettings["GLOW_TARGET_TYPE"].toGlowNum()
				color = "GLOW_TARGET_COLOR"
			} else if (showEnemies && !onTeam) {
				glowType = curSettings["GLOW_ENEMY_TYPE"].toGlowNum()
				color = when (bEnt > 0 && bEnt == ent && showBombCarrier) {
					true -> {
						glowType = curSettings["GLOW_BOMB_CARRIER_TYPE"].toGlowNum(); "GLOW_BOMB_CARRIER_COLOR"
					}
					else -> when (glowHealth) {
						true -> "GLOW_HEALTH"
						else -> "GLOW_ENEMY_COLOR"
					}
				}
			} else if (showTeam && onTeam) {
				glowType = curSettings["GLOW_TEAMMATE_TYPE"].toGlowNum()
				color = when (bEnt > 0 && bEnt == ent && showBombCarrier) {
					true -> {
						glowType = curSettings["GLOW_BOMB_CARRIER_TYPE"].toGlowNum(); "GLOW_BOMB_CARRIER_COLOR"
					}
					else -> when (glowHealth) {
						true -> "GLOW_HEALTH"
						else -> "GLOW_TEAM_COLOR"
					}
				}
			}
		}
		//if (isDefuseKit && showDefuseKits) {
		//	glowType = curSettings["GLOW_DEFUSE_KITS_TYPE"].toGlowNum()
		//	color = "GLOW_DEFUSE_KITS_COLOR"
		//}
		if (isBomb && showBomb) {
			glowType = curSettings["GLOW_BOMB_TYPE"].toGlowNum()
			color = when (curSettings["GLOW_BOMB_ADAPTIVE"].strToBool()) {
				true -> if ((bombState.timeLeftToExplode > 10) || (bombState.gettingDefused && bombState.canDefuse) && bombState.planted) {
					"GLOW_BOMB_ADAPTIVE_CAN_DEFUSE"
				} else if ((bombState.timeLeftToExplode < 5) || (bombState.gettingDefused && !bombState.canDefuse) && bombState.planted) {
					"GLOW_BOMB_ADAPTIVE_CANT_DEFUSE"
				} else if (bombState.planted && bombState.timeLeftToExplode < 10 && bombState.timeLeftToExplode > 5) {
					"GLOW_BOMB_ADAPTIVE_LITTLE_TIME"
				} else {
					"GLOW_BOMB_COLOR"
				}
				false -> "GLOW_BOMB_COLOR"
			}
		}
		if (isWeapon && showWeapons) {
			glowType = curSettings["GLOW_WEAPON_TYPE"].toGlowNum()
			color = "GLOW_WEAPON_COLOR"
		}
		if (showGrenades && isGrenade) {
			glowType = curSettings["GLOW_GRENADE_TYPE"].toGlowNum()
			color = "GLOW_GRENADE_COLOR"
		}


		if (color != "NIL") {
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
}