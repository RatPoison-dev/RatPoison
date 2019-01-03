

package rat.poison.scripts.aim

import rat.poison.game.entity.isScoped
import rat.poison.game.me
import rat.poison.settings.*
import rat.poison.utils.pathAim

fun pathAim() = aimScript(AIM_DURATION, { ENABLE_PATH_AIM }) { dest, current, aimSpeed ->
	pathAim(current, dest, aimSpeed,
			sensMultiplier = if (me.isScoped()) 1.0 else AIM_STRICTNESS,
			perfect = perfect.getAndSet(false))
}