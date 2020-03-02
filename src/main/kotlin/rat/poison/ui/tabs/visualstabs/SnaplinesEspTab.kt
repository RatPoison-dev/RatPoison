package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.ui.tabs.snaplinesEspTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class SnaplinesEspTab : Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    val enableSnaplines = VisCheckBoxCustom("Enable", "ENABLE_SNAPLINES")
    val snaplinesWidth = VisSliderCustom("Line Width", "SNAPLINES_WIDTH", 1F, 10F, 1F, false)

    val enemySnaplines = VisCheckBoxCustom(" ", "SNAPLINES_ENEMIES")
    val enemySnaplinesColor = VisColorPickerCustom("Enemies", "SNAPLINES_ENEMY_COLOR")

    val teamSnaplines = VisCheckBoxCustom(" ", "SNAPLINES_TEAMMATES")
    val teamSnaplinesColor = VisColorPickerCustom("Teammates", "SNAPLINES_TEAM_COLOR")

    val weaponSnaplines = VisCheckBoxCustom(" ", "SNAPLINES_WEAPONS")
    val weaponSnaplinesColor = VisColorPickerCustom("Weapons", "SNAPLINES_WEAPON_COLOR")

    val bombSnaplines = VisCheckBoxCustom(" ", "SNAPLINES_BOMB")
    val bombSnaplinesColor = VisColorPickerCustom("Bomb", "SNAPLINES_BOMB_COLOR")

    val bombCarrierSnaplines = VisCheckBoxCustom(" ", "SNAPLINES_BOMB_CARRIER")
    val bombCarrierSnaplinesColor = VisColorPickerCustom("Bomb Carrier", "SNAPLINES_BOMB_CARRIER_COLOR")

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
        return "Snaplines"
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
    }
}