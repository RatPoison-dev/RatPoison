package rat.poison.ui.tabs.esptabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.scripts.esp.disableEsp
import rat.poison.settings.*
import rat.poison.ui.UIUpdate
import rat.poison.ui.changed

class GlowEspTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val glowEsp = VisCheckBox("Glow Esp")
    val invGlowEsp = VisCheckBox("Inv Glow Esp")
    val modelEsp = VisCheckBox("Model Esp")
    val modelAndGlow = VisCheckBox("Model & Glow Esp")

    val showTeam = VisCheckBox("Show Team")
    val showEnemies = VisCheckBox("Show Enemies")
    val showDormant = VisCheckBox("Show Dormant")
    val showBomb = VisCheckBox("Show Bomb")
    val showWeapons = VisCheckBox("Show Weapons")
    val showGrenades = VisCheckBox("Show Grenades")

    init {
        //Create Glow_Esp Toggle
        Tooltip.Builder("Whether or not to enable glow esp").target(glowEsp).build()
        if (GLOW_ESP) glowEsp.toggle()
        glowEsp.changed { _, _ ->
            GLOW_ESP = glowEsp.isChecked
            if (!GLOW_ESP) {
                disableEsp()
            }
            true
        }

        //Create Inv_Glow_Esp Toggle
        Tooltip.Builder("Whether or not to enable inverted glow esp if glow esp is enabled").target(invGlowEsp).build()
        if (INV_GLOW_ESP) invGlowEsp.toggle()
        invGlowEsp.changed { _, _ ->
            INV_GLOW_ESP = invGlowEsp.isChecked

            if (INV_GLOW_ESP) {
                GLOW_ESP = true
            }
            UIUpdate()
            true
        }

        //Create Model_Esp Toggle
        Tooltip.Builder("Whether or not to enable model esp").target(modelEsp).build()
        if (MODEL_ESP) modelEsp.toggle()
        modelEsp.changed { _, _ ->
            MODEL_ESP = modelEsp.isChecked
            true
        }

        //Create Model_And_Glow Toggle
        Tooltip.Builder("Whether or not to enable model when visible, glow when not visible esp").target(modelAndGlow).build()
        modelAndGlow.isChecked = MODEL_AND_GLOW
        modelAndGlow.changed { _, _ ->
            MODEL_AND_GLOW = modelAndGlow.isChecked
            true
        }

        //Create Show_Team Toggle
        Tooltip.Builder("Whether or not to show team with esp").target(showTeam).build()
        if (GLOW_SHOW_TEAM) showTeam.toggle()
        showTeam.changed { _, _ ->
            GLOW_SHOW_TEAM = showTeam.isChecked
            true
        }

        //Create Show_Enemies Toggle
        Tooltip.Builder("Whether or not to show enemies with esp").target(showEnemies).build()
        if (GLOW_SHOW_ENEMIES) showEnemies.toggle()
        showEnemies.changed { _, _ ->
            GLOW_SHOW_ENEMIES = showEnemies.isChecked
            true
        }

        //Create Show_Dormant Toggle
        Tooltip.Builder("Whether or not to show dormant entities with esp").target(showDormant).build()
        if (GLOW_SHOW_DORMANT) showDormant.toggle()
        showDormant.changed { _, _ ->
            GLOW_SHOW_DORMANT = showDormant.isChecked
            true
        }

        //Create Show_Bomb Toggle
        Tooltip.Builder("Whether or not to show bomb with esp").target(showBomb).build()
        if (GLOW_SHOW_BOMB) showBomb.toggle()
        showBomb.changed { _, _ ->
            GLOW_SHOW_BOMB = showBomb.isChecked
            true
        }

        //Create Show_Bomb Toggle
        Tooltip.Builder("Whether or not to show eapons with esp").target(showWeapons).build()
        if (GLOW_SHOW_WEAPONS) showWeapons.toggle()
        showWeapons.changed { _, _ ->
            GLOW_SHOW_WEAPONS = showWeapons.isChecked
            true
        }

        //Create Show_Bomb Toggle
        Tooltip.Builder("Whether or not to show grenades with esp").target(showGrenades).build()
        if (GLOW_SHOW_GRENADES) showGrenades.toggle()
        showGrenades.changed { _, _ ->
            GLOW_SHOW_GRENADES = showGrenades.isChecked
            true
        }

        table.add(glowEsp)
        table.add(invGlowEsp).row()
        table.add(modelEsp)
        table.add(modelAndGlow).row()

        table.add(showTeam)
        table.add(showEnemies).row()

        table.add(showDormant)
        table.add(showBomb).row()

        table.add(showWeapons)
        table.add(showGrenades).row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Glow"
    }
}