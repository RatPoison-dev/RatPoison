package rat.poison.scripts.esp

import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.Entity
import rat.poison.game.entity.EntityType
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.game.entity.dead
import rat.poison.game.entity.dormant
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.bSpotted
import rat.poison.settings.DANGER_ZONE
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool

internal fun radarEsp() = every(100) {
    if (!curSettings["RADAR_ESP"].strToBool() || DANGER_ZONE) return@every

    if (curSettings["LEGIT_RADAR"].strToBool()) {
        for (i in footSteps.indices) {
            val ent = footSteps[i].ent
            if (ent > 0L && !footSteps[i].open && footSteps[i].ttl > 0) {
                ent.show()
            }
        }
    } else {
        forEntities(EntityType.CCSPlayer) {
            val entity = it.entity

            if (entity.dead() || entity == me || entity.dormant()) return@forEntities
            entity.show()
        }
    }
}

private fun Entity.show() {
    csgoEXE[this + bSpotted] = true
}