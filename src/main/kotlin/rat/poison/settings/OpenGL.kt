package rat.poison.settings

/**
 * Whether or not to use V-sync (vertical sync) for the OpenGL overlay.
 *
 * Usually you want to use V-sync for the overlay if you use V-sync in game.
 */
var OPENGL_VSYNC = false

/**
 * FPS of the OpenGL overlay
 */
var OPENGL_FPS = 60

/**
 * The amount of MSAA antialiasing samples for the OpenGL overlay.
 *
 * Decreasing this number may help improve your FPS! Use 0 to disable.
 *
 * Valid sample amounts are 0 (disable), 2, 4, and 8.
 */
var OPENGL_MSAA_SAMPLES = 8