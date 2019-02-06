package rat.poison.ui.tabs.esptabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisCheckBox
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.settings.*
import rat.poison.ui.changed

class SkeletonEspTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val skeletonEsp = VisCheckBox("Skeleton Esp")

    val showTeam = VisCheckBox("Show Team")
    val showEnemies = VisCheckBox("Show Enemies")

    init {
        //Create Skeleton_Esp Toggle
        Tooltip.Builder("Whether or not to enable skeleton esp").target(skeletonEsp).build()
        if (SKELETON_ESP) skeletonEsp.toggle()
        skeletonEsp.changed { _, _ ->
            SKELETON_ESP = skeletonEsp.isChecked
            true
        }

        //Create Show_Team Toggle
        Tooltip.Builder("Whether or not to show team with esp").target(showTeam).build()
        if (SKELETON_SHOW_TEAM) showTeam.toggle()
        showTeam.changed { _, _ ->
            SKELETON_SHOW_TEAM = showTeam.isChecked
            true
        }

        //Create Show_Enemies Toggle
        Tooltip.Builder("Whether or not to show enemies with esp").target(showEnemies).build()
        if (SKELETON_SHOW_ENEMIES) showEnemies.toggle()
        showEnemies.changed { _, _ ->
            SKELETON_SHOW_ENEMIES = showEnemies.isChecked
            true
        }


        //Add all items to label for tabbed pane content
        table.add(skeletonEsp).colspan(2).row()
        table.add(showTeam)
        table.add(showEnemies)
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Skeleton"
    }
}