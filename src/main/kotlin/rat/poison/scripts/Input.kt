package rat.poison.scripts

import com.sun.jna.Callback
import com.sun.jna.Pointer
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import rat.poison.jna.Interception
import rat.poison.jna.User32
import rat.poison.jna.enums.InterceptionKeyState
import rat.poison.jna.enums.InterceptionMouseFlag
import rat.poison.jna.enums.InterceptionMouseState
import rat.poison.jna.structures.InterceptionKeyStroke
import rat.poison.robot
import rat.poison.safe_mode
import rat.poison.utils.log2
import java.awt.event.MouseEvent
import kotlin.experimental.and
import rat.poison.utils.mouseMove as mmove
import rat.poison.utils.mouseWheel as mwheel
import org.jire.arrowhead.keyPressed as kpressed

typealias mouseClickHookFunc = (Int, Int, Boolean) -> Boolean
data class MouseClickHook(val main: mouseClickHookFunc)

typealias keyboardHookFunc = (Int, Int, Boolean) -> Boolean
data class KeyboardHook(val main: keyboardHookFunc)
const val zeroShort = 0.toShort()

enum class InputDeviceType {
    INTERCEPTION,
    USERMODE;

    companion object {
        fun fromSetting(value: String): InputDeviceType {
            if (value.isBlank()) {
                return INTERCEPTION
            }

            return entries.firstOrNull { it.name.equals(value, ignoreCase = true) }
                ?: INTERCEPTION
        }
    }
}

val mouseEventToInterceptionDown = mapOf(
    MouseEvent.BUTTON1_DOWN_MASK to InterceptionMouseState.INTERCEPTION_MOUSE_LEFT_BUTTON_DOWN.value,
    MouseEvent.BUTTON2_DOWN_MASK to InterceptionMouseState.INTERCEPTION_MOUSE_MIDDLE_BUTTON_DOWN.value,
    MouseEvent.BUTTON3_DOWN_MASK to InterceptionMouseState.INTERCEPTION_MOUSE_RIGHT_BUTTON_DOWN.value,
    MouseEvent.getMaskForButton(5) to InterceptionMouseState.INTERCEPTION_MOUSE_BUTTON_4_DOWN.value,
    MouseEvent.getMaskForButton(6) to InterceptionMouseState.INTERCEPTION_MOUSE_BUTTON_5_DOWN.value
)

val mouseEventToInterceptionUp = mouseEventToInterceptionDown.mapValues { (it.value + 1).toShort() }

val interceptionMouseToVk = mapOf(
    InterceptionMouseState.INTERCEPTION_MOUSE_BUTTON_1_UP.value to 1,
    InterceptionMouseState.INTERCEPTION_MOUSE_BUTTON_1_DOWN.value to 1,
    InterceptionMouseState.INTERCEPTION_MOUSE_BUTTON_2_UP.value to 4,
    InterceptionMouseState.INTERCEPTION_MOUSE_BUTTON_2_DOWN.value to 4,
    InterceptionMouseState.INTERCEPTION_MOUSE_BUTTON_3_UP.value to 2,
    InterceptionMouseState.INTERCEPTION_MOUSE_BUTTON_3_DOWN.value to 2,
    InterceptionMouseState.INTERCEPTION_MOUSE_BUTTON_4_DOWN.value to 5,
    InterceptionMouseState.INTERCEPTION_MOUSE_BUTTON_4_UP.value to 5,
    InterceptionMouseState.INTERCEPTION_MOUSE_BUTTON_5_DOWN.value to 6,
    InterceptionMouseState.INTERCEPTION_MOUSE_BUTTON_5_UP.value to 6
)

abstract class InputThread : Thread() {
    open var mouseDevice = -1
    open var nextMouse = -1
    open var nextKeyboard = -1
    open var keyboardDevice = -1
    protected val pressed = HashSet<Int>()

    abstract fun mouseMove(dx: Int, dy: Int)
    abstract fun mousePress(key: Int, device: Int = mouseDevice)
    abstract fun mouseRelease(key: Int, device: Int = mouseDevice)

    open fun mouseClick(key: Int, device: Int = mouseDevice) {
        mousePress(key, device)
        mouseRelease(key, device)
    }

    abstract fun mouseWheel(amount: Int)
    abstract fun keyPress(key: Int, device: Int = keyboardDevice)

    open fun keyClick(key: Int, device: Int = keyboardDevice) {
        keyPress(key, device)
        keyRelease(key, device)
    }

    abstract fun keyRelease(key: Int, device: Int = keyboardDevice)
    open fun keyPressed(key: Int): Boolean = kpressed(key)

    companion object {
        var mouseClickHooks = ObjectArrayList<MouseClickHook>()
        var keyboardPressHooks = ObjectArrayList<KeyboardHook>()

        fun onMouse(func: mouseClickHookFunc) {
            mouseClickHooks.add(MouseClickHook(func))
        }

        fun onKeyboard(func: keyboardHookFunc) {
            keyboardPressHooks.add(KeyboardHook(func))
        }
    }
}

class UsermodeInputThread : InputThread() {
    override fun mouseMove(dx: Int, dy: Int) = mmove(dx, dy)

    override fun mousePress(key: Int, device: Int) {
        robot.mousePress(key)
    }

    override fun mouseRelease(key: Int, device: Int) {
        robot.mouseRelease(key)
    }

    override fun mouseWheel(amount: Int) = mwheel(amount)

    override fun keyPress(key: Int, device: Int) {
        robot.keyPress(key)
    }

    override fun keyRelease(key: Int, device: Int) {
        robot.keyRelease(key)
    }

    override fun run() = Unit
}

class InterceptionInputThread : InputThread() {
    private val ctx: Pointer
    private var device = -1
    private val sendStroke = ThreadLocal.withInitial { InterceptionKeyStroke() }

    init {
        with(Interception) {
            val cb1: Callback = object : Callback {
                fun callback(device: Int): Int {
                    return interception_is_keyboard(device)
                }
            }

            val cb2: Callback = object : Callback {
                fun callback(device: Int): Int {
                    return interception_is_mouse(device)
                }
            }

            ctx = interception_create_context()
            interception_set_filter(ctx, cb1, 0xFFFF.toShort())
            interception_set_filter(ctx, cb2, 0xFFFF.toShort())
        }
    }

    private fun setDevice(): Int {
        device = Interception.interception_wait(ctx)
        return device
    }

    override fun mouseMove(dx: Int, dy: Int) {
        if (mouseDevice == -1) {
            mmove(dx, dy)
            return
        }

        val stroke = sendStroke.get()
        stroke.x = dx
        stroke.y = dy

        Interception.interception_send(ctx, device, stroke, 1)
    }

    override fun mousePress(key: Int, device: Int) {
        if (device == -1) {
            robot.mousePress(key)
            return
        }

        val value = mouseEventToInterceptionDown[key] ?: return
        val stroke = sendStroke.get()
        stroke.code = value
        stroke.state = InterceptionMouseFlag.INTERCEPTION_MOUSE_MOVE_RELATIVE.value
        Interception.interception_send(ctx, device, stroke, 1)
    }

    override fun mouseRelease(key: Int, device: Int) {
        if (device == -1) {
            robot.mouseRelease(key)
            return
        }

        val value = mouseEventToInterceptionUp[key] ?: return
        val stroke = sendStroke.get()
        stroke.code = value
        stroke.state = InterceptionMouseFlag.INTERCEPTION_MOUSE_MOVE_RELATIVE.value
        Interception.interception_send(ctx, device, stroke, 1)
    }

    override fun mouseWheel(amount: Int) {
        if (mouseDevice == -1) {
            mwheel(amount)
            return
        }

        with(Interception) {
            val stroke = sendStroke.get()
            stroke.code = InterceptionMouseState.INTERCEPTION_MOUSE_WHEEL.value
            stroke.information = if (amount > 0) 0x0078 else 0xFF88.toShort()
            interception_send(ctx, mouseDevice, stroke, 1)
        }
    }

    override fun keyPress(key: Int, device: Int) {
        if (device == -1) {
            robot.keyPress(key)
            return
        }

        with(Interception) {
            val stroke = sendStroke.get()
            stroke.code = User32.vkToScan(key).toShort()
            stroke.state = InterceptionKeyState.INTERCEPTION_KEY_DOWN.value
            interception_send(ctx, device, stroke, 1)
        }
    }

    override fun keyRelease(key: Int, device: Int) {
        if (device == -1) {
            robot.keyRelease(key)
            return
        }

        with(Interception) {
            val stroke = sendStroke.get()
            stroke.code = User32.vkToScan(key).toShort()
            stroke.state = InterceptionKeyState.INTERCEPTION_KEY_UP.value
            interception_send(ctx, device, stroke, 1)
        }
    }

    override fun keyPressed(key: Int): Boolean {
        if (!safe_mode) return kpressed(key)
        return pressed.contains(key)
    }

    override fun run() {
        val stroke = InterceptionKeyStroke()

        with(Interception) {
            while (interception_receive(ctx, setDevice(), stroke, 1) > 0) {
                if (mouseDevice == -1 && interception_is_mouse(device) == 1) {
                    mouseDevice = device
                    for (i in INTERCEPTION_MAX_KEYBOARD + 1 until INTERCEPTION_MAX_DEVICE) {
                        if (mouseDevice != i) {
                            nextMouse = i
                            break
                        }
                    }
                } else if (keyboardDevice == -1 && interception_is_keyboard(device) == 1) {
                    keyboardDevice = device
                    for (i in 2..INTERCEPTION_MAX_KEYBOARD) {
                        if (i != keyboardDevice) {
                            nextKeyboard = i
                        }
                    }
                }

                var send = true
                var deviceCheck = false
                var button = -1
                var keyIsPressed = false

                if (interception_is_keyboard(device) == 1) {
                    InputThread.keyboardPressHooks.forEach {
                        button = User32.scanToVk(stroke.code.toInt())
                        keyIsPressed = stroke.state.toInt() == 0
                        deviceCheck = device == keyboardDevice
                        send = it.main(device, button, keyIsPressed) && send
                    }

                    if (stroke.code == 0x01.toShort()) {
                        break
                    }
                } else if (interception_is_mouse(device) == 1) {
                    if ((stroke.state and InterceptionMouseFlag.INTERCEPTION_MOUSE_MOVE_ABSOLUTE.value) == zeroShort) {
                        if (stroke.code != 0x400.toShort() && stroke.code != zeroShort) {
                            val log = log2(stroke.code)
                            val press = log % 2 == 0
                            button = interceptionMouseToVk[stroke.code] ?: -1
                            keyIsPressed = press
                            deviceCheck = device == mouseDevice

                            InputThread.mouseClickHooks.forEach {
                                send = it.main(device, button, press) && send
                            }
                        }
                    }
                }

                if (send) {
                    if (deviceCheck && button != -1) {
                        if (keyIsPressed) pressed.add(button) else pressed.remove(button)
                    }
                    interception_send(ctx, device, stroke, 1)
                }
            }

            interception_destroy_context(ctx)
        }
    }
}
