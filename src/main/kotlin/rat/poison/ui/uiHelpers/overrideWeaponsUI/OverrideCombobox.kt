package rat.poison.ui.uiHelpers.overrideWeaponsUI

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.*
import rat.poison.ui.changed
import rat.poison.ui.tabs.aimtabs.weaponOverrideSelected
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.stringToList

class OverrideCombobox(mainText: String, varName: String, private val showText: Boolean = true, vararg items: String): VisTable() {
    private val textLabel = mainText
    private val variableName = varName
    private var hasTooltip = false
    private val varIdx = getOverrideVarIndex(DEFAULT_OWEAPON_STR, variableName)

    private var boxLabel = VisLabel("$textLabel:")
    private val selectBox = VisSelectBox<String>()

    private val boxItems = items
    private var selectedList = mutableListOf<String>()

    init {
        update()

        selectBox.changed { _, _ ->
            val selected = selectBox.selected
            val includes = selected in selectedList
            val inList = selected in boxItems
            if (includes && inList) {
                selectedList.remove(selected)
            }
            else if (!includes && inList) {
                selectedList.add(selected)
            }
            else {
                return@changed true
            }

            writeToSettings()
            updateItems()

            true
        }
        if (showText) {
            add(boxLabel).width(200F)
        }

        add(selectBox).spaceRight(6F)
    }

    fun update() {
        if (curSettings["CURRENT_LOCALE"] != "" && showText) { //Only update locale if we have one
            if (curLocale[variableName].isBlank()) { //Variable is missing in locale
                if (dbg) println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $variableName is missing!")
                boxLabel.setText("$textLabel:")
            }
            else {
                boxLabel.setText("${curLocale[variableName]}:")
            }
        }
        updateTooltip()
        updateItems()
    }

    private fun updateTooltip() {
        if (curSettings["MENU_TOOLTIPS"].strToBool()) {
            if (curLocale["${variableName}_TOOLTIP"] != "") {
                if (!hasTooltip) {
                    Tooltip.Builder(curLocale["${variableName}_TOOLTIP"]).target(this).build()
                    hasTooltip = true
                    if (dbg) println("[DEBUG] Added tooltip to $variableName")
                }
            }
        } else {
            if (hasTooltip) {
                Tooltip.removeTooltip(this)
                hasTooltip = false
            }
        }
    }

    private fun updateItems() {
        val itemsArray = Array<String>()
        val myItems = getBoxItems()
        itemsArray.add(myItems[0])
        for (i in myItems.subList(1, myItems.lastIndex+1)) {
            if (curLocale[i].isBlank()) {
                itemsArray.add(i)
            }
            else {
                itemsArray.add(curLocale[i])
            }
        }
        selectBox.items = itemsArray
        selectedList = selectedToMutableList()
        selectBox.selectedIndex = 0
    }

    private fun writeToSettings() {
        setOverrideVar(weaponOverrideSelected, varIdx, selectedToString())
    }

    private fun getBoxString(): String {
        var myList = getOverrideVar(weaponOverrideSelected, varIdx).toString().replace(";", "; ").stringToList(";")

        return when (myList.isNotEmpty()) {
            true -> myList.joinToString(", ", ">")
            false -> "EMPTY"
        }
    }

    private fun selectedToString(): String {
        return when (selectedList.size == 1 && selectedList[0] == "EMPTY") {
            false -> selectedList.joinToString("; ", prefix = "[", postfix = "]")
            true -> "[]"
        }
    }

    private fun selectedToMutableList(): MutableList<String> {
        return when (selectBox.items[0] == "EMPTY") {
            false -> selectBox.items[0].substring(1).stringToList().toMutableList()
            true -> mutableListOf()
        }
    }

    private fun getBoxItems(): List<String> {
        val myString = getBoxString()
        return listOf(myString) + boxItems.toList()
    }

    fun disable(bool: Boolean, col: Color) {
        boxLabel.color = col
        selectBox.color = col
        selectBox.isDisabled = bool
    }
}