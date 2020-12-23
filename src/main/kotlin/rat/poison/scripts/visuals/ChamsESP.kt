package rat.poison.scripts.visuals

import com.badlogic.gdx.math.MathUtils.clamp
import org.jire.kna.set
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
import rat.poison.scripts.aim.meCurWep
import rat.poison.scripts.aim.meDead
import rat.poison.scripts.aim.target
import rat.poison.settings.DANGER_ZONE
import rat.poison.utils.every
import rat.poison.utils.extensions.uint
import java.lang.Float.floatToIntBits

fun chamsEsp() = every(100, true, inGameCheck = true) {
    if (!curSettings.bool["CHAMS_ESP"] || !curSettings.bool["ENABLE_ESP"]) return@every

    val myTeam = me.team()

    val brightnessCounter = if (curSettings.int["CHAMS_BRIGHTNESS"] > 0) {
        (255F / (curSettings.int["CHAMS_BRIGHTNESS"] / 10F)).toInt()
    } else {
        255
    }

    if (me > 0L && !meDead) {
        //Edit playermodel to counter weapon brightness
        val clientVModEnt = csgoEXE.uint(clientDLL.address + dwEntityList + (((csgoEXE.uint(csgoEXE.uint(clientDLL.address + dwLocalPlayer) + m_hViewModel)) and 0xFFF) - 1) * 16)

        //Set VMod
        val clientMColor = if (curSettings.bool["CHAMS_SHOW_SELF"]) {
             curSettings.color["CHAMS_SELF_COLOR"]
        } else {
            Color(brightnessCounter, brightnessCounter, brightnessCounter, 1.0)
        }

        if (clientVModEnt > 0) {
            csgoEXE[clientVModEnt + 0x70] = clientMColor.red.toByte()
            csgoEXE[clientVModEnt + 0x71] = clientMColor.green.toByte()
            csgoEXE[clientVModEnt + 0x72] = clientMColor.blue.toByte()
        }
    }

    //Set Cvar
    engineDLL[dwModelAmbientMin] = floatToIntBits(curSettings.int["CHAMS_BRIGHTNESS"].toFloat()) xor (engineDLL.address + dwModelAmbientMin - 0x2C).toInt()

    val currentAngle = clientState.angle()
    val position = me.position()

    if (!meCurWep.knife && meCurWep != Weapons.ZEUS_X27) {
        if (curSettings.bool["ENABLE_AIM"]) {
            if (curSettings.bool["CHAMS_SHOW_TARGET"] && target == -1L) {
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

    forEntities(EntityType.CCSPlayer) {
        val entity = it.entity
        if (entity <= 0 || entity == me || entity.dormant() || entity.dead()) return@forEntities

        val glowAddress = it.glowAddress
        if (glowAddress <= 0) return@forEntities

        val entityTeam = entity.team()
        val onTeam = !DANGER_ZONE && myTeam == entityTeam

        if (curSettings.bool["CHAMS_SHOW_TARGET"] && entity == espTARGET && espTARGET > 0L && !espTARGET.dead()) {
            entity.chams(curSettings.color["CHAMS_TARGET_COLOR"])
        } else if (curSettings.bool["CHAMS_SHOW_ENEMIES"] && !onTeam) { //Show enemies & is enemy
            if (curSettings.bool["CHAMS_SHOW_HEALTH"]) {
                entity.chams(Color((255 - 2.55 * clamp(entity.health(), 0, 100)).toInt(), (2.55 * clamp(entity.health(), 0, 100)).toInt(), 0, 1.0))
            } else {
                entity.chams(curSettings.color["CHAMS_ENEMY_COLOR"])
            }
        } else if (!curSettings.bool["CHAMS_SHOW_ENEMIES"] && !onTeam) { //Not show enemies
            entity.chams(Color(brightnessCounter, brightnessCounter, brightnessCounter, 1.0))
        } else if (curSettings.bool["CHAMS_SHOW_TEAM"] && onTeam) { //Show team & is team
            entity.chams(curSettings.color["CHAMS_TEAM_COLOR"])
        }
        else {
            entity.chams(Color(brightnessCounter, brightnessCounter, brightnessCounter, 1.0))
        }

        return@forEntities
    }
}