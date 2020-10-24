package rat.poison.ui.tabs.misctabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.toLocale
import rat.poison.ui.tabs.fovChangerTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class FOVChangerTab: Tab(false, false) {
    private val table = VisTable()
    val fovChanger = VisCheckBoxCustom("Fov Changer", "ENABLE_FOV_CHANGER")
    val fovDefault = VisSliderCustom("Default FOV", "FOV_DEFAULT", 10F, 150F, 1F, true)
    val fovSmoothing = VisCheckBoxCustom("Smooth FOV Changes", "FOV_SMOOTH")
    val fovSniperDefault = VisSliderCustom("Default FOV", "FOV_SNIPER_DEFAULT", 10F, 150F, 1F, true)
    val fovSniperZoom1 = VisSliderCustom("Zoom 1 FOV", "FOV_ZOOM_1", 10F, 150F, 1F, true)
    val fovSniperZoom2 = VisSliderCustom("Zoom 2 FOV", "FOV_ZOOM_2", 10F, 150F, 1F, true)
    init {
        table.padLeft(25F)
        table.padRight(25F)

        table.add(fovChanger).left().row()
        table.add(fovDefault).left().row()
        table.add(fovSmoothing).left().row()
        table.add(fovSniperDefault).left().row()
        table.add(fovSniperZoom1).left().row()
        table.add(fovSniperZoom2).left().row()
    }

    override fun getTabTitle(): String {
        return "FOV-Changer".toLocale()
    }

    override fun getContentTable(): Table {
        return table
    }
}

fun fovChangerTabUpdate() {
    fovChangerTab.apply {
        fovChanger.update()
        fovDefault.update()
        fovSmoothing.update()
        fovSniperDefault.update()
        fovSniperZoom1.update()
        fovSniperZoom2.update()
    }
}