package rat.poison.utils

import rat.poison.SETTINGS_DIRECTORY
import rat.poison.curLocale
import rat.poison.dbg
import java.io.File
import java.io.FileReader

fun loadLocale() {
    val localeFile = File("$SETTINGS_DIRECTORY\\Locales\\EN.locale")

    FileReader(localeFile).readLines().forEach { line ->
        if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith("\"") && !line.startsWith(" *") && !line.startsWith("*") && line.trim().isNotEmpty()) {
            val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

            if (curLine.size == 3) {
                curLocale[curLine[0]] = curLine[2]//.reversed()
            }
        }
    }
}

//We do a little using later :wink:
val missedLocales = mutableListOf<String>()

fun locale(string: String): String {
    if (!string.startsWith("L_")) return string

    val lc = curLocale[string]

    return lc.ifEmpty {
        if (!missedLocales.contains(string)) {
            if (dbg) {
                println("[DEBUG] missing locale for $string")
                missedLocales.add(string)
            }
        }

        //Use backup :smirk:
        //TODO

        string
    }
}

fun String.locale(backupText: String? = null): String {
    if (!this.startsWith("L_")) return backupText?: this

    val lc = curLocale[this]

    return lc.ifEmpty {
        if (!missedLocales.contains(this)) {
            if (dbg) {
                println("[DEBUG] missing locale for $this")
                missedLocales.add(this)
            }
        }

        //Use backup :smirk:
        //TODO

        backupText ?: this
    }
}

fun CharSequence?.locale(): String {
    return this.toString().locale()
}