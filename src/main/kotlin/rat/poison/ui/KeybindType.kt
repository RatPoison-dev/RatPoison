package rat.poison.ui

enum class KeybindType(val text: String, val prettyPrint: String = "") {
    ON_HOTKEY("ON_HOTKEY", prettyPrint = "[On Hotkey]"), OFF_HOTKEY("OFF_HOTKEY", "[Off Hotkey]"), TOGGLE("TOGGLE", "[Toggled]"), ALWAYS_ON("ALWAYS_ON", "[Always On]");
    companion object {
        private val cachedValues = values()
        operator fun get(str: String) =  cachedValues.firstOrNull { str.contains(it.text) } ?: ON_HOTKEY
    }
}