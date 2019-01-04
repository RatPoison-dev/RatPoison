package rat.poison.settings

import rat.poison.game.Color

///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////// --- ESP Types --- ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Whether or not to use skeleton ESP.
 */
var SKELETON_ESP = false

/**
 * Whether or not to use box ESP.
 */
var BOX_ESP = false

/**
 * Whether or not to use the within-game glow ESP.
 *
 * This ESP **CANNOT** be hidden from game capture for streaming.
 */
var GLOW_ESP = false

/**
 * Model ESP glow tied to glow_esp, makes the model glow and only when visible, instead of an outline through walls newer chams, will be tied to later
 */
var MODEL_ESP = true

/**
 * Whether or not to use chams ESP
 *
 *
 */
var CHAMS_ESP = false


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

/**
 * The color to make the enemy
 */
var CHAMS_ESP_COLOR = Color(255, 0, 255, 1.0)
