package rat.poison.ui.uiWindows

import com.kotcrab.vis.ui.widget.*
import rat.poison.overlay.App.uiMenu
import rat.poison.settings.WARNINGTOG
import rat.poison.ui.changed
import rat.poison.ui.uiTabs.VisLabelExtension

class UIWarning: VisWindow("") {
    val table = VisTable(false)

    private val headerLabel = VisLabelExtension("WARNING")

    private val contentTable = VisLabelExtension("This is a debug build and is prone to bugs and various config issues, use at your own risk.")

    private val toggleUIWarning = VisTextButton("Hide")

    init {
        padTop(10F)

        toggleUIWarning.changed { _, _ ->
            WARNINGTOG = false
            true
        }

        table.add(headerLabel).colspan(2).expandX().top().row()
        table.addSeparator().colspan(2).expandX().top().row()
        table.add(contentTable).expandX().left().top()
        table.add(toggleUIWarning).expandX().right().top()

        add(table).growX().expandY().top()

        width = 950F
        height = 60F
    }
}