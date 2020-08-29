package rat.poison.ui.uiHelpers.overrideWeaponsUI

import com.kotcrab.vis.ui.widget.VisCheckBox
import rat.poison.curLocale
import rat.poison.oWeapon
import rat.poison.ui.changed
import rat.poison.ui.uiPanelTables.weaponOverrideSelected
import rat.poison.utils.generalUtil.strToBool

class OverrideVisCheckBoxCustom(mainText: String, varName: String) : VisCheckBox(mainText) {
    private val variableName = varName
    private val defaultText = mainText
    private val varIdx = getOverrideVarIndex(oWeapon().toString(), variableName)

    init {
        update()
        changed { _, _ ->
            setOverrideVar(weaponOverrideSelected, varIdx, isChecked)

            true
        }
    }

    fun update() {
        val tmpText = curLocale[defaultText]
        setText(if (tmpText.isBlank()) defaultText else tmpText )
        isChecked = getOverrideVar(weaponOverrideSelected, varIdx).strToBool()
    }

    fun disable(bool: Boolean) {
        isDisabled = bool
    }
}