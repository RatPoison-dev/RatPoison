package rat.poison.utils.extensions

import com.sun.jna.Memory

fun Memory?.readable() = null != this

fun Memory.getFloatArray(offset: Long, arraySize: Int, floatArray: FloatArray): FloatArray {
    read(offset, floatArray, 0, arraySize)
    return floatArray
}