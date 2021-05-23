package rat.poison.utils.extensions

import com.kotcrab.vis.ui.widget.VisLabel

class VisLabelExtension(mainText: String): VisLabel(mainText) {
    override fun setText(newText: CharSequence?) {
        if (this.text != newText) {
            super.setText(newText)
        }
    }
}