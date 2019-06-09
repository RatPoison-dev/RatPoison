package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.onGround
import rat.poison.game.offsets.ClientOffsets
import rat.poison.settings.BUNNY_HOP_KEY
import org.jire.arrowhead.keyPressed
import rat.poison.strToBool

fun bunnyHop() = onGround {
    if (curSettings["ENABLE_BUNNY_HOP"]!!.strToBool() && /*!cursorEnable &&*/ keyPressed(BUNNY_HOP_KEY)) {
        CSGO.clientDLL[ClientOffsets.dwForceJump] = 6//if (jump) 5 else 4
    }
}