package rat.poison.scripts.bspHandling

import com.badlogic.gdx.math.Vector3
import rat.poison.curSettings
import rat.poison.game.entity.EntityType
import rat.poison.game.entity.bones
import rat.poison.game.entity.dead
import rat.poison.game.entity.onGround
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.overlay.App
import rat.poison.overlay.bspVisTime
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.toVector3
import rat.poison.utils.notInGame
import java.util.concurrent.TimeUnit
import kotlin.system.measureNanoTime

fun rayTraceTest() = App {
    bspVisTime = TimeUnit.NANOSECONDS.convert(measureNanoTime {
        if (me.dead() || !curSettings["DEBUG"].strToBool() || notInGame) return@App

        var meVec = me.bones(8).toVector3()
        meVec = Vector3(meVec.x, meVec.y, meVec.z)

        forEntities(EntityType.CCSPlayer) {
            val ent = it.entity
            if (ent == me || !ent.onGround()) return@forEntities

            var entBones = ent.bones(8).toVector3()
            entBones = Vector3(entBones.x, entBones.y, entBones.z)

            bspIsVisible(meVec, Vector3(entBones.x, entBones.y, entBones.z))
        }
    }, TimeUnit.NANOSECONDS)
}