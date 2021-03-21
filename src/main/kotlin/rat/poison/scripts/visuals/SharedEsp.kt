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
import kotlin.math.abs


data class SharedPlayer(var pos: Vector = Vector(), var alpha: Float = 0F, val steamID: Int = 0)
@KtorExperimentalAPI
private var sharedPlayerRecords = ConcurrentList<SharedPlayer>()
private var metEntityList = mutableListOf<Int>()

@KtorExperimentalAPI
fun sharedEsp() {
    WebSocket(precheck = { inGame && opened && curSettings.bool["SHARED_ESP"] && curSettings.bool["ENABLE_ESP"] }, body = {
        val mySid = me.getValidSteamID()

        if (mySid == 0) return@WebSocket
        metEntityList.clear()

        //send what we have to server
        var s = "iterateEntities:${mySid}:${meTeam}:"
        forEntities(EntityType.CCSPlayer) {
            val ent = it.entity
            if (ent.dormant() || ent.dead() || ent <= 0 || ent == me) return@forEntities
            if (ent.team() == meTeam) return@forEntities
            val tmpSid = ent.getValidSteamID()
            if (tmpSid == 0) return@forEntities
            s += "${tmpSid}:${ent.absPosition()}:"

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
            val entPos = Vector(myArray[index + 1].toFloat(), myArray[index + 2].toFloat(), myArray[index + 3].toFloat())
            val listIdx = allocSharedPlayerRecord(realSid)
            if (sharedPlayerRecords[listIdx].alpha <= 0F && sharedPlayerRecords[listIdx].pos != entPos) {
                sharedPlayerRecords[listIdx].apply {
                    pos = entPos
                    alpha = 1F
                }
            }
        }
    })
    App {
        sharedPlayerRecords.forEachIndexed { idx, it ->
            if (it.alpha > 0F) {
                val w2s1 = Vector()
                val w2s2 = Vector()

                if (worldToScreen(it.pos, w2s1) && worldToScreen(Vector(it.pos.x, it.pos.y, it.pos.z - 75F), w2s2)) {
                    if (shapeRenderer.isDrawing) shapeRenderer.end()
                    shapeRenderer.begin()

                    GL11.glEnable(GL20.GL_BLEND)

                    val h = w2s1.y - w2s2.y
                    val w = abs(h / 5F * 2F)

                    shapeRenderer.color = Color(1F, 1F, 1F, it.alpha)
                    shapeRenderer.rect(w2s1.x - w/2F, w2s1.y, w, h)

                    shapeRenderer.end()
                }

                it.alpha -= .01F
            }
            else {
                sharedPlayerRecords.removeAt(idx)
            }
        }
    }
}
@KtorExperimentalAPI
fun allocSharedPlayerRecord(steamID: Int): Int {
    sharedPlayerRecords.forEachIndexed { idx, i ->
        if (i.steamID == steamID) {
            return idx
        }
    }
    sharedPlayerRecords.add(SharedPlayer(steamID = steamID))
    return sharedPlayerRecords.size - 1
}