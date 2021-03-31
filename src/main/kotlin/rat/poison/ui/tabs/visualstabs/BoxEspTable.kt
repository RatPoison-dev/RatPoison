package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.ui.tabs.boxEspTable
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSelectBoxCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

//Swap VisSelectBoxCustom to showText false is mainText is " "
class BoxEspTable: VisTable(false) {
    //Init labels/sliders/boxes that show values here
    val boxEspWeaponScale = VisSliderCustom("Weapons Scale", "BOX_ESP_WEAPON_SCALE", 0.8F, 3F, 0.1F, false, barWidth = 120F, labelWidth = 150F)

    val boxEsp = VisCheckBoxCustom("Enable", "ENABLE_BOX_ESP")
    val boxEspUseIcons = VisCheckBoxCustom("Use Icons", "BOX_ESP_USE_ICONS")

    val advancedBBox = VisCheckBoxCustom("Advanced BBOX", "ADVANCED_BOUNDING_BOX")

    val boxSmokeCheck = VisCheckBoxCustom("Smoke Check", "BOX_SMOKE_CHECK")

    val farRadarBox = VisCheckBoxCustom("Far Radar Box", "BOX_FAR_RADAR")

    val boxEspDetails = VisCheckBoxCustom("Box Details", "BOX_ESP_DETAILS")
    val boxDetailColor = VisColorPickerCustom("Detail Text", "BOX_DETAILS_TEXT_COLOR")

    val boxEspHealth = VisCheckBoxCustom("Health", "BOX_ESP_HEALTH")
    val boxEspHealthPos = VisSelectBoxCustom(" ", "BOX_ESP_HEALTH_POS", false, false, "LEFT", "RIGHT")
    val boxEspArmor = VisCheckBoxCustom("Armor", "BOX_ESP_ARMOR")
    val boxEspArmorPos = VisSelectBoxCustom(" ", "BOX_ESP_ARMOR_POS", false, false, "LEFT", "RIGHT")
    val boxEspName = VisCheckBoxCustom("Name", "BOX_ESP_NAME")
    val boxEspNamePos = VisSelectBoxCustom(" ", "BOX_ESP_NAME_POS", false, false, "TOP", "BOTTOM", "LEFT", "RIGHT")
    val boxEspWeapon = VisCheckBoxCustom("Weapon", "BOX_ESP_WEAPON")
    val boxEspWeaponPos = VisSelectBoxCustom(" ", "BOX_ESP_WEAPON_POS", false, false, "TOP", "BOTTOM", "LEFT", "RIGHT")
    val boxEspMoney = VisCheckBoxCustom("Money", "BOX_ESP_MONEY")
    val boxEspMoneyPos = VisSelectBoxCustom(" ", "BOX_ESP_MONEY_POS", false, false, "TOP", "BOTTOM", "LEFT", "RIGHT")

    val boxEspAmmo = VisCheckBoxCustom("Ammo", "BOX_ESP_AMMO")
    val boxEspAmmoPos = VisSelectBoxCustom(" ", "BOX_ESP_AMMO_POS", false, false, "TOP", "BOTTOM", "LEFT", "RIGHT")
    val boxEspHelmet = VisCheckBoxCustom("Helmet", "BOX_ESP_HELMET")
    val boxEspHelmetPos = VisSelectBoxCustom(" ", "BOX_ESP_HELMET_POS", false, false, "TOP", "BOTTOM", "LEFT", "RIGHT")
    val boxEspKevlar = VisCheckBoxCustom("Kevlar", "BOX_ESP_KEVLAR")
    val boxEspKevlarPos = VisSelectBoxCustom(" ", "BOX_ESP_KEVLAR_POS", false, false, "TOP", "BOTTOM", "LEFT", "RIGHT")
    val boxEspScoped = VisCheckBoxCustom("Scoped", "BOX_ESP_SCOPED")
    val boxEspScopedPos = VisSelectBoxCustom(" ", "BOX_ESP_SCOPED_POS", false, false, "TOP", "BOTTOM", "LEFT", "RIGHT")
    val boxEspFlashed = VisCheckBoxCustom("Flashed", "BOX_ESP_FLASHED")
    val boxEspFlashedPos = VisSelectBoxCustom(" ", "BOX_ESP_FLASHED_POS", false, false, "TOP", "BOTTOM", "LEFT", "RIGHT")

    val showTeamBox = VisCheckBoxCustom("Teammates", "BOX_SHOW_TEAM", false)
    val boxTeamColor = VisColorPickerCustom("Box Teammate Color", "BOX_TEAM_COLOR")

    val showEnemiesBox = VisCheckBoxCustom("Enemies", "BOX_SHOW_ENEMIES", false)
    val boxEnemyColor = VisColorPickerCustom("Box Enemy Color", "BOX_ENEMY_COLOR")

    val showDefusers = VisCheckBoxCustom("Defusers", "BOX_SHOW_DEFUSERS", false)
    val boxDefuserColor = VisColorPickerCustom("Box Defuser Color", "BOX_DEFUSER_COLOR")

    val showWeapons = VisCheckBoxCustom("Weapons", "BOX_SHOW_WEAPONS", false)
    val boxWeaponsColor = VisColorPickerCustom("Box Weapon Color", "BOX_WEAPON_COLOR")

    init {
        val label = VisLabel("Box")
        label.setColor(1F, 1F, 1F, 1F)

        add(label).colspan(2).expandX().padTop(4F).row()
        addSeparator().colspan(2).width(200F).top().height(2F).padBottom(8F)

        add(boxEsp).colspan(2).left().row()

        add(boxEspUseIcons).colspan(2).left().row()
        add(advancedBBox).colspan(2).left().row()
        add(boxSmokeCheck).colspan(2).left().row()
        add(farRadarBox).colspan(2).left().padBottom(8F).row()

        add(showTeamBox).left().padRight(175F - showTeamBox.width)
        add(boxTeamColor).left().expandX().row()

        add(showEnemiesBox).left().padRight(175F - showEnemiesBox.width)
        add(boxEnemyColor).left().expandX().row()

        add(showDefusers).left().padRight(175F - showDefusers.width)
        add(boxDefuserColor).left().expandX().row()

        add(showWeapons).left().padRight(175F - showWeapons.width).padBottom(8F)
        add(boxWeaponsColor).left().expandX().row()

        add(boxEspDetails).left()
        add(boxDetailColor).left().expandX().row()

        add(boxEspName).left()
        add(boxEspNamePos).left().row()
        add(boxEspWeapon).left()
        add(boxEspWeaponPos).left().row()
        add(boxEspMoney).left()
        add(boxEspMoneyPos).left().row()
        add(boxEspAmmo).left()
        add(boxEspAmmoPos).left().row()
        add(boxEspHealth).left()
        add(boxEspHealthPos).left().row()
        add(boxEspArmor).left()
        add(boxEspArmorPos).left().row()
        add(boxEspHelmet).left()
        add(boxEspHelmetPos).left().row()
        add(boxEspKevlar).left()
        add(boxEspKevlarPos).left().row()
        add(boxEspScoped).left()
        add(boxEspScopedPos).left().row()
        add(boxEspFlashed).left()
        add(boxEspFlashedPos).left().row()
//        add(boxEspWeaponScale).left().row()
//
    }
}

fun boxEspTabUpdate() {
    boxEspTable.apply {
        boxEsp.update()
        boxEspUseIcons.update()
        advancedBBox.update()
        boxSmokeCheck.update()
        farRadarBox.update()
        boxEspDetails.update()
        boxEspHealth.update()
        boxEspHealthPos.update()
        boxEspArmor.update()
        boxEspArmorPos.update()
        boxEspName.update()
        boxEspNamePos.update()
        boxEspWeapon.update()
        boxEspWeaponPos.update()
        boxEspMoney.update()
        boxEspMoneyPos.update()
        boxEspHelmet.update()
        boxEspHelmetPos.update()
        boxEspKevlar.update()
        boxEspKevlarPos.update()
        boxEspAmmo.update()
        boxEspAmmoPos.update()
        boxEspScoped.update()
        boxEspScopedPos.update()
        boxEspFlashed.update()
        boxEspFlashedPos.update()
        boxDetailColor.update()
        boxEspWeaponScale.update()
        showTeamBox.update()
        showEnemiesBox.update()
        showDefusers.update()
        showWeapons.update()
        boxTeamColor.update()
        boxEnemyColor.update()
        boxDefuserColor.update()
        boxWeaponsColor.update()
    }
}

fun boxEspTableDisable(bool: Boolean, col: Color) {
    boxEspTable.boxEsp.disable(bool)
    boxEspTable.advancedBBox.disable(bool)
    boxEspTable.advancedBBox.disable(bool)
    boxEspTable.boxEspDetails.disable(bool)
    boxEspTable.boxEspHealth.disable(bool)
    boxEspTable.boxEspHealthPos.disable(bool, col)
    boxEspTable.boxEspArmor.disable(bool)
    boxEspTable.boxEspArmorPos.disable(bool, col)
    boxEspTable.boxEspName.disable(bool)
    boxEspTable.boxEspNamePos.disable(bool, col)
    boxEspTable.boxEspWeapon.disable(bool)
    boxEspTable.boxEspWeaponPos.disable(bool, col)
    boxEspTable.boxEspMoney.disable(bool)
    boxEspTable.boxEspMoneyPos.disable(bool, col)
    boxEspTable.boxEspHelmet.disable(bool)
    boxEspTable.boxEspHelmetPos.disable(bool, col)
    boxEspTable.boxEspKevlar.disable(bool)
    boxEspTable.boxEspKevlarPos.disable(bool, col)
    boxEspTable.boxEspAmmo.disable(bool)
    boxEspTable.boxEspAmmoPos.disable(bool, col)
    boxEspTable.boxEspScoped.disable(bool)
    boxEspTable.boxEspScopedPos.disable(bool, col)
    boxEspTable.boxEspFlashed.disable(bool)
    boxEspTable.boxEspFlashedPos.disable(bool, col)
    boxEspTable.showTeamBox.disable(bool)
    boxEspTable.showEnemiesBox.disable(bool)
    boxEspTable.showDefusers.disable(bool)
    boxEspTable.showWeapons.disable(bool)
    boxEspTable.boxTeamColor.disable(bool)
    boxEspTable.boxEnemyColor.disable(bool)
    boxEspTable.boxWeaponsColor.disable(bool)
    boxEspTable.boxDefuserColor.disable(bool)
    boxEspTable.boxEspWeaponScale.disable(bool, col)
    boxEspTable.boxEspUseIcons.disable(bool)
    boxEspTable.boxSmokeCheck.disable(bool)
    boxEspTable.farRadarBox.disable(bool)
}