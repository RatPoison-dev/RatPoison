package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.curLocalization
import rat.poison.ui.tabs.indicatorEspTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class IndicatorEspTab : Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    val indicatorEsp = VisCheckBoxCustom(curLocalization["ENABLE"], "INDICATOR_ESP", nameInLocalization = "ENABLE")
    val indicatorDistance = VisSliderCustom(curLocalization["INDICATOR_DISTANCE"], "INDICATOR_DISTANCE", 2F, 50F, .1F, false, nameInLocalization = "INDICATOR_DISTANCE")
    val indicatorSize = VisSliderCustom(curLocalization["INDICATOR_SIZE"], "INDICATOR_SIZE", 5F, 25F, .5F, false, nameInLocalization = "INDICATOR_SIZE")

    val showTeam = VisCheckBoxCustom(" ", "INDICATOR_SHOW_TEAM")
    val indicatorTeamColor = VisColorPickerCustom(curLocalization["TEAMMATES"], "GLOW_TEAM_COLOR", nameInLocalization = "TEAMMATES")

    val showEnemies = VisCheckBoxCustom(" ", "INDICATOR_SHOW_ENEMIES")
    val indicatorEnemyColor = VisColorPickerCustom(curLocalization["ENEMIES"], "INDICATOR_ENEMY_COLOR", nameInLocalization = "ENEMIES")

    val showBomb = VisCheckBoxCustom(" ", "INDICATOR_SHOW_BOMB")
    val indicatorBombColor = VisColorPickerCustom(curLocalization["BOMB"], "INDICATOR_BOMB_COLOR", nameInLocalization = "BOMB")

    val showBombCarrier = VisCheckBoxCustom(" ", "INDICATOR_SHOW_BOMB_CARRIER")
    val indicatorBombCarrierColor = VisColorPickerCustom(curLocalization["BOMB_CARRIER"], "INDICATOR_BOMB_CARRIER_COLOR", nameInLocalization = "BOMB_CARRIER")

    val showWeapons = VisCheckBoxCustom(" ", "INDICATOR_SHOW_WEAPONS")
    val indicatorWeaponColor = VisColorPickerCustom(curLocalization["WEAPONS"], "INDICATOR_WEAPON_COLOR", nameInLocalization = "WEAPONS")

    val showGrenades = VisCheckBoxCustom(" ", "INDICATOR_SHOW_GRENADES")
    val indicatorGrenadeColor = VisColorPickerCustom(curLocalization["GRENADES"], "INDICATOR_GRENADE_COLOR", nameInLocalization = "GRENADES")

    val showDefusers = VisCheckBoxCustom(" ", "INDICATOR_SHOW_DEFUSERS")
    val indicatorDefuserColor = VisColorPickerCustom(curLocalization["DEFUSERS"], "INDICATOR_DEFUSER_COLOR", nameInLocalization = "DEFUSERS")

    init {
        table.padLeft(25F)
        table.padRight(25F)

        //Add all items to label for tabbed pane content
        table.add(indicatorEsp).left().row()
        table.add(indicatorDistance).left().colspan(2).row()
        table.add(indicatorSize).left().colspan(2).row()

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
        return curLocalization["INDICATORS_TAB_NAME"]
    }
}

fun indicatorEspTabUpdate() {
    indicatorEspTab.apply {
        indicatorEsp.update()
        indicatorDistance.update()
        indicatorSize.update()
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
        indicatorBombCarrierColor.updateTitle()
        indicatorBombColor.updateTitle()
        indicatorDefuserColor.update()
        indicatorEnemyColor.updateTitle()
        indicatorGrenadeColor.updateTitle()
        indicatorDefuserColor.updateTitle()
        indicatorTeamColor.updateTitle()
        indicatorWeaponColor.updateTitle()
        indicatorWeaponColor.update()
        indicatorGrenadeColor.update()
    }
}