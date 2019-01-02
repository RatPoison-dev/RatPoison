

import rat.plague.settings.*
import com.sun.jna.platform.win32.WinNT

/**
 * These should be set the same as your in-game "m_pitch" and "m_yaw" values.
 */
GAME_PITCH = 0.022 // m_pitch
GAME_YAW = 0.022 // m_yaw

/**
 * The tick ratio of the server.
 *
 * This isn't that important to set, but is recommended.
 */
SERVER_TICK_RATE = 64

/**
 * The maximum amount of entities that can be managed by the cached list.
 */
MAX_ENTITIES = 4096

/**
 * The interin milliseconds to recycle entities in the cached list.
 */
CLEANUP_TIME = 10_000


/**
 * Whether or not garbage collect
 * after every map load startup.
 */
GARBAGE_COLLECT_ON_MAP_START = true

/**
 * The process name of CS:GO
 */
PROCESS_NAME = "csgo.exe"

/**
 * The process flags to open the handle to CS:GO with.
 */
PROCESS_ACCESS_FLAGS = WinNT.PROCESS_QUERY_INFORMATION or WinNT.PROCESS_VM_READ or WinNT.PROCESS_VM_WRITE

/**
 * The module name of the client module.
 */
CLIENT_MODULE_NAME = "client_panorama.dll"

/**
 * The module name of the engine module.
 */
ENGINE_MODULE_NAME = "engine.dll"