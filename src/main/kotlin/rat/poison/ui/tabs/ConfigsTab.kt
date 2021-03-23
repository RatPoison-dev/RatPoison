@file:Suppress("BlockingMethodInNonBlockingContext")

package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.google.gson.reflect.TypeToken
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.*
import rat.poison.overlay.App.menuStage
import rat.poison.overlay.opened
import rat.poison.ui.changed
import rat.poison.ui.refreshMenu
import rat.poison.ui.uiPanels.configsTab
import rat.poison.ui.uiUpdate
import rat.poison.utils.*
import rat.poison.utils.generalUtil.loadLocale
import java.awt.Desktop
import java.io.File

//ronfig giveaway
data class Config(
    val name: String,
    val tag1: String,
    val tag2: String,
    val size: Double
) {
    override fun toString(): String {
        return this.name
    }
}

private val configArrayType = object : TypeToken<Array<Config>>() {}.type
class ConfigsTab : Tab(false, false) {
    private val table = VisTable(true)
    private var cfgNameTextBox = VisTextField()
    private var skinCfgNameTextBox = VisTextField()

    private var configLabel = VisLabel("Configs".toLocale())
    private var localeLabel = VisLabel("Locale".toLocale(), Align.center)
    private var skinChangerLabel = VisLabel("Skins".toLocale(), Align.center)
    private var cloudConfigLabel = VisLabel("Cloud-Configs".toLocale(), Align.center)

    private var configListAdapter = ListAdapter(ArrayList())
    private var configSelectionList = ListView(configListAdapter)

    private var skinChangerListAdapter = ListAdapter(ArrayList())
    private var skinChangerSelectionList = ListView(skinChangerListAdapter)

    private var localeListAdapter = ListAdapter(ArrayList())
    private var localeSelectionList = ListView(localeListAdapter)
    private var loadLocaleButton = VisTextButton("Load-Locale".toLocale())

    private var cloudConfigListAdapter = ListAdapter(ArrayList())
    private var cloudConfigSelectionList = ListView(cloudConfigListAdapter)

    init {
        configSelectionList.updatePolicy = ListView.UpdatePolicy.ON_DRAW
        localeSelectionList.updatePolicy = ListView.UpdatePolicy.ON_DRAW
        skinChangerSelectionList.updatePolicy = ListView.UpdatePolicy.ON_DRAW
        cloudConfigSelectionList.updatePolicy = ListView.UpdatePolicy.ON_DRAW

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

        val saveSkinCfgButton = VisTextButton("Save".toLocale())
        saveSkinCfgButton.changed { _, _ ->
            if (skinCfgNameTextBox.text.isNullOrEmpty()) { //Save using list selection
                val tmpSelection = skinChangerListAdapter.selection
                if (tmpSelection.size > 0) { //Validate
                    val tmpName = tmpSelection[0] //Selection is an array

                    Dialogs.showConfirmDialog(menuStage, "${"OVERWRITE_CONFIG".toLocale()}\n \"$tmpName\"", "", arrayOf("CONFIRM".toLocale(), "CANCEL".toLocale()), arrayOf(1, 2)) { i ->
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
        val loadSkinCfgButton = VisTextButton("Load".toLocale())

        val saveDefaultButton = VisTextButton("SAVE_DEFAULT".toLocale())
        saveDefaultButton.changed { _, _ ->
            saveWindows()
            saveDefault()
            true
        }

        updateCloudConfigsList()

        val deleteSkinCfgButton = VisTextButton("Delete".toLocale())
        deleteSkinCfgButton.changed { _, _ ->
            val tmpSelection = skinChangerListAdapter.selection
            if (tmpSelection.size > 0) { //Validate
                val tmpName = tmpSelection[0] //Selection is an array

                deleteCFG(tmpName, true)
            }

            true
        }

        //Create Load Button
        val loadCFGButton = VisTextButton("Load".toLocale())
        loadCFGButton.changed { _, _ ->
            if (cfgNameTextBox.text.isNullOrEmpty()) { //Save using list selection
                val tmpSelection = configListAdapter.selection
                if (tmpSelection.size > 0) { //Validate
                    val tmpName = tmpSelection[0] //Selection is an array
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
        val deleteButton = VisTextButton("Delete".toLocale())
        deleteButton.changed { _, _ ->
            val tmpSelection = configListAdapter.selection
            if (tmpSelection.size > 0) { //Validate
                val tmpName = configListAdapter.selection[0] //Selection is an array

                deleteCFG(tmpName)
            }

            true
        }

        val openCfgFolder = VisTextButton("Open-configs-folder".toLocale())
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

        val refreshCloudConfigs = VisTextButton("Refresh".toLocale())
        refreshCloudConfigs.changed {_, _ -> updateCloudConfigsList()}
        val downloadCloudConfig = VisTextButton("Download".toLocale())
        downloadCloudConfig.changed { _, _ ->
            val tmpSelection = cloudConfigListAdapter.selection
            if (tmpSelection.size > 0) {
                val cfgName = tmpSelection[0]
                val text = safeUrlRead("https://ratpoison.dimden.dev/", cfgName)
                saveCFG(cfgName, text)

            }
        }

        //File Select Box
        updateCFGList()
        updateLocaleList()

        //Add everything to table
        val leftTopTable = VisTable(false)
        leftTopTable.add(configLabel).center().row()
        leftTopTable.add(configSelectionList.mainTable).left().top().width(240F).height(140F).row()
        leftTopTable.add(cfgNameTextBox).left().width(240F).row()
        val buttonsTable = VisTable()
        buttonsTable.add(saveCFGButton).width(80F)
        buttonsTable.add(loadCFGButton).width(80F)
        buttonsTable.add(deleteButton).width(80F).row()
        val buttonsTable2 = VisTable()
        buttonsTable2.add(saveDefaultButton).width(240F).row()
        buttonsTable2.add(openCfgFolder).width(240F).row()
        leftTopTable.add(buttonsTable).row()
        leftTopTable.add(buttonsTable2)
        table.add(leftTopTable).left().top()

        val rightTopTable = VisTable(false)
        rightTopTable.add(localeLabel).center().row()
        rightTopTable.add(localeSelectionList.mainTable).left().top().width(240F).height(224F).row()
        val buttonsLocaleTable = VisTable()
        buttonsLocaleTable.add(loadLocaleButton).width(240F)
        rightTopTable.add(buttonsLocaleTable).width(240F)
        table.add(rightTopTable).right().top().row()

        val leftBottomTable = VisTable()
        leftBottomTable.add(skinChangerLabel).center().row()
        leftBottomTable.add(skinChangerSelectionList.mainTable).left().top().width(240F).height(140F).row()
        leftBottomTable.add(skinCfgNameTextBox).left().width(240F).row()
        val skinChangerButtonsTable = VisTable()
        skinChangerButtonsTable.add(loadSkinCfgButton).width(80F)
        skinChangerButtonsTable.add(saveSkinCfgButton).width(80F)
        skinChangerButtonsTable.add(deleteSkinCfgButton).width(80F)
        leftBottomTable.add(skinChangerButtonsTable)
        table.add(leftBottomTable).left().bottom()

        val rightBottomTable = VisTable()
        rightBottomTable.add(cloudConfigLabel).center().row()
        rightBottomTable.add(cloudConfigSelectionList.mainTable).left().top().width(240F).height(180F).row()
        val cloudConfigsButtonsTable = VisTable()
        cloudConfigsButtonsTable.add(refreshCloudConfigs).width(120F)
        cloudConfigsButtonsTable.add(downloadCloudConfig).width(120F)
        rightBottomTable.add(cloudConfigsButtonsTable)

        table.add(rightBottomTable).right().top()
    }

    override fun getContentTable(): Table {
        return table
    }

    override fun getTabTitle(): String {
        return "Configs".toLocale()
    }

    fun updateCFGList() {
        if (VisUI.isLoaded() && opened && !updatingRanks) {
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

    private fun updateCloudConfigsList() {
        val text = safeUrlRead("https://ratpoison.dimden.dev/api/configs")
        if (text == "") return
        val lst: Array<Config> = gson.fromJson(text, configArrayType)
        cloudConfigListAdapter.clear()
        lst.forEach {
            if ((appless && it.tag2 == "appless") || (!appless && it.tag2 == "menu")) cloudConfigListAdapter.add(it.toString())
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
        updateSkinCfgList()
    }
}