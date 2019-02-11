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

        val mainTabbedPaneContent = VisTable()
        mainTabbedPaneContent.padTop(10F)
        mainTabbedPaneContent.padBottom(10F)
        mainTabbedPaneContent.align(Align.top)

        val mainScrollPane = ScrollPane(mainTabbedPaneContent)
        mainScrollPane.setFlickScroll(false)
        mainScrollPane.setSize(1000F, 1000F)

        val aimTabbedPaneContent = VisTable()
        aimTabbedPaneContent.padTop(10F)
        aimTabbedPaneContent.padBottom(10F)
        aimTabbedPaneContent.align(Align.top)

        this.x = 960F
        this.y = 540F
        this.align(Align.topLeft)

        this.isResizable = false

        mainTabbedPane.add(aimTab)
        mainTabbedPane.add(visualsTab)
        mainTabbedPane.add(rcsTab)
        mainTabbedPane.add(bTrigTab)
        mainTabbedPane.add(miscTab)
        mainTabbedPane.add(settingsTab)

        mainTabbedPane.switchTab(aimTab)

        mainTabbedPaneContent.add(aimTab.contentTable).growX()

        mainTabbedPane.addListener(object : TabbedPaneAdapter() {
            override fun switchedTab(tab: Tab?) {
                if (tab == null) return

                mainTabbedPaneContent.clear()

                when (tab) {
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

        add(mainTabbedPane.table).growX().minSize(25F).row()

        add(mainScrollPane).minSize(500F, 500F).align(Align.left).growX().row()

        pack()
        centerWindow()

        UIUpdate()
    }
}

