package rat.poison.ui.uiHelpers.binds

import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.*
import org.jetbrains.kotlin.cli.common.computeKotlinPaths
import rat.poison.App.uiBinds
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.ui.changed
import rat.poison.ui.tabs.*
import rat.poison.ui.uiHelpers.VisTextButtonCustom
import rat.poison.utils.varUtil.boolToStr
import rat.poison.utils.varUtil.strToBool

class BindsRelatedCheckBox(mainText: String, varName: String, nameInLocalization: String = varName, padLeft: Float = 175F, isFixedPad: Boolean = false) : VisTable() {
    private val defaultText = mainText
    private val variableName = varName
    private val localeName = nameInLocalization
    val checkBox = VisCheckBox(mainText)
    private val configureButton = VisTextButtonCustom(curLocalization["CONFIGURE"], "CONFIGURE")
    init {
        if (curLocalization[nameInLocalization+"_TOOLTIP"] != "") {
            Tooltip.Builder(curLocalization[nameInLocalization+"_TOOLTIP"]).target(this).build()
        }
        update()
        pack()
        add(checkBox).height(18F).left()
        if (!isFixedPad) {
            add(configureButton).height(22F).padLeft(padLeft - checkBox.width).padRight(10F)
        }
        else {
            add(configureButton).height(22F).padLeft(padLeft)
        }
        configureButton.changed { _, _ ->
            curSettings["BINDS"] = !curSettings["BINDS"].strToBool()
            uiBinds.setBindOption(varName, localeName)
            true
        }
        checkBox.changed { _, _ ->
            curSettings[variableName] = checkBox.isChecked.boolToStr()
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
        val tmpText = curLocalization[localeName]
        configureButton.update()
        this.checkBox.setText(if (tmpText.isBlank()) defaultText else tmpText )
        this.checkBox.isChecked = curSettings[variableName].strToBool()
    }

    fun disable(bool: Boolean) {
        this.checkBox.isDisabled = bool
    }
}