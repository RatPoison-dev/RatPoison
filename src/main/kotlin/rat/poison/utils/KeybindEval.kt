package rat.poison.utils

import rat.poison.curSettings
import rat.poison.ui.KeybindType
import rat.poison.utils.common.ObservableBoolean
import rat.poison.utils.common.keyPressed
import rat.poison.utils.maps.ListBasedMap

data class MutableQuad<KeybindType, ObservableBoolean, Boolean, Setting>(var first: KeybindType, var second: ObservableBoolean, var third: Boolean, var fourth: Setting)
var keyEvalMap = ListBasedMap<String, MutableQuad<KeybindType, ObservableBoolean, Boolean, String?>>() //////////// <VARIABLE_NAME, <KeybindType, ObservableBoolean, Toggled, Setting>

//ong we forgotein smthn
fun keybindRegister(varName: String, keyCode: Int, settingName: String?) {
    val boolean = ObservableBoolean({ keyPressed(keyCode) })

    keyEvalMap[varName] = MutableQuad(KeybindType[curSettings["${varName}_TYPE"]], boolean, false, settingName)
}

fun keybindEval(varName: String, settingName: String? = null): Boolean {
    val keyCode = curSettings.int[varName]

    if (!keyEvalMap.containsKey(varName)) {
        keybindRegister(varName, keyCode, settingName)
    }

    val map = keyEvalMap[varName] ?: return false

    if (settingName != null) {
        if (!curSettings.bool[settingName]) return false
    }

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