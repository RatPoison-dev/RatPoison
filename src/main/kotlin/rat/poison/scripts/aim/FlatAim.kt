package rat.poison.scripts.aim

import rat.poison.curSettings
import rat.poison.utils.writeAim

fun flatAim() = aimScript(
	curSettings.int["AIM_DURATION"],
	{ curSettings.bool["ENABLE_FLAT_AIM"] }) { dest, current, aimSpeed, aimSpeedDivisor ->
	writeAim(current, dest, aimSpeed.toFloat(), divisor = aimSpeedDivisor)
}