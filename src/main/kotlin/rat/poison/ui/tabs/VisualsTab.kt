package rat.poison.ui.tabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.util.Validators
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

    val espTabbedPane = TabbedPane()
    val glowEspTab = GlowEspTab()
    val chamsEspTab = ChamsEspTab()
    val indicatorEspTab = IndicatorEspTab()
    val boxEspTab = BoxEspTab()
    val skeletonEspTab = SkeletonEspTab()

class VisualsTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val enableEsp = VisCheckBox("Enable Esp") //ESP

    val fireKeyField = VisValidatableTextField(Validators.FLOATS)
    val visualsToggleKeyField = VisValidatableTextField(Validators.FLOATS)

    val showTeam = VisCheckBox("Show Team") //Show_Team
    val showEnemies = VisCheckBox("Show Enemies") //Show_Enemies
    val showDormant = VisCheckBox("Show Dormant") //Show_Dormant
    val showBomb = VisCheckBox("Show Bomb") //Show_Bomb
    val showWeapons = VisCheckBox("Show Weapons") //Show_Weapons
    val showGrenades = VisCheckBox("Show Grenades") //Show_Grenades
    val showTarget = VisCheckBox("Show Target") //Show_Target

    val teamColorShow = VisTextButton("Set Team Color") //Team_Color
    val enemyColorShow = VisTextButton("Set Enemy Color") //Enemy_Color
    val bombColorShow = VisTextButton("Set Bomb Color") //Bomb_Color
    val weaponColorShow = VisTextButton("Set Weapon Color") //Weapon_Color
    val grenadeColorShow = VisTextButton("Set Grenade Color") //Grenade_Color
    val highlightColorShow = VisTextButton("Set Highlight Color") //Highlight_Color


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

        //Create Visuals_Toggle_Key Input
        val visualsToggleKey = VisTable()
        Tooltip.Builder("The key code that will toggle all enabled visuals on or off").target(visualsToggleKey).build()
        val visualsToggleKeyLabel = VisLabel("Visuals Toggle Key: ")
        visualsToggleKeyField.text = VISUALS_TOGGLE_KEY.toString()
        visualsToggleKey.changed { _, _ ->
            if (fireKeyField.text.toIntOrNull() != null) {
                VISUALS_TOGGLE_KEY = visualsToggleKeyField.text.toInt()
            }
        }
        visualsToggleKey.add(visualsToggleKeyLabel)
        visualsToggleKey.add(visualsToggleKeyField).spaceRight(6F).width(40F)
        visualsToggleKey.add(LinkLabel("?", "http://cherrytree.at/misc/vk.htm"))

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
            App.menuStage.addActor(teamColorPicker.fadeIn())
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
            App.menuStage.addActor(enemyColorPicker.fadeIn())
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
            App.menuStage.addActor(bombColorPicker.fadeIn())
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
            App.menuStage.addActor(weaponColorPicker.fadeIn())
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
            App.menuStage.addActor(grenadeColorPicker.fadeIn())
        }

        grenadeColor.add(grenadeColorShow)

        //Create Highlight_Color Picker
        val highlightColor = VisTable()
        highlightColor.setColor(HIGHLIGHT_COLOR.red.toFloat(), HIGHLIGHT_COLOR.green.toFloat(), HIGHLIGHT_COLOR.blue.toFloat(), 1F)
        Tooltip.Builder("The esp color of highlighted entities that aim will target").target(highlightColor).build()
        val highlightColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                HIGHLIGHT_COLOR = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
                newCol.a = 1F
                highlightColor.color = newCol
                dispose()
            }

            override fun canceled(oldCol: Color) {
                dispose()
            }
        })

        highlightColor.changed { _, _ ->
            App.menuStage.addActor(highlightColorPicker.fadeIn())
        }

        highlightColor.add(highlightColorShow)

        //Aim Tab
        espTabbedPane.add(glowEspTab)
        espTabbedPane.add(chamsEspTab)
        espTabbedPane.add(indicatorEspTab)
        espTabbedPane.add(boxEspTab)
        espTabbedPane.add(skeletonEspTab)

        espTabbedPane.switchTab(glowEspTab)

        val espTabbedPaneContent = VisTable()
        espTabbedPaneContent.padTop(10F)
        espTabbedPaneContent.padBottom(10F)
        espTabbedPaneContent.align(Align.top)
        espTabbedPaneContent.columnDefaults(2)

        espTabbedPaneContent.add(glowEspTab.contentTable).colspan(2).growX().row()

        espTabbedPaneContent.addSeparator().colspan(2).growX().row()

        espTabbedPaneContent.add(teamColor).right().pad(5F)
        espTabbedPaneContent.add(enemyColor).left().pad(5F).row()
        espTabbedPaneContent.add(bombColor).right().pad(5F)
        espTabbedPaneContent.add(weaponColor).left().pad(5F).row()
        espTabbedPaneContent.add(grenadeColor).right().pad(5F)
        espTabbedPaneContent.add(highlightColor).left().pad(5F).row()

        espTabbedPane.addListener(object : TabbedPaneAdapter() {
            override fun switchedTab(tab: Tab?) {
                if (tab == null) return

                espTabbedPaneContent.clear()

                when (tab) {
                    glowEspTab -> {
                        espTabbedPaneContent.add(glowEspTab.contentTable).colspan(2).growX().row()
                    }
                    chamsEspTab -> {
                        espTabbedPaneContent.add(chamsEspTab.contentTable).colspan(2).growX().row()
                    }
                    indicatorEspTab -> {
                        espTabbedPaneContent.add(indicatorEspTab.contentTable).colspan(2).growX().row()
                    }
                    boxEspTab -> {
                        espTabbedPaneContent.add(boxEspTab.contentTable).colspan(2).growX().row()
                    }
                    skeletonEspTab -> {
                        espTabbedPaneContent.add(skeletonEspTab.contentTable).colspan(2).growX().row()
                    }
                }

                espTabbedPaneContent.addSeparator().colspan(2).row()
                espTabbedPaneContent.add(teamColor).right().pad(5F)
                espTabbedPaneContent.add(enemyColor).left().pad(5F).row()
                espTabbedPaneContent.add(bombColor).right().pad(5F)
                espTabbedPaneContent.add(weaponColor).left().pad(5F).row()
                espTabbedPaneContent.add(grenadeColor).right().pad(5F)
                espTabbedPaneContent.add(highlightColor).left().pad(5F).row()
            }
        })

        espTabbedPane.disableTab(indicatorEspTab, true)

        //Add all items to label for tabbed pane content
        table.add(enableEsp).colspan(2).row()
        table.add(visualsToggleKey).row()
        table.add(espTabbedPane.table).growX().minSize(25F).row()
        table.add(espTabbedPaneContent).growX()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Visuals"
    }
}