@file:JvmName("RatPoison")
@file:Suppress("BlockingMethodInNonBlockingContext")

package rat.poison

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Gdx.gl
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.GL20.GL_FALSE
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.scenes.scene2d.Stage
import com.kotcrab.vis.ui.VisUI
import com.sun.management.OperatingSystemMXBean
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jire.arrowhead.keyPressed
import org.lwjgl.glfw.GLFW.*
import rat.poison.game.CSGO
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.updateViewMatrix
import rat.poison.interfaces.IOverlay
import rat.poison.interfaces.IOverlayListener
import rat.poison.jna.enums.AccentStates
import rat.poison.overlay.Overlay
import rat.poison.scripts.*
import rat.poison.scripts.aim.flatAim
import rat.poison.scripts.aim.pathAim
import rat.poison.scripts.aim.setAim
import rat.poison.scripts.bspHandling.rayTraceTest
import rat.poison.scripts.esp.adrenaline
import rat.poison.scripts.esp.drawBacktrack
import rat.poison.scripts.esp.esp
import rat.poison.scripts.esp.espToggle
import rat.poison.settings.MENUTOG
import rat.poison.ui.tabs.saveDefault
import rat.poison.ui.uiPanels.*
import rat.poison.ui.uiUpdate
import rat.poison.utils.ObservableBoolean
import rat.poison.utils.Settings
import rat.poison.utils.addListeners
import rat.poison.utils.constructVars
import rat.poison.utils.extensions.appendHumanReadableSize
import rat.poison.utils.extensions.roundNDecimals
import rat.poison.utils.varUtil.strToBool
import java.awt.Robot
import java.io.File
import java.io.FileReader
import java.lang.management.ManagementFactory
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.set
import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureNanoTime

//Override Weapon
data class oWeapon(var tOverride: Boolean = false,      var tFRecoil: Boolean = false,      var tFlatAim: Boolean = false,
                   var tPathAim: Boolean = false,       var tAimBone: Int = 0,              var tAimFov: Int = 0,
                   var tAimSpeed: Int = 0,              var tAimSmooth: Double = 0.0,       var tPerfectAim: Boolean = false,
                   var tPAimFov: Int = 1,               var tPAimChance: Int = 1,           var tScopedOnly: Boolean = false,
                   var tBoneTrig: Boolean = false,      var tBTrigBone: Int = 0,            var tBTrigAim: Boolean = false,
                   var tBTrigDelay: Int = 0,            var tAimAfterShots: Int = 0)

//Skinned Weapon
data class sWeapon(var tSkinID: Int, var tStatTrak: Int, var tWear: Float, var tSeed: Int)

const val EXPERIMENTAL = true
const val SETTINGS_DIRECTORY = "settings" //Internal
var saving = false
var settingsLoaded = false

val curSettings = Settings()
val curLocalization = Settings()

var dbg: Boolean = false

val robot = Robot().apply { this.autoDelay = 0 }

// Junk code
fun visualsMap (): Settings {
    val map = Settings()
    map[curLocalization["LEFT"]] = "LEFT"
    map[curLocalization["RIGHT"]] = "RIGHT"
    map[curLocalization["TOP"]] = "TOP"
    map[curLocalization["BOTTOM"]] = "BOTTOM"
    map[curLocalization["NORMAL"]] = "Normal"
    map[curLocalization["MODEL"]] = "Model"
    map[curLocalization["VISIBLE"]] = "Visible"
    map[curLocalization["VISIBLE_FLICKER"]] = "Visible Flicker"
    return map
}
fun aimingMap () : Settings {
    val map = Settings()
    // FOV Types
    map[curLocalization["STATIC"]] = "STATIC"
    map[curLocalization["DISTANCE"]] = "DISTANCE"
    // Bones
    map[curLocalization["HEAD"]] = "HEAD"
    map[curLocalization["NECK"]] = "NECK"
    map[curLocalization["CHEST"]] = "CHEST"
    map[curLocalization["STOMACH"]] = "STOMACH"
    map[curLocalization["PELVIS"]] = "PELVIS"
    map[curLocalization["NEAREST"]] = "NEAREST"
    map[curLocalization["RANDOM"]] = "RANDOM"
    // Weapon categories
    map[curLocalization["PISTOL"]] = "PISTOL"
    map[curLocalization["SHOTGUN"]] = "SHOTGUN"
    map[curLocalization["RIFLE"]] = "RIFLE"
    map[curLocalization["SNIPER"]] = "SNIPER"
    map[curLocalization["SMG"]] = "SMG"
    return map
}
fun main() {
    System.setProperty("jna.nosys", "true")

    println("Loading settings...")
    loadSettingsFromFiles(SETTINGS_DIRECTORY)
    val locale = getSystemLocale()
    if (curSettings["DEFAULT_LOCALE"].replace("\"", "").isBlank()) {
        if (locale != null && Files.exists(Paths.get("settings/Localizations/locale_${locale}.locale"))) {
            loadLocalizationFromFile("locale_${locale}")
        }
        else {
            println("Detected default locale: $locale")
            println("Locale file for your language doesn't exist. Loading english locale.")
            curSettings["DEFAULT_LOCALE"] = "locale_en_US"
            loadLocalizationFromFile(curSettings["DEFAULT_LOCALE"])
            saveDefault()
        }
    }
    else {
        loadLocalizationFromFile(curSettings["DEFAULT_LOCALE"])
    }
    dbg = curSettings["DEBUG"].strToBool()

    if (dbg) println("DEBUG enabled")
    constructVars()
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
        if (dbg) { println("[DEBUG] Initializing Ranks") }; ranks()

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
    //if (dbg) { println("[DEBUG] Initializing Automatic Weapons") }; automaticWeapon()
    if (dbg) { println("[DEBUG] Initializing Fast Stop") }; fastStop()
    if (dbg) { println("[DEBUG] Initializing Head Walk (Currently disabled)") }; headWalk()
    if (dbg) { println("[DEBUG] Initializing Adrenaline") }; adrenaline()
    if (dbg) { println("[DEBUG] Initializing FovChanger") }; fovChanger()
    if (dbg) { println("[DEBUG] Initializing Door Spam") }; doorSpam()
    if (dbg) { println("[DEBUG] Initializing Weapon Spam") }; weaponSpam()
    if (dbg) { println("[DEBUG] Initializing Weapon Changer") }; skinChanger()
    if (dbg) { println("[DEBUG] Initializing NightMode/FullBright") }; nightMode()
    if (dbg) { println("[DEBUG] Initializing Nade Thrower") }; nadeThrower()

    setupBacktrack()
    drawBacktrack()

    if (EXPERIMENTAL) {
        rayTraceTest() //Dont bother rn
    }
    addListeners()
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
                useVsync(false)
                glfwSwapInterval(0)
                glfwWindowHint(GLFW_DOUBLEBUFFER, GL_FALSE)
                setBackBufferConfig(8, 8, 8, 8, 16, 0, curSettings["OPENGL_MSAA_SAMPLES"].toInt())
            })
        }
    }
    else {
        scanner() //Scanner is currently outdated
    }
}

fun loadLocalizationFromFile(localizationName: String) {
    val filePath = "$SETTINGS_DIRECTORY\\Localizations\\$localizationName.locale"
    File(filePath).readLines(Charsets.UTF_8).forEach { line ->
        val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE
        if (curLine.size == 3) {
            curLocalization[curLine[0]] = curLine[2]
        }
    }
    curSettings["DEFAULT_LOCALE"] = localizationName
}

fun loadSettingsFromFiles(fileDir : String, specificFile : Boolean = false) {
    settingsLoaded = false
    if (specificFile) {
        FileReader(File(fileDir)).readLines().forEach { line ->
            if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith("\"") && !line.startsWith(" *") && !line.startsWith("*") && line.trim().isNotEmpty()) {
                val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE
                if (curLine.size == 3) {
                    curSettings[curLine[0]] = curLine[2]
                } else {
                    println("Debug: Setting invalid -- $curLine")
                }
            }
        }
    } else {
        File(fileDir).listFiles()?.forEach { file ->
            if (file.name != "CFGS" && file.name != "Localizations" && file.name != "hitsounds" && file.name != "NadeHelper" && file.name != "SkinInfo" && file.name.contains(".txt")) {
                FileReader(file).readLines().forEach { line ->
                    if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith(" *") && !line.startsWith("*") && line.trim().isNotEmpty()) {
                        val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

                        if (curLine.size == 3) {
                            curSettings[curLine[0]] = curLine[2]
                        } else {
                            println("Debug: Setting invalid -- $curLine")
                        }
                    }
                }
            }
        }
    }
    settingsLoaded = true
    println("Settings loaded")
}

var opened = false
var overlayMenuKey = ObservableBoolean({keyPressed(1)})

var syncTime = 0L
var glowTime = 0L
var appTime = 0L
var menuTime = 0L
var overlayTime = 0L
var bspVisTime = 0L

object App : ApplicationAdapter() {
    lateinit var sb: SpriteBatch
    lateinit var textRenderer: BitmapFont
    lateinit var shapeRenderer: ShapeRenderer
    private val overlay = Overlay(curSettings["MENU_APP"].replace("\"", ""), "Rat Poison UI", AccentStates.ACCENT_ENABLE_BLURBEHIND)
    lateinit var menuStage: Stage
    private val bodies = ObjectArrayList<App.() -> Unit>()
    private lateinit var camera: OrthographicCamera

    lateinit var uiMenu: UIMenu
    lateinit var uiBombWindow: UIBombTimer
    lateinit var uiSpecList: UISpectatorList
    lateinit var uiAimOverridenWeapons: UIAimOverridenWeapons
    lateinit var uiKeybinds: UIKeybinds
    private val sbText = StringBuilder()

    private val osBean = ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean
    var haveTarget = false
    private var timer = 0

    override fun create() {
        overlayMenuKey = ObservableBoolean({ keyPressed(curSettings["MENU_KEY"].toInt()) })
        VisUI.load(Gdx.files.internal("skin\\tinted.json"))
        //Implement stage for menu
        menuStage = Stage() //Main Menu Stage

        shapeRenderer = ShapeRenderer().apply { setAutoShapeType(true) }

        uiMenu = UIMenu()
        uiBombWindow = UIBombTimer()
        uiSpecList = UISpectatorList()
        uiAimOverridenWeapons = UIAimOverridenWeapons()
        uiKeybinds = UIKeybinds()
        menuStage.addActor(uiMenu)

        Gdx.input.inputProcessor = InputMultiplexer().apply {
            addProcessor(menuStage)
        }

        sb = SpriteBatch()
        textRenderer = BitmapFont(false)
        camera = OrthographicCamera()
        gl.glClearColor(0F, 0F, 0F, 0F)

        overlay.start()
    }

    override fun render() {
        timer++

        syncTime = TimeUnit.NANOSECONDS.convert(measureNanoTime {
            sync(curSettings["OPENGL_FPS"].toInt())
        }, TimeUnit.NANOSECONDS)

        if (VisUI.isLoaded()) {
            if (!Thread.interrupted()) {
                gl.apply {
                    glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

                    if (!menuStage.root.isVisible) return

                    overlayTime = TimeUnit.NANOSECONDS.convert(measureNanoTime {
                        menuTime = TimeUnit.NANOSECONDS.convert(measureNanoTime {
                            if (MENUTOG) {
                                if (curSettings["KEYBINDS"].strToBool()) {
                                    if (!menuStage.actors.contains(uiKeybinds)) {
                                        menuStage.addActor(uiKeybinds)
                                        uiKeybinds.updateBindsList()
                                    }
                                } else if (menuStage.actors.contains(uiKeybinds)) {
                                    menuStage.clear()
                                }

                                if (curSettings["ENABLE_OVERRIDE"].strToBool()) {
                                    if (!menuStage.actors.contains(uiAimOverridenWeapons)) {
                                        menuStage.addActor(uiAimOverridenWeapons)
                                    }
                                } else if (menuStage.actors.contains(uiAimOverridenWeapons)) {
                                    menuStage.clear() //actors.remove at index doesnt work after 1 loop?
                                }

                                if (!menuStage.actors.contains(uiMenu)) {
                                    menuStage.addActor(uiMenu)
                                }
                            } else if (menuStage.actors.contains(uiMenu) || menuStage.actors.contains(uiAimOverridenWeapons) || menuStage.actors.contains(uiKeybinds)) {
                                menuStage.clear()
                            }

                            if (curSettings["ENABLE_BOMB_TIMER"].strToBool() && checkFlags("ENABLE_BOMB_TIMER") && curSettings["BOMB_TIMER_MENU"].strToBool() && checkFlags("BOMB_TIMER_MENU") && curSettings["ENABLE_ESP"].strToBool() && checkFlags("ENABLE_ESP")) {
                                if (!menuStage.actors.contains(uiBombWindow)) {
                                    menuStage.addActor(uiBombWindow)
                                }
                            } else if (menuStage.actors.contains(uiBombWindow)) {
                                menuStage.clear() //actors.remove at index doesnt work after 1 loop?
                            }

                            if (curSettings["SPECTATOR_LIST"].strToBool() && checkFlags("SPECTATOR_LIST") && curSettings["ENABLE_ESP"].strToBool() && checkFlags("ENABLE_ESP")) {
                                if (!menuStage.actors.contains(uiSpecList)) {
                                    menuStage.addActor(uiSpecList)
                                }
                            } else if (menuStage.actors.contains(uiSpecList)) {
                                menuStage.clear() //actors.remove at index doesnt work after 1 loop?
                            }

                            menuStage.act(Gdx.graphics.deltaTime)
                            menuStage.draw()
                        }, TimeUnit.NANOSECONDS)

                        glEnable(GL20.GL_BLEND)
                        glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
                        glClearColor(0F, 0F, 0F, 0F)
                        sb.projectionMatrix = menuStage.camera.combined
                        shapeRenderer.projectionMatrix = menuStage.camera.combined
                        updateViewMatrix()
                        appTime = TimeUnit.NANOSECONDS.convert(measureNanoTime {
                            for (i in 0 until bodies.size) {
                                bodies[i]()
                            }
                        }, TimeUnit.NANOSECONDS)
                        glDisable(GL20.GL_BLEND)
                        glFinish()
                    }, TimeUnit.NANOSECONDS)

                    if (curSettings["DEBUG"].strToBool()) { //Draw Debug
                        //Limit updates
                        if (timer >= curSettings["OPENGL_FPS"].toInt()/4) {
                            val runtime = Runtime.getRuntime()

                            val totalMem = runtime.totalMemory()
                            val freeMem = runtime.freeMemory()
                            val usedMem = totalMem - freeMem

                            val totalPhysMem = osBean.totalPhysicalMemorySize
                            val freePhysMem = osBean.freePhysicalMemorySize

                            val processLoad = osBean.processCpuLoad
                            val systemLoad = osBean.systemCpuLoad

                            sbText.clear()
                            sbText.append("Total physical mem: ").appendHumanReadableSize(totalPhysMem)
                            sbText.append("\nFree physical mem: ").appendHumanReadableSize(freePhysMem)

                            sbText.append("\nTotal allocated mem: ").appendHumanReadableSize(totalMem)
                            sbText.append("\nFree allocated mem: ").appendHumanReadableSize(freeMem)
                            sbText.append("\nUsed allocated mem: ").appendHumanReadableSize(usedMem)

                            sbText.append("\nProcess load: ").append((processLoad.toFloat() * 100F).roundNDecimals(2)).append("%")
                            sbText.append("\nSystem load: ").append((systemLoad.toFloat() * 100F).roundNDecimals(2)).append("%")

                            sbText.append("\n\nSync took: ").append((syncTime.toFloat() * 0.000001F).roundNDecimals(4)).append(" ms")
                            sbText.append("\nOverlay took: ").append((overlayTime.toFloat() * 0.000001F).roundNDecimals(4)).append(" ms")
                            sbText.append("\n   Menu took: ").append((menuTime.toFloat() * 0.000001F).roundNDecimals(4)).append(" ms")
                            sbText.append("\n   Apps took: ").append((appTime.toFloat() * 0.000001F).roundNDecimals(4)).append(" ms")
                            sbText.append("\n      Glow took: ").append((glowTime.toFloat() * 0.000001F).roundNDecimals(4)).append(" ms")
                            sbText.append("\n      Bsp Vis Check took: ").append((bspVisTime.toFloat() * 0.000001F).roundNDecimals(4)).append(" ms")
                            timer = 0
                        }


                        textRenderer.apply {
                            sb.begin()

                            draw(sb, sbText, CSGO.gameWidth/3F, CSGO.gameHeight-100F)

                            sb.end()
                        }
                    }
                }

                //Menu Key
                overlayMenuKey.update()
                if (overlayMenuKey.justBecameTrue) {
                    MENUTOG = !MENUTOG
                    overlay.clickThrough = !MENUTOG

                    uiMenu.updateChilds()
                    uiUpdate()
                    curSettings["BINDS"] = "false"
                    if (dbg) println("[DEBUG] Menu Toggled")
                }
                addListeners()

                val w = overlay.width
                val h = overlay.height

                if (menuStage.viewport.screenWidth != w || menuStage.viewport.screenHeight != h) {
                    resize(w, h)
                    menuStage.viewport.update(w, h)
                    if (dbg) println("[DEBUG] Resized Viewports")
                }
            }
        }

        opened = true
    }

    override fun dispose() {
        overlay.stop()
    }

    operator fun invoke(body: App.() -> Unit) {
        bodies.add(body)
    }

    fun open() {
        overlay.listener = object : IOverlayListener {
            override fun onTargetAppWindowClosed(overlay: IOverlay) {
                haveTarget = false
            }

            override fun onAfterInit(overlay: IOverlay) {
                overlay.clickThrough = true
                overlay.protectAgainstScreenshots = false
                haveTarget = true
            }

            override fun onActive(overlay: IOverlay) {MENUTOG = true}
            override fun onPassive(overlay: IOverlay) {MENUTOG = false}
            override fun onBackground(overlay: IOverlay) {}
            override fun onForeground(overlay: IOverlay) {}
            override fun onBoundsChange(overlay: IOverlay, x: Int, y: Int, width: Int, height: Int) {}
        }
    }
}

var variableYieldTime = 0.toLong()
var lastSyncTime = 0.toLong()

fun sync(fps : Int) {
    if (fps <= 0) {
        return
    }

    val nanoSec = 1000000000L

    var overSleep = 0.toLong()
    val sleepTime = nanoSec/fps
    val yieldTime = min(sleepTime, variableYieldTime + sleepTime % (1000000L))

    try {
        while(true) {
            val t = System.nanoTime() - lastSyncTime

            if (t < sleepTime - yieldTime) {
                Thread.sleep(1)
            } else if (t < sleepTime) {
                Thread.yield()
            } else {
                overSleep = t - sleepTime
                break
            }
        }
    } catch (ex: InterruptedException) { println("FPS Sync Failure") } finally {
        lastSyncTime = System.nanoTime() - min(overSleep, sleepTime)

        if (overSleep > variableYieldTime) {
            variableYieldTime = min(variableYieldTime + 200 * 1000, sleepTime)
        } else if (overSleep < variableYieldTime - 200 * 1000) {
            variableYieldTime = max(variableYieldTime - 2 * 1000, 0)
        }
    }
}

fun getSystemLocale(): Locale? {
    return Locale.getDefault()
}


fun convStrToColor(input: String): rat.poison.game.Color { //Rat poison color
    var line = input
    line = line.replace("Color(", "").replace(")", "").replace(",", "")

    val arrayLine = line.trim().split(" ".toRegex(), 4)

    return rat.poison.game.Color(arrayLine[0].replace("red=", "").toInt(),
            arrayLine[1].replace("green=", "").toInt(),
            arrayLine[2].replace("blue=", "").toInt(),
            arrayLine[3].replace("alpha=", "").toDouble())
}

fun convStrToColorGDX(input: String): Color {
    var line = input
    line = line.replace("Color(", "").replace(")", "").replace(",", "")

    val arrayLine = line.trim().split(" ".toRegex(), 4)

    return Color(arrayLine[0].replace("red=", "").toFloat()/255F,
            arrayLine[1].replace("green=", "").toFloat()/255F,
            arrayLine[2].replace("blue=", "").toFloat()/255F,
            arrayLine[3].replace("alpha=", "").toFloat())
}

fun String.toWeaponClass(): oWeapon {
    var tStr = this
    tStr = tStr.replace("oWeapon(", "").replace(")", "")
    val tSA = tStr.split(", ") //temp String Array
    return oWeapon(tOverride = tSA.pull(0).strToBool(), tFRecoil = tSA.pull(1).strToBool(), tFlatAim = tSA.pull(2).strToBool(), tPathAim = tSA.pull(3).strToBool(), tAimBone = tSA.pull(4).toInt(), tAimFov = tSA.pull(5).toInt(), tAimSpeed = tSA.pull(6).toInt(), tAimSmooth = tSA.pull(7).toDouble(), tPerfectAim = tSA.pull(8).strToBool(), tPAimFov = tSA.pull(9).toInt(), tPAimChance = tSA.pull(10).toInt(), tScopedOnly = tSA.pull(11).strToBool(), tBoneTrig = tSA.pull(12).strToBool(), tBTrigBone = tSA.pull(13).toInt(), tBTrigAim = tSA.pull(14).strToBool(), tBTrigDelay = tSA.pull(15).toInt(), tAimAfterShots = tSA.pull(16).toInt())
}

fun String.toSkinWeaponClass(): sWeapon {
    var tStr = this
    tStr = tStr.replace("sWeapon(", "").replace(")", "")
    val tSA = tStr.split(", ")
    return sWeapon(tSkinID = tSA.pull(0).toInt(), tStatTrak = tSA.pull(1).toInt(), tWear = tSA.pull(2).toFloat(), tSeed = tSA.pull(3).toInt())
}

fun List<String>.pull(idx: Int): String {
    val tStr = this[idx].replace(" ", "") //Remove spaces
    val split = tStr.split("=")
    return split[1]
}

fun checkFlags(nameInSettings: String): Boolean {
    val keyType = curSettings[nameInSettings + "_KEY_TYPE"]
    val key = curSettings[nameInSettings + "_KEY"].toInt()
    return (keyType == "OnKey" && keyPressed(key)) || (keyType == "OffKey" && !keyPressed(key)) || (keyType != "OffKey" && keyType != "OnKey")
}
//Matrix 4 uses column-major order
fun Array<DoubleArray>.toMatrix4(): Matrix4 {
    val input = this
    val mat4 = Matrix4()
    val fArr = FloatArray(16)

    var itr = 0
    for (row in 0..3) {
        for (col in 0..3) {
            fArr[itr] = input[col][row].toFloat()
            itr++
        }
    }

    mat4.set(fArr)
    return mat4
}