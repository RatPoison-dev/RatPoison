package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.ui.tabs.skeletonEspTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom

class SkeletonEspTab : Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    val skeletonEsp = VisCheckBoxCustom("Skeleton", "SKELETON_ESP")

    val showTeam = VisCheckBoxCustom("Teammates", "SKELETON_SHOW_TEAM")
    val showEnemies = VisCheckBoxCustom("Enemies", "SKELETON_SHOW_ENEMIES")

    init {
        table.padLeft(25F)
        table.padRight(25F)

        table.add(skeletonEsp).left().row()
        table.add(showTeam).padRight(225F - showTeam.width).left()
        table.add(showEnemies).padRight(225F - showEnemies.width).left()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Skeleton"
    }
}

fun skeletonEspTabUpdate() {
    skeletonEspTab.apply {
        skeletonEsp.update()
        showTeam.update()
        showEnemies.update()
    }
}