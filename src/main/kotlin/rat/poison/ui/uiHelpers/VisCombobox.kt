package rat.poison.ui.uiHelpers

import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.curLocale
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.ui.changed
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.stringToList

//r.rat
class VisCombobox(mainText: String, varName: String, private val showText: Boolean = true, vararg items: String): VisTable() {
    private val textLabel = mainText
    private val variableName = varName
    private var hasTooltip = false

    private var boxLabel = VisLabel("$textLabel:")
    private val selectBox = VisSelectBox<String>()

    private val boxItems = items
    private var selectedList = mutableListOf<String>()
    private var skipNext = false
    init {
        update()
        selectBox.changed { _, _ ->

            val includes = selectBox.selected in selectedList
            if (((selectBox.selectedIndex != 0 || (selectBox.selectedIndex == 0 && (selectedList.size - 1 == 0)))) && includes && !skipNext) {
                selectedList.remove(selectBox.selected)
            }
            else if ((selectBox.selectedIndex != 0 || (selectBox.selectedIndex == 0 && selectedList.size - 1 == 0)) && !includes && !skipNext) {
                selectedList.add(selectBox.selected)
            }
            else {
                skipNext = false
                return@changed true
            }

            writeToSettings()
            updateItems()
            skipNext = true
            selectBox.selectedIndex = 0

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
    }

    private fun writeToSettings() {
        curSettings[variableName] = selectedToString()
    }

    private fun getBoxString(): String {
        val myList = curSettings[variableName].stringToList()
        return when (myList.isNotEmpty()) {
            true -> curSettings[variableName].stringToList().joinToString(", ")
            false -> "EMPTY"
        }
    }

    private fun selectedToString(): String {
        return when (selectedList.size == 1 && selectedList[0] == "EMPTY") {
            false -> selectedList.joinToString(", ", prefix = "[", postfix = "]")
            true -> "[]"
        }
    }

    private fun selectedToMutableList(): MutableList<String> {
        return when (selectBox.items[0] == "EMPTY") {
            false -> selectBox.items[0].stringToList().toMutableList()
            true -> mutableListOf()
        }
    }

    private fun getBoxItems(): List<String> {
        val myString = getBoxString()
        return listOf(myString) + boxItems.toList()
    }
}