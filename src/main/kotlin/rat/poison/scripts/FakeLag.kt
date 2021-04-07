package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.CSGO.engineDLL
import rat.poison.game.me
import rat.poison.game.offsets.EngineOffsets.bVoiceRecording
import rat.poison.scripts.aim.canShoot
import rat.poison.scripts.aim.meDead
import rat.poison.settings.AIM_KEY
import rat.poison.utils.common.every
import rat.poison.utils.common.keyPressed

fun toMilliseconds(ticks: Int): Long {
    return (gvars.intervalPerTick * 1000 * ticks).toLong()
}

fun recordingVoice(): Boolean = engineDLL.boolean(bVoiceRecording)

fun fakeLag() = every(12, inGameCheck = true) {
    if (meDead || me < 0 || !curSettings.bool["FAKE_LAG"] || keyPressed(AIM_KEY) || !me.canShoot(teamCheck = false) || recordingVoice()) return@every
    sendPacket(false)
    Thread.sleep(toMilliseconds(curSettings.int["FAKE_LAG_TICKS"]))
    sendPacket(true)
}