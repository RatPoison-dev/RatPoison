package rat.poison.ui.uiHelpers

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.curLocale
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.ui.changed
import rat.poison.ui.tabs.categorySelected
import rat.poison.ui.tabs.updateDisableRCrosshair
import rat.poison.utils.generalUtil.strToBool

//Swap VisSelectBoxCustom to showText false is mainText is " "
class VisSelectBoxCustom(mainText: String, varName: String, useCategory: Boolean, private val showText: Boolean = true, vararg items: String) : VisTable() {
    private val textLabel = mainText
    private val variableName = varName
    private val useGunCategory = useCategory
    private var hasTooltip = false

    private var boxLabel = VisLabel("$textLabel:")
    private val selectBox = VisSelectBox<String>()

    private val boxItems = items

    var value = 0

    init {
        //Update the items inside the box with locale items
        val itemsArray = Array<String>()
        for (i in boxItems) {
            if (dbg && curLocale[i].isBlank()) {
                println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $i is missing!")
            }

            itemsArray.add(curLocale[i])
        }

        selectBox.items = itemsArray
        update()
        updateTooltip()


        selectBox.changed { _, _ ->
            //This uses the stored box items to set settings appropriately without fucking with enums/switches for the locale
            curSettings[if (useGunCategory) { categorySelected + variableName } else { variableName }] = boxItems[selectBox.selectedIndex]

            updateDisableRCrosshair()

            false
        }

        if (showText) {
            add(boxLabel).width(200F)
        }

        add(selectBox).spaceRight(6F)
    }

    fun update() {
        if (curSettings["CURRENT_LOCALE"] != "") { //Only update locale if we have one
            if (dbg && curLocale[variableName].isBlank()) { //Variable is missing in locale
                println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $variableName is missing!")
            }

            if (showText) { //Update box label text
                boxLabel.setText("${curLocale[variableName]}:")
            }

            selectBox.selectedIndex = boxItems.indexOf(curSettings[if (useGunCategory) { categorySelected + variableName } else { variableName }].uppercase())
        } else {
            selectBox.selected = curSettings[if (useGunCategory) { categorySelected + variableName } else { variableName }]
        }

        updateTooltip()
    }

    private fun updateTooltip() {
        if (curSettings["MENU_TOOLTIPS"].strToBool()) {
            if (curLocale["${variableName}_TOOLTIP"] != "") {
                if (!hasTooltip) {
                    Tooltip.Builder(curLocale["${variableName}_TOOLTIP"]).target(this).build()
                    hasTooltip = true
                    if (dbg) {
                        println("[DEBUG] Added tooltip to $variableName")
                    }
                }
            }
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