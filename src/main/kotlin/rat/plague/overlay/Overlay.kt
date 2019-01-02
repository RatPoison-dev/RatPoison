

package rat.plague.overlay

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import rat.plague.game.CSGO.gameHeight
import rat.plague.game.CSGO.gameWidth
import rat.plague.game.CSGO.gameX
import rat.plague.game.CSGO.gameY
import rat.plague.overlay.transparency.TransparencyApplier
import rat.plague.overlay.transparency.win10.Win10TransparencyApplier
import rat.plague.overlay.transparency.win7.Win7TransparencyApplier
import rat.plague.settings.OPENGL_FPS
import rat.plague.settings.OPENGL_MSAA_SAMPLES
import rat.plague.settings.OPENGL_VSYNC
import rat.plague.utils.randLong
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef

object Overlay {
	
	@Volatile var opened = false
	
	lateinit var hwnd: WinDef.HWND
	
	fun open() = LwjglApplicationConfiguration().apply {
		width = gameWidth
		height = gameHeight
		title = randLong(Long.MAX_VALUE).toString()
		x = gameX
		y = gameY
		resizable = false
		fullscreen = false
		vSyncEnabled = OPENGL_VSYNC
		if (OPENGL_MSAA_SAMPLES > 0)
			samples = OPENGL_MSAA_SAMPLES
		
		foregroundFPS = OPENGL_FPS
		backgroundFPS = OPENGL_FPS
		
		LwjglApplication(RatPlagueOverlay, this)
		
		do {
			val hwnd = User32.INSTANCE.FindWindow(null, title)
			if (hwnd != null) {
				Overlay.hwnd = hwnd
				break
			}
			Thread.sleep(64) // decreased so it won't go black as long
		} while (!Thread.interrupted())
		
		// sets up window to be fullscreen, click-through, etc.
		WindowCorrector.setupWindow(hwnd)
		
		
		// sets up the full transparency of the Window (only Windows 7 and 10 can do this)
		val transparencyApplier: TransparencyApplier =
				if (System.getProperty("os.name").contains("windows 10", ignoreCase = true))
					Win10TransparencyApplier
				else
					Win7TransparencyApplier // will only work on Windows 7 or early Windows 10 builds
		transparencyApplier.applyTransparency(hwnd)
		
		opened = true
	}
	
	init {
		System.setProperty("org.lwjgl.opengl.Window.undecorated", "true")
	}
	
}