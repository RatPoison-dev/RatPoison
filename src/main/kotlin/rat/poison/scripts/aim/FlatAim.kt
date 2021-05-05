package rat.poison.scripts.aim

import rat.poison.curSettings
import rat.poison.settings.ENABLE_FLAT_AIM
import rat.poison.utils.writeAim

fun flatAim() = aimScript(curSettings.int["AIM_DURATION"], { ENABLE_FLAT_AIM && !curSettings.bool["UCMD_SILENT_AIM"] }) { dest, current, smoothing ->
	writeAim(current, dest, smoothing)
}