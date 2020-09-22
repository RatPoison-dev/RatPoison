package rat.poison.utils.Structs

import com.sun.jna.Memory
import com.sun.jna.Structure
import org.jire.arrowhead.Struct
import rat.poison.scripts.gvars

fun memToGlobalVars(mem: Memory): GlobalVars {
    val tmp_gvars = gvars

    tmp_gvars.tickCount = gvar_tickCount(mem)
    tmp_gvars.intervalPerTick = gvar_intervalPerTick(mem)

    return tmp_gvars
}

class GlobalVars: Struct(), Structure.ByReference {
    @JvmField
    var realTime = 0f
    @JvmField
    var frameCount = 0
    @JvmField
    var absoluteFrameTime = 0f
    @JvmField
    var absoluteFrameStartTimeSTDDev = 0f
    @JvmField
    var curTime = 0f
    @JvmField
    var frameTime = 0f
    @JvmField
    var maxClients = 0
    @JvmField
    var tickCount = 0
    @JvmField
    var intervalPerTick = 0f
    @JvmField
    var interpolationAmount = 0f
    @JvmField
    var simTicksThisFrame = 0
    @JvmField
    var networkProtocol = 0

    //pSaveData void*
    @JvmField
    var m_bClient = false
    @JvmField
    var nTimestampNetworkingBase = 0
    @JvmField
    var nTimestampRandomizeWindow = 0
}

fun gvar_tickCount(mem: Memory): Int {
    return mem.getInt(0x1C)
}

fun gvar_intervalPerTick(mem: Memory): Float {
    return mem.getFloat(0x20)
}