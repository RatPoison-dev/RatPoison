package com.charlatano.settings

///////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////// --- GENERAL --- ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

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
 * The field of view of the aimbot, in degrees (0 to 360).
 */
var AIM_FOV = 30

/**
 * The aimbot's "playback" speed, the higher the value the slower the playback.
 *
 * The minimum value is 1, and max must always be greater than min.
 */
var AIM_SPEED_MIN = 28
var AIM_SPEED_MAX = 36

/**
 * The strictness, or "stickiness" of the aimbot; the higher the number, the
 * less strict the aimbot will stick to targets.
 *
 * The minimum value is 1.0
 */
var AIM_STRICTNESS = 1.0

/**
 * The minimum and maximum time in milliseconds to delay the aimbot after
 * targets have been swapped.
 *
 * Set these both to 0L for there to be no delay.
 */
var TARGET_SWAP_MIN_DELAY = 200L
var TARGET_SWAP_MAX_DELAY = 350L


///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////// --- PERFECT AIM --- /////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Whether or not to use perfect aim, which will instantaneously snap
 * to the aim bone once you are within the [PERFECT_AIM_FOV].
 */
var PERFECT_AIM = false

/**
 * The FOV, in degrees (0 to 360) to snap for perfect aim.
 */
var PERFECT_AIM_FOV = 4

/**
 * The chance, from 1% to 100% (0 to 100) for perfect aim to activate.
 */
var PERFECT_AIM_CHANCE = 100


///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////// --- AIM ASSIST --- //////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Enables "aim assist" mode, which has no stickiness, and gives you small
 * extra movements towards the aim bone.
 *
 * This setting should be used by high-level players who are experienced aimers.
 */
var AIM_ASSIST_MODE = true

/**
 * The amount of strictness for the aim assist mode, with a mimimum value of 1.
 */
var AIM_ASSIST_STRICTNESS = 60


///////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////// --- MISCELLANEOUS --- ////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

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