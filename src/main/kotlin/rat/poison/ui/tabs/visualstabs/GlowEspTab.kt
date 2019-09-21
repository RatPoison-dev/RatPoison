package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.ui.tabs.glowEspTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom

class GlowEspTab : Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    val glowEsp = VisCheckBoxCustom("Glow", "GLOW_ESP")
    val invGlowEsp = VisCheckBoxCustom("Inv Glow", "INV_GLOW_ESP")
    val modelEsp = VisCheckBoxCustom("Model", "MODEL_ESP")
    val modelAndGlow = VisCheckBoxCustom("Model & Glow", "MODEL_AND_GLOW")//VisCheckBox("Model & Glow Esp")

    val showTeam = VisCheckBoxCustom(" ", "GLOW_SHOW_TEAM")
    val glowTeamColor = VisColorPickerCustom("Team", "GLOW_TEAM_COLOR")

    val showEnemies = VisCheckBoxCustom(" ", "GLOW_SHOW_ENEMIES")
    val glowEnemyColor = VisColorPickerCustom("Enemy", "GLOW_ENEMY_COLOR")

    val showBomb = VisCheckBoxCustom(" ", "GLOW_SHOW_BOMB")
    val glowBombColor = VisColorPickerCustom("Bomb", "GLOW_BOMB_COLOR")

    val showBombCarrier = VisCheckBoxCustom(" ", "GLOW_SHOW_BOMB_CARRIER")
    val glowBombCarrierColor = VisColorPickerCustom("Bomb Carrier", "GLOW_BOMB_CARRIER_COLOR")

    val showWeapons = VisCheckBoxCustom(" ", "GLOW_SHOW_WEAPONS")
    val glowWeaponColor = VisColorPickerCustom("Weapon", "GLOW_WEAPON_COLOR")

    val showGrenades = VisCheckBoxCustom(" ", "GLOW_SHOW_WEAPONS")
    val glowGrenadeColor = VisColorPickerCustom("Grenade", "GLOW_GRENADE_COLOR")

    val showTarget = VisCheckBoxCustom(" ", "GLOW_SHOW_TARGET")
    val glowHighlightColor = VisColorPickerCustom("Target", "GLOW_HIGHLIGHT_COLOR")

    init {
        ////////////////////FORMATTING
        table.padLeft(25F)
        table.padRight(25F)

//        val colTable = VisTable()
//        val width = 225F
//        colTable.add(glowTeamColor).width(width).padRight(2F)
//        colTable.add(glowEnemyColor).width(width).row()
//        colTable.add(glowBombColor).width(width).padRight(2F)
//        colTable.add(glowDefuserColor).width(width).row()
//        colTable.add(glowWeaponColor).width(width).padRight(2F)
//        colTable.add(glowGrenadeColor).width(width).row()
//        colTable.add(glowHighlightColor).width(width).left()

        table.add(glowEsp).left()
        table.add(invGlowEsp).left().row()

        table.add(modelEsp).left()
        table.add(modelAndGlow).left().row()

        var tmpTable = VisTable()
        tmpTable.add(showTeam)
        tmpTable.add(glowTeamColor).width(175F - showTeam.width).padRight(50F)

        table.add(tmpTable).left()

        tmpTable = VisTable()
        tmpTable.add(showEnemies)
        tmpTable.add(glowEnemyColor).width(175F - showEnemies.width).padRight(50F)

        table.add(tmpTable).left().row()

        tmpTable = VisTable()
        tmpTable.add(showBomb)
        tmpTable.add(glowBombColor).width(175F - showBomb.width).padRight(50F)

        table.add(tmpTable).left()

        tmpTable = VisTable()
        tmpTable.add(showBombCarrier)
        tmpTable.add(glowBombCarrierColor).width(175F - showBombCarrier.width).padRight(50F)

        table.add(tmpTable).left().row()

        tmpTable = VisTable()
        tmpTable.add(showWeapons)
        tmpTable.add(glowWeaponColor).width(175F - showWeapons.width).padRight(50F)

        table.add(tmpTable).left()

        tmpTable = VisTable()
        tmpTable.add(showGrenades)
        tmpTable.add(glowGrenadeColor).width(175F - showGrenades.width).padRight(50F)

        table.add(tmpTable).left().row()

        tmpTable = VisTable()
        tmpTable.add(showTarget)
        tmpTable.add(glowHighlightColor).width(175F - showTarget.width).padRight(50F)

        table.add(tmpTable).left()
        ////////////////////FORMATTING
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Glow"
    }
}

fun glowEspTabUpdate() {
    glowEspTab.apply {
        glowEsp.update()
        invGlowEsp.update()
        modelEsp.update()
        modelAndGlow.update()
        showTeam.update()
        showEnemies.update()
        showBomb.update()
        showBombCarrier.update()
        showWeapons.update()
        showGrenades.update()
        showTarget.update()
        glowTeamColor.update()
        glowEnemyColor.update()
        glowBombColor.update()
        glowBombCarrierColor.update()
        glowWeaponColor.update()
        glowGrenadeColor.update()
        glowHighlightColor.update()

        if (invGlowEsp.isChecked || modelEsp.isChecked) {
            glowEsp.isChecked = true
            glowEsp.isDisabled = true
        }

        if (modelAndGlow.isChecked) {
            modelEsp.isChecked = true
            modelEsp.isDisabled = true
        }
    }
}