package rat.poison.ui

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.Align
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
val aimKts = AimKts()
val generalKts = GeneralKts()
val scriptsKts = ScriptsKts()
val espKts = EspKts()
val misc = Misc()
val settings = Options()

class DebuggerWindow : VisWindow("RatPoison UI") {

    init {
        defaults().left()

        val tabbedPane = TabbedPane()


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
        tabbedPane.add(aimKts)
        tabbedPane.add(generalKts)
        tabbedPane.add(scriptsKts)
        tabbedPane.add(espKts)
        tabbedPane.add(misc)
        tabbedPane.add(settings)

        //Set default tab to first (aimKts)
        tabbedPane.switchTab(aimKts)

        tabbedPaneContent.add(aimKts.contentTable) //Aim.kts is the initial window, initialize pane content with tabs contents

        tabbedPane.addListener(object : TabbedPaneAdapter() {
            override fun switchedTab(tab: Tab?) {
                if (tab == null) return

                when (tab) {
                    aimKts -> {
                        tabbedPaneContent.clear()
                        tabbedPaneContent.add(aimKts.contentTable)
                    }
                    generalKts -> {
                        tabbedPaneContent.clear()
                        tabbedPaneContent.add(generalKts.contentTable)

                    }
                    settings -> {
                        tabbedPaneContent.clear()
                        tabbedPaneContent.add(settings.contentTable)
                    }
                    scriptsKts -> {
                        tabbedPaneContent.clear()
                        tabbedPaneContent.add(scriptsKts.contentTable)
                    }
                    espKts -> {
                        tabbedPaneContent.clear()
                        tabbedPaneContent.add(espKts.contentTable)
                    }
                    misc -> {
                        tabbedPaneContent.clear()
                        tabbedPaneContent.add(misc.contentTable)
                    }
                }
            }
        })

        add(tabbedPane.table).growX().minSize(25F).row()

        add(scrollPane).minSize(500F, 500F).align(Align.center)//.size(500F, 500F)

        pack()
        centerWindow()
    }
}
//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA FUCK
//Expected a value of type Any? - am I autistic or what
//HOLY FUCK THIS A LOT OF SHIT




//Second cluster fuck below, couldnt think of a quick ez way to save settings without a pre-set format, put save button bs in another script/file later
//change from above, save all settings into a standard config folder



