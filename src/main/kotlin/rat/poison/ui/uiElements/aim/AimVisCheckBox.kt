package rat.poison.ui.uiElements.aim

import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisCheckBox
import rat.poison.curSettings
import rat.poison.ui.changed
import rat.poison.ui.uiTabs.categorySelected
import rat.poison.ui.uiTabs.updateDisableAim
import rat.poison.ui.uiUpdate
import rat.poison.utils.generalUtil.boolToStr
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.locale

class AimVisCheckBox(text: String, varExtension: String) : VisCheckBox(text) {
    private val mainText = text
    private val variableExtension = varExtension
    private var hasTooltip = false

    init {
        update()

        updateTooltip()

        changed { _, _ ->
            curSettings[categorySelected + variableExtension] = isChecked.boolToStr()

            //Custom checks
            if (isChecked) {
                if (variableExtension == "_ENABLE_FLAT_AIM") {
                    curSettings[categorySelected + "_ENABLE_PATH_AIM"] = "false"
                } else if (variableExtension == "_ENABLE_PATH_AIM") {
                    curSettings[categorySelected + "_ENABLE_FLAT_AIM"] = "false"
                }
            }
            uiUpdate()
            updateDisableAim()
            true
        }
    }

    fun update() {
        val tmp = curSettings[categorySelected + variableExtension]

        if (tmp.isNotEmpty()) {
            isChecked = tmp.strToBool()
        } else {
            println("[Error] $categorySelected$variableExtension is empty")
        }

        setText("L$variableExtension".locale(mainText)) //TODO store in vars

        updateTooltip()

    }

    private fun updateTooltip() {
        if (curSettings.bool["MENU_TOOLTIPS"]) {
            //TODO tooltippipin
        } else {
            if (hasTooltip) {
                Tooltip.removeTooltip(this)
                hasTooltip = false
            }
        }
    }

    fun disable(bool: Boolean) {
        isDisabled = bool
    }
}