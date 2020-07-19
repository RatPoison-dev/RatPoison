package rat.poison.scripts

import rat.poison.SETTINGS_DIRECTORY
import rat.poison.curLocale
import rat.poison.curLocale
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.rankName
import rat.poison.scripts.esp.disableAllEsp
import rat.poison.ui.tabs.deleteCFG
import rat.poison.ui.tabs.loadCFG
import rat.poison.ui.tabs.saveCFG
import rat.poison.ui.tabs.saveDefault
import rat.poison.utils.extensions.roundNDecimals
import rat.poison.utils.generalUtil.loadSettingsFromFiles
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.util.*
import javax.script.ScriptException
import kotlin.system.exitProcess

fun scanner() {
    println("${curLocale["TYPE_HELP"]}\n")

    //Major optimization, needs to be fixed later, probably move this massive dump elsewhere
    val scanner = Scanner(System.`in`)
    while (!Thread.interrupted()) {
        val line = scanner.nextLine().trim()
        when {
            line.startsWith(curLocale["HELP_COMMAND"]) -> {
                if (line == curLocale["HELP_COMMAND"]) {
                    println("\n${curLocale["SCANNER_HELP_MESSAGE"]}\n")
                } else {
                    val key = "${line.split(" ".toRegex(), 2)[1].toUpperCase()}_COMMAND_HELP_MESSAGE"
                    if (curLocale[key] != "") {
                        println("\n${curLocale[key]}\n")
                    }
                }
            }
            line.equals(curLocale["EXIT_COMMAND"], true) -> {
                disableAllEsp()
                Thread.sleep(1000)
                exitProcess(0)
            }
            line.equals(curLocale["RELOAD_COMMAND"], true) -> {
                println(); loadSettingsFromFiles(SETTINGS_DIRECTORY)
            }
            line.equals(curLocale["LIST_COMMAND"], true) -> {
                print("\n----${curLocale["SETTINGS_FILES"]}----\n")
                File(SETTINGS_DIRECTORY).listFiles()?.forEach {
                    if (it.name.contains(".txt")) {
                        println(it)
                    }
                }
                print("\n----${curLocale["CFGS"]}----\n")
                File("$SETTINGS_DIRECTORY\\CFGS").listFiles()?.forEach {
                    println(it)
                }
            }
            line.startsWith(curLocale["READ_COMMAND"]) -> { //Read file's variables
                println()
                try {
                    File(SETTINGS_DIRECTORY + "\\" + line.trim().split(" ".toRegex())[1] + ".txt").readLines().forEach {
                        if (!it.startsWith("/") && !it.startsWith("*") && !it.startsWith(" ") && it.trim().isNotEmpty() && !it.startsWith("import")) {
                            println(it)
                        }
                    }
                } catch (e: Exception) {
                    println(curLocale["FILE_NOT_FOUND_ERROR"])
                }
            }
            line.startsWith("set") -> { //Set variable, instance use only
                println()
                try {
                    println(line.split(" ".toRegex(), 2)[1])
                    println("Set " + line.trim().split(" ".toRegex(), 2)[1])
                } catch (e: Exception) {
                    println("Invalid variable")
                }
            }
            line.startsWith(curLocale["WRITE_COMMAND"]) -> {
                val fileDir = SETTINGS_DIRECTORY + "\\" + line.trim().split(" ".toRegex())[1] + ".txt"
                val command = line.trim().split(" ".toRegex(), 3)[2]
                var prevFile = ""
                println()
                try {
                    try { //Check for file + variable
                        File(fileDir).readLines().forEach {
                            prevFile = if (!it.startsWith("/") && !it.startsWith("*") && !it.startsWith(" ") && it.trim().isNotEmpty() && !it.startsWith("import") && it.startsWith(command.split(" ".toRegex(), 3)[0])) {
                                prevFile + command + System.lineSeparator()
                            } else {
                                prevFile + it + System.lineSeparator()
                            }
                        }

                        println("${curLocale["WRITING_TO"]} $fileDir")
                        println("${curLocale["SET"]} $command")
                        Files.write(File(fileDir).toPath(), prevFile.toByteArray(), StandardOpenOption.WRITE)
                        println(curLocale["RELOADING_SETTINGS"])
                        loadSettingsFromFiles(SETTINGS_DIRECTORY)
                        println()
                    } catch (e: FileNotFoundException) {
                        println(curLocale["FILE_NOT_FOUND_ERROR"])
                    }

                } catch (e: ScriptException) {
                    println(curLocale["INVALID_VARIABLE_OR_VALUE"])
                }
            }
            line.startsWith(curLocale["SAVE_COMMAND"]) -> {
                println()
                try {
                    val name = line.trim().split(" ".toRegex(), 2)[1]

                    if (name == curLocale["DEFAULT_CONFIG_NAME"]) {
                        saveDefault()
                    } else {
                        saveCFG(name)
                    }
                } catch (e: Exception) {
                    println(curLocale["INVALID_VARIABLE_OR_VALUE"])
                }
            }
            line.startsWith(curLocale["LOAD_COMMAND"]) -> {
                println()
                try {
                    val name = line.trim().split(" ".toRegex(), 2)[1]

                    loadCFG(name)
                } catch (e: Exception) {
                    println(curLocale["INVALID_VARIABLE_OR_VALUE"])
                }
            }
            line.startsWith(curLocale["DELETE_COMMAND"]) -> {
                println()
                try {
                    val name = line.trim().split(" ".toRegex(), 2)[1]

                    deleteCFG(name)
                } catch (e: Exception) {
                    println(curLocale["INVALID_VARIABLE_OR_VALUE"])
                }
            }
            line.startsWith(curLocale["RANKS_COMMAND"]) -> {
                println()
                println("${curLocale["RANKS_TEAM"]}   ${curLocale["RANKS_NAME"]}                             ${curLocale["RANKS_RANK"]}  ${curLocale["RANKS_KILLS"]} ${curLocale["RANKS_DEATHS"]} ${curLocale["RANKS_KD"]}  ${curLocale["RANKS_WINS"]}")
                println("====== ================================ ===== ===== ====== ==== =====")
                try {
                    forEntities(EntityType.CCSPlayer) {
                        val entity = it.entity

                        if (entity.onGround()) { //Change later
                            var entTeam = when (entity.team()) {
                                3L -> curLocale["CT"]
                                2L -> curLocale["T"]
                                else -> curLocale["UNDEFINED"]
                            }

                            var entName = entity.name()
                            var entRank = entity.rank().rankName()
                            var entKills = entity.kills().toString()
                            var entDeaths = entity.deaths().toString()
                            var entKD = when (entDeaths) {
                                "0" -> curLocale["UNDEFINED"]
                                else -> (entKills.toFloat() / entDeaths.toFloat()).roundNDecimals(2).toString()
                            }
                            var entWins = entity.wins().toString()

                            for (i in entTeam.length..5) {
                                entTeam += " "
                            }
                            for (i in entName.length..31) {
                                entName += " "
                            }
                            for (i in entRank.length..4) {
                                entRank += " "
                            }
                            for (i in entKills.length..4) {
                                entKills += " "
                            }
                            for (i in entDeaths.length..5) {
                                entDeaths += " "
                            }
                            for (i in entKD.length..3) {
                                entKD += " "
                            }
                            for (i in entWins.length..4) {
                                entWins += " "
                            }

                            println("$entTeam $entName $entRank $entKills $entDeaths $entKD $entWins")
                        }
                        false
                    }
                    println()
                } catch (e: Exception) {
                    println("Exception")
                }
            }
        }
    }
}