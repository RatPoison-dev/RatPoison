@file:Suppress("BlockingMethodInNonBlockingContext")

package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.util.dialog.InputDialogAdapter
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jire.arrowhead.keyPressed
import rat.poison.*
import rat.poison.App.menuStage
import rat.poison.App.uiBombWindow
import rat.poison.App.uiMenu
import rat.poison.App.uiSpecList
import rat.poison.ui.changed
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisSliderCustom
import rat.poison.ui.uiUpdate
import rat.poison.utils.ObservableBoolean
import java.io.File
import java.io.FileReader
import java.nio.file.Files
import kotlin.math.round

class OptionsTab : Tab(false, false) {
    private val table = VisTable(true)

    private val fileSelectBox = VisSelectBox<String>()

    val oglFPS = VisSliderCustom("OpenGL FPS", "OPENGL_FPS", 30F, 245F, 5F, true, width1 = 200F, width2 = 250F)
    val menuKeyField = VisValidatableTextField(Validators.FLOATS)
    val stayFocused = VisCheckBoxCustom("Stay Focused", "MENU_STAY_FOCUSED")
    val debug = VisCheckBoxCustom("Debug", "DEBUG")
    val discordLink = LinkLabel("Join Discord", "https://discord.gg/8546tDG")

    init {
        //Create UIAlpha Slider
        val menuAlpha = VisTable()
        val menuAlphaLabel = VisLabel("Menu Alpha: " + 1F) //1F is default
        val menuAlphaSlider = VisSlider(0.5F, 1F, 0.05F, false)
        menuAlphaSlider.value = 1F
        menuAlphaSlider.changed { _, _ ->
            val alp = (round(menuAlphaSlider.value * 100F) / 100F)
            uiMenu.changeAlpha(alp)
            menuAlphaLabel.setText("Menu Alpha: " + alp.toString() + when(alp.toString().length) {4->"" 3->"  " 2->"    " else ->"      "})
        }
        menuAlpha.add(menuAlphaLabel).width(200F)
        menuAlpha.add(menuAlphaSlider).width(250F)

        //Create Menu Key Input Box
        val menuKey = VisTable()
        val menuKeyLabel = VisLabel("Menu Key: ")
        menuKeyField.text = curSettings["MENU_KEY"]
        menuKey.changed { _, _ ->
            if (menuKeyField.text.toIntOrNull() != null) {
                curSettings["MENU_KEY"] = menuKeyField.text.toInt().toString()
                overlayMenuKey = ObservableBoolean({ keyPressed(curSettings["MENU_KEY"].toInt()) })
            }
        }
        menuKey.add(menuKeyLabel)
        menuKey.add(menuKeyField).spaceRight(6F).width(40F)
        menuKey.add(LinkLabel("?", "http://cherrytree.at/misc/vk.htm"))

        //Create Save Button
        val saveButton = VisTextButton("Save CFG")
        saveButton.changed { _, _ ->
            Dialogs.showInputDialog(menuStage, "Enter config name: ", "", object : InputDialogAdapter() {
                override fun finished(input: String) {
                    saveCFG(input)
                }
            }).setSize(200F, 200F)
            true
        }

        //Create Load Button
        val loadButton = VisTextButton("Load CFG")
        loadButton.changed { _, _ ->
            if (!fileSelectBox.selected.isNullOrEmpty()) {
                if (fileSelectBox.selected.count() > 0) {
                    loadCFG(fileSelectBox.selected)
                }
            }
            true
        }

        //Create Delete Button
        val deleteButton = VisTextButton("Delete CFG")
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

        //Create Save Current Config To Default
        val saveCurConfig = VisTextButton("Save Current Config To Default Settings")
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

        table.addSeparator()

        table.add(fileSelectBox).row()

        table.add(sldTable).row()

        table.add(saveCurConfig).width(340F).row()

        table.addSeparator()
        table.add(discordLink)
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Options"
    }

    private fun updateCFGList() {
        val cfgFilesArray = Array<String>()
        File("$SETTINGS_DIRECTORY\\CFGS").listFiles()?.forEach {
            cfgFilesArray.add(it.name)
        }

        fileSelectBox.items = cfgFilesArray
    }

    private fun saveDefault() {
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

    private fun saveCFG(cfgFileName: String) {
        saveWindows()

        if (!saving) {
            saving = true
            GlobalScope.launch {
                println("\nSaving!\n")

                val cfgDir = File("$SETTINGS_DIRECTORY\\CFGS")
                if (!cfgDir.exists()) {
                    Files.createDirectory(cfgDir.toPath())
                }

                val cfgFile = File("$SETTINGS_DIRECTORY\\CFGS\\$cfgFileName.cfg")
                if (!cfgFile.exists()) {
                    cfgFile.createNewFile()
                }

                val sbLines = StringBuilder()
                File(SETTINGS_DIRECTORY).listFiles()?.forEach { file ->
                    if (file.name != "CFGS" && file.name != "hitsounds" && file.name != "NadeHelper" && file.name != "SkinInfo") {
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
                updateCFGList()
                saving = false
            }
        }
    }

    private fun loadCFG(cfgFileName: String) {
        if (!saving) {
            val cfgFile = File("$SETTINGS_DIRECTORY\\CFGS\\$cfgFileName")
            if (!cfgFile.exists()) {
                Dialogs.showErrorDialog(menuStage, "Error", "$cfgFileName not found, save your configuration first!")
            } else {
                saving = true
                GlobalScope.launch {
                    println("\nLoading\n")
                    loadSettingsFromFiles("$SETTINGS_DIRECTORY\\CFGS\\$cfgFileName", true)
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

    private fun deleteCFG(cfgFileName: String) {
        val cfgFile = File("$SETTINGS_DIRECTORY\\CFGS\\$cfgFileName")
        if (cfgFile.exists()) {
            cfgFile.delete()
        }
        updateCFGList()
    }

    private fun saveWindows() {
        curSettings["BOMB_TIMER_X"] = uiBombWindow.x
        curSettings["BOMB_TIMER_Y"] = uiBombWindow.y
        curSettings["BOMB_TIMER_ALPHA"] = uiBombWindow.color.a

        curSettings["SPECTATOR_LIST_X"] = uiSpecList.x
        curSettings["SPECTATOR_LIST_Y"] = uiSpecList.y
        curSettings["SPECTATOR_LIST_ALPHA"] = uiSpecList.color.a
    }

    private fun updateWindows() {
        uiBombWindow.updatePosition(curSettings["BOMB_TIMER_X"].toFloat(), curSettings["BOMB_TIMER_Y"].toFloat())
        uiBombWindow.changeAlpha(curSettings["BOMB_TIMER_ALPHA"].toFloat())
        uiSpecList.updatePosition(curSettings["SPECTATOR_LIST_X"].toFloat(), curSettings["SPECTATOR_LIST_Y"].toFloat())
        uiSpecList.changeAlpha(curSettings["SPECTATOR_LIST_ALPHA"].toFloat())
    }
}