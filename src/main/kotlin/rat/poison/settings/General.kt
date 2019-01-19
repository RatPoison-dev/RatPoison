package rat.poison.settings

/**
 * Turn the overlay menu on or off, if enabled the cmd will not be used
 * The menu only shows up while in game windowed or fullscreen windowed
 */
var MENU = true

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