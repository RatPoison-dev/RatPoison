package rat.poison.ui.tabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.color.ColorPicker
import com.kotcrab.vis.ui.widget.color.ColorPickerAdapter
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.App
import rat.poison.boolToStr
import rat.poison.curSettings
import rat.poison.settings.*
import rat.poison.strToBool
import rat.poison.ui.changed
import rat.poison.ui.rcsTab
import kotlin.math.round

class RcsTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val enableRCS = VisCheckBox("Enable RCS") //RCS

    val rcsSmoothingLabel = VisLabel("RCS Smoothing: " + curSettings["RCS_SMOOTHING"] + when(curSettings["RCS_SMOOTHING"]!!.length) {3->"" 2->"  " else->"    "})
    val rcsSmoothingSlider = VisSlider(0.1F, 1F, .02F, false)
    val rcsReturnAim = VisCheckBox("Return Aim")

    val enableRCrosshair = VisCheckBox("Recoil Crosshair")
    val enableSCrosshair = VisCheckBox("Scope Compatible")

    val rCrosshairWidthLabel = VisLabel("RCrosshair Width: " + curSettings["RCROSSHAIR_WIDTH"])
    val rCrosshairWidthSlider = VisSlider(1F, 5F, 1F, false)
    val rCrosshairLengthLabel = VisLabel("RCrosshair Length: " + curSettings["RCROSSHAIR_LENGTH"] + when(curSettings["RCROSSHAIR_LENGTH"]!!.length) {3->"" 2->"  " else->"    "})
    val rCrosshairLengthSlider = VisSlider(3F, 100F, 1F, false)
    val rCrosshairXOffsetLabel = VisLabel("RCrosshair X Offset: " + curSettings["RCROSSHAIR_XOFFSET"] + when(curSettings["RCROSSHAIR_XOFFSET"]!!.length) {2->"  " else->"    "})
    val rCrosshairXOffsetSlider = VisSlider(-48F, 48F, 1F, false)
    val rCrosshairYOffsetLabel = VisLabel("RCrosshair Y Offset: " + curSettings["RCROSSHAIR_YOFFSET"] + when(curSettings["RCROSSHAIR_YOFFSET"]!!.length) {2->"  " else->"    "})
    val rCrosshairYOffsetSlider = VisSlider(-48F, 48F, 1F, false)
    val rCrosshairAlphaLabel = VisLabel("RCrosshair Alpha: " + curSettings["RCROSSHAIR_ALPHA"])
    val rCrosshairAlphaSlider = VisSlider(0.1F, 1F, 0.1F, false)
    val rCrosshairColorShow = VisTextButton("Set RCrosshair Color")

    init {
        //Create RCS Toggle
        Tooltip.Builder("Whether or not to enable the recoil control system").target(enableRCS).build()
        enableRCS.isChecked = curSettings["ENABLE_RCS"]!!.strToBool()
        enableRCS.changed { _, _ ->
            curSettings["ENABLE_RCS"] = enableRCS.isChecked.boolToStr()

            val bool = !curSettings["ENABLE_RCS"]!!.strToBool()
            var color = Color(255F, 255F, 255F, 1F)
            if (bool) {
                color = Color(105F, 105F, 105F, .2F)
            }
            rcsSmoothingLabel.color = color
            rcsSmoothingSlider.isDisabled = bool
            rcsReturnAim.isDisabled = bool
            true
        }

        //Create RCS Smoothing Slider
        val rcsSmoothing = VisTable()
        Tooltip.Builder("The smoothing of the recoil control system").target(rcsSmoothing).build()
        rcsSmoothingSlider.value = curSettings["RCS_SMOOTHING"]!!.toFloat()
        rcsSmoothingSlider.changed { _, _ ->
            curSettings["RCS_SMOOTHING"] = (round(rcsSmoothingSlider.value.toDouble() * 100.0)/100.0).toString()
            rcsSmoothingLabel.setText("RCS Smoothing: " + curSettings["RCS_SMOOTHING"] + when(curSettings["RCS_SMOOTHING"]!!.length) {4->"" 3->"  " 2->"    " else->"      "})
        }
        rcsSmoothing.add(rcsSmoothingLabel).width(200F)
        rcsSmoothing.add(rcsSmoothingSlider).width(250F)

        //Create RCS Return Aim Toggle
        Tooltip.Builder("Whether or not to reset your crosshair after spraying").target(rcsReturnAim).build()
        if (curSettings["RCS_RETURNAIM"]!!.strToBool()) rcsReturnAim.toggle()
        rcsReturnAim.changed { _, _ ->
            curSettings["RCS_RETURNAIM"] = rcsReturnAim.isChecked.boolToStr()
            true
        }

        //Create RCrosshair Toggle
        Tooltip.Builder("Whether or not to enable the recoil crosshair").target(enableRCrosshair).build()
        enableRCrosshair.isChecked = curSettings["ENABLE_RECOIL_CROSSHAIR"]!!.strToBool()
        enableRCrosshair.changed { _, _ ->
            curSettings["ENABLE_RECOIL_CROSSHAIR"] = enableRCrosshair.isChecked.boolToStr()
            rcsTab.apply {
                val bool = !(curSettings["ENABLE_RECOIL_CROSSHAIR"]!!.strToBool() || curSettings["ENABLE_SNIPER_CROSSHAIR"]!!.strToBool())
                var color = Color(255F, 255F, 255F, 1F)
                if (bool) {
                    color = Color(105F, 105F, 105F, .2F)
                }

                rCrosshairWidthSlider.isDisabled = bool
                rCrosshairLengthSlider.isDisabled = bool
                rCrosshairAlphaSlider.isDisabled = bool
                rCrosshairXOffsetSlider.isDisabled = bool
                rCrosshairYOffsetSlider.isDisabled = bool
                rCrosshairColorShow.isDisabled = bool

                rCrosshairWidthLabel.color = color
                rCrosshairLengthLabel.color = color
                rCrosshairAlphaLabel.color = color
                rCrosshairXOffsetLabel.color = color
                rCrosshairYOffsetLabel.color = color
            }
            true
        }

        //Create SCrosshair Toggle
        Tooltip.Builder("Whether or not to enable the sniper crosshair").target(enableSCrosshair).build()
        enableSCrosshair.isChecked = curSettings["ENABLE_SNIPER_CROSSHAIR"]!!.strToBool()
        enableSCrosshair.changed { _, _ ->
            curSettings["ENABLE_SNIPER_CROSSHAIR"] = enableSCrosshair.isChecked.boolToStr()
            rcsTab.apply {
                val bool = !(curSettings["ENABLE_RECOIL_CROSSHAIR"]!!.strToBool() || curSettings["ENABLE_SNIPER_CROSSHAIR"]!!.strToBool())
                var color = Color(255F, 255F, 255F, 1F)
                if (bool) {
                    color = Color(105F, 105F, 105F, .2F)
                }

                rCrosshairWidthSlider.isDisabled = bool
                rCrosshairLengthSlider.isDisabled = bool
                rCrosshairAlphaSlider.isDisabled = bool
                rCrosshairXOffsetSlider.isDisabled = bool
                rCrosshairYOffsetSlider.isDisabled = bool
                rCrosshairColorShow.isDisabled = bool

                rCrosshairWidthLabel.color = color
                rCrosshairLengthLabel.color = color
                rCrosshairAlphaLabel.color = color
                rCrosshairXOffsetLabel.color = color
                rCrosshairYOffsetLabel.color = color
            }
            true
        }

        //Create RCrosshair Width
        val rCrosshairWidth = VisTable()
        Tooltip.Builder("The width of the bars of the recoil crosshair, odd number is recommended").target(rCrosshairWidth).build()
        rCrosshairWidthSlider.value = RCROSSHAIR_WIDTH.toFloat()
        rCrosshairWidthSlider.changed { _, _ ->
            curSettings["RCROSSHAIR_WIDTH"] = rCrosshairWidthSlider.value.toInt().toString()
            rCrosshairWidthLabel.setText("RCrosshair Width: " + curSettings["RCROSSHAIR_WIDTH"])
        }
        rCrosshairWidth.add(rCrosshairWidthLabel).width(200F)
        rCrosshairWidth.add(rCrosshairWidthSlider).width(250F)

        //Create RCrosshair Length
        val rCrosshairLength = VisTable()
        Tooltip.Builder("The length of the bars of the recoil crosshair, odd number is recommended").target(rCrosshairLength).build()
        rCrosshairLengthSlider.value = RCROSSHAIR_LENGTH.toFloat()
        rCrosshairLengthSlider.changed { _, _ ->
            curSettings["RCROSSHAIR_LENGTH"] = rCrosshairLengthSlider.value.toInt().toString()
            rCrosshairLengthLabel.setText("RCrosshair Length: " + curSettings["RCROSSHAIR_LENGTH"] + when(curSettings["RCROSSHAIR_LENGTH"]!!.length) { 2->"  " else ->"    "})
        }
        rCrosshairLength.add(rCrosshairLengthLabel).width(200F)
        rCrosshairLength.add(rCrosshairLengthSlider).width(250F)

        //Create RCrosshair X Offset
        val rCrosshairXOffset = VisTable()
        Tooltip.Builder("The X coordinate offset of the crosshair").target(rCrosshairXOffset).build()
        rCrosshairXOffsetSlider.value = RCROSSHAIR_XOFFSET.toFloat()
        rCrosshairXOffsetSlider.changed { _, _ ->
            curSettings["RCROSSHAIR_XOFFSET"] = rCrosshairXOffsetSlider.value.toInt().toString()
            rCrosshairXOffsetLabel.setText("RCrosshair X Offset: " + curSettings["RCROSSHAIR_XOFFSET"] + when(curSettings["RCROSSHAIR_XOFFSET"]!!.length) { 2->"  " else ->"    "})
        }
        rCrosshairXOffset.add(rCrosshairXOffsetLabel).width(200F)
        rCrosshairXOffset.add(rCrosshairXOffsetSlider).width(250F)

        //Create RCrosshair Y Offset
        val rCrosshairYOffset = VisTable()
        Tooltip.Builder("The Y coordinate offset of the crosshair").target(rCrosshairYOffset).build()
        rCrosshairYOffsetSlider.value = RCROSSHAIR_YOFFSET.toFloat()
        rCrosshairYOffsetSlider.changed { _, _ ->
            curSettings["RCROSSHAIR_YOFFSET"] = rCrosshairYOffsetSlider.value.toInt().toString()
            rCrosshairYOffsetLabel.setText("RCrosshair Y Offset: " + curSettings["RCROSSHAIR_YOFFSET"] + when(curSettings["RCROSSHAIR_YOFFSET"]!!.length) { 2->"  " else ->"    "})
        }
        rCrosshairYOffset.add(rCrosshairYOffsetLabel).width(200F)
        rCrosshairYOffset.add(rCrosshairYOffsetSlider).width(250F)

        //Create RCrosshair Alpha
        val rCrosshairAlpha = VisTable()
        Tooltip.Builder("The alpha of the recoil crosshair").target(rCrosshairAlpha).build()
        rCrosshairAlphaSlider.value = curSettings["RCROSSHAIR_ALPHA"]!!.toFloat()
        rCrosshairAlphaSlider.changed { _, _ ->
            curSettings["RCROSSHAIR_ALPHA"] = (round(rCrosshairAlphaSlider.value.toDouble() * 10.0)/10.0).toString() //Round to 1 decimal place
            rCrosshairAlphaLabel.setText("RCrosshair Alpha: " + curSettings["RCROSSHAIR_ALPHA"])
        }
        rCrosshairAlpha.add(rCrosshairAlphaLabel).width(200F)
        rCrosshairAlpha.add(rCrosshairAlphaSlider).width(250F)

        //Create RCrosshair Color Picker
        val rCrosshairColor = VisTable()
        Tooltip.Builder("The color of the recoil crosshair").target(rCrosshairColor).build()
        rCrosshairColorShow.setColor(RCROSSHAIR_COLOR.red.toFloat(), RCROSSHAIR_COLOR.green.toFloat(), RCROSSHAIR_COLOR.blue.toFloat(), 1F)
        val rCrosshairColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                curSettings["RCROSSHAIR_COLOR"] = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble()).toString()
                newCol.a = 1F
                rCrosshairColorShow.color = newCol
                dispose()
            }

            override fun canceled(oldCol: Color) {
                dispose()
            }
        })

        rCrosshairColorShow.changed { _, _ ->
            App.menuStage.addActor(rCrosshairColorPicker.fadeIn())
        }

        rCrosshairColor.add(rCrosshairColorShow)

        table.padLeft(25F)
        table.padRight(25F)

        table.add(enableRCS).left().row()
        table.add(rcsSmoothing).left().row()
        table.add(rcsReturnAim).left().row()
        table.addSeparator()
        table.add(enableRCrosshair).left().row()
        table.add(enableSCrosshair).left().row()
        table.add(rCrosshairWidth).left().row()
        table.add(rCrosshairLength).left().row()
        table.add(rCrosshairXOffset).left().row()
        table.add(rCrosshairYOffset).left().row()
        table.add(rCrosshairAlpha).left().row()
        table.add(rCrosshairColor).left().row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "RCS"
    }
}