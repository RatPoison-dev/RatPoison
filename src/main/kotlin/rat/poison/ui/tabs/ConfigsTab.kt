@file:Suppress("BlockingMethodInNonBlockingContext")

package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.SETTINGS_DIRECTORY
import rat.poison.curSettings
import rat.poison.overlay.App.menuStage
import rat.poison.overlay.opened
import rat.poison.toLocale
import rat.poison.ui.changed
import rat.poison.ui.refreshMenu
import rat.poison.ui.uiPanels.configsTab
import rat.poison.ui.uiUpdate
import rat.poison.utils.*
import rat.poison.utils.generalUtil.loadLocale
import java.awt.Desktop
import java.io.File

class ConfigsTab : Tab(false, false) {
    private val table = VisTable(true)
    private var cfgNameTextBox = VisTextField()

    private var configLabel = VisLabel("Configs".toLocale(), Align.center)
    private var localeLabel = VisLabel("Locale".toLocale(), Align.center)

    private var configListAdapter = ListAdapter(ArrayList())
    private var configSelectionList = ListView(configListAdapter)

    private var localeListAdapter = ListAdapter(ArrayList())
    private var localeSelectionList = ListView(localeListAdapter)
    private var loadLocaleButton = VisTextButton("Load-Locale".toLocale())

    init {
        configSelectionList.updatePolicy = ListView.UpdatePolicy.ON_DRAW
        localeSelectionList.updatePolicy = ListView.UpdatePolicy.ON_DRAW

        configSelectionList.setItemClickListener { str ->
            if (!str.isNullOrEmpty()) {
                cfgNameTextBox.text = ""
            }
        }

        loadLocaleButton.changed { _, _ ->
            val tmpSelection = localeListAdapter.selection
            if (tmpSelection.size > 0) { //Validate
                val tmpName = localeListAdapter.selection[0] //Selection is an array

                curSettings["CURRENT_LOCALE"] = tmpName
                loadLocale("$SETTINGS_DIRECTORY\\Localizations\\${curSettings["CURRENT_LOCALE"]}.locale")

                refreshMenu()

                uiUpdate()
            }

            true
        }

        //Create Save Button
        val saveCFGButton = VisTextButton("Save".toLocale())
        saveCFGButton.changed { _, _ ->
            if (cfgNameTextBox.text.isNullOrEmpty()) { //Save using list selection
                val tmpSelection = configListAdapter.selection
                if (tmpSelection.size > 0) { //Validate
                    val tmpName = tmpSelection[0] //Selection is an array

                    Dialogs.showConfirmDialog(menuStage, "${"OVERWRITE_CONFIG".toLocale()}\n \"$tmpName\"", "", arrayOf("CONFIRM".toLocale(), "CANCEL".toLocale()), arrayOf(1, 2)) { i ->
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

        val saveDefaultButton = VisTextButton("SAVE_DEFAULT".toLocale())
        saveDefaultButton.changed { _, _ ->
            saveWindows()
            saveDefault()
            true
        }

        //Create Load Button
        val loadCFGButton = VisTextButton("Load".toLocale())
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

        //Create Delete Button
        val deleteButton = VisTextButton("Delete".toLocale())
        deleteButton.changed { _, _ ->
            val tmpSelection = configListAdapter.selection
            if (tmpSelection.size > 0) { //Validate
                val tmpName = configListAdapter.selection[0] //Selection is an array

                deleteCFG(tmpName)
            }

            true
        }

        val openCfgFolder = VisTextButton("Open configs folder")
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
        updateLocaleList()

        //Add everything to table
        val sldTable = VisTable()
        sldTable.add(saveCFGButton).width(80F)
        sldTable.add(loadCFGButton).width(80F)
        sldTable.add(deleteButton).width(80F).row()

        table.add(configLabel).left().width(240F)
        table.add(localeLabel).left().width(240F).row()

        table.add(configSelectionList.mainTable).left().top().width(240F).height(240F)
        table.add(localeSelectionList.mainTable).right().top().width(240F).height(240F).row()

        table.add(cfgNameTextBox).left().width(240F)
        table.add(loadLocaleButton).width(240F).row()

        table.add(sldTable).left().row()
        table.add(saveDefaultButton).width(240F).center().row()
        table.add(openCfgFolder).width(240F).center().row()
    }

    override fun getContentTable(): Table {
        return table
    }

    override fun getTabTitle(): String {
        return "Configs".toLocale()
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

    fun updateLocaleList() {
        if (VisUI.isLoaded() && !saving && !updatingRanks) {
            localeListAdapter.clear()

            File("$SETTINGS_DIRECTORY\\Localizations").listFiles()?.forEach {
                if (it.extension == "locale") {
                    val localeName = it.name.replace(".locale", "")
                    localeListAdapter.add(localeName)
                }
            }
        }
    }
}

fun configsTabUpdate() {
    configsTab.apply {
        updateCFGList()
        updateLocaleList()
    }
}