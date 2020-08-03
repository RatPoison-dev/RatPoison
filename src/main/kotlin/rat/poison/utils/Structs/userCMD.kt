package rat.poison.utils.Structs

import com.sun.jna.Memory
import com.sun.jna.Structure
import org.jire.arrowhead.Struct
import rat.poison.game.CSGO.csgoEXE
import rat.poison.utils.Vector
import rat.poison.utils.generalUtil.strToBool

//This is me when kotlin doesnt have structs and my brain is extremely smooth
fun userCMDToMem(ptr: Int, cmd: UserCMD) {
    csgoEXE[ptr] = cmd.ptr

    csgoEXE[ptr + 0x04] = cmd.iCmdNumber

    csgoEXE[ptr + 0x08] = cmd.iTickCount

    csgoEXE[ptr + 0x0C] = cmd.vecViewAngles.x
    csgoEXE[ptr + 0x10] = cmd.vecViewAngles.y
    csgoEXE[ptr + 0x14] = cmd.vecViewAngles.z

    csgoEXE[ptr + 0x18] = cmd.vecAimDirection.x
    csgoEXE[ptr + 0x1C] = cmd.vecAimDirection.y
    csgoEXE[ptr + 0x20] = cmd.vecAimDirection.z

    csgoEXE[ptr + 0x24] = cmd.flForwardmove

    csgoEXE[ptr + 0x28] = cmd.flSidemove

    csgoEXE[ptr + 0x2C] = cmd.flUpmove

    csgoEXE[ptr + 0x30] = cmd.iButtons

    csgoEXE[ptr + 0x34] = cmd.bImpulse

    csgoEXE[ptr + 0x38] = cmd.iWeaponSelect

    csgoEXE[ptr + 0x3C] = cmd.iWeaponSubtype

    csgoEXE[ptr + 0x40] = cmd.iRandomSeed

    csgoEXE[ptr + 0x44] = cmd.siMouseDx

    csgoEXE[ptr + 0x46] = cmd.siMouseDy

    csgoEXE[ptr + 0x48] = cmd.bHasBeenPredicted
}

fun fixUserCMD(cur: UserCMD, old: UserCMD): UserCMD {
    cur.vecViewAngles = old.vecViewAngles
    cur.vecAimDirection = old.vecAimDirection
    cur.flForwardmove = old.flForwardmove
    cur.flSidemove = old.flSidemove
    cur.flUpmove = old.flUpmove
    cur.iButtons = old.iButtons
    cur.siMouseDx = old.siMouseDx
    cur.siMouseDy = old.siMouseDy

    return cur
}

fun memToUserCMD(mem: Memory): UserCMD {
    val userCMD = UserCMD()

    userCMD.ptr = cmd_pTable(mem)
    userCMD.iCmdNumber = cmd_iCmdNumber(mem)
    userCMD.iTickCount = cmd_iTickCount(mem)
    userCMD.vecViewAngles = cmd_vecViewAngles(mem)
    userCMD.vecAimDirection = cmd_vecAimDirection(mem)
    userCMD.flForwardmove = cmd_flForwardmove(mem)
    userCMD.flSidemove = cmd_flSidemove(mem)
    userCMD.flUpmove = cmd_flUpmove(mem)
    userCMD.iButtons = cmd_iButtons(mem)
    userCMD.bImpulse = cmd_bImpulse(mem)
    userCMD.iWeaponSelect = cmd_iWeaponSelect(mem)
    userCMD.iWeaponSubtype = cmd_iWeaponSubtype(mem)
    userCMD.iRandomSeed = cmd_iRandomSeed(mem)
    userCMD.siMouseDx = cmd_siMouseDx(mem)
    userCMD.siMouseDy = cmd_siMouseDy(mem)
    userCMD.bHasBeenPredicted = cmd_bHasBeenPredicted(mem)

    return userCMD
}

class UserCMD: Struct(), Structure.ByReference {
    @JvmField
    var ptr = 0

    @JvmField
    var iCmdNumber = 0
    @JvmField
    var iTickCount = 0

    var vecViewAngles = Vector()
    var vecAimDirection = Vector()

    @JvmField
    var flForwardmove = 0f
    @JvmField
    var flSidemove = 0f
    @JvmField
    var flUpmove = 0f
    @JvmField
    var iButtons = 0
    @JvmField
    var bImpulse = 0
    @JvmField
    var iWeaponSelect = 0
    @JvmField
    var iWeaponSubtype = 0
    @JvmField
    var iRandomSeed = 0
    @JvmField
    var siMouseDx = 0
    @JvmField
    var siMouseDy = 0
    @JvmField
    var bHasBeenPredicted = false
}

class verifiedUserCMD: Struct(), Structure.ByReference {
    var m_Command = UserCMD()
    var m_Crc = 0
}

fun cmd_pTable(mem: Memory): Int {
    return mem.getInt(0x00)
}

fun cmd_iCmdNumber(mem: Memory): Int {
    return mem.getInt(0x04)
}

fun cmd_iTickCount(mem: Memory): Int {
    return mem.getInt(0x08)
}

fun cmd_vecViewAngles(mem: Memory): Vector {
    return Vector(mem.getFloat(0x0C), mem.getFloat(0x10), mem.getFloat(0x14))
}

fun cmd_vecAimDirection(mem: Memory): Vector {
    return Vector(mem.getFloat(0x18), mem.getFloat(0x1C), mem.getFloat(0x20))
}

fun cmd_flForwardmove(mem: Memory): Float {
    return mem.getFloat(0x24)
}

fun cmd_flSidemove(mem: Memory): Float {
    return mem.getFloat(0x28)
}

fun cmd_flUpmove(mem: Memory): Float {
    return mem.getFloat(0x2C)
}

fun cmd_iButtons(mem: Memory): Int {
    return mem.getInt(0x30)
}

fun cmd_bImpulse(mem: Memory): Int { //Byte
    return mem.getByte(0x34).toInt()
}

fun cmd_iWeaponSelect(mem: Memory): Int {
    return mem.getInt(0x38)
}

fun cmd_iWeaponSubtype(mem: Memory): Int {
    return mem.getInt(0x3C)
}

fun cmd_iRandomSeed(mem: Memory): Int {
    return mem.getInt(0x40)
}

fun cmd_siMouseDx(mem: Memory): Int { //Short
    return mem.getShort(0x44).toInt()
}

fun cmd_siMouseDy(mem: Memory): Int { //Short
    return mem.getShort(0x46).toInt()
}

fun cmd_bHasBeenPredicted(mem: Memory): Boolean {
    return mem.getByte(0x48).toString().strToBool()
}