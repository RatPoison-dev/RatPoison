package rat.poison.utils

inline fun <R> after(duration: Long = 1000, crossinline body: () -> R) = Thread {
    if (!Thread.interrupted()) {
        try {
            Thread.sleep(duration)
            body()
        }
        catch (t: Throwable) {
            t.printStackTrace()
        }
    }
}.start()