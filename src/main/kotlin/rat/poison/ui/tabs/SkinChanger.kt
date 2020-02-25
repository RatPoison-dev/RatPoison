package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.scripts.forcedUpdate
import rat.poison.ui.*
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisInputFieldCustom

class SkinChangerTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    private var skinChanger = VisCheckBoxCustom("Enable Skinchanger", "SKINCHANGER")
    private var warningLabel = VisLabel("This is experimental and is not what the completed\n" +
            " version will look like just a temporary version to show\n" +
            "it works, updates are in progress, and to report any issues.\n" +
            "To find the correct ID use csgostash.com and use the\n" +
            "'Finish Catalog' number.\n\n" +
            "May be cpu intensive. When enabled, press force update\n" +
            "to apply skins.")

    private var AwpID = VisInputFieldCustom("Awp ID", "AWP_SKIN_ID", false)
    private var ScoutID = VisInputFieldCustom("Scout ID", "SCOUT_SKIN_ID", false)
    private var AkID = VisInputFieldCustom("AK ID", "AK_SKIN_ID", false)
    private var M4ID = VisInputFieldCustom("M4 ID", "M4_SKIN_ID", false)
    private var DeagleID = VisInputFieldCustom("Deagle ID", "DEAGLE_SKIN_ID", false)
    private var UspsID = VisInputFieldCustom("USPS ID", "USPS_SKIN_ID", false)

    private var StatTrakNum = VisInputFieldCustom("Stattrak Num", "STATTRAK_NUM", false)

    var forceUpdate = VisTextButton("Force Update")

    init {
        forceUpdate.changed { _, _ ->
            forcedUpdate()

            true
        }
        ////////////////////FORMATTING
        table.padLeft(25F)
        table.padRight(25F)

        table.add(warningLabel).row()
        table.add(skinChanger).row()
        table.add(AwpID).row()
        table.add(ScoutID).row()
        table.add(AkID).row()
        table.add(M4ID).row()
        table.add(DeagleID).row()
        table.add(UspsID).row()
        table.add(StatTrakNum).row()
        table.add(forceUpdate)

        ////////////////////FORMATTING
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Skin Changer"
    }
}

fun skinChangerTabUpdate() {

}