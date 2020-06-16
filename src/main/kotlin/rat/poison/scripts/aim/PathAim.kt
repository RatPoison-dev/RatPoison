package rat.poison.scripts.aim

import rat.poison.curSettings
import rat.poison.utils.pathAim
import rat.poison.utils.varUtil.strToBool

fun pathAim() = aimScript(curSettings["AIM_DURATION"].toInt(), { curSettings["ENABLE_PATH_AIM"].strToBool() }) { dest, current, aimSpeed, aimSpeedDivisor ->
	pathAim(current, dest, aimSpeed, perfect = canPerfect, divisor = aimSpeedDivisor)
}