package rat.poison.scripts.visuals

import com.badlogic.gdx.graphics.Color
import org.jire.kna.int
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.game.w2s
import rat.poison.game.worldToScreen
import rat.poison.overlay.App
import rat.poison.settings.DANGER_ZONE
import rat.poison.utils.*
import rat.poison.utils.extensions.uint
import rat.poison.utils.extensions.unsign

private val bones = Array(2048) { Line() }
private var currentIdx = 0

private val modelMemory = threadLocalMemory(21332)

internal fun skeletonEsp() = App {
	if (!curSettings.bool["SKELETON_ESP"] || !curSettings.bool["ENABLE_ESP"] || !inGame) return@App
	
	val modelMemory = modelMemory.get()
	
	val meTeam = me.team()
	forEntities(EntityType.CCSPlayer) {
		val entity = it.entity
		val entTeam = entity.team()
		
		val dormCheck = (entity.dormant() && !DANGER_ZONE)
		val enemyCheck = ((!curSettings.bool["SKELETON_SHOW_ENEMIES"] && meTeam != entTeam) && !DANGER_ZONE)
		val teamCheck = ((!curSettings.bool["SKELETON_SHOW_TEAM"] && meTeam == entTeam) && !DANGER_ZONE)
		
		if (entity == me || entity.dead() || dormCheck || enemyCheck || teamCheck) return@forEntities
		
		val studioModel = csgoEXE.uint(entity.studioHdr())
		val numBones = csgoEXE.uint(studioModel + 0x9C).toInt()
		val boneOffset = csgoEXE.uint(studioModel + 0xA0)
		
		csgoEXE.read(studioModel + boneOffset, modelMemory)
		
		var offset = 0
		for (idx in 0 until numBones) {
			val parent = csgoEXE.int(studioModel + boneOffset + 0x4 + offset)
			if (parent != -1) {
				val flags = modelMemory.getInt(0xA0L + offset).unsign() and 0x100
				if (flags != 0L) drawBone(entity, parent, idx)//add(parent to idx)
			}
			
			offset += 216
		}
	}
	
	shapeRenderer.apply {
		if (isDrawing) {
			end()
		}
		
		begin()
		for (i in 0 until currentIdx) {
			val bone = bones[i]
			color = bone.color
			line(bone.sX.toFloat(), bone.sY.toFloat(), bone.eX.toFloat(), bone.eY.toFloat())
		}
		end()
	}
	
	currentIdx = 0
}

private val colors: Array<Color> = Array(101) {
	val red = 1 - (it / 100f)
	val green = (it / 100f)
	
	Color(red, green, 0f, 1f)
}

private val boneMemory = threadLocalMemory(4032)

private fun drawBone(target: Player, start: Int, end: Int) {
	//Reduce r/w
	//Replace later
	
	val boneMemory = boneMemory.get()
	
	csgoEXE.read(target.boneMatrix(), boneMemory)
	
	val startBone = vector(
		boneMemory.getFloat(((0x30L * start) + 0xC)),
		boneMemory.getFloat(((0x30L * start) + 0x1C)),
		boneMemory.getFloat(((0x30L * start) + 0x2C))
	)
	val endBone = vector(
		boneMemory.getFloat(((0x30L * end) + 0xC)),
		boneMemory.getFloat(((0x30L * end) + 0x1C)),
		boneMemory.getFloat(((0x30L * end) + 0x2C))
	)
	
	val startDraw = worldToScreen(startBone)
	val endDraw = worldToScreen(endBone)
	if (startDraw.w2s() && endDraw.w2s()) {
		bones[currentIdx].apply {
			sX = startDraw.x.toInt()
			sY = startDraw.y.toInt()
			eX = endDraw.x.toInt()
			eY = endDraw.y.toInt()
			val health = target.health()
			if (health > 0 && health < colors.size) {
				color = colors[health]
			}
		}
		currentIdx++
	}
}

private data class Line(
	var sX: Int = -1, var sY: Int = -1,
	var eX: Int = -1, var eY: Int = -1,
	var color: Color = Color.WHITE
)