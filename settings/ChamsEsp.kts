import rat.poison.game.Color
import rat.poison.settings.*

//Enable clrRender chams
//This modifies a cvar that isn't currently checked, which can change at any time. Use at your own discretion.
CHAMS_ESP = false

//Color players based on their health, overrides entity color
CHAMS_SHOW_HEALTH = false

//clrRender brightness (0 - 1000)
CHAMS_BRIGHTNESS = 250

//Enable chams esp on teammates
CHAMS_SHOW_TEAM = false

//Enable chams esp on enemies
CHAMS_SHOW_ENEMIES = true

//Color of your teammates
CHAMS_TEAM_COLOR = Color(red=0, green=0, blue=255, alpha=1.0)

//Color of your enemies
CHAMS_ENEMY_COLOR = Color(red=255, green=0, blue=0, alpha=0.6)

//Color of your hands/weapon
CHAMS_SELF_COLOR = Color(red=255, green=55, blue=0, alpha=1.0)
