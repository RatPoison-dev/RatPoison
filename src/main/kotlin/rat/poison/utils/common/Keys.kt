package rat.poison.utils.common

import org.jire.arrowhead.keyPressed

fun keyPressed(virtualKeyCode: Int): Boolean {
    val keyMap = duplicationsMap[virtualKeyCode]
    keyMap?.forEach {
        if (keyPressed(it)) {
            return true
        }
    }
    return keyPressed(virtualKeyCode)
}