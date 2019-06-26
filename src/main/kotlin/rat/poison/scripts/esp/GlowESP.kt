package rat.poison.scripts.esp

import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.*
import rat.poison.scripts.aim.findTarget
import rat.poison.scripts.aim.target
import rat.poison.settings.*
import rat.poison.strToBool
import rat.poison.strToColor
import rat.poison.utils.every
import java.util.concurrent.atomic.AtomicLong

val glowTarget = AtomicLong(-1)

internal fun glowEsp() = every(4) {
	if (!curSettings["GLOW_ESP"]!!.strToBool() || !curSettings["ENABLE_ESP"]!!.strToBool()) return@every

	val myTeam = me.team()

	val currentAngle = clientState.angle()
	val position = me.position()

	if (curSettings["ENABLE_AIM"]!!.strToBool()) {
		if (curSettings["GLOW_SHOW_TARGET"]!!.strToBool() && target.get() == -1L) {
			val curTarg = findTarget(position, currentAngle, false)
			if (curTarg >= 0) {
				glowTarget.set(curTarg)
			} else {
				glowTarget.set(-1L)
			}
		} else if (curSettings["GLOW_SHOW_TARGET"]!!.strToBool()) {
			glowTarget.set(target.get())
		}
	} else { glowTarget.set(-1) }

	forEntities body@ {
		val entity = it.entity
		if (entity <= 0 || me == entity || entity.dormant()) return@body false

		val glowAddress = it.glowAddress
		if (glowAddress <= 0) return@body false

		when (it.type) {
			EntityType.CCSPlayer -> {
				if (entity.dead()) return@body false

				val entityTeam = entity.team()
				val team = !DANGER_ZONE && myTeam == entityTeam
				if (curSettings["GLOW_SHOW_TARGET"]!!.strToBool() && it.entity == glowTarget.get() && !me.dead() && glowTarget.get() != -1L)
				{
					glowAddress.glow(curSettings["HIGHLIGHT_COLOR"]!!.strToColor(), entity.spotted())
				} else if (curSettings["GLOW_SHOW_ENEMIES"]!!.strToBool() && !team) {
					glowAddress.glow(curSettings["ENEMY_COLOR"]!!.strToColor(), entity.spotted())
				} else if (curSettings["GLOW_SHOW_TEAM"]!!.strToBool() && team) {
					glowAddress.glow(curSettings["TEAM_COLOR"]!!.strToColor(), entity.spotted())
				}
			}

			EntityType.CPlantedC4, EntityType.CC4 -> if (curSettings["GLOW_SHOW_BOMB"]!!.strToBool()) {
				glowAddress.glow(curSettings["BOMB_COLOR"]!!.strToColor(), entity.spotted())
			}

			else ->
				if (curSettings["GLOW_SHOW_WEAPONS"]!!.strToBool() && it.type.weapon)
					glowAddress.glow(curSettings["WEAPON_COLOR"]!!.strToColor(), entity.spotted())
				else if (curSettings["GLOW_SHOW_GRENADES"]!!.strToBool() && it.type.grenade)
					glowAddress.glow(curSettings["GRENADE_COLOR"]!!.strToColor(), entity.spotted())
		}
		return@body false
	}
	if (curSettings["FLICKER_FREE_GLOW"]!!.strToBool()) {Thread.sleep(256)}
}

fun Entity.glow(color: Color, model: Boolean) {
	csgoEXE[this + 0x4] = color.red / 255F
	csgoEXE[this + 0x8] = color.green / 255F
	csgoEXE[this + 0xC] = color.blue / 255F
	csgoEXE[this + 0x10] = color.alpha.toFloat()//color.alpha.toFloat()
	csgoEXE[this + 0x24] = true //Render When Occluded
	csgoEXE[this + 0x25] = false //Render When Unoccluded

	csgoEXE[this + 0x26] = curSettings["INV_GLOW_ESP"]!!.strToBool() //Full Bloom Render

	if (curSettings["MODEL_AND_GLOW"]!!.strToBool())
		csgoEXE[this + 0x2C] = model
	else
		csgoEXE[this + 0x2C] = curSettings["MODEL_ESP"]!!.strToBool()
}