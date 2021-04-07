package rat.poison.game

import com.sun.jna.Pointer
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.ptr.LongByReference
import org.jire.arrowhead.Module
import org.jire.arrowhead.Process
import org.jire.arrowhead.processByName
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.game.hooks.constructEntities
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.game.netvars.NetVars
import rat.poison.jna.QUNS_RUNNING_D3D_FULL_SCREEN
import rat.poison.jna.Shell32
import rat.poison.settings.CLIENT_MODULE_NAME
import rat.poison.settings.ENGINE_MODULE_NAME
import rat.poison.settings.PROCESS_ACCESS_FLAGS
import rat.poison.settings.PROCESS_NAME
import rat.poison.utils.common.*
import rat.poison.utils.natives.CUser32
import kotlin.system.exitProcess

object CSGO {
	const val ENTITY_SIZE = 16
	const val GLOW_OBJECT_SIZE = 56

	lateinit var csgoEXE: Process
		private set

	lateinit var clientDLL: Module
		private set
	lateinit var engineDLL: Module
		private set

	var gameHeight: Int = 0
		private set

	var gameX: Int = 0
		private set

	var gameWidth: Int = 0
		private set

	var gameY: Int = 0
		private set

	var initialized: Boolean = false
		private set

	lateinit var rect: WinDef.RECT

	fun initialize() {


		after(10000) {
			if (!initialized) {
				println("You are stuck at waiting for ${curSettings["MENU_APP"]}?\n" +
						"Make sure you have checked all of those steps:\n" +
						"- CS:GO is running \n" +
						"- you are running currently most up-to-date version of RatPoison\n" +
						"- you disabled all anti-cheat clients working on your computer\n" +
						"- your RatPoison folder is placed somewhere with all running permissions\n" +
						"- you don't use RatPoison with some other cheats running\n" +
						"- you aren't currently running VAC bypass (running the bat file with administrator privileges should work)\n" +
						"- you restarted your computer\n\n" +
						"If nothing else works then you can try running the bat file as admin.")
			}
		}

		retry(128) {
			csgoEXE = processByName(PROCESS_NAME, PROCESS_ACCESS_FLAGS)!!
		}

		retry(128) {
			csgoEXE.loadModules()
			engineDLL = csgoEXE.modules[ENGINE_MODULE_NAME]!!
			clientDLL = csgoEXE.modules[CLIENT_MODULE_NAME]!!
		}
		initialized = true

		rect = WinDef.RECT()
		val hwd = CUser32.FindWindowA(null, "Counter-Strike: Global Offensive")

		//Get initially
		if (!CUser32.GetClientRect(hwd, rect)) exitProcess(2)
		gameWidth = rect.right - rect.left
		gameHeight = rect.bottom - rect.top

		if (!CUser32.GetWindowRect(hwd, rect)) exitProcess(3)
		gameX = rect.left + (((rect.right - rect.left) - gameWidth) / 2)
		gameY = rect.top + ((rect.bottom - rect.top) - gameHeight)

		every(1000) {
			if (!CUser32.GetClientRect(hwd, rect)) exitProcess(2)
			gameWidth = rect.right - rect.left
			gameHeight = rect.bottom - rect.top

			if (!CUser32.GetWindowRect(hwd, rect)) exitProcess(3)
			gameX = rect.left + (((rect.right - rect.left) - gameWidth) / 2)
			gameY = rect.top + ((rect.bottom - rect.top) - gameHeight)
		}

		every(1000, continuous = true) {
			inBackground = Pointer.nativeValue(hwd.pointer) != CUser32.GetForegroundWindow()
			with (Shell32) {
				val state = LongByReference()
				SHQueryUserNotificationState(state)
				inFullscreen = state.value == QUNS_RUNNING_D3D_FULL_SCREEN
			}
		}

		NetVars.load()

		constructEntities()
		updateCursorEnable()

		if (dbg) println("[DEBUG] CSGO initialized")
	}
}