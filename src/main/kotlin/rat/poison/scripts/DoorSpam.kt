package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.game.offsets.ClientOffsets.dwUse
import rat.poison.scripts.aim.meDead
import rat.poison.utils.every
import rat.poison.utils.keyPressed

fun doorSpam() = every(20, inGameCheck = true) {
    if (!curSettings.bool["D_SPAM"] || meDead) return@every

    updateCursorEnable()
    if (cursorEnable) return@every

    if (keyPressed(curSettings.int["D_SPAM_KEY"])) {
        Thread(Runnable {
            CSGO.clientDLL[dwUse] = 5
            Thread.sleep(20)
            CSGO.clientDLL[dwUse] = 4
            Thread.sleep(20)
        }).start()
    }
}