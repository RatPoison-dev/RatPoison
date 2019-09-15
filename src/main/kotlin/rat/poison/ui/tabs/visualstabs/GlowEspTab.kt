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

    val showTeam = VisCheckBoxCustom("Show Team", "GLOW_SHOW_TEAM")
    val showEnemies = VisCheckBoxCustom("Show Enemies", "GLOW_SHOW_ENEMIES")
    val showBomb = VisCheckBoxCustom("Show Bomb", "GLOW_SHOW_BOMB")
    val showBombCarrier = VisCheckBoxCustom("Show Bomb Carrier", "GLOW_SHOW_BOMB_CARRIER")
    val showWeapons = VisCheckBoxCustom("Show Weapons", "GLOW_SHOW_WEAPONS")
    val showGrenades = VisCheckBoxCustom("Show Grenades", "GLOW_SHOW_WEAPONS")
    val showTarget = VisCheckBoxCustom("Show Target", "GLOW_SHOW_TARGET")

    val glowTeamColor = VisColorPickerCustom("Team Color", "GLOW_TEAM_COLOR")
    val glowEnemyColor = VisColorPickerCustom("Enemy Color", "GLOW_ENEMY_COLOR")
    val glowBombColor = VisColorPickerCustom("Bomb Color", "GLOW_BOMB_COLOR")
    val glowDefuserColor = VisColorPickerCustom("Defuser Color", "GLOW_DEFUSER_COLOR")
    val glowWeaponColor = VisColorPickerCustom("Weapon Color", "GLOW_WEAPON_COLOR")
    val glowGrenadeColor = VisColorPickerCustom("Grenade Color", "GLOW_GRENADE_COLOR")
    val glowHighlightColor = VisColorPickerCustom("Highlight Color", "GLOW_HIGHLIGHT_COLOR")

    init {
        ////////////////////FORMATTING
        table.padLeft(25F)
        table.padRight(25F)

        val colTable = VisTable()
        val width = 225F
        colTable.add(glowTeamColor).width(width).padRight(2F)
        colTable.add(glowEnemyColor).width(width).row()
        colTable.add(glowBombColor).width(width).padRight(2F)
        colTable.add(glowDefuserColor).width(width).row()
        colTable.add(glowWeaponColor).width(width).padRight(2F)
        colTable.add(glowGrenadeColor).width(width).row()
        colTable.add(glowHighlightColor).width(width).left()
        table.add(glowEsp).left()
        table.add(invGlowEsp).left().row()

        table.add(modelEsp).left()
        table.add(modelAndGlow).left().row()

        table.add(showTeam).left()
        table.add(showEnemies).left().row()

        table.add(showBomb).left()
        table.add(showBombCarrier).left().row()

        table.add(showWeapons).left()
        table.add(showGrenades).left().padRight(225F - showGrenades.width).row()

        table.add(showTarget).left().padRight(225F - showTarget.width).row()

        table.add(colTable).colspan(2).width(450F).left()
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
        glowDefuserColor.update()
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