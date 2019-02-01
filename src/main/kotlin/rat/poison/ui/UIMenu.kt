package rat.poison.ui

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.*
import rat.poison.ui.tabs.*

//Issues/todo
//Solution for when str.length spacing //so-1 - try a divider between label and slider
//Massive cleanup
//Fix if(true) quickfix for return Any? problem -- might be autistic
//Add UI color colorpicker
//Never used rename to _ --too widespread atm, not an issue
//Fix use property access syntax --too widespread atm

//Add tooltips for EVERYTHING (im FUCKING SCRaeAMinG)

//A lot of problems/workarounds below, needs optimzation

////Marked for fix, enable esp needs to turn all the esps off

//Tabs, public to access in UIUpdate
val mainTabbedPane = TabbedPane()
    val aimTab = AimTab()
    val visualsTab = VisualsTab()
    val rcsTab = RcsTab()
    val bTrigTab = BTrig()
    val misc = Misc()
    val settings = Options()

class DebuggerWindow : VisWindow("RatPoison UI") {
    init {
        defaults().left()


        val mainTabbedPaneContent = VisTable()
        mainTabbedPaneContent.padTop(10F)
        mainTabbedPaneContent.padBottom(10F)
        mainTabbedPaneContent.align(Align.top)

        val mainScrollPane = ScrollPane(mainTabbedPaneContent)
        mainScrollPane.setFlickScroll(false)
        mainScrollPane.setSize(1000F, 1000F)

        this.x = 960F
        this.y = 540F
        this.align(Align.topLeft)

        this.isResizable = false

        //Add tabs to main tab-pane
        mainTabbedPane.add(aimTab)
        mainTabbedPane.add(visualsTab)
        mainTabbedPane.add(rcsTab)
        mainTabbedPane.add(bTrigTab)
        mainTabbedPane.add(misc)
        mainTabbedPane.add(settings)

        //Set default tab to first (aimTab)
        mainTabbedPane.switchTab(aimTab)

        //mainTabbedPaneContent.add(aimTab.contentTable) //Aim.kts is the initial window, initialize pane content with tabs contents
        mainTabbedPaneContent.add(aimTab.contentTable).growX()


        mainTabbedPane.addListener(object : TabbedPaneAdapter() {
            override fun switchedTab(tab: Tab?) {
                if (tab == null) return

                mainTabbedPaneContent.clear()

                when (tab) {
                    aimTab -> {
                        mainTabbedPaneContent.add(aimTab.contentTable).growX()
                    }
                    settings -> {
                        mainTabbedPaneContent.add(settings.contentTable).growX()
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
                    misc -> {
                        mainTabbedPaneContent.add(misc.contentTable).growX()
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

