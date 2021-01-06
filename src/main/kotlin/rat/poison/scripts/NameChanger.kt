package rat.poison.scripts

import org.jire.kna.Pointer
import org.jire.kna.set
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.clientState
import rat.poison.utils.MedPriority

import rat.poison.utils.extensions.uint

@Volatile
var nameChange = ""

//https://www.unknowncheats.me/forum/counterstrike-global-offensive/190477-csgo-name-changer.html

fun nameChanger() = MedPriority.every(10, true, inGameCheck = true) {
    if (nameChange == "") return@every

    val len = nameChange.length

    val a = byteArrayOf(0x6, (0x8 + len).toByte(), 0xA, (0x6 + len).toByte(), 0xA, (0x4 + len).toByte(), 0x12, len.toByte())
    val b = nameChange.toByteArray(Charsets.US_ASCII)
    val c = byteArrayOf(0x18, 0x6)

    val final = a + b + c

    val netChan = csgoEXE.uint(clientState + 0x9C)
    val voiceStream = csgoEXE.uint(netChan + 0x78)

    val curBit = final.size * 8

    val memSize = final.size.toLong()
    val mem = Pointer.alloc(memSize)
    mem.jna.write(0, final, 0, final.size)

    csgoEXE.write(voiceStream, mem, memSize)
    //Native.free(mem.address)

    csgoEXE[netChan + 0x84] = curBit.toByte()
}

fun changeName(name: String) {
    nameChange = name
}