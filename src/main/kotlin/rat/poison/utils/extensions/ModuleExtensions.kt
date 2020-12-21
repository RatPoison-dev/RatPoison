package rat.poison.utils.extensions

import com.sun.jna.Pointer
import org.jire.kna.attach.AttachedModule
import org.jire.kna.attach.windows.WindowsAttachedProcess
import rat.poison.game.offsets.ModuleScan
import rat.poison.game.offsets.Offset
import java.nio.ByteBuffer
import java.nio.ByteOrder

internal operator fun AttachedModule.invoke(patternOffset: Long = 0, addressOffset: Long = 0,
                                            read: Boolean = true, subtract: Boolean = true)
		= ModuleScan(this, patternOffset, addressOffset, read, subtract)

internal operator fun AttachedModule.invoke(patternOffset: Long = 0, addressOffset: Long = 0,
                                    read: Boolean = true, subtract: Boolean = true, className: String)
		= Offset(this, patternOffset, addressOffset, read, subtract, className.toByteArray(Charsets.UTF_8))

internal operator fun AttachedModule.invoke(patternOffset: Long = 0, addressOffset: Long = 0,
                                    read: Boolean = true, subtract: Boolean = true, offset: Long)
		= Offset(this, patternOffset, addressOffset, read, subtract,
		ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(offset.toInt()).array())

fun AttachedModule.readForced(address: Long, buffer: Pointer, size: Int)
		= (process as WindowsAttachedProcess).readForced(offset(address), buffer, size)

fun AttachedModule.writeForced(address: Long, buffer: Pointer, size: Int)
	= (process as WindowsAttachedProcess).writeForced(offset(address), buffer, size)