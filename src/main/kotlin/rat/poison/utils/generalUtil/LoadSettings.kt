package rat.poison.utils.generalUtil

import org.apache.commons.lang3.StringUtils.isNumeric
import rat.poison.*
import rat.poison.scripts.aim.numToBone
import rat.poison.utils.extensions.upper
import java.io.File
import java.io.FileReader
import kotlin.text.Charsets.UTF_8

private val keybindsSettings = listOf("AIM_TOGGLE_KEY", "FORCE_AIM_KEY", "FORCE_AIM_BONE_KEY", "TRIGGER_KEY", "VISUALS_TOGGLE_KEY", "D_SPAM_KEY", "W_SPAM_KEY", "MENU_KEY")

fun loadSettingsFromFiles(fileDir: String, specificFile: Boolean = false) {
    println("Loading settings... "  + if (specificFile) { fileDir } else "")
    val overloadKeybinds = curSettings.bool["OVERLOAD_KEYBINDS"]
    settingsLoaded = false
    if (specificFile) {
        FileReader(File(fileDir)).readLines().forEach { line ->
            if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith("\"") && !line.startsWith(" *") && !line.startsWith("*") && line.trim().isNotEmpty()) {
                val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

                if (curLine.size == 3) {
                    if (validateSetting(curLine[0], curLine[2]) && (!overloadKeybinds || curLine[0] !in keybindsSettings)) {
                        curSettings[curLine[0]] = curLine[2]
                    }
                } else {
                    println("Invalid Setting: $curLine")
                }
            }
        }
    } else {
        File(fileDir).listFiles()?.forEach { file ->
            if (!file.isDirectory && file.name.contains(".txt")) {
                FileReader(file).readLines().forEach { line ->
                    if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith(" *") && !line.startsWith("*") && line.trim().isNotEmpty()) {
                        val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

                        if (curLine.size == 3) {
                            if (validateSetting(curLine[0], curLine[2]) && (!overloadKeybinds || curLine[0] !in keybindsSettings)) {
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
    val arr = curSettings["RCROSSHAIR_BUILDER_ARRAY"]
    for (i in arr.indices) {
        crosshairArray[i] = arr[i].strToBool()
    }
    curSettings["OVERLOAD_KEYBINDS"] = overloadKeybinds
    settingsLoaded = true
    println("Settings loaded")
}

fun loadSkinSettings(fileDir: String) {
    File(fileDir).readLines(UTF_8).forEach { line ->
        if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith("\"") && !line.startsWith(" *") && !line.startsWith("*") && line.trim().isNotEmpty()) {
            val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

            if (curLine.size == 3) {
                skSettings[curLine[0]] = curLine[2]
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
    val inpValue = value.upper()


    if (!validSettingsMap["SKIP"]!!.contains(settingName)) {
        if (settingName.contains("AIM") && settingName.contains("BONE") && !settingName.contains("KEY")) { //Bones
            if (!validSettingsMap["BONE"]!!.containsAny(inpValue.stringToList())) {
                valid = false
            }
        } else if (settingName.contains("BOX") && settingName.contains("POS")) {
            if (!validSettingsMap["BOX_POS"]!!.contains(inpValue)) {
                valid = false
            }
        } else if (settingName.contains("GLOW") && settingName.contains("TYPE")) {
            if (!validSettingsMap["GLOW_TYPE"]!!.contains(inpValue)) {
                valid = false
            }
        } else if (settingName.contains("FOV_TYPE")) {
            if (!validSettingsMap["FOV_TYPE"]!!.contains(inpValue)) {
                valid = false
            }
        } else if (value.contains("oWeapon")) {
            val size = value.replace("oWeapon(", "").replace(")", "").split(", ").size
            //replace with valid
            var tStr = value
            tStr = tStr.replace("oWeapon(", "").replace(")", "")
            val tSA = tStr.split(", ") //temp String Array
            val weapon = oWeapon()

            if (size > 6 && tSA.pull(5).stringToList(";").all { isNumeric(it) }) {
                weapon.tAimBone = tSA.pull(5).stringToIntList().map { it.numToBone() }
            }

            if (size > 7 && tSA.pull(6).stringToList(";").all { isNumeric(it) }) {
                weapon.tForceBone = tSA.pull(6).stringToIntList().map { it.numToBone() }
            }

            weapon.apply {
                tOverride = if (size > 1) tSA.pull(0).safeToBool(defaultValue = tOverride) else tOverride
                tFRecoil = if (size > 2) tSA.pull(1).safeToBool(defaultValue = tFRecoil) else tFRecoil
                tOnShot = if (size > 3) tSA.pull(2).safeToBool(defaultValue = tOnShot) else tOnShot
                tFlatAim = if (size > 4) tSA.pull(3).safeToBool(defaultValue = tFlatAim) else tFlatAim
                tPathAim = if (size > 5) tSA.pull(4).safeToBool(defaultValue = tPathAim) else tPathAim
                tAimFov = if (size > 7) tSA.pull(6).safeToFloat(defaultValue = tAimFov) else tAimFov
                tAimSmooth = if (size > 8) tSA.pull(7).safeToInt(defaultValue = tAimSmooth) else tAimSmooth
                tPerfectAim = if (size > 9) tSA.pull(8).safeToBool(defaultValue = tPerfectAim) else tPerfectAim
                tPAimFov = if (size > 10) tSA.pull(9).safeToFloat(defaultValue = tPAimFov) else tPAimFov
                tPAimChance = if (size > 11) tSA.pull(10).safeToInt(defaultValue = tPAimChance) else tPAimChance
                tScopedOnly = if (size > 12) tSA.pull(11).safeToBool(defaultValue = tScopedOnly) else tScopedOnly
                tAimAfterShots = if (size > 13) tSA.pull(12).safeToInt(defaultValue = tAimAfterShots) else tAimAfterShots
                tBoneTrig = if (size > 14) tSA.pull(13).safeToBool(defaultValue = tBoneTrig) else tBoneTrig
                tBTrigAim = if (size > 15) tSA.pull(14).safeToBool(defaultValue = tBTrigAim) else tBTrigAim
                tBTrigInCross = if (size > 16) tSA.pull(15).safeToBool(defaultValue = tBTrigInCross) else tBTrigInCross
                tBTrigInFov = if (size > 17) tSA.pull(16).safeToBool(defaultValue = tBTrigInFov) else tBTrigInFov
                tBTrigBacktrack = if (size > 18) tSA.pull(17).safeToBool(defaultValue = tBTrigBacktrack) else tBTrigBacktrack
                tBTrigFov = if (size > 19) tSA.pull(18).safeToFloat(defaultValue = tBTrigFov) else tBTrigFov
                tBTrigInitDelay = if (size > 20) tSA.pull(19).safeToInt(defaultValue = tBTrigInitDelay) else tBTrigInitDelay
                tBTrigPerShotDelay = if (size > 21) tSA.pull(20).safeToInt(defaultValue = tBTrigPerShotDelay) else tBTrigPerShotDelay
                tBacktrack = if (size > 22) tSA.pull(21).safeToBool(defaultValue = tBacktrack) else tBacktrack
                tBTMS = if (size > 23) tSA.pull(22).safeToInt(defaultValue = tBTMS) else tBTMS
                tAutowep = if (size > 24) tSA.pull(23).safeToBool(defaultValue = tAutowep) else tAutowep
                tAutowepDelay = if (size >= 25) tSA.pull(24).safeToInt(defaultValue = tAutowepDelay) else tAutowepDelay
            }
            curSettings[settingName] = weapon.toString()
            return false
        }
    }

    if (!valid) {
        println("Debug: Setting invalid: $settingName has incorrect value of $value")
    }
    return valid
}

val validSettingsMap = mutableMapOf(Pair("SKIP", listOf("AIM_BONE", "FORCE_AIM_BONE")), Pair("BONE", listOf("HEAD", "NECK", "CHEST", "STOMACH", "NEAREST", "PELVIS", "RANDOM")), Pair("BOX_POS", listOf("LEFT", "RIGHT", "TOP", "BOTTOM")), Pair("GLOW_TYPE", listOf("NORMAL", "MODEL", "VISIBLE", "VISIBLE-FLICKER")), Pair("FOV_TYPE", listOf("STATIC", "DISTANCE")))
