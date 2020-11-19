package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.entity.onGround
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets.dwForceJump
import rat.poison.scripts.aim.meDead
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.keyPressed
import rat.poison.utils.randInt

fun bunnyHop() = every(1, continuous = true, inGameCheck = true) {
    if (!curSettings["ENABLE_BUNNY_HOP"].strToBool() || me < 0  || meDead || !me.onGround() || cursorEnable || !keyPressed(curSettings["ENABLE_BUNNY_HOP_KEY"].toInt()) || (randInt(0, 100) > curSettings["BHOP_HITCHANCE"].toInt())) return@every
    updateCursorEnable()
    if (cursorEnable) return@every
    CSGO.clientDLL[dwForceJump] = 6
}