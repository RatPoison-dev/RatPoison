package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.toLocale
import rat.poison.ui.tabs.snaplinesEspTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class SnaplinesEspTab : Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    val enableSnaplines = VisCheckBoxCustom("Enable", "ENABLE_SNAPLINES")
    val snaplinesWidth = VisSliderCustom("Line Width", "SNAPLINES_WIDTH", 1F, 10F, 1F, false)

    val snaplinesSmokeCheck = VisCheckBoxCustom("Smoke Check", "SNAPLINES_SMOKE_CHECK")

    val enemySnaplines = VisCheckBoxCustom(" ", "SNAPLINES_ENEMIES", false)
    val enemySnaplinesColor = VisColorPickerCustom("Enemies", "SNAPLINES_ENEMY_COLOR")

    val teamSnaplines = VisCheckBoxCustom(" ", "SNAPLINES_TEAMMATES", false)
    val teamSnaplinesColor = VisColorPickerCustom("Teammates", "SNAPLINES_TEAM_COLOR")

    val weaponSnaplines = VisCheckBoxCustom(" ", "SNAPLINES_WEAPONS", false)
    val weaponSnaplinesColor = VisColorPickerCustom("Weapons", "SNAPLINES_WEAPON_COLOR")

    val bombSnaplines = VisCheckBoxCustom(" ", "SNAPLINES_BOMB", false)
    val bombSnaplinesColor = VisColorPickerCustom("Bomb", "SNAPLINES_BOMB_COLOR")

    val bombCarrierSnaplines = VisCheckBoxCustom(" ", "SNAPLINES_BOMB_CARRIER", false)
    val bombCarrierSnaplinesColor = VisColorPickerCustom("Bomb Carrier", "SNAPLINES_BOMB_CARRIER_COLOR")

    val defuseKitSnaplines = VisCheckBoxCustom(" ", "SNAPLINES_DEFUSE_KITS", false)
    val defuseKitSnaplinesColor = VisColorPickerCustom("Defuse Kits", "SNAPLINES_DEFUSE_KIT_COLOR")

    init {
        table.padLeft(25F)
        table.padRight(25F)

        table.add(enableSnaplines).left().row()
        table.add(snaplinesWidth).left().colspan(2).row()

        table.add(snaplinesSmokeCheck).left().row()

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

        tmpTable = VisTable()
        tmpTable.add(defuseKitSnaplines)
        tmpTable.add(defuseKitSnaplinesColor).width(175F - defuseKitSnaplines.width).padRight(50F)
        table.add(tmpTable)
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Snaplines".toLocale()
    }
}

fun snaplinesEspTabUpdate() {
    snaplinesEspTab.apply {
        enableSnaplines.update()
        snaplinesWidth.update()
        snaplinesSmokeCheck.update()
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