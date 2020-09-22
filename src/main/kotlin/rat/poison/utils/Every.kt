package rat.poison.utils

import rat.poison.haltProcess

@Volatile
var inBackground = false
@Volatile
var inGame = true
@Volatile
var shouldPostProcess = false

inline fun every(duration: Int, continuous: Boolean = false, crossinline body: () -> Unit) = Thread(Runnable {
    while (!Thread.interrupted()) {
        if ((continuous || !inBackground) && !haltProcess) {
            try {
                body()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        Thread.sleep(duration.toLong())
    }
}).start()