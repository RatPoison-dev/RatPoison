package rat.poison.scripts.visuals

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import rat.poison.SETTINGS_DIRECTORY
import rat.poison.curSettings
import rat.poison.game.entity.kills
import rat.poison.game.me
import rat.poison.scripts.aim.meDead
import rat.poison.utils.every
import rat.poison.utils.inGame

lateinit var killSound: Sound
private var totalKills = when (inGame) {
    true -> me.kills()
    else -> 0
}
private var opened = false

fun killSoundEsp() = every(50, inGameCheck = true) {
    if (!curSettings.bool["ENABLE_KILLSOUND"] || meDead || me < 0 || !curSettings.bool["ENABLE_ESP"]) return@every
    val curKills = me.kills()
    if (!opened) {
        try {
            updateKillSound(curSettings["KILLSOUND_FILE_NAME"].replace("\"", ""))
            opened = true
            totalKills = curKills
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    else if (curKills != totalKills) {
        totalKills = curKills
        killSound.play(curSettings.double["KILLSOUND_VOLUME"].toFloat())
    }
}

fun updateKillSound(fileName: String) {
    if (::killSound.isInitialized) {
        killSound.dispose()
    }
    killSound = Gdx.audio.newSound(Gdx.files.internal("$SETTINGS_DIRECTORY\\hitsounds\\$fileName"))
}