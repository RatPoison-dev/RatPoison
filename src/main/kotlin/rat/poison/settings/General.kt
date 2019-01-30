package rat.poison.settings

/**
 * Turn the overlay menu on or off, if enabled the cmd will not be used
 * The menu only shows up while in game windowed or fullscreen windowed
 */
var MENU = true

/**
 * Whether or not to aim when using the [FIRE_KEY] (by default left click).
 *
 * You should disable this if you don't want aim to activate when left clicking.
 */
var ACTIVATE_FROM_FIRE_KEY = true

/**
 * This will allow you to shoot teammates with aimbot when turned on.
 */
var TEAMMATES_ARE_ENEMIES = false

/**
 * The key code of the force aim button.
 *
 * By default, this uses the backward mouse button
 * (button 5, the button on the bottom left of gaming mice).
 */
var FORCE_AIM_KEY = 5

/**
 * The minimum and maximum time in milliseconds to delay the aimbot after
 * targets have been swapped.
 *
 * Set these both to 0L for there to be no delay.
 */
var TARGET_SWAP_MIN_DELAY = 200L
var TARGET_SWAP_MAX_DELAY = 350L

/**
 * The duration in milliseconds at which aimbot paths are recalculated.
 */
var AIM_DURATION = 1

/**
 * The amount of sprayed shots until the aimbot shifts to aiming at the [SHOULDER_BONE].
 */
var SHIFT_TO_SHOULDER_SHOTS = 3

/**
 * The amount of sprayed shots until the aimbot shifts to aiming at the [BODY_BONE].
 */
var SHIFT_TO_BODY_SHOTS = 4

/**
 * Set this to true if you're playing on a league like ESEA, FaceIT, etc.
 * This will disable writing to the game and disable all visuals.
 */
var LEAGUE_MODE = false

/**
 * This will be set by constructEntities hook
 */
var DANGER_ZONE = false

/**
 * Set this to true if you're playing CS:CO (Counter-Strike: Classic Offensive).
 */
var CLASSIC_OFFENSIVE = false

/**
 * The global fire key, which you use to shoot/fire your weapon.
 *
 * By default, this is left click (1).
 */
var FIRE_KEY = 1

/**
 * Key to disable ESP (say if a friend walks into your room); default G key
 */
var VISUALS_TOGGLE_KEY = 71

/**
 * Enable actions log in cmd
 */
var ACTION_LOG = true

/**
 * Key to toggle open gl menu, default is F1
 */
var MENU_KEY = 112

var MENUTOG = false

/**
 * The bone IDs of the respective bones for a player.
 *
 * The left-side number is for CS:CO, and the right-side number is for CS:GO.
 */
var HEAD_BONE = if (CLASSIC_OFFENSIVE) 6 else 8
var SHOULDER_BONE = if (CLASSIC_OFFENSIVE) 5 else 7
var BODY_BONE = if (CLASSIC_OFFENSIVE) 4 else 6