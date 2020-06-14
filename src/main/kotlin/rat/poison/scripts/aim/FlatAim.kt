package rat.poison.scripts.aim

import rat.poison.curSettings
import rat.poison.utils.varUtil.strToBool
import rat.poison.utils.writeAim

fun flatAim() = aimScript(curSettings["AIM_DURATION"].toInt(), { curSettings["ENABLE_FLAT_AIM"].strToBool() }) { dest, current, aimSpeed, _ ->
	writeAim(current, dest, aimSpeed.toDouble())
}