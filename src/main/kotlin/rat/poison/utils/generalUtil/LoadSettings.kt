package rat.poison.utils.generalUtil

import rat.poison.curLocale
import rat.poison.curSettings
import rat.poison.settingsLoaded
import rat.poison.utils.Settings
import java.io.File
import java.io.FileReader
import kotlin.text.Charsets.UTF_8

fun loadSettingsFromFiles(fileDir: String, specificFile: Boolean = false) {
    println("Loading settings... "  + if (specificFile) { fileDir } else "")
    setupValidSettings()

    settingsLoaded = false
    if (specificFile) {
        FileReader(File(fileDir)).readLines().forEach { line ->
            if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith("\"") && !line.startsWith(" *") && !line.startsWith("*") && line.trim().isNotEmpty()) {
                val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

                if (curLine.size == 3) {
                    if (validateSetting(curLine[0], curLine[2])) {
                        curSettings[curLine[0]] = curLine[2]
                    }
                } else {
                    println("Invalid Setting: $curLine")
                }
            }
        }
    } else {
        File(fileDir).listFiles()?.forEach { file ->
            if (file.name != "CFGS" && file.name != "hitsounds" && file.name != "NadeHelper" && file.name != "Data" && file.name != "Localizations" && file.name.contains(".txt")) {
                FileReader(file).readLines().forEach { line ->
                    if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith(" *") && !line.startsWith("*") && line.trim().isNotEmpty()) {
                        val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

                        if (curLine.size == 3) {
                            if (validateSetting(curLine[0], curLine[2])) {
                                curSettings[curLine[0]] = curLine[2]
                            }
                        } else {
                            println("Invalid Setting: $curLine")
                        }
                    }
                }
            }
        }
    }
    settingsLoaded = true
    println("Settings loaded")
}

fun loadLocale(fileDir: String) {
    File(fileDir).readLines(UTF_8).forEach { line ->
        if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith("\"") && !line.startsWith(" *") && !line.startsWith("*") && line.trim().isNotEmpty()) {
            val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

            if (curLine.size == 3) {
                curLocale[curLine[0]] = curLine[2]
            } else {
                println("Debug: Locale invalid -- $curLine")
            }
        }
    }
}

//fuck a beat i was tryna beat a case
//bitch i did the race
//vroom
fun validateSetting(settingName: String, value: String): Boolean {
    var valid = true
    val inpValue = value.uppercase()


    if (!validSettingsMap["SKIP"].stringToList().contains(settingName)) {
        if (settingName.contains("AIM") && settingName.contains("BONE") && !settingName.contains("KEY")) { //Bones
            if (!validSettingsMap["BONE"].stringToList().contains(inpValue)) {
                valid = false
            }
        } else if (settingName.contains("BOX") && settingName.contains("POS")) {
            if (!validSettingsMap["BOX_POS"].stringToList().contains(inpValue)) {
                valid = false
            }
        } else if (settingName.contains("GLOW") && settingName.contains("TYPE")) {
            if (!validSettingsMap["GLOW_TYPE"].stringToList().contains(inpValue)) {
                valid = false
            }
        } else if (settingName.contains("FOV_TYPE")) {
            if (!validSettingsMap["FOV_TYPE"].stringToList().contains(inpValue)) {
                valid = false
            }
        } else if (value.contains("oWeapon")) {
            val size = value.replace("oWeapon(", "").replace(")", "").split(", ").size

            if (size != 25) {
                println("Debug: Setting invalid: $settingName has incorrect size of $size expected 25")
                return false
            }
        }
    }

    if (!valid) {
        println("Debug: Setting invalid: $settingName has incorrect value of $value")
    }
    return valid
}

val validSettingsMap = Settings()
fun setupValidSettings() {
    validSettingsMap["SKIP"] = listOf("AIM_BONE", "FORCE_AIM_BONE")
    validSettingsMap["BONE"] = listOf("HEAD", "NECK", "CHEST", "STOMACH", "NEAREST", "RANDOM")
    validSettingsMap["BOX_POS"] = listOf("LEFT", "RIGHT", "TOP", "BOTTOM")
    validSettingsMap["GLOW_TYPE"] = listOf("NORMAL", "MODEL", "VISIBLE", "VISIBLE-FLICKER")
    validSettingsMap["FOV_TYPE"] = listOf("STATIC", "DISTANCE")
}

//move elsewhere
fun String.stringToList(): List<String> {
    val list = mutableListOf<String>()
    val strList = this.replace("[", "").replace("]", "").replace(",", "").split(" ")

    for (i in strList) {
        list.add(i)
    }

    return list
}