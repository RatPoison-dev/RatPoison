package rat.poison.scripts.visuals

import com.badlogic.gdx.math.MathUtils
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.m_flHealthShotBoostExpirationTime
import rat.poison.game.netvars.NetVarOffsets.m_totalHitsOnServer
import rat.poison.scripts.aim.meDead
import rat.poison.scripts.bombState
import rat.poison.scripts.currentGameTicks
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool

private var totalHits = 0
private var fl = 0F

fun adrenaline() = every(10, inGameCheck = true) {
    if (!curSettings["ENABLE_ADRENALINE"].strToBool() || !curSettings["ENABLE_ESP"].strToBool() || meDead || me < 0) return@every

    val curHits = csgoEXE.int(me + m_totalHitsOnServer)
    if (curHits < 0 || curHits > 255) return@every

    if (curHits == 0) {
        totalHits = 0
    } else if (totalHits != curHits) {
        totalHits = curHits
        fl += curSettings["ADRENALINE_STRENGTH"].toFloat()
    }

    var bFL = 0F

    if (bombState.planted && curSettings["ADRENALINE_BOMB_TIME"].strToBool()) {
        val tLeft = bombState.timeLeftToExplode
        bFL += (40 - tLeft) / 80F
    }

    if (fl > 0F || bFL > 0F) {
        val cGT = currentGameTicks()
        fl = MathUtils.clamp(fl, 0F, 2.5F)
        csgoEXE[me + m_flHealthShotBoostExpirationTime] = cGT + fl + bFL
        fl -= curSettings["ADRENALINE_COOLDOWN"].toFloat()
    } else {
        fl = 0F
    }
}