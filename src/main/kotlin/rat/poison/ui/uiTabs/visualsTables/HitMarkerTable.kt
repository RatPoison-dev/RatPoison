package rat.poison.ui.uiTabs.visualsTables

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.ui.uiElements.VisCheckBoxCustom
import rat.poison.ui.uiElements.VisColorPickerCustom
import rat.poison.ui.uiElements.VisSliderCustom
import rat.poison.ui.uiTabs.hitMarkerTable

class HitMarkerTable: VisTable(false) {
    //Init labels/sliders/boxes that show values here
    val hitMarker = VisCheckBoxCustom("Enable", "ENABLE_HITMARKER")
    val hitMarkerOutline = VisCheckBoxCustom("Outline", "HITMARKER_OUTLINE")
    val hitMarkerCombo = VisCheckBoxCustom("Combo #", "HITMARKER_COMBO")
    val hitMarkerRecoilPos = VisCheckBoxCustom("Recoil Position", "HITMARKER_RECOIL_POSITION")

    val hitMarkerSpacing = VisSliderCustom("Spacing", "HITMARKER_SPACING", 0F, 20F,1F, true, labelWidth = 175F, barWidth = 117F)
    val hitMarkerLength = VisSliderCustom("Line Length", "HITMARKER_LENGTH", 1F, 50F, 1F, true, labelWidth = 175F, barWidth = 117F)
    val hitMarkerWidth = VisSliderCustom("Line Width", "HITMARKER_WIDTH", 1F, 10F, 1F, true, labelWidth = 175F, barWidth = 117F)

    val hitMarkerColor = VisColorPickerCustom("Hitmarker", "HITMARKER_COLOR")
    val hitMarkerOutlineColor = VisColorPickerCustom("Outline", "HITMARKER_OUTLINE_COLOR")
    val hitMarkerComboColor = VisColorPickerCustom("Combo", "HITMARKER_COMBO_COLOR")

    val mainColorLabel = VisLabel("Main Color")
    val outlineColorLabel = VisLabel("Outline Color")
    val comboColorLabel = VisLabel("Combo Color")

    init {
        val label = VisLabel("Hit Marker")
        label.setColor(1F, 1F, 1F, 1F)

        add(label).colspan(2).expandX().padTop(4F).row()
        addSeparator().colspan(2).width(200F).top().height(2F).padBottom(8F)

        add(hitMarker).colspan(2).left().row()
        add(hitMarkerOutline).colspan(2).left().row()
        add(hitMarkerCombo).colspan(2).left().row()
        add(hitMarkerRecoilPos).colspan(2).left().row()
        add(hitMarkerSpacing).colspan(2).left().row()
        add(hitMarkerLength).colspan(2).left().row()
        add(hitMarkerWidth).colspan(2).left().row()

        add(mainColorLabel).left().padRight(175F - mainColorLabel.width)
        add(hitMarkerColor).left().expandX().row()

        add(outlineColorLabel).left().padRight(175F - outlineColorLabel.width)
        add(hitMarkerOutlineColor).left().expandX().row()

        add(comboColorLabel).left().padRight(175F - comboColorLabel.width)
        add(hitMarkerComboColor).left().expandX().row()
    }
}

fun hitMarkerTabUpdate() {
    hitMarkerTable.apply {
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

fun hitMarkerTableDisable(bool: Boolean, col: Color) {
    hitMarkerTable.apply {
        hitMarker.disable(bool)
        hitMarkerOutline.disable(bool)
        hitMarkerCombo.disable(bool)
        hitMarkerRecoilPos.disable(bool)
        hitMarkerSpacing.disable(bool, col)
        hitMarkerLength.disable(bool, col)
        hitMarkerWidth.disable(bool, col)
        hitMarkerColor.disable(bool)
        hitMarkerOutlineColor.disable(bool)
        hitMarkerComboColor.disable(bool)

        mainColorLabel.color = col
        outlineColorLabel.color = col
        comboColorLabel.color = col
    }
}