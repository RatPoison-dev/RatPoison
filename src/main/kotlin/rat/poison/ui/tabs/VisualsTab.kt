package rat.poison.ui.tabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
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
        //Static Visuals Tab Items
            val enableEsp = VisCheckBox("Enable Esp")

            val visualsToggleKeyLabel = VisLabel("Visuals Toggle Key: ")
            val visualsToggleKeyField = VisValidatableTextField(Validators.FLOATS)

            val radarEsp = VisCheckBox("Radar Esp")

            val teamColorShow = VisTextButton("Set Team Color") //Team Color
            val enemyColorShow = VisTextButton("Set Enemy Color") //Enemy Color
            val bombColorShow = VisTextButton("Set Bomb Color") //Bomb Color
            val weaponColorShow = VisTextButton("Set Weapon Color") //Weapon Color
            val grenadeColorShow = VisTextButton("Set Grenade Color") //Grenade Color
            val highlightColorShow = VisTextButton("Set Highlight Color") //Highlight Color


    init {
        //Create curSettings["ENABLE_ESP"]!!.strToBool() Toggle
        Tooltip.Builder("Whether or not to enable esp").target(enableEsp).build()
        enableEsp.isChecked = curSettings["ENABLE_ESP"]!!.strToBool()
        enableEsp.changed { _, _ ->
            curSettings["ENABLE_ESP"] = enableEsp.isChecked.boolToStr()

            val bool = !enableEsp.isChecked
            var col = Color(255F, 255F, 255F, 1F)
            if (bool) {
                col = Color(105F, 105F, 105F, .2F)
            }

            visualsToggleKeyLabel.color = col
            visualsToggleKeyField.isDisabled = bool
            radarEsp.isDisabled = bool

            val recTab = espTabbedPane.activeTab

            espTabbedPane.disableTab(glowEspTab, bool)
            espTabbedPane.disableTab(chamsEspTab, bool)
            espTabbedPane.disableTab(indicatorEspTab, bool)
            espTabbedPane.disableTab(boxEspTab, bool)
            espTabbedPane.disableTab(skeletonEspTab, bool)

            espTabbedPane.switchTab(recTab)

            glowEspTab.glowEsp.isDisabled = bool
            glowEspTab.invGlowEsp.isDisabled = bool
            glowEspTab.modelEsp.isDisabled = bool
            glowEspTab.modelAndGlow.isDisabled = bool

            glowEspTab.showTeam.isDisabled = bool
            glowEspTab.showEnemies.isDisabled = bool
            glowEspTab.showBomb.isDisabled = bool
            glowEspTab.showWeapons.isDisabled = bool
            glowEspTab.showGrenades.isDisabled = bool
            glowEspTab.showTarget.isDisabled = bool

            chamsEspTab.chamsEsp.isDisabled = bool
            chamsEspTab.chamsShowHealth.isDisabled = bool
            chamsEspTab.chamsBrightnessLabel.color = col
            chamsEspTab.chamsBrightnessSlider.isDisabled = bool

            chamsEspTab.showTeam.isDisabled = bool
            chamsEspTab.showEnemies.isDisabled = bool

            indicatorEspTab.indicatorEsp.isDisabled = bool
            indicatorEspTab.indicatorOnScreen.isDisabled = bool
            indicatorEspTab.indicatorOval.isDisabled = bool
            indicatorEspTab.indicatorDistanceLabel.color = col
            indicatorEspTab.indicatorDistanceSlider.isDisabled = bool

            indicatorEspTab.showTeam.isDisabled = bool
            indicatorEspTab.showEnemies.isDisabled = bool
            indicatorEspTab.showBomb.isDisabled = bool
            indicatorEspTab.showWeapons.isDisabled = bool
            indicatorEspTab.showGrenades.isDisabled = bool

            boxEspTab.boxEsp.isDisabled = bool
            boxEspTab.boxEspDetails.isDisabled = bool

            boxEspTab.showTeam.isDisabled = bool
            boxEspTab.showEnemies.isDisabled = bool

            skeletonEspTab.skeletonEsp.isDisabled = bool
            skeletonEspTab.showTeam.isDisabled = bool
            skeletonEspTab.showEnemies.isDisabled = bool

            teamColorShow.isDisabled = bool
            enemyColorShow.isDisabled = bool
            bombColorShow.isDisabled = bool
            weaponColorShow.isDisabled = bool
            grenadeColorShow.isDisabled = bool
            highlightColorShow.isDisabled = bool

            if (!curSettings["ENABLE_ESP"]!!.strToBool()) {
                disableEsp()
            }
            true
        }

        //Create Visuals_Toggle_Key Input
        val visualsToggleKey = VisTable()
        Tooltip.Builder("The key code that will toggle all enabled visuals on or off").target(visualsToggleKey).build()
        visualsToggleKeyField.text = curSettings["VISUALS_TOGGLE_KEY"]
        visualsToggleKey.changed { _, _ ->
            if (visualsToggleKeyField.text.toIntOrNull() != null) {
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

        //Create Team Color Picker
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

        //Create Enemy Color Picker
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

        enemyColor.changed { _, _ ->
            App.menuStage.addActor(enemyColorPicker.fadeIn())
        }

        enemyColor.add(enemyColorShow).growX()

        //Create Bomb Color Picker
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

        //Create Weapon Color Picker
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

        //Create Grenade Color Picker
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

        //Create Highlight Color Picker
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

        //ESP Tab
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
        espTabbedPaneContent.columnDefaults(1)
        //espTabbedPaneContent.setSize(1000F, 1000F)

        val espScrollPane = ScrollPane(espTabbedPaneContent)
        espScrollPane.setFlickScroll(false)
        espScrollPane.setSize(1000F, 1000F)

        espTabbedPaneContent.add(glowEspTab.contentTable).left().colspan(2).row()

        espTabbedPaneContent.addSeparator().colspan(2).padLeft(25F).padRight(25F)

        var colTable = VisTable()

        colTable.padLeft(25F)
        colTable.padRight(25F)

        colTable.add(teamColor).width(225F).left().pad(1F)
        colTable.add(enemyColor).width(225F).left().pad(1F).row()
        colTable.add(bombColor).width(225F).left().pad(1F)
        colTable.add(weaponColor).width(225F).left().pad(1F).row()
        colTable.add(grenadeColor).width(225F).left().pad(1F)
        colTable.add(highlightColor).width(225F).left().pad(1F).row()
        espTabbedPaneContent.add(colTable).colspan(2)

        espTabbedPane.addListener(object : TabbedPaneAdapter() {
            override fun switchedTab(tab: Tab?) {
                if (tab == null) return

                espTabbedPaneContent.clear()

                when (tab) {
                    glowEspTab -> {
                        espTabbedPaneContent.add(glowEspTab.contentTable).left().colspan(2).row()
                    }
                    chamsEspTab -> {
                        espTabbedPaneContent.add(chamsEspTab.contentTable).left().colspan(2).row()
                    }
                    indicatorEspTab -> {
                        espTabbedPaneContent.add(indicatorEspTab.contentTable).left().colspan(2).row()
                    }
                    boxEspTab -> {
                        espTabbedPaneContent.add(boxEspTab.contentTable).left().colspan(2).row()
                    }
                    skeletonEspTab -> {
                        espTabbedPaneContent.add(skeletonEspTab.contentTable).left().colspan(2).row()
                    }
                }

                espTabbedPaneContent.addSeparator().colspan(2).padLeft(25F).padRight(25F)

                colTable = VisTable()

                colTable.padLeft(25F)
                colTable.padRight(25F)

                colTable.add(teamColor).width(225F).left().pad(1F)
                colTable.add(enemyColor).width(225F).left().pad(1F).row()
                colTable.add(bombColor).width(225F).left().pad(1F)
                colTable.add(weaponColor).width(225F).left().pad(1F).row()
                colTable.add(grenadeColor).width(225F).left().pad(1F)
                colTable.add(highlightColor).width(225F).left().pad(1F).row()
                espTabbedPaneContent.add(colTable).colspan(2).left()
            }
        })

        //Add all items to label for tabbed pane content
        table.add(enableEsp).padLeft(25F).left().row()
        table.add(visualsToggleKey).padLeft(25F).left().row()
        table.add(radarEsp).padLeft(25F).left().row()
        table.add(espTabbedPane.table).minWidth(500F).left().row()
        table.add(espScrollPane).minSize(500F, 500F).prefSize(500F, 500F).align(Align.left).growX().growY().row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Visuals"
    }
}