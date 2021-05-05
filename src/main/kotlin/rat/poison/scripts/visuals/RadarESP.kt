package rat.poison.scripts.visuals

import it.unimi.dsi.fastutil.longs.LongArrayList
import rat.poison.curSettings
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.settings.DANGER_ZONE
import rat.poison.utils.common.Vector
import rat.poison.utils.common.distanceTo
import rat.poison.utils.common.every

private val positionVector = Vector()
private val footstepsVec = Vector()
private val entsChecked = LongArrayList()
private val forEnts = arrayOf(EntityType.CCSPlayer)
internal fun radarEsp() = every(100, inGameCheck = true) {
    if (!curSettings.bool["RADAR_ESP"] || DANGER_ZONE) return@every

    if (curSettings.bool["LEGIT_RADAR"]) {
        entsChecked.clear()
        for (i in footSteps.indices) {
            val ent = footSteps[i].ent

            if (ent > 0L) {
                entsChecked.add(ent)
                if (!footSteps[i].open && footSteps[i].ttl > 0 && footstepsVec.set(footSteps[i].x, footSteps[i].y, footSteps[i].z).distanceTo(me.position(positionVector)) <= curSettings.int["LEGIT_RADAR_FOOTSTEPS_DISTANCE"]) {
                    ent.showOnRadar()
                } else {
                    ent.hideOnRadar()
                }
            }
        }

        forEntities(forEnts) {
            if (!entsChecked.contains(it.entity)) {
                it.entity.hideOnRadar()
            }
        }
    } else {
        forEntities(forEnts) {
            val entity = it.entity

            if (entity.dead() || entity == me || entity.dormant()) return@forEntities
            entity.showOnRadar()
        }
    }
}