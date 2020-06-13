package rat.poison.ui.uiHelpers.overrideWeaponsUI

import com.kotcrab.vis.ui.widget.VisCheckBox
import rat.poison.*
import rat.poison.game.Weapons
import rat.poison.ui.changed
import rat.poison.ui.uiPanelTables.weaponOverrideSelected
import rat.poison.ui.uiPanels.overridenWeapons

class OverrideVisCheckBoxCustom(mainText: String, varName: String) : VisCheckBox(mainText) {
    private val variableName = varName
    private val varIdx = getOverrideVarIndex(oWeapon().toString(), variableName)

    init {
        update()
        changed { _, _ ->
            setOverrideVar(weaponOverrideSelected, varIdx, isChecked)

            true
        }
    }

    fun update() {
        isChecked = getOverrideVar(weaponOverrideSelected, varIdx).strToBool()
    }

    fun disable(bool: Boolean) {
        isDisabled = bool
    }
}