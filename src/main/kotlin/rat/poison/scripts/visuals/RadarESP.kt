package rat.poison.scripts.visuals

import rat.poison.curSettings
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.settings.DANGER_ZONE
import rat.poison.utils.every
import rat.poison.utils.vector

internal fun radarEsp() = every(100, inGameCheck = true) {
    if (!curSettings.bool["RADAR_ESP"] || DANGER_ZONE) return@every

    if (curSettings.bool["LEGIT_RADAR"]) {
        val entsChecked = mutableListOf<Long>()
        for (i in footSteps.indices) {
            val ent = footSteps[i].ent

            if (ent > 0L) {
                entsChecked.add(ent)
                if (!footSteps[i].open && footSteps[i].ttl > 0 && vector(footSteps[i].x, footSteps[i].y, footSteps[i].z).distanceTo(me.position()) <= curSettings["LEGIT_RADAR_FOOTSTEPS_DISTANCE"].toInt()) {
                    ent.showOnRadar()
                } else {
                    ent.hideOnRadar()
                }
            }
        }

        forEntities(EntityType.CCSPlayer) {
            if (!entsChecked.contains(it.entity)) {
                it.entity.hideOnRadar()
            }
        }
    } else {
        forEntities(EntityType.CCSPlayer) {
            val entity = it.entity

            if (entity.dead() || entity == me || entity.dormant()) return@forEntities
            entity.showOnRadar()
        }
    }
}