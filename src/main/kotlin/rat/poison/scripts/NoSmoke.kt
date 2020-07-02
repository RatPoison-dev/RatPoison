package rat.poison.scripts
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.utils.Align
import rat.poison.App
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.Entity
import rat.poison.game.entity.EntityType
import rat.poison.game.entity.position
import rat.poison.game.forEntities
import rat.poison.game.netvars.NetVarOffsets
import rat.poison.game.worldToScreen
import rat.poison.utils.Vector
import rat.poison.utils.emptyVector
import rat.poison.utils.varUtil.strToBool
import rat.poison.utils.varUtil.strToColorGDX

data class Smoke(var ttl: Int = 1050, var open: Boolean = true, var ent: Entity = 0L, var latestPos: Vector = emptyVector)
val smokes = Array(512) { Smoke() }

fun noSmoke() = App {
    if (!curSettings["ENABLE_NO_SMOKE"].strToBool()) return@App
    constructSmokes()

    for (i in smokes.indices) {
        if (!smokes[i].open) {
            val inVec = Vector(smokes[i].latestPos.x, smokes[i].latestPos.y, smokes[i].latestPos.z)
            val outVec = Vector()
            if (worldToScreen(inVec, outVec)) {
                val sbText = StringBuilder("Smoke")
                textRenderer.apply {
                    val glyph = GlyphLayout()

                    sb.begin()

                    glyph.setText(textRenderer, sbText, 0, (sbText as CharSequence).length, curSettings["NO_SMOKE_COLOR"].strToColorGDX(), 1F, Align.left, false, null)
                    draw(sb, glyph, outVec.x.toFloat(), outVec.y.toFloat())

                    sb.end()

                }
            }
        }
    }
    for (i in smokes.indices) {
        if (!smokes[i].open) {
            smokes[i].ttl--
            if (smokes[i].ttl <= 0) { //Reset
                smokes[i].apply {
                    latestPos = emptyVector
                    ttl = 1050
                    open = true
                }
            }
        }
    }
}



fun constructSmokes() {
    forEntities body@ {
        if (it.type == EntityType.CSmokeGrenadeProjectile) {
            val entity = it.entity
            val entPos = entity.position()
            csgoEXE[entity + NetVarOffsets.vecOrigin] = 0.0
            csgoEXE[entity + NetVarOffsets.vecOrigin + 4] = 0.0
            csgoEXE[entity + NetVarOffsets.vecOrigin + 8] = 0.0

            var found = false
            for (i in smokes.indices) {
                if (smokes[i].ent == entity && smokes[i].latestPos != entPos && entPos != emptyVector) {
                    smokes[i].apply {
                        ttl = 1050
                        open = false
                        ent = entity
                        latestPos = entPos
                    }
                    found = true
                }
            }
            if (!found) {
                if (entPos != emptyVector) {
                    val idx = emptySlot()
                    smokes[idx].apply {
                        ttl = 1050
                        open = false
                        ent = entity
                        latestPos = entPos
                    }
                }
            }
        }
        false
    }
}

private fun emptySlot(): Int {
    var idx = -1

    for (i in smokes.indices) {
        if (smokes[i].open) {
            idx = i
            break
        }
    }

    return idx
}