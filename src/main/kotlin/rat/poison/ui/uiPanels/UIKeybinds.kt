package rat.poison.ui.uiPanels

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisSlider
import com.kotcrab.vis.ui.widget.VisWindow
import rat.poison.curSettings
import rat.poison.overlay.App.uiKeybinds
import rat.poison.ui.changed
import rat.poison.ui.tabs.othersTable
import rat.poison.ui.uiHelpers.binds.VisBindTableCustom
import kotlin.math.round

class UIKeybinds : VisWindow("Keybinds") {
    val aimToggleKey = VisBindTableCustom("Toggle Aim Key", "AIM_TOGGLE_KEY")
    val forceAimKey = VisBindTableCustom("Force Aim Key", "FORCE_AIM_KEY")
    val forceAimBoneKey = VisBindTableCustom("Force Aim Bone Key", "FORCE_AIM_BONE_KEY")
    val boneTriggerKey = VisBindTableCustom("Trigger Key", "TRIGGER_KEY")
    val visualsToggleKey = VisBindTableCustom("Visuals Toggle Key", "VISUALS_TOGGLE_KEY")
    val nadeThrowerKey = VisBindTableCustom("Nade Thrower Key", "NADE_THROWER_KEY")
    val menuKeyField = VisBindTableCustom("Menu Key", "MENU_KEY")

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
        add(menuKeyField).left().row()
        add(nadeThrowerKey).left().row()
        add(menuAlphaSlider).growX()

        setSize(325F, 325F)
        setPosition(curSettings.float["KEYBINDS_X"], curSettings.float["KEYBINDS_Y"])
        color.a = curSettings.float["KEYBINDS_ALPHA"]
        isResizable = false
    }

    fun changeAlpha(alpha: Float) {
        color.a = alpha
    }

    fun updatePosition(x: Float, y: Float) {
        setPosition(x, y)
    }
}

fun keybindsUpdate(neglect: Actor?) {
    uiKeybinds.apply {
        aimToggleKey.update(neglect)
        forceAimKey.update(neglect)
        forceAimBoneKey.update(neglect)
        boneTriggerKey.update(neglect)
        visualsToggleKey.update(neglect)
        menuKeyField.update(neglect)
        nadeThrowerKey.update(neglect)
    }

    aimTab.tMain.aimToggleKey.update(neglect)
    aimTab.tMain.forceAimKey.update(neglect)
    aimTab.tMain.forceAimBoneKey.update(neglect)
    aimTab.tTrig.boneTriggerKey.update(neglect)
    visualsTab.visualsToggleKey.update(neglect)
    optionsTab.menuKey.update(neglect)
    nadeHelperTab.nadeThrowerKey.update(neglect)
}