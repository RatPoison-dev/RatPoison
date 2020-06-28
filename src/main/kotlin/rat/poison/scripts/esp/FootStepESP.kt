package rat.poison.scripts.esp

import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.Align
import rat.poison.App
import rat.poison.checkFlags
import rat.poison.curSettings
import rat.poison.game.entity.*
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.game.forEntities
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.me
import rat.poison.game.w2sViewMatrix
import rat.poison.game.worldToScreen
import rat.poison.toMatrix4
import rat.poison.utils.Vector
import rat.poison.utils.distanceTo
import rat.poison.utils.varUtil.cToFloat
import rat.poison.utils.varUtil.strToBool
import rat.poison.utils.varUtil.strToColorGDX
import kotlin.math.pow
import kotlin.math.sqrt

val footSteps = Array(256) { FootStep() }
data class FootStep(var x: Double = 0.0, var y: Double = 0.0, var z: Double = 0.0,
                            var ttl: Int = curSettings["FOOTSTEP_TTL"].toInt(),
                            var open: Boolean = true, var myTeam: Boolean = false, var from: Entity = 0L)

fun footStepEsp() = App {
    if (!curSettings["ENABLE_ESP"].strToBool() || !checkFlags("ENABLE_ESP") || !curSettings["ENABLE_FOOTSTEPS"].strToBool() || !checkFlags("ENABLE_FOOTSTEPS")) return@App
    for (i in footSteps.indices) {
        if (!footSteps[i].open) {
            val color = if (footSteps[i].myTeam) {
                curSettings["FOOTSTEP_TEAM_COLOR"].strToColorGDX()
            } else {
                curSettings["FOOTSTEP_ENEMY_COLOR"].strToColorGDX()
            }
            color.a = footSteps[i].ttl / curSettings["FOOTSTEP_TTL"].toFloat()

            if (curSettings["FOOTSTEP_TYPE"].toInt() == 1) {
                //As text
                val inVec = Vector(footSteps[i].x, footSteps[i].y, footSteps[i].z)
                val outVec = Vector()
                if (worldToScreen(inVec, outVec)) {
                    val sbText = StringBuilder("Step")
                    textRenderer.apply {
                        val glyph = GlyphLayout()

                        sb.begin()

                        glyph.setText(textRenderer, sbText, 0, (sbText as CharSequence).length, color, 1F, Align.left, false, null)
                        draw(sb, glyph, outVec.x.toFloat(), outVec.y.toFloat())

                        sb.end()
                    }
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
                    circle(footSteps[i].x.toFloat(), footSteps[i].y.toFloat(), (curSettings["FOOTSTEP_TTL"].toFloat() - footSteps[i].ttl.toFloat()) + 10F)
                    gameMatrix.translate(0F, 0F, -footSteps[i].z.cToFloat())

                    end()
                }
                shapeRenderer.projectionMatrix = oldMatrix
            }
        }
    }
}

fun constructSteps() {
    forEntities(ccsPlayer) {
        val ent = it.entity
        if (ent == me || ent.dead() || ent.dormant()) return@forEntities false

        val inMyTeam = ent.team() == me.team()

        //Team check
        if (inMyTeam && !curSettings["FOOTSTEP_TEAM"].strToBool()) return@forEntities false
        else if (!inMyTeam && !curSettings["FOOTSTEP_ENEMY"].strToBool()) return@forEntities false

        val entVel = ent.velocity()
        val entMag = sqrt(entVel.x.pow(2.0) + entVel.y.pow(2.0) + entVel.z.pow(2.0))
        val entPos = ent.absPosition()
        val distance = me.position().distanceTo(entPos)
        if (entMag >= 150 && (curSettings["ENABLE_FOOTSTEPS_RANGE"].strToBool() && distance <= curSettings["FOOTSTEPS_RANGE"].toDouble()) || !curSettings["ENABLE_FOOTSTEPS_RANGE"].strToBool()) {

            val idx = emptySlot()
            footSteps[idx].apply {
                x = entPos.x
                y = entPos.y
                z = entPos.z
                ttl = curSettings["FOOTSTEP_TTL"].toInt()
                open = false
                myTeam = inMyTeam
                from = ent
            }
        }

        false
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