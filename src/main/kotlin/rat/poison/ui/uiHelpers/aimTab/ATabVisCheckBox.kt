package rat.poison.ui.uiHelpers.aimTab

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

    init {
        update()
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
            if (dbg && curLocale[variableExtension].isBlank()) {
                println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $variableExtension is missing!")
            }
            setText(curLocale[variableExtension])
        }
    }

    fun disable(bool: Boolean) {
        isDisabled = bool
    }
}