package rat.poison.utils

import org.jire.kna.Pointer

fun threadLocalPointer(size: Long): ThreadLocal<Pointer> = ThreadLocal.withInitial { Pointer.alloc(size) }