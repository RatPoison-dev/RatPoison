package rat.poison.ui

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.*
import rat.poison.App.uiAimOverridenWeapons
import rat.poison.opened
import rat.poison.ui.tabs.*
import kotlin.system.exitProcess

val mainTabbedPane = TabbedPane()
    val aimTab = AimTab()
    val visualsTab = VisualsTab()
    val rcsTab = RcsTab()
    val miscTab = MiscTab()
    val ranksTab = RanksTab()
    val nadeHelperTab = NadeHelperTab()
    val optionsTab = OptionsTab()


class UIMenu : VisWindow("Rat Poison 1.5.3.1") {
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
        mainScrollPane.setSize(1000F, 1000F)

        //Add tabs to the tab header
        mainTabbedPane.add(aimTab)
        mainTabbedPane.add(visualsTab)
        mainTabbedPane.add(rcsTab)
        mainTabbedPane.add(miscTab)
        mainTabbedPane.add(ranksTab)
        mainTabbedPane.add(nadeHelperTab)
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
                        mainTabbedPaneContent.add(aimTab.contentTable).growX()
                    }
                    optionsTab -> {
                        mainTabbedPaneContent.add(optionsTab.contentTable).growX()
                    }
                    rcsTab -> {
                        mainTabbedPaneContent.add(rcsTab.contentTable).growX()
                    }
                    visualsTab -> {
                        mainTabbedPaneContent.add(visualsTab.contentTable).growX()
                    }
                    miscTab -> {
                        mainTabbedPaneContent.add(miscTab.contentTable).growX()
                    }
                    ranksTab -> {
                        mainTabbedPaneContent.add(ranksTab.contentTable).growX()
                    }
                    nadeHelperTab -> {
                        mainTabbedPaneContent.add(nadeHelperTab.contentTable).growX()
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
}

