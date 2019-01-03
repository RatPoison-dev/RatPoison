

package rat.poison.game.hooks

import rat.poison.game.entity.dead
import rat.poison.game.entity.onGround
import rat.poison.game.me
import rat.poison.utils.hook

val onGround = hook(4) {
	me > 0 && !me.dead() && me.onGround()
}