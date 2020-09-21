package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.toLocale
import rat.poison.ui.tabs.chamsEspTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class ChamsEspTab : Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    val chamsEsp = VisCheckBoxCustom("Enable", "CHAMS_ESP")
    val chamsShowHealth = VisCheckBoxCustom("Chams Show Health", "CHAMS_SHOW_HEALTH")
    val chamsBrightness = VisSliderCustom("Chams Brightness", "CHAMS_BRIGHTNESS", 0F, 100F, 1F, true)

    val showTeam = VisCheckBoxCustom(" ", "CHAMS_SHOW_TEAM", false)
    val chamsTeamColor = VisColorPickerCustom("Teammates", "CHAMS_TEAM_COLOR")

    val showEnemies = VisCheckBoxCustom(" ", "CHAMS_SHOW_ENEMIES", false)
    val chamsEnemyColor = VisColorPickerCustom("Enemies", "CHAMS_ENEMY_COLOR")

    val showSelf = VisCheckBoxCustom(" ", "CHAMS_SHOW_SELF", false)
    val chamsSelfColor = VisColorPickerCustom("Self", "CHAMS_SELF_COLOR")

    val showTarget = VisCheckBoxCustom(" ", "CHAMS_SHOW_TARGET", false)
    val chamsTargetColor = VisColorPickerCustom("Target", "CHAMS_TARGET_COLOR")

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

        tmpTable = VisTable()
        tmpTable.add(showTarget)
        tmpTable.add(chamsTargetColor).width(175F - showTarget.width).padRight(50F)

        table.add(tmpTable).left()

        //table.add(colTable).colspan(2).width(450F).left()
        ////////////////////FORMATTING
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Chams".toLocale()
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
        chamsSelfColor.update()
    }
}