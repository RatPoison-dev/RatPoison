package rat.poison.utils.extensions

import com.sun.jna.Memory

fun Memory?.readable() = null != this

fun Memory.int(offset: Long) = getInt(offset)
fun Memory.uint(offset: Long) = int(offset).unsign()