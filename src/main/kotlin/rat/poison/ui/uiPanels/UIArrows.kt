package rat.poison.ui.uiPanels

import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.VisWindow
import rat.poison.overlay.App.uiMenu
import rat.poison.ui.changed

class UIArrows : VisWindow("") {

    var upButton = VisTextButton("UP")
    var downButton = VisTextButton("DOWN")

    init {
        setSize(100F, 100F)

        this.

        upButton.changed { _, _ ->
            uiMenu.mainScrollPane.scrollY -= 100

            true
        }

        downButton.changed { _, _ ->
            uiMenu.mainScrollPane.scrollY += 100

            true
        }

        add(upButton).width(100F).row()
        add(downButton).width(100F)
    }
}