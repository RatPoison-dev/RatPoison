@file:JvmName("RatPoison")
@file:Suppress("BlockingMethodInNonBlockingContext")

package rat.poison

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Gdx.gl
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.backends.lwjgl3.*
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.VisTable
import com.sun.jna.platform.win32.WinNT
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jire.arrowhead.keyPressed
import org.lwjgl.glfw.GLFW.glfwInit
import rat.poison.game.CSGO
import rat.poison.game.updateViewMatrix
import rat.poison.interfaces.*
import rat.poison.jna.enums.AccentStates
import rat.poison.overlay.Overlay
import rat.poison.scripts.*
import rat.poison.scripts.aim.*
import rat.poison.scripts.esp.*
import rat.poison.settings.*
import rat.poison.ui.*
import rat.poison.utils.*
import java.io.*
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

const val EXPERIMENTAL = false
const val SETTINGS_DIRECTORY = "settings"
var saving = false
var settingsLoaded = false

val curSettings = Settings()

var dbg = false

fun main() {
    System.setProperty("jna.nosys", "true")

    println("Loading settings...")
    loadSettingsFromFiles(SETTINGS_DIRECTORY)

    dbg = curSettings["DEBUG"].strToBool()

    if (dbg) println("DEBUG enabled")

    Thread.sleep(5000)
    println("Launching...")

    CSGO.initialize()

    if (dbg) println("[DEBUG] Initializing scripts...")
    //Init scripts
    if (!curSettings["MENU"].strToBool()) { //If we arent' using the menu disable everything that uses the menu
        if (dbg) println("[DEBUG] Menu disabled, disabling box, skeleton, rcrosshair, btimer, indicator, speclist, hitmarker")

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
    } else {
        if (dbg) { println("[DEBUG] Initializing Recoil Crosshair") }; rcrosshair()
        if (dbg) { println("[DEBUG] Initializing Recoil Spectator List") }; spectatorList()
        if (dbg) { println("[DEBUG] Initializing Recoil Ranks") }; ranks()
        if (dbg) { println("[DEBUG] Initializing Recoil Bomb Timer") }; bombTimer()
        if (dbg) { println("[DEBUG] Initializing Hit Marker") }; hitMarker()
        if (dbg) { println("[DEBUG] Initializing Nade Helper") }; nadeHelper()
        if (dbg) { println("[DEBUG] Initializing Nade Tracer") }; nadeTracer()
    }

    if (dbg) { println("[DEBUG] Initializing Bunny Hop") }; bunnyHop()
    if (dbg) { println("[DEBUG] Initializing Auto Strafe") }; autoStrafe()
    if (dbg) { println("[DEBUG] Initializing RCS") }; rcs()
    if (dbg) { println("[DEBUG] Initializing Flat Aim") }; flatAim()
    if (dbg) { println("[DEBUG] Initializing Path Aim") }; pathAim()
    if (dbg) { println("[DEBUG] Initializing Set Aim") }; setAim()
    if (dbg) { println("[DEBUG] Initializing Bone Trigger") }; boneTrigger()
    if (dbg) { println("[DEBUG] Initializing Auto Knife") }; autoKnife()
    if (dbg) { println("[DEBUG] Initializing Reduced Flash") }; reducedFlash()
    if (dbg) { println("[DEBUG] Initializing ESPs") }; esp()
    if (dbg) { println("[DEBUG] Initializing Esp Toggle") }; espToggle()
    if (dbg) { println("[DEBUG] Initializing Automatic Weapons") }; automaticWeapon()
    if (dbg) { println("[DEBUG] Initializing Fast Stop") }; fastStop()
    if (dbg) { println("[DEBUG] Initializing Head Walk (Currently disabled)") }; headWalk()
    if (dbg) { println("[DEBUG] Initializing Adrenaline") }; adrenaline()

    //Overlay check, not updated?
    if (curSettings["MENU"].strToBool()) {
        println("App Title: " + curSettings["MENU_APP"].replace("\"", ""))

        App.open()

        GlobalScope.launch {
            Thread.sleep(2000)
            glfwInit()

            var w = CSGO.gameWidth
            var h = CSGO.gameHeight

            if (w == 0 || h == 0) {
                w = curSettings["OVERLAY_WIDTH"].toInt()
                h = curSettings["OVERLAY_HEIGHT"].toInt()
            }

            Lwjgl3Application(App, Lwjgl3ApplicationConfiguration().apply {
                setTitle("Rat Poison UI")
                setWindowedMode(w, h)

                //Required to fix W2S offset
                setWindowPosition(CSGO.gameX, CSGO.gameY)
                setDecorated(false)

                useVsync(curSettings["OPENGL_VSYNC"].strToBool())
                setBackBufferConfig(8, 8, 8, 8, 16, 0, curSettings["OPENGL_MSAA_SAMPLES"].toInt())
            })
        }
    }
    else {
        scanner() //Scanner is currently outdated
    }
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
            if (file.name != "CFGS" && file.name != "hitsounds" && file.name != "NadeHelper") {
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

object App : ApplicationAdapter() {
    lateinit var sb: SpriteBatch
    lateinit var textRenderer: BitmapFont
    lateinit var shapeRenderer: ShapeRenderer
    val overlay = Overlay(curSettings["MENU_APP"].replace("\"", ""), "Rat Poison UI", AccentStates.ACCENT_ENABLE_BLURBEHIND)
    var haveTarget = false
    lateinit var menuStage: Stage
    private lateinit var aimOverrideStage: Stage
    private lateinit var bombStage: Stage
    private lateinit var specListStage: Stage
    private val bodies = ObjectArrayList<App.() -> Unit>()
    private lateinit var camera: OrthographicCamera

    lateinit var uiMenu: UIMenu
    lateinit var uiBombWindow: UIBombTimer
    lateinit var uiSpecList: UISpectatorList
    lateinit var uiAimOverridenWeapons: UIAimOverridenWeapons

    override fun create() {
        overlayMenuKey = ObservableBoolean({ keyPressed(curSettings["MENU_KEY"].toInt()) })
        VisUI.load(Gdx.files.internal("skin\\tinted.json"))

        //Implement stage for menu
        menuStage = Stage() //Main Menu Stage
        aimOverrideStage = Stage() //Aim Override Weapons Stage
        bombStage = Stage() //Bomb Timer Stage
        specListStage = Stage() //Spectator List Stage
        val root = VisTable()
        //root.setFillParent(true)
        menuStage.addActor(root)
        shapeRenderer = ShapeRenderer().apply { setAutoShapeType(true) }

        uiMenu = UIMenu()
        uiBombWindow = UIBombTimer()
        uiSpecList = UISpectatorList()
        uiAimOverridenWeapons = UIAimOverridenWeapons()

        menuStage.addActor(uiMenu)
        aimOverrideStage.addActor(uiAimOverridenWeapons)
        bombStage.addActor(uiBombWindow)
        specListStage.addActor(uiSpecList)
        val inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(menuStage)
        inputMultiplexer.addProcessor(aimOverrideStage)
        inputMultiplexer.addProcessor(bombStage)
        inputMultiplexer.addProcessor(specListStage)
        //End stage implementation

        Gdx.input.inputProcessor = inputMultiplexer

        sb = SpriteBatch()
        textRenderer = BitmapFont()
        camera = OrthographicCamera()
        Gdx.gl.glClearColor(0F, 0F, 0F, 0F)

        overlay.start()
    }

    override fun render() {
        sync(curSettings["OPENGL_FPS"].toInt())

        if (!Thread.interrupted()) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

            gl.apply {
                if (!menuStage.root.isVisible) return

                if (MENUTOG) {
                    menuStage.act(Gdx.graphics.deltaTime)
                    val batch = menuStage.batch
                    val menuCamera = menuStage.viewport.camera
                    menuCamera.update()
                    batch.projectionMatrix = menuCamera.combined
                    batch.begin()
                    batch.enableBlending()
                    menuStage.root.draw(batch, 1F)
                    batch.end()

                    if (curSettings["ENABLE_OVERRIDE"].strToBool()) {
                        aimOverrideStage.act(Gdx.graphics.deltaTime)
                        val aimOverrideCamera = aimOverrideStage.viewport.camera
                        aimOverrideCamera.update()
                        val aimOverrideBatch = aimOverrideStage.batch
                        aimOverrideBatch.projectionMatrix = aimOverrideCamera.combined
                        aimOverrideBatch.begin()
                        aimOverrideBatch.enableBlending()
                        aimOverrideStage.root.draw(aimOverrideBatch, 1F)
                        aimOverrideBatch.end()
                    }
                }

                if (curSettings["ENABLE_BOMB_TIMER"].strToBool() && curSettings["BOMB_TIMER_MENU"].strToBool())
                {
                    bombStage.act(Gdx.graphics.deltaTime)
                    val bombCamera = bombStage.viewport.camera
                    bombCamera.update()
                    val bombBatch = bombStage.batch
                    bombBatch.projectionMatrix = bombCamera.combined
                    bombBatch.begin()
                    bombBatch.enableBlending()
                    bombStage.root.draw(bombBatch, 1F)
                    bombBatch.end()
                }

                if (curSettings["SPECTATOR_LIST"].strToBool()) {
                    specListStage.act(Gdx.graphics.deltaTime)
                    val specListCamera = specListStage.viewport.camera
                    specListCamera.update()
                    val specListBatch = specListStage.batch
                    specListBatch.projectionMatrix = specListCamera.combined
                    specListBatch.begin()
                    specListBatch.enableBlending()
                    specListStage.root.draw(specListBatch, 1F)
                    specListBatch.end()
                }

                glEnable(GL20.GL_BLEND)
                glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
                glClearColor(0F, 0F, 0F, 0F)
                sb.projectionMatrix = menuStage.camera.combined
                shapeRenderer.projectionMatrix = menuStage.camera.combined
                updateViewMatrix()
                for (i in 0 until bodies.size) {
                    bodies[i]()
                }
                glDisable(GL20.GL_BLEND)
            }

            overlayMenuKey.update()
            if (overlayMenuKey.justBecomeTrue) {
                MENUTOG = !MENUTOG
                overlay.clickThrough = !MENUTOG

                uiMenu.updateChilds()
                uiUpdate()

                if (dbg) println("[DEBUG] Menu Toggled")
            }

            val w = overlay.width
            val h = overlay.height

            if (menuStage.viewport.screenWidth != w || menuStage.viewport.screenHeight != h) {
                resize(w, h)
                menuStage.viewport.update(w, h)
                bombStage.viewport.update(w, h)
                specListStage.viewport.update(w, h)
                if (dbg) println("[DEBUG] Resized Viewports")
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
    } catch (ex: InterruptedException) { } finally {
        lastSyncTime = System.nanoTime() - min(overSleep, sleepTime)

        if (overSleep > variableYieldTime) {
            variableYieldTime = min(variableYieldTime + 200 * 1000, sleepTime)
        } else if (overSleep < variableYieldTime - 200 * 1000) {
            variableYieldTime = max(variableYieldTime - 2 * 1000, 0)
        }
    }
}

fun Any.strToBool() = this == "true" || this == true || this == 1.0
fun Any.boolToDouble() = if (this == "true" || this == true) 1.0 else (0.0)
fun Any.boolToStr() = this.toString()
fun Any.strToColor() = convStrToColor(this.toString())
fun Any.cToDouble() = this.toString().toDouble()
fun Double.toBool() = this == 1.0
fun Boolean.toFloat() = if (this) 1F else 0F
fun Boolean.toDouble() = if (this) 1.0 else 0.0

fun convStrToColor(input: String): rat.poison.game.Color {
    var line = input
    line = line.replace("Color(", "").replace(")", "").replace(",", "")

    val arrayLine = line.trim().split(" ".toRegex(), 4)

    return rat.poison.game.Color(arrayLine[0].replace("red=", "").toInt(),
            arrayLine[1].replace("green=", "").toInt(),
            arrayLine[2].replace("blue=", "").toInt(),
            arrayLine[3].replace("alpha=", "").toDouble())
}

fun convStrToArray(input: String?): Array<Double?> {
    var line = input
    line = line!!.replace("doubleArrayOf(", "").replace(")", "").replace(",", "").replace("[", "").replace("]", "")

    val listLine = line.trim().split(" ".toRegex(), 13)

    val arrayLine = arrayOfNulls<Double>(13)

    for (i in 0 until listLine.size)
    {
        arrayLine[i] = listLine[i].toDouble()
    }

    return arrayLine
}

fun convArrayToStr(input: String): String {
    var line = input
    line = line.replace("[", "doubleArrayOf(").replace("]", ")")

    return line
}

fun Angle.magnitude() : Double = sqrt(x.pow(2) + y.pow(2) + z.pow(2))