package rat.poison.ui.uiHelpers.binds

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisTextButton
import rat.poison.App.menuStage
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.ui.changed
import rat.poison.ui.uiPanels.addToCheck
import rat.poison.utils.varUtil.strToBool

class BindsRelatedButton(mainText: String, varName: String, nameInLocalization: String = "") : VisTextButton(mainText) {
    private val localeName = nameInLocalization
    private val defaultText = mainText
    private val onKey = BindsInputField("_KEY", varName)

    init {
        addToCheck(nameInLocalization, varName)
        // positions
        onKey.setPosition(curSettings["BINDS_X"].toFloat()+130f, curSettings["BINDS_Y"].toFloat()-40)

        update()
        if (curLocalization[nameInLocalization+"_TOOLTIP"] != "") {
            Tooltip.Builder(curLocalization[nameInLocalization+"_TOOLTIP"]).target(this).build()
        }
        addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, xPos: Float, yPos: Float, pointer: Int, button: Int): Boolean {
                if (button == 1) { //0 left click //1 right click
                    if (!curSettings["BINDS"].strToBool()) {
                        curSettings["BINDS"] = "true"
                        menuStage.addActor(onKey)
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
            update()
            true
        }
    }

    fun update() {
        val tmpText = curLocalization[localeName]
        this.setText(if (tmpText.isBlank()) defaultText else tmpText)
        this.setText(curLocalization[localeName])
    }

    fun disable(bool: Boolean) {
        isDisabled = bool
    }
}