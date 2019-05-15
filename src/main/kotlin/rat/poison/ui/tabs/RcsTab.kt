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

class RcsTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val enableRCS = VisCheckBox("Enable RCS") //RCS

    val rcsSmoothingLabel = VisLabel("RCS Smoothing: " + curSettings["RCS_SMOOTHING"].toString() + when(curSettings["RCS_SMOOTHING"].toString().length) {3->"" 2->"  " else->"    "})
    val rcsSmoothingSlider = VisSlider(0.1F, 1F, .02F, false)
    val rcsReturnAim = VisCheckBox("Return Aim")


    val enableRCrosshair = VisCheckBox("Enable Recoil Crosshair")

    val rCrosshairWidthLabel = VisLabel("RCrosshair Width: " + curSettings["RCROSSHAIR_WIDTH"].toString())
    val rCrosshairWidthSlider = VisSlider(1F, 5F, 1F, false)
    val rCrosshairLengthLabel = VisLabel("RCrosshair Length: " + curSettings["RCROSSHAIR_LENGTH"].toString() + when(curSettings["RCROSSHAIR_LENGTH"].toString().length) {2->"  " else ->"    "})
    val rCrosshairLengthSlider = VisSlider(3F, 30F, 1F, false)
    val rCrosshairAlphaLabel = VisLabel("RCrosshair Alpha: " + curSettings["RCROSSHAIR_ALPHA"].toString())
    val rCrosshairAlphaSlider = VisSlider(0.1F, 1F, 0.1F, false)
    val rCrosshairColorShow = VisTextButton("Set RCrosshair Color")

    init {
        //Create RCS Toggle
        Tooltip.Builder("Whether or not to enable the recoil control system").target(enableRCS).build()
        enableRCS.isChecked = curSettings["ENABLE_RCS"]!!.strToBool()
        enableRCS.changed { _, _ ->
            curSettings["ENABLE_RCS"] = enableRCS.isChecked.boolToStr()
            rcsTab.rcsSmoothingSlider.isDisabled = !curSettings["ENABLE_RCS"]!!.strToBool()
            rcsTab.rcsReturnAim.isDisabled = !curSettings["ENABLE_RCS"]!!.strToBool()
            true
        }

        //Create RCS Smoothing Slider
        val rcsSmoothing = VisTable()
        Tooltip.Builder("The smoothing of the recoil control system").target(rcsSmoothing).build()
        //val rcsSmoothingLabel = VisLabel("RCS Smoothing: " + curSettings["RCS_SMOOTHING"].toString().toString())
        //val rcsSmoothingSlider = VisSlider(0.1F, 1F, .1F, false)
        rcsSmoothingSlider.value = curSettings["RCS_SMOOTHING"].toString().toFloat()
        rcsSmoothingSlider.changed { _, _ ->
            curSettings["RCS_SMOOTHING"] = (Math.round(rcsSmoothingSlider.value.toDouble() * 100.0)/100.0).toString()
            rcsSmoothingLabel.setText("RCS Smoothing: " + curSettings["RCS_SMOOTHING"].toString() + when(curSettings["RCS_SMOOTHING"].toString().length) {4->"" 3->"  " 2->"    " else->"      "})
        }
        rcsSmoothing.add(rcsSmoothingLabel).spaceRight(6F) //when gets rid of spaceright
        rcsSmoothing.add(rcsSmoothingSlider)

        //Create curSettings["RCS_RETURNAIM"]!!.strToBool()
        //val rcsReturnAim = VisCheckBox("Return Aim")
        Tooltip.Builder("Whether or not to reset your crosshair after spraying").target(rcsReturnAim).build()
        if (curSettings["RCS_RETURNAIM"]!!.strToBool()) rcsReturnAim.toggle()
        rcsReturnAim.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                curSettings["RCS_RETURNAIM"] = rcsReturnAim.isChecked.boolToStr()
            }
        }

        //Create ENABLE_RECOIL_CROSSHAIR Toggle
        Tooltip.Builder("Whether or not to enable the recoil crosshair").target(enableRCrosshair).build()
        enableRCrosshair.isChecked = curSettings["ENABLE_RECOIL_CROSSHAIR"]!!.strToBool()
        enableRCrosshair.changed { _, _ ->
            curSettings["ENABLE_RECOIL_CROSSHAIR"] = enableRCrosshair.isChecked.boolToStr()
            rcsTab.apply {
                rCrosshairWidthSlider.isDisabled = !curSettings["ENABLE_RECOIL_CROSSHAIR"]!!.strToBool()
                rCrosshairLengthSlider.isDisabled = !curSettings["ENABLE_RECOIL_CROSSHAIR"]!!.strToBool()
                rCrosshairAlphaSlider.isDisabled = !curSettings["ENABLE_RECOIL_CROSSHAIR"]!!.strToBool()
                rCrosshairColorShow.isDisabled = !curSettings["ENABLE_RECOIL_CROSSHAIR"]!!.strToBool()
            }
            true
        }

        //Create RCrosshair_Width
        val rCrosshairWidth = VisTable()
        Tooltip.Builder("The width of the bars of the recoil crosshair, odd number is recommended").target(rCrosshairWidth).build()
        rCrosshairWidthSlider.value = RCROSSHAIR_WIDTH.toFloat()
        rCrosshairWidthSlider.changed { _, _ ->
            curSettings["RCROSSHAIR_WIDTH"] = rCrosshairWidthSlider.value.toInt().toString()
            rCrosshairWidthLabel.setText("RCrosshair Width: " + curSettings["RCROSSHAIR_WIDTH"].toString())
        }
        rCrosshairWidth.add(rCrosshairWidthLabel).spaceRight(6F) //when gets rid of spaceright
        rCrosshairWidth.add(rCrosshairWidthSlider)

        //Create RCrosshair_Length
        val rCrosshairLength = VisTable()
        Tooltip.Builder("The length of the bars of the recoil crosshair, odd number is recommended").target(rCrosshairLength).build()
        //val rCrosshairLengthLabel = VisLabel("RCrosshair Length: $RCROSSHAIR_LENGTH" + when(RCROSSHAIR_LENGTH.toString().length) { 2->"  " else ->"    "})
        //val rCrosshairLengthSlider = VisSlider(3F, 30F, 1F, false)
        rCrosshairLengthSlider.value = RCROSSHAIR_LENGTH.toFloat()
        rCrosshairLengthSlider.changed { _, _ ->
            curSettings["RCROSSHAIR_LENGTH"] = rCrosshairLengthSlider.value.toInt().toString()
            rCrosshairLengthLabel.setText("RCrosshair Length: " + curSettings["RCROSSHAIR_LENGTH"].toString() + when(curSettings["RCROSSHAIR_LENGTH"].toString().length) { 2->"  " else ->"    "})
        }
        rCrosshairLength.add(rCrosshairLengthLabel).spaceRight(6F) //when gets rid of spaceright
        rCrosshairLength.add(rCrosshairLengthSlider)

        //Create RCrosshair_Alpha
        val rCrosshairAlpha = VisTable()
        Tooltip.Builder("The alpha of the recoil crosshair").target(rCrosshairAlpha).build()
        rCrosshairAlphaSlider.value = curSettings["RCROSSHAIR_ALPHA"].toString().toFloat()
        rCrosshairAlphaSlider.changed { _, _ ->
            curSettings["RCROSSHAIR_ALPHA"] = (Math.round(rCrosshairAlphaSlider.value.toDouble() * 10.0)/10.0).toString() //Round to 1 decimal place
            rCrosshairAlphaLabel.setText("RCrosshair Alpha: " + curSettings["RCROSSHAIR_ALPHA"].toString())
        }
        rCrosshairAlpha.add(rCrosshairAlphaLabel).spaceRight(6F)
        rCrosshairAlpha.add(rCrosshairAlphaSlider)

        //Create RCrosshair_Color Picker
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



        //Add all items to label for tabbed pane content
        table.add(enableRCS).row() //Add curSettings["ENABLE_RCS"]!!.strToBool() Toggle
        table.add(rcsSmoothing).row() //Add curSettings["RCS_SMOOTHING"].toString() Slider
        table.add(rcsReturnAim).row() //Add curSettings["RCS_RETURNAIM"]!!.strToBool() Toggle
        table.addSeparator()
        table.add(enableRCrosshair).row() //Add Enable_RCrosshair Toggle
        table.add(rCrosshairWidth).row() //Add RCrosshair_Width Slider
        table.add(rCrosshairLength).row() //Add RCrosshair_Length Slider
        table.add(rCrosshairAlpha).row() //Add RCrosshair_Alpha Slider
        table.add(rCrosshairColor).row() //Add RCrosshair_Color Picker + Button
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "RCS"
    }
}