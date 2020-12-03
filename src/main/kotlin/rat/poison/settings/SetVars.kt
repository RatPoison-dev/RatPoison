package rat.poison.settings

import com.sun.jna.platform.win32.WinNT

var GAME_PITCH = 0.022 // m_pitch
var GAME_YAW = 0.022 // m_yaw
var GAME_SENSITIVITY = 1.0
var HEAD_BONE = 8
var NECK_BONE = 7
var CHEST_BONE = 6
var STOMACH_BONE = 5
var NEAREST_BONE = -1
var RANDOM_BONE = -4

var AIM_KEY = 1

var SERVER_TICK_RATE = 64
var MAX_ENTITIES = 4096
var CLEANUP_TIME = 5_000
var GARBAGE_COLLECT_ON_MAP_START = true
var PROCESS_NAME = "csgo.exe"
var PROCESS_ACCESS_FLAGS = WinNT.PROCESS_QUERY_INFORMATION or WinNT.PROCESS_VM_READ or WinNT.PROCESS_VM_WRITE or WinNT.PROCESS_VM_OPERATION
var CLIENT_MODULE_NAME = "client.dll"
var ENGINE_MODULE_NAME = "engine.dll"

var DANGER_ZONE = false
var MENUTOG = false
