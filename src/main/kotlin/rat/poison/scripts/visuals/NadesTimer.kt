package rat.poison.scripts.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Align
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.EntityType
import rat.poison.game.entity.position
import rat.poison.game.forEntities
import rat.poison.game.netvars.NetVarOffsets
import rat.poison.game.netvars.NetVarOffsets.nSmokeEffectTickBegin
import rat.poison.game.worldToScreen
import rat.poison.overlay.App
import rat.poison.scripts.gvars
import rat.poison.settings.SMOKE_EFFECT_TIME
import rat.poison.utils.Vector
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.strToColorGDX
import rat.poison.utils.inGame

//https://github.com/WarezBox/aimware/blob/master/%5BLuaScript%20V5%5D%20Smoke%20timer%20ESP

fun nadesTimer() = App {
    if (!curSettings["SMOKE_WEAR_OFF_TIME"].strToBool() || !inGame) return@App

    forEntities(EntityType.CSmokeGrenadeProjectile) {
        val ent = it.entity
        if (!csgoEXE.boolean(ent + NetVarOffsets.bDidSmokeEffect)) return@forEntities

        val beginTick = csgoEXE.int(ent + nSmokeEffectTickBegin)
        val diff = gvars.tickCount - beginTick
        val seconds = (SMOKE_EFFECT_TIME - (diff * gvars.intervalPerTick)).toDouble().coerceAtLeast(0.0)
        if (seconds == 0.0) return@forEntities
        val vec = Vector()
        if (worldToScreen(ent.position(), vec)) {
            shapeRenderer.apply {
                if (isDrawing) {
                    end()
                }
                sb.begin()
                textRenderer.color = curSettings["SMOKE_WEAR_OFF_TIME_COLOR"].strToColorGDX()
                val sbText = StringBuilder()
                sbText.append("SMOKE\n${String.format("%.2f", seconds)} s")
                textRenderer.draw(sb, sbText, vec.x, vec.y, 1F, Align.left, false)
                sb.end()
            }
        }
    }
}