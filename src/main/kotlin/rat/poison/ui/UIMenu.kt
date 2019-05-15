package rat.poison.ui

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.*
import rat.poison.ui.tabs.*
import rat.poison.ui.tabs.esptabs.*

val mainTabbedPane = TabbedPane()
    val aimTab = AimTab()
    val visualsTab = VisualsTab()
    val rcsTab = RcsTab()
    val bTrigTab = BTrigTab()
    val miscTab = MiscTab()
    val settingsTab = OptionsTab()

class UIMenu : VisWindow("RatPoison UI") {
    init {
        defaults().left()

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
        mainScrollPane.setSize(1000F, 1000F)


        //Add tabs to the tab header
        mainTabbedPane.add(aimTab)
        mainTabbedPane.add(visualsTab)
        mainTabbedPane.add(rcsTab)
        mainTabbedPane.add(bTrigTab)
        mainTabbedPane.add(miscTab)
        mainTabbedPane.add(settingsTab)


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
                    settingsTab -> {
                        mainTabbedPaneContent.add(settingsTab.contentTable).growX()
                    }
                    rcsTab -> {
                        mainTabbedPaneContent.add(rcsTab.contentTable).growX()
                    }
                    bTrigTab -> {
                        mainTabbedPaneContent.add(bTrigTab.contentTable).growX()
                    }
                    visualsTab -> {
                        mainTabbedPaneContent.add(visualsTab.contentTable).growX()
                    }
                    miscTab -> {
                        mainTabbedPaneContent.add(miscTab.contentTable).growX()
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
        UIUpdate()
    }
}

