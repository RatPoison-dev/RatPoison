package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.entity.*
import rat.poison.game.entity.dead
import rat.poison.game.entity.health
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets
import rat.poison.robot
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.notInGame

private var totalKills = me.kills()

fun killBind() = every(600) {
    if (!curSettings["KILL_BIND"].strToBool() || me <= 0 || notInGame) return@every

    val curKills = me.kills()
    if (curKills != totalKills) {
        totalKills = curKills
        robot.keyPress(curSettings["KILL_BIND_KEY"].toInt())
        robot.keyRelease(curSettings["KILL_BIND_KEY"].toInt())
    }
}