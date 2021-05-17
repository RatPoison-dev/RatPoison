package rat.poison.ui.uiElements.override

import rat.poison.DEFAULT_OWEAPON_STR
import rat.poison.ui.getOverrideVar
import rat.poison.ui.getOverrideVarIndex
import rat.poison.ui.setOverrideVar
import rat.poison.ui.uiElements.VisCombobox
import rat.poison.ui.uiTabs.aimTables.weaponOverrideSelected
import rat.poison.utils.generalUtil.stringToList

class OverrideCombobox(
    mainText: String,
    varName: String,
    showText: Boolean = true,
    textWidth: Float = 200F,
    boxWidth: Float = 100F,
    vararg items: String
) : VisCombobox(mainText, varName, showText, textWidth, boxWidth, items) {
    var overrideIdx = 0
    override fun initialize() {
        this.overrideIdx = getOverrideVarIndex(DEFAULT_OWEAPON_STR, variableName)
    }

    override fun saveItems(items: MutableList<String>) {
        setOverrideVar(weaponOverrideSelected, overrideIdx, items.joinToString(prefix = "[", separator = ";", postfix = "]"))
    }

    override fun getItems(): List<String> {
        return getOverrideVar(weaponOverrideSelected, overrideIdx).stringToList(";")
    }

}