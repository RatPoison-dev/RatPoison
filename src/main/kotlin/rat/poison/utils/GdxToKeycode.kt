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

class DuplicationsMap : MutableMap<Int, List<Int>> {
    private val savedValues = mutableMapOf<Int, List<Int>>()

    override fun get(key: Int): List<Int> {
        if (savedValues.containsKey(key)) {
            return savedValues[key]!!
        }
        return listOf(key)
    }

    override fun put(key: Int, value: List<Int>): List<Int>? {
        return savedValues.put(key, value)
    }

    override fun containsValue(value: List<Int>): Boolean {
        TODO("containsValue")
    }
    override val entries: MutableSet<MutableMap.MutableEntry<Int, List<Int>>>
        get() = TODO("entries")
    override val keys: MutableSet<Int>
        get() = TODO("keys")
    override val size: Int
        get() = TODO("size")
    override val values: MutableCollection<List<Int>>
        get() = TODO("values")
    override fun clear() {}
    override fun putAll(from: Map<out Int, List<Int>>) {}
    override fun remove(key: Int): List<Int> {return listOf()}
    override fun isEmpty(): Boolean {return false}
    override fun containsKey(key: Int): Boolean {return false}
}

val gdxKeycodes = GdxToKeycode()
val gdxButtons = GdxToKeycode()
val keycodesMap = Keycodes()
val duplicationsMap = DuplicationsMap()

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


fun initKeycodes() {
    //duplications
    duplicationsMap[111] = listOf(191, 111)
    duplicationsMap[191] = listOf(111, 191)
    //0 (caps/unknown)
    duplicationsMap[20] = listOf(20, 145, 19, 44, 144)
    duplicationsMap[145] = listOf(145, 20, 19, 44, 144)
    duplicationsMap[19] = listOf(145, 20, 145, 44, 144)
    duplicationsMap[44] = listOf(145, 20, 145, 44, 144)
    duplicationsMap[144] = listOf(145, 20, 145, 44, 144)

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
    gdxKeycodes[BACK] = 220
    gdxKeycodes[BACKSLASH] = 220
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
    gdxKeycodes[EQUALS] = 187
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
    gdxKeycodes[LEFT_BRACKET] = 219
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
    gdxKeycodes[PERIOD] = 110
    gdxKeycodes[PICTSYMBOLS]
    gdxKeycodes[PLUS] = 187
    gdxKeycodes[POUND]
    gdxKeycodes[POWER]
    gdxKeycodes[Q] = 81
    gdxKeycodes[R] = 82
    gdxKeycodes[RIGHT] = 39
    gdxKeycodes[RIGHT_BRACKET] = 221
    gdxKeycodes[S] = 83
    gdxKeycodes[SEARCH]
    gdxKeycodes[SEMICOLON] = 186
    gdxKeycodes[SHIFT_LEFT] = 160
    gdxKeycodes[SHIFT_RIGHT] = 161
    gdxKeycodes[SLASH] = 191
    gdxKeycodes[SOFT_LEFT]
    gdxKeycodes[SOFT_RIGHT]
    gdxKeycodes[SPACE] = 32
    gdxKeycodes[STAR] = 106
    gdxKeycodes[SWITCH_CHARSET]
    gdxKeycodes[SYM] = 91
    gdxKeycodes[T] = 84
    gdxKeycodes[TAB] = 9
    gdxKeycodes[U] = 85
    gdxKeycodes[UNKNOWN] = 20
    gdxKeycodes[UP] = 38
    gdxKeycodes[V] = 86
    gdxKeycodes[VOLUME_DOWN] = 174
    gdxKeycodes[VOLUME_UP] = 175
    gdxKeycodes[W] = 87
    gdxKeycodes[X] = 88
    gdxKeycodes[Y] = 89
    gdxKeycodes[Z] = 90
    //vk keycodes
    keycodesMap[-1] = "none"
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
    keycodesMap[20] = "caps"
    keycodesMap[21] = "none"
    keycodesMap[22] = "none"
    keycodesMap[23] = "none"
    keycodesMap[24] = "none"
    keycodesMap[25] = "none"
    keycodesMap[26] = "none"
    keycodesMap[27] = "esc"
    keycodesMap[28] = "none"
    keycodesMap[29] = "none"
    keycodesMap[30] = "none"
    keycodesMap[31] = "none"
    keycodesMap[32] = "space"
    keycodesMap[33] = "pg up"
    keycodesMap[34] = "pg dwn"
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
    keycodesMap[93] = "none"
    keycodesMap[94] = "none"
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
    keycodesMap[108] = "/"
    keycodesMap[109] = "-"
    keycodesMap[110] = ","
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
    keycodesMap[146] = "none"
    keycodesMap[147] = "none"
    keycodesMap[148] = "none"
    keycodesMap[149] = "none"
    keycodesMap[150] = "none"
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
    keycodesMap[182] = "none"
    keycodesMap[183] = "none"
    keycodesMap[184] = "none"
    keycodesMap[185] = "none"
    keycodesMap[186] = ":"
    keycodesMap[187] = "+"
    keycodesMap[188] = ","
    keycodesMap[189] = "-"
    keycodesMap[190] = "."
    keycodesMap[191] = "/"
    keycodesMap[192] = "~"
    keycodesMap[193] = "none"
    keycodesMap[194] = "none"
    keycodesMap[195] = "none"
    keycodesMap[196] = "none"
    keycodesMap[197] = "none"
    keycodesMap[198] = "none"
    keycodesMap[199] = "none"
    keycodesMap[200] = "none"
    keycodesMap[201] = "none"
    keycodesMap[202] = "none"
    keycodesMap[203] = "none"
    keycodesMap[204] = "none"
    keycodesMap[205] = "none"
    keycodesMap[206] = "none"
    keycodesMap[207] = "none"
    keycodesMap[208] = "none"
    keycodesMap[209] = "none"
    keycodesMap[210] = "none"
    keycodesMap[211] = "none"
    keycodesMap[212] = "none"
    keycodesMap[213] = "none"
    keycodesMap[214] = "none"
    keycodesMap[215] = "none"
    keycodesMap[216] = "none"
    keycodesMap[217] = "none"
    keycodesMap[218] = "none"
    keycodesMap[219] = "["
    keycodesMap[220] = "|"
    keycodesMap[221] = "]"
    keycodesMap[222] = "'"
    keycodesMap[223] = "none"
    keycodesMap[224] = "none"
    keycodesMap[225] = "none"
    keycodesMap[226] = "\\"
    keycodesMap[227] = "none"
    keycodesMap[228] = "none"
    keycodesMap[229] = "none"
    keycodesMap[230] = "none"
    keycodesMap[231] = "none"
    keycodesMap[232] = "none"
    keycodesMap[233] = "none"
    keycodesMap[234] = "none"
    keycodesMap[235] = "none"
    keycodesMap[236] = "none"
    keycodesMap[237] = "none"
    keycodesMap[238] = "none"
    keycodesMap[239] = "none"
    keycodesMap[240] = "none"
    keycodesMap[241] = "none"
    keycodesMap[242] = "none"
    keycodesMap[243] = "none"
    keycodesMap[244] = "none"
    keycodesMap[245] = "none"
    keycodesMap[246] = "attn"
    keycodesMap[247] = "CrSel"
    keycodesMap[248] = "ExSel"
    keycodesMap[249] = "EOF"
    keycodesMap[250] = "play"
    keycodesMap[251] = "zoom"
    keycodesMap[252] = "none"
    keycodesMap[253] = "none"
    keycodesMap[254] = "clear"
}