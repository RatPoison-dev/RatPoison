package rat.poison.scripts.visuals

import rat.poison.curSettings
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.scripts.aim.meDead
import rat.poison.settings.DANGER_ZONE
import rat.poison.utils.MedPriority
import rat.poison.utils.Vector


internal fun radarEsp() = MedPriority.every(100, inGameCheck = true) {
    if (!curSettings.bool["RADAR_ESP"] || DANGER_ZONE) return@every

    if (curSettings.bool["RADAR_ESP_DEAD"] && !meDead) return@every

    if (curSettings.bool["LEGIT_RADAR"]) {
        val entsChecked = mutableListOf<Long>()
        for (i in footSteps.indices) {
            val ent = footSteps[i].ent

            if (ent > 0L) {
                entsChecked.add(ent)
                if (!footSteps[i].open && footSteps[i].ttl > 0 && Vector(footSteps[i].x, footSteps[i].y, footSteps[i].z).distanceTo(me.position()) <= curSettings.int["AUDIBLE_ESP_RANGE"]) {
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