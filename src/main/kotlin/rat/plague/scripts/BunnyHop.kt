

package rat.plague.scripts

import com.badlogic.gdx.math.MathUtils
import rat.plague.game.CSGO
import rat.plague.game.hooks.cursorEnable
import rat.plague.game.hooks.onGround
import rat.plague.game.offsets.ClientOffsets
import rat.plague.settings.BUNNY_HOP_KEY
import rat.plague.settings.ENABLE_BUNNY_HOP
import rat.plague.settings.LEAGUE_MODE
import rat.plague.utils.mouseWheel
import org.jire.arrowhead.keyPressed

fun bunnyHop() = onGround {
    if (ENABLE_BUNNY_HOP && !cursorEnable && keyPressed(BUNNY_HOP_KEY)) {
        if (LEAGUE_MODE) {
            mouseWheel(MathUtils.randomSign() * MathUtils.random(10, 70))
        } else {
            CSGO.clientDLL[ClientOffsets.dwForceJump] = 6//if (jump) 5 else 4
        }
    }
}