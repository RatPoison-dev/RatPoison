package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.offsets.ClientOffsets.dwUse
import rat.poison.strToBool
import rat.poison.utils.every

fun spam(){
spamcheck()
}
fun spamcheck() = every(20, true) {
    if (curSettings["D_SPAM"].strToBool() && keyPressed(curSettings["D_SPAM_KEY"].toInt())) Thread(Runnable {
        CSGO.clientDLL[dwUse] = 5
        Thread.sleep(20)
        CSGO.clientDLL[dwUse] = 4
        Thread.sleep(20)
    }).start()
}