import rat.poison.settings.*

//Enabe DEBUG in cmd
DEBUG = false

//Enable the overlay, menu
//Any settings/scripts reliant on drawing on the overlay won't enable
MENU = true

//VK Key Code (http://cherrytree.at/misc/vk.htm), toggles visibility of the menu on the overlay
MENU_KEY = 112

//The title of the app to draw the overlay on
//Ex "Untitled - Notepad"
MENU_APP = "Counter-Strike: Global Offensive"

//The default overlay width and height, backup if width and height cannot be grabbed from MENU_APP
OVERLAY_WIDTH = 1920
OVERLAY_HEIGHT = 1080

//Enable overlay warning on initialize
WARNING = true

//Enable fast stop
//Will attempt to slow your velocity faster than normal if movement keys aren't being pressed
FAST_STOP = false

//Enable head walk
HEAD_WALK = false

//VK Key Code (http://cherrytree.at/misc/vk.htm), toggles head walk on/off
//Needed to know when to stay on a player's head, and when to be able to walk off
//Default key is Q
HEAD_WALK_KEY = 81


//Global enable aim
ENABLE_AIM = true

//Enable aimbot when aim key is pressed
ACTIVATE_FROM_AIM_KEY = true

//VK Key Code (http://cherrytree.at/misc/vk.htm), ingame key to shoot
//Default key is MOUSE_1 (left click)
AIM_KEY = 1

//Enables aimbot against teammates
TEAMMATES_ARE_ENEMIES = false

//VK Key Code (http://cherrytree.at/misc/vk.htm), enables aimbot when held
//Default key is MOUSE_4
FORCE_AIM_KEY = 5

//MS Delay between when aimbot paths are recalculated
AIM_DURATION = 1

//Bone IDs of bones used
HEAD_BONE = 8
NECK_BONE = 7
CHEST_BONE = 6
STOMACH_BONE = 5
NEAREST_BONE = -1

//Temp variables, irrelevant to edit
PERFECT_AIM = false
PERFECT_AIM_CHANCE = 1
PERFECT_AIM_FOV = 1
AIM_BONE = -1
AIM_SPEED = 1
AIM_FOV = 40
AIM_ASSIST_MODE = false
FACTOR_RECOIL = false
ENABLE_FLAT_AIM = true
ENABLE_PATH_AIM = false
ENABLE_SCOPED_ONLY = true
AIM_STRICTNESS = 1.0
