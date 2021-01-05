package rat.poison.scripts.visuals

import com.badlogic.gdx.graphics.Color
import org.jire.kna.Pointer
import org.jire.kna.int
import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.overlay.App
import rat.poison.scripts.aim.meDead
import rat.poison.settings.DANGER_ZONE
import rat.poison.utils.*
import rat.poison.utils.extensions.uint
import rat.poison.utils.extensions.unsign

private val bones = Array(2048) { Line() }
private var currentIdx = 0

private const val modelMemorySize = 21332L
private val modelMemory = threadLocalPointer(modelMemorySize)

internal fun skeletonEsp() = App {
	if (!curSettings.bool["SKELETON_ESP"] || !curSettings.bool["ENABLE_ESP"] || !inGame || (curSettings.bool["SKELETON_ESP_DEAD"] && !meDead)) return@App
	
	val modelMemory = modelMemory.get()
	
	val meTeam = me.team()
	forEntities(EntityType.CCSPlayer) {
		val entity = it.entity
		val entTeam = entity.team()
		
		val dormCheck = (entity.dormant() && !DANGER_ZONE)
		val enemyCheck = ((!curSettings.bool["SKELETON_SHOW_ENEMIES"] && meTeam != entTeam) && !DANGER_ZONE)
		val teamCheck = ((!curSettings.bool["SKELETON_SHOW_TEAM"] && meTeam == entTeam) && !DANGER_ZONE)
		val audibleCheck = curSettings.bool["SKELETON_ESP_AUDIBLE"] && !inFootsteps(entity)
		
		if (entity == me || entity.dead() || dormCheck || enemyCheck || teamCheck || audibleCheck) return@forEntities
		
		val studioHdr = entity.studioHdr()
		if (studioHdr <= 0) return@forEntities
		val studioModel = csgoEXE.uint(studioHdr)
		if (studioModel <= 0) return@forEntities
		val numBones = csgoEXE.uint(studioModel + 0x9C).toInt()
		if (numBones <= 0) return@forEntities
		val boneOffset = csgoEXE.uint(studioModel + 0xA0)
		if (boneOffset <= 0) return@forEntities
		
		val address = studioModel + boneOffset
		if (address > Int.MAX_VALUE) return@forEntities
		
		if (!csgoEXE.read(
				address,
				modelMemory,
				modelMemorySize
			)
		) throw IllegalStateException("$entity / $studioModel / $boneOffset")
		
		val boneMemory = boneMemory.get()
		if (!csgoEXE.read(entity.boneMatrix(), boneMemory, boneMemorySize)) throw IllegalStateException()
		val health = entity.health()
		
		var offset = 0
		for (idx in 0 until numBones) {
			val parent = csgoEXE.int(studioModel + boneOffset + 0x4 + offset)
			if (parent != -1) {
				val flags = modelMemory.getInt(0xA0L + offset).unsign() and 0x100
				if (flags != 0L) drawBone(boneMemory, health, parent, idx)//add(parent to idx)
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

private const val boneMemorySize = 4032L
private val boneMemory = threadLocalPointer(boneMemorySize)

private fun drawBone(boneMemory: Pointer, targetHealth: Int, start: Int, end: Int) {
	//Reduce r/w
	//Replace later
	
	val startBone = worldToScreenLong(
		boneMemory.getFloat(((0x30L * start) + 0xC)),
		boneMemory.getFloat(((0x30L * start) + 0x1C)),
		boneMemory.getFloat(((0x30L * start) + 0x2C))
	)
	val endBone = worldToScreenLong(
		boneMemory.getFloat(((0x30L * end) + 0xC)),
		boneMemory.getFloat(((0x30L * end) + 0x1C)),
		boneMemory.getFloat(((0x30L * end) + 0x2C))
	)
	if (startBone != -1L && endBone != -1L) {
		bones[currentIdx].apply {
			sX = ((startBone ushr 32) and 0xFFFFFFFFL).toInt()
			sY = (startBone and 0xFFFFFFFFL).toInt()
			eX = ((endBone ushr 32) and 0xFFFFFFFFL).toInt()
			eY = (endBone and 0xFFFFFFFFL).toInt()
			if (targetHealth > 0 && targetHealth < colors.size) {
				color = colors[targetHealth]
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