package rat.poison.utils

import rat.poison.curSettings
import rat.poison.ui.KeybindType
import rat.poison.ui.keybindType
import rat.poison.utils.common.ObservableBoolean

var keyEvalMap = mutableMapOf<String, Triple<KeybindType, ObservableBoolean, Boolean>>() //////////// <VARIABLE_NAME, <KeybindType, ObservableBoolean, Toggled>

//ong we forgotein smthn
fun keybindRegister(varName: String, keyCode: Int) {
    val boolean = ObservableBoolean({ keyPressed(keyCode) })
    boolean.update()

    keyEvalMap[varName] = Triple(curSettings["${varName}_TYPE"].keybindType(), boolean, false)
}

fun keybindEval(varName: String): Boolean {
    val keyCode = curSettings.int[varName]

    if (!keyEvalMap.containsKey(varName)) {
        keybindRegister(varName, keyCode)
    }

    var map = keyEvalMap[varName]!!

    return when (map.first) {
        KeybindType.ON_HOTKEY -> {
            val toggleBool = keyPressed(keyCode)

            if (map.third != toggleBool) {
                map = map.copy(third = toggleBool)
                keyEvalMap[varName] = map
            }

            toggleBool
        }

        KeybindType.OFF_HOTKEY -> {
            val toggleBool = !keyPressed(keyCode)

            if (map.third != toggleBool) {
                map = map.copy(third = toggleBool)
                keyEvalMap[varName] = map
            }

            toggleBool
        }

        KeybindType.TOGGLE -> {
            val boolean = map.second
            boolean.update()

            var toggleBool = map.third

            if (boolean.justBecameTrue) {
                toggleBool = !toggleBool

                map = map.copy(third = toggleBool)
                keyEvalMap[varName] = map
            }

            toggleBool
        }

        KeybindType.ALWAYS_ON -> {
            if (!map.third) {
                map = map.copy(third = true)
                keyEvalMap[varName] = map
            }

            true
        }
    }
}