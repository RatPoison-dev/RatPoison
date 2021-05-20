package rat.poison.ui.uiElements

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.DEFAULT_OWEAPON_STR
import rat.poison.curSettings
import rat.poison.ui.changed
import rat.poison.ui.getOverrideVar
import rat.poison.ui.getOverrideVarIndex
import rat.poison.ui.setOverrideVar
import rat.poison.ui.uiTabs.VisLabelCustom
import rat.poison.ui.uiTabs.aimTables.weaponOverrideSelected
import rat.poison.ui.uiTabs.categorySelected
import rat.poison.utils.generalUtil.stringToList
import rat.poison.utils.locale

abstract class VisComboBox(mainText: String, varName: String, showText: Boolean = true, textWidth: Float = 200F, boxWidth: Float = 100F, items: kotlin.Array<out String>): VisTable(false) {
    private val textLabel = mainText
    open val variableName = varName
    private var hasTooltip = false

    private var dropDownWidth = boxWidth

    private var boxLabel = VisLabelCustom("$textLabel:")
    private val selectBox = VisSelectBox<String>()

    private val boxItems = items

    var selectedItems = mutableListOf<Int>(8, 7, 6)

    init {
        initialize()
        update()

        selectBox.changed { _, _ ->
            val idx = selectBox.selectedIndex

            if (idx == 0) return@changed false

            if (selectedItems.contains(idx)) {
                selectedItems.remove(idx)
            } else {
                selectedItems.add(idx)
            }


            val strItems = mutableListOf<String>()
            for (i in selectedItems) {
                strItems.add(boxItems[i-1])
            }
            saveItems(strItems)
                    //curSettings[if (useGunCategory) { categorySelected + variableName } else { variableName }] = strItems
            updateList()

            false
        }

        if (showText) {
            add(boxLabel).width(textWidth)
        }

        add(selectBox).width(dropDownWidth)
    }

    abstract fun saveItems(items: MutableList<String>)
    abstract fun getItems(): List<String>
    abstract fun initialize()

    fun updateList() {
        val itemsArray = Array<String>()

        if (selectedItems.size > 0) {
            selectedItems.sort()

            var str = ""
            for (i in selectedItems) {
                str += boxItems[i - 1] + ", "
            }

            itemsArray.add(str)
        } else {
            itemsArray.add("NONE")
        }

        for (i in boxItems) {
            itemsArray.add("L_$i".locale(i))
        }
        selectBox.items = itemsArray
        selectBox.selectedIndex = 0
    }

    fun update() {
        //val curValue = curSettings[if (useGunCategory) { categorySelected + variableName } else { variableName }]
        val curValue = getItems()

        selectedItems.clear()
        curValue.forEach {
            selectedItems.add(boxItems.indexOf(it)+1)
        }

        updateList()
        updateTooltip()

        boxLabel.setText("L$variableName".locale(textLabel))
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

class VisComboBoxCustom(val mainText: String, val varName: String, showText: Boolean = true, textWidth: Float = 200F, boxWidth: Float = 100F, vararg items: String): VisComboBox(mainText, varName, showText, textWidth, boxWidth, items) {
    override fun saveItems(items: MutableList<String>) {
        curSettings[variableName] = items
    }

    override fun getItems(): List<String> {
        return curSettings[categorySelected + variableName].stringToList(",")
    }

    override fun initialize() {}

}

class VisAimComboBox(val mainText: String, val varName: String, showText: Boolean = true, textWidth: Float = 200F, boxWidth: Float = 100F, vararg items: String): VisComboBox(mainText, varName, showText, textWidth, boxWidth, items) {
    override fun saveItems(items: MutableList<String>) {
        curSettings[categorySelected + variableName] = items.joinToString(prefix = "[", separator = ";", postfix = "]")
    }

    override fun getItems(): List<String> {
        return curSettings[categorySelected + variableName].stringToList(",")
    }

    override fun initialize() {}
}

class OverrideComboBox(mainText: String, varName: String, showText: Boolean = true, textWidth: Float = 200F, boxWidth: Float = 100F, vararg items: String) : VisComboBox(mainText, varName, showText, textWidth, boxWidth, items) {
    var overrideIdx = 0

    override fun initialize() {
        this.overrideIdx = getOverrideVarIndex(DEFAULT_OWEAPON_STR, variableName)
    }

    override fun saveItems(items: MutableList<String>) {
        setOverrideVar(weaponOverrideSelected, overrideIdx, items.joinToString(prefix = "[", separator = ";", postfix = "]"))
    }

    override fun getItems(): List<String> {
        return getOverrideVar(weaponOverrideSelected, overrideIdx).stringToList(";")
    }

}