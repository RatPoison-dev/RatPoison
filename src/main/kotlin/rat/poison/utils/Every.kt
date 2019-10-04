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
        if (continuous || (!inBackground)) {
            try {
                body()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        Thread.sleep(minDuration.toLong() + maxDuration.toLong())
    }
}