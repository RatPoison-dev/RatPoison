package rat.poison.scripts

import rat.poison.App.haveTarget
import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.*
import rat.poison.game.netvars.NetVarOffsets.m_hObserverTarget
import rat.poison.game.netvars.NetVarOffsets.m_iObserverMode
import rat.poison.game.offsets.ClientOffsets.dwIndex
import rat.poison.game.offsets.EngineOffsets.dwClientState_State
import rat.poison.opened
import rat.poison.settings.MENUTOG
import rat.poison.strToBool
import rat.poison.ui.specListText
import rat.poison.utils.every
import rat.poison.utils.extensions.uint
import rat.poison.utils.notInGame

internal fun spectatorList() = every(1000) {
    if (!curSettings["SPECTATOR_LIST"]!!.strToBool() || notInGame || !curSettings["MENU"]!!.strToBool()) {
        return@every
    }

    var spectators = ""

    if (me.isSpectating()) {
        //playerSpecTarget = csgoEXE.uint(me + m_hObserverTarget) and 0xFFF //Fuck you
        spectators = "N/A"
    }
    else {
        val playerSpecTarget = csgoEXE.uint(me + dwIndex) //Not -1

        forEntities body@ {
            val entity = it.entity

            if (it.type == EntityType.CCSPlayer) {
                if (entity.isSpectating() && entity != me && !entity.hltv() && !entity.dormant() && entity.dead()) {

                    val entName = entity.name()

                    val entitySpecTarget = csgoEXE.uint(entity + m_hObserverTarget) and 0xFFF

                    if (entitySpecTarget == playerSpecTarget && entitySpecTarget != 0xFFF.toLong()) {
                        if (!spectators.contains(entName) && entName != "") {
                            spectators += entName + "\n"
                        }
                    }
                }
            }
            return@body false
        }
    }

    if (opened && haveTarget) {
        specListText.setText(spectators)
    }
}

// Move to normal spot whenever
internal fun Player.observerMode(): Int = csgoEXE.int(this + m_iObserverMode)
internal fun Player.isSpectating(): Boolean = observerMode() > 0
