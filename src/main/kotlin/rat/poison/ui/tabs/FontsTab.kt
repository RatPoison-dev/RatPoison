package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.ListView
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.curSettings
import rat.poison.overlay.App.assetManager
import rat.poison.toLocale
import rat.poison.ui.changed
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSliderCustom
import rat.poison.ui.uiPanels.fontsTab
import rat.poison.utils.updateFonts

class FontsTab : Tab(false, false) {
    val table = VisTable(true)
    private var fontLabel = VisLabel("Fonts".toLocale(), Align.center)
    private var fontListAdapter = ListAdapter(ArrayList())
    private var fontSelectionList = ListView(fontListAdapter)
    private val loadFontButton = VisTextButton("LOAD_FONT".toLocale())
    val fontSize = VisSliderCustom("Font Size", "FONT_SIZE", 3F, 40F, 1F, true, labelWidth = 150F, barWidth = 150F)
    val borderWidth = VisSliderCustom("Border Width", "FONT_BORDER_WIDTH", 0F, 40F, 1F, true, labelWidth = 150F, barWidth = 150F)
    val borderStraight = VisCheckBoxCustom("Border Straight", "FONT_BORDER_USE_STRAIGHT")
    val fontGamma = VisSliderCustom("Gamma", "FONT_GAMMA", 0F, 10F, 0.1F, false, labelWidth = 150F, barWidth = 150F)
    val borderGamma = VisSliderCustom("Border Gamma", "FONT_BORDER_GAMMA", 0F, 10F, 0.1F, false, labelWidth = 150F, barWidth = 150F)
    val shadowOffsetX = VisSliderCustom("Shadow Offset X", "FONT_SHADOW_OFFSET_X", -10F, 100F, 1F, true, labelWidth = 150F, barWidth = 150F)
    val shadowOffsetY = VisSliderCustom("Shadow Offset Y", "FONT_SHADOW_OFFSET_Y", -10F, 100F, 1F, true, labelWidth = 150F, barWidth = 150F)
    val fontKerning = VisCheckBoxCustom("Kerning", "FONT_INCLUDE_KERNING")
    val fontFlip = VisCheckBoxCustom("Flip", "FONT_FLIP")
    val genMipMaps = VisCheckBoxCustom("Generate mip maps", "FON_GEN_MIP_MAPS")
    val fontColor = VisColorPickerCustom("Font Color", "FONT_COLOR")
    val fontBorderColor = VisColorPickerCustom("Border Color", "FONT_BORDER_COLOR")
    init {
        fontSelectionList.updatePolicy = ListView.UpdatePolicy.ON_DRAW

        updateFontsList()

        loadFontButton.changed { _, _ ->
            val selection = fontListAdapter.selection
            if (selection.size > 0) {
                curSettings["FONT"] = selection[0]
                assetManager.updateFonts()
            }
            true
        }

        fontSize.changed { _, _ -> updateFonts = true; true }
        borderWidth.changed { _, _ -> updateFonts = true; true }
        borderStraight.changed { _, _ -> updateFonts = true; true }
        fontGamma.changed { _, _ -> updateFonts = true; true }
        borderGamma.changed { _, _ -> updateFonts = true; true }
        shadowOffsetX.changed { _, _ -> updateFonts = true; true }
        shadowOffsetY.changed { _, _ -> updateFonts = true; true }
        fontKerning.changed { _, _ -> updateFonts = true; true }
        fontFlip.changed { _, _ -> updateFonts = true; true }
        genMipMaps.changed { _, _ -> updateFonts = true; true }
        fontBorderColor.changed { _, _ -> updateFonts = true; true }
        fontColor.changed { _, _ -> updateFonts = true; true }

        //left elements
        var leftTable = VisTable()
        leftTable.add(fontLabel).left().width(120F).padBottom(10F).row()
        leftTable.add(fontSelectionList.mainTable).left().width(120F).height(225F).row()
        leftTable.add(loadFontButton).left().top()

        table.add(leftTable).left().colspan(2).top()
        table.addSeparator(true).colspan(2).padLeft(20F).padRight(20F)
        
        val rightTable = VisTable()
        rightTable.add(fontSize).left().row()
        rightTable.add(borderWidth).left().row()
        rightTable.add(fontGamma).left().row()
        rightTable.add(borderStraight).left().row()
        rightTable.add(borderGamma).left().row()
        rightTable.add(shadowOffsetX).left().row()
        rightTable.add(shadowOffsetY).left().row()
        rightTable.add(fontKerning).left().row()
        rightTable.add(fontFlip).left().row()
        rightTable.add(genMipMaps).left().row()
        rightTable.add(fontColor).left().row()
        rightTable.add(fontBorderColor).left().row()
        table.add(rightTable).right().padLeft(20F)

    }

    private fun updateFontsList() {
        if (VisUI.isLoaded()) {
            fontListAdapter.clear()
            assetManager.updateFontsList()

            fontListAdapter.addAll(assetManager.fonts.keys.map { it.split("\\").last().split(".ttf").first() })
        }
    }

    override fun getTabTitle(): String {
        return "Fonts".toLocale()
    }

    override fun getContentTable(): Table {
        return table
    }
}

fun fontsTabUpdate() {
    fontsTab.apply {
        fontSize.update()
        borderWidth.update()
        borderStraight.update()
        borderGamma.update()
        shadowOffsetX.update()
        shadowOffsetY.update()
        fontKerning.update()
        fontGamma.update()
        fontFlip.update()
        genMipMaps.update()
        fontBorderColor.update()
        fontColor.update()
    }
}