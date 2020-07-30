package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.entity.Entity
import rat.poison.game.entity.EntityType
import rat.poison.game.entity.dead
import rat.poison.game.entity.health
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets
import rat.poison.robot
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.notInGame

val killBinds = Array(256) { Binds() }
data class Binds(var ent: Entity = 0L, var open: Boolean = true)
private var totalHits = 0

fun killBind() = every(600) {
    if (!curSettings["KILL_BIND"].strToBool() || me.dead() || me <= 0 || notInGame) return@every

    forEntities(EntityType.CCSPlayer) {
        val ent = it.entity

        if (ent != me && ent > 0) {
            val idx = emptySlot(it.entity)
            if (idx != -1) {
                killBinds[idx].apply {
                    this.ent = ent
                    open = false
                }
            }
        }
    }

    val curHits = CSGO.csgoEXE.int(me + NetVarOffsets.m_totalHitsOnServer)

    for (i in killBinds.indices) {
        if (!killBinds[i].open) {
            if (killBinds[i].ent.health() <= 0) {
                if (curHits == 0) {
                    totalHits = 0
                }
                else if (totalHits != curHits) {
                    // entity was likely killed by localPlayer
                    totalHits = curHits
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