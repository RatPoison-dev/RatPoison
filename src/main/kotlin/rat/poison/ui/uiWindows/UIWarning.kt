package rat.poison.ui.uiWindows

import com.kotcrab.vis.ui.widget.*
import rat.poison.overlay.App.uiMenu
import rat.poison.ui.uiTabs.VisLabelExtension

class UIWarning: VisWindow("") {
    val table = VisTable(false)

    private val headerLabel = VisLabelExtension("WARNING")

    private val contentTable = VisLabelExtension("This is a debug build and is prone to bugs and various config issues, use at your own risk.")

    init {
        padTop(10F)

        table.add(headerLabel).expandX().top().row()
        table.addSeparator().expandX().top().row()
        table.add(contentTable).expandX().left().top()

        add(table).growX().expandY().top()

        width = 950F
        height = 60F
    }
}