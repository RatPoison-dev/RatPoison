import rat.poison.settings.*

///////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////// --- GENERAL --- ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

//Enable flat aim
//Writes to angles ingame
PISTOL_ENABLE_FLAT_AIM = false

//Enable path aim
//Uses mouse movement to aim
PISTOL_ENABLE_PATH_AIM = true

//Factor recoil when shooting
PISTOL_FACTOR_RECOIL = true

//Aim Bone
//Default bone to aim at
PISTOL_AIM_BONE = 8

//Aim FOV
//Aims at entities inside this value
PISTOL_AIM_FOV = 30

//Aim speed
//MS delay between aiming steps
//Lower is faster
PISTOL_AIM_SPEED = 1

//Aim smoothness
//Lower is less smooth (faster snapping)
PISTOL_AIM_SMOOTHNESS = 2.5

//Aim strictness (1.0 - 5.0)
//Lower is more strict (faster, more accurate snapping)
PISTOL_AIM_STRICTNESS = 2.0


///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////// --- PERFECT AIM --- /////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

//Enable perfect aim
//Will instantly snap to the aimbone
PISTOL_PERFECT_AIM = true

//Perfect aim fov (0 - 45)
PISTOL_PERFECT_AIM_FOV = 10

//Perfect aim % chance to activate (1 - 100)
PISTOL_PERFECT_AIM_CHANCE = 15
