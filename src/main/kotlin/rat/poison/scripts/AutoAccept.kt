package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.CSGO.rect
import rat.poison.robot
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.inGame

fun autoAccept() = every(1000) {
    if (!curSettings["AUTOACCEPT"].strToBool() || inGame) return@every
    robot.mouseMove(rect.right / 2 + 3, rect.bottom / 10 * 6 + 2)
    leftClick()
}