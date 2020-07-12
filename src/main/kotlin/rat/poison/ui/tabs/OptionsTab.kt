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
import rat.poison.overlay.App.menuStage
import rat.poison.overlay.App.uiBombWindow
import rat.poison.overlay.App.uiKeybinds
import rat.poison.overlay.App.uiMenu
import rat.poison.overlay.App.uiSpecList
import rat.poison.ui.changed
import rat.poison.ui.refreshMenu
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisInputFieldCustom
import rat.poison.ui.uiHelpers.VisSliderCustom
import rat.poison.ui.uiPanels.optionsTab
import rat.poison.ui.uiUpdate
import rat.poison.utils.generalUtil.loadLocale
import rat.poison.utils.generalUtil.loadSettingsFromFiles
import java.io.File
import java.io.FileReader
import java.nio.file.Files
import kotlin.math.round

class OptionsTab : Tab(false, false) {
    private val table = VisTable(true)

    val menuKey = VisInputFieldCustom("Menu Key", "MENU_KEY")
    private val menuAlpha = VisSliderCustom("Menu Alpha", "MENU_ALPHA", .5F, 1F, .05F, false, width1 = 200F, width2 = 250F)
    private val oglFPS = VisSliderCustom("OpenGL FPS", "OPENGL_FPS", 30F, 245F, 5F, true, width1 = 200F, width2 = 250F)
    private val stayFocused = VisCheckBoxCustom("Stay Focused", "MENU_STAY_FOCUSED")
    private val debug = VisCheckBoxCustom("Debug", "DEBUG")
    private val keybinds = VisCheckBoxCustom("Keybinds", "KEYBINDS")
    private val blur = VisCheckBoxCustom("Gaussian Blur", "GAUSSIAN_BLUR")
    private val discordLink = LinkLabel("Join-Discord".toLocale(), "https://discord.gg/J2uHTJ2")

    var cfgFileSelectBox = VisSelectBox<String>()
    var localeFileSelectBox = VisSelectBox<String>()
    var loadLocaleButton = VisTextButton("Load-Locale".toLocale())

    init {
        loadLocaleButton.changed { _, _ ->
            CURRENT_LOCALE = localeFileSelectBox.selected
            loadLocale("$SETTINGS_DIRECTORY\\Localizations\\$CURRENT_LOCALE.locale")

            refreshMenu()
            uiUpdate()

            false
        }

        //Create Save Button
        val saveButton = VisTextButton("Save-CFG".toLocale())
        saveButton.changed { _, _ ->
            Dialogs.showInputDialog(menuStage, "Enter-config-name".toLocale(), "", object : InputDialogAdapter() {
                override fun finished(input: String) {
                    saveCFG(input)
                }
            }).setSize(200F, 200F)
            true
        }

        //Create Load Button
        val loadButton = VisTextButton("Load-CFG".toLocale())
        loadButton.changed { _, _ ->
            if (!cfgFileSelectBox.selected.isNullOrEmpty()) {
                if (cfgFileSelectBox.selected.count() > 0) {
                    loadCFG(cfgFileSelectBox.selected)
                }
            }
            true
        }

        //Create Delete Button
        val deleteButton = VisTextButton("Delete-CFG".toLocale())
        deleteButton.changed { _, _ ->
            if (!cfgFileSelectBox.selected.isNullOrEmpty()) {
                if (cfgFileSelectBox.selected.count() > 0) {
                    deleteCFG(cfgFileSelectBox.selected)
                }
            }
            true
        }

        //File Select Box
        updateCFGList()
        updateLocaleList()

        //Create Save Current Config To Default
        val saveCurConfig = VisTextButton("Save-Current-Config-To-Default-Settings".toLocale())
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

        table.add(cfgFileSelectBox).row()

        table.add(sldTable).row()

        table.add(saveCurConfig).width(340F).row()

        table.addSeparator()

        table.add(localeFileSelectBox).row()
        table.add(loadLocaleButton).row()

        table.addSeparator()

        table.add(discordLink)
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Options".toLocale()
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
                cfgFileSelectBox.items = cfgFilesArray
            } else {
                cfgFileSelectBox.clearItems()
            }
        }
    }

    fun updateLocaleList() {
        if (VisUI.isLoaded()) {
            val localeFilesArray = Array<String>()
            var items = 0
            File("$SETTINGS_DIRECTORY\\Localizations").listFiles()?.forEach {
                localeFilesArray.add(it.name.replace(".locale", ""))
                items++
            }

            if (items > 0) {
                localeFileSelectBox.items = localeFilesArray
                localeFileSelectBox.selected = CURRENT_LOCALE
            } else {
                localeFileSelectBox.clearItems()
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
                if (file.name != "CFGS" && file.name != "hitsounds" && file.name != "NadeHelper" && file.name != "SkinInfo") {
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
                if (file.name != "CFGS" && file.name != "hitsounds" && file.name != "NadeHelper" && file.name != "SkinInfo" && file.name != "Localizations") {
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