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
                curLocale[curLine[0]] = curLine[2].reversed()
            }
        }
    }
}

//We do a little using later :wink:
val missedLocales = mutableListOf<String>()

fun String.locale(backupText: String? = null): String {
    if (!this.startsWith("L_")) return backupText?: this

    val lc = curLocale[this]

    return if (lc.isEmpty()) {
        if (!missedLocales.contains(this)) {
            if (dbg) {
                println("[DEBUG] missing locale for $this")
                missedLocales.add(this)
            }
        }

        //Use backup :smirk:
        //TODO

        backupText?: this
    } else {
        lc
    }
}

fun CharSequence?.locale(): String {
    return this.toString().locale()
}