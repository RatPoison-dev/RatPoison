package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.ui.tabs.indicatorEspTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class IndicatorEspTab : Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    val indicatorEsp = VisCheckBoxCustom("Entity Indicator", "INDICATOR_ESP")
    val indicatorOnScreen = VisCheckBoxCustom("Show Onscreen", "INDICATOR_SHOW_ONSCREEN")
    val indicatorOval = VisCheckBoxCustom("Oval", "INDICATOR_OVAL")
    val indicatorDistance = VisSliderCustom("Indicator Distance", "INDICATOR_DISTANCE", 2F, 25F, .1F, false)

    val showTeam = VisCheckBoxCustom("Show Team", "INDICATOR_SHOW_TEAM")
    val showEnemies = VisCheckBoxCustom("Show Enemies", "INDICATOR_SHOW_ENEMIES")
    val showBomb = VisCheckBoxCustom("Show Bomb", "INDICATOR_SHOW_BOMB")
    val showBombCarrier = VisCheckBoxCustom("Show Bomb Carrier", "INDICATOR_SHOW_BOMB_CARRIER")
    val showWeapons = VisCheckBoxCustom("Show Weapons", "INDICATOR_SHOW_WEAPONS")
    val showGrenades = VisCheckBoxCustom("Show Grenades", "INDICATOR_SHOW_GRENADES")
    val showDefusers = VisCheckBoxCustom("Show Defuse Kits", "INDICATOR_SHOW_DEFUSERS")

    val indicatorTeamColor = VisColorPickerCustom("Team Color", "GLOW_TEAM_COLOR")
    val indicatorEnemyColor = VisColorPickerCustom("Enemy Color", "INDICATOR_ENEMY_COLOR")
    val indicatorBombColor = VisColorPickerCustom("Bomb Color", "INDICATOR_BOMB_COLOR")
    val indicatorDefuserColor = VisColorPickerCustom("Defuser Color", "INDICATOR_DEFUSER_COLOR")
    val indicatorWeaponColor = VisColorPickerCustom("Weapon Color", "INDICATOR_WEAPON_COLOR")
    val indicatorGrenadeColor = VisColorPickerCustom("Grenade Color", "INDICATOR_GRENADE_COLOR")

    init {
        table.padLeft(25F)
        table.padRight(25F)

        val colTable = VisTable()
        val width = 225F
        colTable.add(indicatorTeamColor).width(width).padRight(2F)
        colTable.add(indicatorEnemyColor).width(width).row()
        colTable.add(indicatorBombColor).width(width).padRight(2F)
        colTable.add(indicatorDefuserColor).width(width).row()
        colTable.add(indicatorWeaponColor).width(width).padRight(2F)
        colTable.add(indicatorGrenadeColor).width(width).row()

        //Add all items to label for tabbed pane content
        table.add(indicatorEsp).left().row()
        table.add(indicatorOnScreen).left().row()
        table.add(indicatorOval).left().row()
        table.add(indicatorDistance).left().colspan(2).row()
        table.add(showTeam).left()
        table.add(showEnemies).left().row()
        table.add(showBomb).left()
        table.add(showBombCarrier).left().row()
        table.add(showWeapons).left()
        table.add(showGrenades).left().padRight(225F - showGrenades.width).row()
        table.add(showDefusers).left().padRight(225F - showDefusers.width).row()

        table.add(colTable).colspan(2).width(450F).left()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Indicator"
    }
}

fun indicatorEspTabUpdate() {
    indicatorEspTab.apply {
        indicatorEsp.update()
        indicatorOnScreen.update()
        indicatorOval.update()
        indicatorDistance.update()
        showTeam.update()
        showEnemies.update()
        showBomb.update()
        showBombCarrier.update()
        showWeapons.update()
        showGrenades.update()
        showDefusers.update()
        indicatorTeamColor.update()
        indicatorEnemyColor.update()
        indicatorBombColor.update()
        indicatorDefuserColor.update()
        indicatorWeaponColor.update()
        indicatorGrenadeColor.update()
    }
}