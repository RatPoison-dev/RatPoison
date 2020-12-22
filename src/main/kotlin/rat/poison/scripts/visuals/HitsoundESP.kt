package rat.poison.scripts.visuals

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
import rat.poison.utils.generalUtil.strToBool

private var totalHits = 0
private var opened = false

fun hitSoundEsp() = every(50, inGameCheck = true) {
    if (!curSettings["ENABLE_HITSOUND"].strToBool() || MENUTOG || !curSettings["MENU"].strToBool() || me < 0) return@every

    val curHits = csgoEXE.int(me + m_totalHitsOnServer)
    if (curHits < 0 || curHits > 255) return@every
    if (!opened) {
        try {
            opened = true
            totalHits = curHits
        } catch (e: Exception) { e.printStackTrace() }
    }
    else if (curHits == 0) {
        totalHits = 0
    }
    else if (totalHits != curHits)
    {
        val hitSound = assetManager.get<Sound>("$SETTINGS_DIRECTORY/hitsounds/${curSettings["HITSOUND_FILE_NAME"].replace("\"", "")}")
        hitSound.play(curSettings["HITSOUND_VOLUME"].toDouble().toFloat())
        totalHits = curHits
    }
}