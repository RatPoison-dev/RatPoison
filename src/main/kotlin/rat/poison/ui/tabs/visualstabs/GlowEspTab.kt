package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.toLocale
import rat.poison.ui.tabs.glowEspTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSelectBoxCustom

class GlowEspTab: Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    val glowEsp = VisCheckBoxCustom("Enable", "GLOW_ESP")

    val glowSmokeCheck = VisCheckBoxCustom("Smoke Check", "GLOW_SMOKE_CHECK")

    //Visible flicker - is the bypass for now, issue in collecting settings
    var enemyGlowType = VisSelectBoxCustom(" ", "GLOW_ENEMY_TYPE", false, false,"NORMAL", "MODEL", "VISIBLE", "VISIBLE-FLICKER")
    var teammateGlowType = VisSelectBoxCustom(" ", "GLOW_TEAMMATE_TYPE", false, false,"NORMAL", "MODEL", "VISIBLE", "VISIBLE-FLICKER")
    var weaponGlowType = VisSelectBoxCustom(" ", "GLOW_WEAPON_TYPE", false, false,"NORMAL", "MODEL", "VISIBLE", "VISIBLE-FLICKER")
    var grenadeGlowType = VisSelectBoxCustom(" ", "GLOW_GRENADE_TYPE", false, false, "NORMAL", "MODEL", "VISIBLE", "VISIBLE-FLICKER")
    var targetGlowType = VisSelectBoxCustom(" ", "GLOW_TARGET_TYPE", false, false, "NORMAL", "MODEL", "VISIBLE", "VISIBLE-FLICKER")
    var bombCarrierGlowType = VisSelectBoxCustom(" ", "GLOW_BOMB_CARRIER_TYPE", false, false, "NORMAL", "MODEL", "VISIBLE", "VISIBLE-FLICKER")
    var bombGlowType = VisSelectBoxCustom(" ", "GLOW_BOMB_TYPE", false, false, "NORMAL", "MODEL", "VISIBLE", "VISIBLE-FLICKER")

    val glowShowHealth = VisCheckBoxCustom("Show Health", "GLOW_SHOW_HEALTH")

    val showTeam = VisCheckBoxCustom(" ", "GLOW_SHOW_TEAM", false)
    val glowTeamColor = VisColorPickerCustom("Teammates", "GLOW_TEAM_COLOR")

    val showEnemies = VisCheckBoxCustom(" ", "GLOW_SHOW_ENEMIES", false)
    val glowEnemyColor = VisColorPickerCustom("Enemies", "GLOW_ENEMY_COLOR")

    val showBomb = VisCheckBoxCustom(" ", "GLOW_SHOW_BOMB", false)
    val glowBombColor = VisColorPickerCustom("Bomb", "GLOW_BOMB_COLOR")
    val glowBombAdaptive = VisCheckBoxCustom("Adaptive Bomb Glow", "GLOW_BOMB_ADAPTIVE")

    val showBombCarrier = VisCheckBoxCustom(" ", "GLOW_SHOW_BOMB_CARRIER", false)
    val glowBombCarrierColor = VisColorPickerCustom("Bomb Carrier", "GLOW_BOMB_CARRIER_COLOR")

    val showWeapons = VisCheckBoxCustom(" ", "GLOW_SHOW_WEAPONS", false)
    val glowWeaponColor = VisColorPickerCustom("Weapons", "GLOW_WEAPON_COLOR")

    val showGrenades = VisCheckBoxCustom(" ", "GLOW_SHOW_GRENADES", false)
    val glowGrenadeColor = VisColorPickerCustom("Grenades", "GLOW_GRENADE_COLOR")

    val showTarget = VisCheckBoxCustom(" ", "GLOW_SHOW_TARGET", false)
    val glowHighlightColor = VisColorPickerCustom("Target", "GLOW_TARGET_COLOR")

    init {
        ////////////////////FORMATTING
        table.padLeft(25F)
        table.padRight(25F)

//        entityGlowType.selected = curSettings["GLOW_ENTITY_TYPE"]
//        weaponGlowType.selected = curSettings["GLOW_WEAPON_TYPE"]
//        targetGlowType.selected = curSettings["GLOW_TARGET_TYPE"]
//        grenadeGlowType.selected = curSettings["GLOW_GRENADE_TYPE"]
//        bombGlowType.selected = curSettings["GLOW_BOMB_TYPE"]
//        bombCarrierGlowType.selected = curSettings["GLOW_BOMB_CARRIER_TYPE"]

        table.add(glowEsp).left()
        table.add(glowShowHealth).left().row()

        table.add(glowSmokeCheck).left().row()
        table.add(glowBombAdaptive).left().row()

        var tmpTable = VisTable()
        tmpTable.add(showTeam)
        tmpTable.add(glowTeamColor).width(175F - showTeam.width).padRight(50F)

        table.add(tmpTable).left()
        table.add(teammateGlowType).left().row()

        tmpTable = VisTable()
        tmpTable.add(showEnemies)
        tmpTable.add(glowEnemyColor).width(175F - showEnemies.width).padRight(50F)

        table.add(tmpTable).left()
        table.add(enemyGlowType).left().row()

        tmpTable = VisTable()
        tmpTable.add(showBomb)
        tmpTable.add(glowBombColor).width(175F - showBomb.width).padRight(50F)

        table.add(tmpTable).left()
        table.add(bombGlowType).left().row()

        tmpTable = VisTable()
        tmpTable.add(showBombCarrier)
        tmpTable.add(glowBombCarrierColor).width(175F - showBombCarrier.width).padRight(50F)

        table.add(tmpTable).left()
        table.add(bombCarrierGlowType).left().row()

        tmpTable = VisTable()
        tmpTable.add(showWeapons)
        tmpTable.add(glowWeaponColor).width(175F - showWeapons.width).padRight(50F)

        table.add(tmpTable).left()
        table.add(weaponGlowType).left().row()

        tmpTable = VisTable()
        tmpTable.add(showGrenades)
        tmpTable.add(glowGrenadeColor).width(175F - showGrenades.width).padRight(50F)

        table.add(tmpTable).left()
        table.add(grenadeGlowType).left().row()

        tmpTable = VisTable()
        tmpTable.add(showTarget)
        tmpTable.add(glowHighlightColor).width(175F - showTarget.width).padRight(50F)

        table.add(tmpTable).left()
        table.add(targetGlowType).left().row()
        ////////////////////FORMATTING
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Glow".toLocale()
    }
}

fun glowEspTabUpdate() {
    glowEspTab.apply {
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