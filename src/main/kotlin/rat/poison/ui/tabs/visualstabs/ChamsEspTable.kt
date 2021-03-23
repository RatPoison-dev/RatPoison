package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.ui.tabs.chamsEspTable
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class ChamsEspTable: VisTable(false) {
    //Init labels/sliders/boxes that show values here
    val chamsEsp = VisCheckBoxCustom("Enable", "CHAMS_ESP")
    val chamsShowHealth = VisCheckBoxCustom("Show Health", "CHAMS_SHOW_HEALTH")
    val chamsBrightness = VisSliderCustom("Brightness", "CHAMS_BRIGHTNESS", 0F, 100F, 1F, true, 0, 175F, 117F)

    val showTeam = VisCheckBoxCustom("Teammates", "CHAMS_SHOW_TEAM", false)
    val chamsTeamColor = VisColorPickerCustom("Chams Teammate Color", "CHAMS_TEAM_COLOR")

    val showEnemies = VisCheckBoxCustom("Enemies", "CHAMS_SHOW_ENEMIES", false)
    val chamsEnemyColor = VisColorPickerCustom("Chams Enemy Color", "CHAMS_ENEMY_COLOR")

    val showSelf = VisCheckBoxCustom("Self", "CHAMS_SHOW_SELF", false)
    val chamsSelfColor = VisColorPickerCustom("Chams Self Color", "CHAMS_SELF_COLOR")

    val showTarget = VisCheckBoxCustom("Aim Target", "CHAMS_SHOW_TARGET", false)
    val chamsTargetColor = VisColorPickerCustom("Chams Aim Target", "CHAMS_TARGET_COLOR")

    init {
        val label = VisLabel("Chams")
        label.setColor(.85F, .5F, .05F, 1F)

        add(label).colspan(2).padBottom(8F).expandX().row()

        add(chamsEsp).left().padRight(175F - chamsEsp.width)
        add(chamsShowHealth).left().expandX().row()

        add(showTeam).left().padRight(175F - showTeam.width)
        add(chamsTeamColor).left().expandX().row()

        add(showEnemies).left().padRight(175F - showEnemies.width)
        add(chamsEnemyColor).left().expandX().row()

        add(showSelf).left().padRight(175F - showSelf.width)
        add(chamsSelfColor).left().expandX().row()

        add(showTarget).left().padRight(175F - showTarget.width)
        add(chamsTargetColor).left().expandX().row()

        add(chamsBrightness).colspan(2).left().row()
    }
}

fun chamsEspTabUpdate() {
    chamsEspTable.apply {
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

fun chamsEspTabDisable(bool: Boolean, col: Color) {
    chamsEspTable.chamsEsp.disable(bool)
    chamsEspTable.chamsShowHealth.disable(bool)
    chamsEspTable.chamsBrightness.disable(bool, col)
    chamsEspTable.showTeam.disable(bool)
    chamsEspTable.showEnemies.disable(bool)
    chamsEspTable.chamsTeamColor.disable(bool)
    chamsEspTable.chamsEnemyColor.disable(bool)
}