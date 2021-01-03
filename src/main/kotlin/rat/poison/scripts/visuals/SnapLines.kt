package rat.poison.scripts.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.overlay.App
import rat.poison.scripts.aim.meDead
import rat.poison.settings.DANGER_ZONE
import rat.poison.utils.inGame

private val snapColor = Color()

private fun Color.set(red: Float, green: Float, blue: Float, alpha: Float) = apply {
    r = red; g = green; b = blue; a = alpha
}

//TODO god fix this eventually g
fun snapLines() = App {
    if (!curSettings.bool["ENABLE_SNAPLINES"] || !curSettings.bool["ENABLE_ESP"] || !inGame || (curSettings.bool["SNAPLINES_ESP_DEAD"] && !meDead)) return@App

    val bomb: Entity = entityByType(EntityType.CC4)?.entity ?: -1L
    val bEnt = bomb.carrier()

    val y: Float = when (curSettings["SNAPLINES_POSITION"]) {
        "DEFAULT" -> CSGO.gameHeight / 4F
        "CROSSHAIR" -> CSGO.gameHeight / 2F
        "TOP" -> CSGO.gameHeight.toFloat()
        "BOTTOM" -> 0F
        else -> 0F
    }

    if (curSettings.bool["SNAPLINES_DEFUSE_KITS"]) {
        forEntities(EntityType.CEconEntity) {
            val entPos = it.entity.position()

            if (curSettings.bool["SNAPLINES_SMOKE_CHECK"] && lineThroughSmoke(it.entity)) return@forEntities

            if (curSettings.bool["SNAPLINES_ESP_AUDIBLE"] && !inFootsteps(it.entity)) return@forEntities

            if (!(entPos.x == 0F && entPos.y == 0F && entPos.z == 0F)) {
                shapeRenderer.apply {
                    if (shapeRenderer.isDrawing) {
                        end()
                    }

                    begin()

                    val drawColor: rat.poison.game.Color = curSettings.color["SNAPLINES_DEFUSE_KIT_COLOR"]
                    color = snapColor.set(drawColor.red/255F, drawColor.green/255F, drawColor.blue/255F, .5F)

                    set(ShapeRenderer.ShapeType.Filled)
                    val vec = worldToScreen(entPos)
                    if (vec.w2s()) { //Onscreen
                        rectLine(CSGO.gameWidth / 2F, y, vec.x, vec.y, curSettings.float["SNAPLINES_WIDTH"])
                    } else { //Offscreen
                        rectLine(CSGO.gameWidth / 2F, y, vec.x, -vec.y, curSettings.float["SNAPLINES_WIDTH"])
                    }
                    vec.release()
                    set(ShapeRenderer.ShapeType.Line)

                    end()
                }
            }
    
            entPos.release()
        }
    }

    forEntities {
        val entity = it.entity
        var colStr: String? = null

        if (curSettings.bool["SNAPLINES_SMOKE_CHECK"] && lineThroughSmoke(entity)) return@forEntities

        if (curSettings.bool["SNAPLINES_ESP_AUDIBLE"] && !inFootsteps(entity)) return@forEntities

        when (it.type) {
            EntityType.CCSPlayer -> {
                val dormCheck = entity.dormant()
                val onTeam = !DANGER_ZONE && me.team() == entity.team()
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

            else ->
                if (curSettings.bool["SNAPLINES_WEAPONS"] && it.type.weapon) {
                    colStr = "SNAPLINES_WEAPON_COLOR"
                }
        }

        if (colStr == null) return@forEntities

        shapeRenderer.apply {
            if (shapeRenderer.isDrawing) {
                end()
            }

            begin()

            val drawColor: rat.poison.game.Color = curSettings.color[colStr]
            color = snapColor.set(drawColor.red/255F, drawColor.green/255F, drawColor.blue/255F, .5F)

            val entPos = entity.absPosition()
            try {
                if ((entPos.x == 0F && entPos.y == 0F && entPos.z == 0F)) {
                    return@forEntities
                }
    
                val vec = worldToScreen(entPos)
    
                set(ShapeRenderer.ShapeType.Filled)
                if (vec.w2s()) { //Onscreen
                    rectLine(
                        CSGO.gameWidth / 2F,
                        y,
                        vec.x,
                        vec.y,
                        curSettings.float["SNAPLINES_WIDTH"]
                    )
                } else { //Offscreen
                    rectLine(
                        CSGO.gameWidth / 2F,
                        y,
                        vec.x,
                        -vec.y,
                        curSettings.float["SNAPLINES_WIDTH"]
                    )
                }
                set(ShapeRenderer.ShapeType.Line)
    
                end()
            } finally {
                entPos.release()
            }
        }
    }
}