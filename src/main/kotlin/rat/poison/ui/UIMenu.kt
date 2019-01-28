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
val aimTab = AimTab()
val scriptsTab = ScriptsTab()
val espTab = EspTab()
val rcsTab = RcsTab()
val bTrigTab = BTrig()
val misc = Misc()
val settings = Options()
val tabbedPane = TabbedPane()

class DebuggerWindow : VisWindow("RatPoison UI") {

    init {
        defaults().left()

        val tabbedPaneContent = VisTable()
        tabbedPaneContent.padTop(10F)
        tabbedPaneContent.padBottom(10F)
        tabbedPaneContent.align(Align.top)

        val scrollPane = ScrollPane(tabbedPaneContent)
        scrollPane.setFlickScroll(false)

        this.x = 960F
        this.y = 540F
        this.align(Align.topLeft)

        this.isResizable = true

        //Add tabs to tab-pane
        tabbedPane.add(aimTab)
        tabbedPane.add(scriptsTab)
        tabbedPane.add(espTab)
        tabbedPane.add(rcsTab)
        tabbedPane.add(bTrigTab)
        tabbedPane.add(misc)
        tabbedPane.add(settings)

        //Set default tab to first (aimKts)
        tabbedPane.switchTab(aimTab)

        tabbedPaneContent.add(aimTab.contentTable) //Aim.kts is the initial window, initialize pane content with tabs contents

        tabbedPane.addListener(object : TabbedPaneAdapter() {
            override fun switchedTab(tab: Tab?) {
                if (tab == null) return

                tabbedPaneContent.clear()
                when (tab) {
                    aimTab -> {
                        tabbedPaneContent.add(aimTab.contentTable)
                    }
                    settings -> {
                        tabbedPaneContent.add(settings.contentTable)
                    }
                    scriptsTab -> {
                        tabbedPaneContent.add(scriptsTab.contentTable)
                    }
                    rcsTab -> {
                        tabbedPaneContent.add(rcsTab.contentTable)
                    }
                    bTrigTab -> {
                        tabbedPaneContent.add(bTrigTab.contentTable)
                    }
                    espTab -> {
                        tabbedPaneContent.add(espTab.contentTable)
                    }
                    misc -> {
                        tabbedPaneContent.add(misc.contentTable)
                    }
                }
            }
        })

        add(tabbedPane.table).growX().minSize(25F).row()

        add(scrollPane).minSize(500F, 500F).align(Align.center)//.size(500F, 500F)

        pack()
        centerWindow()

        UIUpdate()
    }
}

