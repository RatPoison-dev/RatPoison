package rat.poison.scripts.visuals

import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.netvars.NetVarOffsets.bDidSmokeEffect
import rat.poison.overlay.App
import rat.poison.overlay.App.shapeRenderer
import rat.poison.utils.Vector
import rat.poison.utils.distanceTo
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.strToColorGDX
import rat.poison.utils.inGame
import kotlin.math.cos
import kotlin.math.sin

fun drawSmokes() = App {
    if (!inGame || !curSettings["ENABLE_ESP"].strToBool() || !curSettings["VISUALIZE_SMOKES"].strToBool() || !inGame) return@App

    val smokePolys = curSettings["VISUALIZE_SMOKES_POLYS"].toInt()
    val smokeHeight = curSettings["VISUALIZE_SMOKES_HEIGHT"].toInt()
    val smokeWidth = curSettings["VISUALIZE_SMOKES_WIDTH"].toInt()

    forEntities(EntityType.CSmokeGrenadeProjectile) {
        if (!csgoEXE.boolean(it.entity + bDidSmokeEffect)) return@forEntities

        val smokePos = it.entity.absPosition()
        val points = mutableListOf<Vector>()

        for (i in 0 until smokePolys) {
            val x = smokePos.x + smokeWidth * cos(Math.toRadians(360.0 / smokePolys * i))
            val y = smokePos.y + smokeWidth * sin(Math.toRadians(360.0 / smokePolys * i))
            val z = smokePos.z
            points.add(Vector(x.toFloat(), y.toFloat(), z))
        }

        for (i in 0 until smokePolys) {
            val x = smokePos.x + smokeWidth * cos(Math.toRadians(360.0 / smokePolys * i))
            val y = smokePos.y + smokeWidth * sin(Math.toRadians(360.0 / smokePolys * i))
            val z = smokePos.z + smokeHeight
            points.add(Vector(x.toFloat(), y.toFloat(), z))
        }

        if (points.size > 0) {
            for (i in 0 until smokePolys) {
                connectPoints(points[i], points[(i+1) % smokePolys])
                connectPoints(points[i + smokePolys], points[(i+1) % smokePolys + smokePolys])
                connectPoints(points[i], points[i + smokePolys])
            }
        }

        if (curSettings["DEBUG"].strToBool()) {
            val mePos = me.position()
            val meDir = me.direction()
            val maxPos = Vector(mePos.x + 500 * meDir.x, mePos.y + 500 * meDir.y, mePos.z)

            val pX = -(maxPos.y - mePos.y)
            val pY = (maxPos.x - mePos.x)

            val w2s1 = Vector()
            val w2s2 = Vector()

            shapeRenderer.begin()

            if (worldToScreen(Vector(smokePos.x + pX, smokePos.y + pY, smokePos.z), w2s1) && worldToScreen(Vector(smokePos.x - pX, smokePos.y - pY, smokePos.z), w2s2)) {
                shapeRenderer.line(w2s1.x, w2s1.y, w2s2.x, w2s2.y)
            }

            shapeRenderer.end()
        }
    }

    lineThroughSmoke(me)
}

fun connectPoints(vec1: Vector, vec2: Vector) {
    val w2s1 = Vector()
    val w2s2 = Vector()

    if (worldToScreen(vec1, w2s1) && worldToScreen(vec2, w2s2)) {
        if (shapeRenderer.isDrawing) {
            shapeRenderer.end()
        }

        shapeRenderer.begin()

        shapeRenderer.color = curSettings["VISUALIZE_SMOKES_COLOR"].strToColorGDX()
        shapeRenderer.line(w2s1.x, w2s1.y, w2s2.x, w2s2.y)

        shapeRenderer.end()
    }
}

fun lineThroughSmoke(ent: Player): Boolean {
    var through = false

    val mePos = me.position()
    val maxPos = ent.position()

    val pX = -(maxPos.y - mePos.y)
    val pY = (maxPos.x - mePos.x)

    forEntities(EntityType.CSmokeGrenadeProjectile) {
        if (!csgoEXE.boolean(it.entity + bDidSmokeEffect) || through) return@forEntities

        val pos = it.entity.absPosition()

        //TODO crunch...
        val x3 = pos.x + pX
        val y3 = pos.y + pY
        val x4 = pos.x - pX
        val y4 = pos.y - pY

        val aX = mePos.x - maxPos.x
        val bX = x3 - x4
        val aY = mePos.y - maxPos.y
        val bY = y3 - y4

        val delta = aX * bY - aY * bX

        val a = mePos.x * maxPos.y - mePos.y * maxPos.x
        val b = x3 * y4 - y3 * x4

        val realX = (a * bX - b * aX) / delta
        val realY = (a * bY - b * aY) / delta

        //This is most likely not perfect...
        through = (pos.distanceTo(Vector(realX, realY, pos.z)) <= 175f && mePos.distanceTo(maxPos) > mePos.distanceTo(pos)) || mePos.distanceTo(pos) <= 175f
    }
    return through
}