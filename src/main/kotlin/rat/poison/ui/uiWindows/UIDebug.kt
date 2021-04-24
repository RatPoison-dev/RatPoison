package rat.poison.ui.uiWindows

import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.building.utilities.Alignment
import com.kotcrab.vis.ui.widget.*
import rat.poison.curSettings
import rat.poison.settings.DEBUGTOG
import rat.poison.ui.changed
import rat.poison.ui.uiTabs.VisLabelExtension

class UIDebug: VisWindow("") {
    val table = VisTable(false)

    private val headerLabel = VisLabelExtension("DEBUG")

    val contentLabelLeft = VisLabelExtension("")
    val contentLabelRight = VisLabelExtension("")
    private val toggleShow = VisTextButton("[[]]")

    init {
        toggleShow.changed { _, _ ->
            DEBUGTOG = !DEBUGTOG

            true
        }

        padTop(10F)

        table.add(headerLabel).colspan(2).expandX().top().row()
        table.addSeparator().colspan(2).expandX().top().row()
        table.add(contentLabelLeft).expandX().left().top()
        table.add(contentLabelRight).expandX().left().top().row()
        table.add(toggleShow).colspan(2).expandY().right().bottom().padRight(2F).padBottom(3F)

        add(table).growX().fillY().expandY().top()

        width = 500F
        height = 600F
    }
}