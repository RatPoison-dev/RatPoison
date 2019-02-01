package rat.poison.settings

import rat.poison.game.Color

///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////// --- ESP Types --- ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Whether or not to use skeleton ESP.
 * This requires the overlay which only works when borderless fullscreen or windowed.
 */
var SKELETON_ESP = false

/**
 * Whether or not to use box ESP.
 * This requires the overlay which only works when borderless fullscreen or windowed.
 */
var BOX_ESP = false

/**
 * Whether or not health and weapon is displayed with box esp
 */
var BOX_ESP_DETAILS = false

/**
 * Whether or not to use the within-game glow ESP.
 *
 * This ESP **CANNOT** be hidden from game capture for streaming.
 */
var GLOW_ESP = false

/**
 * Whether or not to invert the glow esp and highlight the player model instead of the outline
 * This works through walls, to change how thick/blurry it is adjust the alpha .5 through 1 should work fine
 */
var INV_GLOW_ESP = false

/**
 * Model ESP glow tied to glow_esp, makes the model glow and only when visible, instead of an outline through walls
 */
var MODEL_ESP = true

/**
 * This gets rid of glow ESP "flicker", and more importantly reduces CPU usage.
 */
var FLICKER_FREE_GLOW = true

/**
 * Whether or not to use chams ESP, may require a game restart to stop/fix
 * To disable chams & brightness on exit (as models will stay colored normally), you must type exit into the cmd to close the program
 * This modifies a cvar that currently isn't being checked, this can change at anytime, use at your own discretion
 */
var CHAMS_ESP = false

/**
 * When this is and CHAMS_ESP are enabled the enemy models will be colored based on health, and will override CHAMS_ESP_COLOR
 */
var CHAMS_SHOW_HEALTH = true

/**
 * Glow esp will work when enemy is not visible, chams will work when enemy is visible (visibility check is delayed as a netvar), takes 1 to 2 seconds to switch between
 */
var MODEL_AND_GLOW = true

/**
 * Brightness for chams esp (from 0 to 1000)
 */
var CHAMS_BRIGHTNESS = 100

/**
 * Will draw circles around your screen to indicate enemy players location in relation to you
 * This requires the overlay which only works when borderless fullscreen or windowed.
 */
var ENEMY_INDICATOR = true

/**
 * Will play a hitsound on enemy hit
 */
var ENABLE_HITSOUND = true

/**
 * Volume of the hitsound
 */
var HITSOUND_VOLUME = 0.2


///////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////// --- TOGGLES --- ////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Whether or not to highlight your team mates.
 */
var SHOW_TEAM = true

/**
 * Whether or not to highlight enemies.
 */
var SHOW_ENEMIES = true

/**
 * Whether or not to highlight "dormant" (unknown-location) players.
 *
 * Enabling this can allow you to see players at a further distance,
 * but you may see some "ghost" players which are really not there.
 */
var SHOW_DORMANT = false

/**
 * Whether or not to highlight the bomb.
 */
var SHOW_BOMB = true

/**
 * Whether or not to highlight weapons.
 */
var SHOW_WEAPONS = false

/**
 * Whether or not to highlight grenades.
 */
var SHOW_GRENADES = false


///////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////// --- COLORS --- ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * The color to highlight your team mates.
 */
var TEAM_COLOR = Color(0, 0, 255, 1.0)

/**
 * The color to highlight your enemies.
 */
var ENEMY_COLOR = Color(255, 0, 0, 1.0)

/**
 * The color to highlight the bomb.
 */
var BOMB_COLOR = Color(255, 255, 0, 1.0)

/**
 * The color to highlight weapons.
 */
var WEAPON_COLOR = Color(0, 255, 0, 0.5)

/**
 * The color to highlight grenades.
 */
var GRENADE_COLOR = Color(0, 255, 0, 1.0)