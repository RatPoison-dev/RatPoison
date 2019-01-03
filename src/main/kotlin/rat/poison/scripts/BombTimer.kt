package rat.poison.scripts

import com.badlogic.gdx.graphics.Color
import rat.poison.game.CSGO
import rat.poison.game.entity.*
import rat.poison.game.entityByType
import rat.poison.game.offsets.EngineOffsets
import rat.poison.overlay.RatPoisonOverlay
import rat.poison.settings.ENABLE_BOMB_TIMER
import rat.poison.utils.every

private var bombState = BombState()

fun bombTimer() {
    bombUpdater()

    RatPoisonOverlay {
        bombState.apply {
            if (ENABLE_BOMB_TIMER && this.planted) {
                batch.begin()
                textRenderer.color = Color.ORANGE
                textRenderer.draw(batch, bombState.toString(), 20F, 500F)
                batch.end()
            }
        }
    }
}

private fun currentGameTicks(): Float = CSGO.engineDLL.float(EngineOffsets.dwGlobalVars + 16)

fun bombUpdater() = every(8, true) {
    if (!ENABLE_BOMB_TIMER) return@every

    val time = currentGameTicks()
    val bomb: Entity = entityByType(EntityType.CPlantedC4)?.entity ?: -1L

    bombState.apply {
        timeLeftToExplode = bomb.blowTime() - time
        hasBomb = bomb > 0 && !bomb.dormant()
        planted = hasBomb && !bomb.defused() && timeLeftToExplode > 0

        if (planted) {
            if (location.isEmpty()) location = bomb.plantLocation()

            val defuser = bomb.defuser()
            timeLeftToDefuse = bomb.defuseTime() - time
            gettingDefused = defuser > 0 && timeLeftToDefuse > 0
            canDefuse = gettingDefused && (timeLeftToExplode > timeLeftToDefuse)
        } else {
            location = ""
            canDefuse = false
            gettingDefused = false
        }
    }
}

private data class BombState(var hasBomb: Boolean = false,
                             var planted: Boolean = false,
                             var canDefuse: Boolean = false,
                             var gettingDefused: Boolean = false,
                             var timeLeftToExplode: Float = -1f,
                             var timeLeftToDefuse: Float = -1f,
                             var location: String = "") {

    private val sb = StringBuilder()

    override fun toString(): String {
        sb.setLength(0)
        sb.append("Bomb Planted!\n")

        sb.append("TimeToExplode : ${formatFloat(timeLeftToExplode)} \n")

        if (location.isNotBlank())
            sb.append("Location : $location \n")
        if (gettingDefused) {
//            sb.append("GettingDefused : $gettingDefused \n")
            sb.append("CanDefuse : $canDefuse \n")
            // Redundant as the UI already shows this, but may have a use case I'm missing
            sb.append("TimeToDefuse : ${formatFloat(timeLeftToDefuse)} ")
        }
        return sb.toString()
    }


    private fun formatFloat(f: Float): String {
        return "%.3f".format(f)
    }
}
