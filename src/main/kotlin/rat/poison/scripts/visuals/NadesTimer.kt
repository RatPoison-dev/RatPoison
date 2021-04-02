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
private val vec = Vector()
private val positionVector = Vector()
private val stringBuilder = StringBuilder()
private const val id = "nadestimer"
fun nadesTimer() = App {
    if (!curSettings.bool["SMOKE_WEAR_OFF_TIME"] || !inGame) return@App

    forEntities(EntityType.CSmokeGrenadeProjectile, identifier = id) {
        val ent = it.entity
        if (!ent.didEffect()) return@forEntities

        val seconds = ent.timeLeftToDisappear()
        if (seconds <= 0.0) return@forEntities
        if (worldToScreen(ent.position(positionVector), vec)) {
            sb.apply {
                if (!sb.isDrawing) sb.begin()

                textRenderer.color = curSettings.colorGDX["SMOKE_WEAR_OFF_TIME_COLOR"]
                stringBuilder.clear()
                stringBuilder.appendLine("SMOKE")
                stringBuilder.append(String.format("%.2f", seconds))
                stringBuilder.append(" s")
                textRenderer.draw(sb, stringBuilder, vec.x, vec.y, 1F, Align.left, false)
                sb.end()
            }
        }
    }
}