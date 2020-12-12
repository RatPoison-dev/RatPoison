package rat.poison.overlay

import com.sun.jna.Native
import org.jire.arrowhead.Struct

object DWM {

    @JvmStatic
    external fun DwmEnableBlurBehindWindow(hWnd: Long, pBlurBehind: DWM_BLURBEHIND): Int

    init {
        Native.register("Dwmapi")
    }

    const val DWM_BB_ENABLE = 0x00000001L
    const val DWM_BB_BLURREGION = 0x00000002L
    const val DWM_BB_TRANSITIONONMAXIMIZED = 0x00000004L

}

class DWM_BLURBEHIND : Struct() {

    @JvmField var dwFlags: Long? = null
    @JvmField var fEnable = false
    @JvmField var hRgnBlur: Long? = null
    @JvmField var fTransitionOnMaximized = false

}

fun win7transparency(hwnd: Long) = DWM_BLURBEHIND().run {
    dwFlags = DWM.DWM_BB_ENABLE
    fEnable = true
    hRgnBlur = null
    DWM.DwmEnableBlurBehindWindow(hwnd, this) == 0
}