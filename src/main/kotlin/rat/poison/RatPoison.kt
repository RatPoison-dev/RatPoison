@file:JvmName("RatPoison")

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
import org.jetbrains.kotlin.cli.common.environment.setIdeaIoUseFallback
import org.jire.arrowhead.keyPressed
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
import rat.poison.utils.*
import java.io.*
import javax.script.ScriptEngineManager
//Move separate toBool/toInt/toFloat to public here

//aaaaaaaaaaaaaaaaAAAAAAAAAAAAA
const val SETTINGS_DIRECTORY = "settings"
var saving = false
var settingsLoaded = false

val curSettings = Settings()

fun main() {
    System.setProperty("jna.nosys", "true")

    loadSettingsFromFiles()

    if (FLICKER_FREE_GLOW) {
        PROCESS_ACCESS_FLAGS = PROCESS_ACCESS_FLAGS or WinNT.PROCESS_VM_OPERATION
    }

    if (!MENU) {
        curSettings["BOX_ESP"] = "false"
        curSettings["SKELETON_ESP"] = "false"
        curSettings["ENABLE_RECOIL_CROSSHAIR"] = "false"
        curSettings["ENABLE_BOMB_TIMER"] = "false"
        curSettings["INDICATOR_ESP"] = "false"
    }

    CSGO.initialize()

    Thread.sleep(256)

    bunnyHop()
    rcs()
    rcrosshair()
    flatAim()
    pathAim()
    setAim()
    boneTrigger()
    reducedFlash()
    bombTimer()
    esp()
    espToggle()
    automaticWeapon()

    //Overlay check, not updated?
    if (MENU && (curSettings["BOX_ESP"]!!.strToBool() || curSettings["SKELETON_ESP"]!!.strToBool() || curSettings["ENABLE_BOMB_TIMER"]!!.strToBool() || curSettings["ENABLE_RECOIL_CROSSHAIR"]!!.strToBool() || curSettings["INDICATOR_ESP"]!!.strToBool())) {
        App.open()

        Lwjgl3Application(App, Lwjgl3ApplicationConfiguration().apply {
            setTitle("Rat Poison UI")
            if (CSGO.gameWidth < 1 || CSGO.gameHeight < 1) {
                setWindowedMode(OVERLAY_WIDTH, OVERLAY_HEIGHT)
            } else {
                setWindowedMode(CSGO.gameWidth, CSGO.gameHeight)
            }
            useVsync(OPENGL_VSYNC)
            setBackBufferConfig(8, 8, 8, 8, 16, 0, OPENGL_MSAA_SAMPLES)
        })
    }
    else {
        scanner()
    }
}

//var engine = ScriptEngineManager().getEngineByName("kotlin")

fun loadSettingsFromFiles() {
    File(SETTINGS_DIRECTORY).listFiles().forEach { file ->
        if (file.name != "cfg1.kts" && file.name != "cfg2.kts" && file.name != "cfg3.kts" && file.name != "Advanced.kts" && file.name != "hitsound.mp3") {
            FileReader(file).readLines().forEach { line ->
                if (!line.startsWith("import") && !line.startsWith("/") && !line.startsWith(" *") && !line.startsWith("*") && !line.trim().isEmpty()) {
                    val curLine = line.trim().split(" ".toRegex(), 3) //Separate line into VARIABLE NAME : "=" : VALUE

                    println(curLine[0] + " = " + curLine[2])

                    curSettings[curLine[0]] = curLine[2]
                }
            }
        }
    }
    settingsLoaded = true
}

var opened = false
var overlayMenuKey = ObservableBoolean({keyPressed(1)})

////Courtesy of Mr. Noad, lmlapp converted to normal
object App : ApplicationAdapter() {
    lateinit var sb: SpriteBatch
    lateinit var textRenderer: BitmapFont
    lateinit var shapeRenderer: ShapeRenderer
    val overlay = Overlay("Counter-Strike: Global Offensive", "Rat Poison UI", AccentStates.ACCENT_ENABLE_BLURBEHIND)
    var haveTarget = false
    lateinit var menuStage: Stage
    lateinit var bombStage: Stage
    private val glyphLayout = GlyphLayout()
    private val bodies = ObjectArrayList<App.() -> Unit>()
    private lateinit var camera: OrthographicCamera

    override fun create() {
        overlayMenuKey = ObservableBoolean({ keyPressed(curSettings["MENU_KEY"].toString().toInt()) })
        opened = true
        VisUI.load()
        super.create()
        overlay.start()

        //Implement stage for menu
        menuStage = Stage()
        bombStage = Stage()
        val root = VisTable()
        root.setFillParent(true)
        menuStage.addActor(root)
        shapeRenderer = ShapeRenderer().apply { setAutoShapeType(true) }
        val UIWindow = UIMenu()
        val UIBombWindow = UIBombTimer()
        menuStage.addActor(UIWindow)
        bombStage.addActor(UIBombWindow)
        val inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(menuStage)
        inputMultiplexer.addProcessor(bombStage)
        //End stage implementation

        Gdx.input.inputProcessor = inputMultiplexer

        sb = SpriteBatch()
        textRenderer = BitmapFont()
        camera = OrthographicCamera()
        Gdx.gl.glClearColor(0F, 0F, 0F, 0F)
    }

    override fun render() {
        sync(OPENGL_FPS)

        if (!Thread.interrupted()) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

            gl.apply {
                if (!menuStage.root.isVisible) return

                //Add menuStage
                menuStage.act(Gdx.graphics.deltaTime)
                bombStage.act(Gdx.graphics.deltaTime)
                val menuCamera = menuStage.viewport.camera
                menuCamera.update()
                val bombCamera = bombStage.viewport.camera
                bombCamera.update()

                if (!overlay.clickThrough) {
                    val batch = menuStage.batch
                    batch.projectionMatrix = menuCamera.combined //camera to menuCamera
                    batch.begin()
                    batch.enableBlending()
                    menuStage.root.draw(batch, 1F)
                    batch.end()
                }

                if (curSettings["ENABLE_BOMB_TIMER"]!!.strToBool())
                {
                    val bombBatch = bombStage.batch
                    bombBatch.projectionMatrix = bombCamera.combined
                    bombBatch.begin()
                    bombBatch.enableBlending()
                    bombStage.root.draw(bombBatch, 1F)
                    bombBatch.end()
                }

                sb.projectionMatrix = menuStage.camera.combined

                //Extra bits might not be needed, from deprecated overlay
                glEnable(GL20.GL_BLEND)
                glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
                glClearColor(0F, 0F, 0F, 0F)
                shapeRenderer.projectionMatrix = menuStage.camera.combined
                for (i in 0 until bodies.size) bodies[i]()
                glDisable(GL20.GL_BLEND)
                //Extra bits might not be needed, from deprecated overlay
            }

            overlayMenuKey.update()
            if (overlayMenuKey.justBecomeTrue) {
                MENUTOG = !MENUTOG
                overlay.clickThrough = !overlay.clickThrough
            }
        }
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
                App.haveTarget = false
            }

            override fun onAfterInit(overlay: IOverlay) {
                overlay.clickThrough = true
                overlay.protectAgainstScreenshots = false
                App.haveTarget = true
            }

            override fun onActive(overlay: IOverlay) {}
            override fun onPassive(overlay: IOverlay) {}
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

    val NANO_SEC = 1000000000L

    var overSleep = 0.toLong()
    val sleepTime = NANO_SEC/fps
    val yieldTime = Math.min(sleepTime, variableYieldTime + sleepTime % (1000000L))

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
        lastSyncTime = System.nanoTime() - Math.min(overSleep, sleepTime)

        if (overSleep > variableYieldTime) {
            variableYieldTime = Math.min(variableYieldTime + 200 * 1000, sleepTime)
        } else if (overSleep < variableYieldTime - 200 * 1000) {
            variableYieldTime = Math.max(variableYieldTime - 2 * 1000, 0)
        }
    }
}

fun Any.strToBool() = this == "true" || this == true
fun Any.boolToStr() = this.toString()

fun Any.strToColor() = convStrToColor(this.toString())

fun convStrToColor(input: String): rat.poison.game.Color {
    var line = input
    line = line.replace("Color(", "").replace(")", "").replace(",", "")

    var curLine = line.trim().split(" ".toRegex(), 4)


    var color = rat.poison.game.Color(curLine[0].replace("red=", "").toInt(),
            curLine[1].replace("green=", "").toInt(),
            curLine[2].replace("blue=", "").toInt(),
            curLine[3].replace("alpha=", "").toDouble())

    return color
}