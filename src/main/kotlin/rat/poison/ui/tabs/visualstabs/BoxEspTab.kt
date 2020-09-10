package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.toLocale
import rat.poison.ui.tabs.boxEspTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSelectBoxCustom

//Swap VisSelectBoxCustom to showText false is mainText is " "
class BoxEspTab: Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    val skeletonEsp = VisCheckBoxCustom("Enable Skeleton", "SKELETON_ESP")
    val showTeamSkeleton = VisCheckBoxCustom("Teammates", "SKELETON_SHOW_TEAM")
    val showEnemiesSkeleton = VisCheckBoxCustom("Enemies", "SKELETON_SHOW_ENEMIES")

    val boxEsp = VisCheckBoxCustom("Bounding Box", "ENABLE_BOX_ESP")

    val advancedBBox = VisCheckBoxCustom("Advanced BBOX", "ADVANCED_BOUNDING_BOX")

    val boxSmokeCheck = VisCheckBoxCustom("Smoke Check", "BOX_SMOKE_CHECK")

    val farRadarBox = VisCheckBoxCustom("Far Radar Box", "BOX_FAR_RADAR")

    val boxEspDetails = VisCheckBoxCustom("Box Details", "BOX_ESP_DETAILS")
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
    val boxEspAmmoPos = VisSelectBoxCustom(" ", "BOX_ESP_AMMO_POS", false, false, "TOP", "BOTTOM")
    val boxEspHelmet = VisCheckBoxCustom("Helmet", "BOX_ESP_HELMET")
    val boxEspHelmetPos = VisSelectBoxCustom(" ", "BOX_ESP_HELMET_POS", false, false, "LEFT", "RIGHT")
    val boxEspKevlar = VisCheckBoxCustom("Kevlar", "BOX_ESP_KEVLAR")
    val boxEspKevlarPos = VisSelectBoxCustom(" ", "BOX_ESP_KEVLAR_POS", false, false, "LEFT", "RIGHT")
    val boxEspScoped = VisCheckBoxCustom("Scoped", "BOX_ESP_SCOPED")
    val boxEspScopedPos = VisSelectBoxCustom(" ", "BOX_ESP_SCOPED_POS", false, false, "TOP", "BOTTOM", "LEFT", "RIGHT")
    val boxEspFlashed = VisCheckBoxCustom("Flashed", "BOX_ESP_FLASHED")
    val boxEspFlashedPos = VisSelectBoxCustom(" ", "BOX_ESP_FLASHED_POS", false, false, "TOP", "BOTTOM", "LEFT", "RIGHT")

    val boxDetailColor = VisColorPickerCustom("Detail Text", "BOX_DETAILS_TEXT_COLOR")

    val showTeamBox = VisCheckBoxCustom(" ", "BOX_SHOW_TEAM", false)
    val boxTeamColor = VisColorPickerCustom("Teammates", "BOX_TEAM_COLOR")

    val showEnemiesBox = VisCheckBoxCustom(" ", "BOX_SHOW_ENEMIES", false)
    val boxEnemyColor = VisColorPickerCustom("Enemies", "BOX_ENEMY_COLOR")

    val showDefusers = VisCheckBoxCustom(" ", "BOX_SHOW_DEFUSERS", false)
    val boxDefuserColor = VisColorPickerCustom("Defusers", "BOX_DEFUSER_COLOR")

    val showWeapons = VisCheckBoxCustom(" ", "BOX_SHOW_WEAPONS", false)
    val boxWeaponsColor = VisColorPickerCustom("Weapons", "BOX_WEAPON_COLOR")

    init {
        table.padLeft(25F)
        table.padRight(25F)

        table.add(skeletonEsp).left().row()
        table.add(showTeamSkeleton).padRight(225F - showTeamSkeleton.width).left() //225
        table.add(showEnemiesSkeleton).padRight(225F - showEnemiesSkeleton.width).left().row()//225
        table.addSeparator().colspan(2)
        table.add(boxEsp).left().row()
        table.add(advancedBBox).left().row()
        table.add(boxSmokeCheck).left().row()
        table.add(farRadarBox).left().row()
        table.add(boxEspDetails).left().row()
        table.add(boxEspName).left()
        table.add(boxEspNamePos).left().row()
        table.add(boxEspWeapon).left()
        table.add(boxEspWeaponPos).left().row()
        table.add(boxEspMoney).left()
        table.add(boxEspMoneyPos).left().row()
        table.add(boxEspAmmo).left()
        table.add(boxEspAmmoPos).left().row()
        table.add(boxEspHealth).left()
        table.add(boxEspHealthPos).left().row()
        table.add(boxEspArmor).left()
        table.add(boxEspArmorPos).left().row()
        table.add(boxEspHelmet).left()
        table.add(boxEspHelmetPos).left().row()
        table.add(boxEspKevlar).left()
        table.add(boxEspKevlarPos).left().row()
        table.add(boxEspScoped).left()
        table.add(boxEspScopedPos).left().row()
        table.add(boxEspFlashed).left()
        table.add(boxEspFlashedPos).left().row()

        table.add(boxDetailColor).width(175F - boxDetailColor.width).left().row()

        var tmpTable = VisTable()
        tmpTable.add(showTeamBox)
        tmpTable.add(boxTeamColor).width(175F - showTeamBox.width).padRight(50F)

        table.add(tmpTable).left()

        tmpTable = VisTable()
        tmpTable.add(showEnemiesBox)
        tmpTable.add(boxEnemyColor).width(175F - showEnemiesBox.width).padRight(50F)

        table.add(tmpTable).left().row()

        tmpTable = VisTable()
        tmpTable.add(showDefusers)
        tmpTable.add(boxDefuserColor).width(175F - showEnemiesBox.width).padRight(50F)

        table.add(tmpTable).left()

        tmpTable = VisTable()
        tmpTable.add(showWeapons)
        tmpTable.add(boxWeaponsColor).width(175F - showEnemiesBox.width).padRight(50F)

        table.add(tmpTable).left().row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Box".toLocale()
    }
}

fun boxEspTabUpdate() {
    boxEspTab.apply {
        boxEsp.update()
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
        skeletonEsp.update()
        showTeamSkeleton.update()
        showEnemiesSkeleton.update()
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