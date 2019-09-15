package rat.poison.scripts.aim

import rat.poison.convStrToArray
import rat.poison.curSettings
import rat.poison.game.Weapons
import rat.poison.game.entity.weapon
import rat.poison.game.me
import rat.poison.settingsLoaded
import rat.poison.strToBool
import rat.poison.utils.every

var override = false
var curWep = Weapons.AK47

fun setAim() = every(256){
    try {
        if (settingsLoaded) { //If we have settings to read
            curWep = me.weapon()

            //V--Update aim settings for current weapons--V\\
            if (curWep.rifle || curWep.smg || curWep.pistol || curWep.sniper || curWep.shotgun) {
                val curWepSettings = convStrToArray(curSettings[curWep.name])

                if (curWepSettings[0]!!.toBool()) {
                    curSettings["FACTOR_RECOIL"] = curWepSettings[1]!!.toBool()
                    curSettings["ENABLE_FLAT_AIM"] = curWepSettings[2]!!.toBool()
                    curSettings["ENABLE_PATH_AIM"] = curWepSettings[3]!!.toBool()
                    curSettings["AIM_BONE"] = curWepSettings[4]!!.toInt()
                    curSettings["AIM_FOV"] = curWepSettings[5]!!.toInt()
                    curSettings["AIM_SPEED"] = curWepSettings[6]!!.toInt()
                    curSettings["AIM_SMOOTHNESS"] = curWepSettings[7]
                    curSettings["AIM_STRICTNESS"] = curWepSettings[8]
                    curSettings["PERFECT_AIM"] = curWepSettings[9]!!.toBool()
                    curSettings["PERFECT_AIM_FOV"] = curWepSettings[10]!!.toInt()
                    curSettings["PERFECT_AIM_CHANCE"] = curWepSettings[11]!!.toInt()
                    curSettings["ENABLE_SCOPED_ONLY"] = curWepSettings[12]!!.toBool()
                    override = true
                } else {
                    override = false
                }
            }
        } else {
            override = false
        }

        if (!override) { //If the current weapon isn't checked to override
            var strPre = "" //Prefix for settings wep
            when { //Set the aim settings to the weapon's category settings
                me.weapon().rifle -> {
                    strPre = "RIFLE"
                }
                me.weapon().smg -> {
                    strPre = "SMG"
                }
                me.weapon().pistol -> {
                    strPre = "PISTOL"
                }
                me.weapon().sniper -> {
                    strPre = "SNIPER"
                }
                me.weapon().shotgun -> {
                    strPre = "SHOTGUN"
                }
            }

            if (strPre != "") {
                curSettings["FACTOR_RECOIL"] = curSettings[strPre + "_FACTOR_RECOIL"].strToBool()
                curSettings["AIM_BONE"] = curSettings[strPre + "_AIM_BONE"].toInt()
                curSettings["AIM_FOV"] = curSettings[strPre + "_AIM_FOV"].toInt()
                curSettings["AIM_SPEED"] = curSettings[strPre + "_AIM_SPEED"].toInt()
                curSettings["AIM_SMOOTHNESS"] = curSettings[strPre + "_AIM_SMOOTHNESS"].toDouble()
                curSettings["AIM_STRICTNESS"] = curSettings[strPre + "_AIM_STRICTNESS"].toDouble()
                curSettings["PERFECT_AIM"] = curSettings[strPre + "_PERFECT_AIM"].strToBool()
                curSettings["PERFECT_AIM_FOV"] = curSettings[strPre + "_PERFECT_AIM_FOV"].toInt()
                curSettings["PERFECT_AIM_CHANCE"] = curSettings[strPre + "_PERFECT_AIM_CHANCE"].toInt()
                curSettings["ENABLE_FLAT_AIM"] = curSettings[strPre + "_ENABLE_FLAT_AIM"].strToBool()
                curSettings["ENABLE_PATH_AIM"] = curSettings[strPre + "_ENABLE_PATH_AIM"].strToBool()
                curSettings["ENABLE_SCOPED_ONLY"] = curSettings["SNIPER_ENABLE_SCOPED_ONLY"].strToBool()
            }
        }
    } catch (e: Exception) {println("SetAim failure")} //Fix crashing
}

fun Double.toBool() = this == 1.0