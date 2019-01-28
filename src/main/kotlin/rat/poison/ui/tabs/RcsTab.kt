package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.settings.*
import rat.poison.ui.changed

class RcsTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val rcsSmoothingLabel = VisLabel("RCS Smoothing: $RCS_SMOOTHING"/* + when(RCS_SMOOTHING.toString().length) {2->"  " else->"    "}*/) //RCS_Smoothing
    val rcsSmoothingSlider = VisSlider(0.1F, 1F, .1F, false) //RCS_Smoothing
    val rcsReturnAim = VisTextButton("RCS_RETURNAIM", "toggle") //RCS_ReturnAim

    init {
        //Create RCS_Smoothing
        val rcsSmoothing = VisTable()
        //val rcsSmoothingLabel = VisLabel("RCS Smoothing: " + RCS_SMOOTHING.toString() + when(RCS_SMOOTHING.toString().length) {2->"  " else->"    "})
        //val rcsSmoothingSlider = VisSlider(0.1F, 1F, .1F, false)
        rcsSmoothingSlider.value = RCS_SMOOTHING.toFloat()
        rcsSmoothingSlider.changed { _, _ ->
            RCS_SMOOTHING = Math.round(rcsSmoothingSlider.value.toDouble() * 10.0)/10.0
            rcsSmoothingLabel.setText("RCS Smoothing: $RCS_SMOOTHING")/* + when(RCS_SMOOTHING.toString().length) {2->"  " else->"    "})*/
        }
        rcsSmoothing.add(rcsSmoothingLabel)//.spaceRight(6F) //when gets rid of spaceright
        rcsSmoothing.add(rcsSmoothingSlider)

        //Create RCS_ReturnAim
        //val rcsReturnAim = VisTextButton("RCS_RETURNAIM", "toggle")
        if (RCS_RETURNAIM) rcsReturnAim.toggle()
        rcsReturnAim.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                RCS_RETURNAIM = rcsReturnAim.isChecked
            }
        }


        //Add all items to label for tabbed pane content
        table.add(rcsSmoothing).row() //Add RCS_Smoothing Slider
        table.add(rcsReturnAim).row() //Add RCS_ReturnAim Toggle
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "RCS"
    }
}