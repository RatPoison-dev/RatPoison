package rat.poison.scripts.esp

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.game.hooks.defuseKitEntities
import rat.poison.overlay.App
import rat.poison.settings.DANGER_ZONE
import rat.poison.settings.MENUTOG
import rat.poison.utils.Vector
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.strToColor
import rat.poison.utils.notInGame

fun snapLines() = App {
    if (!curSettings["ENABLE_SNAPLINES"].strToBool() || !curSettings["ENABLE_ESP"].strToBool() || MENUTOG || notInGame) return@App

    val bomb: Entity = entityByType(EntityType.CC4)?.entity ?: -1L
    val bEnt = bomb.carrier()

    if (curSettings["SNAPLINES_DEFUSE_KITS"].strToBool()) {
        val tmp = defuseKitEntities

        tmp.forEachIndexed { _, entity ->
            val entPos = entity.position()
            val vec = Vector()

            if (!(entPos.x == 0.0 && entPos.y == 0.0 && entPos.z == 0.0)) {

                shapeRenderer.apply {
                    if (shapeRenderer.isDrawing) {
                        end()
                    }

                    begin()

                    val drawColor = curSettings["SNAPLINES_DEFUSE_KIT_COLOR"].strToColor()
                    color = Color(drawColor.red/255F, drawColor.green/255F, drawColor.blue/255F, .5F)

                    set(ShapeRenderer.ShapeType.Filled)
                    if (worldToScreen(entPos, vec)) { //Onscreen
                        rectLine(CSGO.gameWidth / 2F, CSGO.gameHeight / 4F, vec.x.toFloat(), vec.y.toFloat(), curSettings["SNAPLINES_WIDTH"].toFloat())
                    } else { //Offscreen
                        rectLine(CSGO.gameWidth / 2F, CSGO.gameHeight / 4F, vec.x.toFloat(), -vec.y.toFloat(), curSettings["SNAPLINES_WIDTH"].toFloat())
                    }
                    set(ShapeRenderer.ShapeType.Line)

                    end()
                }
            }
        }
    }

    forEntities {
        val entity = it.entity
        var colStr = ""

        when (it.type) {
            EntityType.CCSPlayer -> {

                val dormCheck = (entity.dormant() && !DANGER_ZONE)
                val onTeam = me.team() == entity.team()
                val enemyCheck = ((curSettings["SNAPLINES_ENEMIES"].strToBool() && !onTeam) && !DANGER_ZONE)
                val teamCheck = ((curSettings["SNAPLINES_TEAMMATES"].strToBool() && onTeam) && !DANGER_ZONE)

                if (me <= 0 || entity == me || dormCheck || entity.dead()) return@forEntities

                if (bEnt >= 0 && bEnt == entity) { //This is the bomb carrier
                    if (enemyCheck) {
                        colStr = when(curSettings["SNAPLINES_BOMB_CARRIER"].strToBool()) {
                            true -> "SNAPLINES_BOMB_CARRIER_COLOR"
                            false -> "SNAPLINES_ENEMY_COLOR"
                        }
                    } else if (teamCheck) {
                        colStr = when(curSettings["SNAPLINES_BOMB_CARRIER"].strToBool()) {
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

            EntityType.CPlantedC4, EntityType.CC4 -> if (curSettings["SNAPLINES_BOMB"].strToBool()) {
                colStr = "SNAPLINES_BOMB_COLOR"
            }

            else ->
                if (curSettings["SNAPLINES_WEAPONS"].strToBool() && it.type.weapon) {
                    colStr = "SNAPLINES_WEAPON_COLOR"
                }
        }

        if (colStr.isBlank()) return@forEntities

        shapeRenderer.apply {
            if (shapeRenderer.isDrawing) {
                end()
            }

            begin()

            val drawColor = curSettings[colStr].strToColor()
            color = Color(drawColor.red/255F, drawColor.green/255F, drawColor.blue/255F, .5F)

            val entPos = entity.absPosition()
            val vec = Vector()

            if ((entPos.x == 0.0 && entPos.y == 0.0 && entPos.z == 0.0)) return@forEntities

            set(ShapeRenderer.ShapeType.Filled)
            if (worldToScreen(entPos, vec)) { //Onscreen
                rectLine(CSGO.gameWidth / 2F, CSGO.gameHeight / 4F, vec.x.toFloat(), vec.y.toFloat(), curSettings["SNAPLINES_WIDTH"].toFloat())
            } else { //Offscreen
                rectLine(CSGO.gameWidth / 2F, CSGO.gameHeight / 4F, vec.x.toFloat(), -vec.y.toFloat(), curSettings["SNAPLINES_WIDTH"].toFloat())
            }
            set(ShapeRenderer.ShapeType.Line)

            end()
        }
    }
}