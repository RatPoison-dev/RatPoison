

package rat.plague.game.hooks

import rat.plague.game.entity.dead
import rat.plague.game.entity.onGround
import rat.plague.game.me
import rat.plague.utils.hook

val onGround = hook(4) {
	me > 0 && !me.dead() && me.onGround()
}