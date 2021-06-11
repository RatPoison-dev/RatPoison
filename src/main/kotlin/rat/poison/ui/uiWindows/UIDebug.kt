package rat.poison.ui.uiWindows

import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.VisWindow
import rat.poison.settings.DEBUGTOG
import rat.poison.ui.changed
import rat.poison.ui.uiTabs.VisLabelCustom

class UIDebug: VisWindow("") {
    val table = VisTable(false)

    private val headerLabel = VisLabelCustom("DEBUG")

    val contentLabelLeft = VisLabelCustom("")
    val contentLabelRight = VisLabelCustom("")
    private val toggleShow = VisTextButton("[[]]")

    init {
        toggleShow.changed { _, _ ->
            DEBUGTOG = !DEBUGTOG

            true
        }

        padTop(10F)

        table.add(headerLabel).left().row()
        table.addSeparator().colspan(2).expandX().top().row()
        table.add(contentLabelLeft).expandX().left().top()
        table.add(contentLabelRight).expandX().left().top().row()
        table.add(toggleShow).colspan(2).expandY().right().bottom().padRight(2F).padBottom(3F)

        add(table).growX().fillY().expandY().top()

        width = 500F
        height = 600F
    }
}