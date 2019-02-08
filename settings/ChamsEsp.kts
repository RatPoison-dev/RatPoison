import rat.poison.settings.*

/**
 * Whether or not to use chams ESP, may require a game restart to stop/fix
 * To disable chams & brightness on exit (as models will stay colored normally), you must type exit into the cmd to close the program
 * This modifies a cvar that currently isn't being checked, this can change at anytime, use at your own discretion
 */
CHAMS_ESP = false

/**
 * When this is and CHAMS_ESP are enabled the enemy models will be colored based on health, and will override CHAMS_ESP_COLOR
 */
CHAMS_SHOW_HEALTH = false

/**
 * Brightness for chams esp (from 0 to 1000)
 */
CHAMS_BRIGHTNESS = 0

/**
 * Whether or not to highlight your team mates.
 */
CHAMS_SHOW_TEAM = false

/**
 * Whether or not to highlight enemies.
 */
CHAMS_SHOW_ENEMIES = true
