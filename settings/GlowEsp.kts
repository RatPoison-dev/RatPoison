import rat.poison.settings.*

/**
 * Whether or not to use the within-game glow ESP.
 *
 * This ESP **CANNOT** be hidden from game capture for streaming.
 */
GLOW_ESP = true

/**
 * Whether or not to invert the glow esp and highlight the player model instead of the outline
 * This works through walls, to change how thick/blurry it is adjust the alpha .5 through 1 should work fine
 */
INV_GLOW_ESP = false

/**
 * Model ESP glow tied to glow_esp, makes the model glow and only when visible, instead of an outline through walls
 */
MODEL_ESP = false

/**
 * Glow esp will work when enemy is not visible, chams will work when enemy is visible (visibility check is delayed as a netvar)
 */
MODEL_AND_GLOW = false

/**
 * Whether or not to highlight your team mates.
 */
GLOW_SHOW_TEAM = false

/**
 * Whether or not to highlight enemies.
 */
GLOW_SHOW_ENEMIES = true

/**
 * Whether or not to highlight "dormant" (unknown-location) players.
 *
 * Enabling this can allow you to see players at a further distance,
 * but you may see some "ghost" players which are really not there.
 */
GLOW_SHOW_DORMANT = false

/**
 * Whether or not to highlight the bomb.
 */
GLOW_SHOW_BOMB = true

/**
 * Whether or not to highlight weapons.
 */
GLOW_SHOW_WEAPONS = true

/**
 * Whether or not to highlight grenades.
 */
GLOW_SHOW_GRENADES = false
