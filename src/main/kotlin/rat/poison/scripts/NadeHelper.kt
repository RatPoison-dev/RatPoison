package rat.poison.scripts

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils.clamp
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.util.dialog.InputDialogAdapter
import com.sun.jna.Memory
import org.jire.arrowhead.keyPressed
import rat.poison.*
import rat.poison.App.menuStage
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.game.entity.absPosition
import rat.poison.game.entity.boneMatrix
import rat.poison.game.entity.direction
import rat.poison.game.entity.eyeAngle
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.settings.HEAD_BONE
import rat.poison.settings.MENUTOG
import rat.poison.ui.nadeHelperTab
import rat.poison.utils.ObservableBoolean
import rat.poison.utils.Vector
import rat.poison.utils.notInGame
import java.awt.Robot
import java.awt.event.KeyEvent
import java.io.File
import java.io.FileReader
import java.nio.file.Files
import kotlin.math.cos
import kotlin.math.sin

//Convert shit to an object {} ?
//      mapcoords:            x    y    z
private var feetSpot = listOf(0.0, 0.0, 0.0, "NAME") //Move string out of, remove .cToDouble() temp
private var headPos = listOf(0.0, 0.0, 0.0)
private var headLookPos = listOf(0.0, 0.0, 0.0)
private var LoL: List<List<Any>> = listOf(emptyList(), emptyList(), emptyList())
private var showHelpers = false
private var mPos = Vector()

var nadeHelperArrayList = arrayListOf<List<List<Any>>>()
var nadeHelperToggleKey = ObservableBoolean({keyPressed(curSettings["NADE_HELPER_TOGGLE_KEY"].toInt())})

//I don't like
fun nadeHelper() = App {
    if (!curSettings["ENABLE_NADE_HELPER"].strToBool() || !curSettings["ENABLE_ESP"].strToBool() || notInGame) return@App

    nadeHelperToggleKey.update()
    if (nadeHelperToggleKey.justBecomeTrue) {
        showHelpers = !showHelpers
        nadeHelperTab.nadeHelperToggleText.setText("Toggled: $showHelpers")
    }

    if (me <= 0L || MENUTOG) return@App

    mPos = me.absPosition()

    if (showHelpers) {
        val myWep = me.weapon()
        val nadeToCheck : String
        nadeToCheck = when (myWep.name) {
            "FLASH_GRENADE" -> "Flash"
            "SMOKE_GRENADE" -> "Smoke"
            "MOLOTOV" -> "Molly"
            "INCENDIARY_GRENADE" -> "Molly"
            "EXPLOSIVE_GRENADE" -> "Frag"
            "DECOY_GRENADE" -> "Decoy"
            else -> ""
        }

        nadeHelperArrayList.forEach {
            val fSpot = it[0]
            val hPos = it[1]
            val hLPos = it[2]

            //If valid nade for nade held
            if (fSpot[4] == nadeToCheck || nadeToCheck == "Decoy") {
                //If within 500 units of position's circle
                if ((mPos.x in fSpot[0].cToDouble() - 500..fSpot[0].cToDouble() + 500) && (mPos.y in fSpot[1].cToDouble() - 500..fSpot[1].cToDouble() + 500)) {

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

                        if (worldToScreen(Vector(hPos[0].cToDouble(), hPos[1].cToDouble(), hPos[2].cToDouble()), vec2)) {
                            t1 = true
                        }

                        if (worldToScreen(Vector(hLPos[0].cToDouble(), hLPos[1].cToDouble(), hLPos[2].cToDouble()), vec3)) {
                            set(ShapeRenderer.ShapeType.Filled)
                            circle(vec3.x.toFloat(), vec3.y.toFloat() - 2F, 4F)
                            set(ShapeRenderer.ShapeType.Line)
                            t2 = true

                            textRenderer.apply {
                                val glyph = GlyphLayout()

                                sb.begin()
                                val sbText = StringBuilder()
                                sbText.append(fSpot[3].toString())

                                glyph.setText(textRenderer, sbText, 0, (sbText as CharSequence).length, Color.WHITE, 1F, Align.center, false, null)
                                textRenderer.draw(sb, glyph, vec3.x.toFloat(), vec3.y.toFloat()-10F)

                                sb.end()
                            }
                        }

                        if (t2) {
                            if (!lineUp) {
                                if (t1) {
                                    line(vec2.x.toFloat() - 4F, vec2.y.toFloat() - 4F, vec3.x.toFloat(), vec3.y.toFloat() - 2F)
                                }
                            } else {
                                line(CSGO.gameWidth/2F, 0F, vec3.x.toFloat(), vec3.y.toFloat() - 2F)
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
            //Fuck this
            val chooseNadeArrayString = arrayOf("Flash", "Frag", "Molly", "Smoke")
            val chooseNadeArrayInt = arrayOf(1, 2, 3, 4)

            Dialogs.showConfirmDialog(menuStage, "Choose Nade Type", "", chooseNadeArrayString, chooseNadeArrayInt) { int ->
                val chosenNadeType = when (int) {
                    1 -> "Flash"
                    2 -> "Frag"
                    3 -> "Molly"
                    else -> "Smoke" //4
                }

                mPos = me.absPosition()
                feetSpot = listOf(mPos.x, mPos.y, mPos.z, input, chosenNadeType)
                headPos = listOf(xOff, yOff, zOff)
                headLookPos = listOf(hLPx, hLPy, hLPz)
                LoL = listOf(feetSpot, headPos, headLookPos)
                nadeHelperArrayList.add(LoL)

                nadeHelperTab.updateNadeFileHelperList()
            }
        }
    }).setSize(200F, 200F)
}

fun loadPositions(file: String) {
    println("Clearing Array/Loading Positions")
    nadeHelperArrayList.clear()
    FileReader("$SETTINGS_DIRECTORY\\NadeHelper\\$file").readLines().forEach { line ->
        val lLine = line.trim().replace("\"", "").replace("[", "").replace("]", "").split(", ")
        val chunks = lLine.chunked(11) //Split arrayList into LoLs, size 11
        //Chunks [0] = 1 LoL
        chunks.forEach { chunk ->
            feetSpot = listOf(chunk[0].cToDouble(), chunk[1].cToDouble(), chunk[2].cToDouble(), chunk[3], chunk[4]) //5, 3 for location, 1 name, 1 type
            headPos = listOf(chunk[5].cToDouble(), chunk[6].cToDouble(), chunk[7].cToDouble()) //3
            headLookPos = listOf(chunk[8].cToDouble(), chunk[9].cToDouble(), chunk[10].cToDouble()) //3
            LoL = listOf(feetSpot, headPos, headLookPos)
            nadeHelperArrayList.add(LoL)
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
    if (showHelpers) {
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
}

//Matrix 4 uses column-major order
private fun Array<DoubleArray>.toMatrix4(): Matrix4 {
    val input = this
    val mat4 = Matrix4()
    val fArr = FloatArray(16)

    var itr = 0
    for (row in 0..3) {
        for (col in 0..3) {
            fArr[itr] = input[col][row].toFloat()
            itr++
        }
    }

    mat4.set(fArr)
    return mat4
}