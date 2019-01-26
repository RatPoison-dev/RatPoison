package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSlider
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.App
import rat.poison.SETTINGS_DIRECTORY
import rat.poison.settings.*
import rat.poison.ui.UIUpdate
import rat.poison.ui.changed
import rat.poison.utils.Dojo
import rat.poison.utils.Dojo.engine
import java.io.File
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.StandardOpenOption

class Options : Tab(false, false) {
    private val table = VisTable(true)

    init {
        //Create UI_Alpha Slider
        val menuAlpha = VisTable()
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
        val saveButton = VisTextButton("Save Current Configuration")
        saveButton.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                val cfgfile = File("settings" + "\\" + "cfg.kts")
                var cfgfiletext = ""

                if (!cfgfile.exists()) {
                    cfgfile.createNewFile()
                }

                //Add Imports
                cfgfiletext += "import rat.poison.settings.*" + System.lineSeparator()
                cfgfiletext += "import rat.poison.game.Color" + System.lineSeparator() + System.lineSeparator()

                File(SETTINGS_DIRECTORY).listFiles().forEach { file ->
                    if (file.name != "cfg.kts" && file.name != "sickomode.kts" && file.name != "Advanced.kts") {
                        println(file)
                        FileReader(file).readLines().forEach { line ->
                            if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith(" *") && !line.startsWith("*") && !line.trim().isEmpty()) {
                                val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

                                //Add custom checks for variables that need a type ident ie F
                                when {
                                    /*Case: 1*/     curLine[0] == "FLASH_MAX_ALPHA" -> {cfgfiletext += curLine[0] + " = " + engine.eval(curLine[0]) + "F" + System.lineSeparator() }
                                    /*Case: Else*/  else -> { cfgfiletext += curLine[0] + " = " + engine.eval(curLine[0]) + System.lineSeparator() }
                                }

                            }
                        }
                    }
                }

                val saveFile = File("settings\\cfg.kts")
                Files.delete(saveFile.toPath())
                Files.createFile(saveFile.toPath())
                Files.write(saveFile.toPath(), cfgfiletext.toByteArray(), StandardOpenOption.WRITE)
                println("\n Saving complete! \n")
            }
        }

        //Create Load Button
        val loadButton = VisTextButton("Load Previously Saved Configuration")
        loadButton.changed { _, _ ->
            val cfgfile = File("settings\\cfg.kts")
            if (!cfgfile.exists()) {
                Dialogs.showErrorDialog(App.stage, "Error", "cfg.kts not found, save your configuration first!")
            }
            else{
                FileReader(cfgfile).use { Dojo.script(it.readLines().joinToString("\n"))}
                UIUpdate()
                println("\n Loading complete! \n")
            }
        }

        //Create SickoMode Button
        val sickoMode = VisTextButton("Activate Sicko Mode")
        sickoMode.changed { _, _ ->
            Dojo.script(FileReader(SETTINGS_DIRECTORY + "\\sickomode.kts").readLines().joinToString("\n"))
            UIUpdate()
        }

        //Create Save Current Config To Default
        val saveCurConfig = VisTextButton("Save Current Config To Default Settings")
        saveCurConfig.changed { _, _ ->
            //Files.write(File("settings\\"))
            //val fileDir = "settings\\Aim.kts"
            File(SETTINGS_DIRECTORY).listFiles().forEach { file ->
                var prevLines = ""
                if (file.name != "cfg.kts" && file.name != "sickomode.kts" && file.name != "Advanced.kts") {
                    FileReader(file).readLines().forEach { line ->
                        if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith(" *") && !line.startsWith("*") && !line.trim().isEmpty()) {
                            val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

                            //Add custom checks for variables that need a type ident ie F
                            when {
                                /*Case: 1*/     curLine[0] == "FLASH_MAX_ALPHA" -> {prevLines += curLine[0] + " = " + engine.eval(curLine[0]) + "F" + System.lineSeparator(); println("called flash alpha") }
                                /*Case: Else*/  else -> { prevLines += curLine[0] + " = " + engine.eval(curLine[0]) + System.lineSeparator(); println("called else") }
                            }

                        } else {
                            prevLines += line + System.lineSeparator()
                        }
                    }

                    //Wipe file? Had problems with last line staying
                    Files.delete(file.toPath())
                    Files.createFile(file.toPath())
                    Files.write(file.toPath(), prevLines.toByteArray(), StandardOpenOption.WRITE)
                }
            }
            println("\n Saving complete! \n")
        }

        table.add(menuAlpha).row() //width 250F
        table.add(saveButton).row()
        table.add(loadButton).row()
        table.add(sickoMode).row()
        table.add(saveCurConfig).row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Options"
    }
}