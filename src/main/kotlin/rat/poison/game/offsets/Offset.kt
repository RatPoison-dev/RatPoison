package rat.poison.game.offsets

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import org.jire.kna.Addressed
import org.jire.kna.Pointer
import org.jire.kna.attach.AttachedModule
import org.jire.kna.attach.windows.WindowsAttachedModule
import org.jire.kna.attach.windows.WindowsAttachedProcess
import rat.poison.utils.extensions.readForced
import rat.poison.utils.extensions.unsign
import kotlin.reflect.KProperty

class Offset(
	val module: AttachedModule, private val patternOffset: Long, private val addressOffset: Long,
	val read: Boolean, private val subtract: Boolean, private val mask: ByteArray
) : Addressed {
	
	companion object {
		val bytesByModule = Object2ObjectArrayMap<AttachedModule, ByteArray>()
		
		private fun Offset.cachedBytes(): ByteArray {
			val cached = bytesByModule[module]
			if (cached != null) return cached
			
			val pointer = Pointer.alloc(module.size)
			if (module !is WindowsAttachedModule || module.readForced(
					0,
					pointer,
					module.size.toInt()
				) == 0L
			)
				throw IllegalStateException()
			
			val array = pointer.jna.getByteArray(0, module.size.toInt())
			bytesByModule[module] = array
			return array
		}
	}
	
	private val bytes = cachedBytes()
	
	override val address: Long = run {
		val offset = module.size - mask.size
		val process = module.process as WindowsAttachedProcess
		val readMemory = Pointer.alloc(4)
		var currentAddress = 0L
		while (currentAddress < offset) {
			if (bytes.mask(currentAddress, mask)) {
				currentAddress += module.address + patternOffset
				if (read) {
					if (process.readForced(
							currentAddress,
							readMemory,
							4
						) == 0L
					) throw IllegalStateException("Couldn't resolve currentAddress pointer")
					currentAddress = readMemory.getInt(0).unsign()
				}
				if (subtract) currentAddress -= module.address
				return@run currentAddress + addressOffset
			}
			currentAddress++
		}
		
		//IllegalStateException("Failed to resolve offset, module=$module, memory=$memory, read=$read, subtract=$subtract, currentAddress=$currentAddress").printStackTrace()
		return@run -1L
	}
	
	private var value = -1L
	
	operator fun getValue(thisRef: Any?, property: KProperty<*>): Long {
		if (value == -1L)
			value = address
		return value
	}
	
	operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Long) {
		this.value = value
	}
	
}

fun ByteArray.mask(offset: Long, mask: ByteArray, skipZero: Boolean = true): Boolean {
	val offsetI = offset.toInt()
	for (i in 0..mask.lastIndex) {
		val value = mask[i]
		if (skipZero && 0 == value.toInt()) continue
		if (value != this[offsetI + i])
			return false
	}
	return true
}