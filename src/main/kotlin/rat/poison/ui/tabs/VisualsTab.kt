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
import rat.poison.*
import rat.poison.scripts.esp.disableEsp
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
    private var col : rat.poison.game.Color

    //Init labels/sliders/boxes that show values here
    val enableEsp = VisCheckBox("Enable Esp") //ESP

    private val visualsKeyField = VisValidatableTextField(Validators.FLOATS)
    val visualsToggleKeyField = VisValidatableTextField(Validators.FLOATS)

    val radarEsp = VisCheckBox("Radar Esp")

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
        //Create curSettings["ENABLE_ESP"]!!.strToBool() Toggle
        Tooltip.Builder("Whether or not to enable esp").target(enableEsp).build()
        enableEsp.isChecked = curSettings["ENABLE_ESP"]!!.strToBool()
        enableEsp.changed { _, _ ->
            curSettings["ENABLE_ESP"] = enableEsp.isChecked.boolToStr()

            if (!curSettings["ENABLE_ESP"]!!.strToBool()) {
                disableEsp()
            }
            true
        }

        //Create Visuals_Toggle_Key Input
        val visualsToggleKey = VisTable()
        Tooltip.Builder("The key code that will toggle all enabled visuals on or off").target(visualsToggleKey).build()
        val visualsToggleKeyLabel = VisLabel("Visuals Toggle Key: ")
        visualsToggleKeyField.text = curSettings["VISUALS_TOGGLE_KEY"].toString()
        visualsToggleKey.changed { _, _ ->
            if (visualsKeyField.text.toIntOrNull() != null) {
                curSettings["VISUALS_TOGGLE_KEY"] = visualsToggleKeyField.text.toInt().toString()
            }
        }
        visualsToggleKey.add(visualsToggleKeyLabel)
        visualsToggleKey.add(visualsToggleKeyField).spaceRight(6F).width(40F)
        visualsToggleKey.add(LinkLabel("?", "http://cherrytree.at/misc/vk.htm"))

        //Create Radar Esp Toggle
        Tooltip.Builder("Whether or not to view the enemy team on the radar").target(radarEsp).build()
        if (curSettings["RADAR_ESP"]!!.strToBool()) radarEsp.toggle()
        radarEsp.changed { _, _ ->
            curSettings["RADAR_ESP"] = radarEsp.isChecked.boolToStr()
            true
        }

        //Create Team_Color Picker
        val teamColor = VisTable()
        Tooltip.Builder("The esp color of teammates").target(teamColor).build()
        col = curSettings["TEAM_COLOR"]!!.strToColor()
        teamColorShow.setColor(col.red.toFloat(), col.green.toFloat(), col.blue.toFloat(), 1F)
        val teamColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                curSettings["TEAM_COLOR"] = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
                newCol.a = 1F
                teamColorShow.color = newCol
                dispose()
            }

            override fun canceled(oldCol: Color) {
                dispose()
            }
        })

        teamColor.changed { _, _ ->
            App.menuStage.addActor(teamColorPicker.fadeIn())
        }
        teamColorPicker.color = Color(col.red.toFloat(), col.green.toFloat(), col.blue.toFloat(), 1F)

        teamColor.add(teamColorShow).growX()

        //Create Enemy_Color Picker
        val enemyColor = VisTable()
        Tooltip.Builder("The esp color of enemies").target(enemyColor).build()
        col = curSettings["ENEMY_COLOR"]!!.strToColor()
        enemyColorShow.setColor(col.red.toFloat(), col.green.toFloat(), col.blue.toFloat(), 1F)
        val enemyColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                curSettings["ENEMY_COLOR"] = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
                newCol.a = 1F
                enemyColorShow.color = newCol
                dispose()
            }

            override fun canceled(oldCol: Color) {
                dispose()
            }
        })
        enemyColorPicker.color = Color(col.red.toFloat(), col.green.toFloat(), col.blue.toFloat(), 1F)

        //enemyColorPicker
        enemyColor.changed { _, _ ->
            App.menuStage.addActor(enemyColorPicker.fadeIn())
        }

        enemyColor.add(enemyColorShow).growX()

        //Create Bomb_Color Picker
        val bombColor = VisTable()
        Tooltip.Builder("The esp color of the bomb").target(bombColor).build()
        col = curSettings["BOMB_COLOR"]!!.strToColor()
        bombColor.setColor(col.red.toFloat(), col.green.toFloat(), col.blue.toFloat(), 1F)
        val bombColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                curSettings["BOMB_COLOR"] = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
                newCol.a = 1F
                bombColorShow.color = newCol
                dispose()
            }

            override fun canceled(oldCol: Color) {
                dispose()
            }
        })
        bombColorPicker.color = Color(col.red.toFloat(), col.green.toFloat(), col.blue.toFloat(), 1F)

        bombColor.changed { _, _ ->
            App.menuStage.addActor(bombColorPicker.fadeIn())
        }

        bombColor.add(bombColorShow).growX()

        //Create Weapon_Color Picker
        val weaponColor = VisTable()
        Tooltip.Builder("The esp color of weapons").target(weaponColor).build()
        col = curSettings["WEAPON_COLOR"]!!.strToColor()
        weaponColor.setColor(col.red.toFloat(), col.green.toFloat(), col.blue.toFloat(), 1F)
        val weaponColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                curSettings["WEAPON_COLOR"] = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
                newCol.a = 1F
                weaponColorShow.color = newCol
                dispose()
            }

            override fun canceled(oldCol: Color) {
                dispose()
            }
        })
        weaponColorPicker.color = Color(col.red.toFloat(), col.green.toFloat(), col.blue.toFloat(), 1F)

        weaponColor.changed { _, _ ->
            App.menuStage.addActor(weaponColorPicker.fadeIn())
        }

        weaponColor.add(weaponColorShow).growX()

        //Create Grenade_Color Picker
        val grenadeColor = VisTable()
        col = curSettings["GRENADE_COLOR"]!!.strToColor()
        grenadeColor.setColor(col.red.toFloat(), col.green.toFloat(), col.blue.toFloat(), 1F)
        Tooltip.Builder("The esp color of grenades").target(grenadeColor).build()
        val grenadeColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                curSettings["GRENADE_COLOR"] = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
                newCol.a = 1F
                grenadeColorShow.color = newCol
                dispose()
            }

            override fun canceled(oldCol: Color) {
                dispose()
            }
        })
        grenadeColorPicker.color = Color(col.red.toFloat(), col.green.toFloat(), col.blue.toFloat(), 1F)

        grenadeColor.changed { _, _ ->
            App.menuStage.addActor(grenadeColorPicker.fadeIn())
        }

        grenadeColor.add(grenadeColorShow).growX()

        //Create Highlight_Color Picker
        val highlightColor = VisTable()
        col = curSettings["HIGHLIGHT_COLOR"]!!.strToColor()
        highlightColor.setColor(col.red.toFloat(), col.green.toFloat(), col.blue.toFloat(), 1F)
        Tooltip.Builder("The esp color of highlighted entities that aim will target").target(highlightColor).build()
        val highlightColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                curSettings["HIGHLIGHT_COLOR"] = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
                newCol.a = 1F
                highlightColorShow.color = newCol
                dispose()
            }

            override fun canceled(oldCol: Color) {
                dispose()
            }
        })
        highlightColorPicker.color = Color(col.red.toFloat(), col.green.toFloat(), col.blue.toFloat(), 1F)

        highlightColor.changed { _, _ ->
            App.menuStage.addActor(highlightColorPicker.fadeIn())
        }

        highlightColor.add(highlightColorShow).growX()

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

//        espTabbedPaneContent.add(teamColor).right().pad(5F)
//        espTabbedPaneContent.add(enemyColor).left().pad(5F).row()
//        espTabbedPaneContent.add(bombColor).right().pad(5F)
//        espTabbedPaneContent.add(weaponColor).left().pad(5F).row()
//        espTabbedPaneContent.add(grenadeColor).right().pad(5F)
//        espTabbedPaneContent.add(highlightColor).left().pad(5F).row()

        espTabbedPaneContent.add(teamColor).colspan(1).growX().pad(2F)
        espTabbedPaneContent.add(enemyColor).colspan(1).growX().pad(2F).row()
        espTabbedPaneContent.add(bombColor).colspan(1).growX().pad(2F)
        espTabbedPaneContent.add(weaponColor).colspan(1).growX().pad(2F).row()
        espTabbedPaneContent.add(grenadeColor).colspan(1).growX().pad(2F)
        espTabbedPaneContent.add(highlightColor).colspan(1).growX().pad(2F).row()

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
                espTabbedPaneContent.add(teamColor).colspan(1).growX().pad(2F)
                espTabbedPaneContent.add(enemyColor).colspan(1).growX().pad(2F).row()
                espTabbedPaneContent.add(bombColor).colspan(1).growX().pad(2F)
                espTabbedPaneContent.add(weaponColor).colspan(1).growX().pad(2F).row()
                espTabbedPaneContent.add(grenadeColor).colspan(1).growX().pad(2F)
                espTabbedPaneContent.add(highlightColor).colspan(1).growX().pad(2F).row()
            }
        })

        //Add all items to label for tabbed pane content
        table.add(enableEsp).colspan(2).row()
        table.add(visualsToggleKey).row()
        table.add(radarEsp).row()
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