import rat.poison.game.Color
import rat.poison.settings.*

///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////// --- ESP Types --- ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Whether or not to use skeleton ESP.
 */
SKELETON_ESP = false

/**
 * Whether or not to use box ESP.
 */
BOX_ESP = true

/**
 * Whether or not to use the within-game glow ESP.
 *
 * This ESP **CANNOT** be hidden from game capture for streaming.
 */
GLOW_ESP = true

/**
 * Model ESP glow tied to glow_esp, makes the model glow and only when visible, instead of an outline through walls newer chams, will be tied to later
 */
MODEL_ESP = false

/**
 * This gets rid of glow ESP "flicker", and more importantly reduces CPU usage.
 */
FLICKER_FREE_GLOW = true

/**
 * Whether or not to use chams ESP, may require a game restart to stop/fix
 * To disable chams & brightness on exit (as models will stay colored normally), you must type exit into the cmd to close the program
 * This modifies a cvar that currently isn't being checked, this can change at anytime, use at your own discretion
 */
CHAMS_ESP = false

/**
 * When this is and CHAMS_ESP are enabled the enemy models will be colored based on health, and will override CHAMS_ESP_COLOR
 */
CHAMS_SHOW_HEALTH = false

/**
 * Glow esp will work when enemy is not visible, chams will work when enemy is visible (visibility check is delayed as a netvar)
 */
MODEL_AND_GLOW = false

/**
 * Brightness for chams esp (from 0 to 1000)
 */
CHAMS_BRIGHTNESS = 0

/**
 * Will draw circles around your screen to indicate enemy players location in relation to you
 */
ENEMY_INDICATOR = true



///////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////// --- TOGGLES --- ////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Whether or not to highlight your team mates.
 */
SHOW_TEAM = true

/**
 * Whether or not to highlight enemies.
 */
SHOW_ENEMIES = true

/**
 * Whether or not to highlight "dormant" (unknown-location) players.
 *
 * Enabling this can allow you to see players at a further distance,
 * but you may see some "ghost" players which are really not there.
 */
SHOW_DORMANT = false

/**
 * Whether or not to highlight the bomb.
 */
SHOW_BOMB = true

/**
 * Whether or not to highlight weapons.
 */
SHOW_WEAPONS = true

/**
 * Whether or not to highlight grenades.
 */
SHOW_GRENADES = false


///////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////// --- COLORS --- ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * The color to highlight your team mates.
 */
TEAM_COLOR = Color(0, 0, 255, 1.0)

/**
 * The color to highlight your enemies.
 */
ENEMY_COLOR = Color(255, 0, 0, 1.0)

/**
 * The color to highlight the bomb.
 */
BOMB_COLOR = Color(255, 255, 0, 1.0)

/**
 * The color to highlight weapons.
 */
WEAPON_COLOR = Color(0,0,0,1.0)

/**
 * The color to highlight grenades.
 */
GRENADE_COLOR = Color(0, 255, 0, 1.0)

/**
 * The color to make the enemy
 */
CHAMS_ESP_COLOR = Color(255, 0, 255, 1.0)
