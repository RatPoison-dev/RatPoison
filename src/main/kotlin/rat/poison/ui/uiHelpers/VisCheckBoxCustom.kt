package rat.poison.ui.uiHelpers

import com.kotcrab.vis.ui.widget.VisCheckBox
import rat.poison.curLocale
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.ui.changed
import rat.poison.ui.tabs.*
import rat.poison.utils.generalUtil.boolToStr
import rat.poison.utils.generalUtil.strToBool

class VisCheckBoxCustom(mainText: String, varName: String, visibleText: Boolean = true) : VisCheckBox(mainText) {
    private val variableName = varName
    private val showText = visibleText

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
            updateDisableTrig()
            updateDisableBacktrack()
            true
        }
    }

    fun update() {
        if (curSettings["CURRENT_LOCALE"] != "" && showText) { //Only update locale if we have one
            if (dbg && curLocale[variableName].isBlank()) {
                println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $variableName is missing!")
            }
            setText(curLocale[variableName])
        }

        isChecked = curSettings[variableName].strToBool()
    }

    fun disable(bool: Boolean) {
        isDisabled = bool
    }
}