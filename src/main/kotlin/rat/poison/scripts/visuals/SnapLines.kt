package rat.poison.scripts.visuals

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.overlay.App
import rat.poison.settings.DANGER_ZONE
import rat.poison.utils.Vector
import rat.poison.utils.inGame

private const val emptyString = ""
private val positionVector = Vector()
private const val id = "snaplines"
//TODO god fix this eventually g
private val w2sRet = Vector()
fun snapLines() = App {
    if (!curSettings.bool["ENABLE_SNAPLINES"] || !curSettings.bool["ENABLE_ESP"] || !inGame) return@App

    val bomb: Entity = entityByType(EntityType.CC4)?.entity ?: -1L
    val bEnt = bomb.carrier()

    forEntities(EntityType.CCSPlayer, EntityType.CPlantedC4, EntityType.CC4, EntityType.CEconEntity, iterateWeapons = true, identifier = id) {
        val entity = it.entity
        var colStr = ""

        if (curSettings.bool["SNAPLINES_SMOKE_CHECK"] && lineThroughSmoke(entity)) return@forEntities

        when (it.type) {
            EntityType.CCSPlayer -> {
                val dormCheck = entity.dormant()
                val onTeam = !DANGER_ZONE && meTeam == entity.team()
                val enemyCheck = (curSettings.bool["SNAPLINES_ENEMIES"] && !onTeam)
                val teamCheck = (curSettings.bool["SNAPLINES_TEAMMATES"] && onTeam)

                if (me <= 0 || entity == me || dormCheck || entity.dead()) return@forEntities

                if (bEnt > 0 && bEnt == entity) { //This is the bomb carrier
                    if (enemyCheck) {
                        colStr = when(curSettings.bool["SNAPLINES_BOMB_CARRIER"]) {
                            true -> "SNAPLINES_BOMB_CARRIER_COLOR"
                            false -> "SNAPLINES_ENEMY_COLOR"
                        }
                    } else if (teamCheck) {
                        colStr = when(curSettings.bool["SNAPLINES_BOMB_CARRIER"]) {
                            true -> "SNAPLINES_BOMB_CARRIER_COLOR"
                            false -> "SNAPLINES_TEAM_COLOR"
                        }
                    }
                } else {
                    if (enemyCheck) {
                        colStr = "SNAPLINES_ENEMY_COLOR"
                    } else if (teamCheck) {
                        colStr = "SNAPLINES_TEAM_COLOR"
                    }
                }
            }

            EntityType.CPlantedC4, EntityType.CC4 -> if (curSettings.bool["SNAPLINES_BOMB"]) {
                colStr = "SNAPLINES_BOMB_COLOR"
            }

            EntityType.CEconEntity -> if (curSettings.bool["SNAPLINES_DEFUSE_KITS"]) {
                colStr = "SNAPLINES_DEFUSE_KIT_COLOR"
            }

            else ->
                if (curSettings.bool["SNAPLINES_WEAPONS"]) {
                    colStr = "SNAPLINES_WEAPON_COLOR"
                }
        }

        if (colStr == emptyString) return@forEntities

        shapeRenderer.apply {
            if (shapeRenderer.isDrawing) {
                end()
            }

            begin()

            color = curSettings.colorGDX[colStr]

            val entPos = entity.absPosition(positionVector)

            if ((entPos.x == 0F && entPos.y == 0F && entPos.z == 0F)) return@forEntities

            set(ShapeRenderer.ShapeType.Filled)
            if (worldToScreen(entPos, w2sRet)) { //Onscreen
                rectLine(CSGO.gameWidth / 2F, CSGO.gameHeight / 4F, w2sRet.x, w2sRet.y, curSettings.float["SNAPLINES_WIDTH"])
            } else { //Offscreen
                rectLine(CSGO.gameWidth / 2F, CSGO.gameHeight / 4F, w2sRet.x, -w2sRet.y, curSettings.float["SNAPLINES_WIDTH"])
            }
            set(ShapeRenderer.ShapeType.Line)

            end()
        }
    }
}