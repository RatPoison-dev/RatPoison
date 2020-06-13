package rat.poison.ui.uiHelpers.overrideWeaponsUI

import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisCheckBox
import rat.poison.*
import rat.poison.game.Weapons
import rat.poison.ui.changed
import rat.poison.ui.uiPanelTables.weaponOverrideSelected
import rat.poison.ui.uiPanels.overridenWeapons

class OverrideVisCheckBoxCustom(mainText: String, varName: String, nameInLocalization: String = varName) : VisCheckBox(mainText) {
    private val variableName = varName
    private val defaultText = mainText
    private val varIdx = getOverrideVarIndex(oWeapon().toString(), variableName)
    private val localeName = nameInLocalization
    init {
        if (curLocalization[nameInLocalization+"_TOOLTIP"] != "") {
            Tooltip.Builder(curLocalization[nameInLocalization+"_TOOLTIP"]).target(this).build()
        }
        update()
        changed { _, _ ->
            setOverrideVar(weaponOverrideSelected, varIdx, isChecked)

            true
        }
    }

    fun update() {
        val tmpText = curLocalization[localeName]
        this.setText(if (tmpText.isBlank()) defaultText else tmpText )
        isChecked = getOverrideVar(weaponOverrideSelected, varIdx).strToBool()
    }

    fun disable(bool: Boolean) {
        isDisabled = bool
    }
}