package rat.poison.ui.uiElements

import com.kotcrab.vis.ui.widget.Tooltip.removeTooltip
import com.kotcrab.vis.ui.widget.VisCheckBox
import rat.poison.curSettings
import rat.poison.scripts.visuals.disableAllEsp
import rat.poison.ui.changed
import rat.poison.ui.uiTabs.*
import rat.poison.utils.generalUtil.boolToStr

class VisCheckBoxCustom(mainText: String, varName: String, visibleText: Boolean = true) : VisCheckBox(mainText) {
    private val variableName = varName
    private val showText = visibleText
    private val labelText = mainText
    private var hasTooltip = false

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
            disableAllEsp() //Nothing bad could come from this....??
            true
        }
    }

    fun update() {
        setText(labelText)

        isChecked = curSettings.bool[variableName]

        updateTooltip()
    }

    private fun updateTooltip() {
        if (curSettings.bool["MENU_TOOLTIPS"]) {
            //TODO tooltip dickin
        } else {
            if (hasTooltip) {
                removeTooltip(this)
                hasTooltip = false
            }
        }
    }

    fun disable(bool: Boolean) {
        isDisabled = bool
    }
}