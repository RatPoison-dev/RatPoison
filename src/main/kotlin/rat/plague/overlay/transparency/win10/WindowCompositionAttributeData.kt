package rat.plague.overlay.transparency.win10

import com.sun.jna.Pointer
import com.sun.jna.Structure
import org.jire.arrowhead.Struct

class WindowCompositionAttributeData : Struct(), Structure.ByReference {
	
	@JvmField var Attribute: Int = 0
	@JvmField var Data: Pointer? = null
	@JvmField var SizeOfData: Int = 0
	
}