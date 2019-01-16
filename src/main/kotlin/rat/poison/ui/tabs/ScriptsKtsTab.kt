package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.settings.*
import rat.poison.ui.changed

class ScriptsKts : Tab(false, false) {
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

    init {
        //Create Enable_Bunny_Hop Toggle
        //val enableBunnyHopToggle = VisTextButton("ENABLE_BUNNY_HOP", "toggle")
        if (LEAGUE_MODE) enableBunnyHopToggle.toggle()
        enableBunnyHopToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                ENABLE_BUNNY_HOP = !ENABLE_BUNNY_HOP
            }
        }

        //Create Enable_Rcs Toggle
        //val enableRCSToggle = VisTextButton("ENABLE_RCS", "toggle")
        if (ENABLE_RCS) enableRCSToggle.toggle()
        enableRCSToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                ENABLE_RCS = !ENABLE_RCS
            }
        }

        //Create Enable_Recoil_Crosshair Toggle
        //val enableRCrosshairToggle = VisTextButton("ENABLE_RECOIL_CROSSHAIR", "toggle")
        if (ENABLE_RECOIL_CROSSHAIR) enableRCrosshairToggle.toggle()
        enableRCrosshairToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                ENABLE_RECOIL_CROSSHAIR = !ENABLE_RECOIL_CROSSHAIR
            }
        }
////Marked for fix, enable esp needs to turn all the esps off
        //Create Enable_Esp Toggle
        //val enableEspToggle = VisTextButton("ENABLE_ESP", "toggle")
        if (ENABLE_ESP) enableEspToggle.toggle()
        enableEspToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                ENABLE_ESP = !ENABLE_ESP
            }
        }

        //Create Enable_Flat_Aim Toggle
        //val enableFlatAimToggle = VisTextButton("ENABLE_FLAT_AIM", "toggle")
        if (ENABLE_FLAT_AIM) enableFlatAimToggle.toggle()
        enableFlatAimToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                ENABLE_FLAT_AIM = !ENABLE_FLAT_AIM
            }
        }

        //Create Enable_Path_Aim Toggle
        //val enablePathAimToggle = VisTextButton("ENABLE_PATH_AIM", "toggle")
        if (ENABLE_PATH_AIM) enablePathAimToggle.toggle()
        enablePathAimToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                ENABLE_PATH_AIM = !ENABLE_PATH_AIM
            }
        }

        //Create Enable_Bone_Trigger Toggle
        //val enableBoneTriggerToggle = VisTextButton("ENABLE_BONE_TRIGGER", "toggle")
        if (ENABLE_BONE_TRIGGER) enableBoneTriggerToggle.toggle()
        enableBoneTriggerToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                ENABLE_BONE_TRIGGER = !ENABLE_BONE_TRIGGER
            }
        }

        //Create Enable_Reduced_Flash Toggle
        //val enableReducedFlashToggle = VisTextButton("ENABLE_REDUCED_FLASH", "toggle")
        if (ENABLE_REDUCED_FLASH) enableReducedFlashToggle.toggle()
        enableReducedFlashToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                ENABLE_REDUCED_FLASH = !ENABLE_REDUCED_FLASH
            }
        }

        //Create Enable_Bomb_Timer Toggle
        //val enableBombTimerToggle = VisTextButton("ENABLE_BOMB_TIMER", "toggle")
        if (ENABLE_BOMB_TIMER) enableBombTimerToggle.toggle()
        enableBombTimerToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                ENABLE_BOMB_TIMER = !ENABLE_BOMB_TIMER
            }
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
        return "Settings.kts"
    }
}