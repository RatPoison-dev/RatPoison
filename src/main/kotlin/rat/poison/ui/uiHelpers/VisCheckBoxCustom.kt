package rat.poison.ui.uiHelpers

import com.kotcrab.vis.ui.widget.VisCheckBox
import rat.poison.CURRENT_LOCALE
import rat.poison.curLocale
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.ui.changed
import rat.poison.ui.tabs.*
import rat.poison.utils.generalUtil.boolToStr
import rat.poison.utils.generalUtil.strToBool

class VisCheckBoxCustom(mainText: String, varName: String, showText: Boolean = true) : VisCheckBox(mainText) {
    private val variableName = varName
    private val showText = showText

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
            true
        }
    }

    fun update() {
        if (CURRENT_LOCALE != "" && showText) { //Only update locale if we have one
            if (dbg && curLocale[variableName].isBlank()) {
                println("[DEBUG] $CURRENT_LOCALE $variableName is missing!")
            }
            setText(curLocale[variableName])
        }

        isChecked = curSettings[variableName].strToBool()
    }

    fun disable(bool: Boolean) {
        isDisabled = bool
    }
}