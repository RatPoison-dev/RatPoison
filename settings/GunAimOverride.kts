import rat.poison.settings.*

//                  1/0                                                               8/6
//               True/False       True/False     True/False       True/False       Head/Body 1-360    1-100      1-10            1-5             1-360        True/False       1-100
//Array Format: [Enable Override, Factor Recoil, Enable Flat Aim, Enable Path Aim, Aim Bone, Aim Fov, Aim Speed, Aim Smoothness, Aim Strictness, Perfect Aim, Perfect Aim FOV, Perfect Aim Chance]

//Can't loop certain values due to script eval being dogshit

////Pistols
DESERT_EAGLE = doubleArrayOf(1.0, 0.0, 0.0, 1.0, 8.0, 30.0, 12.0, 2.5, 2.0, 1.0, 10.0, 15.0)
DUAL_BERRETA = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 8.0, 30.0, 12.0, 2.5, 2.0, 1.0, 10.0, 15.0)
FIVE_SEVEN = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 8.0, 30.0, 12.0, 2.5, 2.0, 1.0, 10.0, 15.0)
GLOCK = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 8.0, 30.0, 12.0, 2.5, 2.0, 1.0, 10.0, 15.0)
USP_SILENCER = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 8.0, 30.0, 12.0, 2.5, 2.0, 1.0, 10.0, 15.0)
CZ75A = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 8.0, 30.0, 12.0, 2.5, 2.0, 1.0, 10.0, 15.0)
R8_REVOLVER = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 8.0, 30.0, 12.0, 2.5, 2.0, 1.0, 10.0, 15.0)
P2000 = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 8.0, 30.0, 12.0, 2.5, 2.0, 1.0, 10.0, 15.0)
TEC9 = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 8.0, 30.0, 12.0, 2.5, 2.0, 1.0, 10.0, 15.0)
P250 = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 8.0, 30.0, 12.0, 2.5, 2.0, 1.0, 10.0, 15.0)

//Smgs
MAC10 = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 6.0, 60.0, 12.0, 2.5, 3.5, 1.0, 15.0, 10.0)
P90 = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 6.0, 60.0, 12.0, 2.5, 3.5, 1.0, 15.0, 10.0)
MP5 = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 6.0, 60.0, 12.0, 2.5, 3.5, 1.0, 15.0, 10.0)
UMP45 = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 6.0, 60.0, 12.0, 2.5, 3.5, 1.0, 15.0, 10.0)
MP7 = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 6.0, 60.0, 12.0, 2.5, 3.5, 1.0, 15.0, 10.0)
MP9 = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 6.0, 60.0, 12.0, 2.5, 3.5, 1.0, 15.0, 10.0)
PP_BIZON = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 6.0, 60.0, 12.0, 2.5, 3.5, 1.0, 15.0, 10.0)

//Rifles
AK47 = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 6.0, 80.0, 16.0, 1.5, 2.0, 1.0, 5.0, 10.0)
AUG = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 6.0, 80.0, 16.0, 1.5, 2.0, 1.0, 5.0, 10.0)
FAMAS = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 6.0, 80.0, 16.0, 1.5, 2.0, 1.0, 5.0, 10.0)
SG553 = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 6.0, 80.0, 16.0, 1.5, 2.0, 1.0, 5.0, 10.0)
GALIL = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 6.0, 80.0, 16.0, 1.5, 2.0, 1.0, 5.0, 10.0)
M4A4 = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 6.0, 80.0, 16.0, 1.5, 2.0, 1.0, 5.0, 10.0)
M4A1_SILENCER = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 6.0, 80.0, 16.0, 1.5, 2.0, 1.0, 5.0, 10.0)
NEGEV = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 6.0, 80.0, 16.0, 1.5, 2.0, 1.0, 5.0, 10.0)
M249 = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 6.0, 80.0, 16.0, 1.5, 2.0, 1.0, 5.0, 10.0)

//Snipers
AWP = doubleArrayOf(0.0, 0.0, 1.0, 0.0, 6.0, 80.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0)
G3SG1 = doubleArrayOf(0.0, 0.0, 1.0, 0.0, 6.0, 80.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0)
SCAR20 = doubleArrayOf(0.0, 0.0, 1.0, 0.0, 6.0, 80.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0)
SSG08 = doubleArrayOf(1.0, 0.0, 1.0, 0.0, 8.0, 80.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0)

//Shotguns
XM1014 = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 6.0, 70.0, 4.0, 1.0, 1.5, 1.0, 20.0, 40.0)
MAG7 = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 6.0, 70.0, 4.0, 1.0, 1.5, 1.0, 20.0, 40.0)
SAWED_OFF = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 6.0, 70.0, 4.0, 1.0, 1.5, 1.0, 20.0, 40.0)
NOVA = doubleArrayOf(0.0, 1.0, 0.0, 1.0, 6.0, 70.0, 4.0, 1.0, 1.5, 1.0, 20.0, 40.0)
