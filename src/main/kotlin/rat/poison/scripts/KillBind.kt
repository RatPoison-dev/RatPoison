package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.entity.Entity
import rat.poison.game.entity.EntityType
import rat.poison.game.entity.health
import rat.poison.game.forEntities
import rat.poison.robot
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool

val killBinds = Array(256) { Binds() }
data class Binds(var ent: Entity = 0L, var open: Boolean = true)

fun killBind() = every(600) {
    if (!curSettings["KILL_BIND"].strToBool()) return@every

    forEntities(EntityType.CCSPlayer) {
        val idx = emptySlot(it.entity)
        killBinds[idx].apply {
            ent = it.entity
            open = false
        }
    }
    for (i in killBinds.indices) {
        if (!killBinds[i].open) {
            if (killBinds[i].ent.health() <= 0) {
                killBinds[i].apply {
                    open = true
                    ent = 0L
                }
                robot.keyPress(curSettings["KILL_BIND_KEY"].toInt())
                robot.keyRelease(curSettings["KILL_BIND_KEY"].toInt())
            }
        }
    }
}


private fun emptySlot(ent: Entity): Int {
    var idx = -1

    for (i in killBinds.indices) {
        if (killBinds[i].open || killBinds[i].ent == ent) {
            idx = i
            break
        }
    }

    return idx
}