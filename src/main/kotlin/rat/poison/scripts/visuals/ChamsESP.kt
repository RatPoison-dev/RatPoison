package rat.poison.scripts.visuals

import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.CSGO.engineDLL
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.netvars.NetVarOffsets.m_hViewModel
import rat.poison.game.offsets.ClientOffsets.dwEntityList
import rat.poison.game.offsets.ClientOffsets.dwLocalPlayer
import rat.poison.game.offsets.EngineOffsets.dwModelAmbientMin
import rat.poison.scripts.aim.findTarget
import rat.poison.scripts.aim.target
import rat.poison.settings.DANGER_ZONE
import rat.poison.utils.every
import rat.poison.utils.extensions.uint
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.strToColor
import rat.poison.utils.notInGame
import java.lang.Float.floatToIntBits

//Change for entities to for entities ccsplayer

fun chamsEsp() = every(100) {
    if (!curSettings["CHAMS_ESP"].strToBool() || !curSettings["ENABLE_ESP"].strToBool() || notInGame) return@every

    val myTeam = me.team()

    val brightnessCounter = if (curSettings["CHAMS_BRIGHTNESS"].toInt() > 0) {
        (255F / (curSettings["CHAMS_BRIGHTNESS"].toInt() / 10F)).toInt()
    } else {
        255
    }

    if (me > 0L) {
        //Edit playermodel to counter weapon brightness
        val clientVModEnt = csgoEXE.uint(clientDLL.address + dwEntityList + (((csgoEXE.uint(csgoEXE.uint(clientDLL.address + dwLocalPlayer) + m_hViewModel)) and 0xFFF) - 1) * 16)

        //Set VMod
        val clientMColor = if (curSettings["CHAMS_SHOW_SELF"].strToBool()) {
             curSettings["CHAMS_SELF_COLOR"].strToColor()
        } else {
            Color(brightnessCounter, brightnessCounter, brightnessCounter, 1.0)
        }

        csgoEXE[clientVModEnt + 0x70] = clientMColor.red.toByte()
        csgoEXE[clientVModEnt + 0x71] = clientMColor.green.toByte()
        csgoEXE[clientVModEnt + 0x72] = clientMColor.blue.toByte()
    }

    //Set Cvar
    engineDLL[dwModelAmbientMin] = floatToIntBits(curSettings["CHAMS_BRIGHTNESS"].toInt().toFloat()) xor (engineDLL.address + dwModelAmbientMin - 0x2C).toInt()

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

    forEntities(EntityType.CCSPlayer) {
        val entity = it.entity
        if (entity <= 0 || entity == me || entity.dormant() || entity.dead()) return@forEntities

        val glowAddress = it.glowAddress
        if (glowAddress <= 0) return@forEntities

        val entityTeam = entity.team()
        val team = !DANGER_ZONE && myTeam == entityTeam

        if (curSettings["CHAMS_SHOW_TARGET"].strToBool() && entity == espTARGET && espTARGET != -1L) {
            entity.chams(curSettings["CHAMS_TARGET_COLOR"].strToColor())
        } else if (curSettings["CHAMS_SHOW_ENEMIES"].strToBool() && !team) { //Show enemies & is enemy
            if (curSettings["CHAMS_SHOW_HEALTH"].strToBool()) {
                entity.chams(Color((255 - 2.55 * entity.health()).toInt(), (2.55 * entity.health()).toInt(), 0, 1.0))
            } else {
                entity.chams(curSettings["CHAMS_ENEMY_COLOR"].strToColor())
            }
        } else if (!curSettings["CHAMS_SHOW_ENEMIES"].strToBool() && !team) { //Not show enemies
            entity.chams(Color(brightnessCounter, brightnessCounter, brightnessCounter, 1.0))
        } else if (curSettings["CHAMS_SHOW_TEAM"].strToBool() && team) { //Show team & is team
            entity.chams(curSettings["CHAMS_TEAM_COLOR"].strToColor())
        }
        else {
            entity.chams(Color(brightnessCounter, brightnessCounter, brightnessCounter, 1.0))
        }

        return@forEntities
    }
}

private fun Entity.chams(color: Color) {
    csgoEXE[this + 0x70] = color.red.toByte()
    csgoEXE[this + 0x71] = color.green.toByte()
    csgoEXE[this + 0x72] = color.blue.toByte()
}