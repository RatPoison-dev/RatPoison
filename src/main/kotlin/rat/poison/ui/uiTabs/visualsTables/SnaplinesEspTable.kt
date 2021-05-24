package rat.poison.ui.uiTabs.visualsTables

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.ui.uiElements.VisCheckBoxCustom
import rat.poison.ui.uiElements.VisColorPickerCustom
import rat.poison.ui.uiElements.VisSliderCustom
import rat.poison.ui.uiTabs.snaplinesEspTable

class SnaplinesEspTable: VisTable(false) {
    //Init labels/sliders/boxes that show values here
    val enableSnaplines = VisCheckBoxCustom("Enable", "ENABLE_SNAPLINES")
    val snaplinesWidth = VisSliderCustom("Line Width", "SNAPLINES_WIDTH", 1F, 10F, 1F, true, 0, 175F, 117F)

    val snaplinesSmokeCheck = VisCheckBoxCustom("Smoke Check", "SNAPLINES_SMOKE_CHECK")

    val teamSnaplines = VisCheckBoxCustom("Teammates", "SNAPLINES_TEAMMATES", false)
    val teamSnaplinesColor = VisColorPickerCustom("Snapline Teammate Color", "SNAPLINES_TEAM_COLOR")

    val enemySnaplines = VisCheckBoxCustom("Enemies", "SNAPLINES_ENEMIES", false)
    val enemySnaplinesColor = VisColorPickerCustom("Snapline Enemy Color", "SNAPLINES_ENEMY_COLOR")

    val weaponSnaplines = VisCheckBoxCustom("Weapons", "SNAPLINES_WEAPONS", false)
    val weaponSnaplinesColor = VisColorPickerCustom("Snapline Weapon Color", "SNAPLINES_WEAPON_COLOR")

    val bombSnaplines = VisCheckBoxCustom("Bomb", "SNAPLINES_BOMB", false)
    val bombSnaplinesColor = VisColorPickerCustom("Snapline Bomb Color", "SNAPLINES_BOMB_COLOR")

    val bombCarrierSnaplines = VisCheckBoxCustom("Bomb Carrier", "SNAPLINES_BOMB_CARRIER", false)
    val bombCarrierSnaplinesColor = VisColorPickerCustom("Snapline Bomb Carrier Color", "SNAPLINES_BOMB_CARRIER_COLOR")

    val defuseKitSnaplines = VisCheckBoxCustom("Defuse Kits", "SNAPLINES_DEFUSE_KITS", false)
    val defuseKitSnaplinesColor = VisColorPickerCustom("Snapline Defuse Kit Color", "SNAPLINES_DEFUSE_KIT_COLOR")

    init {
        val label = VisLabel("Snaplines")
        label.setColor(1F, 1F, 1F, 1F)

        add(label).colspan(2).expandX().padTop(4F).row()
        addSeparator().colspan(2).width(200F).top().height(2F).padBottom(8F)

        add(enableSnaplines).left().colspan(2).row()
        add(snaplinesWidth).left().colspan(2).row()

        add(snaplinesSmokeCheck).left().colspan(2).row()

        add(teamSnaplines).left().padRight(175F - teamSnaplines.width)
        add(teamSnaplinesColor).left().expandX().row()

        add(enemySnaplines).left().padRight(175F - enemySnaplines.width)
        add(enemySnaplinesColor).left().expandX().row()

        add(weaponSnaplines).left().padRight(175F - weaponSnaplines.width)
        add(weaponSnaplinesColor).left().expandX().row()

        add(bombSnaplines).left().padRight(175F - bombSnaplines.width)
        add(bombSnaplinesColor).left().expandX().row()

        add(bombCarrierSnaplines).left().padRight(175F - bombCarrierSnaplines.width)
        add(bombCarrierSnaplinesColor).left().expandX().row()

        add(defuseKitSnaplines).left().padRight(175F - defuseKitSnaplines.width)
        add(defuseKitSnaplinesColor).left().expandX().row()
    }
}

fun snaplinesEspTabUpdate() {
    snaplinesEspTable.apply {
        enableSnaplines.update()
        snaplinesWidth.update()
        snaplinesSmokeCheck.update()
        enemySnaplines.update()
        enemySnaplinesColor.update()
        teamSnaplines.update()
        teamSnaplinesColor.update()
        weaponSnaplines.update()
        weaponSnaplinesColor.update()
        bombSnaplines.update()
        bombSnaplinesColor.update()
        bombCarrierSnaplines.update()
        bombCarrierSnaplinesColor.update()
    }
}

fun snaplinesEspTableDisable(bool: Boolean, col: Color) {
    snaplinesEspTable.apply {
        enableSnaplines.disable(bool)
        snaplinesWidth.disable(bool, col)
        snaplinesSmokeCheck.disable(bool)
        enemySnaplines.disable(bool)
        enemySnaplinesColor.disable(bool)
        teamSnaplines.disable(bool)
        teamSnaplinesColor.disable(bool)
        weaponSnaplines.disable(bool)
        weaponSnaplinesColor.disable(bool)
        bombSnaplines.disable(bool)
        bombSnaplinesColor.disable(bool)
        bombCarrierSnaplines.disable(bool)
        bombCarrierSnaplinesColor.disable(bool)
        defuseKitSnaplines.disable(bool)
        defuseKitSnaplinesColor.disable(bool)
    }
}