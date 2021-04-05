package rat.poison.scripts.visuals

import com.badlogic.gdx.audio.Sound
import rat.poison.SETTINGS_DIRECTORY
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.m_totalHitsOnServer
import rat.poison.overlay.App.assetManager
import rat.poison.settings.MENUTOG
import rat.poison.utils.common.every

private var totalHits = 0

fun hitSoundEsp() = every(50, inGameCheck = true) {
    if (!curSettings.bool["ENABLE_HITSOUND"] || MENUTOG || !curSettings.bool["MENU"] || me < 0) return@every

    val curHits = csgoEXE.int(me + m_totalHitsOnServer)
    if (curHits < 0 || curHits > 255) return@every


    if (totalHits != curHits) {
        totalHits = curHits
        val hitSound = assetManager.get<Sound>("$SETTINGS_DIRECTORY/hitsounds/${curSettings["KILLSOUND_FILE_NAME"].replace("\"", "")}")
        hitSound.play(curSettings.float["HITSOUND_VOLUME"])
        totalHits = curHits
    }
}