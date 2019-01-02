package rat.plague.overlay.transparency

import com.sun.jna.platform.win32.WinDef

interface TransparencyApplier {
	
	fun applyTransparency(hwnd: WinDef.HWND): Boolean
	
}