package rat.poison.settings

/**
 * The range of recoil control you want to have applied.
 *
 * If both values are equal, there will be no randomization.
 *
 * Having imperfect RCS will greatly lower league ban rate.
 */
var RCS_MIN = 0.75
var RCS_MAX = 1.25

/**
 * The duration in milliseconds at which recoil control is checked.
 *
 * The higher these values, the lower the "shakiness", but also the lower the accuracy.
 *
 * Max must always be greater than min. Set to 1 and 1 for perfect control.
 */
var RCS_MIN_DURATION = 8
var RCS_MAX_DURATION = 16