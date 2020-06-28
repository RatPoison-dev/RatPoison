package rat.poison.scripts.esp

import rat.poison.curSettings
import rat.poison.utils.every

private var stepTimer = 0

fun constructFootSteps() = every(1) {
    stepTimer++
    if (stepTimer >= curSettings["FOOTSTEP_UPDATE"].toInt()) {
        constructSteps()
        stepTimer = 0
    }
    subtractTtl()
}

fun subtractTtl() {
    for (i in footSteps.indices) {
        footSteps[i].ttl--
        if (footSteps[i].ttl <= 0) { //Reset
            footSteps[i].apply {
                x = 0.0
                y = 0.0
                z = 0.0
                ttl = curSettings["FOOTSTEP_TTL"].toInt()
                open = true
                from = 0L
            }
        }
    }
}