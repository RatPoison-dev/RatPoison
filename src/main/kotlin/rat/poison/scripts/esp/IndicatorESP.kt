package rat.poison.scripts.esp

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.game.hooks.defuseKitEntities
import rat.poison.overlay.App
import rat.poison.overlay.App.shapeRenderer
import rat.poison.settings.DANGER_ZONE
import rat.poison.settings.MENUTOG
import rat.poison.utils.Vector
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.strToColor
import rat.poison.utils.normalize
import rat.poison.utils.notInGame
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

//Whole lotta math shit here
fun indicatorEsp() = App {
    if (!curSettings["ENABLE_ESP"].strToBool() || MENUTOG || !curSettings["INDICATOR_ESP"].strToBool() || notInGame) return@App

    val bomb: Entity = entityByType(EntityType.CC4)?.entity ?: -1L
    val bEnt = bomb.carrier()

    forEntities {
        val entity = it.entity
        val onMyTeam = me.team() == entity.team()

        var color = ""

        when (it.type) {
            EntityType.CCSPlayer -> {
                if (entity.dead() || entity == me || (entity.dormant() && !DANGER_ZONE)) return@forEntities false

                if (bEnt >= 0 && bEnt == entity) { //This is the bomb carrier
                    if (curSettings["INDICATOR_SHOW_ENEMIES"].strToBool() && !onMyTeam) {
                        color = when (curSettings["INDICATOR_SHOW_BOMB_CARRIER"].strToBool()) {
                            true -> "INDICATOR_BOMB_CARRIER_COLOR"
                            false -> "INDICATOR_ENEMY_COLOR"
                        }
                    } else if (curSettings["INDICATOR_SHOW_TEAM"].strToBool() && onMyTeam) {
                        color = when (curSettings["INDICATOR_SHOW_BOMB_CARRIER"].strToBool()) {
                            true -> "INDICATOR_BOMB_CARRIER_COLOR"
                            false -> "INDICATOR_TEAM_COLOR"
                        }
                    }
                } else {
                    if (!curSettings["INDICATOR_SHOW_TEAM"].strToBool() && onMyTeam) {
                        return@forEntities false
                    } else if (!curSettings["INDICATOR_SHOW_ENEMIES"].strToBool()) {
                        return@forEntities false
                    } else {
                        color = when (!onMyTeam) {
                            true -> "INDICATOR_ENEMY_COLOR"
                            false -> "INDICATOR_TEAM_COLOR"
                        }
                    }
                }
            }

            EntityType.CPlantedC4, EntityType.CC4 -> {
                if (curSettings["INDICATOR_SHOW_BOMB"].strToBool()) {
                    color = "INDICATOR_BOMB_COLOR"
                }
            }

            else -> {
                if (curSettings["INDICATOR_SHOW_WEAPONS"].strToBool() && it.type.weapon) {
                    color = "INDICATOR_WEAPON_COLOR"
                } else if (curSettings["INDICATOR_SHOW_GRENADES"].strToBool() && it.type.grenade) {
                    color = "INDICATOR_GRENADE_COLOR"
                }
            }
        }

        if (color != "") {
            drawIndicator(entity, curSettings[color].strToColor())
        }

        false
    }

    if (curSettings["INDICATOR_SHOW_DEFUSERS"].strToBool()) {
        val tmp = defuseKitEntities
        tmp.forEachIndexed { _, entity ->
            drawIndicator(entity, curSettings["INDICATOR_DEFUSER_COLOR"].strToColor())
        }
    }
}

fun calcAngle(src: Vector, dest: Vector, vAng: Vector): Vector {
    val delta = Vector(dest.x - src.x, dest.y - src.y, dest.z - src.z)
    val angs =  Vector(Math.toDegrees(atan2(-delta.z, hypot(delta.x, delta.y))) - vAng.x, Math.toDegrees(atan2(delta.y, delta.x)) - vAng.y, 0.0)
    angs.normalize()

    return angs
}

fun angVec(ang: Vector): Vector {
    val sy = sin(ang.y / 180.0 * Math.PI)
    val cy = cos(ang.y / 180.0 * Math.PI)
    val sp = sin(ang.x / 180.0 * Math.PI)
    val cp = cos(ang.x / 180.0 * Math.PI)

    return Vector(cp * cy, cp * sy, -sp)
}

fun drawIndicator(enemyEnt: Long, drawColor: Color)
{
    val dist = curSettings["INDICATOR_DISTANCE"].toDouble() * 10
    val size = curSettings["INDICATOR_SIZE"].toDouble()

    val meEyeAngle = me.eyeAngle() //Remove readCached?

    val tWidth = CSGO.gameWidth
    val tHeight = CSGO.gameHeight

    val meAbs = me.absPosition()
    val entAbs = enemyEnt.absPosition()

    val src = Vector(meAbs.x, meAbs.y, 0.0)
    val dest = Vector(entAbs.x, entAbs.y, 0.0)

    var tmpAng = calcAngle(src, dest, Vector(0.0, 0.0, 0.0))
    tmpAng = angVec(Vector(-tmpAng.x , 90.0 - tmpAng.y + meEyeAngle.y, -tmpAng.z))

    val triangPos = Vector((tWidth / 2.0) + (-tmpAng.x * dist), (tHeight / 2.0) + (tmpAng.y * dist), 0.0 + (tmpAng.z * dist))

    shapeRenderer.apply {
        begin()
        set(ShapeRenderer.ShapeType.Filled)
        color = com.badlogic.gdx.graphics.Color(drawColor.red / 255F, drawColor.green / 255F, drawColor.blue / 255F, drawColor.alpha.toFloat())

        val rot = -atan2(triangPos.x - tWidth/2.0, triangPos.y - tHeight/2.0)

        //Middle of triangle
        val triangX = triangPos.x
        val triangY = triangPos.y

        val sin = size*sin(rot)
        val cos = size*cos(rot)

        //Rotate triangle
        val vert1x = (-sin + triangX).toFloat()
        val vert1y = (cos + triangY).toFloat()

        val vert2x = (-cos + sin + triangX).toFloat()
        val vert2y = (-sin - cos + triangY).toFloat()

        val vert3x = (cos + sin + triangX).toFloat()
        val vert3y = (sin - cos + triangY).toFloat()

        triangle(vert1x, vert1y, vert2x, vert2y, vert3x, vert3y)

        color = com.badlogic.gdx.graphics.Color(255F, 255F, 255F, 1F)
        set(ShapeRenderer.ShapeType.Line)
        end()
    }
}