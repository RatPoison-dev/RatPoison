package rat.poison.ui.uiWindows

import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.Separator
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisWindow
import rat.poison.BRANCH
import rat.poison.curSettings

class UIWatermark: VisWindow("", "watermark") {
    val table = VisTable(false)

    val watermarkText = VisLabel("[$BRANCH] ${System.currentTimeMillis()}", Align.top)
    val keybindText = VisLabel("", Align.topRight)

    init {
        padTop(10F)

        add(watermarkText).expandX().top().right().row()
        add(Separator()).growX().row()
        add(keybindText).expandX().height(64F).right()

        width = 300F
        height = 100F
    }

    override fun positionChanged() {
        curSettings["UI_WATERMARK_X"] = x
        curSettings["UI_WATERMARK_Y"] = y

        super.positionChanged()
    }

    fun update() {
        if (!dragging) {
            setPosition(curSettings.float["UI_WATERMARK_X"], curSettings.float["UI_WATERMARK_Y"])
        } else {
            curSettings["UI_WATERMARK_X"] = x
            curSettings["UI_WATERMARK_Y"] = y
        }
    }
}