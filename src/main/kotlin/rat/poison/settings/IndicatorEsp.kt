package rat.poison.settings

import rat.poison.game.Color

/**
 * Will draw circles around your screen to indicate enemy players location in relation to you
 * This requires the overlay which only works when borderless fullscreen or windowed.
 */
var INDICATOR_ESP = true

/**
 * Whether enemy indicator will indicate on screen entities
 */
var INDICATOR_SHOW_ONSCREEN = true

/**
 * The distance from the edge of the screen for the indicator circle/oval
 */
var INDICATOR_DISTANCE = 3.0

/**
 * Whether the indicators are in a circle of an oval on screen
 */
var INDICATOR_OVAL = true

/**
 * Whether or not to highlight your team mates.
 */
var INDICATOR_SHOW_TEAM = false

/**
 * Whether or not to highlight enemies.
 */
var INDICATOR_SHOW_ENEMIES = true

/**
 * Whether or not to highlight "dormant" (unknown-location) players.
 *
 * Enabling this can allow you to see players at a further distance,
 * but you may see some "ghost" players which are really not there.
 */
var INDICATOR_SHOW_DORMANT = false

/**
 * Whether or not to highlight the bomb.
 */
var INDICATOR_SHOW_BOMB = true

/**
 * Whether or not to highlight weapons.
 */
var INDICATOR_SHOW_WEAPONS = true

/**
 * Whether or not to highlight grenades.
 */
var INDICATOR_SHOW_GRENADES = false