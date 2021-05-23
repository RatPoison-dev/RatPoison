package rat.poison.ui.uiElements.override

import com.kotcrab.vis.ui.widget.VisCheckBox
import rat.poison.DEFAULT_OWEAPON_STR
import rat.poison.ui.changed
import rat.poison.ui.getOverrideVar
import rat.poison.ui.getOverrideVarIndex
import rat.poison.ui.setOverrideVar
import rat.poison.ui.uiTabs.aimTables.weaponOverrideSelected
import rat.poison.ui.uiTabs.overrideTable
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.locale

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
                overrideTable.enablePathAim.isChecked = false
            } else if (varName == "tPathAim" && isChecked) {
                setOverrideVar(weaponOverrideSelected, flatAimIdx, false)
                overrideTable.enableFlatAim.isChecked = false
            }

            setOverrideVar(weaponOverrideSelected, varIdx, isChecked)

            true
        }
    }

    fun update() {
        setText("L_$variableName".locale(defaultText))
        isChecked = getOverrideVar(weaponOverrideSelected, varIdx).strToBool()
    }

    fun disable(bool: Boolean) {
        isDisabled = bool
    }
}