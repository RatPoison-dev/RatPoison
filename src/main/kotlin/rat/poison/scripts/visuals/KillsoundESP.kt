package rat.poison.scripts.visuals

import com.badlogic.gdx.audio.Sound
import rat.poison.SETTINGS_DIRECTORY
import rat.poison.curSettings
import rat.poison.game.entity.kills
import rat.poison.game.me
import rat.poison.overlay.App
import rat.poison.scripts.aim.meDead
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.inGame

private var totalKills = when (inGame) {
    true -> me.kills()
    else -> 0
}
private var opened = false

fun killSoundEsp() = every(50, inGameCheck = true) {
    if (!curSettings["ENABLE_KILLSOUND"].strToBool() || meDead || me < 0 || !curSettings["ENABLE_ESP"].strToBool()) return@every
    val curKills = me.kills()
    if (!opened) {
        try {
            opened = true
            totalKills = curKills
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    else if (curKills != totalKills) {
        val killSound = App.assetManager.get<Sound>("$SETTINGS_DIRECTORY/hitsounds/${curSettings["KILLSOUND_FILE_NAME"].replace("\"", "")}")
        killSound.play(curSettings["KILLSOUND_VOLUME"].toDouble().toFloat())
        totalKills = curKills
    }
}