package rat.poison.utils

import com.badlogic.gdx.Input.Keys.*
import com.badlogic.gdx.Input

class GdxToKeycode : MutableMap<Int, Int> {
    private val savedValues = mutableMapOf<Int, Int>()

    override fun get(key: Int): Int {
        if (savedValues.containsKey(key)) {
            return savedValues[key]!!
        }
        return -1
    }

    //0 - keycode
    //1 - button
    fun advancedGet(key: Int, type: Boolean): Int {
        return when (type) {
            true -> gdxButtons[key]
            false -> this[key]
        }
    }

    override fun put(key: Int, value: Int): Int? {
        return value.let { savedValues.put(key, it) }
    }

    override fun containsValue(value: Int): Boolean {
        TODO("containsValue")
    }
    override val entries: MutableSet<MutableMap.MutableEntry<Int, Int>>
        get() = TODO("entries")
    override val keys: MutableSet<Int>
        get() = TODO("keys")
    override val size: Int
        get() = TODO("size")
    override val values: MutableCollection<Int>
        get() = TODO("values")
    override fun clear() {}
    override fun putAll(from: Map<out Int, Int>) {}
    override fun remove(key: Int): Int {return 1}
    override fun isEmpty(): Boolean {return false}
    override fun containsKey(key: Int): Boolean {return false}
}

class Keycodes : MutableMap<Int, String> {
    private val savedValues = mutableMapOf<Int, String>()

    override fun get(key: Int): String {
        if (savedValues.containsKey(key)) {
            return savedValues[key].toString()
        }
        return "-1"
    }

    override fun put(key: Int, value: String): String? {
        return savedValues.put(key, value)
    }

    override fun containsValue(value: String): Boolean {
        TODO("containsValue")
    }
    override val entries: MutableSet<MutableMap.MutableEntry<Int, String>>
        get() = TODO("entries")
    override val keys: MutableSet<Int>
        get() = TODO("keys")
    override val size: Int
        get() = TODO("size")
    override val values: MutableCollection<String>
        get() = TODO("values")
    override fun clear() {}
    override fun putAll(from: Map<out Int, String>) {}
    override fun remove(key: Int): String? {return ""}
    override fun isEmpty(): Boolean {return false}
    override fun containsKey(key: Int): Boolean {return false}
}

val gdxKeycodes = GdxToKeycode()
val gdxButtons = GdxToKeycode()
val keycodesMap = Keycodes()


fun initKeycodes() {
    //gdxButtons
    gdxButtons[Input.Buttons.BACK] = 5
    gdxButtons[Input.Buttons.FORWARD] = 6
    gdxButtons[Input.Buttons.LEFT] = 1
    gdxButtons[Input.Buttons.RIGHT] = 2
    gdxButtons[Input.Buttons.MIDDLE] = 4

    //gdxKeycodes
    gdxKeycodes[A] = 65
    gdxKeycodes[ALT_LEFT] = 18
    gdxKeycodes[ALT_RIGHT] = 18
    gdxKeycodes[APOSTROPHE] = 222
    gdxKeycodes[AT]
    gdxKeycodes[B] = 66
    gdxKeycodes[BACK] = 166
    gdxKeycodes[BACKSLASH] = 226
    gdxKeycodes[BACKSPACE] = 8
    gdxKeycodes[BUTTON_A]
    gdxKeycodes[BUTTON_B]
    gdxKeycodes[BUTTON_C]
    gdxKeycodes[BUTTON_CIRCLE]
    gdxKeycodes[BUTTON_L1]
    gdxKeycodes[BUTTON_L2]
    gdxKeycodes[BUTTON_MODE]
    gdxKeycodes[BUTTON_R1]
    gdxKeycodes[BUTTON_R2]
    gdxKeycodes[BUTTON_SELECT]
    gdxKeycodes[BUTTON_START]
    gdxKeycodes[BUTTON_THUMBL]
    gdxKeycodes[BUTTON_THUMBR]
    gdxKeycodes[BUTTON_X]
    gdxKeycodes[BUTTON_Y]
    gdxKeycodes[BUTTON_Z]
    gdxKeycodes[C] = 67
    gdxKeycodes[CALL]
    gdxKeycodes[CAMERA]
    gdxKeycodes[CENTER]
    gdxKeycodes[CLEAR]
    gdxKeycodes[COLON]
    gdxKeycodes[COMMA] = 188
    gdxKeycodes[CONTROL_LEFT] = 17
    gdxKeycodes[CONTROL_RIGHT] = 17
    gdxKeycodes[D] = 68
    gdxKeycodes[DEL] = 46
    gdxKeycodes[DOWN] = 40
    gdxKeycodes[DPAD_CENTER]
    gdxKeycodes[DPAD_DOWN]
    gdxKeycodes[DPAD_LEFT]
    gdxKeycodes[DPAD_RIGHT]
    gdxKeycodes[DPAD_UP]
    gdxKeycodes[E] = 69
    gdxKeycodes[END] = 35
    gdxKeycodes[ENDCALL]
    gdxKeycodes[ENTER] = 13
    gdxKeycodes[ENVELOPE]
    gdxKeycodes[EQUALS]
    gdxKeycodes[ESCAPE] = 27
    gdxKeycodes[EXPLORER]
    gdxKeycodes[F] = 70
    gdxKeycodes[F1] = 112
    gdxKeycodes[F10] = 121
    gdxKeycodes[F11] = 122
    gdxKeycodes[F12] = 123
    gdxKeycodes[F2] = 113
    gdxKeycodes[F3] = 114
    gdxKeycodes[F4] = 115
    gdxKeycodes[F5] = 116
    gdxKeycodes[F6] = 117
    gdxKeycodes[F7] = 118
    gdxKeycodes[F8] = 119
    gdxKeycodes[F9] = 120
    gdxKeycodes[FOCUS]
    gdxKeycodes[FORWARD_DEL] = 46
    gdxKeycodes[G] = 71
    gdxKeycodes[GRAVE] = 192
    gdxKeycodes[H] = 72
    gdxKeycodes[HOME] = 36
    gdxKeycodes[HEADSETHOOK]
    gdxKeycodes[I] = 73
    gdxKeycodes[INSERT] = 45
    gdxKeycodes[J] = 74
    gdxKeycodes[K] = 75
    gdxKeycodes[L] = 76
    gdxKeycodes[LEFT] = 37
    gdxKeycodes[LEFT_BRACKET] = 221
    gdxKeycodes[M] = 77
    //gdxKeycodes[MAX_KEYCODE]
    gdxKeycodes[MEDIA_FAST_FORWARD]
    gdxKeycodes[MEDIA_NEXT]
    gdxKeycodes[MEDIA_PLAY_PAUSE]
    gdxKeycodes[MEDIA_PREVIOUS]
    gdxKeycodes[MEDIA_REWIND]
    gdxKeycodes[MEDIA_STOP]
    gdxKeycodes[MENU] = 164
    gdxKeycodes[META_ALT_LEFT_ON]
    gdxKeycodes[META_ALT_ON]
    gdxKeycodes[META_ALT_RIGHT_ON]
    gdxKeycodes[META_SHIFT_LEFT_ON]
    gdxKeycodes[META_SHIFT_ON]
    gdxKeycodes[META_SHIFT_RIGHT_ON]
    gdxKeycodes[META_SYM_ON]
    gdxKeycodes[MINUS] = 189
    gdxKeycodes[MUTE] = 173
    gdxKeycodes[N] = 78
    gdxKeycodes[NOTIFICATION]
    gdxKeycodes[NUM] = 144 //incorrect?
    gdxKeycodes[NUM_0] = 48
    gdxKeycodes[NUM_1] = 49
    gdxKeycodes[NUM_2] = 50
    gdxKeycodes[NUM_3] = 51
    gdxKeycodes[NUM_4] = 52
    gdxKeycodes[NUM_5] = 53
    gdxKeycodes[NUM_6] = 54
    gdxKeycodes[NUM_7] = 55
    gdxKeycodes[NUM_8] = 56
    gdxKeycodes[NUM_9] = 57
    gdxKeycodes[NUMPAD_0] = 96
    gdxKeycodes[NUMPAD_1] = 97
    gdxKeycodes[NUMPAD_2] = 98
    gdxKeycodes[NUMPAD_3] = 99
    gdxKeycodes[NUMPAD_4] = 100
    gdxKeycodes[NUMPAD_5] = 101
    gdxKeycodes[NUMPAD_6] = 102
    gdxKeycodes[NUMPAD_7] = 103
    gdxKeycodes[NUMPAD_8] = 104
    gdxKeycodes[NUMPAD_9] = 105
    gdxKeycodes[O] = 79
    gdxKeycodes[P] = 80
    gdxKeycodes[PAGE_DOWN] = 34
    gdxKeycodes[PAGE_UP] = 33
    gdxKeycodes[PERIOD]
    gdxKeycodes[PICTSYMBOLS]
    gdxKeycodes[PLUS] = 187
    gdxKeycodes[POUND]
    gdxKeycodes[POWER]
    gdxKeycodes[Q] = 81
    gdxKeycodes[R] = 82
    gdxKeycodes[RIGHT] = 39
    gdxKeycodes[RIGHT_BRACKET] = 219
    gdxKeycodes[S] = 83
    gdxKeycodes[SEARCH]
    gdxKeycodes[SEMICOLON] = 186
    gdxKeycodes[SHIFT_LEFT] = 160
    gdxKeycodes[SHIFT_RIGHT] = 161
    gdxKeycodes[SLASH] = 111
    gdxKeycodes[SOFT_LEFT]
    gdxKeycodes[SOFT_RIGHT]
    gdxKeycodes[SPACE] = 32
    gdxKeycodes[STAR]
    gdxKeycodes[SWITCH_CHARSET]
    gdxKeycodes[SYM] = 91
    gdxKeycodes[T] = 84
    gdxKeycodes[TAB] = 9
    gdxKeycodes[U] = 85
    gdxKeycodes[UNKNOWN]
    gdxKeycodes[UP] = 38
    gdxKeycodes[V] = 86
    gdxKeycodes[VOLUME_DOWN] = 174
    gdxKeycodes[VOLUME_UP] = 175
    gdxKeycodes[W] = 87
    gdxKeycodes[X] = 88
    gdxKeycodes[Y] = 89
    gdxKeycodes[Z] = 90
    //vk keycodes
    keycodesMap[-1] = "null"
    keycodesMap[1] = "m1"
    keycodesMap[2] = "m2"
    keycodesMap[3] = "break"
    keycodesMap[4] = "wheel"
    keycodesMap[5] = "m5"
    keycodesMap[6] = "m6"
    keycodesMap[7]
    keycodesMap[8] = "backspace"
    keycodesMap[9] = "tab"
    keycodesMap[10]
    keycodesMap[11]
    keycodesMap[12] = "clear"
    keycodesMap[13] = "enter"
    keycodesMap[14]
    keycodesMap[15]
    keycodesMap[16] = "shift"
    keycodesMap[17] = "ctrl"
    keycodesMap[18] = "alt"
    keycodesMap[19] = "pause"
    keycodesMap[20] = "caps lock"
    keycodesMap[21] = "null"
    keycodesMap[22] = "null"
    keycodesMap[23] = "null"
    keycodesMap[24] = "null"
    keycodesMap[25] = "null"
    keycodesMap[26] = "null"
    keycodesMap[27] = "esc"
    keycodesMap[28] = "null"
    keycodesMap[29] = "null"
    keycodesMap[30] = "null"
    keycodesMap[31] = "null"
    keycodesMap[32] = "space"
    keycodesMap[33] = "pg up"
    keycodesMap[34] = "pg down"
    keycodesMap[35] = "end"
    keycodesMap[36] = "home"
    keycodesMap[37] = "left arrow"
    keycodesMap[38] = "up arrow"
    keycodesMap[39] = "right arrow"
    keycodesMap[40] = "down arrow"
    keycodesMap[41] = "select"
    keycodesMap[42] = "print"
    keycodesMap[43] = "execute"
    keycodesMap[44] = "print screen"
    keycodesMap[45] = "insert"
    keycodesMap[46] = "delete"
    keycodesMap[47] = "help"
    keycodesMap[48] = "0"
    keycodesMap[49] = "1"
    keycodesMap[50] = "2"
    keycodesMap[51] = "3"
    keycodesMap[52] = "4"
    keycodesMap[53] = "5"
    keycodesMap[54] = "6"
    keycodesMap[55] = "7"
    keycodesMap[56] = "8"
    keycodesMap[57] = "9"
    keycodesMap[58] // empty
    keycodesMap[59] // empty
    keycodesMap[60] // empty
    keycodesMap[61] // empty
    keycodesMap[62] // empty
    keycodesMap[63] // empty
    keycodesMap[64] // empty
    keycodesMap[65] = "A"
    keycodesMap[66] = "B"
    keycodesMap[67] = "C"
    keycodesMap[68] = "D"
    keycodesMap[69] = "E"
    keycodesMap[70] = "F"
    keycodesMap[71] = "G"
    keycodesMap[72] = "H"
    keycodesMap[73] = "I"
    keycodesMap[74] = "J"
    keycodesMap[75] = "K"
    keycodesMap[76] = "L"
    keycodesMap[77] = "M"
    keycodesMap[78] = "N"
    keycodesMap[79] = "O"
    keycodesMap[80] = "P"
    keycodesMap[81] = "Q"
    keycodesMap[82] = "R"
    keycodesMap[83] = "S"
    keycodesMap[84] = "T"
    keycodesMap[85] = "U"
    keycodesMap[86] = "V"
    keycodesMap[87] = "W"
    keycodesMap[88] = "X"
    keycodesMap[89] = "Q"
    keycodesMap[90] = "Z"
    keycodesMap[91] = "win"
    keycodesMap[92] = "win"
    keycodesMap[93] = "null"
    keycodesMap[94] = "null"
    keycodesMap[95] = "sleep"
    keycodesMap[96] = "num0"
    keycodesMap[97] = "num1"
    keycodesMap[98] = "num2"
    keycodesMap[99] = "num3"
    keycodesMap[100] = "num4"
    keycodesMap[101] = "num5"
    keycodesMap[102] = "num6"
    keycodesMap[103] = "num7"
    keycodesMap[104] = "num8"
    keycodesMap[105] = "num9"
    keycodesMap[106] = "*"
    keycodesMap[107] = "+"
    keycodesMap[108] = "?" //incorrect?
    keycodesMap[109] = "-"
    keycodesMap[110] = "null"
    keycodesMap[111] = "/"
    keycodesMap[112] = "F1"
    keycodesMap[113] = "F2"
    keycodesMap[114] = "F3"
    keycodesMap[115] = "F4"
    keycodesMap[116] = "F5"
    keycodesMap[117] = "F6"
    keycodesMap[118] = "F7"
    keycodesMap[119] = "F8"
    keycodesMap[120] = "F9"
    keycodesMap[121] = "F10"
    keycodesMap[122] = "F11"
    keycodesMap[123] = "F12"
    keycodesMap[124] = "F13"
    keycodesMap[125] = "F14"
    keycodesMap[126] = "F15"
    keycodesMap[127] = "F16"
    keycodesMap[128] = "F17"
    keycodesMap[129] = "F18"
    keycodesMap[130] = "F19"
    keycodesMap[131] = "F20"
    keycodesMap[132] = "F21"
    keycodesMap[133] = "F22"
    keycodesMap[134] = "F23"
    keycodesMap[135] = "F24"
    keycodesMap[136] //Unassigned
    keycodesMap[137] //Unassigned
    keycodesMap[138] //Unassigned
    keycodesMap[139] //Unassigned
    keycodesMap[140] //Unassigned
    keycodesMap[141] //Unassigned
    keycodesMap[142] //Unassigned
    keycodesMap[143] //Unassigned
    keycodesMap[144] = "num lock"
    keycodesMap[145] = "scr lock"
    keycodesMap[146] = "null"
    keycodesMap[147] = "null"
    keycodesMap[148] = "null"
    keycodesMap[149] = "null"
    keycodesMap[150] = "null"
    keycodesMap[151] //Unassigned
    keycodesMap[152] //Unassigned
    keycodesMap[153] //Unassigned
    keycodesMap[154] //Unassigned
    keycodesMap[155] //Unassigned
    keycodesMap[156] //Unassigned
    keycodesMap[157] //Unassigned
    keycodesMap[158] //Unassigned
    keycodesMap[159] //Unassigned
    keycodesMap[160] = "shift"
    keycodesMap[161] = "shift"
    keycodesMap[162] = "ctrl"
    keycodesMap[163] = "ctrl"
    keycodesMap[164] = "menu"
    keycodesMap[165] = "menu"
    keycodesMap[166] = "brow back"
    keycodesMap[167] = "brow forw"
    keycodesMap[168] = "brow refr"
    keycodesMap[169] = "brow stop"
    keycodesMap[170] = "brow search"
    keycodesMap[171] = "brow favourite"
    keycodesMap[172] = "brow start"
    keycodesMap[173] = "vol mute"
    keycodesMap[174] = "vol down"
    keycodesMap[175] = "vol up"
    keycodesMap[176] = "next"
    keycodesMap[177] = "prev"
    keycodesMap[178] = "stop"
    keycodesMap[179] = "play"
    keycodesMap[180] = "mail"
    keycodesMap[181] = "media"
    keycodesMap[182] = "null"
    keycodesMap[183] = "null"
    keycodesMap[184] = "null"
    keycodesMap[185] = "null"
    keycodesMap[186] = ":"
    keycodesMap[187] = "+"
    keycodesMap[188] = ","
    keycodesMap[189] = "-"
    keycodesMap[190] = "."
    keycodesMap[191] = "?"
    keycodesMap[192] = "~"
    keycodesMap[193] = "null"
    keycodesMap[194] = "null"
    keycodesMap[195] = "null"
    keycodesMap[196] = "null"
    keycodesMap[197] = "null"
    keycodesMap[198] = "null"
    keycodesMap[199] = "null"
    keycodesMap[200] = "null"
    keycodesMap[201] = "null"
    keycodesMap[202] = "null"
    keycodesMap[203] = "null"
    keycodesMap[204] = "null"
    keycodesMap[205] = "null"
    keycodesMap[206] = "null"
    keycodesMap[207] = "null"
    keycodesMap[208] = "null"
    keycodesMap[209] = "null"
    keycodesMap[210] = "null"
    keycodesMap[211] = "null"
    keycodesMap[212] = "null"
    keycodesMap[213] = "null"
    keycodesMap[214] = "null"
    keycodesMap[215] = "null"
    keycodesMap[216] = "null"
    keycodesMap[217] = "null"
    keycodesMap[218] = "null"
    keycodesMap[219] = "["
    keycodesMap[220] = "|"
    keycodesMap[221] = "]"
    keycodesMap[222] = "'"
    keycodesMap[223] = "null"
    keycodesMap[224] = "null"
    keycodesMap[225] = "null"
    keycodesMap[226] = "\\"
    keycodesMap[227] = "null"
    keycodesMap[228] = "null"
    keycodesMap[229] = "null"
    keycodesMap[230] = "null"
    keycodesMap[231] = "null"
    keycodesMap[232] = "null"
    keycodesMap[233] = "null"
    keycodesMap[234] = "null"
    keycodesMap[235] = "null"
    keycodesMap[236] = "null"
    keycodesMap[237] = "null"
    keycodesMap[238] = "null"
    keycodesMap[239] = "null"
    keycodesMap[240] = "null"
    keycodesMap[241] = "null"
    keycodesMap[242] = "null"
    keycodesMap[243] = "null"
    keycodesMap[244] = "null"
    keycodesMap[245] = "null"
    keycodesMap[246] = "attn"
    keycodesMap[247] = "CrSel"
    keycodesMap[248] = "ExSel"
    keycodesMap[249] = "EOF"
    keycodesMap[250] = "play"
    keycodesMap[251] = "zoom"
    keycodesMap[252] = "null"
    keycodesMap[253] = "null"
    keycodesMap[254] = "clear"

}