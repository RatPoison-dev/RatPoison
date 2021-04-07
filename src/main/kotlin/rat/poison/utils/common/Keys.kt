package rat.poison.utils.common

import org.jire.arrowhead.keyPressed
import rat.poison.utils.duplicationsMap

fun keyPressed(virtualKeyCode: Int): Boolean {
    val keyMap = duplicationsMap[virtualKeyCode]
    keyMap?.forEach {
        if (keyPressed(it)) {
            return true
        }
    }
    return keyPressed(virtualKeyCode)
}