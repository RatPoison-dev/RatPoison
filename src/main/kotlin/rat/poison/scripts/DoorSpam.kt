package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.entity.dead
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets.dwUse
import rat.poison.strToBool
import rat.poison.utils.every

fun doorSpam() = every(20) {
    if (!curSettings["D_SPAM"].strToBool() || me.dead()) return@every

    updateCursorEnable()
    if (cursorEnable) return@every

    if (keyPressed(curSettings["D_SPAM_KEY"].toInt())) {
        Thread(Runnable {
            CSGO.clientDLL[dwUse] = 5
            Thread.sleep(20)
            CSGO.clientDLL[dwUse] = 4
            Thread.sleep(20)
        }).start()
    }
}