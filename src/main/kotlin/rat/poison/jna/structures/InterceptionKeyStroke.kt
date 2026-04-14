package rat.poison.jna.structures

import com.sun.jna.Structure
import org.jire.arrowhead.Struct

class InterceptionKeyStroke : Struct(), Structure.ByReference {
    @JvmField internal var code: Short = 0.toShort();
    @JvmField internal var state: Short = 0.toShort();
    @JvmField internal var information: Short = 0.toShort();
    @JvmField internal var rolling: Short = 0.toShort();
    @JvmField internal var x: Int = 0
    @JvmField internal var y: Int = 0
}