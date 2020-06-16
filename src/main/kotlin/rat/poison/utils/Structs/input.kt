package rat.poison.utils.Structs

import com.sun.jna.Memory
import com.sun.jna.Structure
import org.jire.arrowhead.Struct
import rat.poison.utils.Vector

//All we need baby
fun memToInput(mem: Memory): Input {
    val Input = Input()

    Input.pCommands = input_pCommands(mem)
    Input.pVerifiedCommands = input_pVerifiedCommands(mem)

    return Input
}

class Input: Struct(), Structure.ByReference {
    @JvmField
    var bTrackIRAvailable = false
    @JvmField
    var bMouseInitialized = false
    @JvmField
    var bMouseActive = false
    @JvmField
    var bJoystickAdvancedInit = false
    //pKeys //0x34
    @JvmField
    var bCameraInterceptingMouse = false
    @JvmField
    var bCameraInThirdPerson = false
    @JvmField
    var bCameraMovingWithMouse = false

    var vecCameraOffset = Vector()

    @JvmField
    var bCameraDistanceMove = false
    @JvmField
    var nCameraOldX = 0
    @JvmField
    var nCameraOldY = 0
    @JvmField
    var nCameraX = 0
    @JvmField
    var nCameraY = 0
    @JvmField
    var bCameraIsOrthographic = false

    var vecPreviousViewAngles = Vector()

    var vecPreviousViewAnglesTilt = Vector()

    @JvmField
    var flLastForwardMove = 0f
    @JvmField
    var nClearInputState = 0
    @JvmField
    var pCommands: Int = 0
    @JvmField
    var pVerifiedCommands: Int = 0
}

fun input_pCommands(mem: Memory): Int {
    return mem.getInt(0xF4) //0xEC
}

fun input_pVerifiedCommands(mem: Memory): Int {
    return mem.getInt(0xF8)
}