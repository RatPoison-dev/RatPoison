package rat.poison.utils.generalUtil

import rat.poison.curLocale
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.settingsLoaded
import java.io.File
import java.io.FileReader
import kotlin.text.Charsets.UTF_8

fun loadSettingsFromFiles(fileDir: String, specificFile: Boolean = false) {
    println("Loading settings... " + if (dbg) { "$fileDir $specificFile" } else { "" })
    settingsLoaded = false
    if (specificFile) {
        FileReader(File(fileDir)).readLines().forEach { line ->
            if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith("\"") && !line.startsWith(" *") && !line.startsWith("*") && line.trim().isNotEmpty()) {
                val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

                if (curLine.size == 3) {
                    curSettings[curLine[0]] = curLine[2]
                } else {
                    println("Debug: Setting invalid -- $curLine")
                }
            }
        }
    } else {
        File(fileDir).listFiles()?.forEach { file ->
            if (file.name != "CFGS" && file.name != "hitsounds" && file.name != "NadeHelper" && file.name != "SkinInfo" && file.name.contains(".txt")) {
                FileReader(file).readLines().forEach { line ->
                    if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith(" *") && !line.startsWith("*") && line.trim().isNotEmpty()) {
                        val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

                        if (curLine.size == 3) {
                            curSettings[curLine[0]] = curLine[2]
                        } else {
                            println("Debug: Setting invalid -- $curLine")
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