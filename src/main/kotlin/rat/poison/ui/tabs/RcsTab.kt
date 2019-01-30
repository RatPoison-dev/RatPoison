package rat.poison.ui.tabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.color.ColorPicker
import com.kotcrab.vis.ui.widget.color.ColorPickerAdapter
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.App
import rat.poison.settings.*
import rat.poison.ui.changed

class RcsTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val rcsSmoothingLabel = VisLabel("RCS Smoothing: $RCS_SMOOTHING") //RCS_Smoothing
    val rcsSmoothingSlider = VisSlider(0.1F, 1F, .1F, false) //RCS_Smoothing
    val rcsReturnAim = VisTextButton("RCS_RETURNAIM", "toggle") //RCS_ReturnAim
    val rCrosshairWidthLabel = VisLabel("RCrosshair Width: $RCROSSHAIR_WIDTH") //RCrosshair_Width
    val rCrosshairWidthSlider = VisSlider(1F, 5F, 1F, false) //RCrosshair_Width
    val rcsCrosshairLengthLabel = VisLabel("RCrosshair Length: $RCROSSHAIR_LENGTH" + when(RCROSSHAIR_LENGTH.toString().length) { 2->"  " else ->"    "}) //RCrosshair_Length
    val rcsCrosshairLengthSlider = VisSlider(3F, 30F, 1F, false) //RCrosshair_Length
    val rCrosshairAlphaLabel = VisLabel("RCrosshair Alpha: $RCROSSHAIR_ALPHA") //RCrosshair_Alpha
    val rCrosshairAlphaSlider = VisSlider(0.1F, 1F, 0.1F, false) //RCrosshair_Alpha
    val rCrosshairColorShow = VisTextButton("Set RCrosshair Color") //RCrosshair_Color

    init {
        //Create RCS_Smoothing
        val rcsSmoothing = VisTable()
        Tooltip.Builder("The smoothing of the recoil control system").target(rcsSmoothing).build()
        //val rcsSmoothingLabel = VisLabel("RCS Smoothing: " + RCS_SMOOTHING.toString())
        //val rcsSmoothingSlider = VisSlider(0.1F, 1F, .1F, false)
        rcsSmoothingSlider.value = RCS_SMOOTHING.toFloat()
        rcsSmoothingSlider.changed { _, _ ->
            RCS_SMOOTHING = Math.round(rcsSmoothingSlider.value.toDouble() * 10.0)/10.0
            rcsSmoothingLabel.setText("RCS Smoothing: $RCS_SMOOTHING")
        }
        rcsSmoothing.add(rcsSmoothingLabel).spaceRight(6F) //when gets rid of spaceright
        rcsSmoothing.add(rcsSmoothingSlider)

        //Create RCS_ReturnAim
        //val rcsReturnAim = VisTextButton("RCS_RETURNAIM", "toggle")
        Tooltip.Builder("Whether or not to reset your crosshair after spraying").target(rcsReturnAim).build()
        if (RCS_RETURNAIM) rcsReturnAim.toggle()
        rcsReturnAim.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                RCS_RETURNAIM = rcsReturnAim.isChecked
            }
        }

        //Create RCrosshair_Width
        val rCrosshairWidth = VisTable()
        Tooltip.Builder("The width of the bars of the recoil crosshair, odd number is recommended").target(rCrosshairWidth).build()
        //val rcsCrosshairWidthLabel = VisLabel("RCrosshair Width: ")
        //val rcsCrosshairWidthSlider = VisSlider(0.1F, 1F, .1F, false)
        rCrosshairWidthSlider.value = RCROSSHAIR_WIDTH.toFloat()
        rCrosshairWidthSlider.changed { _, _ ->
            RCROSSHAIR_WIDTH = rCrosshairWidthSlider.value.toInt()//Math.round(rCrosshairWidthSlider.value.toDouble() * 10.0)/10.0
            rCrosshairWidthLabel.setText("RCrosshair Width: $RCROSSHAIR_WIDTH")
        }
        rCrosshairWidth.add(rCrosshairWidthLabel).spaceRight(6F) //when gets rid of spaceright
        rCrosshairWidth.add(rCrosshairWidthSlider)

        //Create RCrosshair_Length
        val rCrosshairLength = VisTable()
        Tooltip.Builder("The length of the bars of the recoil crosshair, odd number is recommended").target(rCrosshairLength).build()
        //val rcsCrosshairLengthLabel = VisLabel("RCrosshair Length: $RCROSSHAIR_LENGTH" + when(RCROSSHAIR_LENGTH.toString().length) { 2->"  " else ->"    "})
        //val rcsCrosshairLengthSlider = VisSlider(3F, 30F, 1F, false)
        rcsCrosshairLengthSlider.value = RCROSSHAIR_LENGTH.toFloat()
        rcsCrosshairLengthSlider.changed { _, _ ->
            RCROSSHAIR_LENGTH = rcsCrosshairLengthSlider.value.toInt()//Math.round(rCrosshairWidthSlider.value.toDouble() * 10.0)/10.0
            rcsCrosshairLengthLabel.setText("RCrosshair Length: $RCROSSHAIR_LENGTH" + when(RCROSSHAIR_LENGTH.toString().length) { 2->"  " else ->"    "})
        }
        rCrosshairLength.add(rcsCrosshairLengthLabel).spaceRight(6F) //when gets rid of spaceright
        rCrosshairLength.add(rcsCrosshairLengthSlider)

        //Create RCrosshair_Alpha
        val rCrosshairAlpha = VisTable()
        Tooltip.Builder("The alpha of the recoil crosshair").target(rCrosshairAlpha).build()
        //val rCrosshairAlphaLabel = VisLabel("RCrosshair Alpha: $RCROSSHAIR_ALPHA"
        //val rCrosshairAlphaSlider = VisSlider(1F, 5F, 0.1F, false)
        rCrosshairAlphaSlider.value = RCROSSHAIR_ALPHA.toFloat()
        rCrosshairAlphaSlider.changed { _, _ ->
            RCROSSHAIR_ALPHA = Math.round(rCrosshairAlphaSlider.value.toDouble() * 10.0)/10.0 //Round to 1 decimal place
            rCrosshairAlphaLabel.setText("RCrosshair Alpha: $RCROSSHAIR_ALPHA")
        }
        rCrosshairAlpha.add(rCrosshairAlphaLabel).spaceRight(6F)
        rCrosshairAlpha.add(rCrosshairAlphaSlider)

        //Create RCrosshair_Color Picker
        val rCrosshairColor = VisTable()
        Tooltip.Builder("The color of the recoil crosshair").target(rCrosshairColor).build()
        //val rCrosshairColorShow = VisTextButton("Set RCrosshair Color")
        rCrosshairColorShow.setColor(RCROSSHAIR_COLOR.red.toFloat(), RCROSSHAIR_COLOR.green.toFloat(), RCROSSHAIR_COLOR.blue.toFloat(), 1F)
        val rCrosshairColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                RCROSSHAIR_COLOR = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
                newCol.a = 1F
                rCrosshairColorShow.color = newCol
                dispose()
            }

            override fun canceled(oldCol: Color) {
                dispose()
            }
        })

        rCrosshairColorShow.changed { _, _ ->
            App.stage.addActor(rCrosshairColorPicker.fadeIn())
        }

        rCrosshairColor.add(rCrosshairColorShow)



        //Add all items to label for tabbed pane content
        table.add(rcsSmoothing).row() //Add RCS_Smoothing Slider
        table.add(rcsReturnAim).row() //Add RCS_ReturnAim Toggle
        table.addSeparator()
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