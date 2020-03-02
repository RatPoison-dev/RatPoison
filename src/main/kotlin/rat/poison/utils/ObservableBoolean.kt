////Courtesy of Mr Noad

package rat.poison.utils

import kotlin.properties.Delegates

open class ObservableBoolean (
        private val updateState: () -> Boolean,
        private var wasTrue: Boolean = updateState(),
        initialValue: Boolean = wasTrue
) {

    var value by Delegates.observable(initialValue) { _, old, _ ->
        wasTrue = old
    }
        private set

    open fun update() {
        value = updateState()
    }

    val justBecameTrue: Boolean
        get() = !wasTrue && value

    val justBecameFalse: Boolean
        get() = wasTrue && !value
}