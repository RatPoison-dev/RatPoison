import rat.poison.game.Color
import rat.poison.settings.*

//Enable glow esp
GLOW_ESP = true

//Enable inverted glow esp
//Glows all of the entity like a silhouette
INV_GLOW_ESP = false

//Enable model esp
//Glows the details of the model
//Not visible through walls
MODEL_ESP = false

//Enable model and glow
//Will use glow esp (or inv glow esp) when entity is not visible
//Will use model esp when entity is visible
MODEL_AND_GLOW = false

//Enable glow esp on teammates
GLOW_SHOW_TEAM = false

//Enable glow esp on enemies
GLOW_SHOW_ENEMIES = true

//Enable glow esp on bomb
GLOW_SHOW_BOMB = true

//Enable glow esp on bomb carrier
GLOW_SHOW_BOMB_CARRIER = true

//Enable glow esp on dropped weapons
GLOW_SHOW_WEAPONS = true

//Enable glow esp on dropped/thrown grenades
GLOW_SHOW_GRENADES = false

//Enable glow esp on visible, in aimbot FOV players
GLOW_SHOW_TARGET = true

//Color of your teammates
GLOW_TEAM_COLOR = Color(red=0, green=0, blue=255, alpha=1.0)

//Color of your enemies
GLOW_ENEMY_COLOR = Color(red=255, green=0, blue=0, alpha=0.6)

//Color of the bomb, entities carrying the bomb
GLOW_BOMB_COLOR = Color(red=255, green=255, blue=0, alpha=1.0)

//Color of the defuse kit
GLOW_DEFUSER_COLOR = Color(red=145, green=0, blue=90, alpha=1.0)

//Color of dropped weapons
GLOW_WEAPON_COLOR = Color(red=251, green=0, blue=255, alpha=0.5)

//Color of thrown grenades
GLOW_GRENADE_COLOR = Color(red=0, green=255, blue=0, alpha=1.0)

//Color of visible, in aimbot FOV players
GLOW_HIGHLIGHT_COLOR = Color(red=0, green=174, blue=255, alpha=1.0)
