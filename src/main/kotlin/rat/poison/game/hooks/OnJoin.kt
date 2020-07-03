package rat.poison.game.hooks

import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.CSGO.engineDLL
import rat.poison.game.offsets.EngineOffsets.dwClientState
import rat.poison.game.offsets.EngineOffsets.dwClientState_State
import rat.poison.utils.ObservableBoolean
import rat.poison.utils.hook

val isOnServer = ObservableBoolean( { csgoEXE.int(engineDLL.int(dwClientState) + dwClientState_State) == 6 } )

val onJoin = hook(250) {
    isOnServer.update()
    if (isOnServer.justBecameTrue) {
        return@hook true
    }
    return@hook false
}