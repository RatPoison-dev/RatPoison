package rat.poison.utils

@Volatile
var inBackground = false
@Volatile
var notInGame = false

inline fun every(duration: Int, continuous: Boolean = false, crossinline body: () -> Unit) = Thread(Runnable {
    while (!Thread.interrupted()) {
        if (continuous || (!inBackground)) {
            try {
                body()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        Thread.sleep(duration.toLong())
    }
}).start()