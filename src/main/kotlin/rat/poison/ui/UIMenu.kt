package rat.poison.ui

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.*
import rat.poison.settings.ACTIVATE_FROM_FIRE_KEY
import rat.poison.settings.FORCE_AIM_KEY
import rat.poison.settings.TEAMMATES_ARE_ENEMIES
import rat.poison.ui.tabs.*
import rat.poison.ui.tabs.aimtabs.AimPistolTab
import rat.poison.ui.tabs.aimtabs.AimRifleTab
import rat.poison.ui.tabs.aimtabs.AimShotgunTab
import rat.poison.ui.tabs.aimtabs.AimSniperTab

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
    val scriptsTab = ScriptsTab()
    val espTab = EspTab()
    val rcsTab = RcsTab()
    val bTrigTab = BTrig()
    val misc = Misc()
    val settings = Options()

val aimTabbedPane = TabbedPane()
    val aimRifleTab = AimRifleTab()
    val aimPistolTab = AimPistolTab()
    val aimSniperTab = AimSniperTab()
    val aimShotgunTab = AimShotgunTab()

val activateFromFireKey = VisTextButton("ACTIVATE_FROM_FIRE_KEY", "toggle") //Activate_From_Fire_Key
val teammatesAreEnemies = VisTextButton("TEAMMATES_ARE_ENEMIES", "toggle") //Teammates_Are_Enemies
val forceAimKeyField = VisValidatableTextField(Validators.FLOATS) //Force_Aim_Key

class DebuggerWindow : VisWindow("RatPoison UI") {
    init {
        defaults().left()


        val mainTabbedPaneContent = VisTable()
        mainTabbedPaneContent.padTop(10F)
        mainTabbedPaneContent.padBottom(10F)
        mainTabbedPaneContent.align(Align.top)

        val aimTabbedPaneContent = VisTable()
        aimTabbedPaneContent.padTop(10F)
        aimTabbedPaneContent.padBottom(10F)
        mainTabbedPaneContent.align(Align.top)

        val mainScrollPane = ScrollPane(mainTabbedPaneContent)
        mainScrollPane.setFlickScroll(false)

        val aimTable = VisTable(true).padBottom(8F)
        //Create Activate_From_Fire_Key Toggle
        //val activateFromFireKey = VisTextButton("ACTIVATE_FROM_FIRE_KEY", "toggle")
        Tooltip.Builder("Activate aim if pressing predefined fire key").target(activateFromFireKey).build()
        if (ACTIVATE_FROM_FIRE_KEY) activateFromFireKey.toggle()
        activateFromFireKey.changed { _, _ ->
            if (true) {
                ACTIVATE_FROM_FIRE_KEY = activateFromFireKey.isChecked//!ACTIVATE_FROM_FIRE_KEY
            }
        }

        //Create Teammates_Are_Enemies Toggle
        //val teammatesAreEnemies = VisTextButton("TEAMMATES_ARE_ENEMIES", "toggle")
        Tooltip.Builder("Teammates will be treated as enemies").target(teammatesAreEnemies).build()
        if (TEAMMATES_ARE_ENEMIES) teammatesAreEnemies.toggle()
        teammatesAreEnemies.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic /fixl ater
                TEAMMATES_ARE_ENEMIES = teammatesAreEnemies.isChecked//!TEAMMATES_ARE_ENEMIES
            }
        }

        //Create Force_Aim_Key Input
        val forceAimKey = VisTable()
        Tooltip.Builder("The key to force lock onto any enemy inside aim fov").target(forceAimKey).build()
        val forceAimKeyLabel = VisLabel("Force Aim Key: ")
        //val forceAimKeyField = VisValidatableTextField(Validators.FLOATS)
        forceAimKeyField.text = FORCE_AIM_KEY.toString()
        forceAimKey.changed { _, _ ->
            if (forceAimKeyField.text.toIntOrNull() != null) {
                FORCE_AIM_KEY = forceAimKeyField.text.toInt()
            }
        }
        forceAimKey.add(forceAimKeyLabel)
        forceAimKey.add(forceAimKeyField).spaceRight(6F).width(40F)
        forceAimKey.add(LinkLabel("?", "http://cherrytree.at/misc/vk.htm"))

        aimTable.add(activateFromFireKey).row()
        aimTable.add(teammatesAreEnemies).row()
        aimTable.add(forceAimKey).row()

        this.x = 960F
        this.y = 540F
        this.align(Align.topLeft)

        this.isResizable = false

        //Add tabs to main tab-pane
        mainTabbedPane.add(aimTab)
        mainTabbedPane.add(scriptsTab)
        mainTabbedPane.add(espTab)
        mainTabbedPane.add(rcsTab)
        mainTabbedPane.add(bTrigTab)
        mainTabbedPane.add(misc)
        mainTabbedPane.add(settings)

        //Add tabs to aim tab-pane
        aimTabbedPane.add(aimRifleTab)
        aimTabbedPane.add(aimPistolTab)
        aimTabbedPane.add(aimSniperTab)
        aimTabbedPane.add(aimShotgunTab)

        //Set default tab to first (aimTab)
        mainTabbedPane.switchTab(aimTab)
        //Set default tab to first (aimGeneralTab)
        aimTabbedPane.switchTab(aimRifleTab)





        aimTabbedPaneContent.add(aimRifleTab.contentTable)

        //mainTabbedPaneContent.add(aimTab.contentTable) //Aim.kts is the initial window, initialize pane content with tabs contents
        mainTabbedPaneContent.add(aimTable).row()
        mainTabbedPaneContent.add(aimTabbedPane.table).growX().minSize(25F).row()
        mainTabbedPaneContent.add(aimTabbedPaneContent)


        mainTabbedPane.addListener(object : TabbedPaneAdapter() {
            override fun switchedTab(tab: Tab?) {
                if (tab == null) return

                mainTabbedPaneContent.clear()

                when (tab) {
                    aimTab -> {
                        mainTabbedPaneContent.add(aimTable).row()
                        mainTabbedPaneContent.add(aimTabbedPane.table).growX().minSize(25F).row()
                        mainTabbedPaneContent.add(aimTabbedPaneContent)
                    }
                    settings -> {
                        mainTabbedPaneContent.add(settings.contentTable)
                    }
                    scriptsTab -> {
                        mainTabbedPaneContent.add(scriptsTab.contentTable)
                    }
                    rcsTab -> {
                        mainTabbedPaneContent.add(rcsTab.contentTable)
                    }
                    bTrigTab -> {
                        mainTabbedPaneContent.add(bTrigTab.contentTable)
                    }
                    espTab -> {
                        mainTabbedPaneContent.add(espTab.contentTable)
                    }
                    misc -> {
                        mainTabbedPaneContent.add(misc.contentTable)
                    }
                }
            }
        })

        aimTabbedPane.addListener(object : TabbedPaneAdapter() {
            override fun switchedTab(tab: Tab?) {
                if (tab == null) return

                aimTabbedPaneContent.clear()

                when (tab) {
                    aimRifleTab -> {
                        aimTabbedPaneContent.add(aimRifleTab.contentTable)
                    }

                    aimPistolTab -> {
                        aimTabbedPaneContent.add(aimPistolTab.contentTable)
                    }

                    aimSniperTab -> {
                        aimTabbedPaneContent.add(aimSniperTab.contentTable)
                    }

                    aimShotgunTab -> {
                        aimTabbedPaneContent.add(aimShotgunTab.contentTable)
                    }
                }
            }
        })

        add(mainTabbedPane.table).growX().minSize(25F).row()

        add(mainScrollPane).minSize(500F, 500F).align(Align.center)

        pack()
        centerWindow()

        UIUpdate()
    }
}

