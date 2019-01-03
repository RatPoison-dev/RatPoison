@file:JvmName("RatPlague")

package rat.plague

import rat.plague.game.CSGO
import rat.plague.overlay.Overlay
import rat.plague.scripts.*
import rat.plague.scripts.aim.flatAim
import rat.plague.scripts.aim.pathAim
import rat.plague.scripts.esp.*
import rat.plague.settings.*
import rat.plague.utils.Dojo
import com.sun.jna.platform.win32.WinNT
import org.jetbrains.kotlin.cli.common.environment.setIdeaIoUseFallback
import java.io.File
import java.io.FileReader

const val SETTINGS_DIRECTORY = "settings"

fun main(args: Array<String>) {
    loadSettings()

    CSGO.initialize()

    bunnyHop()
    rcs()
    esp()
    flatAim()
    pathAim()
    boneTrigger()
    reducedFlash()
    bombTimer()
    espToggle()
    scanner()

    if (LEAGUE_MODE) {
        GLOW_ESP = false
        BOX_ESP = false
        SKELETON_ESP = false
        CHAMS_ESP = false
        ENABLE_ESP = false

        ENABLE_BOMB_TIMER = false
        ENABLE_REDUCED_FLASH = false
        ENABLE_FLAT_AIM = false

        SERVER_TICK_RATE = 128 // most leagues are 128-tick
        PROCESS_ACCESS_FLAGS = WinNT.PROCESS_QUERY_INFORMATION or WinNT.PROCESS_VM_READ // all we need
        GARBAGE_COLLECT_ON_MAP_START = true // get rid of traces
    }
}


fun loadSettings() {
	setIdeaIoUseFallback()
	
	File(SETTINGS_DIRECTORY).listFiles().forEach {
		FileReader(it).use {
			Dojo.script(it
					.readLines()
					.joinToString("\n"))
		}
	}
	
	val needsOverlay = ENABLE_BOMB_TIMER or (ENABLE_ESP and (SKELETON_ESP or BOX_ESP))
	if (!Overlay.opened && needsOverlay) Overlay.open()
}
