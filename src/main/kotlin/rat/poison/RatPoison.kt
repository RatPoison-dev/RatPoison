@file:JvmName("RatPoison")
@file:Suppress("BlockingMethodInNonBlockingContext")

package rat.poison

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.lwjgl.glfw.GLFW.*
import rat.poison.game.CSGO
import rat.poison.overlay.App
import rat.poison.scripts.*
import rat.poison.utils.common.Settings
import rat.poison.utils.generalUtil.loadSettingsFromFiles
import rat.poison.utils.generalUtil.loadSkinSettings
import rat.poison.utils.loadMigration
import java.awt.Robot
import java.io.File
import java.util.*

//Override Weapon
data class oWeapon(var tOverride: Boolean = false,          var tFRecoil: Boolean = false,          var tOnShot: Boolean = false,
                   var tFlatAim: Boolean = false,           var tPathAim: Boolean = false,          var tAimBone: List<String> = listOf(),
                   var tForceBone: List<String> = listOf(), var tAimFov: Float = 0F,                var tAimSmooth: Int = 0,
                   var tPerfectAim: Boolean = false,        var tPAimFov: Float = 1F,               var tPAimChance: Int = 1,
                   var tScopedOnly: Boolean = false,        var tAimAfterShots: Int = 0,

                   var tBoneTrig: Boolean = false,          var tBTrigAim: Boolean = false,         var tBTrigInCross: Boolean = false,
                   var tBTrigInFov: Boolean = false,        var tBTrigBacktrack: Boolean = false,   var tBTrigFov: Float = 0F,
                   var tBTrigInitDelay: Int = 0,            var tBTrigPerShotDelay: Int = 0,

                   var tBacktrack: Boolean = false,         var tBTMS: Int = 0,
                   var tAutowep: Boolean = false,           var tAutowepDelay: Int = 0)

//Skinned Weapon
data class sWeapon(var tSkinID: Int, var tStatTrak: Int, var tWear: Float, var tSeed: Int)

const val TITLE = "RatPoison"
const val BRANCH = "Trolling"
const val F_VERSION = "0.0"
const val M_VERSION = "0.0"
var LOADED_CONFIG = "DEFAULT"
var oWeaponSize = oWeapon::class.java.declaredFields.size

//const val EXPERIMENTAL = false
const val SETTINGS_DIRECTORY = "settings" //Internal

lateinit var WEAPON_STATS_FILE: File
lateinit var SKIN_INFO_FILE: File
lateinit var MUSIC_KITS_FILE: File

var settingsLoaded = false
val curSettings = Settings()
val skSettings = Settings()
var crosshairArray = BitSet(81) //81 is max / 0 //CROSSHAIR_ARRAY

var dbg: Boolean = false
var appless: Boolean = false
val robot = Robot().apply { this.autoDelay = 0 }

val DEFAULT_OWEAPON = oWeapon()
val DEFAULT_OWEAPON_STR = DEFAULT_OWEAPON.toString()

var haltProcess = false

fun main() {
    System.setProperty("jna.nosys", "true")

    loadSettingsFromFiles(SETTINGS_DIRECTORY)
    loadSkinSettings("$SETTINGS_DIRECTORY/skinCFGS/DefaultSettings.cfg")

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
    if (!curSettings.bool["MENU"]) { //If we aren't using the menu disable everything that uses the menu
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
    }

    //if (EXPERIMENTAL) {
        //rayTraceTest()
        //drawMapWireframe()
    //}
    //Overlay check, not updated?
    if (curSettings.bool["MENU"]) {
        println("Game found. Launching.")

        App.open()

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
                glfwSwapInterval(0)
                glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_TRUE)
                setBackBufferConfig(8, 8, 8, 8, 16, 0, curSettings.int["OPENGL_MSAA_SAMPLES"])
            })
        }
    }
}