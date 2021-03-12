package rat.poison.ui.uiHelpers.aimTab

import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisCheckBox
import rat.poison.curLocale
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.ui.changed
import rat.poison.ui.tabs.categorySelected
import rat.poison.ui.tabs.updateDisableAim
import rat.poison.ui.uiUpdate
import rat.poison.utils.generalUtil.boolToStr
import rat.poison.utils.generalUtil.strToBool

class ATabVisCheckBox(text: String, varExtension: String) : VisCheckBox(text) {
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

        if (curSettings["CURRENT_LOCALE"] != "") { //Only update locale if we have one
            if (curLocale[variableExtension].isBlank()) {
                if (dbg) println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $variableExtension is missing!")
                setText(mainText)
            }
            else {
                setText(curLocale[variableExtension])
            }
        }

        updateTooltip()

    }

    private fun updateTooltip() {
        if (curSettings.bool["MENU_TOOLTIPS"]) {
            if (curLocale["${variableExtension}_TOOLTIP"] != "") {
                if (!hasTooltip) {
                    Tooltip.Builder(curLocale["${variableExtension}_TOOLTIP"]).target(this).build()
                    hasTooltip = true
                    if (dbg) {
                        println("[DEBUG] Added tooltip to $variableExtension")
                    }
                }
            }
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