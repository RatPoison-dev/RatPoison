package rat.plague.overlay.transparency.win7

import com.sun.jna.platform.win32.WinDef
import org.jire.arrowhead.Struct

class DWM_BLURBEHIND : Struct() {
	
	@JvmField var dwFlags: WinDef.DWORD? = null
	@JvmField var fEnable = false
	@JvmField var hRgnBlur: WinDef.HRGN? = null
	@JvmField var fTransitionOnMaximized = false
	
}