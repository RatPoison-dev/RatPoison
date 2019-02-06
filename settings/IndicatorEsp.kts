import rat.poison.game.Color
import rat.poison.settings.*

/**
 * Will draw circles around your screen to indicate enemy players location in relation to you
 * This requires the overlay which only works when borderless fullscreen or windowed.
 */
INDICATOR_ESP = true

/**
 * Whether enemy indicator will indicate on screen entities
 */
INDICATOR_SHOW_ONSCREEN = true

/**
 * The distance from the edge of the screen for the indicator circle/oval
 */
INDICATOR_DISTANCE = 3.0

/**
 * Whether the indicators are in a circle of an oval on screen
 */
INDICATOR_OVAL = true

/**
 * Whether or not to highlight your team mates.
 */
INDICATOR_SHOW_TEAM = false

/**
 * Whether or not to highlight enemies.
 */
INDICATOR_SHOW_ENEMIES = true

/**
 * Whether or not to highlight "dormant" (unknown-location) players.
 *
 * Enabling this can allow you to see players at a further distance,
 * but you may see some "ghost" players which are really not there.
 */
INDICATOR_SHOW_DORMANT = false

/**
 * Whether or not to highlight the bomb.
 */
INDICATOR_SHOW_BOMB = true

/**
 * Whether or not to highlight weapons.
 */
INDICATOR_SHOW_WEAPONS = true

/**
 * Whether or not to highlight grenades.
 */
INDICATOR_SHOW_GRENADES = false