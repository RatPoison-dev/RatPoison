package rat.poison.utils.extensions

import org.jire.kna.Pointer

fun Pointer.int(offset: Long) = getInt(offset)
fun Pointer.uint(offset: Long) = int(offset).unsign()