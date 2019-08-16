package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.onGround
import rat.poison.game.offsets.ClientOffsets
import org.jire.arrowhead.keyPressed
import rat.poison.game.entity.onGround
import rat.poison.game.me
import rat.poison.strToBool

fun bunnyHop() = onGround {
    if (curSettings["ENABLE_BUNNY_HOP"]!!.strToBool() && !cursorEnable && keyPressed(curSettings["BUNNY_HOP_KEY"]!!.toInt()) && me.onGround()) {
        CSGO.clientDLL[ClientOffsets.dwForceJump] = 6
    }
}