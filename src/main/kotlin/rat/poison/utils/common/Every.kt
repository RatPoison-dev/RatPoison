package rat.poison.utils.common

import rat.poison.haltProcess

@Volatile
var inBackground = false
@Volatile
var inGame = false
@Volatile
var shouldPostProcess = false
@Volatile
var inFullscreen = false

inline fun every(duration: Int, continuous: Boolean = false, inGameCheck: Boolean = false, crossinline body: () -> Unit) = Thread {
    while (!Thread.interrupted()) {
        if ((continuous || !inBackground) && !haltProcess && ((inGameCheck && inGame) || !inGameCheck)) {
            try {
                body()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        Thread.sleep(duration.toLong())
    }
}.start()