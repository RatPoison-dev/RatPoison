package rat.poison.utils

import rat.poison.SETTINGS_DIRECTORY
import java.io.File

fun loadMigration() {
    val file = File("$SETTINGS_DIRECTORY/CFGS/default_migration.cfg")
    if (file.exists()) {
        try {
            loadCFG("default_migration", true)
            println("Default migration config found and successfully loaded.")
        }
        catch (e: Exception) {
            println("Default migration config found and wasn't successfully loaded.")
        }
    }
}