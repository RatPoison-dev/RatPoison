package rat.poison.scripts.aim

import rat.poison.curSettings
import rat.poison.strToBool
import rat.poison.utils.pathAim

fun pathAim() = aimScript(curSettings["AIM_DURATION"].toInt(), { curSettings["ENABLE_PATH_AIM"].strToBool() }) { dest, current, aimSpeed, aimSpeedDivisor ->
	pathAim(current, dest, aimSpeed, perfect = perfect, divisor = aimSpeedDivisor)
}