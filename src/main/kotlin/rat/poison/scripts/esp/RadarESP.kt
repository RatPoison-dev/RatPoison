package rat.poison.scripts.esp

import rat.poison.checkFlags
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.Entity
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.game.entity.dead
import rat.poison.game.entity.dormant
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.bSpotted
import rat.poison.settings.DANGER_ZONE
import rat.poison.utils.every
import rat.poison.utils.varUtil.strToBool

internal fun radarEsp() = every(100) {
    if (!curSettings["RADAR_ESP"].strToBool() || !checkFlags("ENABLE_ESP") || !checkFlags("RADAR_ESP") || DANGER_ZONE) return@every

    // Legit radar
    if (curSettings["LEGIT_RADAR"].strToBool()) {
        for (i in footSteps.indices) {
            if (!footSteps[i].open) {
                footSteps[i].from.show()
            }
        }
    }

    else {
        forEntities(ccsPlayer) {
            val entity = it.entity

            if (entity.dead() || entity == me || entity.dormant()) return@forEntities false
            entity.show()
            false
        }
    }
}

private fun Entity.show() {
    csgoEXE[this + bSpotted] = true
}