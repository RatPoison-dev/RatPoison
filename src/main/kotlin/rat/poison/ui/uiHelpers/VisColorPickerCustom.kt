package rat.poison.ui.uiHelpers

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.color.ColorPicker
import com.kotcrab.vis.ui.widget.color.ColorPickerAdapter
import rat.poison.App
import rat.poison.curSettings
import rat.poison.strToColor
import rat.poison.ui.changed
import rat.poison.game.Color as rColor

class VisColorPickerCustom(mainText: String, varName: String) : VisTable() {
    private val labelText = mainText
    private val variableName = varName

    private val pickerButton = VisTextButton(labelText)

    private var colorPicker : ColorPicker

    init {
        update()

        colorPicker = ColorPicker(labelText, object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                curSettings[variableName] = rColor((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble()).toString()
                newCol.a = 1F
                color = newCol
                update()
            }
        })

        pickerButton.changed { _, _ ->
            App.menuStage.addActor(colorPicker.fadeIn())
        }

        this.add(pickerButton).growX()
    }

    fun update() {
        val col = curSettings[variableName].strToColor()
        pickerButton.setColor(col.red/255F, col.green/255F, col.blue/255F, 1F)
    }

    fun disable(bool: Boolean) {
        pickerButton.isDisabled = bool
    }
}