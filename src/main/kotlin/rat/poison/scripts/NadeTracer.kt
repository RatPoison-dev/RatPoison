package rat.poison.scripts

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.MathUtils.clamp
import org.lwjgl.opengl.GL11.glDisable
import org.lwjgl.opengl.GL11.glEnable
import rat.poison.curSettings
import rat.poison.game.entity.EntityType
import rat.poison.game.entity.absPosition
import rat.poison.game.forEntities
import rat.poison.game.worldToScreen
import rat.poison.overlay.App
import rat.poison.settings.MENUTOG
import rat.poison.utils.Vector
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.strToColorGDX
import rat.poison.utils.inGame
import java.util.concurrent.ConcurrentLinkedQueue

val grenadeList = ConcurrentLinkedQueue<Long>()
val positionsList = mutableListOf<MutableList<Vector>>()

private var sync = 0

var arraySize = 5

fun nadeTracer() = App {
    if (!curSettings["NADE_TRACER"].strToBool() || MENUTOG || !curSettings["ENABLE_ESP"].strToBool() || !inGame) return@App

    if (sync >= (curSettings["NADE_TRACER_UPDATE_TIME"].toInt())) {
        arraySize = clamp(curSettings["NADE_TRACER_TIMEOUT"].toInt(), 1, 30)
        forEntities(EntityType.CSmokeGrenadeProjectile, EntityType.CMolotovProjectile, EntityType.CDecoyProjectile, EntityType.CBaseCSGrenadeProjectile) {
            val ent = it.entity
            val entPos = ent.absPosition()

            if (entPos.x in -2F..2F && entPos.y in -2F..2F && entPos.z in -2F..2F) {
                return@forEntities
            }

            if (!grenadeList.contains(ent)) {
                grenadeList.add(ent)
                positionsList.add(mutableListOf())
            }

            val idx = grenadeList.indexOf(ent)

            positionsList[idx].add(entPos)

            if (positionsList[idx].size > arraySize) {
                positionsList[idx].removeAt(0)
            }
        }

        grenadeList.forEach { i ->
            val entPos = i.absPosition()
            val idx = grenadeList.indexOf(i)
            if (entPos.x in -2F..2F && entPos.y in -2F..2F && entPos.z in -2F..2F) {
                if (positionsList[idx].size > 2) {
                    positionsList[idx].removeAt(0)
                } else {
                    grenadeList.remove(i)
                    positionsList.removeAt(idx)
                }
            }
        }
        sync = 0 //Reset
    }
    sync++ //Add 1 to tick

    val alphaMin = 1.0F / arraySize.toFloat()

    for (i in 0 until grenadeList.size) {
        if (positionsList[i].size <= 1) {
            continue
        }

        for (j in 0 until positionsList[i].size-1) {
            val pos1 = positionsList[i][j]
            val pos2 = positionsList[i][j+1]
            val w2s1 = Vector()
            val w2s2 = Vector()

            if (pos1.x in -2F..2F && pos1.y in -2F..2F && pos1.z in -2F..2F) {
                continue
            } else if (pos2.x in -2F..2F && pos2.y in -2F..2F && pos2.z in -2F..2F) {
                continue
            }

            if (worldToScreen(pos1, w2s1) && worldToScreen(pos2, w2s2)) {
                shapeRenderer.apply {
                    if (isDrawing) {
                        end()
                    }

                    begin()
                    glEnable(GL20.GL_BLEND)

                    val c = curSettings["NADE_TRACER_COLOR"].strToColorGDX()
                    c.a = 1F - alphaMin * j

                    color = c

                    line(w2s1.x, w2s1.y, w2s2.x, w2s2.y)

                    glDisable(GL20.GL_BLEND)
                    end()
                }
            }
        }
    }
}