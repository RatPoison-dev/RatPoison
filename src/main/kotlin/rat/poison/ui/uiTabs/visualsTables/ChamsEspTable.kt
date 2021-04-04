package rat.poison.ui.uiTabs.visualsTables

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.ui.uiTabs.chamsEspTable
import rat.poison.ui.uiElements.VisCheckBoxCustom
import rat.poison.ui.uiElements.VisColorPickerCustom
import rat.poison.ui.uiElements.VisSliderCustom

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
        label.setColor(1F, 1F, 1F, 1F)

        add(label).colspan(2).expandX().padTop(4F).row()
        addSeparator().colspan(2).width(200F).top().height(2F).padBottom(8F)

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

fun chamsEspTableDisable(bool: Boolean, col: Color) {
    chamsEspTable.chamsEsp.disable(bool)
    chamsEspTable.chamsShowHealth.disable(bool)
    chamsEspTable.chamsBrightness.disable(bool, col)
    chamsEspTable.showTeam.disable(bool)
    chamsEspTable.showEnemies.disable(bool)
    chamsEspTable.chamsTeamColor.disable(bool)
    chamsEspTable.chamsEnemyColor.disable(bool)
}