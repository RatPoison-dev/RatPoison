package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.curSettings
import rat.poison.scripts.esp.disableAllEsp
import rat.poison.strToBool
import rat.poison.ui.tabs.chamsEspTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class ChamsEspTab : Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    val chamsEsp = VisCheckBoxCustom("Chams", "CHAMS_ESP")
    val chamsShowHealth = VisCheckBoxCustom("Chams Show Health", "CHAMS_SHOW_HEALTH")
    val chamsBrightness = VisSliderCustom("Chams Brightness", "CHAMS_BRIGHTNESS", 0F, 5000F, 25F, true)

    val showTeam = VisCheckBoxCustom("Show Team", "CHAMS_SHOW_TEAM")
    val showEnemies = VisCheckBoxCustom("Show Enemies", "CHAMS_SHOW_ENEMIES")

    val chamsTeamColor = VisColorPickerCustom("Team Color", "CHAMS_TEAM_COLOR")
    val chamsEnemyColor = VisColorPickerCustom("Enemy Color", "CHAMS_ENEMY_COLOR")
    val chamsSelfColor = VisColorPickerCustom("Self Color", "CHAMS_SELF_COLOR")

    init {
        ////////////////////FORMATTING
        val colTable = VisTable()
        val width = 225F
        colTable.add(chamsTeamColor).width(width).padRight(2F)
        colTable.add(chamsEnemyColor).width(width).row()
        colTable.add(chamsSelfColor).width(width).left()

        table.padLeft(25F)
        table.padRight(25F)

        table.add(chamsEsp).left()
        table.add(chamsShowHealth).left().row()
        table.add(chamsBrightness).left().colspan(2).row()
        table.add(showTeam).padRight(225F - showTeam.width).left()
        table.add(showEnemies).padRight(225F - showEnemies.width).left().row()

        table.add(colTable).colspan(2).width(450F).left()
        ////////////////////FORMATTING
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Chams"
    }
}

fun chamsEspTabUpdate() {
    chamsEspTab.apply {
        chamsEsp.update()
        chamsShowHealth.update()
        chamsBrightness.update()
        showTeam.update()
        showEnemies.update()
        chamsTeamColor.update()
        chamsEnemyColor.update()
        chamsSelfColor.update()
    }

    if (!curSettings["CHAMS_ESP"].strToBool()) {
        disableAllEsp()
    }
}