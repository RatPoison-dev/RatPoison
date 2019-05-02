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
import rat.poison.settings.CFG_NAME
import rat.poison.ui.UIUpdate
import rat.poison.ui.changed
import java.io.File
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import javax.script.ScriptEngineManager

class OptionsTab : Tab(false, false) {
    private val table = VisTable(true)

    val loadButton = VisTextButton("Load $CFG_NAME")

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

        //Create Save Button
        val saveButton = VisTextButton("Save Current Configuration To Cfg")
        Tooltip.Builder("Save current configuration to the cfg file").target(saveButton).build()
        saveButton.changed { _, _ ->
            Dialogs.showInputDialog(menuStage, "Enter config name: ", "", object : InputDialogAdapter() {
                override fun finished(input: String) {
                    if (!saving) {
                        GlobalScope.launch {
                            saving = true
                            println("\n Saving! \n")
                            CFG_NAME = input
                            loadButton.setText("Load $CFG_NAME")
                            val cfgFile = File("settings" + "\\" + "cfg.kts")

                            if (!cfgFile.exists()) {
                                cfgFile.createNewFile()
                            }

                            //Add Imports
                            var cfgfiletext = ""
                            cfgfiletext += "import rat.poison.settings.*" + System.lineSeparator()
                            cfgfiletext += "import rat.poison.game.Color" + System.lineSeparator() + System.lineSeparator()

                            File(SETTINGS_DIRECTORY).listFiles().forEach { file ->
                                if (file.name != "cfg.kts" && file.name != "Advanced.kts" && file.name != "hitsound.mp3") {
                                    FileReader(file).readLines().forEach { line ->
                                        if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith(" *") && !line.startsWith("*") && !line.trim().isEmpty()) {
                                            val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

                                            //Add custom checks for variables that need a type ident ie F
                                            when {
                                                /*Case: 1*/curLine[0] == "FLASH_MAX_ALPHA" -> {
                                                cfgfiletext += curLine[0] + " = " + engine.eval(curLine[0]) + "F" + System.lineSeparator()
                                            }

                                                /*Case: 2*/     curLine[0] == "CFG_NAME" -> {
                                                cfgfiletext += curLine[0] + " = \"" + engine.eval(curLine[0]) + "\"" + System.lineSeparator()
                                            }
                                                /*Case: 3*/     file.name == "GunAimOverride.kts" -> {
                                                val curOverrideWep = engine.eval(curLine[0]) as kotlin.DoubleArray
                                                cfgfiletext += curLine[0] + " = " + curOverrideWep.contentToString() + System.lineSeparator()
                                            }
                                                /*Case: Else*/  else -> {
                                                cfgfiletext += curLine[0] + " = " + engine.eval(curLine[0]) + System.lineSeparator()
                                            }
                                            }

                                            cfgfiletext = cfgfiletext.replace("[", "doubleArrayOf(")
                                            cfgfiletext = cfgfiletext.replace("]", ")")

                                        }
                                    }
                                }
                            }

                            //cleanup later
                            Files.delete(cfgFile.toPath())
                            Files.createFile(cfgFile.toPath())
                            Files.write(cfgFile.toPath(), cfgfiletext.toByteArray(), StandardOpenOption.WRITE)
                            engine = ScriptEngineManager().getEngineByName("kotlin")
                            FileReader(cfgFile).readLines().forEach { line ->
                                engine.eval(line)
                            }

                            //Repeat for General.kts to save cfg name
                            var prevLines = ""
                            val genFile = File("settings\\General.kts")
                            FileReader(genFile).readLines().forEach { line ->
                                if (line.startsWith("CFG_NAME")) {
                                    val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

                                    prevLines += curLine[0] + " = \"" + engine.eval(curLine[0]) + "\"" + System.lineSeparator()

                                } else {
                                    prevLines += line + System.lineSeparator()
                                }
                            }
                            Files.delete(genFile.toPath())
                            Files.createFile(genFile.toPath())
                            Files.write(genFile.toPath(), prevLines.toByteArray(), StandardOpenOption.WRITE)

                            engine = ScriptEngineManager().getEngineByName("kotlin")
                            FileReader(cfgFile).use { engine.eval(it.readLines().joinToString("\n")) }

                            println("\n Saving complete! \n")
                            saving = false
                        }
                    }
                }
            }).setSize(200F, 200F)
            true
        }

        //Create Load Button
        //val loadButton = VisTextButton("Load: $CFG_NAME")
        Tooltip.Builder("Load the previously saved configuration from the cfg file").target(loadButton).build()
        loadButton.changed { _, _ ->
            if (!saving) {
                val cfgFile = File("settings\\cfg.kts")
                if (!cfgFile.exists()) {
                    Dialogs.showErrorDialog(App.menuStage, "Error", "cfg.kts not found, save your configuration first!")
                } else {
                    GlobalScope.launch {
                        println("\n Loading! \n")
                        //FileReader(cfgFile).use { engine.eval(it.readLines().joinToString("\n"))}
                        engine = ScriptEngineManager().getEngineByName("kotlin")
                        FileReader(cfgFile).use { engine.eval(it.readLines().joinToString("\n")) }
                        UIUpdate()
                        println("\n Loading complete! \n")
                    }
                }
            }
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
                        var prevLines = ""
                        if (file.name != "cfg.kts" && file.name != "Advanced.kts" && file.name != "hitsound.mp3") {
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
                                        prevLines += curLine[0] + " = " + engine.eval(curLine[0]) + "F" + System.lineSeparator()
                                    }
                                        /*Case: 2*/     curLine[0] == "CFG_NAME" -> {
                                        prevLines += curLine[0] + " = \"" + engine.eval(curLine[0]) + "\"" + System.lineSeparator()
                                    }
                                        /*Case: 3*/     file.name == "GunAimOverride.kts" -> {
                                        val curOverrideWep = engine.eval(curLine[0]) as kotlin.DoubleArray; prevLines += curLine[0] + " = " + curOverrideWep.contentToString() + System.lineSeparator()
                                    }
                                        /*Case: Else*/  else -> {
                                        prevLines += curLine[0] + " = " + engine.eval(curLine[0]) + System.lineSeparator()
                                    }
                                    }

                                } else {
                                    prevLines += line + System.lineSeparator()
                                }
                            }
                            prevLines = prevLines.replace("[", "doubleArrayOf(")
                            prevLines = prevLines.replace("]", ")")

                            Files.delete(file.toPath())
                            Files.createFile(file.toPath())
                            Files.write(file.toPath(), prevLines.toByteArray(), StandardOpenOption.WRITE)
                        }
                    }
                    loadSettings()
                    println("\n Saving complete! \n")
                    saving = false
                }
            }
            true
        }

        table.add(menuAlpha).row() //width 250F
        table.add(saveButton).row()
        table.add(loadButton).row()
        table.add(saveCurConfig).row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Options"
    }
}