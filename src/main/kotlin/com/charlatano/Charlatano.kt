@file:JvmName("Charlatano")

package com.charlatano

import com.charlatano.game.CSGO
import com.charlatano.overlay.Overlay
import com.charlatano.scripts.*
import com.charlatano.scripts.aim.flatAim
import com.charlatano.scripts.aim.pathAim
import com.charlatano.scripts.esp.esp
import com.charlatano.settings.*
import com.charlatano.utils.Dojo
import com.sun.jna.platform.win32.WinNT
import org.jetbrains.kotlin.cli.common.environment.setIdeaIoUseFallback
import org.jire.arrowhead.keyPressed
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.util.*
import javax.script.ScriptException

const val SETTINGS_DIRECTORY = "settings"

fun main(args: Array<String>) {
	loadSettings()
	
	CSGO.initialize()
	
	bunnyHop()
	rcs()
	esp()
	flatAim()
	pathAim()
	boneTrigger()
	reducedFlash()
	bombTimer()
	
	if (LEAGUE_MODE) {
		GLOW_ESP = false
		BOX_ESP = false
		SKELETON_ESP = false
		CHAMS_ESP = false
		ENABLE_ESP = false
		
		ENABLE_BOMB_TIMER = false
		ENABLE_REDUCED_FLASH = false
		ENABLE_FLAT_AIM = false
		
		SERVER_TICK_RATE = 128 // most leagues are 128-tick
		PROCESS_ACCESS_FLAGS = WinNT.PROCESS_QUERY_INFORMATION or WinNT.PROCESS_VM_READ // all we need
		GARBAGE_COLLECT_ON_MAP_START = true // get rid of traces
	}

	if (keyPressed(ESP_TOGGLE_KEY)) {
		ENABLE_ESP = !ENABLE_ESP
		if (ACTION_LOG) {
			println("ESP toggled to $ENABLE_ESP")
		}
	}

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
					println("\nAvailable commands: exit, reload, list, read [file name], write [file name] [variable name] = [value]\n")
				} else {
					val helpcommand = line.split(" ".toRegex(), 2)[1]
					when (helpcommand) {
						"exit" -> println("\nCloses program and cmd\n")
						"reload" -> println("\nReloads all settings files, is done automatically on write\n")
						"list" -> println("\nLists all settings files\n")
						"read" -> println("\n Syntax: read [file name] ; Replace [file name] with the file name, viewable from the list command, excluding .kts. Example: read General")
						"write" -> println("\n Syntax: write [file name] [variable name] = [value] ; Replace [file name] with the file name, replace [variable name] with the name of the variable inside of the file from [file name], and replace [value] with the value for the variable")
					}
				}

			}
			line.equals("exit", true) -> System.exit(0)
			line.equals("reload", true) -> { println(); loadSettings(); println() }
			line.equals("list", true) -> { println(); File(SETTINGS_DIRECTORY).listFiles().forEach { println(it) }; println() }
			line.startsWith("read") -> { //Read file's variables
				println()
				try{
					File(SETTINGS_DIRECTORY + "\\" + line.trim().split(" ".toRegex())[1] +".kts").readLines().forEach { if (!it.startsWith("/") && !it.startsWith("*") && !it.startsWith(" ") && !it.trim().isEmpty() && !it.startsWith("import")) {println(it) } }
				} catch (e: FileNotFoundException) {
					println("File not found, use list to see current files")
				}
				println()
			}
            line.startsWith("set") -> { //Set variable, instance use only
                println()
                try {
                    Dojo.script(line.trim().split(" ".toRegex(), 2)[1])
                    println("Set " + line.trim().split(" ".toRegex(), 2)[1])
                } catch (e: ScriptException) {
                    println("Invalid variable")
                }
                println()
            }
            line.startsWith("write") -> {
                val fileDir = SETTINGS_DIRECTORY + "\\" + line.trim().split(" ".toRegex())[1]+".kts"
                val command = line.trim().split(" ".toRegex(),3)[2]
                var prevFile = ""
                println()
                try {
                    try { //Check for file + variable
                        File(fileDir).readLines().forEach {
                            if (!it.startsWith("/") && !it.startsWith("*") && !it.startsWith(" ") && !it.trim().isEmpty() && !it.startsWith("import") && it.startsWith(command.split(" ".toRegex(),3)[0])) {
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

private fun loadSettings() {
	setIdeaIoUseFallback()
	
	File(SETTINGS_DIRECTORY).listFiles().forEach {
		FileReader(it).use {
			Dojo.script(it
					.readLines()
					.joinToString("\n"))
		}
	}
	
	val needsOverlay = ENABLE_BOMB_TIMER or (ENABLE_ESP and (SKELETON_ESP or BOX_ESP))
	if (!Overlay.opened && needsOverlay) Overlay.open()
}
