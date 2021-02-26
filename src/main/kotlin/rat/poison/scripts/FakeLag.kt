package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.entity.isProtected
import rat.poison.game.me
import rat.poison.scripts.aim.meDead
import rat.poison.settings.AIM_KEY
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.keyPressed

fun toMilliseconds(ticks: Int): Long {
    return (gvars.intervalPerTick * 1000 * ticks).toLong()
}

fun fakeLag() = every(12, inGameCheck = true) {
    //engineDLL.boolean(bVoiceRecording) //not tested
    if (meDead || me < 0 || !curSettings["FAKE_LAG"].strToBool() || keyPressed(AIM_KEY) || me.isProtected()) return@every
    sendPacket(false)
    Thread.sleep(toMilliseconds(curSettings["FAKE_LAG_TICKS"].toInt()))
    sendPacket(true)
}