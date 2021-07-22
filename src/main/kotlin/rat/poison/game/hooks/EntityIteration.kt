package rat.poison.game.hooks

import com.sun.jna.Memory
import com.sun.jna.platform.win32.WinNT
import rat.poison.dbg
import rat.poison.game.*
import rat.poison.game.CSGO.GLOW_OBJECT_SIZE
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.CSGO.engineDLL
import rat.poison.game.entity.EntityType
import rat.poison.game.entity.absPosition
import rat.poison.game.offsets.ClientOffsets
import rat.poison.game.offsets.ClientOffsets.dwEntityList
import rat.poison.game.offsets.ClientOffsets.dwGlowObject
import rat.poison.game.offsets.ClientOffsets.dwLocalPlayer
import rat.poison.game.offsets.ClientOffsets.dwSensitivity
import rat.poison.game.offsets.ClientOffsets.dwSensitivityPtr
import rat.poison.game.offsets.EngineOffsets.dwClientState
import rat.poison.game.offsets.EngineOffsets.dwClientState_MapDirectory
import rat.poison.game.offsets.EngineOffsets.dwGameDir
import rat.poison.game.offsets.EngineOffsets.dwSignOnState
import rat.poison.scripts.detectMap
import rat.poison.scripts.nameChange
import rat.poison.scripts.sendPacket
import rat.poison.settings.*
import rat.poison.utils.every
import rat.poison.utils.extensions.uint
import rat.poison.utils.inGame
import rat.poison.utils.shouldPostProcess
import java.util.concurrent.atomic.AtomicLong
import kotlin.properties.Delegates

private val lastCleanup = AtomicLong(0L)

private val contexts = Array(MAX_ENTITIES) { EntityContext() }

private fun shouldReset() = System.currentTimeMillis() - lastCleanup.get() >= CLEANUP_TIME

private fun reset() {
    for (i in entitiesValues) {
        i?.removeAll(i)
    }

    lastCleanup.set(System.currentTimeMillis())
}

private var state by Delegates.observable(SignOnState.MAIN_MENU) { _, old, new ->
    if (old != new) {
        if (new.name == SignOnState.IN_GAME.name) {
            Thread(Runnable {
                Thread.sleep(10000)
                shouldPostProcess = true
            }).start()

            val strBuf: Memory by lazy {
                Memory(128) //128 str?
            }

            csgoEXE.read(clientState + dwClientState_MapDirectory, strBuf)
            val mapName = strBuf.getString(0)

            engineDLL.read(dwGameDir, strBuf)
            val gameDir = strBuf.getString(0)

            if (mapName.isNotBlank() && gameDir.isNotBlank()) {
                if (dbg) {
                    println("[DEBUG] Detecting nade map at -- $gameDir\\$mapName")
                }
                detectMap(mapName)

                //loadBsp("$gameDir\\$mapName")
            }

            //Find correct tonemap values
//        File("$SETTINGS_DIRECTORY\\Data\\ToneMaps.txt").forEachLine { line ->
//            if (mapName.lowercase().contains(line.split(" : ")[0].lowercase())) {
//                //this is working... not needed for now
//            }
//        }

            inGame = true
            nameChange = ""

            if (PROCESS_ACCESS_FLAGS and WinNT.PROCESS_VM_OPERATION > 0) {
                val write = 0xEB.toByte()
                try {
                    clientDLL[ClientOffsets.dwGlowUpdate] = write
                } catch (e: Exception) { }
            }

            if (GARBAGE_COLLECT_ON_MAP_START) {
                System.gc()
            }

            sendPacket(true)
        } else {
            shouldPostProcess = false
            inGame = false
            sendPacket(true)
        }
    }
}

var cursorEnable = false
private val cursorEnableAddress by lazy(LazyThreadSafetyMode.NONE) { clientDLL.address + ClientOffsets.dwMouseEnable }
private val cursorEnablePtr by lazy(LazyThreadSafetyMode.NONE) { clientDLL.address + ClientOffsets.dwMouseEnablePtr }

fun updateCursorEnable() { //Call when needed
    cursorEnable = csgoEXE.int(cursorEnableAddress) xor cursorEnablePtr.toInt() != 1
}

var toneMapController = 0L

fun constructEntities() = every(500, continuous = true) {
    updateCursorEnable()
    clientState = engineDLL.uint(dwClientState)
    state = SignOnState[csgoEXE.int(clientState + dwSignOnState)]

    me = clientDLL.uint(dwLocalPlayer)
    if (!inGame || me <= 0L) return@every

    val glowObject = clientDLL.uint(dwGlowObject)
    val glowObjectCount = clientDLL.int(dwGlowObject + 4)

    if (shouldReset()) reset()

    var dzMode = false

    for (glowIndex in 0..glowObjectCount) {
        val glowAddress = glowObject + (glowIndex * GLOW_OBJECT_SIZE) + 4
        val entity = csgoEXE.uint(glowAddress)

        if (entity > 0L) {
            val type = EntityType.byEntityAddress(entity)
            if (type != EntityType.NULL) {
                val tmpPos = entity.absPosition()
                val check = (tmpPos.x in -2.0F..2.0F && tmpPos.y in -2.0F..2.0F && tmpPos.z in -2.0F..2.0F)

                if (!check) {
                    val context = contexts[glowIndex].set(entity, glowAddress, glowIndex, type) //remove contexts[]

                    with(entities[type]!!) {
                        if (!contains(context)) {
                            add(context)
                        }
                    }
                }

                if (type == EntityType.CFists) {
                    dzMode = true
                }
            }
        }
    }

    val maxIndex = clientDLL.int(dwEntityList + 0x24) //Not right?

    for (i in 64..maxIndex) {
        val entity = clientDLL.uint(dwEntityList + (i * 0x10) - 0x10)

        if (entity != 0L) {
            val type = EntityType.byEntityAddress(entity)

            if (type == EntityType.CEconEntity) {
                val context = EntityContext(entity)

                with(entities[type]!!) {
                    if (!contains(context)) {
                        add(context)
                    }
                }
            }

            if (type == EntityType.CEnvTonemapController) {
                toneMapController = entity
            }
        }
    }

    DANGER_ZONE = dzMode
    GAME_SENSITIVITY = java.lang.Float.intBitsToFloat((clientDLL.uint(dwSensitivity) xor (clientDLL.address + dwSensitivityPtr)).toInt()).toDouble()
}