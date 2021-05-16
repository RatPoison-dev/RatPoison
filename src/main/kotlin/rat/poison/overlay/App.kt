@file:Suppress("DEPRECATION")
package rat.poison.overlay

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.GL20.GL_BLEND
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.actions.Actions.delay
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.kotcrab.vis.ui.VisUI
import com.sun.management.OperatingSystemMXBean
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import rat.poison.*
import rat.poison.game.updateViewMatrix
import rat.poison.interfaces.IOverlay
import rat.poison.interfaces.IOverlayListener
import rat.poison.jna.enums.AccentStates
import rat.poison.scripts.visuals.espToggleCallback
import rat.poison.settings.DEBUGTOG
import rat.poison.settings.MENUTOG
import rat.poison.settings.WARNINGTOG
import rat.poison.ui.MenuStage
import rat.poison.ui.uiTabs.updateDisableAim
import rat.poison.ui.uiUpdate
import rat.poison.ui.uiWindows.*
import rat.poison.utils.AssetManager
import rat.poison.utils.common.ObservableBoolean
import rat.poison.utils.common.keyPressed
import rat.poison.utils.updateFonts
import java.lang.management.ManagementFactory
import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureNanoTime

var opened = false

//exceptions to la rule
var overlayMenuKey = ObservableBoolean({ keyPressed(curSettings.int["MENU_KEY"]) })
var toggleAimKey = ObservableBoolean({ keyPressed(curSettings.int["AIM_TOGGLE_KEY"]) })
var visualsToggleKey = ObservableBoolean({ keyPressed(curSettings.int["VISUALS_TOGGLE_KEY"]) })

var syncTime = 0L
var glowTime = 0L
var appTime = 0L
var menuTime = 0L
var overlayTime = 0L

object App: ApplicationAdapter() {
    lateinit var sb: SpriteBatch
    lateinit var textRenderer: BitmapFont
    lateinit var shapeRenderer: ShapeRenderer
    val appOverlay = Overlay(if (appless) {
        "Counter-Strike: Global Offensive"
    } else {
        curSettings["MENU_APP"].replace("\"", "")
    }, "Rat Poison UI", AccentStates.ACCENT_ENABLE_BLURBEHIND)
    lateinit var menuStage: MenuStage
    lateinit var assetManager: AssetManager
    private lateinit var menuBatch: SpriteBatch
    private lateinit var viewport: ScalingViewport
    lateinit var keyProcessor: KeyProcessor
    private val bodies = ObjectArrayList<App.() -> Unit>()
    private lateinit var camera: OrthographicCamera

    lateinit var uiWarning: UIWarning
    lateinit var uiDebug: UIDebug
    lateinit var uiWatermark: UIWatermark
    lateinit var uiMenu: UIMenu
    lateinit var uiArrows: UIArrows
    lateinit var uiBombWindow: UIBombTimer
    lateinit var uiSpecList: UISpectatorList
    lateinit var uiKeybinds: UIKeybinds

    var firstUpdate = false

    val osBean = ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean
    var haveTarget = false

    override fun create() {
        assetManager = AssetManager()
        assetManager.loadAssets()
        assetManager.updateFonts()

        while (!VisUI.isLoaded()) {
            println("Manually Loading VisUI...")
            delay(1000F)

            if (!VisUI.isLoaded()) {
                assetManager.updateFontsList()

                val defaultFont = "$SETTINGS_DIRECTORY\\Assets\\Fonts\\${curSettings["FONT"].replace("\"", "")}.ttf"
                val font = assetManager.fonts[defaultFont]
                if (font != null) {
                    //apply font settings
                    val parameter = FreeTypeFontParameter()
                    parameter.size = curSettings.int["FONT_SIZE"]
                    parameter.color = curSettings.colorGDX["FONT_COLOR"]

                    //border
                    parameter.borderWidth = curSettings.float["FONT_BORDER_WIDTH"]
                    parameter.borderColor = curSettings.colorGDX["FONT_BORDER_COLOR"]
                    parameter.borderStraight = curSettings.bool["FONT_BORDER_USE_STRAIGHT"]

                    //shadow
                    parameter.shadowColor = curSettings.colorGDX["FONT_SHADOW_COLOR"]
                    parameter.shadowOffsetX = curSettings.int["FONT_SHADOW_OFFSET_X"]
                    parameter.shadowOffsetY = curSettings.int["FONT_SHADOW_OFFSET_Y"]

                    parameter.kerning = curSettings.bool["FONT_INCLUDE_KERNING"]

                    //just a bruh moment
                    parameter.characters += "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
                    parameter.characters += "aąbcćdeęfghijklłmnńoóprsśtuwyzźżAĄBCĆDEĘFGHIJKLŁMNŃOÓPRSŚTUWYZŹŻ"

                    parameter.flip = curSettings.bool["FONT_FLIP"]
                    parameter.genMipMaps = curSettings.bool["FON_GEN_MIP_MAPS"]
                    parameter.gamma = curSettings.float["FONT_GAMMA"]

                    val skin = Skin()

                    val generatedFont = font.generateFont(parameter)
                    textRenderer = generatedFont
                    if (!VisUI.isLoaded()) {
                        skin.add("default-font", generatedFont, BitmapFont::class.java)
                        skin.addRegions(TextureAtlas(Gdx.files.internal(("skin/tinted.atlas"))))
                        skin.load(Gdx.files.internal("skin/tinted.json"))
                        VisUI.load(skin)
                    }
                } else {
                    println("font nullll")
                }
            }
        }

        //Implement key processor for menu
        keyProcessor = KeyProcessor()

        shapeRenderer = ShapeRenderer().apply { setAutoShapeType(true) }

        uiWarning = UIWarning()
        uiDebug = UIDebug()
        uiWatermark = UIWatermark()
        uiMenu = UIMenu()
        uiArrows = UIArrows()
        uiBombWindow = UIBombTimer()
        uiSpecList = UISpectatorList()
        uiKeybinds = UIKeybinds()

        camera = OrthographicCamera()
        sb = SpriteBatch()
        menuBatch = SpriteBatch()
        viewport = ScalingViewport(Scaling.stretch, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat(), camera)

        menuStage = MenuStage(viewport, menuBatch) //Main Menu Stage
        menuStage.addActor(uiMenu)

        Gdx.input.inputProcessor = InputMultiplexer(menuStage, keyProcessor)
        camera = OrthographicCamera()
        Gdx.gl.glClearColor(0F, 0F, 0F, 0F)

        //Implement stage for menu

        appOverlay.start()

        uiUpdate()
    }

    private var variableYieldTime = 0L
    private var lastTime = 0L

    /**
     * An accurate sync method that adapts automatically
     * to the system it runs on to provide reliable results.
     *
     * @param fps The desired frame rate, in frames per second
     * @author kappa (On the LWJGL Forums)
     */
    private fun sync(fps: Int) {
        if (fps <= 0) return
        val sleepTime = (1000000000 / fps).toLong() // nanoseconds to sleep this frame
        // yieldTime + remainder micro & nano seconds if smaller than sleepTime
        val yieldTime = min(sleepTime, variableYieldTime + sleepTime % (1000 * 1000))
        var overSleep: Long = 0 // time the sync goes over by
        try {
            while (true) {
                val t: Long = System.nanoTime() - lastTime
                if (t < sleepTime - yieldTime) {
                    Thread.sleep(1)
                } else if (t < sleepTime) {
                    // burn the last few CPU cycles to ensure accuracy
                    Thread.yield()
                } else {
                    overSleep = t - sleepTime
                    break // exit while loop
                }
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            lastTime = System.nanoTime() - min(overSleep, sleepTime)

            // auto tune the time sync should yield
            if (overSleep > variableYieldTime) {
                // increase by 200 microseconds (1/5 a ms)
                variableYieldTime = min(variableYieldTime + 200 * 1000, sleepTime)
            } else if (overSleep < variableYieldTime - 200 * 1000) {
                // decrease by 2 microseconds
                variableYieldTime = max(variableYieldTime - 2 * 1000, 0)
            }
        }
    }

    override fun render() {
        //println("yea yea we still here")

        if (haltProcess) {
            println("im awake and hiding")

            Gdx.gl.apply {
                glClear(GL20.GL_COLOR_BUFFER_BIT)
            }

            appOverlay.bePassive()

            if (VisUI.isLoaded()) {
                VisUI.dispose()
            }

            return
        }

        GL.createCapabilities()

        syncTime = TimeUnit.NANOSECONDS.convert(measureNanoTime {
            sync(curSettings.int["OPENGL_FPS"])
        }, TimeUnit.NANOSECONDS)

        if (VisUI.isLoaded()) {
            if (!Thread.interrupted()) {
                Gdx.gl.apply {
                    GLFW.glfwWindowHint(GLFW.GLFW_DOUBLEBUFFER, GLFW.GLFW_TRUE)

                    glEnable(GL_BLEND)
                    glDisable(GL20.GL_DEPTH_TEST)
                    glClearColor(0F, 0F, 0F, 0F)
                    glClear(GL20.GL_COLOR_BUFFER_BIT)
                    glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

                    if (!menuStage.root.isVisible) return

                    overlayTime = TimeUnit.NANOSECONDS.convert(measureNanoTime {
                        menuTime = TimeUnit.NANOSECONDS.convert(measureNanoTime {
                            assetManager.updateFonts()

                            if (MENUTOG) {
                                if (!firstUpdate) {
                                    uiUpdate()
                                    firstUpdate = true
                                }

                                if (!WARNINGTOG) {
                                    menuStage.remove(uiWarning)
                                } else {
                                    uiWarning.setPosition(uiMenu.x, uiMenu.y + uiMenu.height + 8F)
                                }
                                uiArrows.setPosition(uiMenu.x + uiMenu.width + 8F, uiMenu.y)
                            }

                            if ((MENUTOG && curSettings.bool["DEBUG"]) || DEBUGTOG) {
                                menuStage.add(uiDebug)

                                if (!DEBUGTOG) {
                                    uiDebug.setPosition(uiMenu.x + uiMenu.width + 8F, uiMenu.y + uiMenu.height - uiDebug.height)
                                }
                            } else {
                                menuStage.remove(uiDebug)
                            }

                            if (curSettings.bool["ENABLE_WATERMARK"]) {
                                menuStage.add(uiWatermark)
                                uiWatermark.update()
                            } else {
                                menuStage.remove(uiWatermark)
                            }

                            if (curSettings.bool["ENABLE_BOMB_TIMER"] && curSettings.bool["BOMB_TIMER_MENU"] && curSettings.bool["ENABLE_VISUALS"]) {
                                menuStage.add(uiBombWindow)
                                uiBombWindow.updateAlpha()
                            } else {
                                menuStage.remove(uiBombWindow)
                            }

                            if (curSettings.bool["SPECTATOR_LIST"] && curSettings.bool["ENABLE_VISUALS"]) {
                                menuStage.add(uiSpecList)
                                uiSpecList.updateAlpha()
                            } else if (menuStage.actors.contains(uiSpecList)) {
                                menuStage.remove(uiSpecList)
                            }
                        }, TimeUnit.NANOSECONDS)

                        sb.projectionMatrix = menuStage.camera.combined
                        shapeRenderer.projectionMatrix = menuStage.camera.combined
                        menuBatch.projectionMatrix = menuStage.camera.combined
                        uiMenu.changeAlpha()
                        appTime = TimeUnit.NANOSECONDS.convert(measureNanoTime {
                            updateViewMatrix()
                            if (!appless) {
                                for (i in 0 until bodies.size) {
                                    bodies[i]()
                                }
                            }
                        }, TimeUnit.NANOSECONDS)

                        if (menuBatch.isDrawing) {
                            menuBatch.end()
                        }
                        menuStage.act(Gdx.graphics.deltaTime)
                        menuStage.draw()

                        glFinish()
                    }, TimeUnit.NANOSECONDS)

                    if (dbg) { //Draw Debug
                        //Limit updates
                        try { //TODO fuck
                            if (sb.isDrawing) {
                                sb.end()
                            }

                            sb.begin()
                            glDisable(GL_BLEND)

                            textRenderer.color = Color.WHITE
                            sb.color = Color.WHITE
                            //textRenderer.draw(sb, sbText, CSGO.gameWidth / 3F, CSGO.gameHeight - 100F)

                            sb.end()
                        } catch (e: Exception) {
                        }
                    }
                }

                //Menu Key
                if (!appless) {
                    overlayMenuKey.update()

                    if (overlayMenuKey.justBecameTrue) {
                        MENUTOG = !MENUTOG
                        appOverlay.clickThrough = !MENUTOG

                        if (dbg) println("[DEBUG] Menu Toggled")
                    }
                }

                //Aim Toggle Key
                toggleAimKey.update()
                if (toggleAimKey.justBecameTrue) {
                    aimTab.tMain.enableAim.isChecked = !aimTab.tMain.enableAim.isChecked
                }

                visualsToggleKey.update()
                if (visualsToggleKey.justBecameTrue) {
                    espToggleCallback()
                }

                if (!appless) {
                    val w = appOverlay.width
                    val h = appOverlay.height

                    if (menuStage.viewport.screenWidth != w || menuStage.viewport.screenHeight != h) {
                        menuStage.viewport.update(w, h)
                        if (dbg) println("[DEBUG] Resized Viewports")
                    }
                }
            }
        }

        opened = true
    }

    override fun dispose() {
        appOverlay.stop()
    }

    operator fun invoke(body: App.() -> Unit) {
        bodies.add(body)
    }

    fun open() {
        appOverlay.listener = object: IOverlayListener {
            override fun onTargetAppWindowClosed(overlay: IOverlay) {
                if (curSettings.bool["CLOSE_WITH_CSGO"]) {
                    haveTarget = false
                    if (opened) {
                        uiMenu.closeMenu()
                    }
                }
            }

            override fun onAfterInit(overlay: IOverlay) {
                overlay.clickThrough = !appless
                overlay.protectAgainstScreenshots = false
                haveTarget = true
                updateDisableAim()
            }

            override fun onActive(overlay: IOverlay) {
                if (curSettings.bool["KEYBINDS"]) {
                    menuStage.add(uiKeybinds)
                } else if (menuStage.actors.contains(uiKeybinds)) {
                    menuStage.clear()
                }

                menuStage.add(uiMenu)

                menuStage.add(uiWarning)
                uiWarning.width = uiMenu.width //TODO FUCK pack()
                uiWarning.setPosition(uiMenu.x, uiMenu.y + uiMenu.height + 8F)

                menuStage.add(uiArrows)
                uiArrows.setPosition(uiMenu.x + uiMenu.width + 8F, uiMenu.y)
            }

            override fun onPassive(overlay: IOverlay) {
                menuStage.removeVisWindows()
            }

            override fun onBackground(overlay: IOverlay) {}
            override fun onForeground(overlay: IOverlay) {}
            override fun onBoundsChange(overlay: IOverlay, x: Int, y: Int, width: Int, height: Int) {}
        }
    }
}