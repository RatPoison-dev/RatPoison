@file:JvmName("RatPoison")
@file:Suppress("BlockingMethodInNonBlockingContext")

package rat.poison

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.sun.jna.Callback
import com.sun.jna.NativeLibrary
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.lwjgl.glfw.GLFW.*
import rat.poison.game.CSGO
import rat.poison.game.offsets.EngineOffsets.dwbSendPackets
import rat.poison.jna.Interception
import rat.poison.jna.Kernel32
import rat.poison.jna.User32
import rat.poison.overlay.App
import rat.poison.scripts.*
import rat.poison.scripts.aim.flatAim
import rat.poison.scripts.aim.handleFireKey
import rat.poison.scripts.aim.pathAim
import rat.poison.scripts.aim.setAim
import rat.poison.scripts.visuals.*
import rat.poison.utils.Settings
import rat.poison.utils.detectLocale
import rat.poison.utils.generalUtil.loadSettingsFromFiles
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.loadMigration
import java.awt.Robot
import java.io.File
import java.nio.file.Paths
import kotlin.collections.set


//Override Weapon
data class oWeapon(var tOverride: Boolean = false,      var tFRecoil: Boolean = false,          var tOnShot: Boolean = false,
                   var tFlatAim: Boolean = false,       var tPathAim: Boolean = false,          var tAimBone: Int = 0,
                   var tForceBone: Int = 0,             var tAimFov: Float = 0F,                var tAimSpeed: Int = 0,
                   var tAimSmooth: Float = 0F,        var tPerfectAim: Boolean = false,       var tPAimFov: Float = 1F,
                   var tPAimChance: Int = 1,            var tScopedOnly: Boolean = false,       var tAimAfterShots: Int = 0,

                   var tBoneTrig: Boolean = false,     var tBTrigAim: Boolean = false,          var tBTrigInCross: Boolean = false,
                   var tBTrigInFov: Boolean = false,   var tBTrigBacktrack: Boolean = false,    var tBTrigFov: Float = 0F,
                   var tBTrigInitDelay: Int = 0,       var tBTrigPerShotDelay: Int = 0,

                   var tBacktrack: Boolean = false,    var tBTMS: Int = 0)

//Skinned Weapon
data class sWeapon(var tSkinID: Int, var tStatTrak: Int, var tWear: Float, var tSeed: Int)

const val TITLE = "RatPoison"
const val BRANCH = "Master"
const val F_VERSION = "1.7"
const val M_VERSION = "1.7.2"
var LOADED_CONFIG = "DEFAULT"


val charPool = ('A'..'Z') + ('a'..'z') + ('0'..'9')
fun randString(length: Int) : String {
    return (1..length)
        .map { charPool.random() }
        .joinToString("")
}


//const val EXPERIMENTAL = false
const val SETTINGS_DIRECTORY = "settings" //Internal
val DEFAULT_MENU_APP = "Counter-Strike: Global Offensive - Direct3D 9"
var MENU_APP = ""
var safe_mode = true
var appless = false
var inputType = InputDeviceType.INTERCEPTION
var app_title = randString(20)
lateinit var WEAPON_STATS_FILE: File
lateinit var SKIN_INFO_FILE: File

var settingsLoaded = false
val curSettings = Settings()
val curLocale = Settings()
lateinit var input: InputThread

var dbg: Boolean = true
val robot = Robot().apply { this.autoDelay = 0 }

var haltProcess = false

fun main() {
    System.setProperty("jna.nosys", "true")
    NativeLibrary.addSearchPath("interception", Paths.get("./drivers").toAbsolutePath().toString())
    Kernel32.raiseProcessPriority()
    loadSettingsFromFiles(SETTINGS_DIRECTORY)

    detectLocale()

    dbg = curSettings["DEBUG"].strToBool()
    if (dbg) println("DEBUG enabled")

    println("Launching...")
    MENU_APP = curSettings["MENU_APP"].replace("\"", "")
    appless = curSettings["APPLESS"].strToBool()
    safe_mode = curSettings["SAFE_MODE"].strToBool()
    inputType = InputDeviceType.fromSetting(curSettings["INPUT_DEVICE_TYPE"])
    input = when (inputType) {
        InputDeviceType.INTERCEPTION -> {
            try {
                InterceptionInputThread()
            } catch (e: UnsatisfiedLinkError) {
                println("Interception library not found, falling back to USERMODE input.")
                inputType = InputDeviceType.USERMODE
                UsermodeInputThread()
            } catch (e: Exception) {
                println("Interception initialization failed, falling back to USERMODE input.")
                inputType = InputDeviceType.USERMODE
                UsermodeInputThread()
            }
        }

        InputDeviceType.USERMODE -> UsermodeInputThread()
    }
    input.start()

    CSGO.initialize()

    WEAPON_STATS_FILE = File("$SETTINGS_DIRECTORY\\Data\\WeaponStats.txt")
    SKIN_INFO_FILE = File("$SETTINGS_DIRECTORY\\Data\\SkinInfo.txt")

    loadMigration()

    if (dbg) println("[DEBUG] Initializing scripts...")
    //Init scripts
    if (dbg) { println("[DEBUG] Initializing input") }

    if (!curSettings["MENU"].strToBool()) { //If we aren't using the menu disable everything that uses the menu
        if (dbg) println("[DEBUG] Menu disabled, disabling box, skeleton, rcrosshair, btimer, indicator, speclist, hitmarker, nade helper, nade tracer, draw fov, spread circle, visualize smokes")

        curSettings["ENABLE_BOX_ESP"] = "false"
        curSettings["SKELETON_ESP"] = "false"
        curSettings["ENABLE_RECOIL_CROSSHAIR"] = "false"
        curSettings["ENABLE_BOMB_TIMER"] = "false"
        curSettings["INDICATOR_ESP"] = "false"
        curSettings["SPECTATOR_LIST"] = "false"
        curSettings["ENABLE_HITMARKER"] = "false"
        curSettings["SNAPLINES"] = "false"
        curSettings["ENABLE_NADE_HELPER"] = "false"
        curSettings["NADE_TRACER"] = "false"
        curSettings["DRAW_AIM_FOV"] = "false"
        curSettings["ENABLE_HITSOUND"] = "false"
        curSettings["SPREAD_CIRCLE"] = "false"
        curSettings["VISUALIZE_SMOKES"] = "false"
    } else {
        if (dbg) { println("[DEBUG] Initializing Ranks") }; ranks()

        if (dbg) { println("[DEBUG] Initializing Recoil Spectator List") }; spectatorList()
        if (dbg) { println("[DEBUG] Initializing Recoil Bomb Timer") }; bombTimer()

        if (dbg) { println("[DEBUG] Initializing Recoil Crosshair") }; rcrosshair()
        if (dbg) { println("[DEBUG] Initializing Hit Marker") }; hitMarker()
        if (dbg) { println("[DEBUG] Initializing Nade Helper") }; nadeHelper()
        if (dbg) { println("[DEBUG] Initializing Nade Tracer") }; nadeTracer()
        if (dbg) { println("[DEBUG] Initializing Draw Fov") }; drawFov()
        if (dbg) { println("[DEBUG] Initializing Spread Circle") }; spreadCircle()
        if (dbg) { println("[DEBUG] Initializing Draw Smokes") }; drawSmokes()
        if (dbg) {println("[DEBUG] Initializing Far Radar") }; farRadar()
        //farEsp()

        drawDebug()
    }
    if (!safe_mode) {
        if (dbg) { println("[DEBUG] Initializing Flat Aim") }; flatAim()
        if (dbg) { println("[DEBUG] Initializing FovChanger") }; fovChanger()
        if (dbg) { println("[DEBUG] Initializing Adrenaline") }; adrenaline()
        if (dbg) { println("[DEBUG] Disabling Post Processing") }; disablePostProcessing()
        if (dbg) { println("[DEBUG] Initializing Weapon Changer") }; skinChanger()
        if (dbg) { println("[DEBUG] Initializing Name Changer") }; nameChanger()
        if (dbg) { println("[DEBUG] Initializing Reduced Flash") }; reducedFlash()
    }
    else {
        if (dbg && inputType == InputDeviceType.INTERCEPTION) { println("[DEBUG] Initializing automatic weapons for safe mode") }
        if (inputType == InputDeviceType.INTERCEPTION) automaticWeaponsSafeMode().start()
        println("Safe mode enabled. Staying safe, king.")
    }
    if (dbg) { println("[DEBUG] Initializing RCS") }; rcs()
    if (dbg) { println("[DEBUG] Initializing Bunny Hop") }; bunnyHop()
    if (dbg) { println("[DEBUG] Initializing Auto Strafe") }; strafeHelper()
    if (dbg) { println("[DEBUG] Initializing Kill Bind") }; killBind()
    if (dbg) { println("[DEBUG] Initializing Path Aim") }; pathAim()
    if (dbg) { println("[DEBUG] Initializing Set Aim") }; setAim()
    if (dbg) { println("[DEBUG] Initializing Bone Trigger") }; triggerBot()
    if (dbg) { println("[DEBUG] Initializing Auto Knife") }; autoKnife()
    if (dbg) { println("[DEBUG] Initializing ESPs") }; esp()
    if (dbg) { println("[DEBUG] Initializing Esp Toggle") }; espToggle()
    if (dbg) { println("[DEBUG] Initializing Fast Stop") }; fastStop()
    if (dbg) { println("[DEBUG] Initializing Head Walk") }; headWalk()
    if (dbg) { println("[DEBUG] Initializing Door Spam") }; doorSpam()
    if (dbg) { println("[DEBUG] Initializing Weapon Spam") }; weaponSpam()
    if (dbg) { println("[DEBUG] Initializing NightMode/FullBright") }; nightMode()
    // if (dbg) { println("[DEBUG] Initializing Bomb Updater")}; bombUpdater()
    if (dbg) { println("[DEBUG] Initializing Handle Fire Key") }; handleFireKey()

    if (dbg) { println("[DEBUG] Initializing Head Level Helper") }; headLevelHelper()
    if (dbg) { println("[DEBUG] Initializing Nade Thrower") }; nadeThrower()
    println("Backtrack disabled")

    //if (EXPERIMENTAL) {
        //rayTraceTest()
        //drawMapWireframe()
    //}
    //Overlay check, not updated?
    if (curSettings["MENU"].strToBool()) {
        println("App Title: " + curSettings["MENU_APP"].replace("\"", ""))
        App.open()

        GlobalScope.launch {
            glfwInit()

            Lwjgl3Application(App, Lwjgl3ApplicationConfiguration().apply {
                setTitle(app_title)

                var w = CSGO.gameWidth
                var h = CSGO.gameHeight

                if ((w == 0 || h == 0) || MENU_APP != DEFAULT_MENU_APP || appless) {
                    w = curSettings["OVERLAY_WIDTH"].toInt()
                    h = curSettings["OVERLAY_HEIGHT"].toInt()
                }

                setWindowedMode(w, h)

                if (curSettings["OPENGL_3"].strToBool()) {
                    useOpenGL3(true, 4, 0)
                    if (dbg) { println("[DEBUG] Using GL3") }
                } else {
                    useOpenGL3(false, 2, 2)
                    if (dbg) { println("[DEBUG] Using GL2") }
                }

                //Required to fix W2S offset
                setWindowPosition(CSGO.gameX, CSGO.gameY)
                setDecorated(appless)
                useVsync(false)
                glfwSwapInterval(0)
                glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_TRUE)
                setBackBufferConfig(8, 8, 8, 8, 16, 0, curSettings["OPENGL_MSAA_SAMPLES"].toInt())
            })
        }
    } else {
        scanner()
    }
}

fun String.toLocale(): String {
    if (dbg && curLocale[this].isBlank()) {
        println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $this is missing!")
    }
    return curLocale[this]
}

fun String.safeToInt(identifier: String = ""): Int {
    return try {
        this.toInt()
    } catch(e: Exception) {
        if (dbg) {
            println("[DEBUG] $identifier is invalid")
        }
        1
    }
}
