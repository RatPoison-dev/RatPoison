package rat.poison.ui.uiHelpers

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.VisImageButton
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.color.ColorPicker
import com.kotcrab.vis.ui.widget.color.ColorPickerAdapter
import rat.poison.curLocale
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.overlay.App
import rat.poison.toLocale
import rat.poison.ui.changed
import rat.poison.utils.generalUtil.strToColor
import rat.poison.game.Color as rColor

private val white = VisUI.getSkin().getDrawable("white")

class VisColorPickerCustom(mainText: String, varName: String) : VisTable() {
    private val labelText = mainText
    private val variableName = varName

    private val pickerButton = VisLabel(labelText)
    private val pickerImage = VisImageButton(white)

    private var colorPicker : ColorPicker

    init {
        colorPicker = ColorPicker(variableName.toLocale(), object : ColorPickerAdapter() {
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

        update()
    }

    fun update() {
        val col = curSettings[variableName].strToColor()
        pickerImage.setColor(col.red/255F, col.green/255F, col.blue/255F, 1F)

        if (curSettings["CURRENT_LOCALE"] != "") { //Only update locale if we have one
            if (dbg && curLocale[variableName].isBlank()) {
                println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $variableName is missing!")
            }
            pickerButton.setText(curLocale[variableName])
            colorPicker.titleLabel.setText(variableName.toLocale())
        }
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