package rat.poison.scripts

import com.sun.jna.Structure
import org.jire.arrowhead.Struct
import rat.poison.game.CSGO.engineDLL
import rat.poison.game.offsets.EngineOffsets.dwbSendPacket
import rat.poison.toInt
import rat.poison.utils.Vector

private var bestTarget: Long = -1L

const val MULTIPLAYER_BACKUP = 150

//prolly outdated
class UserCmd: Struct(), Structure.ByReference {
    @JvmField
    var pVft: Int = -1 //0x00
    @JvmField
    var iCmdNumber: Int = -1 //0x04
    @JvmField
    var iTickCount: Int = -1 //0x08
    @JvmField
    var vecViewAnglesX: Double = -1.0
    @JvmField
    var vecViewAnglesY: Double = -1.0
    @JvmField
    var vecViewAnglesZ: Double = -1.0
    @JvmField
    var vecAimDirectionX: Double = -1.0
    @JvmField
    var vecAimDirectionY: Double = -1.0
    @JvmField
    var vecAimDirectionZ: Double = -1.0
    @JvmField
    var flForwardMove: Float = -1F //0x24
    @JvmField
    var flSideMove: Float = -1F //0x28
    @JvmField
    var flUpMove: Float = -1F //0x2C
    @JvmField
    var iButtons: Int = -1 //0x30
    @JvmField
    var bImpulse: Byte = -1 //0x34
    @JvmField
    var pad1: Byte = -1
    @JvmField
    var iWeaponSelect: Int = -1 //0x38
    @JvmField
    var iWeaponSubtype: Int = -1 //0x3C
    @JvmField
    var iRandomSeed: Int = -1 //0x40
    @JvmField
    var siMouseDx: Short = -1 //0x44
    @JvmField
    var siMouseDy: Short = -1 //0x46
    @JvmField
    var bHasBeenPredicted: Boolean = false //0x48
} //0x64 Size

class Input: Struct(), Structure.ByReference {
    @JvmField
    var pVftable: Int = -1
    @JvmField
    var m_bTrackIRAvailable: Boolean = false
    @JvmField
    var m_bMouseInitialized: Boolean = false
    @JvmField
    var m_bMouseActive: Boolean = false
    @JvmField
    var m_bJoystickAdvancedInit: Boolean = false
    @JvmField
    var Unk1: ByteArray = ByteArray(44)
    @JvmField
    var m_pKeys: Int = -1
    @JvmField
    var Unk2: ByteArray = ByteArray(100)
    @JvmField
    var m_bCameraInterceptingMouse: Boolean = false
    @JvmField
    var m_bCameraInThirdPerson: Boolean = false
    @JvmField
    var m_bCameraMovingWithMouse: Boolean = false
    @JvmField
    var m_vecCameraOffsetX: Double = -1.0
    @JvmField
    var m_vecCameraOffsetY: Double = -1.0
    @JvmField
    var m_vecCameraOffsetZ: Double = -1.0
    @JvmField
    var m_bCameraDistanceMove: Boolean = false
    @JvmField
    var m_nCameraOldX: Int = -1
    @JvmField
    var m_nCameraOldY: Int = -1
    @JvmField
    var m_nCameraX: Int = -1
    @JvmField
    var m_nCameraY: Int = -1
    @JvmField
    var m_bCameraIsOrthographic: Boolean = false
    @JvmField
    var m_vecPreviousViewAnglesX: Double = -1.0
    @JvmField
    var m_vecPreviousViewAnglesY: Double = -1.0
    @JvmField
    var m_vecPreviousViewAnglesZ: Double = -1.0
    @JvmField
    var m_vecPreviousViewAnglesTiltX: Double = -1.0
    @JvmField
    var m_vecPreviousViewAnglesTiltY: Double = -1.0
    @JvmField
    var m_vecPreviousViewAnglesTiltZ: Double = -1.0
    @JvmField
    var m_flLastForwardMove: Float = -1F
    @JvmField
    var m_nClearInputState: Int = -1
    @JvmField
    var Unk3: ByteArray = ByteArray(0x8)
    @JvmField
    var m_pCommands: Int = -1
    @JvmField
    var m_pVerifiedCommands: Int = -1
} //240 Size or //244

fun sendPacket(status: Boolean) {
    engineDLL[dwbSendPacket] = status.toInt().toByte()
}

//Weapon.CanFire()

fun fixCmd(currentCmd: UserCmd, oldCmd: UserCmd): UserCmd {
    currentCmd.vecViewAnglesX = oldCmd.vecViewAnglesX
    currentCmd.vecViewAnglesY = oldCmd.vecViewAnglesY
    currentCmd.vecViewAnglesZ = oldCmd.vecViewAnglesZ
    currentCmd.vecAimDirectionX = oldCmd.vecAimDirectionX
    currentCmd.vecAimDirectionY = oldCmd.vecAimDirectionY
    currentCmd.vecAimDirectionZ = oldCmd.vecAimDirectionZ
    currentCmd.flForwardMove = oldCmd.flForwardMove
    currentCmd.flSideMove = oldCmd.flSideMove
    currentCmd.flUpMove = oldCmd.flUpMove
    currentCmd.iButtons = oldCmd.iButtons
    currentCmd.siMouseDx = oldCmd.siMouseDx
    currentCmd.siMouseDy = oldCmd.siMouseDy

    return currentCmd
}

//fun bestSimTime(): Float {
//    if (bestTarget == -1L) {
//        return -1F
//    }
//
//    var tmp = Float.MAX_VALUE
//    var best = -1
//    for (i in 0 until 12) {
//
//    }
//}

