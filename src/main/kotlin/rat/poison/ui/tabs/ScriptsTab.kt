package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.game.CSGO
import rat.poison.game.Color
import rat.poison.game.offsets.ClientOffsets
import rat.poison.scripts.esp.disableEsp
import rat.poison.settings.*
import rat.poison.ui.*

class ScriptsTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val enableBunnyHopToggle = VisTextButton("ENABLE_BUNNY_HOP", "toggle") //Bunny_Hop
    val enableRCSToggle = VisTextButton("ENABLE_RCS", "toggle") //RCS
    val enableRCrosshairToggle = VisTextButton("ENABLE_RECOIL_CROSSHAIR", "toggle") //Recoil_Crosshair
    val enableEspToggle = VisTextButton("ENABLE_ESP", "toggle") //ESP
    val enableFlatAimToggle = VisTextButton("ENABLE_FLAT_AIM", "toggle") //Enable_Flat_Aim
    val enablePathAimToggle = VisTextButton("ENABLE_PATH_AIM", "toggle") //Enable_Path_Aim
    val enableBoneTriggerToggle = VisTextButton("ENABLE_BONE_TRIGGER", "toggle") //Bone_Trigger
    val enableReducedFlashToggle = VisTextButton("ENABLE_REDUCED_FLASH", "toggle") //Reduced_Flash
    val enableBombTimerToggle = VisTextButton("ENABLE_BOMB_TIMER", "toggle") //Bomb_Timer

    //Needed to toggle esp
    var prevchamsshowhealth = CHAMS_SHOW_HEALTH
    var prevchamsbrightness = CHAMS_BRIGHTNESS
    var prevchamsespcolor = CHAMS_ESP_COLOR

    init {
        //Create Enable_Bunny_Hop Toggle
        //val enableBunnyHopToggle = VisTextButton("ENABLE_BUNNY_HOP", "toggle")
        enableBunnyHopToggle.isChecked = ENABLE_BUNNY_HOP
        enableBunnyHopToggle.changed { _, _ ->
            ENABLE_BUNNY_HOP = enableBunnyHopToggle.isChecked
            true
        }

        //Create Enable_Rcs Toggle
        //val enableRCSToggle = VisTextButton("ENABLE_RCS", "toggle")
        enableRCSToggle.isChecked = ENABLE_RCS
        enableRCSToggle.changed { _, _ ->
            ENABLE_RCS = enableRCSToggle.isChecked
            tabbedPane.disableTab(rcsTab, !ENABLE_RCS)
            true
        }

        //Create Enable_Recoil_Crosshair Toggle
        //val enableRCrosshairToggle = VisTextButton("ENABLE_RECOIL_CROSSHAIR", "toggle")
        enableRCrosshairToggle.isChecked = ENABLE_RECOIL_CROSSHAIR
        enableRCrosshairToggle.changed { _, _ ->
            ENABLE_RECOIL_CROSSHAIR = enableRCrosshairToggle.isChecked
            true
        }

        //Create Enable_Esp Toggle
        //val enableEspToggle = VisTextButton("ENABLE_ESP", "toggle")
        enableEspToggle.isChecked = ENABLE_ESP
        enableEspToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                ENABLE_ESP = enableEspToggle.isChecked

                val lastTab = tabbedPane.activeTab
                tabbedPane.disableTab(espTab, !ENABLE_ESP)
                tabbedPane.switchTab(lastTab)

                if (!ENABLE_ESP) {
                    disableEsp()
                }
            }
        }

        //Create Enable_Flat_Aim Toggle
        //val enableFlatAimToggle = VisTextButton("ENABLE_FLAT_AIM", "toggle")
        enableFlatAimToggle.isChecked = ENABLE_FLAT_AIM
        enableFlatAimToggle.changed { _, _ ->
            ENABLE_FLAT_AIM = enableFlatAimToggle.isChecked
            true
        }

        //Create Enable_Path_Aim Toggle
        //val enablePathAimToggle = VisTextButton("ENABLE_PATH_AIM", "toggle")
        enablePathAimToggle.isChecked = ENABLE_PATH_AIM
        enablePathAimToggle.changed { _, _ ->
            ENABLE_PATH_AIM = enablePathAimToggle.isChecked
            true
        }

        //Create Enable_Bone_Trigger Toggle
        //val enableBoneTriggerToggle = VisTextButton("ENABLE_BONE_TRIGGER", "toggle")
        enableBoneTriggerToggle.isChecked = ENABLE_BONE_TRIGGER
        enableBoneTriggerToggle.changed { _, _ ->
            ENABLE_BONE_TRIGGER = enableBoneTriggerToggle.isChecked
            tabbedPane.disableTab(bTrigTab, !ENABLE_BONE_TRIGGER)
        }

        //Create Enable_Reduced_Flash Toggle
        //val enableReducedFlashToggle = VisTextButton("ENABLE_REDUCED_FLASH", "toggle")
        enableReducedFlashToggle.isChecked = ENABLE_REDUCED_FLASH
        enableReducedFlashToggle.changed { _, _ ->
            ENABLE_REDUCED_FLASH = enableReducedFlashToggle.isChecked
            true
        }

        //Create Enable_Bomb_Timer Toggle
        //val enableBombTimerToggle = VisTextButton("ENABLE_BOMB_TIMER", "toggle")
        enableBombTimerToggle.isChecked = ENABLE_BOMB_TIMER
        enableBombTimerToggle.changed { _, _ ->
            ENABLE_BOMB_TIMER = enableBombTimerToggle.isChecked
            true
        }

        //Add all items to label for tabbed pane content
        table.add(enableBunnyHopToggle).row() //Add Enable_Bunny_Hop Toggle
        table.add(enableRCSToggle).row() //Add Enable_Rcs Toggle
        table.add(enableRCrosshairToggle).row() //Add Enable_RCrosshair Toggle
        table.add(enableEspToggle).row() //Add Enable_RCrosshair Toggle
        table.add(enableFlatAimToggle).row() //Add Enable_Flat_Aim Toggle
        table.add(enablePathAimToggle).row() //Add Enable_Path_Aim Toggle
        table.add(enableBoneTriggerToggle).row() //Add Enable_Bone_Trigger Toggle
        table.add(enableReducedFlashToggle).row() //Add Enable_Reduced_Flash Toggle
        table.add(enableBombTimerToggle).row() //Add Enable_Bomb_Timer Toggle
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Scripts"
    }
}