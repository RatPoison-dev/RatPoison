package rat.poison.jna

import com.sun.jna.Callback
import com.sun.jna.Native
import com.sun.jna.Pointer
import rat.poison.jna.structures.InterceptionKeyStroke


object Interception {
    val INTERCEPTION_MAX_KEYBOARD = 10
    val INTERCEPTION_MAX_MOUSE = 10
    val INTERCEPTION_MAX_DEVICE = ((INTERCEPTION_MAX_KEYBOARD) + (INTERCEPTION_MAX_MOUSE))
    init {
        Native.register("interception")
    }
    fun INTERCEPTION_KEYBOARD(index: Int): Int {
        return index + 1
    }
    fun INTERCEPTION_MOUSE(index: Int): Int {
        return INTERCEPTION_MAX_KEYBOARD + index + 1
    }
    external fun interception_create_context(): Pointer
    external fun interception_send(context: Pointer, device: Int, stroke: InterceptionKeyStroke, nstroke: Int): Int
    external fun interception_destroy_context(context: Pointer)
    external fun interception_is_keyboard(device: Int): Int
    external fun interception_is_mouse(device: Int): Int
    external fun interception_set_filter(context: Pointer, predicate: Callback, filter: Short)
    external fun interception_receive(context: Pointer, device: Int, stroke: InterceptionKeyStroke, nstroke: Int): Int
    external fun interception_wait(context: Pointer): Int


}