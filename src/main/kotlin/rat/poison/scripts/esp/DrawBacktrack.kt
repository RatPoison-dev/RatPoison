package rat.poison.scripts.esp

import com.badlogic.gdx.graphics.Color
import org.jire.arrowhead.keyPressed
import rat.poison.App
import rat.poison.checkFlags
import rat.poison.curSettings
import rat.poison.game.entity.dead
import rat.poison.game.entity.weapon
import rat.poison.game.me
import rat.poison.game.worldToScreen
import rat.poison.scripts.attemptBacktrack
import rat.poison.scripts.btRecords
import rat.poison.settings.MENUTOG
import rat.poison.utils.Angle
import rat.poison.utils.Vector
import rat.poison.utils.notInGame
import rat.poison.utils.varUtil.strToBool

fun drawBacktrack() = App {
    if (MENUTOG) return@App
    if (me.dead()) return@App
    if (notInGame || !curSettings["BACKTRACK_VISUALIZE"].strToBool() || !checkFlags("ENABLE_ESP") || !curSettings["ENABLE_ESP"].strToBool() || !curSettings["ENABLE_BACKTRACK"].strToBool()) return@App

    if (!checkFlags("ENABLE_BACKTRACK")) return@App

    val meWep = me.weapon()
    var prefix = ""
    when {
        meWep.pistol -> { prefix = "PISTOL_" }
        meWep.rifle -> { prefix = "RIFLE_" }
        meWep.shotgun -> { prefix = "SHOTGUN_" }
        meWep.sniper -> { prefix = "SNIPER_" }
        meWep.smg -> { prefix = "SMG_" }
    }

    var enableNeck = false; var enableChest = false; var enableStomach = false; var enablePelvis = false

    if (meWep.gun) { //Not 100% this applies to every 'gun'
        enableNeck = curSettings[prefix + "BACKTRACK_NECK"].strToBool()
        enableChest = curSettings[prefix + "BACKTRACK_CHEST"].strToBool()
        enableStomach = curSettings[prefix + "BACKTRACK_STOMACH"].strToBool()
        enablePelvis = curSettings[prefix + "BACKTRACK_PELVIS"].strToBool()
    }

    for (i in 0 until 63) {
        for (j in 0 until 12) {
            val neckPos = btRecords[i][j].neckPos
            val chestPos = btRecords[i][j].chestPos
            val stomachPos = btRecords[i][j].stomachPos
            val pelvisPos = btRecords[i][j].pelvisPos
            val alpha = btRecords[i][j].alpha

            if (alpha > 0) {
                val w2s = Vector()

                shapeRenderer.apply {
                    if (shapeRenderer.isDrawing) {
                        end()
                    }

                    begin()

                    color = Color(1f, 1f, 1f, alpha / 100f)

                    if (enableNeck && neckPos != Angle()) {
                        if (worldToScreen(neckPos, w2s)) {
                            circle(w2s.x.toFloat(), w2s.y.toFloat(), 2f)
                        }
                    }

                    if (enableChest && chestPos != Angle()) {
                        if (worldToScreen(chestPos, w2s)) {
                            circle(w2s.x.toFloat(), w2s.y.toFloat(), 2f)
                        }
                    }

                    if (enableStomach && stomachPos != Angle()) {
                        if (worldToScreen(stomachPos, w2s)) {
                            circle(w2s.x.toFloat(), w2s.y.toFloat(), 2f)
                        }
                    }

                    if (enablePelvis && pelvisPos != Angle()) {
                        if (worldToScreen(pelvisPos, w2s)) {
                            circle(w2s.x.toFloat(), w2s.y.toFloat(), 2f)
                        }
                    }

                    btRecords[i][j].alpha -= .5f

                    color = Color.WHITE

                    end()
                }
            }
        }
    }
}