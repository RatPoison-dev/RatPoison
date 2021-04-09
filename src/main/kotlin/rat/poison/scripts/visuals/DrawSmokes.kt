package rat.poison.scripts.visuals

import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.netvars.NetVarOffsets.bDidSmokeEffect
import rat.poison.overlay.App
import rat.poison.overlay.App.shapeRenderer
import rat.poison.utils.common.Vector
import rat.poison.utils.common.distanceTo
import rat.poison.utils.common.inGame
import kotlin.math.cos
import kotlin.math.sin

private val w2s1 = Vector()
private val w2s2 = Vector()
private val smokePos = Vector()
private val points = mutableListOf<Vector>()
private const val id = "drawsmokes"
private val forEnts = arrayOf(EntityType.CSmokeGrenadeProjectile)
fun drawSmokes() = App {
    if (!inGame || !curSettings.bool["ENABLE_ESP"] || !curSettings.bool["VISUALIZE_SMOKES"] || !inGame) return@App

    val smokePolys = curSettings.int["VISUALIZE_SMOKES_POLYS"]
    val smokeHeight = curSettings.int["VISUALIZE_SMOKES_HEIGHT"]
    val smokeWidth = curSettings.int["VISUALIZE_SMOKES_WIDTH"]
    points.clear()

    forEntities(forEnts, identifier = id) {
        points.clear()
        val entity = it.entity
        if (!entity.didEffect()) return@forEntities
        if (entity.timeLeftToDisappear() <= 0) return@forEntities

        val smokePos = it.entity.absPosition(smokePos)

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
        shapeRenderer.begin()
        shapeRenderer.color = curSettings.colorGDX["VISUALIZE_SMOKES_COLOR"]

        if (points.size > 0) {
            for (i in 0 until smokePolys) {
                connectPoints(points[i], points[(i+1) % smokePolys])
                connectPoints(points[i + smokePolys], points[(i+1) % smokePolys + smokePolys])
                connectPoints(points[i], points[i + smokePolys])
            }
        }

        shapeRenderer.end()

        //ratto do something with this ong
        //if (dbg) {
        //    val mePos = me.position()
        //    val meDir = me.direction()
        //    val maxPos = Vector(mePos.x + 500 * meDir.x, mePos.y + 500 * meDir.y, mePos.z)
//
        //    val pX = -(maxPos.y - mePos.y)
        //    val pY = (maxPos.x - mePos.x)
//
        //    shapeRenderer.begin()
//
        //    if (worldToScreen(Vector(smokePos.x + pX, smokePos.y + pY, smokePos.z), w2s1) && worldToScreen(Vector(smokePos.x - pX, smokePos.y - pY, smokePos.z), w2s2)) {
        //        shapeRenderer.line(w2s1.x, w2s1.y, w2s2.x, w2s2.y)
        //    }
//
        //    shapeRenderer.end()
        //}
    }
}

fun connectPoints(vec1: Vector, vec2: Vector) {
    if (worldToScreen(vec1, w2s1) && worldToScreen(vec2, w2s2)) {
        shapeRenderer.line(w2s1.x, w2s1.y, w2s2.x, w2s2.y)
    }
}

private val mePos = Vector()
private val entPos = Vector()
private val entPos2 = Vector()
private var pX = -1F
private var pY = -1F
private var through = false
private const val forEntsId = "linethroughsmoke"
private val forEnts2 = arrayOf(EntityType.CSmokeGrenadeProjectile)
fun lineThroughSmoke(ent: Player): Boolean {
    through = false

    val mePos = me.position(mePos)
    val maxPos = ent.position(entPos)

    pX = -(maxPos.x - mePos.x)
    pY = (maxPos.y - mePos.y)

    forEntities(forEnts2, identifier = forEntsId) {
        if (through || !csgoEXE.boolean(it.entity + bDidSmokeEffect)) return@forEntities

        val pos = it.entity.absPosition(entPos2)

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