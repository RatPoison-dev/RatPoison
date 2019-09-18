package rat.poison.scripts

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils.clamp
import rat.poison.App
import rat.poison.curSettings
import rat.poison.game.entity.absPosition
import rat.poison.game.worldToScreen
import rat.poison.settings.MENUTOG
import rat.poison.strToBool
import rat.poison.utils.Vector
import rat.poison.utils.notInGame

var entsToTrack = mutableListOf<Long>()

var positionsList = mutableListOf<List<Double>>()
var grenadeList = mutableListOf<MutableList<List<Double>>>()

var sync = 0

fun nadeTracer() = App {
    if (!curSettings["NADE_TRACER"].strToBool() || MENUTOG || !curSettings["ENABLE_ESP"].strToBool() || notInGame) return@App

    val alphaUpdate = clamp(.011 - curSettings["NADE_TRACER_TIMEOUT"].toDouble(), .001, .01)
    //Calculate spots
    if (sync >= (curSettings["NADE_TRACER_UPDATE_TIME"].toInt())) { //Change to add a 0 to the end to prevent connecting grenade lines
        entsToTrack.forEachIndexed { i, ent ->
            val entPos = ent.absPosition()

            var idx = -1 //If already in a list
            grenadeList.forEachIndexed {j, posList ->
                val n = posList[posList.size-1]
                val nn = n[4].toLong()

                if (nn == ent) {
                    idx = j
                }
            }

            val tmp = listOf(entPos.x, entPos.y, entPos.z, 1.0, ent.toDouble())
            val check = (entPos.x in -2.0..2.0 && entPos.y in -2.0..2.0 && entPos.z in -2.0..2.0)
            if (!check) {
                if (idx == -1) {
                    positionsList = mutableListOf()
                    positionsList.add(tmp)
                    grenadeList.add(positionsList)
                } else {
                    positionsList = grenadeList[idx]
                    positionsList.add(positionsList.size-1, tmp) //Add at end
                    grenadeList[idx] = positionsList
                }
            }
        }
        sync = 0 //Reset
    }
    sync++ //Add 1 to tick

    //Draw everything
    var sizeVar = 0
    for (i in 0 until grenadeList.size - sizeVar) {
        if (i == 0) sizeVar = 0 //Reset

        if (i >= grenadeList.size) {
            continue
        }

        val tmpPosList = grenadeList[i]

        var toRemove = true
        for (j in 0 until tmpPosList.size-2) {
            if (tmpPosList.size == 1) { //Edit the alpha of the very first spot
                val tmpPos = tmpPosList[0]
                val newTmpPos = listOf(tmpPos[0], tmpPos[1], tmpPos[2], tmpPos[3]-alphaUpdate, tmpPos[4])
                tmpPosList[0] = newTmpPos
                continue
            }

            val pPos1 = tmpPosList[j]
            val pPos2 = tmpPosList[j+1]

            if (pPos1[3] <= 0) {
                continue
            } else if (pPos2[3] <= 0) {
                continue
            } else {
                toRemove = false
            }

            val pPos1Vec = Vector()
            val pPos2Vec = Vector()

            if (worldToScreen(Vector(pPos1[0], pPos1[1], pPos1[2]), pPos1Vec) && worldToScreen(Vector(pPos2[0], pPos2[1], pPos2[2]), pPos2Vec)) {
                shapeRenderer.apply {
                    begin()

                    val a = ((pPos1[3] + pPos2[3]) / 2F).toFloat()

                    color = Color(1F, 1F, 1F, a)

                    line(pPos1Vec.x.toFloat(), pPos1Vec.y.toFloat(), pPos2Vec.x.toFloat(), pPos2Vec.y.toFloat())

                    end()
                }
            }

            val newPos1 = listOf(pPos1[0], pPos1[1], pPos1[2], pPos1[3]-alphaUpdate, pPos1[4])
            val newPos2 = listOf(pPos2[0], pPos2[1], pPos2[2], pPos2[3]-alphaUpdate, pPos2[4])

            tmpPosList[j] = newPos1
            tmpPosList[j+1] = newPos2
        }

        if (tmpPosList.size > 2) {
            if (toRemove) {
                grenadeList.removeAt(i)
                sizeVar++
            }
        }
    }
}