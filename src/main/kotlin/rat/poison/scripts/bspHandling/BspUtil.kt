package rat.poison.scripts.bspHandling
//https://github.com/hrt/CSGO/blob/master/BSP/TraceRay.cpp

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector3
import info.ata4.bsplib.BspFile
import info.ata4.bsplib.app.SourceAppDB
import info.ata4.bsplib.app.SourceAppID
import info.ata4.bsplib.struct.BspData
import info.ata4.bsplib.struct.DLeaf
import info.ata4.bsplib.struct.DNode
import info.ata4.bsplib.struct.DPlane
import org.lwjgl.opengl.GL11.glPointSize
import rat.poison.dbg
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.game.worldToScreen
import rat.poison.overlay.App.sb
import rat.poison.overlay.App.shapeRenderer
import rat.poison.overlay.App.textRenderer
import rat.poison.utils.Vector
import rat.poison.utils.generalUtil.toVector
import java.io.File
import kotlin.math.pow
import kotlin.math.sqrt

//var bspData: BspData = BspData()

//fun loadBsp(fileDir: String) {
//    try {
//        if (fileDir.isEmpty()) return
//
//        val bsp = BspFile(File(fileDir).toPath())
//
//        bsp.sourceApp = SourceAppDB.getInstance().fromID(SourceAppID.COUNTER_STRIKE_GO)
//        bsp.loadLumpFiles()
//        val reader = bsp.reader
//        reader.loadAll()
//        bspData = reader.data
//    } catch (e: Exception) {
//        println("Failed to load map $fileDir")
//    }
//}

//Read brushes/faces/surfaces? from leaf to determine more accurately, i do be dummy brained
//Doesnt work properly with different heights, such as a ramp vs cat
//fun bspIsVisible(start: Vector3, end: Vector3): Boolean {
//    if (bspData.nodes == null) {
//        return false
//    }
//
//    var dir = Vector3(end.x - start.x, end.y - start.y, end.z - start.z)
//    var point = Vector3(start.x, start.y, start.z)
//
//    var steps = sqrt(dir.x.pow(2F) + dir.y.pow(2F) + dir.z.pow(2F))
//
//    if (steps > 4000) {
//        if (dbg) {
//            println("[DEBUG] Ray over 4000 steps, cancelling")
//        }
//        return false
//    }
//
//    dir = Vector3(dir.x / steps, dir.y / steps, dir.z / steps)
//
//    shapeRenderer.apply {
//        if (isDrawing) {
//            end()
//        }
//
//        begin()
//
//        val sbText = StringBuilder()
//
//        val entW2S = Vector()
//        val entOnScreen = worldToScreen(Vector(end.x, end.y, end.z), entW2S)
//
//        val w2sOut = Vector()
//        var pLeaf: DLeaf?
//
//        while (steps > 0) {
//            point = Vector3(point.x + dir.x, point.y + dir.y, point.z + dir.z)
//            pLeaf = getLeafFromPoint(point)
//
////            for (i in 0 until pLeaf.numleafface) { //Loop faces
////                val idx = pLeaf.fstleafface + i
////
////                val face = bspData.leafFaces[idx]
////
////                face.edg
////
////
////            }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//            worldToScreen(point.toVector(), w2sOut)
//
//            glPointSize(3F)
//
//            val bW = pLeaf.contents and MASK_SOLID
//            if (bW != 0) {
//                if (entOnScreen) {
//                    line(gameWidth/2F, gameHeight/2F, entW2S.x, entW2S.y)
//
//                    sbText.append("false")
//                    textRenderer.apply {
//                        sb.begin()
//
//                        draw(sb, sbText, entW2S.x, entW2S.y)
//
//                        sb.end()
//                    }
//                }
//
//                set(ShapeRenderer.ShapeType.Line)
//                color = Color(1F, 1F, 1F, 1F)
//                end()
//                return false
//            }
//
//            steps--
//        }
//
//        if (entOnScreen) {
//            line(gameWidth/2F, gameHeight/2F, entW2S.x, entW2S.y)
//
//            sbText.append("true")
//            textRenderer.apply {
//                sb.begin()
//
//                draw(sb, sbText, entW2S.x, entW2S.y)
//
//                sb.end()
//            }
//        }
//
//        set(ShapeRenderer.ShapeType.Line)
//        color = Color(1F, 1F, 1F, 1F)
//        end()
//    }
//
//    return true
//}

//fun getLeafFromPoint(point: Vector3): DLeaf {
//    var nodeNum = 0
//    var pNode: DNode? = DNode()
//    var pPlane: DPlane? = DPlane()
//
//    var dist: Float
//
//    while (nodeNum >= 0) {
//        if (pNode == null || pPlane == null) { //Shouldnt be null
//            println("shnull we continuing")
//            continue
//        }
//
//        pNode = bspData.nodes.elementAtOrNull(nodeNum)
//
//        pPlane = bspData.planes.elementAtOrNull(pNode!!.planenum)!!
//
//        dist = (point.x * pPlane.normal.x + point.y * pPlane.normal.y + point.z * pPlane.normal.z) - pPlane.dist
//
//        //Loop node tree until we get to a leaf (negative number)
//        nodeNum = if (dist > 0.0F) {
//            pNode.children[0]
//        } else {
//            pNode.children[1]
//        }
//    }
//
//    return bspData.leaves[-nodeNum - 1]
//}

//fun getNodeFromPoint(point: Vector): DNode {
//    var nodeNum = 0
//    var pNode: DNode? = DNode()
//    var pPlane: DPlane? = DPlane()
//
//    var dist: Float
//
//    while (nodeNum >= 0) {
//        if (pNode == null || pPlane == null) { //Shouldnt be null
//            println("shnull we continuing")
//            continue
//        }
//
//        pNode = bspData.nodes.elementAtOrNull(nodeNum)
//
//        pPlane = bspData.planes.elementAtOrNull(pNode!!.planenum)!!
//
//        dist = (point.x * pPlane.normal.x + point.y * pPlane.normal.y + point.z * pPlane.normal.z) - pPlane.dist
//
//        //Loop node tree until we get to a leaf (negative number)
//        nodeNum = if (dist > 0.0F) {
//            pNode.children[0]
//        } else {
//            pNode.children[1]
//        }
//    }
//
//    return pNode!!
//}