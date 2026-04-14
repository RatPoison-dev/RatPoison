package rat.poison.jna

import com.sun.jna.Native
import com.sun.jna.Pointer

const val HIGH_PRIORITY_CLASS = 0x00000080

object Kernel32 {
    init {
        Native.register("kernel32")
    }

    external fun SetPriorityClass(hProcess: Pointer, dwPriorityClass: Int): Boolean
    external fun GetCurrentProcess(): Pointer

    fun raiseProcessPriority() {
        SetPriorityClass(GetCurrentProcess(), HIGH_PRIORITY_CLASS)
    }
}