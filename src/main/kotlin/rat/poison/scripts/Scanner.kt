package rat.poison.scripts

import com.sun.jna.platform.win32.OaIdl
import rat.poison.SETTINGS_DIRECTORY
import rat.poison.curLocalization
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.rankName
import rat.poison.loadSettingsFromFiles
import rat.poison.scripts.esp.disableAllEsp
import rat.poison.ui.tabs.deleteCFG
import rat.poison.ui.tabs.loadCFG
import rat.poison.ui.tabs.saveCFG
import rat.poison.ui.tabs.saveDefault
import rat.poison.utils.extensions.roundNDecimals
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.util.*
import javax.script.ScriptException
import kotlin.system.exitProcess

fun scanner() {
    println("${curLocalization["TYPE_HELP"]}\n")

    //Major optimization, needs to be fixed later, probably move this massive dump elsewhere
    val scanner = Scanner(System.`in`)
    while (!Thread.interrupted()) {
        val line = scanner.nextLine().trim()
        when {
            line.startsWith(curLocalization["HELP_COMMAND"]) -> {
                if (line == curLocalization["HELP_COMMAND"]) {
                    println("\n${curLocalization["SCANNER_HELP_MESSAGE"]}\n")
                } else {
                    val key = "${line.split(" ".toRegex(), 2)[1].toUpperCase()}_COMMAND_HELP_MESSAGE"
                    if (curLocalization[key] != "") {
                        println("\n${curLocalization[key]}\n")
                    }
                }
            }
            line.equals(curLocalization["EXIT_COMMAND"], true) -> {
                disableAllEsp()
                Thread.sleep(1000)
                exitProcess(0)
            }
            line.equals(curLocalization["RELOAD_COMMAND"], true) -> {
                println(); loadSettingsFromFiles(SETTINGS_DIRECTORY)
            }
            line.equals(curLocalization["LIST_COMMAND"], true) -> {
                print("\n----${curLocalization["SETTINGS_FILES"]}----\n")
                File(SETTINGS_DIRECTORY).listFiles()?.forEach {
                    if (it.name.contains(".txt")) {
                        println(it)
                    }
                }
                print("\n----${curLocalization["CFGS"]}----\n")
                File("$SETTINGS_DIRECTORY\\CFGS").listFiles()?.forEach {
                    println(it)
                }
            }
            line.startsWith(curLocalization["READ_COMMAND"]) -> { //Read file's variables
                println()
                try {
                    File(SETTINGS_DIRECTORY + "\\" + line.trim().split(" ".toRegex())[1] + ".txt").readLines().forEach {
                        if (!it.startsWith("/") && !it.startsWith("*") && !it.startsWith(" ") && it.trim().isNotEmpty() && !it.startsWith("import")) {
                            println(it)
                        }
                    }
                } catch (e: Exception) {
                    println(curLocalization["FILE_NOT_FOUND_ERROR"])
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
            line.startsWith(curLocalization["WRITE_COMMAND"]) -> {
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

                        println("${curLocalization["WRITING_TO"]} $fileDir")
                        println("${curLocalization["SET"]} $command")
                        Files.write(File(fileDir).toPath(), prevFile.toByteArray(), StandardOpenOption.WRITE)
                        println(curLocalization["RELOADING_SETTINGS"])
                        loadSettingsFromFiles(SETTINGS_DIRECTORY)
                        println()
                    } catch (e: FileNotFoundException) {
                        println(curLocalization["FILE_NOT_FOUND_ERROR"])
                    }

                } catch (e: ScriptException) {
                    println(curLocalization["INVALID_VARIABLE_OR_VALUE"])
                }
            }
            line.startsWith(curLocalization["SAVE_COMMAND"]) -> {
                println()
                try {
                    val name = line.trim().split(" ".toRegex(), 2)[1]

                    if (name == curLocalization["DEFAULT_CONFIG_NAME"]) {
                        saveDefault()
                    } else {
                        saveCFG(name)
                    }
                } catch (e: Exception) {
                    println(curLocalization["INVALID_VARIABLE_OR_VALUE"])
                }
            }
            line.startsWith(curLocalization["LOAD_COMMAND"]) -> {
                println()
                try {
                    val name = line.trim().split(" ".toRegex(), 2)[1]

                    loadCFG(name)
                } catch (e: Exception) {
                    println(curLocalization["INVALID_VARIABLE_OR_VALUE"])
                }
            }
            line.startsWith(curLocalization["DELETE_COMMAND"]) -> {
                println()
                try {
                    val name = line.trim().split(" ".toRegex(), 2)[1]

                    deleteCFG(name)
                } catch (e: Exception) {
                    println(curLocalization["INVALID_VARIABLE_OR_VALUE"])
                }
            }
            line.startsWith(curLocalization["RANKS_COMMAND"]) -> {
                println()
                println("${curLocalization["RANKS_TEAM"]}   ${curLocalization["RANKS_NAME"]}                             ${curLocalization["RANKS_RANK"]}  ${curLocalization["RANKS_KILLS"]} ${curLocalization["RANKS_DEATHS"]} ${curLocalization["RANKS_KD"]}  ${curLocalization["RANKS_WINS"]}")
                println("====== ================================ ===== ===== ====== ==== =====")
                try {
                    forEntities(EntityType.ccsPlayer) {
                        val entity = it.entity

                        if (entity.onGround()) { //Change later
                            var entTeam = when (entity.team()) {
                                3L -> curLocalization["CT"]
                                2L -> curLocalization["T"]
                                else -> curLocalization["UNDEFINED"]
                            }

                            var entName = entity.name()
                            var entRank = entity.rank().rankName()
                            var entKills = entity.kills().toString()
                            var entDeaths = entity.deaths().toString()
                            var entKD = when (entDeaths) {
                                "0" -> curLocalization["UNDEFINED"]
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