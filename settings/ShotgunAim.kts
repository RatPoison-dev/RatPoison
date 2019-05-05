import rat.poison.settings.*

///////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////// --- GENERAL --- ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Enables the flat aim script.
 *
 * This script uses traditional flat linear-regression smoothing.
 */
SHOTGUN_ENABLE_FLAT_AIM = false

/**
 * Enables the path aim script.
 *
 * This script uses an advanced path generation smoothing.
 */
SHOTGUN_ENABLE_PATH_AIM = true

/**
 * Whether or not to account for recoil when aiming
 */
SHOTGUN_FACTOR_RECOIL = false

/**
 * Default aim bone the aims go to
 */
SHOTGUN_AIM_BONE = 6

/**
 * The field of view of the aimbot, in degrees (0 to 360).
 */
SHOTGUN_AIM_FOV = 70

/**
 * The aimbot's "playback" speed, the higher the value the slower the playback.
 *
 * The minimum value is 1
 */
SHOTGUN_AIM_SPEED = 1

/**
 * The smoothness of the aimbot
 */
SHOTGUN_AIM_SMOOTHNESS = 1.5


///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////// --- PERFECT AIM --- /////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Whether or not to use perfect aim, which will instantaneously snap
 * to the aim bone once you are within the (PERFECT_AIM_FOV).
 */
SHOTGUN_PERFECT_AIM = true

/**
 * The FOV, in degrees (0 to 360) to snap for perfect aim.
 */
SHOTGUN_PERFECT_AIM_FOV = 20

/**
 * The chance, from 1% to 100% (0 to 100) for perfect aim to activate.
 */
SHOTGUN_PERFECT_AIM_CHANCE = 40
