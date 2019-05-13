import rat.poison.settings.*

/**
 * Turn the overlay and menu on or off, if enabled the cmd will not be used
 * The menu only shows up while in game windowed or fullscreen windowed
 * If disabled will turn off anything that uses the overlay
 * The menu
 */
MENU = true

/**
 * Default menu size, if game cannot be found and sized on initialize it refers to this
 */
OVERLAY_WIDTH = 1920
OVERLAY_HEIGHT = 1080

/**
 * Whether or not to aim when using the (FIRE_KEY) (by default left click).
 *
 * You should disable this if you don't want aim to activate when left clicking.
 */
ACTIVATE_FROM_FIRE_KEY = true

/**
 * This will allow you to shoot teammates with aimbot when turned on.
 */
TEAMMATES_ARE_ENEMIES = false

/**
 * Whether non-automatic weapons shoot when FIRE_KEY is held down
 */
AUTOMATIC_WEAPONS = false

/**
 * Punch check delay in ms, if punch has been 0 for this amount of time in ms, shoot
 * This is used to tap while using AUTOMATIC_WEAPONS
 * The lower the less accurate
 */
AUTO_WEP_DELAY = 10

/**
 * The key code of the force aim button.
 *
 * By default, this uses the backward mouse button
 * (button 5, the button on the bottom left of gaming mice).
 */
FORCE_AIM_KEY = 5

/**
 * The duration in milliseconds at which aimbot paths are recalculated.
 */
AIM_DURATION = 1

/**
 * The amount of sprayed shots until the aimbot shifts to aiming at the (SHOULDER_BONE).
 */
SHIFT_TO_SHOULDER_SHOTS = 3

/**
 * The amount of sprayed shots until the aimbot shifts to aiming at the (BODY_BONE).
 */
SHIFT_TO_BODY_SHOTS = 4

/**
 * Set this to true if you're playing CS:CO (Counter-Strike: Classic Offensive).
 */
CLASSIC_OFFENSIVE = false

/**
 * The global fire key, which you use to shoot/fire your weapon.
 *
 * By default, this is left click (1).
 */
FIRE_KEY = 1

/**
 * Key to disable ESP (say if a friend walks into your room); default G key
 */
VISUALS_TOGGLE_KEY = 35

/**
 * Key to toggle open gl menu, default is F1
 */
MENU_KEY = 112

/**
 * Name of config file
 */
CFG1_NAME = "Null"

/**
 * Name of config file
 */
CFG2_NAME = "Null"

/**
 * Name of config file
 */
CFG3_NAME = "Null"

/**
 * The bone IDs of the respective bones for a player.
 *
 * The left-side number is for CS:CO, and the right-side number is for CS:GO.
 */
HEAD_BONE = 8
SHOULDER_BONE = 7
BODY_BONE = 6

/**
 * Variables used in game, not saved settings, no need to touch
 */
PERFECT_AIM = true
PERFECT_AIM_CHANCE = 15
PERFECT_AIM_FOV = 10
AIM_BONE = 8
AIM_SPEED = 12
AIM_FOV = 30
AIM_ASSIST_MODE = false
FACTOR_RECOIL = true
ENABLE_FLAT_AIM = false
ENABLE_PATH_AIM = true
AIM_STRICTNESS = 2.0
