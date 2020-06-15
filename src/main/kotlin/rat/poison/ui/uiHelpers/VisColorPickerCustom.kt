package rat.poison.ui.uiHelpers

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.VisImageButton
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.color.ColorPicker
import com.kotcrab.vis.ui.widget.color.ColorPickerAdapter
import rat.poison.App
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.ui.changed
import rat.poison.utils.varUtil.strToColor
import rat.poison.game.Color as rColor

private val white = VisUI.getSkin().getDrawable("white")

class VisColorPickerCustom(mainText: String, varName: String, nameInLocalization: String = varName) : VisTable() {
    private val defaultText = mainText
    private val variableName = varName

    private val pickerButton = VisLabelCustom(defaultText, nameInLocalization)
    private val pickerImage = VisImageButton(white)

    private var colorPicker : ColorPicker
    private val localeName = nameInLocalization

    init {
        update()

        val tmpText = curLocalization[localeName]
        pickerButton.setText(if (tmpText.isBlank()) defaultText else tmpText )

        colorPicker = ColorPicker(curLocalization[nameInLocalization], object : ColorPickerAdapter() {
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
        this.pickerButton.update()
        val col = curSettings[variableName].strToColor()
        pickerImage.setColor(col.red/255F, col.green/255F, col.blue/255F, 1F)
    }
    fun updateTitle() {
        this.colorPicker.titleLabel.setText(curLocalization[this.localeName])
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