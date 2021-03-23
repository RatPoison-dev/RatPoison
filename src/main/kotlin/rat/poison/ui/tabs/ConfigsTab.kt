@file:Suppress("BlockingMethodInNonBlockingContext")

package rat.poison.ui.tabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.SETTINGS_DIRECTORY
import rat.poison.overlay.App.menuStage
import rat.poison.overlay.opened
import rat.poison.scripts.skinChanger
import rat.poison.ui.changed
import rat.poison.ui.uiPanels.configsTab
import rat.poison.utils.*
import java.awt.Desktop
import java.io.File

class ConfigsTab : Tab(false, false) {
    private val table = VisTable(true)
    private var cfgNameTextBox = VisTextField()
    private var skinCfgNameTextBox = VisTextField()

    private var configListAdapter = ListAdapter(ArrayList())
    private var configSelectionList = ListView(configListAdapter)

    private var skinChangerListAdapter = ListAdapter(ArrayList())
    private var skinChangerSelectionList = ListView(skinChangerListAdapter)

    init {
        configSelectionList.updatePolicy = ListView.UpdatePolicy.ON_DRAW
        skinChangerSelectionList.updatePolicy = ListView.UpdatePolicy.ON_DRAW

        configSelectionList.setItemClickListener { str ->
            if (!str.isNullOrEmpty()) {
                cfgNameTextBox.text = ""
            }
        }

        skinChangerSelectionList.setItemClickListener { str ->
            if (!str.isNullOrEmpty()) {
                skinCfgNameTextBox.text = ""
            }
        }

        //Create Save Button
        val saveCFGButton = VisTextButton("Save")
        saveCFGButton.changed { _, _ ->
            if (cfgNameTextBox.text.isNullOrEmpty()) { //Save using list selection
                val tmpSelection = configListAdapter.selection
                if (tmpSelection.size > 0) { //Validate
                    val tmpName = tmpSelection[0] //Selection is an array

                    Dialogs.showConfirmDialog(menuStage, "${"OVERWRITE_CONFIG"}\n \"$tmpName\"", "", arrayOf("CONFIRM", "CANCEL"), arrayOf(1, 2)) { i ->
                        if (i == 1) { //Confirm
                            saveCFG(tmpName)
                        }
                    }
                }
            } else { //Save using text box
                saveCFG(cfgNameTextBox.text)
            }

            //Wipe text box and list selection
            configListAdapter.selectionManager.deselectAll()
            cfgNameTextBox.text = ""
            updateCFGList()

            true
        }

        val saveSkinCfgButton = VisTextButton("Save")
        saveSkinCfgButton.changed { _, _ ->
            if (skinCfgNameTextBox.text.isNullOrEmpty()) { //Save using list selection
                val tmpSelection = skinChangerListAdapter.selection
                if (tmpSelection.size > 0) { //Validate
                    val tmpName = tmpSelection[0] //Selection is an array

                    Dialogs.showConfirmDialog(menuStage, "${"OVERWRITE_CONFIG"}\n \"$tmpName\"", "", arrayOf("CONFIRM", "CANCEL"), arrayOf(1, 2)) { i ->
                        if (i == 1) { //Confirm
                            saveSkinCFG(tmpName)
                        }
                    }
                }
            } else { //Save using text box
                saveSkinCFG(skinCfgNameTextBox.text)
            }

            //Wipe text box and list selection
            skinChangerListAdapter.selectionManager.deselectAll()
            skinCfgNameTextBox.text = ""
            updateSkinCfgList()
            true

        }
        val loadSkinCfgButton = VisTextButton("Load")

        val saveDefaultButton = VisTextButton("Save To Default")
        saveDefaultButton.changed { _, _ ->
            saveWindows()
            saveDefault()
            true
        }

        val deleteSkinCfgButton = VisTextButton("Delete")
        deleteSkinCfgButton.changed { _, _ ->
            val tmpSelection = skinChangerListAdapter.selection
            if (tmpSelection.size > 0) { //Validate
                val tmpName = tmpSelection[0] //Selection is an array

                deleteCFG(tmpName, true)
            }

            true
        }

        //Create Load Button
        val loadCFGButton = VisTextButton("Load")
        loadCFGButton.changed { _, _ ->
            if (cfgNameTextBox.text.isNullOrEmpty()) { //Save using list selection
                val tmpSelection = configListAdapter.selection
                if (tmpSelection.size > 0) { //Validate
                    val tmpName = configListAdapter.selection[0] //Selection is an array
                    loadCFG(tmpName)
                }
            } else {
                loadCFG(cfgNameTextBox.text)
            }

            true
        }

        loadSkinCfgButton.changed { _, _ ->
            if (skinCfgNameTextBox.text.isNullOrEmpty()) { //Save using list selection
                val tmpSelection = skinChangerListAdapter.selection
                if (tmpSelection.size > 0) { //Validate
                    val tmpName = skinChangerListAdapter.selection[0] //Selection is an array
                    loadSkinCFG(tmpName)
                }
            } else {
                loadSkinCFG(cfgNameTextBox.text)
            }

            true
        }

        //Create Delete Button
        val deleteButton = VisTextButton("Delete")
        deleteButton.changed { _, _ ->
            val tmpSelection = configListAdapter.selection
            if (tmpSelection.size > 0) { //Validate
                val tmpName = configListAdapter.selection[0] //Selection is an array

                deleteCFG(tmpName)
            }

            true
        }

        val openCfgFolder = VisTextButton("Open Configs Folder")
        openCfgFolder.changed {_, _ ->
            if (Desktop.isDesktopSupported()) {
                val desktop = Desktop.getDesktop()
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(File("$SETTINGS_DIRECTORY/CFGS"))
                }
            }
            true
        }

        //Config name text input box
        cfgNameTextBox.changed { _, _ ->
            configListAdapter.selectionManager.deselectAll()
        }

        //File Select Box
        updateCFGList()

        val containerTable1 = VisTable(false)
        with (containerTable1) {
            val sldTable = VisTable(false)
            sldTable.add(saveCFGButton).prefWidth(80F).growX()
            sldTable.add(loadCFGButton).prefWidth(80F).growX()
            sldTable.add(deleteButton).prefWidth(80F).growX().row()

            val label = VisLabel("Configs", Align.center)
            label.setColor(.85F, .5F, .05F, 1F)
            add(label).left().prefWidth(240F).growX().padBottom(10F).row()

            add(configSelectionList.mainTable).left().top().prefWidth(240F).prefHeight(120F).growX().row()

            add(cfgNameTextBox).left().prefWidth(240F).growX().row()

            add(sldTable).left().growX().row()

            add(saveDefaultButton).prefWidth(240F).growX().row()
            add(openCfgFolder).prefWidth(240F).growX().row()
        }

        val containerTable2 = VisTable(false)
        with (containerTable2) {
            val sldTable = VisTable(false)
            sldTable.add(saveSkinCfgButton).left().prefWidth(80f).growX()
            sldTable.add(loadSkinCfgButton).left().prefWidth(80f).growX()
            sldTable.add(deleteSkinCfgButton).left().prefWidth(80f).growX()

            val label = VisLabel("Skins", Align.center)
            label.setColor(.85F, .5F, .05F, 1F)
            add(label).left().prefWidth(240F).growX().padBottom(10F).row()

            add(skinChangerSelectionList.mainTable).left().top().prefWidth(240F).prefHeight(120F).growX().row()

            add(skinCfgNameTextBox).left().prefWidth(240F).growX().row()

            add(sldTable).left().growX().row()
        }

        table.add(containerTable1).top().growX()
        table.addSeparator(true)
        table.add(containerTable2).top().growX().row()
        table.addSeparator().colspan(3)
    }

    override fun getContentTable(): Table {
        return table
    }

    override fun getTabTitle(): String {
        return "Configs"
    }

    fun updateCFGList() {
        if (VisUI.isLoaded() && !saving && opened && !updatingRanks) {
            configListAdapter.clear()

            File("$SETTINGS_DIRECTORY\\CFGS").listFiles()?.forEach {
                if ((it.extension == "cfg")) {
                    val cfgName = it.name.replace(".cfg", "")
                    configListAdapter.add(cfgName)
                }
            }
        }
    }

    fun updateSkinCfgList() {
        if (VisUI.isLoaded() && !saving && opened && !updatingRanks) {
            skinChangerListAdapter.clear()

            File("$SETTINGS_DIRECTORY\\skinCFGS").listFiles()?.forEach {
                if ((it.extension == "cfg")) {
                    val cfgName = it.name.replace(".cfg", "")
                    skinChangerListAdapter.add(cfgName)
                }
            }
        }
    }
}

fun configsTabUpdate() {
    configsTab.apply {
        updateCFGList()
        updateSkinCfgList()
    }
}