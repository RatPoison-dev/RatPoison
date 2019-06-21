package rat.poison.ui.tabs.esptabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.boolToStr
import rat.poison.curSettings
import rat.poison.scripts.esp.disableEsp
import rat.poison.settings.*
import rat.poison.strToBool
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
    val showBomb = VisCheckBox("Show Bomb")
    val showWeapons = VisCheckBox("Show Weapons")
    val showGrenades = VisCheckBox("Show Grenades")
    val showTarget = VisCheckBox("Show Target")

    init {
        //Create Glow_Esp Toggle
        Tooltip.Builder("Whether or not to enable glow esp").target(glowEsp).build()
        if (curSettings["GLOW_ESP"]!!.strToBool()) glowEsp.toggle()
        glowEsp.changed { _, _ ->
            curSettings["GLOW_ESP"] = glowEsp.isChecked.boolToStr()
            if (!GLOW_ESP) {
                disableEsp()
            }
            true
        }

        //Create curSettings["INV_GLOW_ESP"]!!.strToBool() Toggle
        Tooltip.Builder("Whether or not to enable inverted glow esp if glow esp is enabled").target(invGlowEsp).build()
        if (curSettings["INV_GLOW_ESP"]!!.strToBool()) invGlowEsp.toggle()
        invGlowEsp.changed { _, _ ->
            curSettings["INV_GLOW_ESP"] = invGlowEsp.isChecked.boolToStr()

            if (curSettings["INV_GLOW_ESP"]!!.strToBool()) {
                GLOW_ESP = true
                glowEsp.isChecked = true
                glowEsp.isDisabled = true
            }
            else if (!curSettings["MODEL_ESP"]!!.strToBool())
            {
                glowEsp.isDisabled = false
            }
            UIUpdate()
            true
        }

        //Create curSettings["MODEL_ESP"]!!.strToBool() Toggle
        Tooltip.Builder("Whether or not to enable model esp").target(modelEsp).build()
        if (curSettings["MODEL_ESP"]!!.strToBool()) modelEsp.toggle()
        modelEsp.changed { _, _ ->
            curSettings["MODEL_ESP"] = modelEsp.isChecked.boolToStr()

            if (curSettings["MODEL_ESP"]!!.strToBool())
            {
                GLOW_ESP = true
                glowEsp.isChecked = true
                glowEsp.isDisabled = true
            }
            else if (!curSettings["INV_GLOW_ESP"]!!.strToBool())
            {
                glowEsp.isDisabled = false
            }
            true
        }

        //Create Model And Glow Toggle
        Tooltip.Builder("Whether or not to enable model when visible, glow when not visible esp").target(modelAndGlow).build()
        modelAndGlow.isChecked = curSettings["MODEL_AND_GLOW"]!!.strToBool()
        modelAndGlow.changed { _, _ ->
            curSettings["MODEL_AND_GLOW"] = modelAndGlow.isChecked.boolToStr()

            if (curSettings["MODEL_AND_GLOW"]!!.strToBool()) {
                curSettings["MODEL_ESP"] = true
                modelEsp.isChecked = true
                modelEsp.isDisabled = true

                GLOW_ESP = true
                glowEsp.isChecked = true
            }
            else
            {
                modelEsp.isDisabled = false
            }

            true
        }

        //Create Show Team Toggle
        Tooltip.Builder("Whether or not to show team with esp").target(showTeam).build()
        if (curSettings["GLOW_SHOW_TEAM"]!!.strToBool()) showTeam.toggle()
        showTeam.changed { _, _ ->
            curSettings["GLOW_SHOW_TEAM"] = showTeam.isChecked.boolToStr()
            true
        }

        //Create Show Enemies Toggle
        Tooltip.Builder("Whether or not to show enemies with esp").target(showEnemies).build()
        if (curSettings["GLOW_SHOW_ENEMIES"]!!.strToBool()) showEnemies.toggle()
        showEnemies.changed { _, _ ->
            curSettings["GLOW_SHOW_ENEMIES"] = showEnemies.isChecked.boolToStr()
            true
        }

        //Create Show Bomb Toggle
        Tooltip.Builder("Whether or not to show bomb with esp").target(showBomb).build()
        if (curSettings["GLOW_SHOW_BOMB"]!!.strToBool()) showBomb.toggle()
        showBomb.changed { _, _ ->
            curSettings["GLOW_SHOW_BOMB"] = showBomb.isChecked.boolToStr()
            true
        }

        //Create Show Weapons Toggle
        Tooltip.Builder("Whether or not to show eapons with esp").target(showWeapons).build()
        if (curSettings["GLOW_SHOW_WEAPONS"]!!.strToBool()) showWeapons.toggle()
        showWeapons.changed { _, _ ->
            curSettings["GLOW_SHOW_WEAPONS"] = showWeapons.isChecked.boolToStr()
            true
        }

        //Create Show Grenades Toggle
        Tooltip.Builder("Whether or not to show grenades with esp").target(showGrenades).build()
        if (curSettings["GLOW_SHOW_GRENADES"]!!.strToBool()) showGrenades.toggle()
        showGrenades.changed { _, _ ->
            curSettings["GLOW_SHOW_GRENADES"] = showGrenades.isChecked.boolToStr()
            true
        }

        //Create Show Target Toggle
        Tooltip.Builder("Whether or not to show grenades with esp").target(showTarget).build()
        if (curSettings["GLOW_SHOW_TARGET"]!!.strToBool()) showTarget.toggle()
        showTarget.changed { _, _ ->
            curSettings["GLOW_SHOW_TARGET"] = showTarget.isChecked.boolToStr()
            true
        }

        table.add(glowEsp)
        table.add(invGlowEsp).row()
        table.add(modelEsp)
        table.add(modelAndGlow).row()
        table.add(showTeam)
        table.add(showEnemies).row()
        table.add(showBomb).row()
        table.add(showWeapons)
        table.add(showGrenades).row()

        table.add(showTarget).colspan(2).row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Glow"
    }
}