package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.robot
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.inGame

fun autoAccept() = every(1000) {
    if (!curSettings["AUTOACCEPT"].strToBool() || inGame) return@every
    robot.mouseMove(gameWidth/2, gameHeight/2)
    rightClick()
}