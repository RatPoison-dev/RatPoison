package rat.poison.ui.uiHelpers.aimTab

import com.kotcrab.vis.ui.widget.VisCheckBox
import rat.poison.boolToStr
import rat.poison.curSettings
import rat.poison.strToBool
import rat.poison.ui.changed
import rat.poison.ui.tabs.categorySelected
import rat.poison.ui.tabs.updateDisableAim
import rat.poison.ui.uiUpdate

class ATabVisCheckBox(mainText: String, varExtension: String) : VisCheckBox(mainText) {
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
    }

    fun disable(bool: Boolean) {
        isDisabled = bool
    }
}