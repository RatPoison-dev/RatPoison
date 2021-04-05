package rat.poison.scripts.visuals

import com.badlogic.gdx.audio.Sound
import rat.poison.SETTINGS_DIRECTORY
import rat.poison.curSettings
import rat.poison.game.entity.kills
import rat.poison.game.me
import rat.poison.overlay.App.assetManager
import rat.poison.scripts.aim.meDead
import rat.poison.utils.common.every
import rat.poison.utils.common.inGame

private var totalKills = when (inGame) {
    true -> me.kills()
    else -> 0
}
private var opened = false

fun killSoundEsp() = every(50, inGameCheck = true) {
    if (!curSettings.bool["ENABLE_KILLSOUND"] || meDead || me < 0 || !curSettings.bool["ENABLE_ESP"]) return@every
    val curKills = me.kills()
    if (curKills != totalKills) {
        val killSound = assetManager.get<Sound>("$SETTINGS_DIRECTORY/hitsounds/${curSettings["KILLSOUND_FILE_NAME"].replace("\"", "")}")
        totalKills = curKills
        killSound.play(curSettings.float["KILLSOUND_VOLUME"])
    }
}