////Courtesy of Mr Noad

package rat.poison.jna.structures

import com.sun.jna.Structure
import rat.poison.utils.DisposableMemory

class Rect : DisposableMemory(16), Structure.ByReference {
    /** The left.  */
    var left: Int
        get() {
            return this.getInt(0)
        }
        set(value) {
            this.setInt(0, value)
        }

    /** The top.  */
    var top: Int
        get() {
            return this.getInt(4)
        }
        set(value) {
            this.setInt(4, value)
        }

    /** The right.  */
    var right: Int
        get() {
            return this.getInt(8)
        }
        set(value) {
            this.setInt(8, value)
        }

    /** The bottom.  */
    var bottom: Int
        get() {
            return this.getInt(12)
        }
        set(value) {
            this.setInt(12, value)
        }

    override fun toString(): String {
        return "[($left,$top)($right,$bottom)]"
    }
}