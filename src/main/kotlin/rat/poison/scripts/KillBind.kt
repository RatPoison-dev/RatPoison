package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.entity.kills
import rat.poison.game.me
import rat.poison.robot
import rat.poison.scripts.aim.meDead
import rat.poison.utils.every

private var totalKills = me.kills()

fun killBind() = every(600, inGameCheck = true) {
	if (!curSettings.bool["KILL_BIND"] || me <= 0 || meDead) return@every
	val key = curSettings.int["KILL_BIND_KEY"]
	if (key < 0) return@every
	
	val curKills = me.kills()
	if (curKills != totalKills) {
		totalKills = curKills
		robot.keyPress(key)
		robot.keyRelease(key)
	}
}