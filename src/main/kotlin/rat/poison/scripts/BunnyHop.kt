package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.entity.onGround
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets.dwForceJump
import rat.poison.scripts.userCmd.meDead
import rat.poison.utils.common.every
import rat.poison.utils.keybindEval
import rat.poison.utils.randInt

fun bunnyHop() = every(1, continuous = true, inGameCheck = true) {
    if (!curSettings.bool["ENABLE_BUNNY_HOP"] || me < 0  || meDead || !me.onGround() || cursorEnable || !keybindEval("ENABLE_BUNNY_HOP_KEY") || (randInt(0, 100) > curSettings.int["BHOP_HITCHANCE"])) return@every
    updateCursorEnable()
    if (cursorEnable) return@every
    CSGO.clientDLL[dwForceJump] = 6
}