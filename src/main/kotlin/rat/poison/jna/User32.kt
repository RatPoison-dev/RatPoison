////Courtesy of Mr Noad

package rat.poison.jna

import com.sun.jna.Native
import com.sun.jna.ptr.IntByReference
import com.sun.jna.win32.StdCallLibrary
import rat.poison.jna.structures.Rect
import rat.poison.jna.structures.WindowCompositionAttributeData

class AccentFlags {
    val Transparent = 2
    val DrawLeftBorder = 0x20
    val DrawTopBorder = 0x40
    val DrawRightBorder = 0x80
    val DrawBottomBorder = 0x100
    val DrawAllBorders = (DrawLeftBorder or DrawTopBorder or DrawRightBorder or DrawBottomBorder)
}

val accentFlags = AccentFlags()


const val WS_EX_TOOLWINDOW = 0x00000080
const val WS_EX_TOPMOST = 0x00000008
const val HWND_TOPPOS = -1L
const val HWND_ZERO = 0L

object User32 {
    //rewrote methods to get rid of WinNT.HWND and other wrapper classes,
    //they create shared memory instances in the stack, causing terrible
    //garbage collection cycles!
    init {
        Native.register("user32")
    }

    interface WndEnumProc : StdCallLibrary.StdCallCallback {
        fun callback(hwnd: Long): Boolean
    }

    external fun SetWindowCompositionAttribute(hwnd: Long, data: WindowCompositionAttributeData): Long
    external fun SetWindowDisplayAffinity(hwnd: Long, dwAffinity: Long): Boolean
    external fun SetActiveWindow(hwnd: Long): Long
    external fun FindWindowA(s: String?, s1: String?): Long
    external fun GetWindowLongA(hwnd: Long, i: Int): Int
    external fun SetWindowLongA(hwnd: Long, i: Int, i1: Int): Int
    external fun SetWindowPos(hwnd: Long, hwnd1: Long, i: Int, i1: Int, i2: Int, i3: Int, i4: Int): Boolean
    external fun SetForegroundWindow(hwnd: Long): Boolean
    external fun SetFocus(hwnd: Long): Long
    external fun IsWindowVisible(hwnd: Long): Boolean
    external fun ShowWindow(hwnd: Long, i: Int): Boolean
    external fun GetClientRect(hwnd: Long, rect: Rect): Boolean
    external fun GetWindowRect(hwnd: Long, rect: Rect): Boolean
    external fun GetWindowThreadProcessId(hwnd: Long, intByReference: IntByReference?): Int
    external fun AttachThreadInput(dword: Long, dword1: Long, b: Boolean): Boolean
    external fun EnumWindows(enumProc: WndEnumProc): Boolean
}
