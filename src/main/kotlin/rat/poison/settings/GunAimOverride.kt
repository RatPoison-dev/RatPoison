package rat.poison.settings

//               #      True/False       True/False     True/False       True/False       Head/Body 1-360    1-100      1-10            1-5             1-360        True/False   1-100               True/False       1-100
//Array Format: [WepID, Enable Override, Factor Recoil, Enable Flat Aim, Enable Path Aim, Aim Bone, Aim Fov, Aim Speed, Aim Smoothness, Aim Strictness, Perfect Aim, Perfect Aim FOV, Perfect Aim Chance, Aim Assist Mode, Aim Assist Strictness]

//Can't loop certain values due to script eval being dogshit

////Pistols
//var DESERT_EAGLE = doubleArrayOf(1.0, 0.0, 1.0, 0.0, 1.0, 8.0, 30.0, 10.0, 4.5, 1.0, 1.0, 10.0, 15.0, 1.0, 80.0)
//var DUAL_BERRETA = doubleArrayOf(1.0, 0.0, 1.0, 0.0, 1.0, 8.0, 30.0, 10.0, 4.5, 1.0, 1.0, 10.0, 15.0, 1.0, 80.0)
//var FIVE_SEVEN = doubleArrayOf(1.0, 0.0, 1.0, 0.0, 1.0, 8.0, 30.0, 10.0, 4.5, 1.0, 1.0, 10.0, 15.0, 1.0, 80.0)
//var GLOCK = doubleArrayOf(1.0, 0.0, 1.0, 0.0, 1.0, 8.0, 30.0, 10.0, 4.5, 1.0, 1.0, 10.0, 15.0, 1.0, 80.0)
//var USP_SILENCER = doubleArrayOf(1.0, 0.0, 1.0, 0.0, 1.0, 8.0, 30.0, 10.0, 4.5, 1.0, 1.0, 10.0, 15.0, 1.0, 80.0)
//var CZ75A = doubleArrayOf(1.0, 0.0, 1.0, 0.0, 1.0, 8.0, 30.0, 10.0, 4.5, 1.0, 1.0, 10.0, 15.0, 1.0, 80.0)
//var R8_REVOLVER = doubleArrayOf(1.0, 0.0, 1.0, 0.0, 1.0, 8.0, 30.0, 10.0, 4.5, 1.0, 1.0, 10.0, 15.0, 1.0, 80.0)
//var P2000 = doubleArrayOf(1.0, 0.0, 1.0, 0.0, 1.0, 8.0, 30.0, 10.0, 4.5, 1.0, 1.0, 10.0, 15.0, 1.0, 80.0)
//var TEC9 = doubleArrayOf(1.0, 0.0, 1.0, 0.0, 1.0, 8.0, 30.0, 10.0, 4.5, 1.0, 1.0, 10.0, 15.0, 1.0, 80.0)
//var P250 = doubleArrayOf(1.0, 0.0, 1.0, 0.0, 1.0, 8.0, 30.0, 10.0, 4.5, 1.0, 1.0, 10.0, 15.0, 1.0, 80.0)
//
////Smgs
//var MAC10 = doubleArrayOf(17.0, 0.0, 1.0, 0.0, 1.0, 6.0, 40.0, 24.0, 4.5, 1.0, 0.0, 1.0, 1.0, 1.0, 80.0)
//var P90 = doubleArrayOf(17.0, 0.0, 1.0, 0.0, 1.0, 6.0, 40.0, 24.0, 4.5, 1.0, 0.0, 1.0, 1.0, 1.0, 80.0)
//var MP5 = doubleArrayOf(17.0, 0.0, 1.0, 0.0, 1.0, 6.0, 40.0, 24.0, 4.5, 1.0, 0.0, 1.0, 1.0, 1.0, 80.0)
//var UMP45 = doubleArrayOf(17.0, 0.0, 1.0, 0.0, 1.0, 6.0, 40.0, 24.0, 4.5, 1.0, 0.0, 1.0, 1.0, 1.0, 80.0)
//var MP7 = doubleArrayOf(17.0, 0.0, 1.0, 0.0, 1.0, 6.0, 40.0, 24.0, 4.5, 1.0, 0.0, 1.0, 1.0, 1.0, 80.0)
//var MP9 = doubleArrayOf(17.0, 0.0, 1.0, 0.0, 1.0, 6.0, 40.0, 24.0, 4.5, 1.0, 0.0, 1.0, 1.0, 1.0, 80.0)
//var PP_BIZON = doubleArrayOf(17.0, 0.0, 1.0, 0.0, 1.0, 6.0, 40.0, 24.0, 4.5, 1.0, 0.0, 1.0, 1.0, 1.0, 80.0)
//
////Rifles
//var AK47 = doubleArrayOf(7.0, 0.0, 1.0, 0.0, 1.0, 6.0, 40.0, 16.0, 5.0, 1.2, 1.0, 5.0, 10.0, 1.0, 60.0)
//var AUG = doubleArrayOf(7.0, 0.0, 1.0, 0.0, 1.0, 6.0, 40.0, 16.0, 5.0, 1.2, 1.0, 5.0, 10.0, 1.0, 60.0)
//var FAMAS = doubleArrayOf(7.0, 0.0, 1.0, 0.0, 1.0, 6.0, 40.0, 16.0, 5.0, 1.2, 1.0, 5.0, 10.0, 1.0, 60.0)
//var SG553 = doubleArrayOf(7.0, 0.0, 1.0, 0.0, 1.0, 6.0, 40.0, 16.0, 5.0, 1.2, 1.0, 5.0, 10.0, 1.0, 60.0)
//var GALIL = doubleArrayOf(7.0, 0.0, 1.0, 0.0, 1.0, 6.0, 40.0, 16.0, 5.0, 1.2, 1.0, 5.0, 10.0, 1.0, 60.0)
//var M4A4 = doubleArrayOf(7.0, 0.0, 1.0, 0.0, 1.0, 6.0, 40.0, 16.0, 5.0, 1.2, 1.0, 5.0, 10.0, 1.0, 60.0)
//var M4A1_SILENCER = doubleArrayOf(7.0, 0.0, 1.0, 0.0, 1.0, 6.0, 40.0, 16.0, 5.0, 1.2, 1.0, 5.0, 10.0, 1.0, 60.0)
//var NEGEV = doubleArrayOf(7.0, 0.0, 1.0, 0.0, 1.0, 6.0, 40.0, 16.0, 5.0, 1.2, 1.0, 5.0, 10.0, 1.0, 60.0)
//var M249 = doubleArrayOf(7.0, 0.0, 1.0, 0.0, 1.0, 6.0, 40.0, 16.0, 5.0, 1.2, 1.0, 5.0, 10.0, 1.0, 60.0)
//
////Snipers
//var AWP = doubleArrayOf(9.0, 0.0, 0.0, 1.0, 0.0, 6.0, 80.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0)
//var G3SG1 = doubleArrayOf(9.0, 0.0, 0.0, 1.0, 0.0, 6.0, 80.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0)
//var SCAR20 = doubleArrayOf(9.0, 0.0, 0.0, 1.0, 0.0, 6.0, 80.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0)
//var SSG08 = doubleArrayOf(9.0, 0.0, 0.0, 1.0, 0.0, 6.0, 80.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0)
//
////Shotguns
//var XM1014 = doubleArrayOf(25.0, 0.0, 0.0, 0.0, 1.0, 6.0, 70.0, 1.0, 2.5, 1.5, 1.0, 20.0, 40.0, 1.0, 20.0)
//var MAG7 = doubleArrayOf(25.0, 0.0, 0.0, 0.0, 1.0, 6.0, 70.0, 1.0, 2.5, 1.5, 1.0, 20.0, 40.0, 1.0, 20.0)
//var SAWED_OFF = doubleArrayOf(25.0, 0.0, 0.0, 0.0, 1.0, 6.0, 70.0, 1.0, 2.5, 1.5, 1.0, 20.0, 40.0, 1.0, 20.0)
//var NOVA = doubleArrayOf(25.0, 0.0, 0.0, 0.0, 1.0, 6.0, 70.0, 1.0, 2.5, 1.5, 1.0, 20.0, 40.0, 1.0, 20.0)
//
////Blank of pistol (default for init?)
//var blankArray = doubleArrayOf(1.0, 1.0, 1.0, 0.0, 1.0, 8.0, 30.0, 10.0, 4.5, 1.0, 1.0, 10.0, 15.0, 1.0, 80.0)