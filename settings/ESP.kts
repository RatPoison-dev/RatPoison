import rat.poison.game.Color
import rat.poison.settings.*

/**
 * Enables the extra sensory perception (ESP) script.
 */
ENABLE_ESP = true


///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////// --- ESP Types --- ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * This gets rid of glow ESP "flicker", and more importantly reduces CPU usage.
 * This glow will remain visible even after you close the cheat, toggle visuals off or restart csgo to fix.
 */
FLICKER_FREE_GLOW = false

/**
 * Whether or not to enable Radar ESP
 */
RADAR_ESP = false

/**
 * Visual hitmarker on enemy hit
 */
ENABLE_HITMARKER = false

/**
 * Name of the hitmarker
 */
HITMARKER_TYPE = 1

/**
 * Will play a hitsound on enemy hit
 */
ENABLE_HITSOUND = false

/**
 * Name of the hitsound file to play
 */
HITSOUND_FILE_NAME = "COD.wav"

/**
 * Volume of the hitsound
 */
HITSOUND_VOLUME = 0.1

//////////////////////////////////////////////////////
//////////////////////  Colors  //////////////////////
//////////////////////////////////////////////////////

/**
 * The color to highlight your team mates.
 */
TEAM_COLOR = Color(red=0, green=0, blue=255, alpha=1.0)

/**
 * The color to highlight your enemies.
 */
ENEMY_COLOR = Color(red=255, green=0, blue=0, alpha=0.6117647290229797)

/**
 * The color to highlight the bomb.
 */
BOMB_COLOR = Color(red=255, green=255, blue=0, alpha=1.0)

/**
 * The color to highlight weapons.
 */
WEAPON_COLOR = Color(red=251, green=0, blue=255, alpha=0.49803921580314636)

/**
 * The color to highlight grenades.
 */
GRENADE_COLOR = Color(red=0, green=255, blue=0, alpha=1.0)

/**
 * The color to highlight aimed enemies
 */
HIGHLIGHT_COLOR = Color(red=0, green=174, blue=255, alpha=1.0)
