@file:Suppress("BlockingMethodInNonBlockingContext")

package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.util.dialog.InputDialogAdapter
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rat.poison.*
import rat.poison.App.menuStage
import rat.poison.App.uiBombWindow
import rat.poison.App.uiKeybinds
import rat.poison.App.uiMenu
import rat.poison.App.uiSpecList
import rat.poison.ui.changed
import rat.poison.ui.uiHelpers.*
import rat.poison.ui.uiHelpers.optionsTab.MenuSliderCustom
import rat.poison.ui.uiPanels.optionsTab
import rat.poison.ui.uiPanels.updateVisWindowsNames
import rat.poison.ui.uiUpdate
import java.io.File
import java.io.FileReader
import java.nio.file.Files
import kotlin.math.round

class OptionsTab : Tab(false, false) {
    private val table = VisTable(true)

    val menuKey = VisInputFieldCustom(curLocalization["MENU_KEY"], "MENU_KEY", nameInLocalization = "MENU_KEY")
    val menuAlphaSliderCustom = MenuSliderCustom(curLocalization["MENU_ALPHA_SLIDER"], "MENU_ALPHA", 0.5F, 1F, 0.05F, false)
    val oglFPS = VisSliderCustom(curLocalization["OPENGL_FPS"], "OPENGL_FPS", 30F, 245F, 5F, true, width1 = 200F, width2 = 250F, nameInLocalization = "OPENGL_FPS")
    val stayFocused = VisCheckBoxCustom(curLocalization["MENU_STAY_FOCUSED"], "MENU_STAY_FOCUSED", nameInLocalization = "MENU_STAY_FOCUSED")
    val debug = VisCheckBoxCustom(curLocalization["ENABLE_DEBUG"], "DEBUG", nameInLocalization = "ENABLE_DEBUG")
    val keybinds = VisCheckBoxCustom(curLocalization["KEYBINDS"], "KEYBINDS", nameInLocalization = "KEYBINDS")
    val blur = VisCheckBoxCustom(curLocalization["GAUSSIAN_BLUR"], "GAUSSIAN_BLUR", nameInLocalization = "GAUSSIAN_BLUR")

    val saveCurConfig = VisTextButtonCustom(curLocalization["SAVE_CONFIG_TO_DEFAULT"], nameInLocalization = "SAVE_CONFIG_TO_DEFAULT")
    val saveButton = VisTextButtonCustom(curLocalization["SAVE_CONFIG"], nameInLocalization = "SAVE_CONFIG")
    val loadButton = VisTextButtonCustom(curLocalization["LOAD_CONFIG"], nameInLocalization = "LOAD_CONFIG")
    val deleteButton = VisTextButtonCustom(curLocalization["DELETE_CONFIG"], nameInLocalization = "DELETE_CONFIG")

    private val discordLink = LinkLabel(curLocalization["JOIN_DISCORD"], "https://discord.gg/J2uHTJ2")

    var fileSelectBox: VisSelectBox<String> = VisSelectBox()
    var localizationSelectBox: VisSelectBox<String> = VisSelectBox()
    val loadLocalizationButton = VisTextButtonCustom(curLocalization["LOAD_LOCALIZATION"], nameInLocalization = "LOAD_LOCALIZATION")
    init {
        //Create UIAlpha Slider
        val menuAlpha = VisTable()
        menuAlphaSliderCustom.changed { _, _ ->
            val alp = (round(menuAlphaSliderCustom.sliderBar.value * 100F) / 100F)
            uiMenu.changeAlpha(alp)
            menuAlphaSliderCustom.sliderLabel.setText(curLocalization["MENU_ALPHA_SLIDER"] + ": " + alp.toString() + when(alp.toString().length) {4->"" 3->"  " 2->"    " else ->"      "})
        }
        menuAlpha.add(menuAlphaSliderCustom).width(200F)


        //Create Save Button
        saveButton.changed { _, _ ->
            Dialogs.showInputDialog(menuStage, curLocalization["ENTER_CONFIG_NAME"], "", object : InputDialogAdapter() {
                override fun finished(input: String) {
                    saveCFG(input)
                }
            }).setSize(200F, 200F)
            true
        }

        //Create Load Button
        loadButton.changed { _, _ ->
            if (!fileSelectBox.selected.isNullOrEmpty()) {
                if (fileSelectBox.selected.count() > 0) {
                    loadCFG(fileSelectBox.selected)
                }
            }
            true
        }
        loadLocalizationButton.changed { _, _ ->
            if (!localizationSelectBox.selected.isNullOrEmpty()) {
                if (localizationSelectBox.selected.count() > 0) {
                    loadLocalizationFromFile(localizationSelectBox.selected)
                    uiMenu.updateTabs()
                    updateVisWindowsNames()
                    updateWindows()
                    uiUpdate()
                }
            }
            true
        }
        //Create Delete Button
        deleteButton.changed { _, _ ->
            if (!fileSelectBox.selected.isNullOrEmpty()) {
                if (fileSelectBox.selected.count() > 0) {
                    deleteCFG(fileSelectBox.selected)
                }
            }
            true
        }


        //File Select Box
        updateCFGList()
        updateLocalizationsList()

        //Create Save Current Config To Default
        saveCurConfig.changed { _, _ ->
            saveWindows()
            saveDefault()
            true
        }

        //Add everything to table
        val sldTable = VisTable()
        sldTable.add(saveButton).width(100F)
        sldTable.add(loadButton).padLeft(20F).padRight(20F).width(100F)
        sldTable.add(deleteButton).width(100F)

        val localizationTab = VisTable()
        localizationTab.add(localizationSelectBox).padBottom(8F).padTop(2.5F).row()
        localizationTab.add(loadLocalizationButton)
        debug.changed { _, _ ->
            dbg = debug.isChecked
            true
        }

        table.add(menuKey).padLeft(25F).left().row()
        table.add(menuAlpha).row()
        table.add(oglFPS).row()
        table.add(stayFocused).padLeft(25F).left().row()
        table.add(debug).padLeft(25F).left().row()
        table.add(keybinds).padLeft(25F).left().row()
        table.add(blur).padLeft(25F).left().row()

        table.addSeparator()

        table.add(fileSelectBox).row()

        table.add(sldTable).row()

        table.add(saveCurConfig).width(340F).row()

        table.addSeparator()
        table.add(localizationTab).row()
        table.addSeparator()
        table.add(discordLink)
    }
    fun changeAlpha() {
        val alp = (round(menuAlphaSliderCustom.sliderBar.value * 100F) / 100F)
        uiMenu.changeAlpha(alp)
    }
    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return curLocalization["OPTIONS_TAB_NAME"]
    }

    fun updateLocalizationsList() {
        if (VisUI.isLoaded()) {
            val localizationsList = Array<String>()
            var items = 0
            File("$SETTINGS_DIRECTORY\\Localizations").listFiles()?.forEach {
                localizationsList.add(it.name.replace(".locale", ""))
                items++
            }

            if (items > 0) {
                localizationSelectBox.items = localizationsList
            } else {
                localizationSelectBox.clearItems()
            }
        }
    }

    fun updateCFGList() {
        if (VisUI.isLoaded()) {
            val cfgFilesArray = Array<String>()
            var items = 0
            File("$SETTINGS_DIRECTORY\\CFGS").listFiles()?.forEach {
                cfgFilesArray.add(it.name.replace(".cfg", ""))
                items++
            }

            if (items > 0) {
                fileSelectBox.items = cfgFilesArray
            } else {
                fileSelectBox.clearItems()
            }
        }
    }
}

fun saveDefault() {
    if (!saving) {
        GlobalScope.launch {
            saving = true
            println("\nSaving!\n")
            File(SETTINGS_DIRECTORY).listFiles()?.forEach { file ->
                val sbLines = StringBuilder()
                if (file.name != "CFGS" && file.name != "Localizations" && file.name != "hitsounds" && file.name != "NadeHelper" && file.name != "SkinInfo") {
                    FileReader(file).readLines().forEach { line ->
                        if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith(" *") && !line.startsWith("*") && line.trim().isNotEmpty()) {
                            val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

                            //Add custom checks
                            when {
                                curLine[0] == "HITSOUND_FILE_NAME" -> {
                                    sbLines.append(curLine[0] + " = \"" + curSettings[curLine[0]].replace("\"", "") + "\"\n")
                                }

                                curLine[0] == "FLASH_MAX_ALPHA" -> {
                                    sbLines.append(curLine[0] + " = " + curSettings[curLine[0]].replace("F", "") + "F\n")
                                }

                                else -> {
                                    sbLines.append(curLine[0] + " = " + curSettings[curLine[0]] + "\n")
                                }
                            }

                        }
                        else
                        {
                            sbLines.append(line + "\n")
                        }
                    }
                    Files.delete(file.toPath())
                    Files.createFile(file.toPath())
                    var firstLine = false
                    sbLines.lines().forEach {file.appendText(if (!firstLine) { firstLine = true; it } else if (!it.isBlank()) "\n" + it else "\n")}
                    sbLines.clear()
                }
            }
            println("\nSaving Complete!\n")
            saving = false
        }
    }
}

fun saveCFG(cfgFileName: String) {
    saveWindows()

    if (!saving) {
        saving = true
        GlobalScope.launch {
            println("Saving!")

            val cfgDir = File("$SETTINGS_DIRECTORY\\CFGS")
            if (!cfgDir.exists()) {
                Files.createDirectory(cfgDir.toPath())
            }

            cfgFileName.replace(".cfg", "")

            val cfgFile = File("$SETTINGS_DIRECTORY\\CFGS\\$cfgFileName.cfg")
            if (!cfgFile.exists()) {
                cfgFile.createNewFile()
            }

            val sbLines = StringBuilder()
            File(SETTINGS_DIRECTORY).listFiles()?.forEach { file ->
                if (file.name != "CFGS" && file.name != "Localizations" && file.name != "hitsounds" && file.name != "NadeHelper" && file.name != "SkinInfo") {
                    FileReader(file).readLines().forEach { line ->
                        if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith(" *") && !line.startsWith("*") && line.trim().isNotEmpty()) {
                            val tempCurLine = line.trim().split(" ".toRegex(), 3) //Separate line into 'VARIABLE = VALUE'

                            sbLines.append(tempCurLine[0] + " = " + curSettings[tempCurLine[0]] + "\n")
                        }
                    }
                }
            }

            Files.delete(cfgFile.toPath()) //Replace with cfgFile. ??
            Files.createFile(cfgFile.toPath())

            var firstLine = false
            sbLines.lines().forEach {cfgFile.appendText(if (!firstLine) { firstLine = true; it } else if (!it.isBlank()) "\n" + it else "\n")}
            sbLines.clear()

            println("\nSaving Complete!\n")
            if (VisUI.isLoaded()) {
                optionsTab.updateCFGList()
            }
            saving = false
        }
    }
}

fun loadCFG(cfgFileName: String) {
    if (!saving) {

        cfgFileName.replace(".cfg", "")

        val cfgFile = File("$SETTINGS_DIRECTORY\\CFGS\\$cfgFileName.cfg")
        if (!cfgFile.exists()) {
            Dialogs.showErrorDialog(menuStage, "Error", "$cfgFileName not found, save your configuration first!")
        } else {
            saving = true
            GlobalScope.launch {
                println("Loading\n")
                loadSettingsFromFiles("$SETTINGS_DIRECTORY\\CFGS\\$cfgFileName.cfg", true)
                uiUpdate()
                updateWindows()
                println("\nLoading Complete!\n")
                saving = false
            }
        }
    } else {
        Dialogs.showErrorDialog(menuStage, "Error", "Wait for saving to finish first!")
    }
}

fun deleteCFG(cfgFileName: String) {
    cfgFileName.replace(".cfg", "")

    val cfgFile = File("$SETTINGS_DIRECTORY\\CFGS\\$cfgFileName.cfg")
    if (cfgFile.exists()) {
        cfgFile.delete()
    }
    if (VisUI.isLoaded()) {
        optionsTab.updateCFGList()
    }
    println("Deleted $cfgFileName\n")
}

fun updateOptionsTab() {
    optionsTab.apply {
        menuKey.update()
        changeAlpha()
        menuAlphaSliderCustom.update()
        oglFPS.update()
        stayFocused.update()
        debug.update()
        keybinds.update()
        blur.update()
        saveButton.update()
        loadButton.update()
        deleteButton.update()
        loadLocalizationButton.update()
        saveCurConfig.update()
    }
}

fun saveWindows() {
    if (VisUI.isLoaded()) {
        curSettings["BOMB_TIMER_X"] = uiBombWindow.x
        curSettings["BOMB_TIMER_Y"] = uiBombWindow.y
        curSettings["BOMB_TIMER_ALPHA"] = uiBombWindow.color.a

        curSettings["SPECTATOR_LIST_X"] = uiSpecList.x
        curSettings["SPECTATOR_LIST_Y"] = uiSpecList.y
        curSettings["SPECTATOR_LIST_ALPHA"] = uiSpecList.color.a

        curSettings["KEYBINDS_X"] = uiKeybinds.x
        curSettings["KEYBINDS_Y"] = uiKeybinds.y
        curSettings["KEYBINDS_ALPHA"] = uiKeybinds.color.a
    }
}

fun updateWindows() {
    if (VisUI.isLoaded()) {
        uiBombWindow.updatePosition(curSettings["BOMB_TIMER_X"].toFloat(), curSettings["BOMB_TIMER_Y"].toFloat())
        uiBombWindow.changeAlpha(curSettings["BOMB_TIMER_ALPHA"].toFloat())
        uiSpecList.updatePosition(curSettings["SPECTATOR_LIST_X"].toFloat(), curSettings["SPECTATOR_LIST_Y"].toFloat())
        uiSpecList.changeAlpha(curSettings["SPECTATOR_LIST_ALPHA"].toFloat())
    }
}