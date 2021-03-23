package rat.poison.utils

import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.util.*
import it.unimi.dsi.fastutil.objects.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import rat.poison.curSettings
import rat.poison.haltProcess
import rat.poison.overlay.webSocketTime
import java.util.concurrent.TimeUnit
import kotlin.system.measureNanoTime


data class Body(val mainFunction: suspend WebSocket.(DefaultClientWebSocketSession) -> Unit, val delay: Int, val precheck: () -> Boolean)

private const val apiVersion = "v1.0"
object WebSocket {
    lateinit var webSocketSession: DefaultClientWebSocketSession
    lateinit var webSocketClient: HttpClient
    private var bodies = ObjectArrayList<Body>()
    var sendTasks: ObjectList<String> = ObjectLists.synchronize(ObjectList.of(""))


    fun initialize() = Thread {
        sendTasks.removeAt(0)
        webSocketClient = HttpClient {
            install(WebSockets)
        }
        GlobalScope.launch {
            webSocketClient.ws(
                method = HttpMethod.Get,
                host = curSettings["WEBSOCKET_SERVER"],
                port = 8080, path = ""
            ) {
                webSocketSession = this
                webSocketSession.send("version:${apiVersion}")
                val wsVersion = (webSocketSession.incoming.receive() as Frame.Text).readText()
                if (wsVersion != apiVersion) {
                    println("[WARNING] WebSocket Api Version is deprecated. Please update RatPoison")
                }
                while (!haltProcess) {
                    webSocketTime = TimeUnit.NANOSECONDS.convert(measureNanoTime {
                        for (i in 0 until sendTasks.size) {
                            val thisStr = sendTasks[i]
                            webSocketSession.send(thisStr)
                            sendTasks.removeAt(i)
                        }
                        bodies.forEach {
                            if (!it.precheck()) return@forEach
                            val coroutine = async {
                                it.mainFunction.invoke(this@WebSocket, webSocketSession)
                            }
                            coroutine.start()
                            coroutine.await()
                        }
                    }, TimeUnit.NANOSECONDS)
                    delay(minDelay())
                }
            }
        }
    }.start()

    private fun minDelay(): Long {
        var min = Long.MAX_VALUE
        bodies.forEach {
            if (!it.precheck()) return@forEach
            if (it.delay < min) min = it.delay.toLong()
        }
        if (min == Long.MAX_VALUE) return 10L
        return min
    }

    operator fun invoke(delay: Int = 10, precheck: () -> Boolean = {true}, body: suspend WebSocket.(DefaultClientWebSocketSession) -> Unit, ) {
        bodies.add(Body(body, delay, precheck))
    }

    fun createSendTask(str: String) {
        sendTasks.add(str)
    }

}