

package rat.plague.overlay.transparency.win10

import rat.plague.overlay.transparency.TransparencyApplier
import com.sun.jna.NativeLibrary
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinNT.HRESULT

object Win10TransparencyApplier : TransparencyApplier {
	
	// CREDITS: https://gist.github.com/Guerra24/429de6cadda9318b030a7d12d0ad58d4
	
	override fun applyTransparency(hwnd: WinDef.HWND): Boolean {
		val user32 = NativeLibrary.getInstance("user32")
		
		val accent = AccentPolicy()
		accent.AccentState = AccentState.ACCENT_ENABLE_TRANSPARENTGRADIENT
		accent.AccentFlags = 2 // must be 2 for transparency
		accent.GradientColor = 0 // ARGB color code for gradient
		val accentStructSize = accent.size()
		accent.write()
		val accentPtr = accent.pointer
		
		val data = WindowCompositionAttributeData()
		data.Attribute = WindowCompositionAttribute.WCA_ACCENT_POLICY
		data.SizeOfData = accentStructSize
		data.Data = accentPtr
		
		val setWindowCompositionAttribute = user32.getFunction("SetWindowCompositionAttribute")
		val result = setWindowCompositionAttribute(HRESULT::class.java, arrayOf(hwnd, data)) as HRESULT
		return 1 == result.toInt()
	}
	
}