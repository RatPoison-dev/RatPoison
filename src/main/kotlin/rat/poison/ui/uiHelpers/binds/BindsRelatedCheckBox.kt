package rat.poison.ui.uiHelpers.binds
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisCheckBox
import com.kotcrab.vis.ui.widget.VisSelectBox
import rat.poison.App.menuStage
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.ui.changed
import rat.poison.ui.tabs.*
import rat.poison.ui.uiPanels.addToCheck
import rat.poison.utils.varUtil.boolToStr
import rat.poison.utils.varUtil.strToBool


class BindsRelatedCheckBox(mainText: String, varName: String, nameInLocalization: String = varName) : VisCheckBox(mainText) {
    private val defaultText = mainText
    private val variableName = varName
    private val localeName = nameInLocalization
    private val selectBox = VisSelectBox<String>()
    private val keyType = BindsInputField("_KEY", varName)
    init {

        addToCheck(nameInLocalization, varName)

        if (curLocalization[nameInLocalization+"_TOOLTIP"] != "") {
            Tooltip.Builder(curLocalization[nameInLocalization+"_TOOLTIP"]).target(this).build()
        }

        // positions
        selectBox.setPosition(curSettings["BINDS_X"].toFloat(), curSettings["BINDS_Y"].toFloat())
        keyType.setPosition(selectBox.x+130f, selectBox.y-40)

        // w&h
        selectBox.width = 200f

        // right click listener
        addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, xPos: Float, yPos: Float, pointer: Int, button: Int): Boolean {
                if (button == 1) { //0 left click //1 right click
                    if (!curSettings["BINDS"].strToBool()) {
                        curSettings["BINDS"] = "true"
                        menuStage.addActor(selectBox)
                        keyType.remove()
                        selectBox.setItems(curLocalization["ALWAYS_ON"], curLocalization["TOGGLE_KEY"], curLocalization["ON_HOTKEY"], curLocalization["OFF_HOTKEY"])
                        keyType.update()
                        when (curSettings[varName + "_KEY_TYPE"]) {
                            "OnKey" -> {
                                selectBox.selected = curLocalization["ON_HOTKEY"]
                                menuStage.addActor(keyType)
                            }
                            "OffKey" -> {
                                selectBox.selected = curLocalization["OFF_HOTKEY"]
                                menuStage.addActor(keyType)
                            }
                            "SwitchKey" -> {
                                selectBox.selected = curLocalization["TOGGLE_KEY"]
                                menuStage.addActor(keyType)
                            }
                            "AlwaysOn" -> {
                                selectBox.selected = curLocalization["ALWAYS_ON"]
                            }
                        }
                        selectBox.changed {_, _ ->
                            curSettings[varName+"_KEY_TYPE"] = when (selectBox.selected) {
                                curLocalization["ON_HOTKEY"] -> "OnKey"
                                curLocalization["OFF_HOTKEY"] -> "OffKey"
                                curLocalization["TOGGLE_KEY"] -> "SwitchKey"
                                else -> "AlwaysOn"
                            }
                            if (curSettings[varName + "_KEY_TYPE"] != "AlwaysOn") {
                                keyType.update()
                                if (menuStage.actors.contains(keyType)) {
                                    keyType.remove()
                                    menuStage.addActor(keyType)
                                }
                                menuStage.addActor(keyType)
                            }
                        }
                    }
                    else {
                        curSettings["BINDS"] = "false"
                        menuStage.clear()
                    }
                }

                return super.touchDown(event, xPos, yPos, pointer, button)
            }
        })
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

        val tmpText = curLocalization[localeName]
        this.setText(if (tmpText.isBlank()) defaultText else tmpText )
        this.isChecked = curSettings[variableName].strToBool()
    }

    fun disable(bool: Boolean) {
        this.isDisabled = bool
    }
}