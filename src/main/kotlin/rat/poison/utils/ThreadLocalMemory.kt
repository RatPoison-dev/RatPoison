package rat.poison.utils

import com.sun.jna.Memory

fun threadLocalMemory(size: Long): ThreadLocal<Memory> = ThreadLocal.withInitial { Memory(size) }