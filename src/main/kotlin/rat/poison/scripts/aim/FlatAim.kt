

package rat.poison.scripts.aim

import rat.poison.settings.AIM_DURATION
import rat.poison.settings.ENABLE_FLAT_AIM
import rat.poison.utils.writeAim

fun flatAim() = aimScript(AIM_DURATION, { ENABLE_FLAT_AIM }) { dest, current, aimSpeed ->
	writeAim(current, dest, aimSpeed.toDouble())
}