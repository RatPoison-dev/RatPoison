package rat.poison.scripts

import rat.poison.game.entity.EntityType
import rat.poison.game.entity.name
import rat.poison.game.entity.rank
import rat.poison.game.forEntities
import rat.poison.utils.every
import rat.poison.utils.notInGame

//private val players = Array(32){""}
//private val playerNames = ArrayList<Pair<Int, String>>()
//private val playerRanks = ArrayList<Pair<Int, String>>()

//Incomplete, can't test
internal fun ranks() = every(1000) {
    if (notInGame) return@every

    //playerNames.clear()
    //playerRanks.clear()

    forEntities body@{
        val entity = it.entity

        if (it.type == EntityType.CCSPlayer) {
            //val entIndex = csgoEXE.readIndex(entity + dwIndex)
            val entName = entity.name()
            val entRank = entity.rank()

            println("$entName: $entRank") //havent tested if accurate yet
        }

        return@body false
    }
}