package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.toLocale
import rat.poison.ui.tabs.indicatorEspTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class IndicatorEspTab : Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    val indicatorEsp = VisCheckBoxCustom("Enable", "INDICATOR_ESP")
    val indicatorDistance = VisSliderCustom("Indicator Distance", "INDICATOR_DISTANCE", 2F, 50F, .1F, false)
    val indicatorSize = VisSliderCustom("Indicator Size", "INDICATOR_SIZE", 5F, 25F, .5F, false)

    val indicatorSmokeCheck = VisCheckBoxCustom("Smoke Check", "INDICATOR_SMOKE_CHECK")

    val showTeam = VisCheckBoxCustom(" ", "INDICATOR_SHOW_TEAM", false)
    val indicatorTeamColor = VisColorPickerCustom("Teammates", "GLOW_TEAM_COLOR")

    val showEnemies = VisCheckBoxCustom(" ", "INDICATOR_SHOW_ENEMIES", false)
    val indicatorEnemyColor = VisColorPickerCustom("Enemies", "INDICATOR_ENEMY_COLOR")

    val showBomb = VisCheckBoxCustom(" ", "INDICATOR_SHOW_BOMB", false)
    val indicatorBombColor = VisColorPickerCustom("Bomb", "INDICATOR_BOMB_COLOR")

    val showBombCarrier = VisCheckBoxCustom(" ", "INDICATOR_SHOW_BOMB_CARRIER", false)
    val indicatorBombCarrierColor = VisColorPickerCustom("Bomb Carrier", "INDICATOR_BOMB_CARRIER_COLOR")

    val showWeapons = VisCheckBoxCustom(" ", "INDICATOR_SHOW_WEAPONS", false)
    val indicatorWeaponColor = VisColorPickerCustom("Weapons", "INDICATOR_WEAPON_COLOR")

    val showGrenades = VisCheckBoxCustom(" ", "INDICATOR_SHOW_GRENADES", false)
    val indicatorGrenadeColor = VisColorPickerCustom("Grenades", "INDICATOR_GRENADE_COLOR")

    val showDefusers = VisCheckBoxCustom(" ", "INDICATOR_SHOW_DEFUSERS", false)
    val indicatorDefuserColor = VisColorPickerCustom("Defusers", "INDICATOR_DEFUSER_COLOR")

    init {
        table.padLeft(25F)
        table.padRight(25F)

        //Add all items to label for tabbed pane content
        table.add(indicatorEsp).left().row()
        table.add(indicatorDistance).left().colspan(2).row()
        table.add(indicatorSize).left().colspan(2).row()

        table.add(indicatorSmokeCheck).left().row()

        var tmpTable = VisTable()
        tmpTable.add(showTeam)
        tmpTable.add(indicatorTeamColor).width(175F - showTeam.width).padRight(50F)

        table.add(tmpTable).left()

        tmpTable = VisTable()
        tmpTable.add(showEnemies)
        tmpTable.add(indicatorEnemyColor).width(175F - showEnemies.width).padRight(50F)

        table.add(tmpTable).left().row()

        tmpTable = VisTable()
        tmpTable.add(showBomb)
        tmpTable.add(indicatorBombColor).width(175F - showBomb.width).padRight(50F)

        table.add(tmpTable).left()

        tmpTable = VisTable()
        tmpTable.add(showBombCarrier)
        tmpTable.add(indicatorBombCarrierColor).width(175F - showBombCarrier.width).padRight(50F)

        table.add(tmpTable).left().row()

        tmpTable = VisTable()
        tmpTable.add(showWeapons)
        tmpTable.add(indicatorWeaponColor).width(175F - showWeapons.width).padRight(50F)

        table.add(tmpTable).left()

        tmpTable = VisTable()
        tmpTable.add(showGrenades)
        tmpTable.add(indicatorGrenadeColor).width(175F - showGrenades.width).padRight(50F)

        table.add(tmpTable).left().row()

        tmpTable = VisTable()
        tmpTable.add(showDefusers)
        tmpTable.add(indicatorDefuserColor).width(175F - showDefusers.width).padRight(50F)

        table.add(tmpTable).left()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Indicator".toLocale()
    }
}

fun indicatorEspTabUpdate() {
    indicatorEspTab.apply {
        indicatorEsp.update()
        indicatorDistance.update()
        indicatorSize.update()
        indicatorSmokeCheck.update()
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
        indicatorBombCarrierColor.update()
        indicatorDefuserColor.update()
        indicatorWeaponColor.update()
        indicatorGrenadeColor.update()
    }
}