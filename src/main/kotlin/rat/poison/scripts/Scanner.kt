package rat.poison.scripts

import rat.poison.SETTINGS_DIRECTORY
import rat.poison.loadSettings
import rat.poison.settings.*
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.util.*
import javax.script.ScriptException

fun scanner() {
    if (ACTION_LOG) {
        println("Type help for options\n")
    }

    //Major optimization, needs to be fixed later, probably move this massive dump elsewhere
    val scanner = Scanner(System.`in`)
    while (!Thread.interrupted()) {
        val line = scanner.nextLine().trim()
        when {
            line.startsWith("help") -> {
                if (line == "help") {
                    println("\nAvailable commands: help [command], exit, reload, list, read [file name], write [file name] [variable name] = [value]\n")
                } else {
                    val helpcommand = line.split(" ".toRegex(), 2)[1]
                    when (helpcommand) {
                        "exit" -> println("\nCloses program and cmd\n")
                        "reload" -> println("\nReloads all settings files, is done automatically on write\n")
                        "list" -> println("\nLists all settings files\n")
                        "read" -> println("\n Syntax: read [file name] ; Replace [file name] with the file name, viewable from the list command, excluding .kts. Example: read General")
                        "write" -> println("\n Syntax: write [file name] [variable name] = [value] ; Replace [file name] with the file name, replace [variable name] with the name of the variable inside of the file from [file name], and replace [value] with the value for the variable")
                        "help" -> println("\n Standalone or Syntax: help [command] ; Replace [command] with the command listed in list")
                    }
                }

            }
            line.equals("exit", true) -> {
                CHAMS_BRIGHTNESS = 0
                CHAMS_SHOW_HEALTH = false
                Thread.sleep(1000)
                System.exit(0)
            }
            line.equals("reload", true) -> {
                println(); loadSettings(); println()
            }
            line.equals("list", true) -> {
                println(); File(SETTINGS_DIRECTORY).listFiles().forEach { println(it) }; println()
            }
            line.startsWith("read") -> { //Read file's variables
                println()
                try {
                    File(SETTINGS_DIRECTORY + "\\" + line.trim().split(" ".toRegex())[1] + ".kts").readLines().forEach {
                        if (!it.startsWith("/") && !it.startsWith("*") && !it.startsWith(" ") && !it.trim().isEmpty() && !it.startsWith("import")) {
                            println(it)
                        }
                    }
                } catch (e: FileNotFoundException) {
                    println("File not found, use list to see current files")
                }
                println()
            }
            line.startsWith("set") -> { //Set variable, instance use only
                println()
                try {
                    //Dojo.script(line.trim().split(" ".toRegex(), 2)[1])
                    println("Set " + line.trim().split(" ".toRegex(), 2)[1])
                } catch (e: ScriptException) {
                    println("Invalid variable")
                }
                println()
            }
            line.startsWith("write") -> {
                val fileDir = SETTINGS_DIRECTORY + "\\" + line.trim().split(" ".toRegex())[1] + ".kts"
                val command = line.trim().split(" ".toRegex(), 3)[2]
                var prevFile = ""
                println()
                try {
                    try { //Check for file + variable
                        File(fileDir).readLines().forEach {
                            if (!it.startsWith("/") && !it.startsWith("*") && !it.startsWith(" ") && !it.trim().isEmpty() && !it.startsWith("import") && it.startsWith(command.split(" ".toRegex(), 3)[0])) {
                                prevFile = prevFile + command + System.lineSeparator()
                            } else {
                                prevFile = prevFile + it + System.lineSeparator()
                            }
                        }
                        if (ACTION_LOG) {
                            println("Writing to " + fileDir)
                            println("Set " + command)
                            Files.write(File(fileDir).toPath(), prevFile.toByteArray(), StandardOpenOption.WRITE)
                            println("Reloading settings"); loadSettings()
                            println()
                        }
                    } catch (e: FileNotFoundException) {
                        println("File not found, use list to see current files")
                    }

                } catch (e: ScriptException) {
                    println("Invalid variable/value")
                }
            }
        }
    }
}