package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import com.sun.jna.platform.win32.OaIdl
import rat.poison.curLocalization
import rat.poison.ui.tabs.chamsEspTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class ChamsEspTab : Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    val chamsEsp = VisCheckBoxCustom(curLocalization["ENABLE"], "CHAMS_ESP", nameInLocalization = "ENABLE")
    val chamsShowHealth = VisCheckBoxCustom(curLocalization["CHAMS_SHOW_HEALTH"], "CHAMS_SHOW_HEALTH", nameInLocalization = "CHAMS_SHOW_HEALTH")
    val chamsBrightness = VisSliderCustom(curLocalization["CHAMS_BRIGHTNESS"], "CHAMS_BRIGHTNESS", 0F, 5000F, 25F, true, nameInLocalization = "CHAMS_BRIGHTNESS")

    val showTeam = VisCheckBoxCustom(" ", "CHAMS_SHOW_TEAM")
    val chamsTeamColor = VisColorPickerCustom(curLocalization["TEAMMATES"], "CHAMS_TEAM_COLOR", nameInLocalization = "TEAMMATES")

    val showEnemies = VisCheckBoxCustom(" ", "CHAMS_SHOW_ENEMIES")
    val chamsEnemyColor = VisColorPickerCustom(curLocalization["ENEMIES"], "CHAMS_ENEMY_COLOR", nameInLocalization = "ENEMIES")

    val showSelf = VisCheckBoxCustom(" ", "CHAMS_SHOW_SELF")
    val chamsSelfColor = VisColorPickerCustom(curLocalization["SELF"], "CHAMS_SELF_COLOR", nameInLocalization = "SELF")

    init {
        ////////////////////FORMATTING
        table.padLeft(25F)
        table.padRight(25F)

        table.add(chamsEsp).left()
        table.add(chamsShowHealth).left().row()
        table.add(chamsBrightness).left().colspan(2).row()

        var tmpTable = VisTable()
        tmpTable.add(showTeam)
        tmpTable.add(chamsTeamColor).width(175F - showTeam.width).padRight(50F)

        table.add(tmpTable).left()

        tmpTable = VisTable()
        tmpTable.add(showEnemies)
        tmpTable.add(chamsEnemyColor).width(175F - showEnemies.width).padRight(50F)

        table.add(tmpTable).left().row()

        tmpTable = VisTable()
        tmpTable.add(showSelf)
        tmpTable.add(chamsSelfColor).width(175F - showSelf.width).padRight(50F)

        table.add(tmpTable).left()

        //table.add(colTable).colspan(2).width(450F).left()
        ////////////////////FORMATTING
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return curLocalization["CHAMS_TAB_NAME"]
    }
}

fun chamsEspTabUpdate() {
    chamsEspTab.apply {
        chamsEsp.update()
        chamsShowHealth.update()
        chamsBrightness.update()
        showTeam.update()
        showEnemies.update()
        showSelf.update()
        chamsTeamColor.update()
        chamsEnemyColor.update()
        chamsEnemyColor.updateTitle()
        chamsTeamColor.updateTitle()
        chamsSelfColor.updateTitle()
        chamsSelfColor.update()
    }
}