package rat.poison.ui.uiHelpers

import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisCheckBox
import rat.poison.boolToStr
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.strToBool
import rat.poison.ui.changed
import rat.poison.ui.tabs.*

class VisCheckBoxCustom(mainText: String, varName: String, nameInLocalization: String = "") : VisCheckBox(mainText) {
    private val variableName = varName
    private val nameInLocalization = nameInLocalization
    init {
        if (curLocalization[nameInLocalization+"_TOOLTIP"] != "") {
            Tooltip.Builder(curLocalization[nameInLocalization+"_TOOLTIP"]).target(this).build()
        }
        update()
        changed { _, _ ->
            curSettings[variableName] = isChecked.boolToStr()
            //CheckBoxes are the only things to disable/enable other settings, call all updates on change
                //Move to update() ?
            updateDisableRCrosshair()
            updateDisableRcsSmoothing()
            updateDisableEsp()
            updateDisableAim()
            updateDisableTrig()
            true
        }
    }

    fun update() {
        this.setText(curLocalization[nameInLocalization])
        isChecked = curSettings[variableName].strToBool()
    }

    fun disable(bool: Boolean) {
        isDisabled = bool
    }
}