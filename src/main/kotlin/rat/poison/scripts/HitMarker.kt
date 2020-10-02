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
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.strToColor
import rat.poison.utils.generalUtil.strToColorGDX
import rat.poison.utils.inGame

var hitMarkerAlpha = 0F
var hitMarkerCombo = 0
private var totalHits = 0

fun hitMarker() = App {
    if ((!curSettings["ENABLE_HITMARKER"].strToBool() && !curSettings["HITMARKER_COMBO"].strToBool()) || !curSettings["ENABLE_ESP"].strToBool() || MENUTOG || meDead || !inGame) return@App

    val curHits = csgoEXE.int(me + m_totalHitsOnServer)

    if (curHits == 0) {
        totalHits = 0
    }
    else if (totalHits != curHits)
    {
        hitMarkerAlpha = 1F
        hitMarkerCombo++
        totalHits = curHits

        if (curSettings["ENABLE_ADRENALINE"].strToBool()) {
            val cGT = currentGameTicks()
            csgoEXE[me + m_flHealthShotBoostExpirationTime] = cGT + clamp((hitMarkerCombo * .5F), 0F, 2.5F)
        }
    }

    if (hitMarkerAlpha > 0F) {
        val x: Float
        val y: Float

        val rccXo = curSettings["RCROSSHAIR_XOFFSET"].toFloat()
        val rccYo = curSettings["RCROSSHAIR_YOFFSET"].toFloat()

        if (curSettings["HITMARKER_RECOIL_POSITION"].strToBool()) {
            val punch = me.punch()

            //Center
            x = CSGO.gameWidth / 2 - ((CSGO.gameWidth / 95F) * punch.y) + rccXo
            y = CSGO.gameHeight / 2 - ((CSGO.gameHeight / 95F) * punch.x) + rccYo

        } else {
            //Center
            x = CSGO.gameWidth / 2 + rccXo
            y = CSGO.gameHeight / 2 + rccYo
        }

        val hMS = curSettings["HITMARKER_SPACING"].toInt()
        val hMLL = curSettings["HITMARKER_LENGTH"].toInt()
        val hMLW = curSettings["HITMARKER_WIDTH"].toFloat()

        shapeRenderer.apply {
            if (isDrawing) {
                end()
            }

            begin()
            set(ShapeRenderer.ShapeType.Filled)

            var col : rat.poison.game.Color

            if (curSettings["ENABLE_HITMARKER"].strToBool()) {
                if (curSettings["HITMARKER_OUTLINE"].strToBool()) { //Outline
                    col = curSettings["HITMARKER_OUTLINE_COLOR"].strToColor()
                    setColor(col.red / 255F, col.green / 255F, col.blue / 255F, hitMarkerAlpha)

                    rectLine(x + hMS - 1F, y + hMS - 1F, x + hMS + hMLL + 1F, y + hMS + hMLL + 1F, hMLW + 2F) //Top right
                    rectLine(x - hMS + 1F, y + hMS - 1F, x - hMS - hMLL - 1F, y + hMS + hMLL + 1F, hMLW + 3F) //Top left
                    rectLine(x + hMS - 1F, y - hMS + 1F, x + hMS + hMLL + 1F, y - hMS - hMLL - 1F, hMLW + 3F) //Bottom right
                    rectLine(x - hMS + 1F, y - hMS + 1F, x - hMS - hMLL - 1F, y - hMS - hMLL - 1F, hMLW + 2F) //Bottom left
                }


                col = curSettings["HITMARKER_COLOR"].strToColor()
                setColor(col.red / 255F, col.green / 255F, col.blue / 255F, hitMarkerAlpha)

                rectLine(x + hMS, y + hMS, x + hMS + hMLL, y + hMS + hMLL, hMLW) //Top right
                rectLine(x - hMS, y + hMS, x - hMS - hMLL, y + hMS + hMLL, hMLW + 1F) //Top left
                rectLine(x + hMS, y - hMS, x + hMS + hMLL, y - hMS - hMLL, hMLW + 1F) //Bottom right
                rectLine(x - hMS, y - hMS, x - hMS - hMLL, y - hMS - hMLL, hMLW) //Bottom left
            }

            set(ShapeRenderer.ShapeType.Line)
            end()
        }

        if (curSettings["HITMARKER_COMBO"].strToBool()) {
            if (hitMarkerCombo >= 2) {
                if (sb.isDrawing) {
                    sb.end()
                }

                sb.begin()

                val hitMarkerComboSB = StringBuilder()

                hitMarkerComboSB.append("x$hitMarkerCombo")

                val col = curSettings["HITMARKER_COMBO_COLOR"].strToColorGDX()
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