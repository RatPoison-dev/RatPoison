package rat.plague.overlay.transparency.win7

import com.sun.jna.Native
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinNT

object DWM {
	
	@JvmStatic
	external fun DwmEnableBlurBehindWindow(hWnd: WinDef.HWND, pBlurBehind: DWM_BLURBEHIND): WinNT.HRESULT
	
	init {
		Native.register("Dwmapi")
	}
	
	const val DWM_BB_ENABLE = 0x00000001L
	const val DWM_BB_BLURREGION = 0x00000002L
	const val DWM_BB_TRANSITIONONMAXIMIZED = 0x00000004L
	
}