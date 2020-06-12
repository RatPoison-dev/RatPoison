package rat.poison.ui.uiPanels

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.VisSlider
import com.kotcrab.vis.ui.widget.VisValidatableTextField
import com.kotcrab.vis.ui.widget.VisWindow
import rat.poison.App.uiKeybinds
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.ui.changed
import rat.poison.ui.uiHelpers.VisInputFieldCustom
import kotlin.math.round

class UIKeybinds : VisWindow(curLocalization["KEYBINDS_PANEL_NAME"]) {
    val aimToggleKey = VisInputFieldCustom(curLocalization["AIM_TOGGLE_KEY"], "AIM_TOGGLE_KEY", nameInLocalization = "AIM_TOGGLE_KEY")
    val forceAimKey = VisInputFieldCustom(curLocalization["FORCE_AIM_KEY"], "FORCE_AIM_KEY", nameInLocalization = "FORCE_AIM_KEY")
    val rcsToggleKey = VisInputFieldCustom(curLocalization["RCS_TOGGLE_KEY"], "RCS_TOGGLE_KEY", nameInLocalization = "RCS_TOGGLE_KEY")
    val forceAimBoneKey = VisInputFieldCustom(curLocalization["FORCE_AIM_BONE_KEY"], "FORCE_AIM_BONE_KEY", nameInLocalization = "FORCE_AIM_BONE_KEY" )
    val boneTriggerKey = VisInputFieldCustom(curLocalization["TRIGGER_KEY"], "TRIGGER_KEY", nameInLocalization = "TRIGGER_KEY")
    val visualsToggleKey = VisInputFieldCustom(curLocalization["VISUALS_TOGGLE_KEY"], "VISUALS_TOGGLE_KEY", nameInLocalization = "VISUALS_TOGGLE_KEY")
    val doorSpamKey = VisInputFieldCustom(curLocalization["DOOR_SPAM_KEY"], "D_SPAM_KEY", nameInLocalization = "DOOR_SPAM_KEY")
    val weaponSpamKey = VisInputFieldCustom(curLocalization["WEAPON_SPAM_KEY"], "W_SPAM_KEY", nameInLocalization = "WEAPON_SPAM_KEY")
    val menuKeyField = VisInputFieldCustom(curLocalization["MENU_KEY"], "MENU_KEY", nameInLocalization = "MENU_KEY")

    init {
        defaults().left()

        align(Align.left)

        padLeft(25F)
        padRight(25F)

        //Create UI_Alpha Slider
        val menuAlphaSlider = VisSlider(0.5F, 1F, 0.05F, false)
        menuAlphaSlider.value = 1F
        menuAlphaSlider.changed { _, _ ->
            val alp = (round(menuAlphaSlider.value * 100F) / 100F)
            changeAlpha(alp)

            true
        }

        add(aimToggleKey).left().row()
        add(forceAimKey).left().row()
        add(forceAimBoneKey).left().row()
        add(boneTriggerKey).left().row()
        add(visualsToggleKey).left().row()
        add(rcsToggleKey).left().row()
        add(doorSpamKey).left().row()
        add(weaponSpamKey).left().row()
        add(menuKeyField).left().row()
        add(menuAlphaSlider).growX()

        setSize(325F, 325F)
        setPosition(curSettings["KEYBINDS_X"].toFloat(), curSettings["KEYBINDS_Y"].toFloat())
        color.a = curSettings["KEYBINDS_ALPHA"].toFloat()
        isResizable = false
    }

    fun changeAlpha(alpha: Float) {
        color.a = alpha
    }

    fun updatePosition(x: Float, y: Float) {
        setPosition(x, y)
    }
    fun update() {
        this.titleLabel.setText(curLocalization["KEYBINDS_PANEL_NAME"])
    }
}

// This is retarted
fun updateKeybinds() {
    uiKeybinds.apply {
        update()
        aimToggleKey.update()
        forceAimKey.update()
        forceAimBoneKey.update()
        rcsToggleKey.update()
        boneTriggerKey.update()
        visualsToggleKey.update()
        doorSpamKey.update()
        weaponSpamKey.update()
        menuKeyField.update()
    }
}

fun keybindsUpdate(neglect: Actor) {
    uiKeybinds.apply {
        aimToggleKey.update(neglect)
        forceAimKey.update(neglect)
        forceAimBoneKey.update(neglect)
        rcsToggleKey.update()
        boneTriggerKey.update(neglect)
        visualsToggleKey.update(neglect)
        doorSpamKey.update(neglect)
        weaponSpamKey.update(neglect)
        menuKeyField.update(neglect)
    }

    aimTab.tAim.aimToggleKey.update(neglect)
    aimTab.tAim.forceAimKey.update(neglect)
    aimTab.tAim.forceAimBoneKey.update(neglect)
    aimTab.tTrig.boneTriggerKey.update(neglect)
    visualsTab.visualsToggleKey.update(neglect)
    rcsTab.rcsToggleKey.update(neglect)
    miscTab.doorSpamKey.update(neglect)
    miscTab.weaponSpamKey.update(neglect)
    optionsTab.menuKey.update(neglect)
}