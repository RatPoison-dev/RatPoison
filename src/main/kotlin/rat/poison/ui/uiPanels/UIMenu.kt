package rat.poison.ui.uiPanels

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisWindow
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPaneAdapter
import rat.poison.*
import rat.poison.game.CSGO
import rat.poison.scripts.sendPacket
import rat.poison.scripts.visuals.disableAllEsp
import rat.poison.ui.tabs.*
import rat.poison.ui.uiUpdate
import rat.poison.utils.randInt
import rat.poison.utils.shouldPostProcess
import kotlin.math.sign
import kotlin.system.exitProcess

val mainTabbedPane = TabbedPane()
    var aimTab = AimTab()
    var visualsTab = VisualsTab()
    var rcsTab = RcsTab()
    var miscTab = MiscTabs()
    var ranksTab = RanksTab()
    var nadeHelperTab = NadeHelperTab()
    var skinChangerTab = SkinChangerTab()
    var optionsTab = OptionsTab()
    var configsTab = ConfigsTab()

private var uid = 0//randInt(2, 999999)

class UIMenu : VisWindow("$TITLE $F_VERSION - [$M_VERSION $BRANCH] - $LOADED_CONFIG - UID: $uid") {
    val normHeight = 750F
    val normWidth = 950F

    //Changed through runtime
    var wantedHeight = normHeight
    var wantedWidth = normWidth

    //Main content pane for all tabs
    val mainTabbedPaneContent = VisTable(false)

    //Scroll pane for the content pane, content pane goes inside
    val mainScrollPane = ScrollPane(mainTabbedPaneContent) //Init scroll pane containing main content pane


    private var isResizingHeight = false
    private var isResizingWidth = false

    init {
        defaults().left()

        addCloseButton()

        mainTabbedPaneContent.padTop(10F)
        mainTabbedPaneContent.padBottom(10F)
        mainTabbedPaneContent.align(Align.top)

        mainScrollPane.setFlickScroll(false)
        mainScrollPane.setScrollbarsVisible(true)


        //Main ui window settings
        x = 960F
        y = 540F
        align(Align.topLeft)
        isResizable = true


        //Add tabs to the tab header
        mainTabbedPane.add(aimTab)
        mainTabbedPane.add(visualsTab)
        mainTabbedPane.add(rcsTab)
        mainTabbedPane.add(miscTab)
        mainTabbedPane.add(ranksTab)
        mainTabbedPane.add(nadeHelperTab)
        mainTabbedPane.add(skinChangerTab)
        mainTabbedPane.add(optionsTab)
        mainTabbedPane.add(configsTab)

        //Set aim tab as the first (init) tab
        mainTabbedPane.switchTab(aimTab)
        //Add aim tab content to the table
        mainTabbedPaneContent.add(aimTab.contentTable).left()

        //Tab switch listener
        mainTabbedPane.addListener(object : TabbedPaneAdapter() {
            override fun switchedTab(tab: Tab?) {
                if (tab == null) return

                mainTabbedPaneContent.clear()

                when (tab) { //Update table content to tab selected content
                    aimTab -> {
                        wantedHeight = normHeight
                        wantedWidth = normWidth
                        changeWidth()
                        changeHeight()
                        mainTabbedPaneContent.add(aimTab.contentTable).left()
                    }

                    optionsTab -> {
                        wantedHeight = normHeight
                        wantedWidth = normWidth
                        changeWidth()
                        changeHeight()
                        mainTabbedPaneContent.add(optionsTab.contentTable).growX().left()
                    }

                    rcsTab -> {
                        wantedHeight = normHeight
                        wantedWidth = normWidth
                        changeWidth()
                        changeHeight()
                        mainTabbedPaneContent.add(rcsTab.contentTable).growX().left()
                    }

                    visualsTab -> {
                        wantedHeight = normHeight
                        wantedWidth = normWidth
                        changeWidth()
                        changeHeight()
                        mainTabbedPaneContent.add(visualsTab.contentTable).growX().left()
                    }

                    miscTab -> {
                        wantedHeight = normHeight
                        wantedWidth = normWidth
                        changeWidth()
                        changeHeight()
                        mainTabbedPaneContent.add(miscTab.contentTable).growX().left()
                    }

                    ranksTab -> {
                        wantedHeight = normHeight
                        wantedWidth = normWidth
                        changeWidth()
                        changeHeight()
                        mainTabbedPaneContent.add(ranksTab.contentTable).growX().left()
                    }

                    nadeHelperTab -> {
                        wantedHeight = normHeight
                        wantedWidth = normWidth
                        changeWidth()
                        changeHeight()
                        mainTabbedPaneContent.add(nadeHelperTab.contentTable).growX().left()
                    }

                    configsTab -> {
                        wantedHeight = normHeight
                        wantedWidth = normWidth
                        changeWidth()
                        changeHeight()
                        mainTabbedPaneContent.add(configsTab.contentTable).growX().left()
                    }

                    skinChangerTab -> {
                        wantedHeight = if (CSGO.gameHeight < 1000F) {
                            CSGO.gameHeight.toFloat() - 100F
                        } else {
                            1000F
                        }
                        wantedHeight = when (appless) {
                            true -> normHeight
                            false -> if (CSGO.gameHeight < 1000F) {
                                CSGO.gameHeight.toFloat() - 100F
                            } else {
                                1000F
                            }
                        }
                        //wantedWidth = normWidth
                        //wantedHeight = normHeight
                        changeWidth()
                        changeHeight()
                        mainTabbedPaneContent.add(skinChangerTab.contentTable).growX()
                    }
                }

                Thread {
                    Thread.sleep(50)
                    uiUpdate()
                }.start()
            }
        })

        //Add tab pane & scroll pane to main ui window
        add(mainTabbedPane.table).growX().fillY().top().minHeight(30F).left().row()
        add(mainScrollPane).left().growX()
        pack()
        centerWindow()

        //Update all tab content
        uiUpdate()
        changeWidth()
        changeHeight()
    }

    //override fun positionChanged() {
    //    updateChilds()
    //}

    //fun updateChilds() {
    //    if (opened) {
    //        uiAimOverridenWeapons.setPosition(x, y + height - uiAimOverridenWeapons.height)
    //    }
    //}

    fun closeMenu() {
        haltProcess = true
        disableAllEsp()
        sendPacket(true)
        exitProcess(0)
    }


    public override fun close() {
        println("Close button pressed. Unloading...")
        closeMenu()
    }

    private val defaultAlpha by lazy(LazyThreadSafetyMode.NONE) {
        curSettings.float["MENU_ALPHA"]
    }

    internal fun changeAlpha(alpha: Float = defaultAlpha) {
        color.a = alpha
    }

    fun changeHeight() {
        if (!isResizingHeight) {
            isResizingHeight = true
            Thread {
                while (true) {
                    val difHeight = wantedHeight - height
                    val dChange = sign(difHeight) * 2

                    if (height in wantedHeight - 4..wantedHeight + 4) {
                        isResizingHeight = false
                        break
                    }

                    height += dChange
                    y -= dChange
                    Thread.sleep(1)
                }
            }.start()
        }
    }

    fun changeWidth() {
        if (!isResizingWidth) {
            isResizingWidth = true
            Thread {
                while (true) {
                    val difWidth = wantedWidth - width
                    val dChange = sign(difWidth)

                    if (width in wantedWidth - 4..wantedWidth + 4) {
                        isResizingWidth = false
                        break
                    }

                    width += dChange * 2
                    //x -= dChange*2
                    Thread.sleep(1)
                }
            }.start()
        }
    }

    fun updateTitle() {
        titleLabel.setText("$TITLE $F_VERSION - [$M_VERSION $BRANCH] - $LOADED_CONFIG - UID: $uid")
    }
}

