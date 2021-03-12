package rat.poison.scripts.visuals

import com.badlogic.gdx.utils.Align
import rat.poison.curSettings
import rat.poison.game.entity.EntityType
import rat.poison.game.entity.didEffect
import rat.poison.game.entity.position
import rat.poison.game.entity.timeLeftToDisappear
import rat.poison.game.forEntities
import rat.poison.game.worldToScreen
import rat.poison.overlay.App
import rat.poison.utils.Vector
import rat.poison.utils.inGame

//https://github.com/WarezBox/aimware/blob/master/%5BLuaScript%20V5%5D%20Smoke%20timer%20ESP

fun nadesTimer() = App {
    if (!curSettings.bool["SMOKE_WEAR_OFF_TIME"] || !inGame) return@App

    forEntities(EntityType.CSmokeGrenadeProjectile) {
        val ent = it.entity
        if (!ent.didEffect()) return@forEntities

        val seconds = ent.timeLeftToDisappear()
        if (seconds <= 0.0) return@forEntities
        val vec = Vector()
        if (worldToScreen(ent.position(), vec)) {
            shapeRenderer.apply {
                if (isDrawing) {
                    end()
                }
                sb.begin()
                textRenderer.color = curSettings.colorGDX["SMOKE_WEAR_OFF_TIME_COLOR"]
                val sbText = StringBuilder()
                sbText.append("SMOKE\n${String.format("%.2f", seconds)} s")
                textRenderer.draw(sb, sbText, vec.x, vec.y, 1F, Align.left, false)
                sb.end()
            }
        }
    }
}