package rat.poison.scripts

import com.sun.jna.Memory
import rat.poison.game.CSGO
import rat.poison.game.clientState
import rat.poison.game.hooks.onJoin
import rat.poison.game.offsets.EngineOffsets
import rat.poison.ui.uiPanels.nadeHelperTab

fun detectJoin() = onJoin {
    val strBuf: Memory by lazy {
        Memory(128) //128 str?
    }

    CSGO.csgoEXE.read(clientState + EngineOffsets.dwClientState_MapDirectory, strBuf)
    var mapName = strBuf.getString(0)
    mapName = mapName.replace("maps\\", "").replace(".bsp", "")
    if (mapName.isNotBlank()) {
        nadeHelperTab.nadeHelperFileSelectBox.items.forEach {
            if (mapName == it.replace(".txt", "")) {
                loadPositions(it)
                return@forEach
            }
        }
    }
}