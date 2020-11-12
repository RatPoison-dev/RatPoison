package rat.poison.scripts

import rat.poison.SETTINGS_DIRECTORY
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.rankName
import rat.poison.haltProcess
import rat.poison.scripts.visuals.disableAllEsp
import rat.poison.utils.deleteCFG
import rat.poison.utils.extensions.roundNDecimals
import rat.poison.utils.generalUtil.loadSettingsFromFiles
import rat.poison.utils.loadCFG
import rat.poison.utils.saveCFG
import rat.poison.utils.saveDefault
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.util.*
import javax.script.ScriptException
import kotlin.system.exitProcess

fun scanner() {
    println("Type help for options\n")

    //Major optimization, needs to be fixed later, probably move this massive dump elsewhere
    val scanner = Scanner(System.`in`)
    while (!Thread.interrupted()) {
        val line = scanner.nextLine().trim()
        when {
            line.startsWith("help") -> {
                if (line == "help") {
                    println("\nAvailable commands: help [command], exit, ranks, reload, list, read [file name], write [file name] [variable name] = [value], save [default/cfgname], load [cfgname], delete [cfgname], nadehelper [nadehelpername], namechanger [name]\n")
                } else {
                    when (line.split(" ".toRegex(), 2)[1]) {
                        "exit" -> println("\nCloses program and cmd\n")
                        "ranks" -> println("\nShows players, ranks, kills, deaths, kd, and wins")
                        "reload" -> println("\nReloads all settings files, is done automatically on write\n")
                        "list" -> println("\nLists all settings files\n")
                        "read" -> println("\nSyntax: read [file name] ; Replace [file name] with the file name, viewable from the list command, excluding .kts. Example: read General\n")
                        "write" -> println("\nSyntax: write [file name] [variable name] = [value] ; Replace [file name] with the file name, replace [variable name] with the name of the variable inside of the file from [file name], and replace [value] with the value for the variable\n")
                        "help" -> println("\nStandalone or Syntax: help [command] ; Replace [command] with the command listed in list\n")
                        "save" -> println("\nSave to default settings or to a config\n")
                        "load" -> println("\nLoad config\n")
                        "nadehelper" -> println("\nLoad nadehelper file\n")
                        "namechanger" -> println("\nTemporarily change your name")
                    }
                }
            }
            line.equals("exit", true) -> {
                haltProcess = true
                disableAllEsp()
                sendPacket(true)
                exitProcess(0)
            }
            line.equals("reload", true) -> {
                println(); loadSettingsFromFiles(SETTINGS_DIRECTORY)
            }
            line.equals("list", true) -> {
                print("\n----Settings Files----\n")
                File(SETTINGS_DIRECTORY).listFiles()?.forEach {
                    if (it.name.contains(".txt")) {
                        println(it)
                    }
                }
                print("\n----CFGS----\n")
                File("$SETTINGS_DIRECTORY\\CFGS").listFiles()?.forEach {
                    println(it)
                }
            }
            line.startsWith("read") -> { //Read file's variables
                println()
                try {
                    File(SETTINGS_DIRECTORY + "\\" + line.trim().split(" ".toRegex())[1] + ".txt").readLines().forEach {
                        if (!it.startsWith("/") && !it.startsWith("*") && !it.startsWith(" ") && it.trim().isNotEmpty() && !it.startsWith("import")) {
                            println(it)
                        }
                    }
                } catch (e: Exception) {
                    println("File not found, use list to see current files")
                }
            }
            line.startsWith("namechanger") -> {
                println()
                try {
                    val name = line.trim().split(" ".toRegex(), 2)[1]
                    changeName(name)
                } catch (e: Exception) {
                    println("Failed to parse input")
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
            line.startsWith("write") -> {
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

                        println("Writing to $fileDir")
                        println("Set $command")
                        Files.write(File(fileDir).toPath(), prevFile.toByteArray(), StandardOpenOption.WRITE)
                        println("Reloading settings")
                        loadSettingsFromFiles(SETTINGS_DIRECTORY)
                        println()
                    } catch (e: FileNotFoundException) {
                        println("File not found, use list to see current files")
                    }

                } catch (e: ScriptException) {
                    println("Invalid variable/value")
                }
            }
            line.startsWith("save") -> {
                println()
                try {
                    val name = line.trim().split(" ".toRegex(), 2)[1]

                    if (name == "default") {
                        saveDefault()
                    } else {
                        saveCFG(name)
                    }
                } catch (e: Exception) {
                    println("Invalid variable/value")
                }
            }
            line.startsWith("nadehelper") -> {
                try {
                    val name = line.trim().split(" ".toRegex(), 2)[1].replace(".txt", "")

                    loadPositions("$name.txt")
                } catch (e: Exception) {
                    println("Invalid variable/value")
                }

            }
            line.startsWith("load") -> {
                println()
                try {
                    val name = line.trim().split(" ".toRegex(), 2)[1]

                    loadCFG(name)
                } catch (e: Exception) {
                    println("Invalid variable/value")
                }
            }
            line.startsWith("delete") -> {
                println()
                try {
                    val name = line.trim().split(" ".toRegex(), 2)[1]

                    deleteCFG(name)
                } catch (e: Exception) {
                    println("Invalid variable/value")
                }
            }
            line.startsWith("ranks") -> {
                println()
                println("Team   Name                             Rank  Kills Deaths K/D  Wins  Money")
                println("====== ================================ ===== ===== ====== ==== ===== =====")
                try {
                    forEntities(EntityType.CCSPlayer) {
                        val entity = it.entity
                        var entTeam = when (entity.team()) {
                            3L -> "CT"
                            2L -> "T"
                            else -> "N/A"
                        }

                        var entName = entity.name()
                        var entRank = entity.rank().rankName()
                        var entKills = entity.kills().toString()
                        var entDeaths = entity.deaths().toString()
                        var entKD = when (entDeaths) {
                            "0" -> "N/A"
                            else -> (entKills.toFloat() / entDeaths.toFloat()).roundNDecimals(2).toString()
                        }
                        var entWins = entity.wins().toString()
                        var entMoney = entity.money().toString()

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

                        for (i in entMoney.length..4) {
                            entMoney += " "
                        }

                        println("$entTeam $entName $entRank $entKills $entDeaths $entKD $entWins $entMoney")
                    }
                    println()
                } catch (e: Exception) {
                    println("Error...")
                }
            }
        }

        Thread.sleep(1)
    }
}