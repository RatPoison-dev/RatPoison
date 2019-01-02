

package rat.plague.game.netvars

import rat.plague.game.CSGO.csgoEXE
import rat.plague.utils.extensions.readable
import rat.plague.utils.extensions.uint
import org.jire.arrowhead.Addressed
import kotlin.LazyThreadSafetyMode.NONE

internal class Class(override val address: Long) : Addressed {
	
	val id by lazy(NONE) { csgoEXE.uint(address + 20) }
	
	val next by lazy(NONE) { csgoEXE.uint(address + 16) }
	
	val table by lazy(NONE) { csgoEXE.uint(address + 12) }
	
	fun readable() = csgoEXE.read(address, 40).readable()
	
}