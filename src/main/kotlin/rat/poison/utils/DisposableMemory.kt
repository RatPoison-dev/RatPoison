////Courtesy of Mr Noad

package rat.poison.utils

import com.sun.jna.Memory

open class DisposableMemory : Memory {
    constructor(size: Long) : super(size)
    constructor() : super()

    fun disposeUnsafe() {
        this.dispose()
    }
}