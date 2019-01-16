package rat.poison.ui

import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.*
import rat.poison.ui.tabs.*

//Issues/todo
//Remove libktx, not using it
//Solution for when str.length spacing //so-1 - try a divider between label and slider
//Massive cleanup
//Fix if(true) quickfix for return Any? problem -- might be autistic
//Add UI color colorpicker
//Never used rename to _ --too widespread atm, not an issue
//Fix use property access syntax --too widespread atm

//Add tooltips for EVERYTHING (im FUCKING SCRaeAMinG)

//A lot of problems/workarounds below, needs optimzation

////Marked for fix, enable esp needs to turn all the esps off

//Grow a massive penis

//1000 lines of FUCK, split tabs into new files?

//fuck cleanup release soon regardless

//Tabs, public to access in UIUpdate
val aimkts = AimKts()
val generalkts = GeneralKts()
val scriptskts = ScriptsKts()
val espkts = EspKts()
val misc = Misc()
val settings = Options()

class DebuggerWindow : VisWindow("RatPoison UI") {

    init {
        defaults().left()

        val tabbedPane = TabbedPane()
        val tabbedPaneContent = VisTable()

        this.x = 960F
        this.y = 540F

        //Add tabs to tab-pane
        tabbedPane.add(aimkts)
        tabbedPane.add(generalkts)
        tabbedPane.add(scriptskts)
        tabbedPane.add(espkts)
        tabbedPane.add(misc)
        tabbedPane.add(settings)

        //Set default tab to first (aimkts)
        tabbedPane.switchTab(aimkts)

        tabbedPaneContent.add(aimkts.contentTable) //Aim.kts is the initial window, initialize pane content with tabs contents

        tabbedPane.addListener(object : TabbedPaneAdapter() {
            override fun switchedTab(tab: Tab?) {
                if (tab == null) return

                when (tab) {
                    aimkts -> {
                        tabbedPaneContent.clear()
                        tabbedPaneContent.add(aimkts.contentTable)
                        tabbedPaneContent.setSize(500F, 500F)
                    }
                    generalkts -> {
                        tabbedPaneContent.clear()
                        tabbedPaneContent.add(generalkts.contentTable)
                        tabbedPaneContent.setSize(500F, 250F)

                    }
                    settings -> {
                        tabbedPaneContent.clear()
                        tabbedPaneContent.add(settings.contentTable)
                    }
                    scriptskts -> {
                        tabbedPaneContent.clear()
                        tabbedPaneContent.add(scriptskts.contentTable)
                    }
                    espkts -> {
                        tabbedPaneContent.clear()
                        tabbedPaneContent.add(espkts.contentTable)
                    }
                    misc -> {
                        tabbedPaneContent.clear()
                        tabbedPaneContent.add(misc.contentTable)
                    }
                }
            }
        })

        add(tabbedPane.table).growX().row()
        add(tabbedPaneContent).size(500F, 750F)

        //setColor(Color.RED)

        pack()
        centerWindow()
    }
}
//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA FUCK
//Expected a value of type Any? - am I autistic or what
//HOLY FUCK THIS A LOT OF SHIT




//Second cluster fuck below, couldnt think of a quick ez way to save settings without a pre-set format, put save button bs in another script/file later
//change from above, save all settings into a standard config folder



