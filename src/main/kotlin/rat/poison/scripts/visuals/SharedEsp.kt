package rat.poison.scripts.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.util.*
import io.ktor.util.collections.*
import kotlinx.coroutines.*
import org.apache.commons.lang3.StringUtils
import org.lwjgl.opengl.GL11
import rat.poison.curSettings
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.game.meTeam
import rat.poison.game.worldToScreen
import rat.poison.overlay.App
import rat.poison.overlay.opened
import rat.poison.utils.*


data class SharedPlayer(var pos: Vector = Vector(), var alpha: Float = 0F, val steamID: Int = 0)
@KtorExperimentalAPI
private var sharedPlayerRecords = ConcurrentList<SharedPlayer>()
private var metEntityList = mutableListOf<Int>()

private val w2s1 = Vector()
private val w2s2 = Vector()
private val entPosVec = Vector()
private val forEntsList = arrayOf(EntityType.CCSPlayer)
private val bbox = BoundingBox()
@KtorExperimentalAPI
fun sharedEsp() {
    WebSocket(precheck = { inGame && opened && curSettings.bool["SHARED_ESP"] && curSettings.bool["ENABLE_ESP"] }, body = {
        val mySid = me.getValidSteamID()

        if (mySid == 0) return@WebSocket
        metEntityList.clear()

        //send what we have to server
        var s = "iterateEntities:${mySid}:${meTeam}:"
        forEntities(forEntsList) {
            val ent = it.entity
            if (ent.dormant() || ent.dead() || ent <= 0 || ent == me) return@forEntities
            //sharedPlayerRecords[allocSharedPlayerRecord(0)].apply {
            //    pos = ent.position()
            //    alpha = 1F
            //}
            if (ent.team() == meTeam) return@forEntities
            val tmpSid = ent.getValidSteamID()
            if (tmpSid == 0) return@forEntities
            s += "${tmpSid}:${ent.position(entPosVec)}:"

            metEntityList.add(tmpSid)
        }
        webSocketSession.send(s)


        var text = ""
        when (val frame = webSocketSession.incoming.receive()) {
            is Frame.Text -> text = frame.readText()
            is Frame.Binary -> text = frame.readBytes().toString()
        }

        val myArray = text.split(":")
        myArray.forEachIndexed { index, sID ->
            if (index % 4 != 0) return@forEachIndexed
            if (!StringUtils.isNumeric(sID) || sID == "0") return@forEachIndexed
            val realSid = sID.toInt()
            if (metEntityList.contains(realSid)) return@forEachIndexed
            val listIdx = allocSharedPlayerRecord(realSid)
            val newX = myArray[index + 1].toFloat()
            val newY = myArray[index + 2].toFloat()
            val newZ = myArray[index + 3].toFloat()
            val get = sharedPlayerRecords[listIdx]
            if (get.alpha <= 0F && (get.pos.x != newX && get.pos.y != newY && get.pos.z != newZ)) {
                get.apply {
                    pos.set(newX, newY, newZ)
                    alpha = 1F
                }
            }
        }
    })
    App {
        for (i in 0 until sharedPlayerRecords.size) {
            val it = sharedPlayerRecords[i]
            if (it.alpha > 0F) {

                if (worldToScreen(it.pos, w2s1) && worldToScreen(it.pos.x, it.pos.y, it.pos.z - 75F, w2s2)) {
                    if (shapeRenderer.isDrawing) shapeRenderer.end()
                    shapeRenderer.begin()

                    val bbox = setupFakeBox(w2s1, w2s2, bbox)
                    val boxWidth = bbox.right - bbox.left
                    val boxHeight = bbox.bottom - bbox.top

                    GL11.glEnable(GL20.GL_BLEND)

                    shapeRenderer.color = Color(1F, 1F, 1F, it.alpha)
                    shapeRenderer.rect(bbox.left, bbox.top, boxWidth, boxHeight)

                    shapeRenderer.end()

                    if (!sb.isDrawing) {
                        sb.begin()
                    }

                    //textRenderer.draw(sb, "jojo", bbox.left, bbox.top + 18F, 1F, Align.right, false) //details later
                }

                it.alpha -= .01F
            } else {
                sharedPlayerRecords.removeAt(i)
            }
        }
    }
}
@KtorExperimentalAPI
fun allocSharedPlayerRecord(steamID: Int): Int {
    for (idx in 0 until sharedPlayerRecords.size ) {
        val i = sharedPlayerRecords[idx]
        if (i.steamID == steamID) {
            return idx
        }
    }
    sharedPlayerRecords.add(SharedPlayer(steamID = steamID))
    return sharedPlayerRecords.size - 1
}