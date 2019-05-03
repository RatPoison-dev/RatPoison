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
import rat.poison.settings.CFG1_NAME
import rat.poison.settings.CFG2_NAME
import rat.poison.settings.CFG3_NAME
import rat.poison.ui.UIUpdate
import rat.poison.ui.changed
import java.io.File
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import javax.script.ScriptEngineManager

class OptionsTab : Tab(false, false) {
    private val table = VisTable(true)

    val loadButton1 = VisTextButton("Load $CFG1_NAME")
    val loadButton2 = VisTextButton("Load $CFG2_NAME")
    val loadButton3 = VisTextButton("Load $CFG3_NAME")

    init {
        //Create UI_Alpha Slider
        val menuAlpha = VisTable()
        Tooltip.Builder("The alpha of the menu").target(menuAlpha).build()
        val menuAlphaLabel = VisLabel("Menu Alpha: " + 1F) //1F is default
        val menuAlphaSlider = VisSlider(0.1F, 1F, 0.1F, false)
        menuAlphaSlider.value = 1F
        menuAlphaSlider.changed { _, _ ->
            menuAlpha.parent.parent.parent.parent.color.a = (Math.round(menuAlphaSlider.value * 10F) / 10F) //Set the top level parents alpha (currently .parent.parent.parent.parent is the only way, instead of a way to find top most instantly
            menuAlphaLabel.setText("Menu Alpha: " + menuAlpha.parent.parent.parent.parent.color.a.toString()) //Same parent situation
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
                        if (file.name != "cfg1.kts" && file.name != "cfg2.kts" && file.name != "cfg3.kts" && file.name != "Advanced.kts" && file.name != "hitsound.mp3") {
                            if (file.name == "Advanced.kts" || file.name == "hitsound.mp3")
                            {
                                println("using file we shouldnt")
                            }
                            FileReader(file).readLines().forEach { line ->
                                if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith(" *") && !line.startsWith("*") && !line.trim().isEmpty()) {
                                    val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

                                    //Add custom checks for variables that need a type ident ie F
                                    when {
                                        /*Case: 1*/     curLine[0] == "FLASH_MAX_ALPHA" -> {
                                        sbLines.append(curLine[0] + " = " + engine.eval(curLine[0]) + "F\n")
                                        }

                                        /*Case: 2*/     curLine[0] == "CFG1_NAME" || curLine[0] == "CFG2_NAME" || curLine[0] == "CFG3_NAME" -> {
                                        sbLines.append(curLine[0] + " = \"" + engine.eval(curLine[0]) + "\"\n")
                                        }

                                        /*Case: 3*/     file.name == "GunAimOverride.kts" -> { val curOverrideWep = engine.eval(curLine[0]) as kotlin.DoubleArray; sbLines.append((curLine[0] + " = " + curOverrideWep.contentToString() + "\n").replace("[", "doubleArrayOf(").replace("]", ")")) }

                                        /*Case: Else*/  else -> {
                                        sbLines.append(curLine[0] + " = " + engine.eval(curLine[0]) + "\n")
                                        }
                                    }

                                }
                                else
                                {
                                    sbLines.append(line + "\n")
                                }
                                println(line)
                            }
                            Files.delete(file.toPath())
                            Files.createFile(file.toPath())
                            var firstLine = false
                            sbLines.lines().forEach {file.appendText(if (!firstLine) { firstLine = true; it } else if (!it.isBlank()) "\n" + it else "\n")}
                            sbLines.clear()
                        }
                    }
                    loadSettings()
                    println("\n Saving complete! \n")
                    saving = false
                }
            }
            true
        }

        table.add(menuAlpha).colspan(2).row() //width 250F
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
                    1 -> { CFG1_NAME = cfgName; loadButton1.setText("Load $CFG1_NAME") }
                    2 -> { CFG2_NAME = cfgName; loadButton2.setText("Load $CFG2_NAME") }
                    else -> { CFG3_NAME = cfgName; loadButton3.setText("Load $CFG3_NAME") }
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
                    if (file.name != "cfg1.kts" && file.name != "cfg2.kts" && file.name != "cfg3.kts" && file.name != "Advanced.kts" && file.name != "hitsound.mp3") {
                        FileReader(file).readLines().forEach { line ->
                            if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith(" *") && !line.startsWith("*") && !line.trim().isEmpty()) {
                                val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

                                //Add custom checks for variables that need a type ident ie F
                                when {
                                    /*Case: 1*/     curLine[0] == "FLASH_MAX_ALPHA" -> { sbLines.append(curLine[0] + " = " + engine.eval(curLine[0]) + "F\n") }
                                    /*Case: 2*/     (curLine[0] == "CFG1_NAME" || curLine[0] == "CFG2_NAME" || curLine[0] == "CFG3_NAME") -> { sbLines.append(curLine[0] + " = \"" + engine.eval(curLine[0]) + "\"\n") }
                                    /*Case: 3*/     file.name == "GunAimOverride.kts" -> { val curOverrideWep = engine.eval(curLine[0]) as kotlin.DoubleArray; sbLines.append((curLine[0] + " = " + curOverrideWep.contentToString() + "\n").replace("[", "doubleArrayOf(").replace("]", ")")) }
                                    /*Case: Else*/  else -> { sbLines.append(curLine[0] + " = " + engine.eval(curLine[0]) + "\n")}
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

//                engine = ScriptEngineManager().getEngineByName("kotlin")
//                FileReader(cfgFile).readLines().forEach { line ->
//                    engine.eval(line)
//                }

                //Repeat for General.kts to save cfg name
                val genFile = File("settings\\General.kts")
                FileReader(genFile).readLines().forEach { line ->
                    if (line.startsWith(when (cfgNum) {1 -> "CFG1_NAME" 2 -> "CFG2_NAME" else -> "CFG3_NAME"})) {
                        val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

                        sbLines.append(curLine[0] + " = \"" + engine.eval(curLine[0]) + "\"\n")

                    } else {
                        sbLines.append(line + "\n")
                    }
                }
                Files.delete(genFile.toPath())
                Files.createFile(genFile.toPath())
                firstLine = false
                sbLines.lines().forEach {genFile.appendText(if (!firstLine) { firstLine = true; it } else if (!it.isBlank()) "\n" + it else "\n")}
                sbLines.clear()

                engine = ScriptEngineManager().getEngineByName("kotlin")
                FileReader(cfgFile).use { engine.eval(it.readLines().joinToString("\n")) }

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
                    engine = ScriptEngineManager().getEngineByName("kotlin")
                    FileReader(cfgFile).use { engine.eval(it.readLines().joinToString("\n")) }
                    UIUpdate()
                    println("\n Loading complete! \n")
                }
            }
        } else {
            Dialogs.showErrorDialog(App.menuStage, "Error", "Wait for saving to complete first!")
        }
    }
}