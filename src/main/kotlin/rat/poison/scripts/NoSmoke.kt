package rat.poison.scripts
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.EntityType
import rat.poison.game.forEntities
import rat.poison.game.netvars.NetVarOffsets
import rat.poison.utils.every
import rat.poison.utils.varUtil.strToBool

fun noSmoke() = every(10) {
    if (!curSettings["ENABLE_NO_SMOKE"].strToBool()) return@every

    forEntities body@{
        val entity = it.entity
        when (it.type) {
            EntityType.CSmokeGrenadeProjectile -> {
                csgoEXE[entity + NetVarOffsets.vecOrigin] = 0.0
                csgoEXE[entity + NetVarOffsets.vecOrigin + 4] = 0.0
                csgoEXE[entity + NetVarOffsets.vecOrigin + 8] = 0.0
            }
        }
        false
    }
}