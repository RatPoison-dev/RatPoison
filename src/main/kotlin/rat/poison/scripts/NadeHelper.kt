package rat.poison.scripts

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.util.dialog.InputDialogAdapter
import com.sun.jna.Memory
import rat.poison.*
import rat.poison.game.CSGO
import rat.poison.game.entity.absPosition
import rat.poison.game.entity.boneMatrix
import rat.poison.game.entity.direction
import rat.poison.game.me
import rat.poison.game.w2sViewMatrix
import rat.poison.game.worldToScreen
import rat.poison.overlay.App
import rat.poison.overlay.App.menuStage
import rat.poison.overlay.opened
import rat.poison.scripts.aim.meCurWep
import rat.poison.scripts.aim.meDead
import rat.poison.settings.HEAD_BONE
import rat.poison.settings.MENUTOG
import rat.poison.ui.tabs.nadeHelperLoadedFileStr
import rat.poison.ui.uiPanels.nadeHelperTab
import rat.poison.utils.Vector
import rat.poison.utils.generalUtil.cToDouble
import rat.poison.utils.generalUtil.cToFloat
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.toMatrix4
import rat.poison.utils.inGame
import java.io.File
import java.io.FileReader
import java.nio.file.Files

//Convert shit to an object {} ?
//      mapcoords:            x    y    z
private var feetSpot = listOf(0.0, 0.0, 0.0, "NAME", "TYPE") //Move string out of, remove .cToDouble() temp
private var headPos = listOf(0.0, 0.0, 0.0)
private var headLookPos = listOf(0.0, 0.0, 0.0)
private var LoL: List<List<Any>> = listOf(emptyList(), emptyList(), emptyList())
private var mPos = Vector()

var nadeHelperArrayList = arrayListOf<List<List<Any>>>()

fun nadeHelper() = App {
    if (!curSettings["ENABLE_NADE_HELPER"].strToBool() || !curSettings["ENABLE_ESP"].strToBool() || !inGame) return@App

    if (me <= 0L || MENUTOG || meDead) return@App

    mPos = me.absPosition()

    val nadeToCheck : String = when (meCurWep.name) {
        "FLASH_GRENADE" -> "Flash"
        "SMOKE_GRENADE" -> "Smoke"
        "MOLOTOV" -> "Molly"
        "INCENDIARY_GRENADE" -> "Molly"
        "EXPLOSIVE_GRENADE" -> "Frag"
        "DECOY_GRENADE" -> "Decoy"
        else -> ""
    }

    if (nadeToCheck != "") {
        nadeHelperArrayList.forEach {
            val fSpot = it[0]
            val hPos = it[1]
            val hLPos = it[2]

            //If valid nade for nade held
            if (fSpot[4] == nadeToCheck || nadeToCheck == "Decoy") {
                //If within X units of position's circle
                val units = curSettings["NADE_ACCURACY_RADIUS"].toInt()
                if ((mPos.x in fSpot[0].cToDouble() - units..fSpot[0].cToDouble() + units) && (mPos.y in fSpot[1].cToDouble() - units..fSpot[1].cToDouble() + units)) {

                    val oldMatrix = Matrix4(shapeRenderer.projectionMatrix.values)

                    shapeRenderer.apply {
                        val gameMatrix = w2sViewMatrix.toMatrix4()
                        projectionMatrix = gameMatrix

                        begin()
                        color = Color(1F, 1F, 1F, 1F)
                        var lineUp = false

                        //If standing on position's circle
                        if ((mPos.x in fSpot[0].cToDouble() - 20..fSpot[0].cToDouble() + 20) && (mPos.y in fSpot[1].cToDouble() - 20..fSpot[1].cToDouble() + 20)) {
                            color = Color(1F, 0F, 0F, 1F)
                            lineUp = true
                        }

                        //Circle at foot's position
                        gameMatrix.translate(0F, 0F, fSpot[2].cToFloat())
                        projectionMatrix = gameMatrix
                        circle(fSpot[0].cToFloat(), fSpot[1].cToFloat(), 10F)
                        gameMatrix.translate(0F, 0F, -fSpot[2].cToFloat())
                        projectionMatrix = oldMatrix

                        val vec2 = Vector()
                        val vec3 = Vector()

                        var t1 = false
                        var t2 = false

                        if (worldToScreen(Vector(hPos[0].cToFloat(), hPos[1].cToFloat(), hPos[2].cToFloat()), vec2)) {
                            t1 = true
                        }

                        if (worldToScreen(Vector(hLPos[0].cToFloat(), hLPos[1].cToFloat(), hLPos[2].cToFloat()), vec3)) {
                            set(ShapeRenderer.ShapeType.Filled)
                            circle(vec3.x, vec3.y - 2F, 4F)
                            set(ShapeRenderer.ShapeType.Line)
                            t2 = true

                            val sbText = StringBuilder()
                            if (fSpot[5] != "Other") {
                                //            Name          Throwing Type
                                sbText.append("${fSpot[3]} (${fSpot[5]})")
                            }
                            else {
                                sbText.append("${fSpot[3]}")
                            }

                            if (!sb.isDrawing) {
                                sb.begin()
                            }

                            textRenderer.color = Color.WHITE
                            textRenderer.draw(sb, sbText, vec3.x, vec3.y - 10F, 1F, Align.center, false)

                            sb.end()
                        }

                        if (t2) {
                            if (!lineUp) {
                                if (t1) {
                                    line(vec2.x - 4F, vec2.y - 4F, vec3.x, vec3.y - 2F)
                                }
                            } else {
                                line(CSGO.gameWidth / 2F, 0F, vec3.x, vec3.y - 2F)
                            }
                        }

                        end()
                    }

                    shapeRenderer.projectionMatrix = oldMatrix
                }
            }
        }
    }
}

fun createPosition() {
    val boneMemory: Memory by lazy {
        Memory(3984)
    }

    CSGO.csgoEXE.read(me.boneMatrix(), boneMemory)
    val xOff = boneMemory.getFloat(((0x30L * HEAD_BONE) + 0xC)).toDouble()
    val yOff = boneMemory.getFloat(((0x30L * HEAD_BONE) + 0x1C)).toDouble()
    val zOff = boneMemory.getFloat(((0x30L * HEAD_BONE) + 0x2C)).toDouble()

    val mDir = me.direction()
    val hLPx = xOff + 500 * mDir.x
    val hLPy = yOff + 500 * mDir.y
    val hLPz = zOff + 500 * mDir.z

    Dialogs.showInputDialog(menuStage, "Enter Position Name", "", object : InputDialogAdapter() {
        override fun finished(input: String) {

            val chooseNadeArrayString = arrayOf("Flash", "Frag", "Molly", "Smoke")
            val chooseNadeArrayInt = arrayOf(1, 2, 3, 4)

            Dialogs.showConfirmDialog(menuStage, "Choose Nade Type", "", chooseNadeArrayString, chooseNadeArrayInt) { int ->
                val chosenNadeType = when (int) {
                    1 -> "Flash"
                    2 -> "Frag"
                    3 -> "Molly"
                    else -> "Smoke" //4
                }
                //
                Dialogs.showConfirmDialog(menuStage, "Choose Nade Throwing Type", "", arrayOf("Jump + Throw", "Stand + Throw", "Other"), arrayOf(1, 2, 3)) { i ->
                    val throwingNadeType = when (i) {
                        1 -> "J+T"
                        2 -> "S+T"
                        else -> "Other" //3
                    }
                    mPos = me.absPosition()
                    feetSpot = listOf(mPos.x, mPos.y, mPos.z, input, chosenNadeType, throwingNadeType)
                    headPos = listOf(xOff, yOff, zOff)
                    headLookPos = listOf(hLPx, hLPy, hLPz)
                    LoL = listOf(feetSpot, headPos, headLookPos)
                    nadeHelperArrayList.add(LoL)

                    nadeHelperTab.updateNadeFileHelperList()
                }
            }
        }
    }).setSize(200F, 200F)
}

fun loadPositions(file: String) {
    println("Clearing Array/Loading Positions")
    nadeHelperArrayList.clear()
    FileReader("$SETTINGS_DIRECTORY\\NadeHelper\\$file").readLines().forEach { line ->
        val lLine = line.trim().replace("\"", "").replace("[", "").replace("]", "").split(", ")
        val chunks = lLine.chunked(12) //Split arrayList into LoLs, size 12
        //Chunks [0] = 1 LoL
        chunks.forEach { chunk ->
            var bNotEmpty = true
            chunk.forEach {
                if (it == "") {
                    bNotEmpty = false
                }
            }

            if (bNotEmpty) {
                feetSpot = listOf(chunk[0].cToDouble(), chunk[1].cToDouble(), chunk[2].cToDouble(), chunk[3], chunk[4], chunk[5]) //5, 3 for location, 1 name, 1 type, 1 throwing type
                headPos = listOf(chunk[6].cToDouble(), chunk[7].cToDouble(), chunk[8].cToDouble()) //3
                headLookPos = listOf(chunk[9].cToDouble(), chunk[10].cToDouble(), chunk[11].cToDouble()) //3
                LoL = listOf(feetSpot, headPos, headLookPos)
                nadeHelperArrayList.add(LoL)

                if (opened) {
                    nadeHelperTab.nadeHelperLoadedFile.setText("Loaded: $file")
                }
                nadeHelperLoadedFileStr = file

            } else {
                println("[Error] $file is empty, not loading")
                if (curSettings["MENU"].strToBool()) {
                    nadeHelperTab.nadeHelperLoadedFile.setText("Loaded: N/A")
                }
            }
        }
    }
}

fun savePositions() {
    println("Saving Positions")
    Dialogs.showInputDialog(menuStage, "Enter File Name", "", object : InputDialogAdapter() {
        override fun finished(input: String) {
            val cfgFile = File("$SETTINGS_DIRECTORY\\NadeHelper\\$input.txt")
            if (!cfgFile.exists()) {
                cfgFile.createNewFile()
            }

            Files.delete(cfgFile.toPath())
            Files.createFile(cfgFile.toPath())

            cfgFile.appendText("\"$nadeHelperArrayList\"")

            nadeHelperTab.updateNadeFileHelperList()
        }
    }).setSize(200F, 200F)
}

fun deletePosition() {
    var iPos = 0
    var removePos = -1

    nadeHelperArrayList.forEach {
        val fSpot = it[0]

        if ((mPos.x in fSpot[0].cToDouble()-500..fSpot[0].cToDouble()+500) && (mPos.y in fSpot[1].cToDouble()-500..fSpot[1].cToDouble()+500)) {
            if ((mPos.x in fSpot[0].cToDouble()-5..fSpot[0].cToDouble()+5) && (mPos.y in fSpot[1].cToDouble()-5..fSpot[1].cToDouble()+5)) {
                removePos = iPos
            }
        }
        iPos++
    }
    if (removePos != -1) {
        nadeHelperArrayList.removeAt(removePos)
    }
}

fun detectMap(mapName: String) {
    if (!curSettings["ENABLE_NADE_HELPER"].strToBool() || !curSettings["ENABLE_ESP"].strToBool()) return

    val newMapName = mapName.replace("maps\\", "").replace(".bsp", "")
    val dir = File("$SETTINGS_DIRECTORY/NadeHelper").listFiles()
    dir?.forEach {
        val name = it.name
        if (newMapName.contains(name.replace(".txt", ""))) {
            loadPositions(name)
            return@forEach
        }
    }
}