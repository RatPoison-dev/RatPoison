@file:JvmName("RatPoison")
@file:Suppress("BlockingMethodInNonBlockingContext")

package rat.poison

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.lwjgl.glfw.GLFW.*
import rat.poison.game.CSGO
import rat.poison.game.offsets.EngineOffsets.dwbSendPackets
import rat.poison.overlay.App
import rat.poison.scripts.*
import rat.poison.scripts.aim.flatAim
import rat.poison.scripts.aim.handleFireKey
import rat.poison.scripts.aim.pathAim
import rat.poison.scripts.aim.setAim
import rat.poison.scripts.visuals.*
import rat.poison.utils.*
import rat.poison.utils.generalUtil.loadSettingsFromFiles
import java.awt.Robot
import java.io.File
import kotlin.concurrent.thread

//Override Weapon
data class oWeapon(var tOverride: Boolean = false,      var tFRecoil: Boolean = false,          var tOnShot: Boolean = false,
                   var tFlatAim: Boolean = false,       var tPathAim: Boolean = false,          var tAimBone: Int = 0,
                   var tForceBone: Int = 0,             var tAimFov: Float = 0F,                var tAimSpeed: Int = 0,
                   var tAimSmooth: Float = 0F,        var tPerfectAim: Boolean = false,       var tPAimFov: Float = 1F,
                   var tPAimChance: Int = 1,            var tScopedOnly: Boolean = false,       var tAimAfterShots: Int = 0,

                   var tBoneTrig: Boolean = false,     var tBTrigAim: Boolean = false,          var tBTrigInCross: Boolean = false,
                   var tBTrigInFov: Boolean = false,   var tBTrigBacktrack: Boolean = false,    var tBTrigFov: Float = 0F,
                   var tBTrigInitDelay: Int = 0,       var tBTrigPerShotDelay: Int = 0,

                   var tBacktrack: Boolean = false,    var tBTMS: Int = 0,
                   var tAutowep: Boolean = false,      var tAutowepDelay: Int = 0)

//Skinned Weapon
data class sWeapon(var tSkinID: Int, var tStatTrak: Int, var tWear: Float, var tSeed: Int)

const val TITLE = "RatPoison"
const val BRANCH = "Beta"
const val F_VERSION = "1.8"
const val M_VERSION = "1.8.5"
var LOADED_CONFIG = "DEFAULT"
var oWeaponSize = oWeapon::class.java.declaredFields.size

//const val EXPERIMENTAL = false
const val SETTINGS_DIRECTORY = "settings" //Internal

lateinit var WEAPON_STATS_FILE: File
lateinit var SKIN_INFO_FILE: File
lateinit var MUSIC_KITS_FILE: File

var settingsLoaded = false
object curSettings : Settings()
object curLocale : Settings()

var dbg: Boolean = false
var appless: Boolean = false
val robot = Robot().apply { this.autoDelay = 0 }

@Volatile
var haltProcess = false

fun main() {
    System.setProperty("jna.nosys", "true")
    
    //trackVectors() // turn on to debug Vector memory leaks

    loadSettingsFromFiles(SETTINGS_DIRECTORY)

    detectLocale()

    dbg = curSettings.bool["DEBUG"]
    appless = curSettings.bool["APPLESS"]
    if (dbg) println("DEBUG enabled")

    println("Waiting for ${curSettings["MENU_APP"]} process...")

    CSGO.initialize()

    WEAPON_STATS_FILE = File("$SETTINGS_DIRECTORY\\Data\\WeaponStats.txt")
    SKIN_INFO_FILE = File("$SETTINGS_DIRECTORY\\Data\\SkinInfo.txt")
    MUSIC_KITS_FILE = File("$SETTINGS_DIRECTORY\\Data\\MusicKits.txt")

    loadMigration()

    if (dbg) println("[DEBUG] Initializing scripts...")
    //Init scripts
    if (dbg) { println("[DEBUG] Initializing Ranks") }; ranks()
    if (dbg) { println("[DEBUG] Initializing Spectator List") }; spectatorList()
    if (dbg) { println("[DEBUG] Initializing Bomb Timer") }; bombTimer()
    if (dbg) { println("[DEBUG] Initializing Recoil Crosshair") }; rcrosshair()
    if (dbg) { println("[DEBUG] Initializing Hit Marker") }; hitMarker()
    if (dbg) { println("[DEBUG] Initializing Nade Helper") }; nadeHelper()
    if (dbg) { println("[DEBUG] Initializing Nade Tracer") }; nadeTracer()
    if (dbg) { println("[DEBUG] Initializing Draw Fov") }; drawFov()
    if (dbg) { println("[DEBUG] Initializing Spread Circle") }; spreadCircle()
    if (dbg) { println("[DEBUG] Initializing Draw Smokes") }; drawSmokes()
    if (dbg) { println("[DEBUG] Initializing Far Radar") }; farRadar()
    //farEsp()

    if (dbg) { println("[DEBUG] Initializing Draw Debug") }; drawDebug()

    if (dbg) { println("[DEBUG] Initializing Bunny Hop") }; bunnyHop()
    if (dbg) { println("[DEBUG] Initializing Auto Strafe") }; strafeHelper()
    if (dbg) { println("[DEBUG] Initializing Kill Bind") }; killBind()
    if (dbg) { println("[DEBUG] Initializing RCS") }; rcs()
    if (dbg) { println("[DEBUG] Initializing Flat Aim") }; flatAim()
    if (dbg) { println("[DEBUG] Initializing Path Aim") }; pathAim()
    if (dbg) { println("[DEBUG] Initializing Set Aim") }; setAim()
    if (dbg) { println("[DEBUG] Initializing Bone Trigger") }; triggerBot()
    if (dbg) { println("[DEBUG] Initializing Auto Knife") }; autoKnife()
    if (dbg) { println("[DEBUG] Initializing Reduced Flash") }; reducedFlash()
    if (dbg) { println("[DEBUG] Initializing ESPs") }; esp()
    if (dbg) { println("[DEBUG] Initializing Fast Stop") }; fastStop()
    if (dbg) { println("[DEBUG] Initializing Head Walk") }; headWalk()
    if (dbg) { println("[DEBUG] Initializing Adrenaline") }; adrenaline()
    if (dbg) { println("[DEBUG] Initializing FovChanger") }; fovChanger()
    if (dbg) { println("[DEBUG] Disabling Post Processing") }; disablePostProcessing()
    if (dbg) { println("[DEBUG] Initializing Door Spam") }; doorSpam()
    if (dbg) { println("[DEBUG] Initializing Weapon Spam") }; weaponSpam()
    if (dbg) { println("[DEBUG] Initializing Weapon Changer") }; skinChanger()
    if (dbg) { println("[DEBUG] Initializing NightMode/FullBright") }; nightMode()
    if (dbg) { println("[DEBUG] Initializing Bomb Updater")}; bombUpdater()

    if (dbg) { println("[DEBUG] Initializing Backtrack") }; setupBacktrack()
    if (dbg) { println("[DEBUG] Initializing Draw Backtrack") }; drawBacktrack()
    if (dbg) { println("[DEBUG] Initializing Handle Fire Key") }; handleFireKey()
    if (dbg) { println("[DEBUG] Initializing GVars updater") }; updateGVars()
    if (dbg) { println("[DEBUG] Initializing Nades Timer") }; nadesTimer()

    if (dbg) { println("[DEBUG] Initializing Head Level Helper") }; headLevelHelper()
    if (dbg) { println("[DEBUG] Initializing Nade Thrower") }; nadeThrower()
    if (dbg) { println("[DEBUG] Initializing Name Changer") }; nameChanger()
    if (dbg) { println("[DEBUG] Initializing Kill Sound") }; killSoundEsp()
    if (dbg) { println("[DEBUG] Initializing MusicKit Spoofer") }; musicKitSpoofer()
    if (dbg) { println("[DEBUG] dwbSendPackets: $dwbSendPackets")}

    //if (EXPERIMENTAL) {
        //rayTraceTest()
        //drawMapWireframe()
    //}
    //Overlay check, not updated?
    println("Game found. Launching.")

    App.open()

    thread { scanner() }

    GlobalScope.launch {
        glfwInit()

        Lwjgl3Application(App, Lwjgl3ApplicationConfiguration().apply {
            setTitle("Rat Poison UI")

            var w = CSGO.gameWidth
            var h = CSGO.gameHeight

            if ((w == 0 || h == 0) || curSettings["MENU_APP"] != "\"Counter-Strike: Global Offensive\"") {
                w = curSettings.int["OVERLAY_WIDTH"]
                h = curSettings.int["OVERLAY_HEIGHT"]
            }

            if (appless) {
                w = curSettings.int["APPLESS_WIDTH"]
                h = curSettings.int["APPLESS_HEIGHT"]
            }

            setWindowedMode(w, h)

            if (curSettings.bool["OPENGL_3"]) {
                useOpenGL3(true, 4, 0)
                if (dbg) { println("[DEBUG] Using GL3") }
            } else {
                useOpenGL3(false, 2, 0)
                if (dbg) { println("[DEBUG] Using GL2") }
            }

            //Required to fix W2S offset
            if (!appless) setWindowPosition(CSGO.gameX, CSGO.gameY) else setWindowPosition(curSettings.int["APPLESS_X"], curSettings.int["APPLESS_Y"])
            setResizable(false)
            setDecorated(appless)
            useVsync(false)
            //setWindowIcon("$SETTINGS_DIRECTORY/Assets/Images/icon.png") // replace with ratpoison logo
            glfwSwapInterval(0)
            glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_TRUE)
            setBackBufferConfig(8, 8, 8, 8, 16, 0, curSettings.int["OPENGL_MSAA_SAMPLES"])
        })
    }
	
	LowPriority.start()
    HighPriority.start()
}

fun String.toLocale(): String {
    val locale = curLocale[this]
    if (locale.isEmpty()) {
        if (dbg) println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $this is missing!")
        return this
    }
    return locale
}