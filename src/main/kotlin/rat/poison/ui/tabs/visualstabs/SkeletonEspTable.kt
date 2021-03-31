package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.ui.tabs.boxEspTable
import rat.poison.ui.tabs.skeletonEspTable
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSelectBoxCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

//Swap VisSelectBoxCustom to showText false is mainText is " "
class SkeletonEspTable: VisTable(false) {
    //Init labels/sliders/boxes that show values here
    val skeletonEsp = VisCheckBoxCustom("Enable", "SKELETON_ESP")
    val showTeamSkeleton = VisCheckBoxCustom("Teammates", "SKELETON_SHOW_TEAM")
    val showEnemiesSkeleton = VisCheckBoxCustom("Enemies", "SKELETON_SHOW_ENEMIES")

    init {
        val label = VisLabel("Skeleton")
        label.setColor(1F, 1F, 1F, 1F)

        add(label).colspan(2).expandX().padTop(4F).row()
        addSeparator().colspan(2).width(200F).top().height(2F).padBottom(8F)

        add(skeletonEsp).colspan(2).left().row()

        add(showTeamSkeleton).left().padRight(175F - showTeamSkeleton.width)
        add(showEnemiesSkeleton).left().expandX().row()
    }
}

fun skeletonEspTableUpdate() {
    skeletonEspTable.skeletonEsp.update()
    skeletonEspTable.showTeamSkeleton.update()
    skeletonEspTable.showEnemiesSkeleton.update()
}

fun skeletonEspTableDisable(bool: Boolean) {
    skeletonEspTable.skeletonEsp.disable(bool)
    skeletonEspTable.showTeamSkeleton.disable(bool)
    skeletonEspTable.showEnemiesSkeleton.disable(bool)
}