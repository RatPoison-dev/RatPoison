package rat.poison.utils

import rat.poison.curSettings
import rat.poison.ui.KeybindType
import rat.poison.utils.common.ListBasedMap
import rat.poison.utils.common.ObservableBoolean
import rat.poison.utils.common.keyPressed

data class MutableTripple<KeybindType, ObservableBoolean, Boolean>(var first: KeybindType, var second: ObservableBoolean, var third: Boolean)
var keyEvalMap = ListBasedMap<String, MutableTripple<KeybindType, ObservableBoolean, Boolean>>() //////////// <VARIABLE_NAME, <KeybindType, ObservableBoolean, Toggled>

//ong we forgotein smthn
fun keybindRegister(varName: String, keyCode: Int) {
    val boolean = ObservableBoolean({ keyPressed(keyCode) })

    keyEvalMap[varName] = MutableTripple(KeybindType[curSettings["${varName}_TYPE"]], boolean, false)
}

fun keybindEval(varName: String): Boolean {
    val keyCode = curSettings.int[varName]

    if (!keyEvalMap.containsKey(varName)) {
        keybindRegister(varName, keyCode)
    }

    val map = keyEvalMap[varName] ?: return false

    return when (map.first) {
        KeybindType.ON_HOTKEY -> {
            val toggleBool = keyPressed(keyCode)

            if (map.third != toggleBool) {
                map.third = toggleBool
            }

            toggleBool
        }

        KeybindType.OFF_HOTKEY -> {
            val toggleBool = !keyPressed(keyCode)

            if (map.third != toggleBool) {
                map.third = toggleBool
            }

            toggleBool
        }

        KeybindType.TOGGLE -> {
            val boolean = map.second
            boolean.update()

            var toggleBool = map.third

            if (boolean.justBecameTrue) {
                toggleBool = !toggleBool

                map.third = toggleBool
            }

            toggleBool
        }

        KeybindType.ALWAYS_ON -> {
            if (!map.third) {
                map.third = true
            }

            true
        }
    }
}