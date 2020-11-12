package rat.poison.scripts.visuals

import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.Align
import rat.poison.*
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.game.w2sViewMatrix
import rat.poison.game.worldToScreen
import rat.poison.overlay.App
import rat.poison.utils.Vector
import rat.poison.utils.every
import rat.poison.utils.generalUtil.cToFloat
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.strToColorGDX
import rat.poison.utils.generalUtil.toMatrix4
import kotlin.math.pow
import kotlin.math.sqrt

val footSteps = Array(256) { FootStep() }
data class FootStep(var x: Float = 0F, var y: Float = 0F, var z: Float = 0F,
                            var ttl: Int = curSettings["FOOTSTEP_TTL"].toInt(),
                            var open: Boolean = true, var myTeam: Boolean = false,
                            var ent: Entity = 0L)
private var stepTimer = 0

fun footStepEsp() {
    constructSteps()

    if (curSettings["MENU"].strToBool()) {
        runFootSteps()
    }
}


fun runFootSteps() = App {
    if (!curSettings["ENABLE_ESP"].strToBool()) return@App

    if (!curSettings["ENABLE_FOOTSTEPS"].strToBool()) return@App

    for (i in footSteps.indices) {
        if (!footSteps[i].open) {
            val color = if (footSteps[i].myTeam) {
                curSettings["FOOTSTEP_TEAM_COLOR"].strToColorGDX()
            } else {
                curSettings["FOOTSTEP_ENEMY_COLOR"].strToColorGDX()
            }
            color.a = footSteps[i].ttl / curSettings["FOOTSTEP_TTL"].toFloat()

            if ((footSteps[i].myTeam && !curSettings["FOOTSTEP_TEAM"].strToBool()) || (!footSteps[i].myTeam && !curSettings["FOOTSTEP_ENEMY"].strToBool())) {
                continue
            }

            if (curSettings["FOOTSTEP_TYPE"].toInt() == 1) {
                //As text
                val inVec = Vector(footSteps[i].x, footSteps[i].y, footSteps[i].z)
                val outVec = Vector()
                if (worldToScreen(inVec, outVec)) {
                    val sbText = StringBuilder("Step")

                    sb.begin()

                    textRenderer.color = color
                    textRenderer.draw(sb, sbText, outVec.x, outVec.y, 1F, Align.left, false)

                    sb.end()
                }
            } else {
                //As circle
                val oldMatrix = Matrix4(shapeRenderer.projectionMatrix.values)
                shapeRenderer.apply {
                    if (isDrawing) {
                        end()
                    }

                    val gameMatrix = w2sViewMatrix.toMatrix4()

                    begin()
                    this.color = color

                    //Circle at position
                    gameMatrix.translate(0F, 0F, footSteps[i].z.cToFloat())
                    projectionMatrix = gameMatrix
                    circle(footSteps[i].x, footSteps[i].y, (curSettings["FOOTSTEP_TTL"].toFloat() - footSteps[i].ttl.toFloat()) + 10F)
                    gameMatrix.translate(0F, 0F, -footSteps[i].z.cToFloat())

                    end()
                }

                shapeRenderer.projectionMatrix = oldMatrix
            }
        }
    }
}

private fun constructSteps() = every(10) {
    stepTimer+= 1
    if (stepTimer >= curSettings["FOOTSTEP_UPDATE"].toInt()) {
        forEntities(EntityType.CCSPlayer) {
            val ent = it.entity
            if (ent == me || ent.dead() || ent.dormant()) return@forEntities

            val inMyTeam = ent.team() == me.team()

            val entVel = ent.velocity()
            val entMag = sqrt(entVel.x.pow(2F) + entVel.y.pow(2F) + entVel.z.pow(2F))

            if (entMag >= 150) {
                val entPos = ent.absPosition()

                val idx = emptySlot()
                if (idx != -1) {
                    footSteps[idx].apply {
                        x = entPos.x
                        y = entPos.y
                        z = entPos.z
                        ttl = curSettings["FOOTSTEP_TTL"].toInt()
                        open = false
                        myTeam = inMyTeam
                        this.ent = ent
                    }
                }
            }
        }

        for (i in footSteps.indices) {
            footSteps[i].ttl -= 10
            if (footSteps[i].ttl <= 0) { //Reset
                footSteps[i].apply {
                    x = 0F
                    y = 0F
                    z = 0F
                    ttl = curSettings["FOOTSTEP_TTL"].toInt()
                    open = true
                    this.ent = 0L
                }
            }
        }

        stepTimer = 0
    }
}

private fun emptySlot(): Int {
    var idx = -1

    for (i in footSteps.indices) {
        if (footSteps[i].open) {
            idx = i
            break
        }
    }

    return idx
}