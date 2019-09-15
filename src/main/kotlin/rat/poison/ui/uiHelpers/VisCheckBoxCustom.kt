package rat.poison.ui.uiHelpers

import com.kotcrab.vis.ui.widget.VisCheckBox
import rat.poison.boolToStr
import rat.poison.curSettings
import rat.poison.strToBool
import rat.poison.ui.changed
import rat.poison.ui.tabs.updateDisableAim
import rat.poison.ui.tabs.updateDisableEsp
import rat.poison.ui.tabs.updateDisableRCrosshair
import rat.poison.ui.tabs.updateDisableRcsSmoothing

class VisCheckBoxCustom(mainText: String, varName: String) : VisCheckBox(mainText) {
    private val variableName = varName

    init {
        update()
        changed { _, _ ->
            curSettings[variableName] = isChecked.boolToStr()
            //CheckBoxes are the only things to disable/enable other settings, call all updates on change
                //Move to update() ?
            updateDisableRCrosshair()
            updateDisableRcsSmoothing()
            updateDisableEsp()
            updateDisableAim()
            true
        }
    }

    fun update() {
        isChecked = curSettings[variableName].strToBool()
    }

    fun disable(bool: Boolean) {
        isDisabled = bool
    }
}