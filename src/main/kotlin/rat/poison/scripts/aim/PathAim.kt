package rat.poison.scripts.aim

import rat.poison.curSettings
import rat.poison.utils.pathAim

fun pathAim() = aimScript(curSettings.int["AIM_DURATION"], { curSettings.bool["ENABLE_PATH_AIM"] }) { dest, current, aimSpeed, aimSpeedDivisor ->
	pathAim(current, dest, aimSpeed, perfect = canPerfect, divisor = aimSpeedDivisor)
}