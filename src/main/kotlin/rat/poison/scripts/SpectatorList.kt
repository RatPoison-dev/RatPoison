//Thanks to Mr. Noad for help

package rat.poison.scripts

import rat.poison.App.haveTarget
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.game.entity.dormant
import rat.poison.game.entity.hltv
import rat.poison.game.entity.isSpectating
import rat.poison.game.entity.name
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.m_hObserverTarget
import rat.poison.game.offsets.ClientOffsets.dwIndex
import rat.poison.opened
import rat.poison.strToBool
import rat.poison.ui.uiPanels.specListText
import rat.poison.utils.every
import rat.poison.utils.extensions.readIndex
import rat.poison.utils.notInGame

internal fun spectatorList() = every(100) {
    if (!curSettings["SPECTATOR_LIST"].strToBool() || notInGame || !curSettings["MENU"].strToBool()) {
        return@every
    }

    var spectators = ""

    val playerSpecTarget = csgoEXE.readIndex(me + dwIndex)

    forEntities(ccsPlayer) body@ {
        val entity = it.entity

        if (entity.isSpectating() && !entity.hltv() && !entity.dormant()) {
            val entSpecTarget = csgoEXE.readIndex(entity + m_hObserverTarget)
            val entName = entity.name()

            if (entSpecTarget > -1 && entSpecTarget == playerSpecTarget) {
                if (!spectators.contains(entName)) {
                    spectators += "name: $entName\n"
                }
            }
        }
        return@body false
    }

    if (opened && haveTarget) {
        specListText.setText(spectators)
    }
}

// Move to normal spot whenever
fun Int.toIndex() = ((this and 0xFFF) - 1).run {
    if (this == 4094) -1 else this
}
