package rat.poison.scripts.esp

import com.badlogic.gdx.graphics.Color
import rat.poison.App
import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.game.entity.absPosition
import rat.poison.game.entity.dormant
import rat.poison.game.entity.team
import rat.poison.game.forEntities
import rat.poison.settings.DANGER_ZONE
import rat.poison.settings.MENUTOG
import rat.poison.strToBool
import rat.poison.strToColor
import rat.poison.utils.Vector
import rat.poison.utils.notInGame

fun snapLines() = App {
    if (!curSettings["SNAPLINES"].strToBool() || !curSettings["ENABLE_ESP"].strToBool() || MENUTOG || notInGame || me.dead()) return@App

    val meTeam = me.team()
    forEntities(ccsPlayer) {
        val entity = it.entity

        val dormCheck = (entity.dormant() && !DANGER_ZONE)
        //val enemyCheck = ((!curSettings["BOX_SHOW_ENEMIES"].strToBool() && me.team() != entity.team()) && !DANGER_ZONE)
        val teamCheck = ((!curSettings["BOX_SHOW_TEAM"].strToBool() && meTeam == entity.team()) && !DANGER_ZONE)

        if (me <= 0 || entity == me || dormCheck || teamCheck || entity.dead()) return@forEntities false

        shapeRenderer.apply {
            if (shapeRenderer.isDrawing) {
                end()
            }

            begin()

            val drawColor = curSettings["SNAPLINES_COLOR"].strToColor()

            color = Color(drawColor.red/255F, drawColor.green/255F, drawColor.blue/255F, .5F)

            val entPos = entity.absPosition()
            val vec = Vector()

            if ((entPos.x == 0.0 && entPos.y == 0.0 && entPos.z == 0.0)) return@forEntities false

            if (worldToScreen(entPos, vec)) {
                line(CSGO.gameWidth / 2F, CSGO.gameHeight / 4F, vec.x.toFloat(), vec.y.toFloat())
            } else {
                line(CSGO.gameWidth / 2F, CSGO.gameHeight / 4F, vec.x.toFloat(), -vec.y.toFloat())
            }

            end()
        }
        false
    }
}