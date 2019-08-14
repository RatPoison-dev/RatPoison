import rat.poison.settings.*

///////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////// --- GENERAL --- ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Enables the flat aim script.
 *
 * This script uses traditional flat linear-regression smoothing.
 */
SNIPER_ENABLE_FLAT_AIM = true

/**
 * Enables the path aim script.
 *
 * This script uses an advanced path generation smoothing.
 */
SNIPER_ENABLE_PATH_AIM = false

/**
 * Only aim if scoped in
 */
SNIPER_ENABLE_SCOPED_ONLY = true

/**
 * Whether or not to account for recoil when aiming
 */
SNIPER_FACTOR_RECOIL = false

/**
 * Default aim bone the aims go to
 */
SNIPER_AIM_BONE = -1

/**
 * The field of view of the aimbot, in degrees (0 to 360).
 */
SNIPER_AIM_FOV = 40

/**
 * The aimbot's "playback" speed, the higher the value the slower the playback.
 *
 * The minimum value is 1
 */
SNIPER_AIM_SPEED = 0

/**
 * The smoothness of the aimbot
 */
SNIPER_AIM_SMOOTHNESS = 1.0

/**
 * The sens multiplier of the aimbot
 */
SNIPER_AIM_STRICTNESS = 1.0


///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////// --- PERFECT AIM --- /////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Whether or not to use perfect aim, which will instantaneously snap
 * to the aim bone once you are within the (PERFECT_AIM_FOV).
 */
SNIPER_PERFECT_AIM = false

/**
 * The FOV, in degrees (0 to 360) to snap for perfect aim.
 */
SNIPER_PERFECT_AIM_FOV = 1

/**
 * The chance, from 1% to 100% (0 to 100) for perfect aim to activate.
 */
SNIPER_PERFECT_AIM_CHANCE = 1
