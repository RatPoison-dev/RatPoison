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
 * Whether or not to account for recoil when aiming
 */
SNIPER_FACTOR_RECOIL = false

/**
 * Default aim bone the aims go to
 */
SNIPER_AIM_BONE = 6

/**
 * The field of view of the aimbot, in degrees (0 to 360).
 */
SNIPER_AIM_FOV = 80

/**
 * The aimbot's "playback" speed, the higher the value the slower the playback.
 *
 * The minimum value is 1
 */
SNIPER_AIM_SPEED = 1

/**
 * The smoothness of the aimbot
 */
SNIPER_AIM_SMOOTHNESS = 1.0

/**
 * The strictness, or "stickiness" of the aimbot; the higher the number, the
 * less strict the aimbot will stick to targets.
 *
 * The minimum value is 1.0
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
SNIPER_PERFECT_AIM_FOV = 40

/**
 * The chance, from 1% to 100% (0 to 100) for perfect aim to activate.
 */
SNIPER_PERFECT_AIM_CHANCE = 100


///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////// --- AIM ASSIST --- //////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Enables "aim assist" mode, which has no stickiness, and gives you small
 * extra movements towards the aim bone.
 *
 * This setting should be used by high-level players who are experienced aimers.
 */
SNIPER_AIM_ASSIST_MODE = false

/**
 * The amount of strictness for the aim assist mode, with a mimimum value of 1.
 */
SNIPER_AIM_ASSIST_STRICTNESS = 60
