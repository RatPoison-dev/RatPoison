package rat.poison.scripts.visuals

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import org.jire.kna.int
import rat.poison.SETTINGS_DIRECTORY
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.m_totalHitsOnServer
import rat.poison.overlay.App.assetManager
import rat.poison.settings.MENUTOG
import rat.poison.utils.every

private var totalHits = 0
private var opened = false
lateinit var hitSound: Sound

fun hitSoundEsp() = every(50, inGameCheck = true) {
    if (!curSettings.bool["ENABLE_HITSOUND"] || MENUTOG || me < 0) return@every

    val curHits = csgoEXE.int(me + m_totalHitsOnServer)
    if (curHits < 0 || curHits > 255) return@every

    if (!opened) {
        try {
            updateHitsound(curSettings["HITSOUND_FILE_NAME"].replace("\"", ""))
            opened = true
            totalHits = curHits
        } catch (e: Exception) { e.printStackTrace() }
    }
    else if (curHits == 0) {
        totalHits = 0
    }
    else if (totalHits != curHits)
    {
        hitSound.play(curSettings.double["HITSOUND_VOLUME"].toFloat())
        val hitSound = assetManager.get<Sound>("$SETTINGS_DIRECTORY/hitsounds/${curSettings["HITSOUND_FILE_NAME"].replace("\"", "")}")
        hitSound.play(curSettings.double["HITSOUND_VOLUME"].toFloat())
        totalHits = curHits
    }
}

fun updateHitsound(fileName: String) {
    if (::hitSound.isInitialized) {
        hitSound.dispose()
    }
    hitSound = Gdx.audio.newSound(Gdx.files.internal("$SETTINGS_DIRECTORY\\hitsounds\\$fileName"))
}