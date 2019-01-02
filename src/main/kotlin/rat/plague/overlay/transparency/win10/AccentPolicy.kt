package rat.plague.overlay.transparency.win10

import com.sun.jna.Structure
import org.jire.arrowhead.Struct

class AccentPolicy : Struct(), Structure.ByReference {
	
	@JvmField var AccentState: Int = 0
	@JvmField var AccentFlags: Int = 0
	@JvmField var GradientColor: Int = 0
	@JvmField var AnimationId: Int = 0
	
}