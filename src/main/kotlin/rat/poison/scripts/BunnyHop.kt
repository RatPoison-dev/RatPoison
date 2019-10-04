package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.hooks.cursorEnable
import org.jire.arrowhead.keyPressed
import rat.poison.game.entity.dead
import rat.poison.game.entity.onGround
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets.dwForceJump
import rat.poison.strToBool
import rat.poison.utils.every
import java.awt.event.KeyEvent.VK_SPACE

fun bunnyHop() = every(4) {
    if (curSettings["ENABLE_BUNNY_HOP"].strToBool() && keyPressed(VK_SPACE) && (me > 0 && !me.dead() && me.onGround())) {
        updateCursorEnable()
        if (cursorEnable) return@every
        CSGO.clientDLL[dwForceJump] = 6
    }
}