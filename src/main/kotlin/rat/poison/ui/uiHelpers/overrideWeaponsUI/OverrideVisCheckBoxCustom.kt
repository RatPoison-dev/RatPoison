package rat.poison.ui.uiHelpers.overrideWeaponsUI

import com.kotcrab.vis.ui.widget.VisCheckBox
import rat.poison.DEFAULT_OWEAPON_STR
import rat.poison.toLocale
import rat.poison.ui.changed
import rat.poison.ui.tabs.aimtabs.weaponOverrideSelected
import rat.poison.ui.tabs.overridenWeapons
import rat.poison.utils.generalUtil.strToBool

private val flatAimIdx = getOverrideVarIndex(DEFAULT_OWEAPON_STR, "tFlatAim")
private val pathAimIdx = getOverrideVarIndex(DEFAULT_OWEAPON_STR, "tPathAim")

class OverrideVisCheckBoxCustom(mainText: String, varName: String) : VisCheckBox(mainText) {
    private val variableName = varName
    private val defaultText = mainText
    private val varIdx = getOverrideVarIndex(DEFAULT_OWEAPON_STR, variableName)

    init {
        update()
        changed { _, _ ->
            if (varName == "tFlatAim" && isChecked) {
                setOverrideVar(weaponOverrideSelected, pathAimIdx, false)
                overridenWeapons.enablePathAim.isChecked = false
            } else if (varName == "tPathAim" && isChecked) {
                setOverrideVar(weaponOverrideSelected, flatAimIdx, false)
                overridenWeapons.enableFlatAim.isChecked = false
            }

            setOverrideVar(weaponOverrideSelected, varIdx, isChecked)

            true
        }
    }

    fun update() {
        val tmpText = variableName.toLocale()
        setText(if (tmpText.isBlank()) defaultText else tmpText )
        isChecked = getOverrideVar(weaponOverrideSelected, varIdx).strToBool()
    }

    fun disable(bool: Boolean) {
        isDisabled = bool
    }
}