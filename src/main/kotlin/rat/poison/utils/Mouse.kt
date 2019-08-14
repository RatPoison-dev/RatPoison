package rat.poison.utils

import rat.poison.utils.natives.CUser32.mouse_event

const val MOUSEEVENTF_ABSOLUTE = 0x8000
const val MOUSEEVENTF_MOVE = 0x0001
const val MOUSEEVENTF_WHEEL = 0x0800

const val MOUSEEVENTF_LEFTDOWN = 0x0002
const val MOUSEEVENTF_LEFTUP = 0x0004

fun mouseMove(dx: Int, dy: Int, flags: Int = MOUSEEVENTF_MOVE) = mouse_event(flags, dx, dy, 0, 0)

fun mouseWheel(amount: Int) = mouse_event(MOUSEEVENTF_WHEEL, 0, 0, amount, 0)

fun mouse(button: Int) = mouse_event(button, 0, 0, 0, 0)