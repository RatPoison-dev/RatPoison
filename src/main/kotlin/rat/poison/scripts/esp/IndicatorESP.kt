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
import rat.poison.scripts.bombState
import rat.poison.settings.*
import rat.poison.strToBool
import rat.poison.curSettings
import rat.poison.utils.Vector
import rat.poison.utils.distanceTo
import rat.poison.utils.notInGame

//Add radius var and oval toggle

internal fun indicatorEsp() = App {
    if (!curSettings["ENABLE_ESP"]!!.strToBool() || MENUTOG || !curSettings["INDICATOR_ESP"]!!.strToBool() || notInGame) return@App //Needed menutog/notingame/inbackground or would crash at w2s view matrix

    forEntities {
        val entity = it.entity
        if (entity == me) return@forEntities false

        when (it.type) {
            EntityType.CCSPlayer -> {
                if (entity.dead() || entity.dormant()) return@forEntities false

                if (curSettings["INDICATOR_SHOW_ENEMIES"]!!.strToBool() && me.team() != entity.team()) {
                    w2sHandler(entity.position(), me.position().distanceTo(entity.position()), ENEMY_COLOR)
                } else if (curSettings["INDICATOR_SHOW_TEAM"]!!.strToBool() && me.team() == entity.team()) {
                    w2sHandler(entity.position(), me.position().distanceTo(entity.position()), TEAM_COLOR)
                }
            }

            EntityType.CPlantedC4, EntityType.CC4 -> {
                if (curSettings["INDICATOR_SHOW_BOMB"]!!.strToBool() && bombState.planted) {
                    w2sHandler(entity.position(), me.position().distanceTo(entity.position()), BOMB_COLOR)
                }
            }

            else -> {
                if (curSettings["INDICATOR_SHOW_WEAPONS"]!!.strToBool() && it.type.weapon) {
                    w2sHandler(entity.position(), me.position().distanceTo(entity.position()), WEAPON_COLOR)
                } else if (curSettings["INDICATOR_SHOW_GRENADES"]!!.strToBool() && it.type.grenade) {
                    w2sHandler(entity.position(), me.position().distanceTo(entity.position()), GRENADE_COLOR)
                }
            }
        }
        false
    }
}

fun indicatorPosition(screenPos: Vector3, indicatorPos: Vector3): Float {
    val centerX = CSGO.gameWidth / 2F
    val centerY = CSGO.gameHeight / 2F

    val d = Vector2.dst(screenPos.x, screenPos.y, centerX, centerY)

    if (!curSettings["INDICATOR_OVAL"]!!.strToBool()) {
        val r = CSGO.gameHeight / curSettings["INDICATOR_DISTANCE"].toString().toDouble().toFloat() / d

        indicatorPos.x = r * screenPos.x + (1 - r) * centerX
        indicatorPos.y = r * screenPos.y + (1 - r) * centerY
    } else {
        val ry = CSGO.gameHeight / curSettings["INDICATOR_DISTANCE"].toString().toDouble().toFloat() / d
        val rx = CSGO.gameWidth / curSettings["INDICATOR_DISTANCE"].toString().toDouble().toFloat() / d

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

    if (curSettings["INDICATOR_SHOW_ONSCREEN"]!!.strToBool() && (wTest >= dist/3)) { //On screen
        worldToScreen(Vector(vector.x, vector.y, vector.z), vOut)
        shapeRenderer.apply {
            val indicatorPos = Vector3(vOut.x.toFloat(), vOut.y.toFloat()+25F, 0F)
            val rot = 3.14//-indicatorPosition(indicatorPos, indicatorPos).toDouble()

            //Cleaner way?

            //Middle of triangle
            val p = indicatorPos.x
            val q = indicatorPos.y

            val indX = indicatorPos.x.toDouble()
            val indY = indicatorPos.y.toDouble()

            val vert1x = ((indX - p)*Math.cos(rot) - (indY+10 - q)*Math.sin(rot) + p).toFloat()//(indX*Math.cos(rot) - (indY+10)*Math.sin(rot)).toFloat()
            val vert1y = ((indX - p)*Math.sin(rot) + (indY+10 - q)*Math.cos(rot) + q).toFloat()//(indX*Math.sin(rot) + (indY+10)*Math.cos(rot)).toFloat()

            val vert2x = ((indX-10 - p)*Math.cos(rot) - (indY-10 - q)*Math.sin(rot) + p).toFloat()//((indX-10)*Math.cos(rot) - (indY-10)*Math.sin(rot)).toFloat()
            val vert2y = ((indX-10 - p)*Math.sin(rot) + (indY-10 - q)*Math.cos(rot) + q).toFloat()//((indX-10)*Math.sin(rot) + (indY-10)*Math.cos(rot)).toFloat()

            val vert3x = ((indX+10 - p)*Math.cos(rot) - (indY-10 - q)*Math.sin(rot) + p).toFloat()//((indX+10)*Math.cos(rot) - (indY+10)*Math.sin(rot)).toFloat()
            val vert3y = ((indX+10 - p)*Math.sin(rot) + (indY-10 - q)*Math.cos(rot) + q).toFloat()//((indX-10)*Math.sin(rot) + (indY-10)*Math.cos(rot)).toFloat()

            begin()
            //set(ShapeRenderer.ShapeType.Filled)
            set(ShapeRenderer.ShapeType.Filled)
            color = com.badlogic.gdx.graphics.Color(drawColor.red.toFloat(), drawColor.green.toFloat(), drawColor.blue.toFloat(), .5F)
            triangle(vert1x, vert1y, vert2x, vert2y, vert3x, vert3y)
            color = com.badlogic.gdx.graphics.Color(255F, 255F, 255F, 1F)
            set(ShapeRenderer.ShapeType.Line)
            end()
        }
    } else if (wTest < dist/3) {
        worldToScreen(Vector(vector.x, vector.y, vector.z), vOut)
        shapeRenderer.apply {
            val indicatorPos = Vector3(vOut.x.toFloat(), vOut.y.toFloat(), 0F)
            val rot = -indicatorPosition(indicatorPos, indicatorPos).toDouble()

            //Cleaner way?

            //Middle of triangle
            val p = indicatorPos.x
            val q = indicatorPos.y

            val indX = indicatorPos.x.toDouble()
            val indY = indicatorPos.y.toDouble()

            val vert1x = ((indX - p)*Math.cos(rot) - (indY+10 - q)*Math.sin(rot) + p).toFloat()//(indX*Math.cos(rot) - (indY+10)*Math.sin(rot)).toFloat()
            val vert1y = ((indX - p)*Math.sin(rot) + (indY+10 - q)*Math.cos(rot) + q).toFloat()//(indX*Math.sin(rot) + (indY+10)*Math.cos(rot)).toFloat()

            val vert2x = ((indX-10 - p)*Math.cos(rot) - (indY-10 - q)*Math.sin(rot) + p).toFloat()//((indX-10)*Math.cos(rot) - (indY-10)*Math.sin(rot)).toFloat()
            val vert2y = ((indX-10 - p)*Math.sin(rot) + (indY-10 - q)*Math.cos(rot) + q).toFloat()//((indX-10)*Math.sin(rot) + (indY-10)*Math.cos(rot)).toFloat()

            val vert3x = ((indX+10 - p)*Math.cos(rot) - (indY-10 - q)*Math.sin(rot) + p).toFloat()//((indX+10)*Math.cos(rot) - (indY+10)*Math.sin(rot)).toFloat()
            val vert3y = ((indX+10 - p)*Math.sin(rot) + (indY-10 - q)*Math.cos(rot) + q).toFloat()//((indX-10)*Math.sin(rot) + (indY-10)*Math.cos(rot)).toFloat()

            begin()
            //set(ShapeRenderer.ShapeType.Filled)
            set(ShapeRenderer.ShapeType.Filled)
            color = com.badlogic.gdx.graphics.Color(drawColor.red.toFloat(), drawColor.green.toFloat(), drawColor.blue.toFloat(), .5F)
            triangle(vert1x, vert1y, vert2x, vert2y, vert3x, vert3y)
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