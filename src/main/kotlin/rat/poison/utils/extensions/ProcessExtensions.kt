package rat.poison.utils.extensions

import com.sun.jna.Pointer
import org.jire.kna.PointerCache
import org.jire.kna.attach.windows.WindowsAttachedProcess
import org.jire.kna.nativelib.windows.Kernel32

fun WindowsAttachedProcess.writeForced(address: Long, buffer: Pointer, size: Int) = Kernel32.WriteProcessMemory(
	handle.pointer,
	PointerCache[address],
	buffer,
	size,
	0
)