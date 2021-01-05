package rat.poison.scripts.visuals

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.overlay.App
import rat.poison.overlay.App.shapeRenderer
import rat.poison.scripts.aim.meDead
import rat.poison.settings.DANGER_ZONE
import rat.poison.utils.Vector
import rat.poison.utils.inGame
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin
import com.badlogic.gdx.graphics.Color as ColorGDX

fun indicatorEsp() = App {
	if (!curSettings.bool["ENABLE_ESP"] || !curSettings.bool["INDICATOR_ESP"] || !inGame || (curSettings.bool["INDICATOR_ESP_DEAD"] && !meDead)) return@App
	
	val bomb: Entity = entityByType(EntityType.CC4)?.entity ?: -1L
	val bEnt = bomb.carrier()
	val myTeam = me.team()
	val meEyeAngle = me.eyeAngle()
	val meAbs = me.absPosition()
	
	val dist = curSettings.float["INDICATOR_DISTANCE"] * 10F
	val size = curSettings.float["INDICATOR_SIZE"]
	
	forEntities {
		val entity = it.entity
		val onTeam = !DANGER_ZONE && myTeam == entity.team()
		
		var color = ""
		
		when (it.type) {
			EntityType.CCSPlayer -> {
				if (entity.dead() || entity == me || entity.dormant()) return@forEntities
				
				if (curSettings.bool["INDICATOR_SMOKE_CHECK"] && lineThroughSmoke(entity)) return@forEntities
				if (curSettings.bool["INDICATOR_ESP_AUDIBLE"] && !inFootsteps(entity)) return@forEntities
				
				if (bEnt > 0 && bEnt == entity) { //This is the bomb carrier
					if (curSettings.bool["INDICATOR_SHOW_ENEMIES"] && !onTeam) {
						color = when (curSettings.bool["INDICATOR_SHOW_BOMB_CARRIER"]) {
							true -> "INDICATOR_BOMB_CARRIER_COLOR"
							false -> "INDICATOR_ENEMY_COLOR"
						}
					} else if (curSettings.bool["INDICATOR_SHOW_TEAM"] && onTeam) {
						color = when (curSettings.bool["INDICATOR_SHOW_BOMB_CARRIER"]) {
							true -> "INDICATOR_BOMB_CARRIER_COLOR"
							false -> "INDICATOR_TEAM_COLOR"
						}
					}
				} else {
					if (!curSettings.bool["INDICATOR_SHOW_TEAM"] && onTeam) {
						return@forEntities
					} else if (!curSettings.bool["INDICATOR_SHOW_ENEMIES"]) {
						return@forEntities
					} else {
						color = when (!onTeam) {
							true -> "INDICATOR_ENEMY_COLOR"
							false -> "INDICATOR_TEAM_COLOR"
						}
					}
				}
			}
			
			EntityType.CPlantedC4, EntityType.CC4 -> {
				if (curSettings.bool["INDICATOR_SHOW_BOMB"]) {
					color = "INDICATOR_BOMB_COLOR"
				}
			}
			
			else -> {
				if (curSettings.bool["INDICATOR_SHOW_WEAPONS"] && it.type.weapon) {
					color = "INDICATOR_WEAPON_COLOR"
				} else if (curSettings.bool["INDICATOR_SHOW_GRENADES"] && it.type.grenade) {
					color = "INDICATOR_GRENADE_COLOR"
				}
			}
		}
		
		if (color != "") {
			drawIndicator(meEyeAngle, meAbs, entity, curSettings.color[color], dist, size)
		}
	}
	
	if (curSettings.bool["INDICATOR_SHOW_DEFUSERS"]) {
		val color = curSettings.color["INDICATOR_DEFUSER_COLOR"]
		forEntities(EntityType.CEconEntity) {
			drawIndicator(meEyeAngle, meAbs, it.entity, color, dist, size)
		}
	}
	
	meEyeAngle.release()
	meAbs.release()
}

fun calcAngle(src: Vector, dest: Vector, vAng: Vector): Vector {
	val delta = Vector(dest.x - src.x, dest.y - src.y, dest.z - src.z)
	val angs = Vector(
		(Math.toDegrees(atan2(-delta.z, hypot(delta.x, delta.y)).toDouble()) - vAng.x).toFloat(),
		(Math.toDegrees(atan2(delta.y, delta.x).toDouble()) - vAng.y).toFloat(), 0F
	)
	delta.release()
	return angs.normalize()
}

fun angVec(ang: Vector): Vector {
	val sy = sin(ang.y / 180.0 * Math.PI)
	val cy = cos(ang.y / 180.0 * Math.PI)
	val sp = sin(ang.x / 180.0 * Math.PI)
	val cp = cos(ang.x / 180.0 * Math.PI)
	
	return Vector((cp * cy).toFloat(), (cp * sy).toFloat(), (-sp).toFloat())
}

private val colorGDX: ThreadLocal<ColorGDX> = ThreadLocal.withInitial { ColorGDX() }

fun drawIndicator(meEyeAngle: Vector, meAbs: Vector, enemyEnt: Long, drawColor: Color, dist: Float, size: Float) {
	val tWidth = CSGO.gameWidth
	val tHeight = CSGO.gameHeight
	
	val entAbs = enemyEnt.absPosition()
	
	var tmpAng = calcAngle(meAbs, entAbs, Vector.DEFAULT)
	entAbs.release()
	
	val tmpVAng = Vector(-tmpAng.x, 90F - tmpAng.y + meEyeAngle.y, -tmpAng.z)
	tmpAng.release()
	
	tmpAng = angVec(tmpVAng)
	tmpVAng.release()
	
	val triangPos =
		Vector((tWidth / 2F) + (-tmpAng.x * dist), (tHeight / 2F) + (tmpAng.y * dist), 0F + (tmpAng.z * dist))
	tmpAng.release()
	
	if (!shapeRenderer.isDrawing) {
		shapeRenderer.begin()
	}
	
	shapeRenderer.set(ShapeRenderer.ShapeType.Filled)
	shapeRenderer.color = colorGDX.get().apply {
		r = drawColor.red / 255F
		g = drawColor.green / 255F
		b = drawColor.blue / 255F
		a = drawColor.alpha.toFloat()
	}
	
	val rot = -atan2(triangPos.x - tWidth / 2.0, triangPos.y - tHeight / 2.0)
	
	//Middle of triangle
	val triangX = triangPos.x
	val triangY = triangPos.y
	
	triangPos.release()
	
	val sin = size * sin(rot)
	val cos = size * cos(rot)
	
	//Rotate triangle
	val vert1x = (-sin + triangX).toFloat()
	val vert1y = (cos + triangY).toFloat()
	
	val vert2x = (-cos + sin + triangX).toFloat()
	val vert2y = (-sin - cos + triangY).toFloat()
	
	val vert3x = (cos + sin + triangX).toFloat()
	val vert3y = (sin - cos + triangY).toFloat()
	
	shapeRenderer.triangle(vert1x, vert1y, vert2x, vert2y, vert3x, vert3y)
	
	//shapeRenderer.color = END_LINE_COLOR
	//shapeRenderer.set(ShapeRenderer.ShapeType.Line)
	shapeRenderer.end()
}

private val END_LINE_COLOR = ColorGDX(255F, 255F, 255F, 1F)