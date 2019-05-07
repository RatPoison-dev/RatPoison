import rat.poison.settings.*

///////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////// --- GENERAL --- ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Enables the flat aim script.
 *
 * This script uses traditional flat linear-regression smoothing.
 */
PISTOL_ENABLE_FLAT_AIM = false

/**
 * Enables the path aim script.
 *
 * This script uses an advanced path generation smoothing.
 */
PISTOL_ENABLE_PATH_AIM = true

/**
 * Whether or not to account for recoil when aiming
 */
PISTOL_FACTOR_RECOIL = false

/**
 * Default aim bone the aims go to
 */
PISTOL_AIM_BONE = 8

/**
 * The field of view of the aimbot, in degrees (0 to 360).
 */
PISTOL_AIM_FOV = 30

/**
 * The aimbot's "playback" speed, the higher the value the slower the playback.
 *
 * The minimum value is 1
 */
PISTOL_AIM_SPEED = 12

/**
 * The smoothness of the aimbot
 */
PISTOL_AIM_SMOOTHNESS = 2.5

/**
 * The sens multiplier of the aimbot
 */
PISTOL_AIM_STRICTNESS = 2.0


///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////// --- PERFECT AIM --- /////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Whether or not to use perfect aim, which will instantaneously snap
 * to the aim bone once you are within the (PERFECT_AIM_FOV).
 */
PISTOL_PERFECT_AIM = true

/**
 * The FOV, in degrees (0 to 360) to snap for perfect aim.
 */
PISTOL_PERFECT_AIM_FOV = 10

/**
 * The chance, from 1% to 100% (0 to 100) for perfect aim to activate.
 */
PISTOL_PERFECT_AIM_CHANCE = 15
