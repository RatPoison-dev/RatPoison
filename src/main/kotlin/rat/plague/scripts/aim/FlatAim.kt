

package rat.plague.scripts.aim

import rat.plague.settings.AIM_DURATION
import rat.plague.settings.ENABLE_FLAT_AIM
import rat.plague.utils.writeAim

fun flatAim() = aimScript(AIM_DURATION, { ENABLE_FLAT_AIM }) { dest, current, aimSpeed ->
	writeAim(current, dest, aimSpeed.toDouble())
}