package rat.poison.ui.tabs.misctabs

import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.ui.tabs.fovChangerTable
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class FOVChangerTable: VisTable(false) {
    val fovChanger = VisCheckBoxCustom("Fov Changer", "ENABLE_FOV_CHANGER")
    val fovDefault = VisSliderCustom("Default FOV", "FOV_DEFAULT", 10F, 150F, 1F, true)
    val fovSmoothing = VisCheckBoxCustom("Smooth FOV Changes", "FOV_SMOOTH")
    val fovSniperDefault = VisSliderCustom("Default FOV", "FOV_SNIPER_DEFAULT", 10F, 150F, 1F, true)
    val fovSniperZoom1 = VisSliderCustom("Zoom 1 FOV", "FOV_ZOOM_1", 10F, 150F, 1F, true)
    val fovSniperZoom2 = VisSliderCustom("Zoom 2 FOV", "FOV_ZOOM_2", 10F, 150F, 1F, true)

    init {
        add(fovChanger).expandX().left().row()
        add(fovDefault).expandX().left().row()
        add(fovSmoothing).expandX().left().row()
        add(fovSniperDefault).expandX().left().row()
        add(fovSniperZoom1).expandX().left().row()
        add(fovSniperZoom2).expandX().left().row()
    }
}

fun fovChangerTabUpdate() {
    fovChangerTable.apply {
        fovChanger.update()
        fovDefault.update()
        fovSmoothing.update()
        fovSniperDefault.update()
        fovSniperZoom1.update()
        fovSniperZoom2.update()
    }
}