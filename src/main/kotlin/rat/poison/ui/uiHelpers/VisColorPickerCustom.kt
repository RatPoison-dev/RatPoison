package rat.poison.ui.uiHelpers

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.color.ColorPicker
import com.kotcrab.vis.ui.widget.color.ColorPickerAdapter
import rat.poison.App
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.strToColor
import rat.poison.ui.changed
import rat.poison.game.Color as rColor

private val white = VisUI.getSkin().getDrawable("white")

class VisColorPickerCustom(mainText: String, varName: String, nameInLocalization: String = "") : VisTable() {
    private val labelText = mainText
    private val variableName = varName

    private val pickerButton = VisLabel(labelText)
    private val pickerImage = VisImageButton(white)

    private var colorPicker : ColorPicker
    private val nameInLocalization = nameInLocalization

    init {
        update()

        colorPicker = ColorPicker(labelText, object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                curSettings[variableName] = rColor((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), 1.0).toString()
                newCol.a = 1F
                color = newCol
                update()
            }
        })

        pickerImage.changed { _, _ ->
            App.menuStage.addActor(colorPicker.fadeIn())
        }

        this.add(pickerButton).growX()
        this.add(pickerImage).size(24f).pad(3f)
    }

    fun update() {
        this.pickerButton.setText(curLocalization[this.nameInLocalization])
        val col = curSettings[variableName].strToColor()
        pickerImage.setColor(col.red/255F, col.green/255F, col.blue/255F, 1F)
    }

    fun disable(bool: Boolean) {
        val col = if (bool) {
            Color(105F, 105F, 105F, .2F)
        } else {
            Color(255F, 255F, 255F, 1F)
        }

        pickerButton.color = col
        pickerImage.isDisabled = bool
    }
}