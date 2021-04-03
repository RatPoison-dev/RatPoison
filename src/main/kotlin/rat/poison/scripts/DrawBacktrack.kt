package rat.poison.scripts

import com.badlogic.gdx.graphics.Color
import rat.poison.curSettings
import rat.poison.game.me
import rat.poison.game.worldToScreen
import rat.poison.overlay.App
import rat.poison.scripts.aim.meCurWep
import rat.poison.scripts.aim.meDead
import rat.poison.scripts.visuals.lineThroughSmoke
import rat.poison.settings.MENUTOG
import rat.poison.utils.Vector
import rat.poison.utils.inGame
import rat.poison.utils.keyPressed

private val minHeadPos = Vector()
private val maxHeadPos = Vector()
private val minAbsPos = Vector()
private val maxAbsPos = Vector()
private val topLeft = Vector()
private val topRight = Vector()
private val bottomLeft = Vector()
private val bottomRight = Vector()

fun drawBacktrack() = App {
    if (MENUTOG) return@App
    if (meDead) return@App
    if (!inGame || !curSettings.bool["BACKTRACK_VISUALIZE"] || !curSettings.bool["ENABLE_ESP"] || !curSettings.bool["ENABLE_BACKTRACK"]) return@App

    val backtrackOnKey = curSettings.bool["ENABLE_BACKTRACK_ON_KEY"]
    val backtrackKeyPressed = keyPressed(curSettings.int["BACKTRACK_KEY"])

    if (backtrackOnKey && !backtrackKeyPressed) return@App

    if (!meCurWep.gun) return@App

    if (curSettings.bool["BACKTRACK_VISUALIZE_SMOKE_CHECK"] && lineThroughSmoke(me)) return@App

    for (i in 0 until 63) {
        if (btRecords[i][0].simtime == 0F) continue

        val minMaxIDX = getRangeRecords(i)

        if (minMaxIDX[0] == Int.MAX_VALUE || minMaxIDX[1] == -1) continue

        val minRecord = btRecords[i][minMaxIDX[0]]
        val maxRecord = btRecords[i][minMaxIDX[1]]

        if (worldToScreen(minRecord.headPos, minHeadPos) && worldToScreen(minRecord.absPos, minAbsPos) && worldToScreen(maxRecord.headPos, maxHeadPos) && worldToScreen(maxRecord.absPos, maxAbsPos)) {
            val w = (minAbsPos.y - minHeadPos.y) / 4F
            val minMidX = (minAbsPos.x + minHeadPos.x) / 2F
            val maxMidX = (maxAbsPos.x + maxAbsPos.x) / 2F

            var sign = -1

            if (minMidX > maxMidX) {
                sign = 1
            }

            topLeft.set(minHeadPos.x - (w / 3F) * sign, minHeadPos.y, minHeadPos.z)
            topRight.set(maxHeadPos.x + (w / 3F) * sign, maxHeadPos.y, maxHeadPos.z)

            bottomLeft.set(minMidX - (w / 2F) * sign, minAbsPos.y+8F, minAbsPos.z)
            bottomRight.set(maxMidX + (w / 2F) * sign, maxAbsPos.y+8F, maxAbsPos.z)

            shapeRenderer.apply {
                if (!shapeRenderer.isDrawing) {
                    begin()
                }

                color = Color.WHITE

                line(topLeft.x, topLeft.y, topRight.x, topRight.y)
                line(topRight.x, topRight.y, bottomRight.x, bottomRight.y)
                line(bottomRight.x, bottomRight.y, bottomLeft.x, bottomLeft.y)
                line(bottomLeft.x, bottomLeft.y, topLeft.x, topLeft.y)

                color = Color.WHITE

                end()
            }
        }

        for (j in btRecords[i]) {
            j.alpha -= .5F
        }
    }
}