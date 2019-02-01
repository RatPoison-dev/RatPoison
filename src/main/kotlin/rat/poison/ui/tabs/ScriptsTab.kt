package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.scripts.esp.disableEsp
import rat.poison.settings.*
import rat.poison.ui.*

class ScriptsTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val enableBunnyHop = VisTextButton("ENABLE_BUNNY_HOP", "toggle") //Bunny_Hop
    val enableRCS = VisTextButton("ENABLE_RCS", "toggle") //RCS
    val enableRCrosshair = VisTextButton("ENABLE_RECOIL_CROSSHAIR", "toggle") //Recoil_Crosshair
    val enableEsp = VisTextButton("ENABLE_ESP", "toggle") //ESP
    val enableBoneTrigger = VisTextButton("ENABLE_BONE_TRIGGER", "toggle") //Bone_Trigger
    val enableReducedFlash = VisTextButton("ENABLE_REDUCED_FLASH", "toggle") //Reduced_Flash
    val enableBombTimer = VisTextButton("ENABLE_BOMB_TIMER", "toggle") //Bomb_Timer

    init {
        //Create Enable_Bunny_Hop Toggle
        //val enableBunnyHop = VisTextButton("ENABLE_BUNNY_HOP", "toggle")
        Tooltip.Builder("Whether or not to enable bunny hop").target(enableBunnyHop).build()
        enableBunnyHop.isChecked = ENABLE_BUNNY_HOP
        enableBunnyHop.changed { _, _ ->
            ENABLE_BUNNY_HOP = enableBunnyHop.isChecked
            true
        }

        //Create Enable_Rcs Toggle
        //val enableRCS = VisTextButton("ENABLE_RCS", "toggle")
        Tooltip.Builder("Whether or not to enable the recoil control system").target(enableRCS).build()
        enableRCS.isChecked = ENABLE_RCS
        enableRCS.changed { _, _ ->
            ENABLE_RCS = enableRCS.isChecked
            mainTabbedPane.disableTab(rcsTab, (!ENABLE_RCS && !ENABLE_RECOIL_CROSSHAIR))
            rcsTab.rcsSmoothingSlider.isDisabled = !ENABLE_RCS
            rcsTab.rcsReturnAim.isDisabled = !ENABLE_RCS
            true
        }

        //Create Enable_Recoil_Crosshair Toggle
        //val enableRCrosshair = VisTextButton("ENABLE_RECOIL_CROSSHAIR", "toggle")
        Tooltip.Builder("Whether or not to enable the recoil crosshair").target(enableRCrosshair).build()
        enableRCrosshair.isChecked = ENABLE_RECOIL_CROSSHAIR
        enableRCrosshair.changed { _, _ ->
            ENABLE_RECOIL_CROSSHAIR = enableRCrosshair.isChecked
            mainTabbedPane.disableTab(rcsTab, (!ENABLE_RCS && !ENABLE_RECOIL_CROSSHAIR))
            rcsTab.apply {
                rCrosshairWidthSlider.isDisabled = !ENABLE_RECOIL_CROSSHAIR
                rcsCrosshairLengthSlider.isDisabled = !ENABLE_RECOIL_CROSSHAIR
                rCrosshairAlphaSlider.isDisabled = !ENABLE_RECOIL_CROSSHAIR
                rCrosshairColorShow.isDisabled = !ENABLE_RECOIL_CROSSHAIR
            }
            true
        }

        //Create Enable_Esp Toggle
        //val enableEsp = VisTextButton("ENABLE_ESP", "toggle")
        Tooltip.Builder("Whether or not to enable esp").target(enableEsp).build()
        enableEsp.isChecked = ENABLE_ESP
        enableEsp.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                ENABLE_ESP = enableEsp.isChecked

                val lastTab = mainTabbedPane.activeTab
                mainTabbedPane.disableTab(espTab, !ENABLE_ESP)
                mainTabbedPane.switchTab(lastTab)

                if (!ENABLE_ESP) {
                    disableEsp()
                }
            }
        }

        //Create Enable_Bone_Trigger Toggle
        //val enableBoneTrigger = VisTextButton("ENABLE_BONE_TRIGGER", "toggle")
        Tooltip.Builder("Whether or not to enable bone trigger").target(enableBoneTrigger).build()
        enableBoneTrigger.isChecked = ENABLE_BONE_TRIGGER
        enableBoneTrigger.changed { _, _ ->
            ENABLE_BONE_TRIGGER = enableBoneTrigger.isChecked
            mainTabbedPane.disableTab(bTrigTab, !ENABLE_BONE_TRIGGER)
        }

        //Create Enable_Reduced_Flash Toggle
        //val enableReducedFlash = VisTextButton("ENABLE_REDUCED_FLASH", "toggle")
        Tooltip.Builder("Whether or not to enable reduced flash").target(enableReducedFlash).build()
        enableReducedFlash.isChecked = ENABLE_REDUCED_FLASH
        enableReducedFlash.changed { _, _ ->
            ENABLE_REDUCED_FLASH = enableReducedFlash.isChecked
            true
        }

        //Create Enable_Bomb_Timer Toggle
        //val enableBombTimer = VisTextButton("ENABLE_BOMB_TIMER", "toggle")
        Tooltip.Builder("Whether or not to enable bomb timer").target(enableBombTimer).build()
        enableBombTimer.isChecked = ENABLE_BOMB_TIMER
        enableBombTimer.changed { _, _ ->
            ENABLE_BOMB_TIMER = enableBombTimer.isChecked
            true
        }

        //Add all items to label for tabbed pane content
        table.add(enableBunnyHop).row() //Add Enable_Bunny_Hop Toggle
        table.add(enableRCS).row() //Add Enable_Rcs Toggle
        table.add(enableRCrosshair).row() //Add Enable_RCrosshair Toggle
        table.add(enableEsp).row() //Add Enable_RCrosshair Toggle
        table.add(enableBoneTrigger).row() //Add Enable_Bone_Trigger Toggle
        table.add(enableReducedFlash).row() //Add Enable_Reduced_Flash Toggle
        table.add(enableBombTimer).row() //Add Enable_Bomb_Timer Toggle
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Scripts"
    }
}