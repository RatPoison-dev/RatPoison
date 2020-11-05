package rat.poison.utils

import com.badlogic.gdx.Input.Keys.*
import com.badlogic.gdx.Input

const val blankKeycode = -1
const val blankKey = "None"

val buttonsMap = mutableMapOf(Pair(Input.Buttons.LEFT, "m1"), Pair(Input.Buttons.BACK, "m5"), Pair(Input.Buttons.FORWARD, "m6"), Pair(Input.Buttons.RIGHT, "m2"), Pair(Input.Buttons.MIDDLE, "m_wheel"))
val gdxButtons = mutableMapOf(Pair(Input.Buttons.FORWARD, 6), Pair(Input.Buttons.LEFT, 1), Pair(Input.Buttons.RIGHT, 2), Pair(Input.Buttons.MIDDLE, 4), Pair(Input.Buttons.BACK, 5))
val gdxToVk = mutableMapOf(Pair(A, 65), Pair(ALT_LEFT, 18), Pair(ALT_RIGHT, 18), Pair(APOSTROPHE, 222), Pair(AT, blankKeycode), Pair(B, 66), Pair(BACK, 220), Pair(BACKSLASH, 220), Pair(BACKSPACE, 8), Pair(BUTTON_A, blankKeycode), Pair(BUTTON_B, blankKeycode), Pair(UNKNOWN, -1), Pair(BUTTON_C, blankKeycode), Pair(BUTTON_CIRCLE, blankKeycode), Pair(BUTTON_L1, blankKeycode), Pair(BUTTON_L2, blankKeycode), Pair(BUTTON_MODE, blankKeycode), Pair(BUTTON_R1, blankKeycode), Pair(BUTTON_R2, blankKeycode), Pair(BUTTON_SELECT, blankKeycode), Pair(BUTTON_START, blankKeycode), Pair(BUTTON_THUMBL, blankKeycode), Pair(BUTTON_THUMBR, blankKeycode), Pair(BUTTON_X, blankKeycode), Pair(BUTTON_Y, blankKeycode), Pair(BUTTON_Z, blankKeycode), Pair(C, 67), Pair(CALL, blankKeycode), Pair(CAMERA, blankKeycode), Pair(CENTER, blankKeycode), Pair(CLEAR, blankKeycode), Pair(COLON, blankKeycode), Pair(COMMA, 188), Pair(CONTROL_LEFT, 17), Pair(CONTROL_RIGHT, 17), Pair(D, 68), Pair(DEL, 46), Pair(DOWN, 40), Pair(DPAD_CENTER, blankKeycode), Pair(DPAD_DOWN, blankKeycode), Pair(DPAD_LEFT, blankKeycode), Pair(DPAD_RIGHT, blankKeycode), Pair(DPAD_UP, blankKeycode), Pair(E, 69), Pair(END, 35), Pair(ENDCALL, blankKeycode), Pair(ENTER, 13), Pair(ENVELOPE, blankKeycode), Pair(EQUALS, 187), Pair(ESCAPE, 27), Pair(EXPLORER, blankKeycode), Pair(F, 70), Pair(F1, 112), Pair(F10, 121), Pair(F11, 122), Pair(F12, 123), Pair(F2, 113), Pair(F3, 114), Pair(F4, 115), Pair(F5, 116), Pair(F6, 117), Pair(F7, 118), Pair(F8, 119), Pair(F9, 120), Pair(FOCUS, blankKeycode), Pair(FORWARD_DEL, 46), Pair(G, 71), Pair(GRAVE, 192), Pair(H, 72), Pair(HOME, 36), Pair(HEADSETHOOK, blankKeycode), Pair(I, 73), Pair(INSERT, 45), Pair(J, 74), Pair(K, 75), Pair(L, 76), Pair(LEFT, 37), Pair(LEFT_BRACKET, 219), Pair(M, 77), Pair(MEDIA_FAST_FORWARD, blankKeycode), Pair(MEDIA_NEXT, blankKeycode), Pair(MEDIA_PLAY_PAUSE, blankKeycode), Pair(MEDIA_PREVIOUS, blankKeycode), Pair(MEDIA_REWIND, blankKeycode), Pair(MEDIA_STOP, blankKeycode), Pair(MENU, 164), Pair(META_ALT_LEFT_ON, blankKeycode), Pair(META_ALT_ON, blankKeycode), Pair(META_ALT_RIGHT_ON, blankKeycode), Pair(META_SHIFT_LEFT_ON, blankKeycode), Pair(META_SHIFT_ON, blankKeycode), Pair(META_SHIFT_RIGHT_ON, blankKeycode), Pair(META_SYM_ON, blankKeycode), Pair(MINUS, 189), Pair(MUTE, 173), Pair(N, 78), Pair(NOTIFICATION, blankKeycode), Pair(NUM, 144), Pair(NUM_0, 48), Pair(NUM_1, 49), Pair(NUM_2, 50), Pair(NUM_3, 51), Pair(NUM_4, 52), Pair(NUM_5, 53), Pair(NUM_6, 54), Pair(NUM_7, 55), Pair(NUM_8, 56), Pair(NUM_9, 57), Pair(NUMPAD_0, 96), Pair(NUMPAD_1, 97), Pair(NUMPAD_2, 98), Pair(NUMPAD_3, 99), Pair(NUMPAD_4, 100), Pair(NUMPAD_5, 101), Pair(NUMPAD_6, 102), Pair(NUMPAD_7, 103), Pair(NUMPAD_8, 104), Pair(NUMPAD_9, 105), Pair(O, 79), Pair(P, 80), Pair(PAGE_DOWN, 34), Pair(PAGE_UP, 33), Pair(PERIOD, 110), Pair(PICTSYMBOLS, blankKeycode), Pair(PLUS, 187), Pair(POUND, blankKeycode), Pair(POWER, blankKeycode), Pair(Q, 81), Pair(R, 82), Pair(RIGHT, 39), Pair(RIGHT_BRACKET, 221), Pair(S, 83), Pair(SEARCH, blankKeycode), Pair(SEMICOLON, 186), Pair(SHIFT_LEFT, 160), Pair(SHIFT_RIGHT, 161), Pair(SLASH, 191), Pair(SOFT_LEFT, blankKeycode), Pair(SOFT_RIGHT, blankKeycode), Pair(SPACE, 32), Pair(STAR, 106), Pair(SWITCH_CHARSET, blankKeycode), Pair(SYM, 91), Pair(T, 84), Pair(TAB, 9), Pair(U, 85), Pair(UP, 38), Pair(V, 86),
        Pair(VOLUME_DOWN, 174), Pair(VOLUME_UP, 175), Pair(W, 87), Pair(X, 88), Pair(Y, 89), Pair(Z, 90))
val vkToButton = gdxToVk.entries.associateBy({ it.value }) { it.key }
val vkToMouse =  gdxButtons.entries.associateBy({ it.value }) { it.key }
val duplicationsMap = mutableMapOf(Pair(191, listOf(111, 191)), Pair(20, listOf(20, 145, 19, 44, 144)), Pair(145, listOf(145, 20, 19, 44, 144)), Pair(19, listOf(145, 20, 145, 44, 144)), Pair(44, listOf(145, 20, 145, 44, 144)), Pair(144, listOf(145, 20, 145, 44, 144)))

fun keycodeToString(keycode: Int, type: String): String {
    if (type == "button") {
        if (keycode < 0 || keycode > 255) return blankKey
        return toString(keycode)
    }
    else { return buttonsMap[keycode]!! }
}

fun vkKeycodeToString(keycode: Int): String {
    val tmp = vkToButton[keycode]
    return if (keycode in vkToButton.keys && tmp != null) {
        if (keycode < 0 || keycode > 255) blankKey
        else toString(tmp)
    } else {
        buttonsMap[vkToMouse[keycode]]!!
    }
}

//uncomment when needed
//fun keysTest() {
//    while (true) {
//        for (i in 1..255) {
//            if (keyPressed(i)) {
//                println(i)
//            }
//        }
//    }
//}