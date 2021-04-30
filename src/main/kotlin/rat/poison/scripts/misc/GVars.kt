package rat.poison.scripts.misc

import rat.poison.game.me
import rat.poison.scripts.getGlobalVars
import rat.poison.utils.Structs.GlobalVars
import rat.poison.utils.common.every

var haveGvars = false
var gvars = GlobalVars()

fun updateGVars() = every(15, true, inGameCheck = true){
    if (me <= 0) return@every
    val tGvars = getGlobalVars()
    if (tGvars != null) {
        gvars = tGvars
        haveGvars = true
    } else {
        haveGvars = false
    }
}