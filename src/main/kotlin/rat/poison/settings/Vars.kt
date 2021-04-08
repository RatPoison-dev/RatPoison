package rat.poison.settings

import com.sun.jna.platform.win32.WinNT
import rat.poison.appless

//todo standardize names
var PERFECT_AIM = false
var PERFECT_AIM_CHANCE = 1
var PERFECT_AIM_FOV = 1F
var AIM_FOV = 2F
var FACTOR_RECOIL = false
var AIM_AFTER_SHOTS = 0
var AIM_ADVANCED = false
var AIM_RCS_X = 0.05F
var AIM_RCS_Y = 0.05F
var AIM_RANDOM_X_VARIATION = 0
var AIM_RANDOM_Y_VARIATION = 0
var AIM_RCS_VARIATION = 0.0
var AIM_VARIATION_DEADZONE = 0
var ENABLE_FLAT_AIM = false
var ENABLE_PATH_AIM = false
var TRIGGER_USE_FOV = false
var TRIGGER_USE_INCROSS = false
var TRIGGER_FOV = 1.0F
var TRIGGER_USE_AIMBOT = false
var TRIGGER_USE_BACKTRACK = false
var AIM_ONLY_ON_SHOT = false
var ENABLE_SCOPED_ONLY = false
var TRIGGER_BOT = false
var TRIGGER_INIT_SHOT_DELAY = 0
var TRIGGER_PER_SHOT_DELAY = 0

var AUTOMATIC_WEAPONS = false
var AUTO_WEP_DELAY = 0

var AIM_SMOOTHNESS = 0

var BACKTRACK = false
var BACKTRACK_MS = 0













var GAME_PITCH = 0.022 // m_pitch
var GAME_YAW = 0.022 // m_yaw
var GAME_SENSITIVITY = 1.0
const val  HEAD_BONE = 8
const val  NECK_BONE = 7
const val CHEST_BONE = 6
const val  STOMACH_BONE = 5
const val  PELVIS_BONE = 0
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
var SMOKE_EFFECT_TIME = 18

var DANGER_ZONE = false
var MENUTOG = false
    set(value) {
        field = if (!appless) value else false
    }
