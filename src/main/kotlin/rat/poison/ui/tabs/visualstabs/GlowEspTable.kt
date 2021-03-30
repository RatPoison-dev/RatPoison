package rat.poison.ui.tabs.visualstabs

import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.ui.tabs.glowEspTable
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSelectBoxCustom

class GlowEspTable: VisTable(false) {
    //Init labels/sliders/boxes that show values here
    val glowEsp = VisCheckBoxCustom("Enable", "GLOW_ESP")

    val glowSmokeCheck = VisCheckBoxCustom("Smoke Check", "GLOW_SMOKE_CHECK")

    val glowTypes = arrayOf("NORMAL", "MODEL", "VISIBLE", "VISIBLE-FLICKER")

    //Visible flicker - is the bypass for now, issue in collecting settings
    var enemyGlowType = VisSelectBoxCustom(" ", "GLOW_ENEMY_TYPE", false, false, *glowTypes)
    var teammateGlowType = VisSelectBoxCustom(" ", "GLOW_TEAMMATE_TYPE", false, false, items = glowTypes)
    var weaponGlowType = VisSelectBoxCustom(" ", "GLOW_WEAPON_TYPE", false, false, items = glowTypes)
    var grenadeGlowType = VisSelectBoxCustom(" ", "GLOW_GRENADE_TYPE", false, false, items = glowTypes)
    var targetGlowType = VisSelectBoxCustom(" ", "GLOW_TARGET_TYPE", false, false, items = glowTypes)
    var bombCarrierGlowType = VisSelectBoxCustom(" ", "GLOW_BOMB_CARRIER_TYPE", false, false, items = glowTypes)
    var bombGlowType = VisSelectBoxCustom(" ", "GLOW_BOMB_TYPE", false, false, items = glowTypes)

    val glowShowHealth = VisCheckBoxCustom("Show Health", "GLOW_SHOW_HEALTH")

    val showTeam = VisCheckBoxCustom("Teammates", "GLOW_SHOW_TEAM", false)
    val glowTeamColor = VisColorPickerCustom("Teammate Glow Color", "GLOW_TEAM_COLOR")

    val showEnemies = VisCheckBoxCustom("Enemies", "GLOW_SHOW_ENEMIES", false)
    val glowEnemyColor = VisColorPickerCustom("Enemy Glow Color", "GLOW_ENEMY_COLOR")

    val showBomb = VisCheckBoxCustom("Bomb", "GLOW_SHOW_BOMB", false)
    val glowBombColor = VisColorPickerCustom("Bomb", "GLOW_BOMB_COLOR")
    val glowBombAdaptive = VisCheckBoxCustom("Bomb Glow", "GLOW_BOMB_ADAPTIVE")

    val showBombCarrier = VisCheckBoxCustom("Bomb Carrier", "GLOW_SHOW_BOMB_CARRIER", false)
    val glowBombCarrierColor = VisColorPickerCustom("Bomb Carrier", "GLOW_BOMB_CARRIER_COLOR")

    val showWeapons = VisCheckBoxCustom("Weapons", "GLOW_SHOW_WEAPONS", false)
    val glowWeaponColor = VisColorPickerCustom("Weapons", "GLOW_WEAPON_COLOR")

    val showGrenades = VisCheckBoxCustom("Grenades", "GLOW_SHOW_GRENADES", false)
    val glowGrenadeColor = VisColorPickerCustom("Grenades", "GLOW_GRENADE_COLOR")

    val showTarget = VisCheckBoxCustom("Aim Target", "GLOW_SHOW_TARGET", false)
    val glowTargetColor = VisColorPickerCustom("Target", "GLOW_TARGET_COLOR")

    init {
        val label = VisLabel("Glow")
        label.setColor(1F, 1F, 1F, 1F)

        add(label).colspan(3).expandX().padTop(4F).row()
        addSeparator().colspan(3).width(200F).top().height(2F).padBottom(8F)

        add(glowEsp).left().padRight(175F - glowEsp.width)
        add(glowShowHealth).colspan(2).left().expandX().row()

        add(showTeam).left().padRight(175F - showTeam.width)
        add(glowTeamColor).left().expandX()
        add(teammateGlowType).left().expandX().row()

        add(showEnemies).left().padRight(175F - showEnemies.width)
        add(glowEnemyColor).left().expandX()
        add(enemyGlowType).left().expandX().row()

        add(showBomb).left().padRight(175F - showBomb.width)
        add(glowBombColor).left().expandX()
        add(bombGlowType).left().expandX().row()

        add(showBombCarrier).left().padRight(175F - showBombCarrier.width)
        add(glowBombCarrierColor).left().expandX()
        add(bombCarrierGlowType).left().expandX().row()

        add(showWeapons).left().padRight(175F - showWeapons.width)
        add(glowWeaponColor).left().expandX()
        add(weaponGlowType).left().expandX().row()

        add(showGrenades).left().padRight(175F - showGrenades.width)
        add(glowGrenadeColor).left().expandX()
        add(grenadeGlowType).left().expandX().row()

        add(showTarget).left().padRight(175F - showTarget.width)
        add(glowTargetColor).left().expandX()
        add(targetGlowType).left().expandX().row()

        add(glowSmokeCheck).colspan(3).left().row()
        add(glowBombAdaptive).colspan(3).left().row()
    }
}

fun glowEspTabUpdate() {
    glowEspTable.apply {
        glowEsp.update()
        glowShowHealth.update()
        glowSmokeCheck.update()

        enemyGlowType.update()
        teammateGlowType.update()
        weaponGlowType.update()
        targetGlowType.update()
        grenadeGlowType.update()
        bombGlowType.update()
        bombCarrierGlowType.update()

        showTeam.update()
        showEnemies.update()
        showBomb.update()
        showBombCarrier.update()
        showWeapons.update()
        showGrenades.update()
        showTarget.update()
        glowTeamColor.update()
        glowEnemyColor.update()
        glowBombAdaptive.update()
        glowBombColor.update()
        glowBombCarrierColor.update()
        glowWeaponColor.update()
        glowGrenadeColor.update()
    }
}

fun glowEspTableDisable(bool: Boolean) {
    glowEspTable.glowEsp.disable(bool)
    glowEspTable.glowShowHealth.disable(bool)
    glowEspTable.glowSmokeCheck.disable(bool)
    glowEspTable.showTeam.disable(bool)
    glowEspTable.showEnemies.disable(bool)
    glowEspTable.showBomb.disable(bool)
    glowEspTable.showBombCarrier.disable(bool)
    glowEspTable.showWeapons.disable(bool)
    glowEspTable.showGrenades.disable(bool)
    glowEspTable.showTarget.disable(bool)
    glowEspTable.glowTeamColor.disable(bool)
    glowEspTable.glowEnemyColor.disable(bool)
    glowEspTable.glowBombColor.disable(bool)
    glowEspTable.glowBombAdaptive.disable(bool)
    glowEspTable.glowBombCarrierColor.disable(bool)
    glowEspTable.glowWeaponColor.disable(bool)
    glowEspTable.glowGrenadeColor.disable(bool)
    glowEspTable.glowTargetColor.disable(bool)
}