package rat.poison.scripts.bspHandling

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector3
import info.ata4.bsplib.struct.DFace
import rat.poison.curSettings
import rat.poison.game.entity.*
import rat.poison.game.entity.dead
import rat.poison.game.entity.onGround
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.game.worldToScreen
import rat.poison.overlay.App
import rat.poison.overlay.bspVisTime
import rat.poison.scripts.aim.meDead
import rat.poison.utils.Vector
import rat.poison.utils.distanceTo
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.toVector3
import rat.poison.utils.inGame
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.system.measureNanoTime

//fun rayTraceTest() = App {
//    bspVisTime = TimeUnit.NANOSECONDS.convert(measureNanoTime {
//        if (meDead || !curSettings["DEBUG"].strToBool() || !inGame) return@App
//
//        var meVec = me.bones(8).toVector3()
//        meVec = Vector3(meVec.x, meVec.y, meVec.z)
//
//        forEntities(EntityType.CCSPlayer) {
//            val ent = it.entity
//            if (ent == me || !ent.onGround()) return@forEntities
//
//            var entBones = ent.bones(8).toVector3()
//            entBones = Vector3(entBones.x, entBones.y, entBones.z)
//
//            bspIsVisible(meVec, Vector3(entBones.x, entBones.y, entBones.z))
//        }
//    }, TimeUnit.NANOSECONDS)
//}

//fun drawMapWireframe() = App {
//    if (bspData.nodes == null) {
//        println("that buh niuulll")
//        return@App
//    }
//
//    shapeRenderer.color = Color.WHITE
//
//    val mePos = me.position()
//
//    val listOfFaces = mutableListOf<DFace>()
//    listOfFaces.addAll(bspData.faces)
//
//    //for (i in 0 until bspData.leaves.size) {
//        //val leaf = bspData.leaves[i]
//
//        //if (leaf.contents == 0) { //Leaf contents is incorrect...?
//            //for (j in 0 until leaf.numleafface) {
//                //val face = bspData.faces[bspData.leafFaces[leaf.fstleafface + j]]
//
//                //listOfFaces.remove(face)
//           // }
//        //}
//    //}
//
//    for (i in 0 until listOfFaces.size) { //Now loop our solids...
//        val face = listOfFaces[i]
//
//        for (k in 0 until face.numedge) {
//            val edge = bspData.edges[abs(bspData.surfEdges[face.fstedge + k])]
//
//            val vert1 = bspData.verts[edge.v[0]]
//            val vert2 = bspData.verts[edge.v[1]]
//
//            val vec1 = Vector(vert1.point.x, vert1.point.y, vert1.point.z)
//            val vec2 = Vector(vert2.point.x, vert2.point.y, vert2.point.z)
//
//            if (mePos.distanceTo(vec1) <= 1000) {
//                val v1 = Vector()
//                val v2 = Vector()
//
//                //Store these verts to reloop later...
//                if (worldToScreen(vec1, v1) && worldToScreen(vec2, v2)) {
//                    shapeRenderer.begin()
//                    shapeRenderer.line(v1.x, v1.y, v2.x, v2.y)
//                    shapeRenderer.end()
//                }
//            }
//        }
//    }
//
//    //edge count == 4
//}