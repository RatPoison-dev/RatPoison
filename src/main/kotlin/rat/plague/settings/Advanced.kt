package rat.plague.settings

import rat.plague.game.CSGO.clientDLL
import rat.plague.game.offsets.ClientOffsets.dwSensitivity
import rat.plague.game.offsets.ClientOffsets.dwSensitivityPtr
import rat.plague.utils.extensions.uint
import com.sun.jna.platform.win32.WinNT

/**
 * These should be set the same as your in-game "m_pitch" and "m_yaw" values.
 */
var GAME_PITCH = 0.022 // m_pitch
var GAME_YAW = 0.022 // m_yaw

val GAME_SENSITIVITY by lazy(LazyThreadSafetyMode.NONE) {
	val pointer = clientDLL.address + dwSensitivityPtr
	val value = clientDLL.uint(dwSensitivity) xor pointer
	
	java.lang.Float.intBitsToFloat(value.toInt()).toDouble()
}

/**
 * The tick ratio of the server.
 */
var SERVER_TICK_RATE = 64

/**
 * The maximum amount of entities that can be managed by the cached list.
 */
var MAX_ENTITIES = 4096

/**
 * The interval in milliseconds to recycle entities in the cached list.
 */
var CLEANUP_TIME = 10_000

/**
 * Whether or not garbage collect
 * after every map load startup.
 */
var GARBAGE_COLLECT_ON_MAP_START = true

/**
 * The process name of CS:GO
 */
var PROCESS_NAME = "csgo.exe"

/**
 * The process flags to open the handle to CS:GO with.
 */
var PROCESS_ACCESS_FLAGS = WinNT.PROCESS_QUERY_INFORMATION or WinNT.PROCESS_VM_READ or WinNT.PROCESS_VM_WRITE

/**
 * The module name of the client module.
 */
var CLIENT_MODULE_NAME = "client_panorama.dll"

/**
 * The module name of the engine module.
 */
var ENGINE_MODULE_NAME = "engine.dll"