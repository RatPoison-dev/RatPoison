package rat.poison.utils.extensions

import org.jire.kna.Pointer
import org.jire.kna.attach.windows.WindowsAttachedProcess
import org.jire.kna.nativelib.windows.Kernel32

fun WindowsAttachedProcess.readForced(address: Long, buffer: Pointer, size: Int) = Kernel32.ReadProcessMemory(
	handle.pointer,
	offset(address),
	buffer.address,
	size,
	0
)

fun WindowsAttachedProcess.writeForced(address: Long, buffer: Pointer, size: Int) = Kernel32.WriteProcessMemory(
	handle.pointer,
	offset(address),
	buffer.address,
	size,
	0
)