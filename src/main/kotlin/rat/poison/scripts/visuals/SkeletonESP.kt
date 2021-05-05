package rat.poison.scripts.visuals

import com.badlogic.gdx.graphics.Color
import org.jire.arrowhead.unsign
import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.overlay.App
import rat.poison.settings.DANGER_ZONE
import rat.poison.utils.common.Vector
import rat.poison.utils.common.inGame
import rat.poison.utils.common.threadLocalPointer
import rat.poison.utils.extensions.uint

private const val modelMemorySize = 21332
private var modelMemory = threadLocalPointer(modelMemorySize)
private const val boneMemorySize = 5000
private var boneMemory = threadLocalPointer(boneMemorySize)
private var health = -1
private const val skeletonEspIdentifier = "skeletonesp"

private val w2sRet1 = Vector()
private val w2sRet2 = Vector()
private val c = Color()
private val forEnts = arrayOf(EntityType.CCSPlayer)
fun skeletonEsp() = App {
	if (!curSettings.bool["SKELETON_ESP"] || !curSettings.bool["ENABLE_VISUALS"] || !inGame) return@App

	if (!shapeRenderer.isDrawing) {
		shapeRenderer.begin()
	}

	forEntities(forEnts, identifier = skeletonEspIdentifier) {
		val entity = it.entity
		val entTeam = entity.team()

		val dormCheck = (entity.dormant() && !DANGER_ZONE)
		val enemyCheck = ((!curSettings.bool["SKELETON_SHOW_ENEMIES"] && meTeam != entTeam) && !DANGER_ZONE)
		val teamCheck = ((!curSettings.bool["SKELETON_SHOW_TEAM"] && meTeam == entTeam) && !DANGER_ZONE)

		if (entity == me || entity.dead() || dormCheck || enemyCheck || teamCheck) return@forEntities
		val studioModel = csgoEXE.uint(entity.studioHdr())
		val boneOffset = csgoEXE.uint(studioModel + 0xA0)
		val boneMatrix = entity.boneMatrix()
		val numBones = csgoEXE.uint(studioModel + 0x9C).toInt()
		health = entity.health()
		c.set(1F - (.01F * health), (.01F * health), 0F, 1F)
		shapeRenderer.color = c

		//Get actual size
		val modelMemory = modelMemory.get()
		val boneMemory = boneMemory.get()

		csgoEXE.read(studioModel + boneOffset, modelMemory)
		csgoEXE.read(boneMatrix, boneMemory)
		var offset = 0
		for (idx in 0 until numBones) {
			if (offset+4 > modelMemorySize) return@forEntities
			val parent = modelMemory.getInt(0x4L + offset)
			if (parent != -1) {
				val flags = modelMemory.getInt(0xA0L + offset).unsign() and 0x100
				if (flags != 0L) {
					if (worldToScreen(boneMemory.getFloat(((0x30L * parent) + 0xC)),
									boneMemory.getFloat(((0x30L * parent) + 0x1C)),
									boneMemory.getFloat(((0x30L * parent) + 0x2C)), w2sRet1) && worldToScreen(boneMemory.getFloat(((0x30L * idx) + 0xC)),
									boneMemory.getFloat(((0x30L * idx) + 0x1C)),
									boneMemory.getFloat(((0x30L * idx) + 0x2C)), w2sRet2)) {
						shapeRenderer.line(w2sRet1.x, w2sRet1.y, w2sRet2.x, w2sRet2.y)
					}
				}
			}
			offset += 216
		}
	}
	shapeRenderer.end()
}