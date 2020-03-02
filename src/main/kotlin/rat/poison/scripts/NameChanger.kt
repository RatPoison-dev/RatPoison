package rat.poison.scripts

import com.sun.jna.Memory
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.clientState
import rat.poison.utils.extensions.uint

fun nameChanger(name: String) {
    val len = name.length

    val a = byteArrayOf(0x6, (0x8 + len).toByte(), 0xA, (0x6 + len).toByte(), 0xA, (0x4 + len).toByte(), 0x12, len.toByte())
    val b = name.toByteArray(Charsets.US_ASCII)
    val c = byteArrayOf( 0x18, 0x6 )

    val final = a+b+c

    val netChan = csgoEXE.uint(clientState + 0x9C)
    val voiceStream = csgoEXE.uint(netChan + 0x78)

    val curBit = final.size * 8

    val mem = Memory(final.size.toLong())
    mem.write(0, final, 0, final.size)

    csgoEXE.write(voiceStream, mem)

    csgoEXE[netChan + 0x84] = curBit.toByte()
}