package rat.poison.scripts.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import org.lwjgl.opengl.GL11.glEnable
import rat.poison.curSettings
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.game.meTeam
import rat.poison.game.offsets.ClientOffsets
import rat.poison.game.offsets.ClientOffsets.dwRadarBase
import rat.poison.game.worldToScreen
import rat.poison.overlay.App
import rat.poison.scripts.userCmd.meDead
import rat.poison.settings.DANGER_ZONE
import rat.poison.utils.common.Vector
import rat.poison.utils.common.inGame
import rat.poison.utils.common.threadLocalPointer
import kotlin.math.abs

data class FarPlayer(val pos: Vector = Vector(), var alpha: Float = 0F)
private var farPlayerRecords = Array(64) { FarPlayer() }
private val w2s1 = Vector()
private val w2s2 = Vector()
private const val id = "farradar"
private val forEnts = arrayOf(EntityType.CCSPlayer)
private const val dwRadarMemorySize = 237
private val dwRadarMemory = threadLocalPointer(dwRadarMemorySize)
fun farRadar() = App {
    if (!inGame || !curSettings.bool["BOX_FAR_RADAR"] || meDead) return@App

    var dwRadar = clientDLL.int(dwRadarBase)
    dwRadar = csgoEXE.int(dwRadar + 0x74)

    forEntities(forEnts, identifier = id) { //This will probably require more prechecks
        val ent = it.entity

        //Prechecks
        if (ent == me || meTeam == ent.team() || DANGER_ZONE || !ent.dormant() || ent.dead()) return@forEntities

        val entID = csgoEXE.int(ent + ClientOffsets.dwIndex)
        val memory = dwRadarMemory.get()
        csgoEXE.read(dwRadar + (0x174 * (entID + 1)) - 0x3C, memory, dwRadarMemorySize)
        val mem =  memory?: return@forEntities

        val pos = Vector(mem.getFloat(0), mem.getFloat(4), mem.getFloat(8))
        val health = mem.getInt(0x50)

        if (pos.x != 0.0F && health > 0 && health <= 100 && entID <= 64) {
            if (farPlayerRecords[entID].alpha <= 0F && farPlayerRecords[entID].pos != pos) {
                farPlayerRecords[entID] = FarPlayer(pos, 1F)
            }
        }
    }

    farPlayerRecords.forEach {
        if (it.alpha > 0F) {

            if (worldToScreen(it.pos, w2s1) && worldToScreen(it.pos.x, it.pos.y, it.pos.z - 75F, w2s2)) {
                if (shapeRenderer.isDrawing) shapeRenderer.end()
                shapeRenderer.begin()

                glEnable(GL20.GL_BLEND)

                val h = w2s1.y - w2s2.y
                val w = abs(h / 5F * 2F)

                shapeRenderer.color = Color(1F, 1F, 1F, it.alpha)
                shapeRenderer.rect(w2s1.x - w/2F, w2s1.y, w, h)

                shapeRenderer.end()
            }

            it.alpha -= 0.01F
        }
    }
}