package rat.poison.ui.uiElements

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.ui.changed
import rat.poison.ui.uiTabs.categorySelected
import rat.poison.ui.uiTabs.updateDisableRCrosshair
import rat.poison.utils.extensions.upper

//Swap VisSelectBoxCustom to showText false is mainText is " "
class VisSelectBoxCustom(mainText: String, varName: String, useCategory: Boolean, showText: Boolean = true, vararg items: String, textWidth: Float = 200F, boxWidth: Float = 100F): VisTable(false) {
    private val textLabel = mainText
    private val variableName = varName
    private val useGunCategory = useCategory
    private var hasTooltip = false

    private var dropDownWidth = boxWidth

    private var boxLabel = VisLabel("$textLabel:")
    private val selectBox = VisSelectBox<String>()

    private val boxItems = items

    var selected = ""

    init {
        val itemsArray = Array<String>()
        for (i in boxItems) {
            itemsArray.add(i)
        }

        selectBox.items = itemsArray
        selected = selectBox.selected
        update()
        updateTooltip()

        selectBox.changed { _, _ ->
            curSettings[if (useGunCategory) { categorySelected + variableName } else { variableName }] = boxItems[selectBox.selectedIndex]
            selected = selectBox.selected

            updateDisableRCrosshair()

            false
        }

        if (showText) {
            add(boxLabel).width(textWidth)
        }

        add(selectBox).width(dropDownWidth)
    }

    fun update() {
        val setting = if (useGunCategory) { categorySelected + variableName } else { variableName }

        try {
            selectBox.selectedIndex = boxItems.indexOf(curSettings[setting].upper())
        } catch (e: Exception) {
            selectBox.selectedIndex = 0

            if (dbg) {
                println("[DEBUG - Error Handling] -- $setting invalid, setting value to [${selectBox.selected}]")
            }
        }

        selected = selectBox.selected
        updateTooltip()
    }

    private fun updateTooltip() {
        if (curSettings.bool["MENU_TOOLTIPS"]) {
            //TODO tooltips
        } else {
            if (hasTooltip) {
                Tooltip.removeTooltip(this)
                hasTooltip = false
            }
        }
    }

    fun disable(bool: Boolean, col: Color) {
        boxLabel.color = col
        selectBox.color = col
        selectBox.isDisabled = bool
    }
}