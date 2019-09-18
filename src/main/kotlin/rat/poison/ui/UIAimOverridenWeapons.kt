package rat.poison.ui

import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisWindow
import rat.poison.App.uiMenu
import rat.poison.curSettings
import rat.poison.opened
import rat.poison.ui.uiPanels.OverridenWeapons

val overridenWeapons = OverridenWeapons()

class UIAimOverridenWeapons : VisWindow("Override Weapons") {
    init {
        defaults().left()
        align(Align.topLeft)

        add(overridenWeapons).left()

        pack()

        setSize(300F, uiMenu.height)
        isResizable = true
    }

    override fun positionChanged() { //Not draggable
//        if (opened) {
//            setPosition(uiMenu.x+uiMenu.width+4F, uiMenu.y)
//        }
        if (opened) {
            uiMenu.setPosition(x-uiMenu.width-4F, y)
        }
    }

    fun changeAlpha(alpha: Float) {
        color.a = alpha
    }
}