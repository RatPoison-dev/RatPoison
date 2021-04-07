package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.nMusicID
import rat.poison.game.offsets.ClientOffsets
import rat.poison.utils.common.every
import rat.poison.utils.common.inGame
import rat.poison.utils.extensions.uint

//https://www.unknowncheats.me/forum/counterstrike-global-offensive/164236-spoofing-music-kit-casual-rank-via-netvar-patching.html
fun musicKitSpoofer() = every(10000, true, inGameCheck = true) {
    if (curSettings.bool["MUSIC_KIT_SPOOFER"]) {
        writeSpoof()
    }
}

fun writeSpoof() {
    if (inGame) {
        val index = csgoEXE.uint(me + ClientOffsets.dwIndex)
        csgoEXE[CSGO.clientDLL.uint(ClientOffsets.dwPlayerResource) + nMusicID + index * 4] = curSettings.int["MUSIC_KIT_ID"]
    }
}