package rat.poison.ui.uiPanels

import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisWindow
import rat.poison.overlay.App.uiMenu
import rat.poison.overlay.opened
import rat.poison.toLocale
import rat.poison.ui.uiPanelTables.OverridenWeapons

var overridenWeapons = OverridenWeapons()

class UIAimOverridenWeapons : VisWindow("Override-Weapons".toLocale()) {
    init {
        defaults().left()
        align(Align.topLeft)

        add(overridenWeapons).left()

        pack()

        setSize(300F, uiMenu.height)
        isResizable = true
    }

    override fun positionChanged() { //Not draggable
        if (opened) {
            uiMenu.setPosition(x-uiMenu.width-4F, y)
        }
    }

    fun changeAlpha(alpha: Float) {
        color.a = alpha
    }
}