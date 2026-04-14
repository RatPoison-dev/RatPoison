package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.game.offsets.ClientOffsets.dwUse
import rat.poison.input
import rat.poison.safe_mode
import rat.poison.scripts.aim.meDead
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool

fun doorSpam() = every(20, inGameCheck = true) {
    if (!curSettings["D_SPAM"].strToBool() || meDead) return@every

    updateCursorEnable()
    if (cursorEnable) return@every

    if (input.keyPressed(curSettings["D_SPAM_KEY"].toInt())) {
        if (safe_mode) {
            input.keyPress(0x45)
            Thread.sleep(20)
            input.keyPressed(0x45)
            Thread.sleep(20)
        }

        else Thread {
            CSGO.clientDLL[dwUse] = 5
            Thread.sleep(20)
            CSGO.clientDLL[dwUse] = 4
            Thread.sleep(20)
        }.start()
    }
}