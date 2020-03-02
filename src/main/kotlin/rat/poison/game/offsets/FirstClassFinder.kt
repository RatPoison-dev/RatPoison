

package rat.poison.game.offsets

import rat.poison.game.CSGO.clientDLL
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.offsets.ClientOffsets.decalname
import rat.poison.utils.extensions.uint

fun findDecal(): Long {
	val mask = ByteArray(4)
	for (i in 0..3) mask[i] = (((decalname shr 8 * i)) and 0xFF).toByte()
	
	val memory = Offset.memoryByModule[clientDLL]!!
	
	var skipped = 0
	var currentAddress = 0L
	while (currentAddress < clientDLL.size - mask.size) {
		if (memory.mask(currentAddress, mask, false)) {
			if (skipped < 5) { // skips
				currentAddress += 0xA // skipSize
				skipped++
				continue
			}
			return currentAddress + clientDLL.address
		}
		currentAddress++
	}
	
	return -1L
}

fun findFirstClass() = csgoEXE.uint(findDecal() + 0x3B)