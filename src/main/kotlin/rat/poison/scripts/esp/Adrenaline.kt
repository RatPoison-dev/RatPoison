package rat.poison.scripts.esp

import com.badlogic.gdx.math.MathUtils
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.dead
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.m_flHealthShotBoostExpirationTime
import rat.poison.game.netvars.NetVarOffsets.m_totalHitsOnServer
import rat.poison.scripts.bombState
import rat.poison.scripts.currentGameTicks
import rat.poison.scripts.hitMarkerCombo
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.notInGame

private var totalHits = 0
private var fl = 0F

fun adrenaline() = every(10) {
    if (!curSettings["ENABLE_ADRENALINE"].strToBool() || !curSettings["ENABLE_ESP"].strToBool() || me.dead() || notInGame) return@every

    val curHits = csgoEXE.int(me + m_totalHitsOnServer)

    if (curHits == 0) {
        totalHits = 0
    } else if (totalHits != curHits) {
        totalHits = curHits
        fl += if (curSettings["MENU"].strToBool()) { hitMarkerCombo * .5F } else { .25F }
    }

    var bFL = 0F

    if (bombState.planted) {
        val tLeft = bombState.timeLeftToExplode
        bFL += (40 - tLeft) / 80F
    }

    if (fl > 0F || bFL > 0F) {
        val cGT = currentGameTicks()
        fl = MathUtils.clamp(fl, 0F, 2.5F)
        csgoEXE[me + m_flHealthShotBoostExpirationTime] = cGT + fl + bFL
        fl -= .01F
    } else {
        fl = 0F
    }
}