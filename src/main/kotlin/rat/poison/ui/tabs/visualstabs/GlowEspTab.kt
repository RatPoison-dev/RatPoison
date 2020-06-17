package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.ui.changed
import rat.poison.ui.tabs.glowEspTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.binds.BindsRelatedCheckBox
import rat.poison.visualsMap

class GlowEspTab : Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    val glowEsp = BindsRelatedCheckBox(curLocalization["ENABLE"], "GLOW_ESP", nameInLocalization = "ENABLE", padLeft = 155F)
    var map = visualsMap()
    var enemyGlowType = VisSelectBox<String>() //Change to VisSelectBoxCustom sometime....
    var teammateGlowType = VisSelectBox<String>() //Change to VisSelectBoxCustom sometime....
    var weaponGlowType = VisSelectBox<String>() //Change to VisSelectBoxCustom sometime....
    var grenadeGlowType = VisSelectBox<String>() //Change to VisSelectBoxCustom sometime....
    var targetGlowType = VisSelectBox<String>() //Change to VisSelectBoxCustom sometime....
    var bombCarrierGlowType = VisSelectBox<String>() //Change to VisSelectBoxCustom sometime....
    var bombGlowType = VisSelectBox<String>() //Change to VisSelectBoxCustom sometime....

    val glowShowHealth = VisCheckBoxCustom(curLocalization["HEALTH_BASED"], "GLOW_SHOW_HEALTH", "HEALTH_BASED")

    val showTeam = VisCheckBoxCustom(" ", "GLOW_SHOW_TEAM")
    val glowTeamColor = VisColorPickerCustom(curLocalization["TEAMMATES"], "GLOW_TEAM_COLOR", nameInLocalization = "TEAMMATES")

    val showEnemies = VisCheckBoxCustom(" ", "GLOW_SHOW_ENEMIES")
    val glowEnemyColor = VisColorPickerCustom(curLocalization["ENEMIES"], "GLOW_ENEMY_COLOR", nameInLocalization = "ENEMIES")

    val showBomb = VisCheckBoxCustom(" ", "GLOW_SHOW_BOMB")
    val glowBombColor = VisColorPickerCustom(curLocalization["BOMB"], "GLOW_BOMB_COLOR", nameInLocalization = "BOMB")

    val showBombCarrier = VisCheckBoxCustom(" ", "GLOW_SHOW_BOMB_CARRIER")
    val glowBombCarrierColor = VisColorPickerCustom(curLocalization["BOMB_CARRIER"], "GLOW_BOMB_CARRIER_COLOR", nameInLocalization = "BOMB_CARRIER")

    val showWeapons = VisCheckBoxCustom(" ", "GLOW_SHOW_WEAPONS")
    val glowWeaponColor = VisColorPickerCustom(curLocalization["WEAPONS"], "GLOW_WEAPON_COLOR", nameInLocalization = "WEAPONS")

    val showGrenades = VisCheckBoxCustom(" ", "GLOW_SHOW_GRENADES")
    val glowGrenadeColor = VisColorPickerCustom(curLocalization["GRENADES"], "GLOW_GRENADE_COLOR", nameInLocalization = "GRENADES")

    val showTarget = VisCheckBoxCustom(" ", "GLOW_SHOW_TARGET")
    val glowHighlightColor = VisColorPickerCustom(curLocalization["TARGET"], "GLOW_HIGHLIGHT_COLOR", nameInLocalization = "TARGET")

    fun updateMap() {
        map = visualsMap()
    }

    init {
        //weaponGlowType = VisSelectBox<String>() //Change to VisSelectBoxCustom sometime....
        //var grenadeGlowType = VisSelectBox<String>() //Change to VisSelectBoxCustom sometime....
        //var targetGlowType = VisSelectBox<String>() //Change to VisSelectBoxCustom sometime....
        //var bombCarrierGlowType = VisSelectBox<String>() //Change to VisSelectBoxCustom sometime....
        //var bombGlowType = VisSelectBox<String>() //Change to VisSelectBoxCustom sometime....

        enemyGlowType.setItems(curLocalization["NORMAL"], curLocalization["MODEL"], curLocalization["VISIBLE"], curLocalization["VISIBLE_FLICKER"])
        enemyGlowType.selected = map[curLocalization["VISIBLE"]]
        enemyGlowType.changed { _, _ ->
            curSettings["GLOW_ENEMY_TYPE"] = map[enemyGlowType.selected]
            //uiUpdate() ?
            true
        }

        teammateGlowType.setItems(curLocalization["NORMAL"], curLocalization["MODEL"], curLocalization["VISIBLE"], curLocalization["VISIBLE_FLICKER"])
        teammateGlowType.selected = map[curLocalization["NORMAL"]]
        teammateGlowType.changed { _, _ ->
            curSettings["GLOW_TEAMMATE_TYPE"] = map[teammateGlowType.selected]
            //uiUpdate() ?
            true
        }

        grenadeGlowType.setItems(curLocalization["NORMAL"], curLocalization["MODEL"], curLocalization["VISIBLE"], curLocalization["VISIBLE_FLICKER"])
        grenadeGlowType.selected = map[curLocalization["NORMAL"]]
        grenadeGlowType.changed { _, _ ->
            curSettings["GLOW_GRENADE_TYPE"] = map[grenadeGlowType.selected]
            //uiUpdate() ?
            true
        }

        weaponGlowType.setItems(curLocalization["NORMAL"], curLocalization["MODEL"], curLocalization["VISIBLE"], curLocalization["VISIBLE_FLICKER"])
        weaponGlowType.selected = map[curLocalization["NORMAL"]]
        weaponGlowType.changed { _, _ ->
            curSettings["GLOW_WEAPON_TYPE"] = map[weaponGlowType.selected]
            //uiUpdate() ?
            true
        }

        targetGlowType.setItems(curLocalization["NORMAL"], curLocalization["MODEL"], curLocalization["VISIBLE"], curLocalization["VISIBLE_FLICKER"])
        targetGlowType.selected = map[curLocalization["NORMAL"]]
        targetGlowType.changed { _, _ ->
            curSettings["GLOW_TARGET_TYPE"] = map[targetGlowType.selected]
            //uiUpdate() ?
            true
        }

        bombCarrierGlowType.setItems(curLocalization["NORMAL"], curLocalization["MODEL"], curLocalization["VISIBLE"], curLocalization["VISIBLE_FLICKER"])
        bombCarrierGlowType.selected = map[curLocalization["NORMAL"]]
        bombCarrierGlowType.changed { _, _ ->
            curSettings["GLOW_BOMB_CARRIER_TYPE"] = map[bombCarrierGlowType.selected]
            //uiUpdate() ?
            true
        }

        bombGlowType.setItems(curLocalization["NORMAL"], curLocalization["MODEL"], curLocalization["VISIBLE"], curLocalization["VISIBLE_FLICKER"])
        bombGlowType.selected = map[curLocalization["NORMAL"]]
        bombGlowType.changed { _, _ ->
            curSettings["GLOW_BOMB_TYPE"] = map[bombGlowType.selected]
            //uiUpdate() ?
            true
        }

        ////////////////////FORMATTING
        table.padLeft(25F)
        table.padRight(25F)

//        entityGlowType.selected = curSettings["GLOW_ENTITY_TYPE"]
//        weaponGlowType.selected = curSettings["GLOW_WEAPON_TYPE"]
//        targetGlowType.selected = curSettings["GLOW_TARGET_TYPE"]
//        grenadeGlowType.selected = curSettings["GLOW_GRENADE_TYPE"]
//        bombGlowType.selected = curSettings["GLOW_BOMB_TYPE"]
//        bombCarrierGlowType.selected = curSettings["GLOW_BOMB_CARRIER_TYPE"]

        table.add(glowEsp).left().row()
        table.add(glowShowHealth).left().row()

        var tmpTable = VisTable()
        tmpTable.add(showTeam)
        tmpTable.add(glowTeamColor).width(175F - showTeam.width).padRight(50F)

        table.add(tmpTable).left()
        table.add(teammateGlowType).left().row()

        tmpTable = VisTable()
        tmpTable.add(showEnemies)
        tmpTable.add(glowEnemyColor).width(175F - showEnemies.width).padRight(50F)

        table.add(tmpTable).left()
        table.add(enemyGlowType).left().row()

        tmpTable = VisTable()
        tmpTable.add(showBomb)
        tmpTable.add(glowBombColor).width(175F - showBomb.width).padRight(50F)

        table.add(tmpTable).left()
        table.add(bombGlowType).left().row()

        tmpTable = VisTable()
        tmpTable.add(showBombCarrier)
        tmpTable.add(glowBombCarrierColor).width(175F - showBombCarrier.width).padRight(50F)

        table.add(tmpTable).left()
        table.add(bombCarrierGlowType).left().row()

        tmpTable = VisTable()
        tmpTable.add(showWeapons)
        tmpTable.add(glowWeaponColor).width(175F - showWeapons.width).padRight(50F)

        table.add(tmpTable).left()
        table.add(weaponGlowType).left().row()

        tmpTable = VisTable()
        tmpTable.add(showGrenades)
        tmpTable.add(glowGrenadeColor).width(175F - showGrenades.width).padRight(50F)

        table.add(tmpTable).left()
        table.add(grenadeGlowType).left().row()

        tmpTable = VisTable()
        tmpTable.add(showTarget)
        tmpTable.add(glowHighlightColor).width(175F - showTarget.width).padRight(50F)

        table.add(tmpTable).left()
        table.add(targetGlowType).left().row()
        ////////////////////FORMATTING
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return curLocalization["GLOW_TAB_NAME"]
    }
}

fun glowEspTabUpdate() {
    glowEspTab.apply {
        updateMap()
        glowEsp.update()
        glowShowHealth.update()

        showTeam.update()
        showEnemies.update()
        showBomb.update()
        showBombCarrier.update()
        showWeapons.update()
        showGrenades.update()
        showTarget.update()
        glowTeamColor.update()
        glowEnemyColor.update()
        glowBombColor.update()
        glowBombCarrierColor.update()
        glowWeaponColor.update()
        glowGrenadeColor.update()

        glowBombCarrierColor.updateTitle()
        glowBombColor.updateTitle()
        glowEnemyColor.updateTitle()
        glowGrenadeColor.updateTitle()
        glowHighlightColor.update()
        glowTeamColor.updateTitle()
        glowHighlightColor.updateTitle()
        glowWeaponColor.updateTitle()
    }
}