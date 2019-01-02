/*
 * Charlatano: Free and open-source (FOSS) cheat for CS:GO/CS:CO
 * Copyright (C) 2017 - Thomas G. P. Nappo, Jonathan Beaudoin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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

    //Major optimization, needs to be fixed later
	val scanner = Scanner(System.`in`)
	while (!Thread.interrupted()) {
		val line = scanner.nextLine().trim()
		when {
			line.equals("help", true) -> println("Available commands: exit/quit, reload, list, read [file name], write [file name] [variable name] = [value]")
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
                val FileDir = SETTINGS_DIRECTORY + "\\" + line.trim().split(" ".toRegex())[1]+".kts"
                val Command = line.trim().split(" ".toRegex(),3)[2]
                var prevFile = ""
                println()
                try {
                    try { //Check for file + variable
                        File(FileDir).readLines().forEach {
                            if (!it.startsWith("/") && !it.startsWith("*") && !it.startsWith(" ") && !it.trim().isEmpty() && !it.startsWith("import") && it.startsWith(Command.split(" ".toRegex(),3)[0])) {
                                prevFile = prevFile + Command + System.lineSeparator()
                            } else {
                                prevFile = prevFile + it + System.lineSeparator()
                            }
                        }
                        println("Writing to " + FileDir)
                        println("Set " + Command)
                        Files.write(File(FileDir).toPath(), prevFile.toByteArray(), StandardOpenOption.WRITE)
                        println("Reloading settings"); loadSettings()
                        println()
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
