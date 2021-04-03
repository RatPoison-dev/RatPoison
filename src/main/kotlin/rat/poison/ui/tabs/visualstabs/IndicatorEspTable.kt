package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.ui.tabs.indicatorEspTable
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class IndicatorEspTable: VisTable(false) {
    //Init labels/sliders/boxes that show values here
    val indicatorEsp = VisCheckBoxCustom("Enable", "INDICATOR_ESP")
    val indicatorDistance = VisSliderCustom("Distance", "INDICATOR_DISTANCE", 2F, 50F, 1F, true, 0, 175F, 117F)
    val indicatorSize = VisSliderCustom("Size", "INDICATOR_SIZE", 5F, 25F, .5F, false, 1, 175F, 117F)

    val indicatorSmokeCheck = VisCheckBoxCustom("Smoke Check", "INDICATOR_SMOKE_CHECK")

    val showTeam = VisCheckBoxCustom("Teammates", "INDICATOR_SHOW_TEAM", false)
    val indicatorTeamColor = VisColorPickerCustom("Indicator Teammate Color", "GLOW_TEAM_COLOR")

    val showEnemies = VisCheckBoxCustom("Enemies", "INDICATOR_SHOW_ENEMIES", false)
    val indicatorEnemyColor = VisColorPickerCustom("Indicator Enemy Color", "INDICATOR_ENEMY_COLOR")

    val showBomb = VisCheckBoxCustom("Bomb", "INDICATOR_SHOW_BOMB", false)
    val indicatorBombColor = VisColorPickerCustom("Indicator Bomb Color", "INDICATOR_BOMB_COLOR")

    val showBombCarrier = VisCheckBoxCustom("Bomb Carrier", "INDICATOR_SHOW_BOMB_CARRIER", false)
    val indicatorBombCarrierColor = VisColorPickerCustom("Indicator Bomb Carrier Color", "INDICATOR_BOMB_CARRIER_COLOR")

    val showWeapons = VisCheckBoxCustom("Weapons", "INDICATOR_SHOW_WEAPONS", false)
    val indicatorWeaponColor = VisColorPickerCustom("Indicator Weapon Color", "INDICATOR_WEAPON_COLOR")

    val showGrenades = VisCheckBoxCustom("Grenades", "INDICATOR_SHOW_GRENADES", false)
    val indicatorGrenadeColor = VisColorPickerCustom("Indicator Grenade Color", "INDICATOR_GRENADE_COLOR")

    val showDefusers = VisCheckBoxCustom("Defusers", "INDICATOR_SHOW_DEFUSERS", false)
    val indicatorDefuserColor = VisColorPickerCustom("Indicator Defuser Color", "INDICATOR_DEFUSER_COLOR")

    init {
        val label = VisLabel("Indicators")
        label.setColor(1F, 1F, 1F, 1F)

        add(label).colspan(2).expandX().padTop(4F).row()
        addSeparator().colspan(2).width(200F).top().height(2F).padBottom(8F)

        add(indicatorEsp).colspan(2).left().row()
        add(indicatorDistance).colspan(2).left().row()
        add(indicatorSize).colspan(2).left().row()

        add(showTeam).left().padRight(175F - showTeam.width)
        add(indicatorTeamColor).left().expandX().row()

        add(showEnemies).left().padRight(175F - showEnemies.width)
        add(indicatorEnemyColor).left().expandX().row()

        add(showBomb).left().padRight(175F - showBomb.width)
        add(indicatorBombColor).left().expandX().row()

        add(showBombCarrier).left().padRight(175F - showBombCarrier.width)
        add(indicatorBombCarrierColor).left().expandX().row()

        add(showWeapons).left().padRight(175F - showWeapons.width)
        add(indicatorWeaponColor).left().expandX().row()

        add(showGrenades).left().padRight(175F - showGrenades.width)
        add(indicatorGrenadeColor).left().expandX().row()

        add(showDefusers).left().padRight(175F - showTeam.width)
        add(indicatorDefuserColor).left().expandX().row()

        add(indicatorSmokeCheck).colspan(2).left().row()
    }
}

fun indicatorEspTabUpdate() {
    indicatorEspTable.apply {
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

fun indicatorEspTableDisable(bool: Boolean, col: Color) {
    indicatorEspTable.indicatorEsp.disable(bool)
    indicatorEspTable.indicatorDistance.disable(bool, col)
    indicatorEspTable.indicatorSize.disable(bool, col)
    indicatorEspTable.indicatorSmokeCheck.disable(bool)
    indicatorEspTable.showTeam.disable(bool)
    indicatorEspTable.showEnemies.disable(bool)
    indicatorEspTable.showBomb.disable(bool)
    indicatorEspTable.showBombCarrier.disable(bool)
    indicatorEspTable.showWeapons.disable(bool)
    indicatorEspTable.showGrenades.disable(bool)
    indicatorEspTable.showDefusers.disable(bool)
    indicatorEspTable.indicatorTeamColor.disable(bool)
    indicatorEspTable.indicatorEnemyColor.disable(bool)
    indicatorEspTable.indicatorBombColor.disable(bool)
    indicatorEspTable.indicatorDefuserColor.disable(bool)
    indicatorEspTable.indicatorWeaponColor.disable(bool)
    indicatorEspTable.indicatorGrenadeColor.disable(bool)
}