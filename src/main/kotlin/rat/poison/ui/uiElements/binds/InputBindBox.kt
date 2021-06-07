package rat.poison.ui.uiElements.binds

import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import rat.poison.curSettings
import rat.poison.overlay.App.keyProcessor
import rat.poison.ui.*
import rat.poison.ui.uiElements.VisSelectBoxCustom
import rat.poison.utils.keybindRegister
import rat.poison.utils.common.vkKeycodeToString

private val keybindTypesArray = arrayOf("ON_HOTKEY", "OFF_HOTKEY", "TOGGLE", "ALWAYS_ON")

class InputBindBox(mainText: String, varName: String): VisTextButton("_") {
    private val textLabel = mainText
    private val variableName = varName
    private val keyListener = KeyProcessorListener()

    var contextMenuOpen = false
    private lateinit var contextMenu: ContextMenu

    init {
        initContextTable()

        keyProcessor.listener = keyListener
        addListener(RightClickListener { onRightClick() })

        update()
        changed { _, _ ->
            setText("_")
            needKeyPressVar = varName
            needKeyPressActor = this
            keyProcessor.needKeyPress = true

            true
        }
    }

    fun update() {
        setText(vkKeycodeToString(curSettings.int[variableName]))
    }

    private fun onRightClick() {
        contextMenuOpen = contextMenu.open()
    }

    private fun initContextTable() {
        val contextTable = VisTable(false)

        val keybindTypeSelectBox = VisSelectBoxCustom("Key Type", "${variableName}_TYPE", false, textWidth = 75F, boxWidth = 75F, items = keybindTypesArray)

        keybindTypeSelectBox.changed { _, _ ->
            curSettings["${variableName}_TYPE"] = keybindTypesArray[keybindTypeSelectBox.selectBox.selectedIndex]
            keybindRegister(variableName, curSettings.int[variableName], variableName)
        }

        contextTable.apply {
            add(keybindTypeSelectBox).left()
        }

        contextMenu = ContextMenu(this, contextTable, 150F, 150F, textLabel)
    }
}