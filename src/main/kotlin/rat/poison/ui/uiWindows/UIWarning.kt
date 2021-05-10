package rat.poison.ui.uiWindows

import com.kotcrab.vis.ui.widget.*
import rat.poison.settings.WARNINGTOG
import rat.poison.ui.changed
import rat.poison.ui.uiTabs.VisLabelCustom
import rat.poison.ui.uiTabs.VisTextButtonCustom
import rat.poison.utils.locale

class UIWarning: VisWindow("") {
    val table = VisTable(false)

    private val headerLabel = VisLabelCustom("L_WARNING")

    private val contentTable = VisLabelCustom("L_WARNING_TEXT")

    private val toggleUIWarning = VisTextButtonCustom("L_HIDE")

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