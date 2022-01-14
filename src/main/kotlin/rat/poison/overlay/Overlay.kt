/////////////////////////////////
////This was provided by Mr. Noad
/////////////////////////////////
package rat.poison.overlay

import com.sun.jna.platform.win32.WinUser
import rat.poison.DEFAULT_MENU_APP
import rat.poison.MENU_APP
import rat.poison.appless
import rat.poison.curSettings
import rat.poison.interfaces.IOverlay
import rat.poison.interfaces.IOverlayListener
import rat.poison.jna.*
import rat.poison.jna.enums.AccentStates
import rat.poison.jna.structures.Rect
import rat.poison.jna.structures.WindowCompositionAttributeData
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.inBackground
import kotlin.concurrent.thread
import kotlin.properties.Delegates

class Overlay(private val targetAppTitle: String, private val myAppTitle: String, private var accentStateWhenActive: AccentStates = AccentStates.ACCENT_ENABLE_ACRYLIC) : IOverlay {
	private var myHWND = HWND_ZERO
	private var targetAppHWND = HWND_ZERO
	private val rcClient = Rect()
	private val rcWindow = Rect()
	private var x: Int = 0
	private var y: Int = 0
	var width: Int = 0
	var height: Int = 0
	private var initialWidth: Int = 0
	private var initialHeight: Int = 0
	private var initialWindowStyle: Int = 0
	private var initialWindowExStyle: Int = 0
	var listener: IOverlayListener? = null
	private var firstRun = true

	@Volatile
	private var run = false
	private var overlayWindowMonitorThread: Thread? = null

	override val haveTargetWindow: Boolean
		get() = targetAppHWND != HWND_ZERO

	override var clickThrough: Boolean by Delegates.observable(false) { _, _, value ->
		if (haveTargetWindow) {
			if (value) {
				bePassive()
			} else {
				beActive()
			}
		}
	}

	override var protectAgainstScreenshots: Boolean by Delegates.observable(false) { _, _, _ /* value */ ->
		//if (EXPERIMENTAL) {
		//	with(User32) {
		//		SetWindowDisplayAffinity(myHWND, if (value) 1 else 0)
		//	}
		//}
	}

	fun start() {
		val me = getWindowHWND(myAppTitle)
		if (me == HWND_ZERO) throw RuntimeException("Cannot find $myAppTitle")
		myHWND = me
		if (overlayWindowMonitorThread != null) stop()
		run = true
		overlayWindowMonitorThread = thread(name = "OverlayWindowMonitorThread") {
			try {
				while (run && !Thread.interrupted()) {
					Thread.sleep(8)
					monitorTargetApp()
				}
			} catch (e: InterruptedException) { println("InterruptedException"); e.printStackTrace() } catch (e: Exception) { println("StandardException"); e.printStackTrace() }
			run = false
			println("${Thread.currentThread().name} died!")
		}
	}

	fun stop() {
		try {
			if (run) {
				run = false
				overlayWindowMonitorThread?.apply { interrupt(); join() }
			}
		} catch (e: Exception) { e.printStackTrace() }
		overlayWindowMonitorThread = null
		targetAppHWND = HWND_ZERO
	}

	private fun init() {
		println("Initializing overlay")

		saveStyle()

		if (!appless) {
			makeUndecorated()
		}

		beActive()

		listener?.onAfterInit(this)

		println("Overlay initialized")
	}

	private fun monitorTargetApp() = with(User32) {
		if (targetAppHWND == HWND_ZERO) {
			println("Waiting for App")
			targetAppHWND = getWindowHWND(targetAppTitle, Long.MAX_VALUE)
			if (targetAppHWND == HWND_ZERO) {
				return@with
			}
			init()
		}

		val oldWidth = width
		val oldHeight = height
		val oldX = x
		val oldY = y

		if (GetClientRect(targetAppHWND, rcClient) && GetWindowRect(targetAppHWND, rcWindow)) {
			//shitty DWM does not draw background windows if the top window bounds is same
			//as screen bounds. Doesn't matter whether the top window is layered or not,
			//hence we broke the equation so our overlay won't go opaque with a black background...
			width = rcClient.right - rcClient.left + 2
			height = rcClient.bottom - rcClient.top + 2
			x = rcWindow.left + (rcWindow.right - rcWindow.left - width) / 2 - 1
			y = rcWindow.top + rcWindow.bottom - rcWindow.top - height - 1

			if (!appless) {
				if (oldX != x || oldY != y || oldWidth != width || oldHeight != height) {
					SetWindowPos(myHWND, HWND_TOPPOS, x, y, width, height, WinUser.SWP_NOSENDCHANGING or WinUser.SWP_NOZORDER or WinUser.SWP_DEFERERASE or WinUser.SWP_NOREDRAW or WinUser.SWP_ASYNCWINDOWPOS or WinUser.SWP_FRAMECHANGED)
					listener?.onBoundsChange(this@Overlay, x, y, width, height)
				}
				val isMyWindowVisible = IsWindowVisible(myHWND)
				if (getActiveWindow() == targetAppHWND) {
					if (!isMyWindowVisible) {
						ShowWindow(myHWND, WinUser.SW_SHOW)
						listener?.onForeground(this@Overlay)
						if (!clickThrough) beActive()
					}
				} else {
					if (isMyWindowVisible) {
						if (MENU_APP == DEFAULT_MENU_APP) {
							if (!curSettings["MENU_STAY_FOCUSED"].strToBool()) {
								ShowWindow(myHWND, WinUser.SW_HIDE)
								listener?.onBackground(this@Overlay)
							}
						}
					}
				}
			}
		} else {
			listener?.onTargetAppWindowClosed(this@Overlay)
			targetAppHWND = HWND_ZERO
			inBackground = true
			restoreStyle()
			makeOpaque()
		}
	}

	private fun saveStyle() = with(User32) {
		initialWindowStyle = GetWindowLongA(myHWND, WinUser.GWL_STYLE)
		initialWindowExStyle = GetWindowLongA(myHWND, WinUser.GWL_EXSTYLE)
		GetClientRect(myHWND, rcClient)
		initialWidth = rcClient.right - rcClient.left
		initialHeight = rcClient.bottom - rcClient.top
	}

	private fun restoreStyle() = with(User32) {
		protectAgainstScreenshots = false
		clickThrough = false
		SetWindowLongA(myHWND, WinUser.GWL_STYLE, initialWindowStyle)
		SetWindowLongA(myHWND, WinUser.GWL_EXSTYLE, initialWindowExStyle)
		val dimension = java.awt.Toolkit.getDefaultToolkit().screenSize
		val x = ((dimension.getWidth() - initialWidth) / 2).toInt()
		val y = ((dimension.getHeight() - initialHeight) / 2).toInt()
		SetWindowPos(myHWND, HWND_ZERO, x, y, initialWidth, initialHeight, WinUser.SWP_SHOWWINDOW)

		SetForegroundWindow(myHWND)
		SetActiveWindow(myHWND)
		SetFocus(myHWND)
	}

	private fun makeUndecorated() = with(User32) {
		var gwl = GetWindowLongA(myHWND, WinUser.GWL_STYLE)
		gwl = gwl and WinUser.WS_OVERLAPPEDWINDOW.inv()
		SetWindowLongA(myHWND, WinUser.GWL_STYLE, gwl)
	}

	private fun makeTransparent() = with(User32) {
		SetWindowCompositionAttribute(
			myHWND,
			WindowCompositionAttributeData(
					AccentState = AccentStates.ACCENT_ENABLE_TRANSPARENTGRADIENT,
					AccentFlags = 2
			)
		)
	}

	private fun makeBlurBehind() = with(User32) {
		SetWindowCompositionAttribute(
			myHWND,
			WindowCompositionAttributeData(
					AccentState = accentStateWhenActive,
					AccentFlags = 2
			)
		)
	}

	private fun makeOpaque() = with(User32) {
		SetWindowCompositionAttribute(myHWND, WindowCompositionAttributeData(AccentState = AccentStates.ACCENT_DISABLED, AccentFlags = AccentFlag_DrawAllBorders))
	}

	private fun getWindowHWND(windowName: String, timeout: Long = 3000L): Long = with(User32) {
		val start = System.currentTimeMillis()
		do {
			val hwnd = FindWindowA(null, windowName)
			if (hwnd != HWND_ZERO) {
				return hwnd
			}
			try {
				Thread.sleep(8)
			} catch (e: InterruptedException) { e.printStackTrace() }
		} while (!Thread.interrupted() && System.currentTimeMillis() - start < timeout)
		return HWND_ZERO
	}


	private class ActiveWindowCallback(private val resetValue: Long = HWND_ZERO) : User32.WndEnumProc {
		var activeWindowHWND = HWND_ZERO
			private set

		fun reset() {
			activeWindowHWND = resetValue
		}

		private fun isTopMost(hwnd: Long): Boolean =
				(User32.GetWindowLongA(hwnd, WinUser.GWL_EXSTYLE) and WS_EX_TOPMOST) != 0

		override fun callback(hwnd: Long): Boolean {
			val topMost = isTopMost(hwnd)
			if (!topMost) {
				activeWindowHWND = hwnd
			}
			return topMost
		}
	}

	private val activeWindowCallback = ActiveWindowCallback()

	private fun getActiveWindow() = with(User32) {
		if (firstRun) {
			firstRun = false
			return@with targetAppHWND
		}
		activeWindowCallback.reset()
		EnumWindows(activeWindowCallback)
		activeWindowCallback.activeWindowHWND
	}

	private fun beActive() = with(User32) {
		if (curSettings["GAUSSIAN_BLUR"].strToBool()) {
			makeBlurBehind()
		}

		if (targetAppHWND != HWND_ZERO) {
			SetWindowLongA(myHWND, WinUser.GWL_EXSTYLE, WS_EX_TOOLWINDOW or WS_EX_TOPMOST)

			val dwCurrentThread = GetWindowThreadProcessId(myHWND, null)
			val dwFGThread = GetWindowThreadProcessId(targetAppHWND, null)
			AttachThreadInput(dwCurrentThread.toLong(), dwFGThread.toLong(), true)

			SetForegroundWindow(myHWND)
			SetActiveWindow(myHWND)
			SetFocus(myHWND)

			AttachThreadInput(dwCurrentThread.toLong(), dwFGThread.toLong(), false)

			if (!appless) {
				SetWindowPos(myHWND, HWND_TOPPOS, x, y, width, height, 0)
			}

			listener?.onActive(this@Overlay)
		}
	}

	private fun bePassive() = with(User32) {
		if (!appless) {
			SetWindowLongA(myHWND, WinUser.GWL_EXSTYLE, WinUser.WS_EX_LAYERED or WinUser.WS_EX_TRANSPARENT or WS_EX_TOOLWINDOW or WS_EX_TOPMOST)
			makeTransparent()
		}

		if (targetAppHWND != HWND_ZERO) {
			if (getActiveWindow() == targetAppHWND) {
				SetForegroundWindow(targetAppHWND)
				SetActiveWindow(targetAppHWND)
				SetFocus(targetAppHWND)
			}
		}

		listener?.onPassive(this@Overlay)
	}
}