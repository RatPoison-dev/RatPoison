@file:JvmName("RatPoison")
@file:Suppress("BlockingMethodInNonBlockingContext")

package rat.poison

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.badlogic.gdx.graphics.GL20.GL_FALSE
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.lwjgl.glfw.GLFW.*
import rat.poison.game.CSGO
import rat.poison.overlay.App
import rat.poison.scripts.*
import rat.poison.scripts.aim.flatAim
import rat.poison.scripts.aim.handleFireKey
import rat.poison.scripts.aim.pathAim
import rat.poison.scripts.aim.setAim
import rat.poison.scripts.bspHandling.rayTraceTest
import rat.poison.scripts.esp.adrenaline
import rat.poison.scripts.esp.esp
import rat.poison.scripts.esp.espToggle
import rat.poison.utils.Settings
import rat.poison.utils.generalUtil.loadLocale
import rat.poison.utils.generalUtil.loadSettingsFromFiles
import rat.poison.utils.generalUtil.strToBool
import java.awt.Robot
import kotlin.collections.set

//Override Weapon
data class oWeapon(var tOverride: Boolean = false,      var tFRecoil: Boolean = false,      var tFlatAim: Boolean = false,
                   var tPathAim: Boolean = false,       var tAimBone: Int = 0,              var tAimFov: Float = 0F,
                   var tAimSpeed: Int = 0,              var tAimSmooth: Double = 0.0,       var tPerfectAim: Boolean = false,
                   var tPAimFov: Int = 1,               var tPAimChance: Int = 1,           var tScopedOnly: Boolean = false,
                   var tBoneTrig: Boolean = false,      var tBTrigBone: Int = 0,            var tBTrigAim: Boolean = false,
                   var tBTrigDelay: Int = 0,            var tAimAfterShots: Int = 0)

//Skinned Weapon
data class sWeapon(var tSkinID: Int, var tStatTrak: Int, var tWear: Float, var tSeed: Int)

const val EXPERIMENTAL = false
const val SETTINGS_DIRECTORY = "settings" //Internal
var saving = false
var settingsLoaded = false

val curSettings = Settings()
val curLocale = Settings()

var dbg: Boolean = false

val robot = Robot().apply { this.autoDelay = 0 }

fun main() {
    System.setProperty("jna.nosys", "true")

    loadSettingsFromFiles(SETTINGS_DIRECTORY)
    //Set CURRENT_LOCALE here later
    loadLocale("$SETTINGS_DIRECTORY\\Localizations\\${curSettings["CURRENT_LOCALE"]}.locale")

    dbg = curSettings["DEBUG"].strToBool()
    if (dbg) println("DEBUG enabled")

    Thread.sleep(5000)
    println("Launching...")

    CSGO.initialize()

    if (dbg) println("[DEBUG] Initializing scripts...")
    //Init scripts
    if (!curSettings["MENU"].strToBool()) { //If we arent' using the menu disable everything that uses the menu
        if (dbg) println("[DEBUG] Menu disabled, disabling box, skeleton, rcrosshair, btimer, indicator, speclist, hitmarker, nade helper, nade tracer, draw fov")

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
    } else {
        if (dbg) { println("[DEBUG] Initializing Recoil Ranks") }; ranks()

        if (dbg) { println("[DEBUG] Initializing Recoil Spectator List") }; spectatorList()
        if (dbg) { println("[DEBUG] Initializing Recoil Bomb Timer") }; bombTimer()

        if (dbg) { println("[DEBUG] Initializing Recoil Crosshair") }; rcrosshair()
        if (dbg) { println("[DEBUG] Initializing Hit Marker") }; hitMarker()
        if (dbg) { println("[DEBUG] Initializing Nade Helper") }; nadeHelper()
        if (dbg) { println("[DEBUG] Initializing Nade Tracer") }; nadeTracer()
        if (dbg) { println("[DEBUG] Initializing Draw Fov") }; drawFov()
    }

    if (dbg) { println("[DEBUG] Initializing Bunny Hop") }; bunnyHop()
    if (dbg) { println("[DEBUG] Initializing Auto Strafe") }; strafeHelper()
    if (dbg) { println("[DEBUG] Initializing RCS") }; rcs()
    if (dbg) { println("[DEBUG] Initializing Flat Aim") }; flatAim()
    if (dbg) { println("[DEBUG] Initializing Path Aim") }; pathAim()
    if (dbg) { println("[DEBUG] Initializing Set Aim") }; setAim()
    if (dbg) { println("[DEBUG] Initializing Bone Trigger") }; boneTrigger()
    if (dbg) { println("[DEBUG] Initializing Auto Knife") }; autoKnife()
    if (dbg) { println("[DEBUG] Initializing Reduced Flash") }; reducedFlash()
    if (dbg) { println("[DEBUG] Initializing ESPs") }; esp()
    if (dbg) { println("[DEBUG] Initializing Esp Toggle") }; espToggle()
    if (dbg) { println("[DEBUG] Initializing Fast Stop") }; fastStop()
    if (dbg) { println("[DEBUG] Initializing Head Walk") }; headWalk()
    if (dbg) { println("[DEBUG] Initializing Adrenaline") }; adrenaline()
    if (dbg) { println("[DEBUG] Initializing FovChanger") }; fovChanger()
    if (dbg) { println("[DEBUG] Initializing Door Spam") }; doorSpam()
    if (dbg) { println("[DEBUG] Initializing Weapon Spam") }; weaponSpam()
    if (dbg) { println("[DEBUG] Initializing Weapon Changer") }; skinChanger()
    if (dbg) { println("[DEBUG] Initializing NightMode/FullBright") }; nightMode()

    if (dbg) { println("[DEBUG] Initializing Backtrack") }; setupBacktrack()
    if (dbg) { println("[DEBUG] Initializing Draw Backtrack") }; drawBacktrack()
    if (dbg) { println("[DEBUG] Initializing Handle Fire Key") }; handleFireKey()

    if (EXPERIMENTAL) {
        rayTraceTest()
    }

    //Overlay check, not updated?
    if (curSettings["MENU"].strToBool()) {
        println("App Title: " + curSettings["MENU_APP"].replace("\"", ""))

        App.open()

        GlobalScope.launch {
            Thread.sleep(2000)

            glfwInit()

            Lwjgl3Application(App, Lwjgl3ApplicationConfiguration().apply {
                setTitle("Rat Poison UI")

                var w = CSGO.gameWidth
                var h = CSGO.gameHeight

                if ((w == 0 || h == 0) || curSettings["MENU_APP"] != "\"Counter-Strike: Global Offensive\"") {
                    w = curSettings["OVERLAY_WIDTH"].toInt()
                    h = curSettings["OVERLAY_HEIGHT"].toInt()
                }

                setWindowedMode(w, h)

                if (curSettings["OPENGL_3"].strToBool()) {
                    useOpenGL3(true, 4, 2)
                    if (dbg) { println("[DEBUG] Using GL3") }
                } else {
                    useOpenGL3(false, 2, 2)
                    if (dbg) { println("[DEBUG] Using GL2") }
                }

                //Required to fix W2S offset
                setWindowPosition(CSGO.gameX, CSGO.gameY)
                setDecorated(false)
                //useVsync(false)
                glfwSwapInterval(0)
                glfwWindowHint(GLFW_DOUBLEBUFFER, GL_FALSE)
                setBackBufferConfig(8, 8, 8, 8, 16, 0, curSettings["OPENGL_MSAA_SAMPLES"].toInt())

                //useVsync(true)
                setIdleFPS(165)
            })
        }
    }
    else {
        scanner() //Scanner is currently outdated
    }
}

fun String.toLocale(): String {
    if (dbg && curLocale[this].isBlank()) {
        println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $this is missing!")
    }
    return curLocale[this]
}