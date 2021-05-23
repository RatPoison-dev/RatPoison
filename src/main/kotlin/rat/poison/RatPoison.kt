@file:JvmName("RatPoison")
@file:Suppress("BlockingMethodInNonBlockingContext")

package rat.poison

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rat.poison.game.CSGO
import rat.poison.game.offsets.EngineOffsets.dwbSendPackets
import rat.poison.overlay.App
import rat.poison.scripts.*
import rat.poison.scripts.aim.flatAim
import rat.poison.scripts.aim.pathAim
import rat.poison.scripts.aim.setAim
import rat.poison.scripts.misc.updateGVars
import rat.poison.scripts.ui.handleUIDebug
import rat.poison.scripts.ui.handleUIWatermark
import rat.poison.scripts.userCmd.handleUCMD
import rat.poison.scripts.visuals.*
import rat.poison.utils.common.Settings
import rat.poison.utils.generalUtil.loadSettingsFromFiles
import rat.poison.utils.generalUtil.loadSkinSettings
import rat.poison.utils.loadLocale
import rat.poison.utils.loadMigration
import rat.poison.utils.updateFonts
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
                   var tAutowep: Boolean = false,           var tAutowepDelay: Int = 0) {
    override fun toString(): String {
        return "oWeapon(tOverride=$tOverride, tFRecoil=$tFRecoil, tOnShot=$tOnShot, tFlatAim=$tFlatAim, tPathAim=$tPathAim, tAimBone=${tAimBone.joinToString(prefix = "[", separator = ";", postfix = "]")}, tForceBone=${tForceBone.joinToString(prefix = "[", separator = ";", postfix = "]")}, tAimFov=$tAimFov, tAimSmooth=$tAimSmooth, tPerfectAim=$tPerfectAim, tPAimFov=$tPAimFov, tPAimChance=$tPAimChance, tScopedOnly=$tScopedOnly, tAimAfterShots=$tAimAfterShots, tBoneTrig=$tBoneTrig, tBTrigAim=$tBTrigAim, tBTrigInCross=$tBTrigInCross, tBTrigInFov=$tBTrigInCross, tBTrigBacktrack=$tBTrigBacktrack, tBTrigFov=$tBTrigFov, tBTrigInitDelay=$tBTrigInitDelay, tBTrigPerShotDelay=$tBTrigPerShotDelay, tBacktrack=$tBacktrack, tBTMS=$tBTMS, tAutowep=$tAutowep, tAutowepDelay=$tAutowepDelay)"
    }
}

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
val curLocale = Settings()
var crosshairArray = BitSet(81) //81 is max / 0 //RCROSSHAIR_BUILDER_ARRAY

val DEFAULT_OWEAPON = oWeapon()
val DEFAULT_OWEAPON_STR = DEFAULT_OWEAPON.toString()

var dbg: Boolean = false
var appless: Boolean = false
val robot = Robot().apply { this.autoDelay = 0 }

var haltProcess = false

fun dbgLog(str: String) {
    if (dbg) {
        println("[DEBUG] $str")
    }
}

fun main() {
    System.setProperty("jna.nosys", "true")

    loadLocale()

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

    dbgLog("Initializing scripts...")
    
    //Init scripts
    if (!curSettings.bool["MENU"]) { //If we aren't using the menu disable everything that uses the menu
        if (dbg) println("[DEBUG] Menu disabled, disabling box, skeleton, rcrosshair, btimer, indicator, speclist, hitmarker, nade helper, nade tracer, draw fov, spread circle, visualize smokes")

        curSettings["ENABLE_BOX_ESP"] = "false"
        curSettings["SKELETON_ESP"] = "false"
        curSettings["ENABLE_RCROSSHAIR"] = "false"
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
        dbgLog("Initializing Recoil Ranks"); ranks()

        dbgLog("Initializing Recoil Spectator List"); spectatorList()
        dbgLog("Initializing Recoil Bomb Timer"); bombTimer()

        dbgLog("Initializing Recoil Crosshair"); rCrosshair()
        dbgLog("Initializing Hit Marker"); hitMarker()
        dbgLog("Initializing Nade Helper"); nadeHelper()
        dbgLog("Initializing Nade Tracer"); nadeTracer()
        dbgLog("Initializing Draw Fov"); drawFov()
        dbgLog("Initializing Spread Circle"); spreadCircle()
        dbgLog("Initializing Draw Smokes"); drawSmokes()
    }
        
    dbgLog("Initializing Far Radar"); farRadar()
    dbgLog("Initializing Handle UI Watermark"); handleUIWatermark()
    dbgLog("Initializing Handle UI Debug"); handleUIDebug()
    dbgLog("Initializing Bunny Hop"); bunnyHop()
    dbgLog("Initializing Auto Strafe"); strafeHelper()
    dbgLog("Initializing Kill Bind"); killBind()
    dbgLog("Initializing RCS"); rcs()
    dbgLog("Initializing Flat Aim"); flatAim()

    dbgLog("Initializing Path Aim"); pathAim()

    dbgLog("Initializing Set Aim"); setAim()

    dbgLog("Initializing Bone Trigger"); triggerBot()
    dbgLog("Initializing Auto Knife"); autoKnife()
    dbgLog("Initializing Reduced Flash"); reducedFlash()
    dbgLog("Initializing ESPs"); esp()
    dbgLog("Initializing Fast Stop"); fastStop()
    dbgLog("Initializing Head Walk"); headWalk()
    dbgLog("Initializing Adrenaline"); adrenaline()
    dbgLog("Initializing FovChanger"); fovChanger()
    dbgLog("Disabling Post Processing"); disablePostProcessing()
    dbgLog("Initializing Weapon Changer"); skinChanger()
    dbgLog("Initializing NightMode/FullBright"); nightMode()
    dbgLog("Initializing Bomb Updater"); bombUpdater()
    dbgLog("Initializing Backtrack"); setupBacktrack()
    dbgLog("Initializing Draw Backtrack"); drawBacktrack()
    dbgLog("Initializing GVars Updater"); updateGVars()

    dbgLog("Initializing Nades Timer"); nadesTimer()
    dbgLog("Initializing Head Level Helper"); headLevelHelper()
    dbgLog("Initializing Fake Lag"); fakeLag()
    dbgLog("Initializing Nade Thrower"); nadeThrower()
    dbgLog("Initializing Kill Sound"); killSoundEsp()
    dbgLog("Initializing MusicKit Spoofer"); musicKitSpoofer()
    dbgLog("Initializing Block Bot"); blockBot()
    dbgLog("dwbSendPackets: $dwbSendPackets")

    handleUCMD()

        //if (EXPERIMENTAL) {
        //rayTraceTest()
        //drawMapWireframe()l
        //}
        //Overlay check, not updated?

    if (curSettings.bool["MENU"]) {
        println("Game found. Launching.")

        initApp()
    }

    scanner()
}


fun initApp() {
    haltProcess = false
    updateFonts = true

    GlobalScope.launch {
        App.open()

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
                if (dbg) {
                    println("[DEBUG] Using GL3")
                }
            } else {
                useOpenGL3(false, 2, 0)
                if (dbg) {
                    println("[DEBUG] Using GL2")
                }
            }

            //Required to fix W2S offset
            if (!appless) setWindowPosition(CSGO.gameX, CSGO.gameY) else setWindowPosition(curSettings.int["APPLESS_X"], curSettings.int["APPLESS_Y"])
            setResizable(false)
            setDecorated(appless)
            useVsync(false)

            //glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_TRUE)
            setBackBufferConfig(8, 8, 8, 8, 16, 0, curSettings.int["OPENGL_MSAA_SAMPLES"])
        })
    }
}
