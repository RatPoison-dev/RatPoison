package rat.poison.ui.uiWindows

import com.kotcrab.vis.ui.widget.*
import rat.poison.curSettings
import rat.poison.ui.uiTabs.VisLabelExtension

class UIDebug: VisWindow("") {
    val table = VisTable(false)

    private val headerLabel = VisLabelExtension("DEBUG")

    val contentLabelLeft = VisLabelExtension("")
    val contentLabelRight = VisLabelExtension("")

    init {
        padTop(10F)

        table.add(headerLabel).colspan(2).expandX().top().row()
        table.addSeparator().colspan(2).top().row()
        table.add(contentLabelLeft).left().top()
        table.add(contentLabelRight).left().top()

        add(table).growX().expandY().top()

        width = 500F
        height = 600F
    }

    override fun positionChanged() {
        curSettings["UI_WATERMARK_X"] = x
        curSettings["UI_WATERMARK_Y"] = y

        super.positionChanged()
    }
}