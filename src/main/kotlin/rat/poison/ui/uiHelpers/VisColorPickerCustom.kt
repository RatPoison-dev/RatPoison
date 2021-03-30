package rat.poison.ui.uiHelpers

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.VisImageButton
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.color.ColorPicker
import com.kotcrab.vis.ui.widget.color.ColorPickerAdapter
import rat.poison.curSettings
import rat.poison.overlay.App
import rat.poison.ui.changed
import rat.poison.game.Color as rColor

class VisColorPickerCustom(mainText: String, varName: String) : VisTable(false) {
    private val labelText = mainText
    private val variableName = varName

    private val pickerImage = VisTextButton("")

    private var colorPicker : ColorPicker

    init {
        colorPicker = ColorPicker(variableName, object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                curSettings[variableName] = rColor((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble()).toString()
                newCol.a = 1F
                color = newCol
                update()
            }
        })

        pickerImage.changed { _, _ ->
            App.menuStage.addActor(colorPicker.fadeIn())
        }

        add(pickerImage).width(25F).height(16F).left()

        update()
    }

    fun update() {
        val col = curSettings.colorGDX[variableName]
        pickerImage.color = col
        colorPicker.color = col


        //pickerButton.setText(labelText)
        colorPicker.titleLabel.setText(labelText)
    }

    fun disable(bool: Boolean) {
        pickerImage.isDisabled = bool
    }
}