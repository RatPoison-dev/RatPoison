package rat.poison.utils.extensions

import org.jire.kna.ReadableSource
import org.jire.kna.int
import rat.poison.scripts.toIndex

fun ReadableSource.uint(address: Long, offset: Long = 0) = int(address, offset).unsign()
fun ReadableSource.readIndex(address: Long) = int(address).toIndex()