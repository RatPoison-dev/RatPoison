package rat.poison.utils

import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.util.dialog.Dialogs
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rat.poison.*
import rat.poison.overlay.App
import rat.poison.overlay.opened
import rat.poison.ui.uiTabs.updateWindows
import rat.poison.ui.uiUpdate
import rat.poison.ui.uiWindows.configsTab
import rat.poison.utils.extensions.toBitString
import rat.poison.utils.generalUtil.loadSettingsFromFiles
import rat.poison.utils.generalUtil.loadSkinSettings
import java.io.File
import java.io.FileReader
import java.nio.file.Files

var saving = false

fun saveDefault() {
    if (!saving) {
        GlobalScope.launch {
            saving = true
            println("\nSaving!\n")
            curSettings["CROSSHAIR_ARRAY"] = crosshairArray.toString()

            File(SETTINGS_DIRECTORY).listFiles()?.forEach { file ->
                val sbLines = StringBuilder()
                if (!file.isDirectory) {
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
                    sbLines.lines().forEach {file.appendText(if (!firstLine) { firstLine = true; it } else if (!it.isEmpty()) "\n" + it else "\n")}
                    sbLines.clear()
                }
            }

            println("\nSaving Complete!\n")
            saving = false
        }
    }
}

fun loadSkinCFG(realCfgFileName: String) {
    var cfgFileName = realCfgFileName
    if (!saving) {
        cfgFileName = cfgFileName.replace(".cfg", "")
        val cfgFile = File("$SETTINGS_DIRECTORY\\skinCFGS\\$cfgFileName.cfg")
        if (!cfgFile.exists() && opened) {
            Dialogs.showOKDialog(
                App.menuStage,
                "Error",
                "${"FILE_NOT_FOUND_ERROR"}\n \"${cfgFileName}\""
            )
        } else {
            saving = true
            println("\nLoading Skin Changer config\n")

            loadSkinSettings("$SETTINGS_DIRECTORY\\skinCFGS\\$cfgFileName.cfg")

            println("Loading Complete!\n")
            saving = false
        }
    }
}

fun loadCFG(realCfgFileName: String, deleteCfgAfterLoad: Boolean = false) {
    var cfgFileName = realCfgFileName
    if (!saving) {
        cfgFileName = cfgFileName.replace(".cfg", "")

        val cfgFile = File("$SETTINGS_DIRECTORY\\CFGS\\$cfgFileName.cfg")
        if (!cfgFile.exists() && opened) {
            Dialogs.showOKDialog(App.menuStage, "Error", "${"FILE_NOT_FOUND_ERROR"}\n \"${cfgFileName}\"")
        } else {
            saving = true
            println("Loading\n")
            GlobalScope.launch {
                loadSettingsFromFiles("$SETTINGS_DIRECTORY\\CFGS\\$cfgFileName.cfg", true)
                uiUpdate()
                updateWindows()
                println("Loading Complete!\n")
                LOADED_CONFIG = cfgFileName

                if (opened) {
                    App.uiMenu.updateTitle()
                    App.uiMenu.changeAlpha()
                }
                if (deleteCfgAfterLoad) { cfgFile.delete() }
                saving = false
            }
        }
    } else {
        Dialogs.showOKDialog(App.menuStage, "Error", "${"SAVING_NOW_ERROR"}\n \"${cfgFileName}\"")
    }
}

fun saveWindows() {
    if (VisUI.isLoaded()) {
        curSettings["BOMB_TIMER_X"] = App.uiBombWindow.x
        curSettings["BOMB_TIMER_Y"] = App.uiBombWindow.y
        curSettings["BOMB_TIMER_ALPHA"] = App.uiBombWindow.color.a

        curSettings["SPECTATOR_LIST_X"] = App.uiSpecList.x
        curSettings["SPECTATOR_LIST_Y"] = App.uiSpecList.y
        curSettings["SPECTATOR_LIST_ALPHA"] = App.uiSpecList.color.a

        curSettings["KEYBINDS_X"] = App.uiKeybinds.x
        curSettings["KEYBINDS_Y"] = App.uiKeybinds.y
        curSettings["KEYBINDS_ALPHA"] = App.uiKeybinds.color.a
    }
}

fun saveSkinCFG(cfgFileName: String) {
    if (!saving) {
        saving = true
        println("\nSaving Skin Changer config!")
        val skinCfgDir = File("$SETTINGS_DIRECTORY\\skinCFGS")
        if (!skinCfgDir.exists()) {
            Files.createDirectory(skinCfgDir.toPath())
        }
        val cfgFile = File("$SETTINGS_DIRECTORY\\skinCFGS\\$cfgFileName.cfg")
        if (!cfgFile.exists()) {
            cfgFile.createNewFile()
        }

        val sbLines = StringBuilder()
        skSettings.savedValues.keys.forEach {
            sbLines.append("$it = ${skSettings[it]}\n")
        }

        Files.delete(cfgFile.toPath()) //Replace with cfgFile. ??
        Files.createFile(cfgFile.toPath())

        var firstLine = false
        sbLines.lines().forEach {cfgFile.appendText(if (!firstLine) { firstLine = true; it } else if (it.isNotBlank()) "\n" + it else "\n")}
        sbLines.clear()

        println("\nSaving Complete!\n")
        if (VisUI.isLoaded()) {
            configsTab.updateSkinCfgList()
        }
        saving = false
    }
}

fun saveCFG(cfgFileName: String) {
    if (!saving) {
        saving = true
        saveWindows()

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
            if (!file.isDirectory) {
                FileReader(file).readLines().forEach { line ->
                    if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith(" *") && !line.startsWith("*") && line.trim().isNotEmpty()) {
                        val tempCurLine = line.trim().split(" ".toRegex(), 3) //Separate line into 'VARIABLE=VALUE' //no spaces bc of trim()
                        if (tempCurLine[0] == "CROSSHAIR_ARRAY") return@forEach
                        sbLines.append(tempCurLine[0] + " = " + curSettings[tempCurLine[0]] + "\n") //add spaces back
                    }
                }
            }
        }
        sbLines.append("CROSSHAIR_ARRAY = ")
        for (i in 0..crosshairArray.length()) {
            sbLines.append(crosshairArray[i].toBitString())
        }
        sbLines.append("\n")

        Files.delete(cfgFile.toPath()) //Replace with cfgFile. ??
        Files.createFile(cfgFile.toPath())

        var firstLine = false
        sbLines.lines().forEach {cfgFile.appendText(if (!firstLine) { firstLine = true; it } else if (it.isNotBlank()) "\n" + it else "\n")}
        sbLines.clear()

        println("\nSaving Complete!\n")
        if (VisUI.isLoaded()) {
            configsTab.updateCFGList()
        }
        saving = false
    }
}

fun deleteCFG(cfgFileName: String, skinCfg: Boolean = false) {
    cfgFileName.replace(".cfg", "")

    val cfgFile = if (!skinCfg) File("$SETTINGS_DIRECTORY\\CFGS\\$cfgFileName.cfg") else File("$SETTINGS_DIRECTORY\\skinCFGS\\$cfgFileName.cfg")
    if (cfgFile.exists()) {
        cfgFile.delete()
    }
    if (VisUI.isLoaded()) {
        if (!skinCfg) configsTab.updateCFGList() else configsTab.updateSkinCfgList()
    }
    println("Deleted $cfgFileName\n")
}