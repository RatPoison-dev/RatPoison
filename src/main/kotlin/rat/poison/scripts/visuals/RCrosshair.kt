package rat.poison.scripts.visuals

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import rat.poison.crosshairArray
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.game.entity.isScoped
import rat.poison.game.entity.punch
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets
import rat.poison.overlay.App
import rat.poison.scripts.aim.meCurWep
import rat.poison.settings.MENUTOG
import rat.poison.ui.uiWindows.rcsTab
import rat.poison.utils.common.Vector
import rat.poison.utils.common.inGame
import java.lang.Math.toRadians
import kotlin.math.*

private val mePunchVec = Vector()
internal fun rCrosshair() = App {
    if (!curSettings.bool["ENABLE_VISUALS"] || !inGame) return@App

    val eRC = curSettings.bool["ENABLE_RCROSSHAIR"]
    val eSC = curSettings.bool["RCROSSHAIR_SCOPE_COMPATIBLE"]
    val scoped = me.isScoped() || !meCurWep.sniper

    if (!(eRC || eSC) || (MENUTOG && uiMenu.activeTab == rcsTab)) return@App
    if (!eSC && !scoped) return@App

    //Crosshair X/Y offset
    val rccXo = curSettings.float["RCROSSHAIR_XOFFSET"]
    val rccYo = curSettings.float["RCROSSHAIR_YOFFSET"]

    //Crosshair FOV modifier
    val curFov = csgoEXE.int(me + NetVarOffsets.m_iDefaultFov)
    val rccFov1 = atan((gameWidth.toFloat()/gameHeight.toFloat()) * 0.75 * tan(toRadians(curFov/2.0)))
    val rccFov2 = (gameWidth/2) / tan(rccFov1).toFloat()

    val xx: Float
    val yy: Float

    if (eRC && !(eSC && meCurWep.sniper)) {
        val punch = me.punch(mePunchVec)

        //Center
        xx = (gameWidth / 2) - tan(toRadians(punch.y.toDouble())).toFloat() * rccFov2 + rccXo
        yy = (gameHeight / 2) - tan(toRadians(punch.x.toDouble())).toFloat() * rccFov2 + rccYo
    } else {
        //Center
        xx = gameWidth / 2F + rccXo
        yy = gameHeight / 2F + rccYo
    }

    if (!shapeRenderer.isDrawing) {
        shapeRenderer.begin()
    }

    shapeRenderer.set(ShapeRenderer.ShapeType.Filled)
    shapeRenderer.color = curSettings.colorGDX["RCROSSHAIR_COLOR"]

    val rCrosshairBuilderRes = curSettings.int["RCROSSHAIR_BUILDER_RESOLUTION"]
    val rCrosshairBoxSize = curSettings.float["RCROSSHAIR_BUILDER_SIZE"]
    val halfXY = (rCrosshairBoxSize * rCrosshairBuilderRes) / 2F

    for (i in 0 until rCrosshairBuilderRes) { //row
        for (j in 0 until rCrosshairBuilderRes) { //column
            val row = i + 1

            val bool = crosshairArray[i * rCrosshairBuilderRes + j]

            if (bool) {
                shapeRenderer.box(xx - halfXY + (rCrosshairBoxSize * j), yy - halfXY + (rCrosshairBoxSize * rCrosshairBuilderRes) - (rCrosshairBoxSize * row), 1F, rCrosshairBoxSize, rCrosshairBoxSize, 1F)
            }
        }
    }

    shapeRenderer.end()
}