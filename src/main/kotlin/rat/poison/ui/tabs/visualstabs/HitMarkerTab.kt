package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.curLocalization
import rat.poison.ui.tabs.hitMarkerTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class HitMarkerTab : Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    val hitMarker = VisCheckBoxCustom(curLocalization["ENABLE"], "ENABLE_HITMARKER", nameInLocalization = "ENABLE")
    val hitMarkerOutline = VisCheckBoxCustom(curLocalization["HITMARKER_OUTLINE"], "HITMARKER_OUTLINE", nameInLocalization = "HITMARKER_OUTLINE")
    val hitMarkerCombo = VisCheckBoxCustom(curLocalization["HITMARKER_COMBO"], "HITMARKER_COMBO", nameInLocalization = "HITMARKER_COMBO")
    val hitMarkerRecoilPos = VisCheckBoxCustom(curLocalization["HITMARKER_RECOIL_POSITION"], "HITMARKER_RECOIL_POSITION", nameInLocalization = "HITMARKER_RECOIL_POSITION")

    val hitMarkerSpacing = VisSliderCustom(curLocalization["HITMARKER_SPACING"], "HITMARKER_SPACING", 0F, 20F,1F, true, width1 = 200F, width2 = 250F, nameInLocalization = "HITMARKER_SPACING")
    val hitMarkerLength = VisSliderCustom(curLocalization["HITMARKER_LENGTH"], "HITMARKER_LENGTH", 1F, 50F, 1F, true, width1 = 200F, width2 = 250F, nameInLocalization = "HITMARKER_LENGTH")
    val hitMarkerWidth = VisSliderCustom(curLocalization["LINE_WIDTH"], "HITMARKER_WIDTH", 1F, 10F, 1F, true, width1 = 200F, width2 = 250F, nameInLocalization = "LINE_WIDTH")

    val hitMarkerColor = VisColorPickerCustom(curLocalization["HITMARKER_COLOR"], "HITMARKER_COLOR", nameInLocalization = "HITMARKER_COLOR")
    val hitMarkerOutlineColor = VisColorPickerCustom(curLocalization["HITMARKER_OUTLINE"], "HITMARKER_OUTLINE_COLOR", nameInLocalization = "HITMARKER_OUTLINE")
    val hitMarkerComboColor = VisColorPickerCustom(curLocalization["HITMARKER_COMBO_COLOR"], "HITMARKER_COMBO_COLOR", nameInLocalization = "HITMARKER_COMBO_COLOR")

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
        return curLocalization["HITMARKER_TAB_NAME"]
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
        hitMarkerColor.updateTitle()
        hitMarkerOutlineColor.update()
        hitMarkerComboColor.update()
        hitMarkerComboColor.updateTitle()
        hitMarkerOutlineColor.updateTitle()
    }
}