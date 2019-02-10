package rat.poison.scripts

import rat.poison.game.CSGO
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.onGround
import rat.poison.game.offsets.ClientOffsets
import rat.poison.settings.BUNNY_HOP_KEY
import rat.poison.settings.ENABLE_BUNNY_HOP
import org.jire.arrowhead.keyPressed

fun bunnyHop() = onGround {
    if (ENABLE_BUNNY_HOP && !cursorEnable && keyPressed(BUNNY_HOP_KEY)) {
        CSGO.clientDLL[ClientOffsets.dwForceJump] = 6//if (jump) 5 else 4
    }
}