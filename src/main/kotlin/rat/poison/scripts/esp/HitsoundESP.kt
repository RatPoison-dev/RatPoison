package rat.poison.scripts.esp

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.kotcrab.vis.ui.VisUI
import rat.poison.SETTINGS_DIRECTORY
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.m_totalHitsOnServer
import rat.poison.strToBool
import rat.poison.utils.every
import rat.poison.utils.notInGame

private var totalHits = 0
var opened = false
lateinit var hitSound: Sound

fun hitSoundEsp() = every(50) {
    if (!curSettings["MENU"].strToBool() || !curSettings["ENABLE_HITSOUND"].strToBool() || notInGame || me <= 0) return@every

    if (VisUI.isLoaded()) {
        val curHits = csgoEXE.int(me + m_totalHitsOnServer)

        if (!opened) {
            try {
                updateHitsound(curSettings["HITSOUND_FILE_NAME"].replace("\"", ""))
                opened = true
                totalHits = curHits
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if (curHits == 0) {
            totalHits = 0
        } else if (totalHits != curHits) {
            hitSound.play(curSettings["HITSOUND_VOLUME"].toDouble().toFloat())
            totalHits = curHits
        }
    }
}

fun updateHitsound(fileName: String) {
    if (::hitSound.isInitialized) {
        hitSound.dispose()
    }
    hitSound = Gdx.audio.newSound(Gdx.files.internal("$SETTINGS_DIRECTORY\\hitsounds\\$fileName"))
}