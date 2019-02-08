package rat.poison.scripts.esp

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import rat.poison.App
import rat.poison.App.shapeRenderer
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.game.offsets.ClientOffsets
import rat.poison.settings.*
import rat.poison.utils.Vector
import rat.poison.utils.distanceTo

//Add radius var and oval toggle

internal fun indicatorEsp() = App {
    if (!ENABLE_ESP || MENUTOG || !INDICATOR_ESP) return@App //Needed menutog/notingame/inbackground or would crash at w2s view matrix

    forEntities {
        val entity = it.entity
        if (entity == me) return@forEntities false

        when (it.type) {
            EntityType.CCSPlayer -> {
                if (entity.dead() || entity.dormant()) return@forEntities false

                if (INDICATOR_SHOW_ENEMIES && me.team() != entity.team()) {
                    w2sHandler(entity.position(), me.position().distanceTo(entity.position()), ENEMY_COLOR)
                } else if (INDICATOR_SHOW_TEAM && me.team() == entity.team()) {
                    w2sHandler(entity.position(), me.position().distanceTo(entity.position()), TEAM_COLOR)
                }
            }

            EntityType.CPlantedC4, EntityType.CC4 -> {
                if (INDICATOR_SHOW_BOMB) {
                    w2sHandler(entity.position(), me.position().distanceTo(entity.position()), BOMB_COLOR)
                }
            }

            else -> {
                if (INDICATOR_SHOW_WEAPONS && it.type.weapon) {
                    w2sHandler(entity.position(), me.position().distanceTo(entity.position()), WEAPON_COLOR)
                } else if (INDICATOR_SHOW_GRENADES && it.type.grenade) {
                    w2sHandler(entity.position(), me.position().distanceTo(entity.position()), GRENADE_COLOR)
                }
            }
        }
        false
    }
}

//calling all of these is not efficient at all

fun indicatorPosition(screenPos: Vector3, indicatorPos: Vector3): Float {
    val centerX = CSGO.gameWidth / 2F
    val centerY = CSGO.gameHeight / 2F

    val d = Vector2.dst(screenPos.x, screenPos.y, centerX, centerY)

    if (!INDICATOR_OVAL) {
        val r = CSGO.gameHeight / INDICATOR_DISTANCE.toFloat() / d

        indicatorPos.x = r * screenPos.x + (1 - r) * centerX
        indicatorPos.y = r * screenPos.y + (1 - r) * centerY
    } else {
        val ry = CSGO.gameHeight / INDICATOR_DISTANCE.toFloat() / d
        val rx = CSGO.gameWidth / INDICATOR_DISTANCE.toFloat() / d

        indicatorPos.x = rx * screenPos.x + (1 - rx) * centerX
        indicatorPos.y = ry * screenPos.y + (1 - ry) * centerY
    }

    return MathUtils.atan2(screenPos.x - centerX, screenPos.y - centerY)
}

fun w2sHandler(vector: Vector, dist: Double, drawColor: rat.poison.game.Color) {
    if (vector.x == 0.0 && vector.y == 0.0 && vector.z == 0.0) {
        return
    }

    val vOut = Vector()
    val wTest = wTest(vector)

    if (INDICATOR_SHOW_ONSCREEN && (wTest >= dist/3)) { //On screen
        vector.z *= 1.5
        worldToScreen(Vector(vector.x, vector.y, vector.z), vOut)
        shapeRenderer.apply {
            val indicatorPos = Vector3(vOut.x.toFloat(), vOut.y.toFloat(), 0F)
            indicatorPosition(indicatorPos, indicatorPos)

            begin()
            set(ShapeRenderer.ShapeType.Filled)
            color = com.badlogic.gdx.graphics.Color(drawColor.red.toFloat(), drawColor.green.toFloat(), drawColor.blue.toFloat(), .5F)
            circle(indicatorPos.x, indicatorPos.y, 10F)
            color = com.badlogic.gdx.graphics.Color(255F, 255F, 255F, 1F)
            set(ShapeRenderer.ShapeType.Line)
            end()
        }
    } else if (wTest < dist/3) {
        vector.z *= .5
        worldToScreen(Vector(vector.x, vector.y, vector.z), vOut)
        shapeRenderer.apply {
            val indicatorPos = Vector3(vOut.x.toFloat(), vOut.y.toFloat(), 0F)
            indicatorPosition(indicatorPos, indicatorPos)

            begin()
            set(ShapeRenderer.ShapeType.Filled)
            color = com.badlogic.gdx.graphics.Color(drawColor.red.toFloat(), drawColor.green.toFloat(), drawColor.blue.toFloat(), .5F)
            circle(indicatorPos.x, indicatorPos.y, 10F)
            color = com.badlogic.gdx.graphics.Color(255F, 255F, 255F, 1F)
            set(ShapeRenderer.ShapeType.Line)
            end()
        }
    }
}

private val viewMatrix = Array(4) { DoubleArray(4) }
fun wTest(from: Vector): Double { //Fails at large distances, but still indicates 'accurately'
    try {
        val vOut = Vector(0.0, 0.0)
        val buffer = CSGO.clientDLL.read(ClientOffsets.dwViewMatrix, 4 * 4 * 4)!!
        var offset = 0
        for (row in 0..3) for (col in 0..3) {
            val value = buffer.getFloat(offset.toLong())
            viewMatrix[row][col] = value.toDouble()
            offset += 4 //Changed, error but not compd
        }

        vOut.x = viewMatrix[0][0] * from.x + viewMatrix[0][1] * from.y + viewMatrix[0][2] * from.z + viewMatrix[0][3]
        vOut.y = viewMatrix[1][0] * from.x + viewMatrix[1][1] * from.y + viewMatrix[1][2] * from.z + viewMatrix[1][3]

        return viewMatrix[3][0] * from.x + viewMatrix[3][1] * from.y + viewMatrix[3][2] * from.z + viewMatrix[3][3]
    } catch (e: Exception) {
        return 0.0
    }
}