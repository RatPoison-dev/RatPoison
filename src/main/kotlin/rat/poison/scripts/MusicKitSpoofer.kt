package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.nMusicID
import rat.poison.game.offsets.ClientOffsets
import rat.poison.utils.every
import rat.poison.utils.extensions.uint
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.inGame
import rat.poison.utils.shouldPostProcess

fun musicKitSpoofer() = every(10000, true, inGameCheck = true) {
    if (!shouldPostProcess) return@every
    if (curSettings["MUSIC_KIT_SPOOFER"].strToBool()) {
        writeSpoof()
    }
}

fun writeSpoof() {
    if (inGame) {
        val index = csgoEXE.uint(me + ClientOffsets.dwIndex)
        csgoEXE[CSGO.clientDLL.uint(ClientOffsets.dwPlayerResource) + nMusicID + index * 4] = curSettings["MUSIC_KIT_ID"].toInt()
    }
}