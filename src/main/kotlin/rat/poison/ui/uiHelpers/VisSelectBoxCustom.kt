package rat.poison.ui.uiHelpers

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.widget.*
import rat.poison.CURRENT_LOCALE
import rat.poison.curLocale
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.ui.changed
import rat.poison.ui.tabs.*

//Swap VisSelectBoxCustom to showText false is mainText is " "
class VisSelectBoxCustom(mainText: String, varName: String, useCategory: Boolean, private val showText: Boolean = true, vararg items: String) : VisTable() {
    private val textLabel = mainText
    private val variableName = varName
    private val useGunCategory = useCategory

    private var boxLabel = VisLabel("$textLabel:")
    private val selectBox = VisSelectBox<String>()

    private val boxItems = items

    var value = 0

    init {
        update()

        val itemsArray = Array<String>()
        for (i in boxItems) {
            itemsArray.add(i)
        }
        selectBox.items = itemsArray

        selectBox.changed { _, _ ->
            //This uses the stored box items to set settings appropriately without fucking with enums/switches for the locale
            curSettings[if (useGunCategory) { categorySelected + variableName } else { variableName }] = boxItems[selectBox.selectedIndex]
            false
        }

        if (showText) {
            add(boxLabel).width(200F)
        }

        add(selectBox).spaceRight(6F)
    }

    fun update() {
        if (CURRENT_LOCALE != "") { //Only update locale if we have one
            //Update the box label text
            if (dbg && curLocale[variableName].isBlank()) {
                println("[DEBUG] $CURRENT_LOCALE $variableName is missing!")
            }

            if (showText) {
                boxLabel.setText("${curLocale[variableName]}:")
            }

            //Update the items inside the box with locale items
            val itemsArray = Array<String>()
            for (i in boxItems) {
                if (dbg && curLocale[i].isBlank()) {
                    println("[DEBUG] $CURRENT_LOCALE $i is missing!")
                }

                itemsArray.add(curLocale[i])
            }
            selectBox.items = itemsArray
            selectBox.selectedIndex = boxItems.indexOf(curSettings[if (useGunCategory) { categorySelected + variableName } else { variableName }].toUpperCase())
        } else {
            selectBox.selected = curSettings[if (useGunCategory) { categorySelected + variableName } else { variableName }]
        }
    }

    fun disable(bool: Boolean, col: Color) {
        boxLabel.color = col
        selectBox.color = col
        selectBox.isDisabled = bool
    }
}