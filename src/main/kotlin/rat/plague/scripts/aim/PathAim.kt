

package rat.plague.scripts.aim

import rat.plague.game.entity.isScoped
import rat.plague.game.me
import rat.plague.settings.*
import rat.plague.utils.pathAim

fun pathAim() = aimScript(AIM_DURATION, { ENABLE_PATH_AIM }) { dest, current, aimSpeed ->
	pathAim(current, dest, aimSpeed,
			sensMultiplier = if (me.isScoped()) 1.0 else AIM_STRICTNESS,
			perfect = perfect.getAndSet(false))
}