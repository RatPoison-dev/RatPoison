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
import rat.poison.overlay.App.uiAimOverridenWeapons
import rat.poison.overlay.opened
import rat.poison.scripts.sendPacket
import rat.poison.scripts.visuals.disableAllEsp
import rat.poison.ui.tabs.*
import rat.poison.ui.uiUpdate
import rat.poison.utils.randInt
import kotlin.math.sign
import kotlin.system.exitProcess

val mainTabbedPane = TabbedPane()
    var aimTab = AimTab()
    var visualsTab = VisualsTab()
    var rcsTab = RcsTab()
    var miscTab = MiscTab()
    var ranksTab = RanksTab()
    var nadeHelperTab = NadeHelperTab()
    var skinChangerTab = SkinChangerTab()
    var optionsTab = OptionsTab()

private var uid = randInt(2, 999999)

class UIMenu : VisWindow("$TITLE $F_VERSION - [$M_VERSION $BRANCH] - $LOADED_CONFIG - UID: $uid") {
    var wantedHeight = 565F
    var wantedWidth = 535F
    val normHeight = 565F //Fuck you too
    val normWidth = 535F
    private var isResizingHeight = false
    private var isResizingWidth = false

    init {
        defaults().left()

        addCloseButton()

        //Main ui window settings
        x = 960F
        y = 540F
        align(Align.topLeft)
        isResizable = true

        //Main content pane for all tabs
        val mainTabbedPaneContent = VisTable()
        mainTabbedPaneContent.padTop(10F)
        mainTabbedPaneContent.padBottom(10F)
        mainTabbedPaneContent.align(Align.top)

        //Scroll pane for the content pane, content pane goes inside
        val mainScrollPane = ScrollPane(mainTabbedPaneContent) //Init scroll pane containing main content pane
        mainScrollPane.setFlickScroll(false)
        mainScrollPane.setScrollbarsVisible(true)
        mainScrollPane.setSize(565F, 535F)

        //Add tabs to the tab header
        mainTabbedPane.add(aimTab)
        mainTabbedPane.add(visualsTab)
        mainTabbedPane.add(rcsTab)
        mainTabbedPane.add(miscTab)
        mainTabbedPane.add(ranksTab)
        mainTabbedPane.add(nadeHelperTab)
        mainTabbedPane.add(skinChangerTab)
        mainTabbedPane.add(optionsTab)

        //Set aim tab as the first (init) tab
        mainTabbedPane.switchTab(aimTab)
        //Add aim tab content to the table
        mainTabbedPaneContent.add(aimTab.contentTable).growX()

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
                        mainTabbedPaneContent.add(aimTab.contentTable).growX()
                    }
                    optionsTab -> {
                        wantedHeight = normHeight
                        wantedWidth = normWidth
                        changeWidth()
                        changeHeight()
                        mainTabbedPaneContent.add(optionsTab.contentTable).growX()
                    }
                    rcsTab -> {
                        wantedHeight = normHeight
                        wantedWidth = normWidth
                        changeWidth()
                        changeHeight()
                        mainTabbedPaneContent.add(rcsTab.contentTable).growX()
                    }
                    visualsTab -> {
                        wantedHeight = normHeight
                        wantedWidth = normWidth
                        changeWidth()
                        changeHeight()
                        mainTabbedPaneContent.add(visualsTab.contentTable).growX()
                    }
                    miscTab -> {
                        wantedHeight = normHeight
                        wantedWidth = normWidth
                        changeWidth()
                        changeHeight()
                        mainTabbedPaneContent.add(miscTab.contentTable).growX()
                    }
                    ranksTab -> {
                        wantedHeight = normHeight
                        wantedWidth = normWidth
                        changeWidth()
                        changeHeight()
                        mainTabbedPaneContent.add(ranksTab.contentTable).growX()
                    }
                    nadeHelperTab -> {
                        wantedHeight = normHeight
                        wantedWidth = normWidth
                        changeWidth()
                        changeHeight()
                        mainTabbedPaneContent.add(nadeHelperTab.contentTable).growX()
                    }
                    skinChangerTab -> {
                        wantedHeight = if (CSGO.gameHeight < 1000F) {
                            CSGO.gameHeight.toFloat() - 100F
                        } else {
                            1000F
                        }
                        wantedWidth = normWidth
                        changeWidth()
                        changeHeight()
                        mainTabbedPaneContent.add(skinChangerTab.contentTable).growX()
                    }
                }
            }
        })

        //Add tab pane & scroll pane to main ui window
        add(mainTabbedPane.table).growX().row()
        add(mainScrollPane).minSize(500F, 500F).prefSize(500F, 500F).align(Align.top).growY().row()
        pack()
        centerWindow()

        //Update all tab content
        uiUpdate()
        changeWidth()
        changeHeight()
    }

    override fun positionChanged() {
        updateChilds()
    }

    fun updateChilds() {
        if (opened) {
            uiAimOverridenWeapons.setPosition(x + width + 4F, y + height - uiAimOverridenWeapons.height)
        }
    }

    override fun close() {
        haltProcess = true
        disableAllEsp()
        sendPacket(true)
        exitProcess(0)
    }

    internal fun changeAlpha(alpha: Float = curSettings["MENU_ALPHA"].toFloat()) {
        color.a = alpha
        uiAimOverridenWeapons.color.a = alpha
    }

    fun changeHeight() {
        if (!isResizingHeight) {
            isResizingHeight = true
            Thread(Runnable {
                while (true) {
                    val difHeight = wantedHeight - height
                    val dChange = sign(difHeight)*2

                    if (height in wantedHeight - 4..wantedHeight + 4) {
                        isResizingHeight = false
                        break
                    }

                    height += dChange
                    y -= dChange
                    Thread.sleep(1)
                }
            }).start()
        }
    }

    fun changeWidth() {
        if (!isResizingWidth) {
            isResizingWidth = true
            Thread(Runnable {
                while (true) {
                    val difWidth = wantedWidth - width
                    val dChange = sign(difWidth)

                    if (width in wantedWidth - 4..wantedWidth + 4) {
                        isResizingWidth = false
                        break
                    }

                    width += 2*dChange

                    if (dChange > 0) { //This shit d u m b
                        x -= 1F
                    } else if (dChange < 0) {
                        x += 1F
                    }
                    //x -= dChange/2F
                    Thread.sleep(1)
                }
            }).start()
        }
    }

    fun updateTitle() {
        titleLabel.setText("$TITLE $F_VERSION - [$M_VERSION $BRANCH] - $LOADED_CONFIG - UID: $uid")
    }
}

