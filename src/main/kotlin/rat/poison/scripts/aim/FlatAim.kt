package rat.poison.scripts.aim

import rat.poison.curSettings
import rat.poison.utils.writeAim
import rat.poison.strToBool

fun flatAim() = aimScript(curSettings["AIM_DURATION"]!!.toInt(), { curSettings["ENABLE_FLAT_AIM"]!!.strToBool() }) { dest, current, aimSpeed ->
	writeAim(current, dest, aimSpeed.toDouble())
}