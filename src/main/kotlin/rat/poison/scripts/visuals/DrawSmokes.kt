package rat.poison.scripts.visuals

import it.unimi.dsi.fastutil.longs.LongArrayList
import org.jire.kna.boolean
import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.netvars.NetVarOffsets.bDidSmokeEffect
import rat.poison.overlay.App
import rat.poison.overlay.App.shapeRenderer
import rat.poison.utils.Vector
import rat.poison.utils.inGame
import rat.poison.utils.vectorLong
import kotlin.math.cos
import kotlin.math.sin

fun drawSmokes() = App {
	if (!inGame || !curSettings.bool["ENABLE_ESP"] || !curSettings.bool["VISUALIZE_SMOKES"] || !inGame) return@App
	
	val smokePolys = curSettings.int["VISUALIZE_SMOKES_POLYS"]
	val smokeHeight = curSettings.int["VISUALIZE_SMOKES_HEIGHT"]
	val smokeWidth = curSettings.int["VISUALIZE_SMOKES_WIDTH"]
	
	forEntities(EntityType.CSmokeGrenadeProjectile) {
		val entity = it.entity
		if (!entity.didEffect()) return@forEntities
		if (entity.timeLeftToDisappear() <= 0) return@forEntities
		
		val smokePos = it.entity.absPosition()
		val points = LongArrayList()
		
		for (i in 0 until smokePolys) {
			val x = smokePos.x + smokeWidth * cos(Math.toRadians(360.0 / smokePolys * i))
			val y = smokePos.y + smokeWidth * sin(Math.toRadians(360.0 / smokePolys * i))
			val z = smokePos.z
			points.add(vectorLong(x.toFloat(), y.toFloat(), z))
		}
		
		for (i in 0 until smokePolys) {
			val x = smokePos.x + smokeWidth * cos(Math.toRadians(360.0 / smokePolys * i))
			val y = smokePos.y + smokeWidth * sin(Math.toRadians(360.0 / smokePolys * i))
			val z = smokePos.z + smokeHeight
			points.add(vectorLong(x.toFloat(), y.toFloat(), z))
		}
		
		if (points.size > 0) {
			for (i in 0 until smokePolys) {
				connectPoints(
					Vector(points.getLong(i)),
					Vector(points.getLong((i + 1) % smokePolys))
				)
				connectPoints(
					Vector(points.getLong(i + smokePolys)),
					Vector(points.getLong((i + 1) % smokePolys + smokePolys))
				)
				connectPoints(
					Vector(points.getLong(i)),
					Vector(points.getLong(i + smokePolys))
				)
			}
		}
		
		if (curSettings.bool["DEBUG"]) {
			val mePos = me.position()
			val meDir = me.direction()
			val maxPos = Vector(mePos.x + 500 * meDir.x, mePos.y + 500 * meDir.y, mePos.z)
			
			val pX = -(maxPos.y - mePos.y)
			val pY = (maxPos.x - mePos.x)
			
			mePos.release()
			meDir.release()
			maxPos.release()
			
			val w1 = Vector(smokePos.x + pX, smokePos.y + pY, smokePos.z)
			val w2s1 = worldToScreen(w1)
			val w2 = Vector(smokePos.x - pX, smokePos.y - pY, smokePos.z)
			val w2s2 = worldToScreen(w2)
			
			shapeRenderer.begin()
			
			if (w2s1.w2s() && w2s2.w2s()) {
				shapeRenderer.line(w2s1.x, w2s1.y, w2s2.x, w2s2.y)
			}
			
			shapeRenderer.end()
			
			w1.release()
			w2s1.release()
			w2.release()
			w2s2.release()
		}
		
		smokePos.release()
	}
}

fun connectPoints(vec1: Vector, vec2: Vector) {
	val w2s1 = worldToScreen(vec1)
	val w2s2 = worldToScreen(vec2)
	
	if (w2s1.w2s() && w2s2.w2s()) {
		if (shapeRenderer.isDrawing) {
			shapeRenderer.end()
		}
		
		shapeRenderer.begin()
		
		shapeRenderer.color = curSettings.colorGDX["VISUALIZE_SMOKES_COLOR"]
		shapeRenderer.line(w2s1.x, w2s1.y, w2s2.x, w2s2.y)
		
		shapeRenderer.end()
	}
	
	vec1.release()
	vec2.release()
	w2s1.release()
	w2s2.release()
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
		through = (pos.distanceTo(
			Vector(
				realX,
				realY,
				pos.z
			)
		) <= 175f && mePos.distanceTo(maxPos) > mePos.distanceTo(pos)) || mePos.distanceTo(pos) <= 175f
		
		pos.release()
	}
	
	mePos.release()
	maxPos.release()
	
	return through
}