package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.toLocale
import rat.poison.ui.tabs.hitMarkerTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class HitMarkerTab : Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    val hitMarker = VisCheckBoxCustom("Enable", "ENABLE_HITMARKER")
    val hitMarkerOutline = VisCheckBoxCustom("Outline", "HITMARKER_OUTLINE")
    val hitMarkerCombo = VisCheckBoxCustom("Combo #", "HITMARKER_COMBO")
    val hitMarkerRecoilPos = VisCheckBoxCustom("Recoil Position", "HITMARKER_RECOIL_POSITION")

    val hitMarkerSpacing = VisSliderCustom("Spacing", "HITMARKER_SPACING", 0F, 20F,1F, true, width1 = 200F, width2 = 250F)
    val hitMarkerLength = VisSliderCustom("Line Length", "HITMARKER_LENGTH", 1F, 50F, 1F, true, width1 = 200F, width2 = 250F)
    val hitMarkerWidth = VisSliderCustom("Line Width", "HITMARKER_WIDTH", 1F, 10F, 1F, true, width1 = 200F, width2 = 250F)

    val hitMarkerColor = VisColorPickerCustom("Hitmarker", "HITMARKER_COLOR")
    val hitMarkerOutlineColor = VisColorPickerCustom("Outline", "HITMARKER_OUTLINE_COLOR")
    val hitMarkerComboColor = VisColorPickerCustom("Combo", "HITMARKER_COMBO_COLOR")

    init {
        table.padLeft(25F)
        table.padRight(25F)

        val colTab = VisTable()
        colTab.add(hitMarkerColor).width(150F).padRight(2F)
        colTab.add(hitMarkerOutlineColor).width(150F).padRight(2F)
        colTab.add(hitMarkerComboColor).width(150F).padRight(2F)

        table.add(hitMarker).left().row()
        table.add(hitMarkerOutline).left().row()
        table.add(hitMarkerCombo).left().row()
        table.add(hitMarkerRecoilPos).left().row()
        table.add(hitMarkerSpacing).left().row()
        table.add(hitMarkerLength).left().row()
        table.add(hitMarkerWidth).left().row()
        table.add(colTab).width(444F).center()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Hitmarker".toLocale()
    }
}

fun hitMarkerTabUpdate() {
    hitMarkerTab.apply {
        hitMarker.update()
        hitMarkerOutline.update()
        hitMarkerCombo.update()
        hitMarkerRecoilPos.update()
        hitMarkerSpacing.update()
        hitMarkerLength.update()
        hitMarkerWidth.update()
        hitMarkerColor.update()
        hitMarkerOutlineColor.update()
        hitMarkerComboColor.update()
    }
}