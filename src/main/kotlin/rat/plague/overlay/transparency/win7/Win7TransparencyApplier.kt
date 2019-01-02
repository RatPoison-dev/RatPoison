

package rat.plague.overlay.transparency.win7

import rat.plague.overlay.transparency.TransparencyApplier
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.User32.INSTANCE as User32

object Win7TransparencyApplier : TransparencyApplier {
	
	override fun applyTransparency(hwnd: WinDef.HWND) = DWM_BLURBEHIND().run {
		dwFlags = WinDef.DWORD(DWM.DWM_BB_ENABLE)
		fEnable = true
		hRgnBlur = null
		DWM.DwmEnableBlurBehindWindow(hwnd, this).toInt() == 0
	}
	
}