package rat.poison.scripts.esp

import com.badlogic.gdx.graphics.Color
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.CSGO.engineDLL
import rat.poison.game.entity.*
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.game.offsets.EngineOffsets.pStudioModel
import rat.poison.game.worldToScreen
import rat.poison.settings.*
import rat.poison.utils.Vector
import rat.poison.utils.collections.CacheableList
import rat.poison.utils.extensions.uint
import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap
import rat.poison.App
import rat.poison.curSettings
import rat.poison.strToBool

private val bones = Array(2048) { Line() }
private val entityBones = Long2ObjectArrayMap<CacheableList<Pair<Int, Int>>>()
private var currentIdx = 0

internal fun skeletonEsp() {
	App {
		if (!curSettings["SKELETON_ESP"]!!.strToBool() || !curSettings["ENABLE_ESP"]!!.strToBool() || MENUTOG) return@App

		forEntities(ccsPlayer) {
			val entity = it.entity
			if (entity == me || entity.dead() || (!curSettings["SKELETON_SHOW_DORMANT"]!!.strToBool() && entity.dormant()) || (!curSettings["SKELETON_SHOW_TEAM"]!!.strToBool() && me.team() == entity.team()) || (!curSettings["SKELETON_SHOW_ENEMIES"]!!.strToBool() && me.team() != entity.team())) return@forEntities false
			(entityBones.get(entity) ?: CacheableList(20)).apply {
				if (isEmpty()) {
					val studioModel = csgoEXE.uint(entity.studioHdr())
					val numBones = csgoEXE.uint(studioModel + 0x9C).toInt()
					val boneIndex = csgoEXE.uint(studioModel + 0xA0)

					var offset = 0
					for (idx in 0 until numBones) {
						val parent = csgoEXE.int(studioModel + boneIndex + 0x4 + offset)
						if (parent != -1) {
							val flags = csgoEXE.uint(studioModel + boneIndex + 0xA0 + offset) and 0x100
							if (flags != 0L) add(parent to idx)
						}

						offset += 216
					}

					entityBones[entity] = this
				}

				forEach { et -> drawBone(entity, et.first, et.second); false }
			}

			false
		}

		shapeRenderer.apply {
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
}

private fun findStudioModel(pModel: Long): Long {
	val type = csgoEXE.uint(pModel + 0x0110)
	if (type != 3L) return 0 // Type is not Studiomodel

	var handle = csgoEXE.uint(pModel + 0x0138) and 0xFFFF
	if (handle == 0xFFFFL) return 0 // Handle is not valid
	handle = handle shl 4

	var studioModel = engineDLL.uint(pStudioModel)
	studioModel = csgoEXE.uint(studioModel + 0x28)
	studioModel = csgoEXE.uint(studioModel + handle + 0xC)

	return csgoEXE.uint(studioModel + 0x74)
}

private val colors: Array<Color> = Array(101) {
	val red = 1 - (it / 100f)
	val green = (it / 100f)

	Color(red, green, 0f, 1f)
}

private val startBone = Vector()
private val endBone = Vector()

private val startDraw = Vector()
private val endDraw = Vector()

private fun drawBone(target: Player, start: Int, end: Int) {
	val boneMatrix = target.boneMatrix()
	startBone.set(
			target.bone(0xC, start, boneMatrix),
			target.bone(0x1C, start, boneMatrix),
			target.bone(0x2C, start, boneMatrix))
	endBone.set(
			target.bone(0xC, end, boneMatrix),
			target.bone(0x1C, end, boneMatrix),
			target.bone(0x2C, end, boneMatrix))

	if (worldToScreen(startBone, startDraw) && worldToScreen(endBone, endDraw)) {
		bones[currentIdx].apply {
			sX = startDraw.x.toInt()
			sY = startDraw.y.toInt()
			eX = endDraw.x.toInt()
			eY = endDraw.y.toInt()
			val health = target.health()
			if (health >= 0 && health < colors.size) {
				color = colors[health]
			}
		}
		currentIdx++
	}
}

private data class Line(var sX: Int = -1, var sY: Int = -1,
						var eX: Int = -1, var eY: Int = -1,
						var color: Color = Color.WHITE)