package rat.poison.game.netvars

import org.jire.kna.Addressed
import org.jire.kna.int
import rat.poison.game.CSGO.csgoEXE
import rat.poison.utils.extensions.toNetVarString
import rat.poison.utils.extensions.uint
import kotlin.LazyThreadSafetyMode.NONE

internal class ClassTable(override val address: Long, val offset: Long = 16) : Addressed {
	
	val name by lazy(NONE) {
		val bytes = ByteArray(64)
		
		val memoryAddress = csgoEXE.uint(address + 12)
		val memory = csgoEXE.readPointer(memoryAddress, bytes.size.toLong()).ensureReadable()
		memory.read(0, bytes, 0, bytes.size)
		
		bytes.toNetVarString()
	}
	
	val propCount by lazy(NONE) { csgoEXE.int(address + 4) }
	
	fun propForID(id: Int) = csgoEXE.uint(address) + id * 60
	
	fun readable() = csgoEXE.readPointer(address, offset).readable()
}