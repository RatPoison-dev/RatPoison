package rat.poison.ui

import rat.poison.game.Ranks

//TODO smoked out fofrgot how to do this lol
enum class KeybindType {
    ON_HOTKEY, OFF_HOTKEY, TOGGLE, ALWAYS_ON
}

fun String.keybindType(): KeybindType {
    return when {
        contains("ON_HOTKEY") -> {
            KeybindType.ON_HOTKEY
        }

        contains("OFF_HOTKEY") -> {
            KeybindType.OFF_HOTKEY
        }

        contains("TOGGLE") -> {
            KeybindType.TOGGLE
        }

        contains("ALWAYS_ON") -> {
            KeybindType.ALWAYS_ON
        }

        else -> {
            println("[DEBUG - Error Handling] -- Keybind Type $this invalid, using value [${KeybindType.ON_HOTKEY}]")
            KeybindType.ON_HOTKEY
        }
    }
}

//u getttin smocked soung bitch nigga
fun KeybindType.text(): String {
    return when (this) {
        KeybindType.ON_HOTKEY -> "[On Hotkey]"
        KeybindType.OFF_HOTKEY -> "[Off Hotkey]"
        KeybindType.TOGGLE -> "[Toggled]"
        else -> "[Always On]"
    }
}