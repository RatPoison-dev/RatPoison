@file:Suppress("DEPRECATION")
package rat.poison.overlay

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.GL20.GL_BLEND
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.kotcrab.vis.ui.VisUI
import com.sun.management.OperatingSystemMXBean
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import rat.poison.appless
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.game.CSGO
import rat.poison.game.entity.shotsFired
import rat.poison.game.me
import rat.poison.game.updateViewMatrix
import rat.poison.haltProcess
import rat.poison.interfaces.IOverlay
import rat.poison.interfaces.IOverlayListener
import rat.poison.jna.enums.AccentStates
import rat.poison.scripts.aim.meDead
import rat.poison.scripts.visuals.espToggleCallback
import rat.poison.settings.DANGER_ZONE
import rat.poison.settings.MENUTOG
import rat.poison.ui.uiTabs.updateDisableAim
import rat.poison.ui.uiWindows.*
import rat.poison.ui.uiUpdate
import rat.poison.utils.*
import rat.poison.utils.common.ObservableBoolean
import rat.poison.utils.extensions.appendHumanReadableSize
import rat.poison.utils.extensions.roundNDecimals
import java.lang.management.ManagementFactory
import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureNanoTime

var opened = false
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
    private val overlay = Overlay(if (appless) {
        "Counter-Strike: Global Offensive"
    } else {
        curSettings["MENU_APP"].replace("\"", "")
    }, "Rat Poison UI", AccentStates.ACCENT_ENABLE_BLURBEHIND)
    lateinit var menuStage: Stage
    lateinit var assetManager: AssetManager
    private lateinit var menuBatch: SpriteBatch
    lateinit var viewport: ScalingViewport
    lateinit var keyProcessor: KeyProcessor
    private val bodies = ObjectArrayList<App.() -> Unit>()
    private lateinit var camera: OrthographicCamera

    lateinit var uiWatermark: UIWatermark
    lateinit var uiMenu: UIMenu
    lateinit var uiArrows: UIArrows
    lateinit var uiBombWindow: UIBombTimer
    lateinit var uiSpecList: UISpectatorList
    lateinit var uiKeybinds: UIKeybinds
    private val sbText = StringBuilder()

    private val osBean = ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean
    var haveTarget = false
    private var timer = 0

    override fun create() {
        assetManager = AssetManager()
        assetManager.loadAssets()
        assetManager.updateFonts()

        //Implement key processor for menu
        keyProcessor = KeyProcessor()

        shapeRenderer = ShapeRenderer().apply { setAutoShapeType(true) }

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
        menuStage = Stage(viewport, menuBatch) //Main Menu Stage
        menuStage.addActor(uiMenu)

        Gdx.input.inputProcessor = InputMultiplexer(menuStage, keyProcessor)
        camera = OrthographicCamera()
        Gdx.gl.glClearColor(0F, 0F, 0F, 0F)

        //Implement stage for menu

        overlay.start()
    }

    /**
     * An accurate sync method that adapts automatically
     * to the system it runs on to provide reliable results.
     *
     * @param fps The desired frame rate, in frames per second
     * @author kappa (On the LWJGL Forums)
     */
    private var variableYieldTime = 0L
    private var lastTime = 0L
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
        timer++

        syncTime = TimeUnit.NANOSECONDS.convert(measureNanoTime {
            sync(curSettings.int["OPENGL_FPS"])
        }, TimeUnit.NANOSECONDS)

        if (VisUI.isLoaded()) {
            if (!Thread.interrupted()) {
                Gdx.gl.apply {
                    glEnable(GL_BLEND)
                    glDisable(GL20.GL_DEPTH_TEST)
                    glClearColor(0F, 0F, 0F, 0F)
                    glClear(GL20.GL_COLOR_BUFFER_BIT)
                    glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

                    if (!menuStage.root.isVisible) return

                    overlayTime = TimeUnit.NANOSECONDS.convert(measureNanoTime {
                        menuTime = TimeUnit.NANOSECONDS.convert(measureNanoTime {
                            assetManager.updateFonts()
                            if (MENUTOG || appless) {
                                if (curSettings.bool["KEYBINDS"]) {
                                    if (!menuStage.actors.contains(uiKeybinds)) {
                                        menuStage.addActor(uiKeybinds)
                                    }
                                } else if (menuStage.actors.contains(uiKeybinds)) {
                                    menuStage.clear()
                                }

                                if (!menuStage.actors.contains(uiMenu)) {
                                    menuStage.addActor(uiMenu)
                                }

                                if (!menuStage.actors.contains(uiArrows)) {
                                    menuStage.addActor(uiArrows)
                                } else {
                                    uiArrows.setPosition(uiMenu.x + uiMenu.width + 8F, uiMenu.y)
                                }
                            } else if (menuStage.actors.contains(uiMenu) || menuStage.actors.contains(uiKeybinds) || menuStage.actors.contains(uiArrows)) {
                                menuStage.clear()
                            }

                            if (!menuStage.actors.contains(uiWatermark)) {
                                menuStage.addActor(uiWatermark)
                                uiWatermark.setPosition(CSGO.gameX.toFloat() + CSGO.gameWidth - 8F, CSGO.gameY.toFloat() + CSGO.gameHeight)
                            }

                            if (curSettings.bool["ENABLE_BOMB_TIMER"] && curSettings.bool["BOMB_TIMER_MENU"] && curSettings.bool["ENABLE_ESP"]) {
                                if (!menuStage.actors.contains(uiBombWindow)) {
                                    uiBombWindow.updateAlpha()
                                    menuStage.addActor(uiBombWindow)
                                }
                            } else if (menuStage.actors.contains(uiBombWindow)) {
                                menuStage.clear() //actors.remove at index doesnt work after 1 loop?
                            }

                            if (curSettings.bool["SPECTATOR_LIST"] && curSettings.bool["ENABLE_ESP"]) {
                                if (!menuStage.actors.contains(uiSpecList)) {
                                    uiSpecList.updateAlpha()
                                    menuStage.addActor(uiSpecList)
                                }
                            } else if (menuStage.actors.contains(uiSpecList)) {
                                menuStage.clear() //actors.remove at index doesnt work after 1 loop?
                            }
                        }, TimeUnit.NANOSECONDS)

                        sb.projectionMatrix = menuStage.camera.combined
                        shapeRenderer.projectionMatrix = menuStage.camera.combined
                        menuBatch.projectionMatrix = menuStage.camera.combined
                        uiMenu.changeAlpha()
                        appTime = TimeUnit.NANOSECONDS.convert(measureNanoTime {
                            updateViewMatrix()
                            if (!appless && !haltProcess) {
                                for (i in 0 until bodies.size) {
                                    bodies[i]()
                                }
                            }
                        }, TimeUnit.NANOSECONDS)

                        try { //Draw menu last, on top
                            if (menuBatch.isDrawing) {
                                menuBatch.end()
                            }
                            menuStage.act(Gdx.graphics.deltaTime)
                            menuStage.draw()
                        } catch(e: Exception) {
                            e.printStackTrace()
                        }

                        glFinish()
                    }, TimeUnit.NANOSECONDS)

                    if (dbg) { //Draw Debug
                        //Limit updates
                        if (timer >= curSettings.int["OPENGL_FPS"]/4) {
                            val runtime = Runtime.getRuntime()

                            val totalMem = runtime.totalMemory()
                            val freeMem = runtime.freeMemory()
                            val usedMem = totalMem - freeMem

                            val totalPhysMem = osBean.totalPhysicalMemorySize
                            val freePhysMem = osBean.freePhysicalMemorySize

                            val processLoad = osBean.processCpuLoad
                            val systemLoad = osBean.systemCpuLoad

                            sbText.clear()
                            sbText.append("In Game: ")
                            sbText.appendLine(inGame)
                            sbText.append("ShouldPostProcess: ")
                            sbText.appendLine(shouldPostProcess)
                            sbText.append("Me: ")
                            sbText.append(me)
                            sbText.append(" Dead: ")
                            sbText.appendLine(meDead)
                            sbText.append("Danger Zone: ")
                            sbText.appendLine(DANGER_ZONE)
                            sbText.append("Shots fired: ")
                            sbText.appendLine(me.shotsFired())
                            sbText.appendLine()
                            sbText.append("Total physical mem: ")
                            sbText.appendHumanReadableSize(totalPhysMem)
                            sbText.appendLine()
                            sbText.append("Free physical mem: ").appendHumanReadableSize(freePhysMem).appendLine()

                            sbText.append("Total allocated mem: ").appendHumanReadableSize(totalMem).appendLine()

                            sbText.append("Free allocated mem: ").appendHumanReadableSize(freeMem).appendLine()
                            sbText.append("Used allocated mem: ").appendHumanReadableSize(usedMem).appendLine()

                            sbText.append("\nProcess load: ").append((processLoad.toFloat() * 100F).roundNDecimals(2)).append("%")
                            sbText.append("\nSystem load: ").append((systemLoad.toFloat() * 100F).roundNDecimals(2)).append("%")

                            sbText.append("\n\nSync took: ").append((syncTime.toFloat() * 0.000001F).roundNDecimals(4)).append(" ms")
                            sbText.append("\nOverlay took: ").append((overlayTime.toFloat() * 0.000001F).roundNDecimals(4)).append(" ms")
                            sbText.append("\n   Menu took: ").append((menuTime.toFloat() * 0.000001F).roundNDecimals(4)).append(" ms")
                            sbText.append("\n   Apps took: ").append((appTime.toFloat() * 0.000001F).roundNDecimals(4)).append(" ms")
                            sbText.append("\n      Glow took: ").append((glowTime.toFloat() * 0.000001F).roundNDecimals(4)).append(" ms")
                            //sbText.append("\n      Bsp Vis Check took: ").append((bspVisTime.toFloat() * 0.000001F).roundNDecimals(4)).append(" ms")
                            timer = 0
                        }


                        try { //TODO fuck
                            if (sb.isDrawing) {
                                sb.end()
                            }

                            sb.begin()
                            glDisable(GL_BLEND)

                            textRenderer.color = Color.WHITE
                            sb.color = Color.WHITE
                            textRenderer.draw(sb, sbText, CSGO.gameWidth / 3F, CSGO.gameHeight - 100F)

                            sb.end()
                        } catch (e: Exception) {}
                    }
                }

                //Menu Key
                if (!appless) {
                    overlayMenuKey.update()
                    if (overlayMenuKey.justBecameTrue) {
                        MENUTOG = !MENUTOG
                        overlay.clickThrough = !MENUTOG

                        //uiMenu.updateChilds()
                        uiUpdate()

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
                    val w = overlay.width
                    val h = overlay.height

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
        overlay.stop()
    }

    operator fun invoke(body: App.() -> Unit) {
        bodies.add(body)
    }

    fun open() {
        overlay.listener = object : IOverlayListener {
            override fun onTargetAppWindowClosed(overlay: IOverlay) {
                haveTarget = false
                if (opened) {
                    uiMenu.closeMenu()
                }
            }

            override fun onAfterInit(overlay: IOverlay) {
                overlay.clickThrough = !appless
                overlay.protectAgainstScreenshots = false
                haveTarget = true
                updateDisableAim()
            }

            override fun onActive(overlay: IOverlay) {
                MENUTOG = true}
            override fun onPassive(overlay: IOverlay) {
                MENUTOG = false}
            override fun onBackground(overlay: IOverlay) {}
            override fun onForeground(overlay: IOverlay) {}
            override fun onBoundsChange(overlay: IOverlay, x: Int, y: Int, width: Int, height: Int) {}
        }
    }
}

var variableYieldTime = 0.toLong()
var lastSyncTime = 0.toLong()