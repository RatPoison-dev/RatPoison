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
import rat.poison.interfaces.*
import rat.poison.jna.enums.AccentStates
import rat.poison.overlay.Overlay
import rat.poison.scripts.*
import rat.poison.scripts.aim.*
import rat.poison.scripts.esp.*
import rat.poison.settings.*
import rat.poison.ui.UIBombTimer
import rat.poison.ui.UIMenu
import rat.poison.ui.UISpectatorList
import rat.poison.ui.uiUpdate
import rat.poison.utils.*
import java.io.*
import kotlin.math.max
import kotlin.math.min

const val SETTINGS_DIRECTORY = "settings"
var saving = false
var settingsLoaded = false

val curSettings = Settings()

fun main() {
    System.setProperty("jna.nosys", "true")

    loadSettingsFromFiles(SETTINGS_DIRECTORY)

    Thread.sleep(5000)
    println("Launching...")

    if (FLICKER_FREE_GLOW) {
        PROCESS_ACCESS_FLAGS = PROCESS_ACCESS_FLAGS or WinNT.PROCESS_VM_OPERATION
    }

    if (!MENU) { //If we arent' using the menu disable everything that uses the menu
        curSettings["BOX_ESP"] = "false"
        curSettings["SKELETON_ESP"] = "false"
        curSettings["ENABLE_RECOIL_CROSSHAIR"] = "false"
        curSettings["ENABLE_BOMB_TIMER"] = "false"
        curSettings["INDICATOR_ESP"] = "false"
    }

    CSGO.initialize()

    //Init all scripts
    bunnyHop()
    autoStrafe()
    rcs()
    rcrosshair()
    flatAim()
    pathAim()
    setAim()
    boneTrigger() //Called once during startup, causes firing on startup
    autoKnife()
    reducedFlash()
    spectatorList()
    bombTimer()
    esp() //Contains esp scripts
    espToggle()
    automaticWeapon()
    fastStop()
    //ranks()

    println("App Title: " + curSettings["MENU_APP"]!!.replace("\"", ""))

    //Overlay check, not updated?
    if (curSettings["MENU"]!!.strToBool()) {
        App.open()

        GlobalScope.launch {
            Thread.sleep(2000)
            glfwInit()

            var w = CSGO.gameWidth
            var h = CSGO.gameHeight

            if (w == 0 || h == 0) {
                w = curSettings["OVERLAY_WIDTH"]!!.toInt()
                h = curSettings["OVERLAY_HEIGHT"]!!.toInt()
            }

            Lwjgl3Application(App, Lwjgl3ApplicationConfiguration().apply {
                setTitle("Rat Poison UI")
                setWindowedMode(w, h)
                useVsync(curSettings["OPENGL_VSYNC"]!!.strToBool())
                setBackBufferConfig(8, 8, 8, 8, 16, 0, curSettings["OPENGL_MSAA_SAMPLES"]!!.toInt())
            })
        }
    }
    else {
        scanner() //Scanner is currently outdated
    }
}

fun loadSettingsFromFiles(fileDir : String, specificFile : Boolean = false) {
    settingsLoaded = false
    if (specificFile)
    {
        FileReader(File(fileDir)).readLines().forEach { line ->
            if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith("\"") && !line.startsWith(" *") && !line.startsWith("*") && line.trim().isNotEmpty()) {
                val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

                curSettings[curLine[0]] = curLine[2]
            }
        }
    }
    else {
        File(fileDir).listFiles()?.forEach { file ->
            if (file.name != "cfg1.kts" && file.name != "cfg2.kts" && file.name != "cfg3.kts" && file.name != "hitsound.mp3") {
                FileReader(file).readLines().forEach { line ->
                    if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith(" *") && !line.startsWith("*") && line.trim().isNotEmpty()) {
                        val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

                        curSettings[curLine[0]] = curLine[2]
                    }
                }
            }
        }
    }
    settingsLoaded = true
    println("Finished loading settings")
}

var opened = false
var overlayMenuKey = ObservableBoolean({keyPressed(1)})

object App : ApplicationAdapter() {
    lateinit var sb: SpriteBatch
    lateinit var textRenderer: BitmapFont
    lateinit var shapeRenderer: ShapeRenderer
    private val overlay = Overlay(curSettings["MENU_APP"]!!.toString().replace("\"", ""), "Rat Poison UI", AccentStates.ACCENT_ENABLE_BLURBEHIND)
    var haveTarget = false
    lateinit var menuStage: Stage
    private lateinit var bombStage: Stage
    private lateinit var specListStage: Stage
    private val glyphLayout = GlyphLayout()
    private val bodies = ObjectArrayList<App.() -> Unit>()
    private lateinit var camera: OrthographicCamera

    lateinit var uiMenu: UIMenu //= UIMenu() //Main UI Window
    private lateinit var uiBombWindow: UIBombTimer //= UIBombTimer() //Bomb Timer UI Window
    private lateinit var uiSpecList: UISpectatorList //= UISpectatorList() //Spectator List UI Window

    override fun create() {
        overlayMenuKey = ObservableBoolean({ keyPressed(curSettings["MENU_KEY"]!!.toInt()) })
        VisUI.load()

        //Implement stage for menu
        menuStage = Stage() //Main Menu Stage
        bombStage = Stage() //Bomb Timer Stage
        specListStage = Stage() //Spectator List Stage
        val root = VisTable()
        //root.setFillParent(true)
        menuStage.addActor(root)
        shapeRenderer = ShapeRenderer().apply { setAutoShapeType(true) }
        uiMenu = UIMenu()
        uiBombWindow = UIBombTimer()
        uiSpecList = UISpectatorList()
        menuStage.addActor(uiMenu)
        bombStage.addActor(uiBombWindow)
        specListStage.addActor(uiSpecList)
        val inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(menuStage)
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
        sync(curSettings["OPENGL_FPS"]!!.toInt())

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
                }

                if (curSettings["ENABLE_BOMB_TIMER"]!!.strToBool())
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

                if (curSettings["SPECTATOR_LIST"]!!.strToBool()) {
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


                sb.projectionMatrix = menuStage.camera.combined

                glEnable(GL20.GL_BLEND)
                glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA) //Not needed?
                glClearColor(0F, 0F, 0F, 0F)
                shapeRenderer.projectionMatrix = menuStage.camera.combined
                for (i in 0 until bodies.size) bodies[i]()
                glDisable(GL20.GL_BLEND)
            }

            overlayMenuKey.update()
            if (overlayMenuKey.justBecomeTrue) {
                MENUTOG = !MENUTOG
                overlay.clickThrough = !MENUTOG
                uiUpdate()
            }

            val w = overlay.width
            val h = overlay.height

            if (menuStage.viewport.screenWidth != w || menuStage.viewport.screenHeight != h) {
                resize(w, h)
                menuStage.viewport.update(w, h)
                bombStage.viewport.update(w, h)
                specListStage.viewport.update(w, h)
                println("Debug: Updated viewports")
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
fun Double.toBool() = this == 1.0
fun Boolean.toFloat() = if (this) 1F else 0F
fun Boolean.toDouble() = if (this) 1.0 else 0.0

fun Any.strToColor() = convStrToColor(this.toString())

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

    val listLine = line.trim().split(" ".toRegex(), 12)

    val arrayLine = arrayOfNulls<Double>(12)

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