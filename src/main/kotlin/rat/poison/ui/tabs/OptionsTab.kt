@file:Suppress("BlockingMethodInNonBlockingContext")

package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.util.dialog.InputDialogAdapter
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rat.poison.*
import rat.poison.App.menuStage
import rat.poison.ui.UIUpdate
import rat.poison.ui.changed
import java.io.File
import java.io.FileReader
import java.nio.file.Files

class OptionsTab : Tab(false, false) {
    private val table = VisTable(true)

    private val loadButton1 = VisTextButton("Load " + curSettings["CFG1_NAME"].toString().replace("\"", ""))
    private val loadButton2 = VisTextButton("Load " + curSettings["CFG2_NAME"].toString().replace("\"", ""))
    private val loadButton3 = VisTextButton("Load " + curSettings["CFG3_NAME"].toString().replace("\"", ""))

    init {
        //Create UIAlpha Slider
        val menuAlpha = VisTable()
        Tooltip.Builder("The alpha of the menu").target(menuAlpha).build()
        val menuAlphaLabel = VisLabel("Menu Alpha: " + 1F) //1F is default
        val menuAlphaSlider = VisSlider(0.5F, 1F, 0.05F, false)
        menuAlphaSlider.value = 1F
        menuAlphaSlider.changed { _, _ ->
            val alp = (Math.round(menuAlphaSlider.value * 100F) / 100F)
            menuAlpha.parent.parent.parent.parent.color.a = alp //Set the top level parents alpha
            menuAlphaLabel.setText("Menu Alpha: " + alp.toString() + when(alp.toString().length) {4 -> "" 3->"  " 2->"    " else ->"      "}) //Same parent situation
        }
        menuAlpha.add(menuAlphaLabel).spaceRight(6F)
        menuAlpha.add(menuAlphaSlider)

        //Create Save Button 1
        val saveButton1 = VisTextButton("Save to CFG1")
        Tooltip.Builder("Save current configuration to the cfg file").target(saveButton1).build()
        saveButton1.changed { _, _ ->
            Dialogs.showInputDialog(menuStage, "Enter config name: ", "", object : InputDialogAdapter() {
                override fun finished(input: String) {
                    saveCFG(1, input)
                }
            }).setSize(200F, 200F)
            true
        }

        //Create Save Button 2
        val saveButton2 = VisTextButton("Save to CFG2")
        Tooltip.Builder("Save current configuration to the cfg file").target(saveButton2).build()
        saveButton2.changed { _, _ ->
            Dialogs.showInputDialog(menuStage, "Enter config name: ", "", object : InputDialogAdapter() {
                override fun finished(input: String) {
                    saveCFG(2, input)
                }
            }).setSize(200F, 200F)
            true
        }

        //Create Save Button 3
        val saveButton3 = VisTextButton("Save to CFG3")
        Tooltip.Builder("Save current configuration to the cfg file").target(saveButton3).build()
        saveButton3.changed { _, _ ->
            Dialogs.showInputDialog(menuStage, "Enter config name: ", "", object : InputDialogAdapter() {
                override fun finished(input: String) {
                    saveCFG(3, input)
                }
            }).setSize(200F, 200F)
            true
        }

        //Create Load Button 1
        Tooltip.Builder("Load the previously saved configuration from the cfg file").target(loadButton1).build()
        loadButton1.changed { _, _ ->
            loadCFG(1)
        }

        //Create Load Button 2
        Tooltip.Builder("Load the previously saved configuration from the cfg file").target(loadButton2).build()
        loadButton2.changed { _, _ ->
            loadCFG(2)
        }

        //Create Load Button 3
        Tooltip.Builder("Load the previously saved configuration from the cfg file").target(loadButton3).build()
        loadButton3.changed { _, _ ->
            loadCFG(3)
        }

        //Create Save Current Config To Default
        val saveCurConfig = VisTextButton("Save Current Config To Default Settings")
        Tooltip.Builder("Save current configuration to the settings files").target(saveCurConfig).build()
        saveCurConfig.changed { _, _ ->
            if (!saving) {
                GlobalScope.launch {
                    saving = true
                    println("\n Saving! \n")
                    File(SETTINGS_DIRECTORY).listFiles().forEach { file ->
                        val sbLines = StringBuilder()
                        if (file.name != "cfg1.kts" && file.name != "cfg2.kts" && file.name != "cfg3.kts" && file.name != "hitsound.mp3") {
                            FileReader(file).readLines().forEach { line ->
                                if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith(" *") && !line.startsWith("*") && !line.trim().isEmpty()) {
                                    val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

                                    //Add custom checks
                                    when {
                                        curLine[0] == "CFG1_NAME" || curLine[0] == "CFG2_NAME" || curLine[0] == "CFG3_NAME" -> {
                                            sbLines.append(curLine[0] + " = " + curSettings[curLine[0]] + "\n")
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
                    println("\n Saving complete! \n")
                    saving = false
                }
            }
            true
        }

        table.add(menuAlpha).colspan(2).row()

        table.add(saveButton1)
        table.add(loadButton1).row()

        table.add(saveButton2)
        table.add(loadButton2).row()

        table.add(saveButton3)
        table.add(loadButton3).row()

        table.add(saveCurConfig).colspan(2).row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Options"
    }

    private fun saveCFG(cfgNum: Int, cfgName: String) {
        if (!saving) {
            GlobalScope.launch {
                saving = true
                println("\n Saving! \n")

                when (cfgNum)
                {
                    1 -> { curSettings["CFG1_NAME"] = cfgName; loadButton1.setText("Load " + curSettings["CFG1_NAME"].toString().replace("\"", "")) }
                    2 -> { curSettings["CFG2_NAME"] = cfgName; loadButton2.setText("Load " + curSettings["CFG2_NAME"].toString().replace("\"", "")) }
                    else -> { curSettings["CFG3_NAME"] = cfgName; loadButton3.setText("Load " + curSettings["CFG3_NAME"].toString().replace("\"", "")) }
                }

                val cfgFile = File("settings\\cfg$cfgNum.kts")

                if (!cfgFile.exists()) {
                    cfgFile.createNewFile()
                }

                //Add Imports
                val sbLines = StringBuilder()
                sbLines.append("import rat.poison.settings.*\n")
                sbLines.append("import rat.poison.game.Color\n\n")

                File(SETTINGS_DIRECTORY).listFiles().forEach { file ->
                    if (file.name != "cfg1.kts" && file.name != "cfg2.kts" && file.name != "cfg3.kts" && file.name != "hitsound.mp3") {
                        FileReader(file).readLines().forEach { line ->
                            if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith(" *") && !line.startsWith("*") && !line.trim().isEmpty()) {
                                val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

                                when {
                                        curLine[0] == "CFG1_NAME" || curLine[0] == "CFG2_NAME" || curLine[0] == "CFG3_NAME" -> {
                                            sbLines.append(curLine[0] + " = \"" + curSettings[curLine[0]].toString().replace("\"", "") + "\"\n")
                                        }

                                        else -> {
                                            sbLines.append(curLine[0] + " = " + curSettings[curLine[0]] + "\n")
                                        }
                                    }
                            }
                        }
                    }
                }

                Files.delete(cfgFile.toPath())
                Files.createFile(cfgFile.toPath())
                var firstLine = false
                sbLines.lines().forEach {cfgFile.appendText(if (!firstLine) { firstLine = true; it } else if (!it.isBlank()) "\n" + it else "\n")}
                sbLines.clear()

                //Repeat for General.kts to save cfg name
                val genFile = File("settings\\General.kts")
                FileReader(genFile).readLines().forEach { line ->
                    if (line.startsWith(when (cfgNum) {1 -> "CFG1_NAME" 2 -> "CFG2_NAME" else -> "CFG3_NAME"})) {
                        val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

                        sbLines.append(curLine[0] + " = \"" + curSettings[curLine[0]] + "\"\n")

                    } else {
                        sbLines.append(line + "\n")
                    }
                }
                Files.delete(genFile.toPath())
                Files.createFile(genFile.toPath())
                firstLine = false
                sbLines.lines().forEach {genFile.appendText(if (!firstLine) { firstLine = true; it } else if (!it.isBlank()) "\n" + it else "\n")}
                sbLines.clear()

                println("\n Saving complete! \n")
                saving = false
            }
        } else {
            Dialogs.showErrorDialog(App.menuStage, "Error", "Wait for saving to complete first!")
        }
    }

    private fun loadCFG(cfgNum: Int) {
        if (!saving) {
            val cfgFile = File("settings\\cfg$cfgNum.kts")
            if (!cfgFile.exists()) {
                Dialogs.showErrorDialog(App.menuStage, "Error", "cfg$cfgNum.kts not found, save your configuration first!")
            } else {
                GlobalScope.launch {
                    println("\n Loading! \n")
                    loadSettingsFromFiles("settings\\cfg$cfgNum.kts", true)
                    UIUpdate()
                    println("\n Loading complete! \n")
                }
            }
        } else {
            Dialogs.showErrorDialog(App.menuStage, "Error", "Wait for saving to complete first!")
        }
    }
}