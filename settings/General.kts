import com.charlatano.settings.*

/**
 * Set this to true if you're playing on a league like ESEA, FaceIT, etc.
 * This will disable writing to the game and disable all visuals.
 */
LEAGUE_MODE = false

/**
 * Set this to true if you're playing CS:CO (Counter-Strike: Classic Offensive).
 */
CLASSIC_OFFENSIVE = false

/**
 * The global fire key, which you use to shoot/fire your weapon.
 *
 * By default, this is left click (1).
 */
FIRE_KEY = 1

/**
 * Enable actions log in cmd
 */
ACTION_LOG = true

/**
 * Key to disable ESP (say if a friend walks into your room); default G key
 */
ESP_TOGGLE_KEY = 71

/**
 * The bone IDs of the respective bones for a player.
 *
 * The left-side number is for CS:CO, and the right-side number is for CS:GO.
 */
HEAD_BONE = if (CLASSIC_OFFENSIVE) 6 else 8
SHOULDER_BONE = if (CLASSIC_OFFENSIVE) 5 else 7
BODY_BONE = if (CLASSIC_OFFENSIVE) 4 else 6