package rat.poison.scripts.aim

import rat.poison.curSettings
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.writeAim

fun flatAim() = aimScript(curSettings["AIM_DURATION"].toInt(), { curSettings["ENABLE_FLAT_AIM"].strToBool() }) { dest, current, aimSpeed, aimSpeedDivisor ->
	writeAim(current, dest, aimSpeed.toFloat(), divisor = aimSpeedDivisor)
}