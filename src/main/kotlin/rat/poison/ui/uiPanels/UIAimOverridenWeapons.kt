package rat.poison.ui.uiPanels

import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisWindow
import rat.poison.App.uiMenu
import rat.poison.curLocalization
import rat.poison.opened
import rat.poison.ui.uiPanelTables.OverridenWeapons

val overridenWeapons = OverridenWeapons()

class UIAimOverridenWeapons : VisWindow(curLocalization["OVERRIDE_WEAPONS_PANEL_NAME"]) {
    init {
        defaults().left()
        align(Align.topLeft)

        add(overridenWeapons).left()

        pack()

        setSize(300F, uiMenu.height)
        isResizable = false
    }

    override fun positionChanged() { //Not draggable
        if (opened) {
            uiMenu.setPosition(x-uiMenu.width-4F, y)
        }
    }

    fun changeAlpha(alpha: Float) {
        color.a = alpha
    }
    fun update() {
        this.titleLabel.setText(curLocalization["OVERRIDE_WEAPONS_PANEL_NAME"])
    }
}