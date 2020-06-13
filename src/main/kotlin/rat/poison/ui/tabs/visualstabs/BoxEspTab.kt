package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.curSettings
import rat.poison.ui.changed
import rat.poison.ui.tabs.boxEspTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom

class BoxEspTab : Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    val skeletonEsp = VisCheckBoxCustom("Enable Skeleton", "SKELETON_ESP")
    val showTeamSkeleton = VisCheckBoxCustom("Teammates", "SKELETON_SHOW_TEAM")
    val showEnemiesSkeleton = VisCheckBoxCustom("Enemies", "SKELETON_SHOW_ENEMIES")
    val boxEsp = VisCheckBoxCustom("Bounding Box", "ENABLE_BOX_ESP")
    val boxEspDetails = VisCheckBoxCustom("Box Details", "BOX_ESP_DETAILS")
    val boxEspHealth = VisCheckBoxCustom("Health Bar", "BOX_ESP_HEALTH")
    val boxEspHealthPos = VisSelectBox<String>()
    val boxEspArmor = VisCheckBoxCustom("Armor Bar", "BOX_ESP_ARMOR")
    val boxEspArmorPos = VisSelectBox<String>()
    val boxEspName = VisCheckBoxCustom("Name", "BOX_ESP_NAME")
    val boxEspNamePos = VisSelectBox<String>()
    val boxEspWeapon = VisCheckBoxCustom("Weapon", "BOX_ESP_WEAPON")
    val boxEspWeaponPos = VisSelectBox<String>()
    val boxEspAmmo = VisCheckBoxCustom("Ammo", "BOX_ESP_AMMO")
    val boxEspAmmoPos = VisSelectBox<String>()
    val boxEspHelmet = VisCheckBoxCustom("Helmet", "BOX_ESP_HELMET")
    val boxEspHelmetPos = VisSelectBox<String>()
    val boxEspKevlar = VisCheckBoxCustom("Kevlar", "BOX_ESP_KEVLAR")
    val boxEspKevlarPos = VisSelectBox<String>()
    val boxEspScoped = VisCheckBoxCustom("Scoped", "BOX_ESP_SCOPED")
    val boxEspScopedPos = VisSelectBox<String>()
    val boxEspFlashed = VisCheckBoxCustom("Flashed", "BOX_ESP_FLASHED")
    val boxEspFlashedPos = VisSelectBox<String>()


    val boxDetailColor = VisColorPickerCustom("Detail Text", "BOX_DETAILS_TEXT_COLOR")

    val showTeamBox = VisCheckBoxCustom(" ", "BOX_SHOW_TEAM")
    val boxTeamColor = VisColorPickerCustom("Teammates", "BOX_TEAM_COLOR")

    val showEnemiesBox = VisCheckBoxCustom(" ", "BOX_SHOW_ENEMIES")
    val boxEnemyColor = VisColorPickerCustom("Enemies", "BOX_ENEMY_COLOR")

    val showDefusers = VisCheckBoxCustom(" ", "BOX_SHOW_DEFUSERS")
    val boxDefuserColor = VisColorPickerCustom("Defusers", "BOX_DEFUSER_COLOR")

    init {
        //Create Box ESP Health Pos Selector
        boxEspHealthPos.setItems("Left", "Right")
        boxEspHealthPos.selected = when (curSettings["BOX_ESP_HEALTH_POS"].replace("\"", "")) {
            "L" -> "Left"
            else -> "Right"
        }
        boxEspHealthPos.changed { _, _ ->
            curSettings["BOX_ESP_HEALTH_POS"] = boxEspHealthPos.selected.first()
            true
        }

        //Create Box ESP Armor Pos Selector
        boxEspArmorPos.setItems("Left", "Right")
        boxEspArmorPos.selected = when (curSettings["BOX_ESP_ARMOR_POS"].replace("\"", "")) {
            "L" -> "Left"
            else -> "Right"
        }
        boxEspArmorPos.changed { _, _ ->
            curSettings["BOX_ESP_ARMOR_POS"] = boxEspArmorPos.selected.first()
            true
        }

        //Create Box ESP Name Pos Selector
        boxEspNamePos.setItems("Top", "Bottom")
        boxEspNamePos.selected = when (curSettings["BOX_ESP_NAME_POS"].replace("\"", "")) {
            "T" -> "Top"
            else -> "Bottom"
        }
        boxEspNamePos.changed { _, _ ->
            curSettings["BOX_ESP_NAME_POS"] = boxEspNamePos.selected.first()
            true
        }

        //Create Box ESP Weapon Pos Selector
        boxEspWeaponPos.setItems("Top", "Bottom")
        boxEspWeaponPos.selected = when (curSettings["BOX_ESP_WEAPON_POS"].replace("\"", "")) {
            "T" -> "Top"
            else -> "Bottom"
        }
        boxEspWeaponPos.changed { _, _ ->
            curSettings["BOX_ESP_WEAPON_POS"] = boxEspWeaponPos.selected.first()
            true
        }

        //Create Box ESP Weapon Pos Selector
        boxEspHelmetPos.setItems("Left", "Right")
        boxEspHelmetPos.selected = when (curSettings["BOX_ESP_HELMET_POS"].replace("\"", "")) {
            "L" -> "Left"
            else -> "Right"
        }
        boxEspHelmetPos.changed { _, _ ->
            curSettings["BOX_ESP_HELMET_POS"] = boxEspHelmetPos.selected.first()
            true
        }

        //Create Box ESP Weapon Pos Selector
        boxEspKevlarPos.setItems("Left", "Right")
        boxEspKevlarPos.selected = when (curSettings["BOX_ESP_KEVLAR_POS"].replace("\"", "")) {
            "L" -> "Left"
            else -> "Right"
        }
        boxEspKevlarPos.changed { _, _ ->
            curSettings["BOX_ESP_KEVLAR_POS"] = boxEspKevlarPos.selected.first()
            true
        }

        //Create Box ESP Weapon Pos Selector
        boxEspAmmoPos.setItems("Top", "Bottom")
        boxEspAmmoPos.selected = when (curSettings["BOX_ESP_AMMO_POS"].replace("\"", "")) {
            "T" -> "Top"
            else -> "Bottom"
        }
        boxEspAmmoPos.changed { _, _ ->
            curSettings["BOX_ESP_AMMO_POS"] = boxEspAmmoPos.selected.first()
            true
        }

        //Create Box ESP Weapon Pos Selector
        boxEspScopedPos.setItems("Left", "Right")
        boxEspScopedPos.selected = when (curSettings["BOX_ESP_SCOPED_POS"].replace("\"", "")) {
            "L" -> "Left"
            else -> "Right"
        }
        boxEspScopedPos.changed { _, _ ->
            curSettings["BOX_ESP_SCOPED_POS"] = boxEspScopedPos.selected.first()
            true
        }

        //Create Box ESP Weapon Pos Selector
        boxEspFlashedPos.setItems("Left", "Right")
        boxEspFlashedPos.selected = when (curSettings["BOX_ESP_FLASHED_POS"].replace("\"", "")) {
            "L" -> "Left"
            else -> "Right"
        }
        boxEspFlashedPos.changed { _, _ ->
            curSettings["BOX_ESP_FLASHED_POS"] = boxEspFlashedPos.selected.first()
            true
        }

        table.padLeft(25F)
        table.padRight(25F)

        table.add(skeletonEsp).left().row()
        table.add(showTeamSkeleton).padRight(225F - showTeamSkeleton.width).left()
        table.add(showEnemiesSkeleton).padRight(225F - showEnemiesSkeleton.width).left().row()
        table.addSeparator().colspan(2)
        table.add(boxEsp).left().row()
        table.add(boxEspDetails).left().row()
        table.add(boxEspName).left()
        table.add(boxEspNamePos).left().row()
        table.add(boxEspWeapon).left()
        table.add(boxEspWeaponPos).left().row()
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

        table.add(boxDetailColor).width(175F - boxDetailColor.width).padRight(50F).row()

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

        table.add(tmpTable).left().row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Box"
    }
}

fun boxEspTabUpdate() {
    boxEspTab.apply {
        boxEsp.update()
        boxEspDetails.update()
        boxEspHealth.update()
        boxEspArmor.update()
        boxEspName.update()
        boxEspWeapon.update()
        boxEspHelmet.update()
        boxEspKevlar.update()
        boxEspAmmo.update()
        boxEspScoped.update()
        boxEspFlashed.update()
        boxDetailColor.update()
        skeletonEsp.update()
        showTeamSkeleton.update()
        showEnemiesSkeleton.update()
        showTeamBox.update()
        showEnemiesBox.update()
        boxTeamColor.update()
        boxEnemyColor.update()
        boxDefuserColor.update()
    }
}