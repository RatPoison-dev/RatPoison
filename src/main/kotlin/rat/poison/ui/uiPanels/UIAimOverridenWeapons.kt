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

        add(overridenWeapons).top().left()

        pack()

        setSize(400F, 684F)
        isResizable = false
    }

    override fun positionChanged() { //Not draggable
        if (opened) {
            setPosition(uiMenu.x + uiMenu.width + 4F, uiMenu.y + uiMenu.height - height)
        }
    }

    fun changeAlpha(alpha: Float) {
        color.a = alpha
    }
}