package rat.poison.utils

import rat.poison.settings.MENUTOG
import java.util.concurrent.ThreadLocalRandom
import kotlin.concurrent.thread

@Volatile
var inBackground = false
@Volatile
var notInGame = false

inline fun every(duration: Int, continuous: Boolean = false,
                 crossinline body: () -> Unit) = every(duration, duration, continuous, body)

inline fun every(minDuration: Int, maxDuration: Int,
                 continuous: Boolean = false,
                 crossinline body: () -> Unit) = thread {
    while (!Thread.interrupted()) {
        if (continuous || (!MENUTOG && !inBackground)) {
            try {
                body()
            } catch (e: Exception) {
                //println(e)
            }
        }

        Thread.sleep(minDuration.toLong() + maxDuration.toLong())
    }
}