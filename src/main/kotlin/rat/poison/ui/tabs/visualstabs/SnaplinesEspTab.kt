package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import com.sun.jna.platform.win32.OaIdl
import rat.poison.curLocalization
import rat.poison.ui.tabs.snaplinesEspTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class SnaplinesEspTab : Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    val enableSnaplines = VisCheckBoxCustom(curLocalization["ENABLE"], "ENABLE_SNAPLINES", nameInLocalization = "ENABLE")
    val snaplinesWidth = VisSliderCustom(curLocalization["LINE_WIDTH"], "SNAPLINES_WIDTH", 1F, 10F, 1F, false, nameInLocalization = "LINE_WIDTH")

    val enemySnaplines = VisCheckBoxCustom(" ", "SNAPLINES_ENEMIES")
    val enemySnaplinesColor = VisColorPickerCustom(curLocalization["ENEMIES"], "SNAPLINES_ENEMY_COLOR", nameInLocalization = "ENEMIES")

    val teamSnaplines = VisCheckBoxCustom(" ", "SNAPLINES_TEAMMATES")
    val teamSnaplinesColor = VisColorPickerCustom(curLocalization["TEAMMATES"], "SNAPLINES_TEAM_COLOR", nameInLocalization = "TEAMMATES")

    val weaponSnaplines = VisCheckBoxCustom(" ", "SNAPLINES_WEAPONS")
    val weaponSnaplinesColor = VisColorPickerCustom(curLocalization["WEAPONS"], "SNAPLINES_WEAPON_COLOR", nameInLocalization = "WEAPONS")

    val bombSnaplines = VisCheckBoxCustom(" ", "SNAPLINES_BOMB")
    val bombSnaplinesColor = VisColorPickerCustom(curLocalization["BOMB"], "SNAPLINES_BOMB_COLOR", nameInLocalization = "BOMB")

    val bombCarrierSnaplines = VisCheckBoxCustom(" ", "SNAPLINES_BOMB_CARRIER")
    val bombCarrierSnaplinesColor = VisColorPickerCustom(curLocalization["BOMB_CARRIER"], "SNAPLINES_BOMB_CARRIER_COLOR", nameInLocalization = "BOMB_CARRIER")

    init {
        table.padLeft(25F)
        table.padRight(25F)

        table.add(enableSnaplines).left().row()
        table.add(snaplinesWidth).left().colspan(2).row()

        var tmpTable = VisTable()
        tmpTable.add(enemySnaplines)
        tmpTable.add(enemySnaplinesColor).width(175F - enemySnaplines.width).padRight(50F)

        table.add(tmpTable)

        tmpTable = VisTable()
        tmpTable.add(teamSnaplines)
        tmpTable.add(teamSnaplinesColor).width(175F - teamSnaplines.width).padRight(50F).row()

        table.add(tmpTable).row()

        tmpTable = VisTable()
        tmpTable.add(weaponSnaplines)
        tmpTable.add(weaponSnaplinesColor).width(175F - weaponSnaplines.width).padRight(50F)

        table.add(tmpTable)

        tmpTable = VisTable()
        tmpTable.add(bombSnaplines)
        tmpTable.add(bombSnaplinesColor).width(175F - bombSnaplines.width).padRight(50F).row()

        table.add(tmpTable).row()

        tmpTable = VisTable()
        tmpTable.add(bombCarrierSnaplines)
        tmpTable.add(bombCarrierSnaplinesColor).width(175F - bombCarrierSnaplines.width).padRight(50F)

        table.add(tmpTable)
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return curLocalization["SNAPLINES_TAB_NAME"]
    }
}

fun snaplinesEspTabUpdate() {
    snaplinesEspTab.apply {
        enableSnaplines.update()
        snaplinesWidth.update()
        enemySnaplines.update()
        enemySnaplinesColor.update()
        teamSnaplines.update()
        teamSnaplinesColor.update()
        weaponSnaplines.update()
        weaponSnaplinesColor.update()
        bombSnaplines.update()
        bombSnaplinesColor.update()
        bombCarrierSnaplines.update()
        bombCarrierSnaplinesColor.update()
        bombCarrierSnaplinesColor.updateTitle()
        bombSnaplinesColor.updateTitle()
        enemySnaplinesColor.updateTitle()
        teamSnaplinesColor.updateTitle()
        weaponSnaplinesColor.updateTitle()
    }
}