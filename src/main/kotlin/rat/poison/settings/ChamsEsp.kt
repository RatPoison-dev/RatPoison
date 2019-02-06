package rat.poison.settings

/**
 * Whether or not to use chams ESP, may require a game restart to stop/fix
 * To disable chams & brightness on exit (as models will stay colored normally), you must type exit into the cmd to close the program
 * This modifies a cvar that currently isn't being checked, this can change at anytime, use at your own discretion
 */
var CHAMS_ESP = false

/**
 * When this is and CHAMS_ESP are enabled the enemy models will be colored based on health, and will override CHAMS_ESP_COLOR
 */
var CHAMS_SHOW_HEALTH = false

/**
 * Brightness for chams esp (from 0 to 1000)
 */
var CHAMS_BRIGHTNESS = 0

/**
 * Whether or not to highlight your team mates.
 */
var CHAMS_SHOW_TEAM = false

/**
 * Whether or not to highlight enemies.
 */
var CHAMS_SHOW_ENEMIES = true