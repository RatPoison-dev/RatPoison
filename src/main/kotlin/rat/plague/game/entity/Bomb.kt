

package rat.plague.game.entity

import rat.plague.game.CSGO
import rat.plague.game.CSGO.ENTITY_SIZE
import rat.plague.game.CSGO.clientDLL
import rat.plague.game.CSGO.csgoEXE
import rat.plague.game.CSGO.engineDLL
import rat.plague.game.netvars.NetVarOffsets
import rat.plague.game.netvars.NetVarOffsets.bBombDefused
import rat.plague.game.netvars.NetVarOffsets.flC4Blow
import rat.plague.game.netvars.NetVarOffsets.flDefuseCountDown
import rat.plague.game.netvars.NetVarOffsets.hOwnerEntity
import rat.plague.game.netvars.NetVarOffsets.szLastPlaceName
import rat.plague.game.offsets.ClientOffsets
import rat.plague.game.offsets.ClientOffsets.dwEntityList
import rat.plague.game.offsets.EngineOffsets.dwGlobalVars
import rat.plague.utils.extensions.uint

typealias Bomb = Long

internal fun Bomb.defused(): Boolean = csgoEXE.boolean(this + bBombDefused)

internal fun Bomb.blowTime() = csgoEXE.float(this + flC4Blow)

internal fun Bomb.defuseTime() = csgoEXE.float(this + flDefuseCountDown)

internal fun Bomb.defuserPointer(): Long = csgoEXE.uint(this + NetVarOffsets.hBombDefuser)

internal fun Bomb.defuser(): Player {
    val defuserPointer = defuserPointer()
    return if (defuserPointer > 0) clientDLL.uint(dwEntityList + ((defuserPointer and 0xFFF) - 1L) * ENTITY_SIZE) else 0
}

internal fun Bomb.owner() = csgoEXE.uint(this + NetVarOffsets.hOwnerEntity)

internal fun Bomb.carrier(): Player {
    val owner = owner()
    return if (owner > 0)
        CSGO.clientDLL.uint(ClientOffsets.dwEntityList + ((owner and 0xFFF) - 1L) * ENTITY_SIZE)
    else 0
}

internal fun Bomb.plantLocation(): String = carrier().location()