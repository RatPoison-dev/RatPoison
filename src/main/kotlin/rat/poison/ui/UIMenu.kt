package rat.poison.ui

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisWindow
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPaneAdapter
import rat.poison.App.uiAimOverridenWeapons
import rat.poison.game.CSGO
import rat.poison.opened
import rat.poison.ui.tabs.*
import kotlin.math.sign
import kotlin.system.exitProcess

val mainTabbedPane = TabbedPane()
    val aimTab = AimTab()
    val visualsTab = VisualsTab()
    val rcsTab = RcsTab()
    val miscTab = MiscTab()
    val ranksTab = RanksTab()
    val nadeHelperTab = NadeHelperTab()
    val skinChangerTab = SkinChangerTab()
    val optionsTab = OptionsTab()

private var wantedHeight = 550F
private var isResizing = false

class UIMenu : VisWindow("Rat Poison 1.6") {
    init {
        defaults().left()

        addCloseButton()

        //Main ui window settings
        x = 960F
        y = 540F
        align(Align.topLeft)
        isResizable = false

        //Main content pane for all tabs
        val mainTabbedPaneContent = VisTable()
        mainTabbedPaneContent.padTop(10F)
        mainTabbedPaneContent.padBottom(10F)
        mainTabbedPaneContent.align(Align.top)

        //Scroll pane for the content pane, content pane goes inside
        val mainScrollPane = ScrollPane(mainTabbedPaneContent) //Init scroll pane containing main content pane
        mainScrollPane.setFlickScroll(false)
        mainScrollPane.setSize(500F, 500F)

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

                var normHeight = 565F //Fuck you too

                when (tab) { //Update table content to tab selected content
                    aimTab -> {
                        wantedHeight = normHeight
                        changeHeight()
                        mainTabbedPaneContent.add(aimTab.contentTable).growX()
                    }
                    optionsTab -> {
                        wantedHeight = normHeight
                        changeHeight()
                        mainTabbedPaneContent.add(optionsTab.contentTable).growX()
                    }
                    rcsTab -> {
                        wantedHeight = normHeight
                        changeHeight()
                        mainTabbedPaneContent.add(rcsTab.contentTable).growX()
                    }
                    visualsTab -> {
                        wantedHeight = normHeight
                        changeHeight()
                        mainTabbedPaneContent.add(visualsTab.contentTable).growX()
                    }
                    miscTab -> {
                        wantedHeight = normHeight
                        changeHeight()
                        mainTabbedPaneContent.add(miscTab.contentTable).growX()
                    }
                    ranksTab -> {
                        wantedHeight = normHeight
                        changeHeight()
                        mainTabbedPaneContent.add(ranksTab.contentTable).growX()
                    }
                    nadeHelperTab -> {
                        wantedHeight = normHeight
                        changeHeight()
                        mainTabbedPaneContent.add(nadeHelperTab.contentTable).growX()
                    }
                    skinChangerTab -> {
                        if (CSGO.gameHeight < 1000F) {
                            wantedHeight = CSGO.gameHeight.toFloat() - 100F
                        } else {
                            wantedHeight = 1000F
                        }
                        changeHeight()
                        mainTabbedPaneContent.add(skinChangerTab.contentTable).growX()
                    }
                }
            }
        })

        //Add tab pane & scroll pane to main ui window
        add(mainTabbedPane.table).growX().minSize(25F).row()
        add(mainScrollPane).minSize(500F, 500F).prefSize(500F, 500F).align(Align.left).growX().growY().row()
        pack()
        centerWindow()

        //Update all tab content
        uiUpdate()
    }

    override fun positionChanged() {
        updateChilds()
    }

    fun updateChilds() {
        if (opened) {
            uiAimOverridenWeapons.setPosition(x+width+4F, y)
        }
    }

    override fun close() {
        exitProcess(0)
    }

    internal fun changeAlpha(alpha: Float) {
        color.a = alpha
        uiAimOverridenWeapons.color.a = alpha
    }

    private fun changeHeight() {
        if (!isResizing) {
            isResizing = true
            Thread(Runnable {
                while (true) {
                    val difHeight = wantedHeight - height
                    val dChange = sign(difHeight)

                    if (height in wantedHeight - 4..wantedHeight + 4) {
                        isResizing = false
                        break
                    }

                    height += dChange
                    y -= dChange
                    Thread.sleep(1)
                }
            }).start()
        }
    }
}

