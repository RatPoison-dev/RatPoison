package rat.poison.ui.uiHelpers.binds

import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisCheckBox
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.ui.changed
import rat.poison.ui.tabs.*
import rat.poison.utils.varUtil.boolToStr
import rat.poison.utils.varUtil.strToBool

class BindsCheckBox(varName: String) : VisCheckBox(curLocalization["ON_KEY"]) {
    private val defaultText = curLocalization["ON_KEY"]
    var variableName = varName+"_ON_KEY"
    private val localeName = curLocalization["ON_KEY"]

    init {
        if (curLocalization[localeName+"_ON_KEY_TOOLTIP"] != "") {
            Tooltip.Builder(curLocalization[localeName+"_ON_KEY_TOOLTIP"]).target(this).build()
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
            updateDisableBacktrack()
            true
        }
    }

    fun update() {
        val tmpText = curLocalization["ON_KEY"]
        this.setText(if (tmpText.isBlank()) defaultText else tmpText )
        isChecked = curSettings[variableName].strToBool()
    }

    fun disable(bool: Boolean) {
        isDisabled = bool
    }
}