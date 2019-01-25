@file:JvmName("RatPoison")

package rat.poison

///Fuck these imports holy shit, move app somewhere else

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Gdx.gl
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.VisTable
import com.sun.jna.platform.win32.WinNT
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import org.jetbrains.kotlin.cli.common.environment.setIdeaIoUseFallback
import org.jire.arrowhead.keyPressed
import rat.poison.game.CSGO
import rat.poison.interfaces.IOverlay
import rat.poison.interfaces.IOverlayListener
import rat.poison.jna.enums.AccentStates
import rat.poison.overlay.Overlay
import rat.poison.scripts.*
import rat.poison.scripts.aim.flatAim
import rat.poison.scripts.aim.pathAim
import rat.poison.scripts.esp.esp
import rat.poison.scripts.esp.espToggle
import rat.poison.settings.*
import rat.poison.ui.DebuggerWindow
import rat.poison.utils.Dojo
import rat.poison.utils.ObservableBoolean
import java.io.File
import java.io.FileReader

//imports make me wet, cleanup later, offload to other files?
//this bitch is a fucking m e s s //remove and cleanup before pushing, if i dont forget/sudoku

//todo? cut down extras from mr noads proj, change from current dc
//continue ui menu, ui menu has xxxtra todos in its own file
//Fix box esp other drawing shit using the wrong color, were not setting the color back to white

//convert CUser32 to normal User32

//Fix dojo reading cfg file

//Need import color in menu cfg?

//aaaaaaaaaaaaaaaaAAAAAAAAAAAAA
const val SETTINGS_DIRECTORY = "settings"

fun main(args: Array<String>) {
    System.setProperty("jna.nosys", "true")

    //scanner() //not needed, keeping until menu fully finished, possibly implemented UI console
    //implement to ui console? or keep and add a disable menu

    loadSettings()

    if (FLICKER_FREE_GLOW) {
        PROCESS_ACCESS_FLAGS = PROCESS_ACCESS_FLAGS or WinNT.PROCESS_VM_OPERATION
    }

    if (LEAGUE_MODE) { //Currently not updated
        GLOW_ESP = false
        BOX_ESP = false
        SKELETON_ESP = false
        CHAMS_ESP = false
        CHAMS_BRIGHTNESS = 0
        MODEL_ESP = false
        MODEL_AND_GLOW = false
        ENEMY_INDICATOR = false
        ENABLE_ESP = false

        ENABLE_BOMB_TIMER = false
        ENABLE_REDUCED_FLASH = false
        ENABLE_FLAT_AIM = false

        SERVER_TICK_RATE = 128 // most leagues are 128-tick
        PROCESS_ACCESS_FLAGS = WinNT.PROCESS_QUERY_INFORMATION or WinNT.PROCESS_VM_READ // all we need
        GARBAGE_COLLECT_ON_MAP_START = true // get rid of traces
    }

    CSGO.initialize()

    bunnyHop()
    rcs()
    rcrosshair()
    flatAim()
    pathAim()
    boneTrigger()
    reducedFlash()
    bombTimer()
    esp()
    espToggle()

    //Overlay check, not updated?
    if (MENU || BOX_ESP || SKELETON_ESP || ENABLE_BOMB_TIMER || ENABLE_RECOIL_CROSSHAIR || ENEMY_INDICATOR) { //Illegal reflective access, search says need coffee 9 or higher big fuck
        App.open() //New overlay, change name to 'App' later

        Lwjgl3Application(App, Lwjgl3ApplicationConfiguration().apply {
            setTitle("Rat Poison UI")
            setWindowedMode(CSGO.gameWidth, CSGO.gameHeight)
        })
    } else {
        //reimplement scanner if overlay isnt needed at all
        scanner()
    }
}


fun loadSettings() {
	setIdeaIoUseFallback()
	
	File(SETTINGS_DIRECTORY).listFiles().forEach {
        if (it.name != "cfg.kts" && it.name != "sickomode.kts") {
            FileReader(it).use {
                Dojo.script(it.readLines().joinToString("\n"))
            }
        }
    }
}


////Courtesy of Mr. Noad, lmlapp converted to normal
object App : ApplicationAdapter() {
    lateinit var sb: SpriteBatch
    lateinit var textRenderer: BitmapFont
    lateinit var shapeRenderer: ShapeRenderer
    val overlay = Overlay("Counter-Strike: " + "Global" + " Offensive", "Rat Poison UI", AccentStates.ACCENT_ENABLE_BLURBEHIND)
    var haveTarget = false
    var Menu_Key = ObservableBoolean({ keyPressed(MENU_KEY) })
    lateinit var stage: Stage
    private val glyphLayout = GlyphLayout()
    private val bodies = ObjectArrayList<App.() -> Unit>()
    private lateinit var camera: OrthographicCamera

    override fun create() {
        VisUI.load()
        super.create()
        overlay.start()

        //Implement stage for menu
        stage = Stage()
        val root = VisTable()
        root.setFillParent(true)
        stage.addActor(root)
        shapeRenderer = ShapeRenderer().apply { setAutoShapeType(true) }
        val UIWindow = DebuggerWindow()
        stage.addActor(UIWindow)
        val inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(stage)
        //End stage implementation

        Gdx.input.inputProcessor = inputMultiplexer

        sb = SpriteBatch()
        textRenderer = BitmapFont()
        camera = OrthographicCamera()
        Gdx.gl.glClearColor(0F, 0F, 0F, 0F)
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        gl.apply {
            //Add stage
            stage.act(Gdx.graphics.deltaTime)
            val camera = stage.viewport.camera
            camera.update()

            if (!stage.root.isVisible) return

            if (!overlay.clickThrough) {
                val batch = stage.batch
                batch.projectionMatrix = camera.combined
                batch.begin()
                batch.enableBlending()
                stage.root.draw(batch, 1F)
                batch.end()
            }
            sb.projectionMatrix = stage.camera.combined

            //Extra bits might not be needed, from deprecated overlay
            glEnable(GL20.GL_BLEND)
            glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
            glClearColor(0F, 0F, 0F, 0F)
            shapeRenderer.projectionMatrix = stage.camera.combined
            for (i in 0 until bodies.size) bodies[i]()
            glDisable(GL20.GL_BLEND)
            //Extra bits might not be needed, from deprecated overlay

            //ree?
            //sb.begin()
            //textRenderer.draw(sb, glyphLayout, Gdx.graphics.width - 100f, Gdx.graphics.height - 72f) //Dont know if works
            //sb.end()
        }

        Menu_Key.update()
        if (Menu_Key.justBecomeTrue) {
            MENUTOG = !MENUTOG
            overlay.clickThrough = !overlay.clickThrough
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