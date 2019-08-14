package rat.poison.game.entity

import rat.poison.game.CSGO.ENTITY_SIZE
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.netvars.NetVarOffsets
import rat.poison.game.netvars.NetVarOffsets.bBombDefused
import rat.poison.game.netvars.NetVarOffsets.flC4Blow
import rat.poison.game.netvars.NetVarOffsets.flDefuseCountDown
import rat.poison.game.offsets.ClientOffsets.dwEntityList
import rat.poison.utils.extensions.uint

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
        clientDLL.uint(dwEntityList + ((owner and 0xFFF) - 1L) * ENTITY_SIZE)
    else 0
}

internal fun Bomb.plantLocation(): String = carrier().location()