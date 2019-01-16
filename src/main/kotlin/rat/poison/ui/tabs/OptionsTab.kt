package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSlider
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.App
import rat.poison.settings.*
import rat.poison.ui.UIUpdate
import rat.poison.ui.changed
import rat.poison.utils.Dojo
import java.io.File
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.StandardOpenOption

class Options : Tab(false, false) {
    private val table = VisTable(true)

    init {
        //Create UI_Alpha Slider
        val menuAlpha = VisTable()
        val menuAlphaLabel = VisLabel("Menu Alpha: " + 1F) //1F is default
        val menuAlphaSlider = VisSlider(.1F, 1F, .1F, false)
        menuAlphaSlider.value = PERFECT_AIM_FOV.toFloat()
        menuAlphaSlider.changed { _, _ ->
            menuAlpha.parent.parent.parent.color.a = (Math.round(menuAlphaSlider.value * 10.0)/10.0).toFloat() //Set the top level parents alpha (currently .parent.parent.parent is the only way, instead of a way to find top most instantly
            menuAlphaLabel.setText("Menu Alpha: " + menuAlpha.parent.parent.parent.color.a.toString()) //Same parent situation
        }
        menuAlpha.add(menuAlphaLabel).spaceRight(6F)
        menuAlpha.add(menuAlphaSlider)

        //Create Save Button
        val saveButton = VisTextButton("Save Current Configuration")
        saveButton.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                val cfgfile = File("settings" + "\\" + "cfg.kts")
                var cfgfiletext = ""

                if (!cfgfile.exists()) {
                    cfgfile.createNewFile()
                }

                //Easier way todo later
                //Add current values modified by menu here

                //Add Imports
                cfgfiletext += "import rat.poison.settings.*" + System.lineSeparator()
                cfgfiletext += "import rat.poison.game.Color" + System.lineSeparator() + System.lineSeparator()

                //From Aim.kts
                cfgfiletext += "AIM_BONE = " + if (AIM_BONE == HEAD_BONE) {"HEAD_BONE"} else {"BODY_BONE"} + System.lineSeparator()
                cfgfiletext += "ACTIVATE_FROM_FIRE_KEY = " + ACTIVATE_FROM_FIRE_KEY + System.lineSeparator()
                cfgfiletext += "TEAMMATES_ARE_ENEMIES = " + TEAMMATES_ARE_ENEMIES + System.lineSeparator()
                cfgfiletext += "FORCE_AIM_KEY = " + FORCE_AIM_KEY + System.lineSeparator()
                cfgfiletext += "AIM_FOV = " + AIM_FOV + System.lineSeparator()
                cfgfiletext += "AIM_SPEED_MIN = " + AIM_SPEED_MIN + System.lineSeparator()
                cfgfiletext += "AIM_SPEED_MAX = " + AIM_SPEED_MAX + System.lineSeparator()
                cfgfiletext += "AIM_STRICTNESS = " + AIM_STRICTNESS + System.lineSeparator()
                cfgfiletext += "PERFECT_AIM = " + PERFECT_AIM + System.lineSeparator()
                cfgfiletext += "PERFECT_AIM_FOV = " + PERFECT_AIM_FOV + System.lineSeparator()
                cfgfiletext += "PERFECT_AIM_CHANCE = " + PERFECT_AIM_CHANCE + System.lineSeparator()
                cfgfiletext += "AIM_ASSIST_MODE = " + AIM_ASSIST_MODE + System.lineSeparator()
                cfgfiletext += "AIM_ASSIST_STRICTNESS = " + AIM_ASSIST_STRICTNESS + System.lineSeparator()

                //From General.kts
                cfgfiletext += "LEAGUE_MODE = " + LEAGUE_MODE + System.lineSeparator()
                cfgfiletext += "FIRE_KEY = " + FIRE_KEY + System.lineSeparator()
                cfgfiletext += "VISUALS_TOGGLE_KEY = " + VISUALS_TOGGLE_KEY + System.lineSeparator()
                cfgfiletext += "MENU_KEY = " + MENU_KEY + System.lineSeparator()

                //From Settings.kts
                cfgfiletext += "ENABLE_BUNNY_HOP = " + ENABLE_BUNNY_HOP + System.lineSeparator()
                cfgfiletext += "ENABLE_RCS = " + ENABLE_RCS + System.lineSeparator()
                cfgfiletext += "ENABLE_RECOIL_CROSSHAIR = " + ENABLE_RECOIL_CROSSHAIR + System.lineSeparator()
                cfgfiletext += "ENABLE_ESP = " + ENABLE_ESP + System.lineSeparator()
                cfgfiletext += "ENABLE_FLAT_AIM = " + ENABLE_FLAT_AIM + System.lineSeparator()
                cfgfiletext += "ENABLE_PATH_AIM = " + ENABLE_PATH_AIM + System.lineSeparator()
                cfgfiletext += "ENABLE_BONE_TRIGGER = " + ENABLE_BONE_TRIGGER + System.lineSeparator()
                cfgfiletext += "ENABLE_REDUCED_FLASH = " + ENABLE_REDUCED_FLASH + System.lineSeparator()
                cfgfiletext += "ENABLE_BOMB_TIMER = " + ENABLE_BOMB_TIMER + System.lineSeparator()

                //From ESP.kts
                cfgfiletext += "BOX_ESP = " + BOX_ESP + System.lineSeparator()
                cfgfiletext += "GLOW_ESP = " + GLOW_ESP + System.lineSeparator()
                cfgfiletext += "MODEL_ESP = " + MODEL_ESP + System.lineSeparator()
                cfgfiletext += "CHAMS_ESP = " + CHAMS_ESP + System.lineSeparator()
                cfgfiletext += "CHAMS_SHOW_HEALTH = " + CHAMS_SHOW_HEALTH + System.lineSeparator()
                cfgfiletext += "SKELETON_ESP = " + SKELETON_ESP + System.lineSeparator()
                cfgfiletext += "CHAMS_BRIGHTNESS = " + CHAMS_BRIGHTNESS + System.lineSeparator()
                cfgfiletext += "SHOW_TEAM = " + SHOW_TEAM + System.lineSeparator()
                cfgfiletext += "SHOW_ENEMIES = " + SHOW_ENEMIES + System.lineSeparator()
                cfgfiletext += "SHOW_DORMANT = " + SHOW_DORMANT + System.lineSeparator()
                cfgfiletext += "SHOW_BOMB = " + SHOW_BOMB + System.lineSeparator()
                cfgfiletext += "SHOW_WEAPONS = " + SHOW_WEAPONS + System.lineSeparator()
                cfgfiletext += "SHOW_GRENADES = " + SHOW_GRENADES + System.lineSeparator()
                cfgfiletext += "TEAM_COLOR = Color(" + TEAM_COLOR.red.toString() + ", " + TEAM_COLOR.green + ", " + TEAM_COLOR.blue + ", " + TEAM_COLOR.alpha.toFloat() +")" + System.lineSeparator()
                cfgfiletext += "ENEMY_COLOR = Color(" + ENEMY_COLOR.red + ", " + ENEMY_COLOR.green + ", " + ENEMY_COLOR.blue + ", " + ENEMY_COLOR.alpha.toFloat() +")" + System.lineSeparator()
                cfgfiletext += "BOMB_COLOR = Color(" + BOMB_COLOR.red + ", " + BOMB_COLOR.green + ", " + BOMB_COLOR.blue + ", " + BOMB_COLOR.alpha.toFloat() +")" + System.lineSeparator()
                cfgfiletext += "WEAPON_COLOR = Color(" + WEAPON_COLOR.red + ", " + WEAPON_COLOR.green + ", " + WEAPON_COLOR.blue + ", " + WEAPON_COLOR.alpha.toFloat() +")" + System.lineSeparator()
                cfgfiletext += "GRENADE_COLOR = Color(" + GRENADE_COLOR.red + ", " + GRENADE_COLOR.green + ", " + GRENADE_COLOR.blue + ", " + GRENADE_COLOR.alpha.toFloat() +")" + System.lineSeparator()
                cfgfiletext += "CHAMS_ESP_COLOR = Color(" + CHAMS_ESP_COLOR.red + ", " + CHAMS_ESP_COLOR.green + ", " + CHAMS_ESP_COLOR.blue + ", " + CHAMS_ESP_COLOR.alpha.toFloat() +")" + System.lineSeparator()

                //From Misc
                cfgfiletext += "BONE_TRIGGER_FOV = " + BONE_TRIGGER_FOV + System.lineSeparator()
                cfgfiletext += "BONE_TRIGGER_BONE = " + if (BONE_TRIGGER_BONE == HEAD_BONE) {"HEAD_BONE"} else {"BODY_BONE"} + System.lineSeparator()
                cfgfiletext += "AIM_ON_BONE_TRIGGER = " + AIM_ON_BONE_TRIGGER + System.lineSeparator()
                cfgfiletext += "BONE_TRIGGER_ENABLE_KEY = " + BONE_TRIGGER_ENABLE_KEY + System.lineSeparator()
                cfgfiletext += "BONE_TRIGGER_KEY = " + BONE_TRIGGER_KEY + System.lineSeparator()
                cfgfiletext += "RCS_MIN = " + RCS_MIN + System.lineSeparator()
                cfgfiletext += "RCS_MAX = " + RCS_MAX + System.lineSeparator()
                cfgfiletext += "RCS_MIN_DURATION = " + RCS_MIN_DURATION + System.lineSeparator()
                cfgfiletext += "RCS_MAX_DURATION = " + RCS_MAX_DURATION + System.lineSeparator()
                cfgfiletext += "FLASH_MAX_ALPHA = " + FLASH_MAX_ALPHA + "F" + System.lineSeparator()
                //cfgfiletext += "DRAW_FOV = " + DRAW_FOV + System.lineSeparator()
                cfgfiletext += "ENEMY_INDICATOR = " + ENEMY_INDICATOR + System.lineSeparator()

                Files.write(File("settings\\cfg.kts").toPath(), cfgfiletext.toByteArray(), StandardOpenOption.WRITE)
            }
        }

        //Create Save Button
        val loadButton = VisTextButton("Load Previously Saved Configuration")
        loadButton.changed { _, _ ->
            val cfgfile = File("settings" + "\\" + "cfg.kts")
            if (!cfgfile.exists()) {
                Dialogs.showErrorDialog(App.stage, "Error", "cfg.kts not found, save your configuration first!")
            }
            else{
                FileReader(cfgfile).use { Dojo.script(it.readLines().joinToString("\n"))}
                UIUpdate()
            }
        }

        table.add(menuAlpha).width(250F).row()
        table.add(saveButton).row()
        table.add(loadButton)
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Options"
    }
}