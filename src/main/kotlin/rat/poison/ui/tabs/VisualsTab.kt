package rat.poison.ui.tabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.color.ColorPicker
import com.kotcrab.vis.ui.widget.color.ColorPickerAdapter
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPaneAdapter
import rat.poison.App
import rat.poison.scripts.esp.disableEsp
import rat.poison.settings.*
import rat.poison.ui.changed
import rat.poison.ui.tabs.esptabs.*

val aimTabbedPane = TabbedPane()
    val glowEspTab = GlowEspTab()
    val chamsEspTab = ChamsEspTab()
    val indicatorEspTab = IndicatorEspTab()
    val boxEspTab = BoxEspTab()
    val skeletonEspTab = SkeletonEspTab()

class VisualsTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val enableEsp = VisCheckBox("Enable Esp") //ESP

    val showTeam = VisCheckBox("Show Team") //Show_Team
    val showEnemies = VisCheckBox("Show Enemies") //Show_Enemies
    val showDormant = VisCheckBox("Show Dormant") //Show_Dormant
    val showBomb = VisCheckBox("Show Bomb") //Show_Bomb
    val showWeapons = VisCheckBox("Show Weapons") //Show_Weapons
    val showGrenades = VisCheckBox("Show Grenades") //Show_Grenades

    val teamColorShow = VisTextButton("Set Team Color") //Team_Color
    val enemyColorShow = VisTextButton("Set Enemy Color") //Enemy_Color
    val bombColorShow = VisTextButton("Set Bomb Color") //Bomb_Color
    val weaponColorShow = VisTextButton("Set Weapon Color") //Weapon_Color
    val grenadeColorShow = VisTextButton("Set Grenade Color") //Grenade_Color


    init {
        //Create Enable_Esp Toggle
        //val enableEsp = VisTextButton("ENABLE_ESP", "toggle")
        Tooltip.Builder("Whether or not to enable esp").target(enableEsp).build()
        enableEsp.isChecked = ENABLE_ESP
        enableEsp.changed { _, _ ->
            ENABLE_ESP = enableEsp.isChecked

            if (!ENABLE_ESP) {
                disableEsp()
            }
            true
        }

        //VisImage(Color) doesnt work??
        //Create Team_Color Picker
        val teamColor = VisTable()
        Tooltip.Builder("The esp color of teammates").target(teamColor).build()
        //val teamColorShow = VisTextButton("Set Team Color")
        teamColorShow.setColor(TEAM_COLOR.red.toFloat(), TEAM_COLOR.green.toFloat(), TEAM_COLOR.blue.toFloat(), 1F/*TEAM_COLOR.alpha.toFloat()*/)
        val teamColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                TEAM_COLOR = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
                newCol.a = 1F
                teamColorShow.color = newCol
                dispose()
            }

            override fun canceled(oldCol: Color) {
                dispose()
            }
        })

        teamColorShow.changed { _, _ ->
            App.stage.addActor(teamColorPicker.fadeIn())
        }

        teamColor.add(teamColorShow)

        //Create Enemy_Color Picker
        val enemyColor = VisTable()
        Tooltip.Builder("The esp color of enemies").target(enemyColor).build()
        //val enemyColorShow = VisTextButton("Set Enemy Color")
        enemyColorShow.setColor(ENEMY_COLOR.red.toFloat(), ENEMY_COLOR.green.toFloat(), ENEMY_COLOR.blue.toFloat(), 1F/*ENEMY_COLOR.alpha.toFloat()*/)
        val enemyColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                ENEMY_COLOR = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
                newCol.a = 1F
                enemyColorShow.color = newCol
                dispose()
            }

            override fun canceled(oldCol: Color) {
                dispose()
            }
        })

        //enemyColorPicker

        enemyColorShow.changed { _, _ ->
            App.stage.addActor(enemyColorPicker.fadeIn())
        }

        enemyColor.add(enemyColorShow)

        //Create Bomb_Color Picker
        val bombColor = VisTable()
        Tooltip.Builder("The esp color of the bomb").target(bombColor).build()
        //val bombColorShow = VisTextButton("Set Bomb Color")
        bombColorShow.setColor(BOMB_COLOR.red.toFloat(), BOMB_COLOR.green.toFloat(), BOMB_COLOR.blue.toFloat(), 1F/*BOMB_COLOR.alpha.toFloat()*/)
        val bombColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                BOMB_COLOR = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
                newCol.a = 1F
                bombColorShow.color = newCol
                dispose()
            }

            override fun canceled(oldCol: Color) {
                dispose()
            }
        })

        bombColorShow.changed { _, _ ->
            App.stage.addActor(bombColorPicker.fadeIn())
        }

        bombColor.add(bombColorShow)

        //Create Weapon_Color Picker
        val weaponColor = VisTable()
        Tooltip.Builder("The esp color of weapons").target(weaponColor).build()
        //val weaponColorShow = VisTextButton("Set Weapon Color")
        weaponColorShow.setColor(WEAPON_COLOR.red.toFloat(), WEAPON_COLOR.green.toFloat(), WEAPON_COLOR.blue.toFloat(), 1F/*WEAPON_COLOR.alpha.toFloat()*/)
        val weaponColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                WEAPON_COLOR = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
                newCol.a = 1F
                weaponColorShow.color = newCol
                dispose()
            }

            override fun canceled(oldCol: Color) {
                dispose()
            }
        })

        weaponColorShow.changed { _, _ ->
            App.stage.addActor(weaponColorPicker.fadeIn())
        }

        weaponColor.add(weaponColorShow)

        //Create Grenade_Color Picker
        val grenadeColor = VisTable()
        grenadeColor.setColor(GRENADE_COLOR.red.toFloat(), GRENADE_COLOR.green.toFloat(), GRENADE_COLOR.blue.toFloat(), 1F/*GRENADE_COLOR.alpha.toFloat()*/)
        Tooltip.Builder("The esp color of grenades").target(grenadeColor).build()
        val grenadeColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                GRENADE_COLOR = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
                newCol.a = 1F
                grenadeColor.color = newCol
                dispose()
            }

            override fun canceled(oldCol: Color) {
                dispose()
            }
        })

        grenadeColor.changed { _, _ ->
            App.stage.addActor(grenadeColorPicker.fadeIn())
        }

        grenadeColor.add(grenadeColorShow)

        //Aim Tab
        aimTabbedPane.add(glowEspTab)
        aimTabbedPane.add(chamsEspTab)
        aimTabbedPane.add(indicatorEspTab)
        aimTabbedPane.add(boxEspTab)
        aimTabbedPane.add(skeletonEspTab)

        aimTabbedPane.switchTab(glowEspTab)

        val aimTabbedPaneContent = VisTable()
        aimTabbedPaneContent.padTop(10F)
        aimTabbedPaneContent.padBottom(10F)
        aimTabbedPaneContent.align(Align.top)
        aimTabbedPaneContent.columnDefaults(2)

        aimTabbedPaneContent.add(glowEspTab.contentTable).colspan(2).growX().row()

        aimTabbedPaneContent.addSeparator().colspan(2).growX().row()

        aimTabbedPaneContent.add(teamColor).right().pad(5F)
        aimTabbedPaneContent.add(enemyColor).left().pad(5F).row()
        aimTabbedPaneContent.add(bombColor).right().pad(5F)
        aimTabbedPaneContent.add(weaponColor).left().pad(5F).row()
        aimTabbedPaneContent.add(grenadeColor).colspan(2).pad(5F).row()

        aimTabbedPane.addListener(object : TabbedPaneAdapter() {
            override fun switchedTab(tab: Tab?) {
                if (tab == null) return

                aimTabbedPaneContent.clear()

                when (tab) {
                    glowEspTab -> {
                        aimTabbedPaneContent.add(glowEspTab.contentTable).colspan(2).growX().row()
                    }
                    chamsEspTab -> {
                        aimTabbedPaneContent.add(chamsEspTab.contentTable).colspan(2).growX().row()
                    }
                    indicatorEspTab -> {
                        aimTabbedPaneContent.add(indicatorEspTab.contentTable).colspan(2).growX().row()
                    }
                    boxEspTab -> {
                        aimTabbedPaneContent.add(boxEspTab.contentTable).colspan(2).growX().row()
                    }
                    skeletonEspTab -> {
                        aimTabbedPaneContent.add(skeletonEspTab.contentTable).colspan(2).growX().row()
                    }
                }

                aimTabbedPaneContent.addSeparator().colspan(2).row()
                aimTabbedPaneContent.add(teamColor).right().pad(5F)
                aimTabbedPaneContent.add(enemyColor).left().pad(5F).row()
                aimTabbedPaneContent.add(bombColor).right().pad(5F)
                aimTabbedPaneContent.add(weaponColor).left().pad(5F).row()
                aimTabbedPaneContent.add(grenadeColor).colspan(2).pad(5F).row()
            }
        })
        //Aim Tab


        //Add all items to label for tabbed pane content
        table.add(enableEsp).colspan(2).row()
        table.add(aimTabbedPane.table).growX().minSize(25F).row()
        table.add(aimTabbedPaneContent).growX()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Visuals"
    }
}