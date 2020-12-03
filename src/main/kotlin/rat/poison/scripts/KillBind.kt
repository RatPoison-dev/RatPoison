package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.entity.kills
import rat.poison.game.me
import rat.poison.robot
import rat.poison.scripts.aim.meDead
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool

private var totalKills = me.kills()

fun killBind() = every(600, inGameCheck = true) {
    if (!curSettings["KILL_BIND"].strToBool() || me <= 0 || meDead) return@every

    val curKills = me.kills()
    if (curKills != totalKills) {
        totalKills = curKills
        robot.keyPress(curSettings["KILL_BIND_KEY"].toInt())
        robot.keyRelease(curSettings["KILL_BIND_KEY"].toInt())
    }
}