package rat.poison.scripts.aim

import rat.poison.curSettings
import rat.poison.game.entity.isScoped
import rat.poison.game.me
import rat.poison.utils.pathAim

fun pathAim() = aimScript(3, { true }) { dest, current, aimSpeed ->
	pathAim(current, dest, aimSpeed,
			sensMultiplier = if (me.isScoped()) 1.0 else 2.0,
			perfect = perfect.getAndSet(false))
}