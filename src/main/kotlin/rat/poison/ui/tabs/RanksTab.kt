package rat.poison.ui.tabs
//
//import com.badlogic.gdx.scenes.scene2d.ui.Table
//import com.kotcrab.vis.ui.util.Validators
//import com.kotcrab.vis.ui.widget.*
//import com.kotcrab.vis.ui.widget.tabbedpane.Tab
//import org.jire.arrowhead.keyPressed
//import rat.poison.*
//import rat.poison.ui.changed
//import rat.poison.utils.ObservableBoolean
//
//class RanksTab : Tab(false, false) {
//    private val table = VisTable(true)
//
//    //Init labels/sliders/boxes that show values here
//    //val bunnyHop = VisCheckBox("Bunny Hop")
//
//    init {
//        //Create Bunny Hop Toggle
////        Tooltip.Builder("Whether or not to enable bunny hop").target(bunnyHop).build()
////        bunnyHop.isChecked = curSettings["ENABLE_BUNNY_HOP"]!!.strToBool()
////        bunnyHop.changed { _, _ ->
////            curSettings["ENABLE_BUNNY_HOP"] = bunnyHop.isChecked.boolToStr()
////            true
////        }
//
//
//        //Add all items to label for tabbed pane content
//        //table.add(bunnyHop).row()
//    }
//
//    override fun getContentTable(): Table? {
//        return table
//    }
//
//    override fun getTabTitle(): String? {
//        return "Ranks"
//    }
//}