package rat.poison.scripts

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils.clamp
import com.badlogic.gdx.utils.Align
import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.punch
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.m_flHealthShotBoostExpirationTime
import rat.poison.game.netvars.NetVarOffsets.m_totalHitsOnServer
import rat.poison.overlay.App
import rat.poison.scripts.aim.meDead
import rat.poison.settings.MENUTOG
import rat.poison.utils.common.Vector
import rat.poison.utils.common.inGame

var hitMarkerAlpha = 0F
var hitMarkerCombo = 0
private var totalHits = 0

private val punchVec = Vector()
private val hitMarkerComboSB = StringBuilder()
fun hitMarker() = App {
    if ((!curSettings.bool["ENABLE_HITMARKER"] && !curSettings.bool["HITMARKER_COMBO"]) || !curSettings.bool["ENABLE_ESP"] || MENUTOG || meDead || !inGame) return@App

    val curHits = csgoEXE.int(me + m_totalHitsOnServer)

    if (curHits == 0) {
        totalHits = 0
    }
    else if (totalHits != curHits)
    {
        hitMarkerAlpha = 1F
        hitMarkerCombo++
        totalHits = curHits

        if (curSettings.bool["ENABLE_ADRENALINE"]) {
            val cGT = currentGameTicks()
            csgoEXE[me + m_flHealthShotBoostExpirationTime] = cGT + clamp((hitMarkerCombo * .5F), 0F, 2.5F)
        }
    }

    if (hitMarkerAlpha > 0F) {
        val x: Float
        val y: Float

        val rccXo = curSettings.float["RCROSSHAIR_XOFFSET"]
        val rccYo = curSettings.float["RCROSSHAIR_YOFFSET"]

        if (curSettings.bool["HITMARKER_RECOIL_POSITION"]) {
            val punch = me.punch(punchVec)

            //Center
            x = CSGO.gameWidth / 2 - ((CSGO.gameWidth / 95F) * punch.y) + rccXo
            y = CSGO.gameHeight / 2 - ((CSGO.gameHeight / 95F) * punch.x) + rccYo

        } else {
            //Center
            x = CSGO.gameWidth / 2 + rccXo
            y = CSGO.gameHeight / 2 + rccYo
        }

        val hMS = curSettings.int["HITMARKER_SPACING"]
        val hMLL = curSettings.int["HITMARKER_LENGTH"]
        val hMLW = curSettings.float["HITMARKER_WIDTH"]

        shapeRenderer.apply {
            if (isDrawing) {
                end()
            }

            begin()
            set(ShapeRenderer.ShapeType.Filled)

            var col : rat.poison.game.Color

            if (curSettings.bool["ENABLE_HITMARKER"]) {
                if (curSettings.bool["HITMARKER_OUTLINE"]) { //Outline
                    col = curSettings.color["HITMARKER_OUTLINE_COLOR"]
                    setColor(col.red / 255F, col.green / 255F, col.blue / 255F, hitMarkerAlpha)

                    rectLine(x + hMS - 1F, y + hMS - 1F, x + hMS + hMLL + 1F, y + hMS + hMLL + 1F, hMLW + 2F) //Top right
                    rectLine(x - hMS + 1F, y + hMS - 1F, x - hMS - hMLL - 1F, y + hMS + hMLL + 1F, hMLW + 3F) //Top left
                    rectLine(x + hMS - 1F, y - hMS + 1F, x + hMS + hMLL + 1F, y - hMS - hMLL - 1F, hMLW + 3F) //Bottom right
                    rectLine(x - hMS + 1F, y - hMS + 1F, x - hMS - hMLL - 1F, y - hMS - hMLL - 1F, hMLW + 2F) //Bottom left
                }


                col = curSettings.color["HITMARKER_COLOR"]
                setColor(col.red / 255F, col.green / 255F, col.blue / 255F, hitMarkerAlpha)

                rectLine(x + hMS, y + hMS, x + hMS + hMLL, y + hMS + hMLL, hMLW) //Top right
                rectLine(x - hMS, y + hMS, x - hMS - hMLL, y + hMS + hMLL, hMLW + 1F) //Top left
                rectLine(x + hMS, y - hMS, x + hMS + hMLL, y - hMS - hMLL, hMLW + 1F) //Bottom right
                rectLine(x - hMS, y - hMS, x - hMS - hMLL, y - hMS - hMLL, hMLW) //Bottom left
            }

            set(ShapeRenderer.ShapeType.Line)
            end()
        }

        if (curSettings.bool["HITMARKER_COMBO"]) {
            if (hitMarkerCombo >= 2) {
                if (sb.isDrawing) {
                    sb.end()
                }

                sb.begin()

                hitMarkerComboSB.clear().append("x").append(hitMarkerCombo)

                val col = curSettings.colorGDX["HITMARKER_COMBO_COLOR"]
                col.a = hitMarkerAlpha

                textRenderer.color = col
                textRenderer.draw(sb, hitMarkerComboSB, x + hMS + hMLL, y - hMS - hMLL, 1F, Align.center, false)

                sb.end()
            }
        }

        hitMarkerAlpha -= .01F
    } else {
        hitMarkerCombo = 0
    }
}