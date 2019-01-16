package rat.poison.scripts.esp

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import rat.poison.App
import rat.poison.game.CSGO
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.game.worldToScreen
import rat.poison.settings.*
import rat.poison.utils.Vector

//Add radius var and oval toggle

internal fun miscEsp() = App {
    if (!ENABLE_ESP || MENUTOG || (!ENEMY_INDICATOR)) return@App

    forEntities(EntityType.ccsPlayer) {
        val entity = it.entity
        if (entity == me || entity.dead() || entity.dormant()) return@forEntities false

        if (entity.team() != me.team()) { //Enemy team only
            val screenpos = Vector()

            val entitypos = entity.position() //convert to v3?
            worldToScreen(entitypos, screenpos)
            //val screenposv3 = Vector3(screenpos.x.toFloat(), screenpos.y.toFloat(), 0F)
            //val indicatorpos = screenposv3
            val indicatorpos = Vector3(screenpos.x.toFloat(), screenpos.y.toFloat(), 0F)
            /*val rotation =*/ indicatorPosition(indicatorpos, indicatorpos)

            shapeRenderer.apply {
                begin()
                if (ENEMY_INDICATOR) { //Redundant for now, but adding more options
                    set(ShapeRenderer.ShapeType.Filled)
                    color = Color(255F, 0F, 0F, .5F)
                    circle(indicatorpos.x - 10, indicatorpos.y - 10, 10F)
                    color = Color(255F, 255F, 255F, 1F)
                    set(ShapeRenderer.ShapeType.Line)
                }
                end()
            }
        }
        false
    }
}

fun indicatorPosition(screenPos: Vector3, indicatorPos: Vector3): Float {
    val centerX = CSGO.gameWidth /2F
    val centerY = CSGO.gameHeight /2F

    val d = Vector2.dst(screenPos.x, screenPos.y, centerX, centerY)

    //val ry = CSGO.gameHeight / 3 / d
    //val rx = ry //why? gameWidth / 3 / d oval

    val r = CSGO.gameHeight / 3 / d

    indicatorPos.x = r * screenPos.x + (1 - r) * centerX
    indicatorPos.y = r * screenPos.y + (1 - r) * centerY

    return MathUtils.atan2(screenPos.x - centerX, screenPos.y - centerY)
}