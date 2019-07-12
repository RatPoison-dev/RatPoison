package rat.poison.ui

import com.badlogic.gdx.scenes.scene2d.Actor
import com.kotcrab.vis.ui.widget.VisTable

object TableBuilder {
    fun build(text: String, labelWidth: Int, actor: Actor): VisTable {
        val table = VisTable(true)
        table.add(text).width(labelWidth.toFloat())
        table.add(actor)
        return table
    }

    fun build(vararg actors: Actor): VisTable {
        return build(VisTable(true), *actors)
    }

    fun build(rightSpacing: Int, vararg actors: Actor): VisTable {
        val table = VisTable(true)
        table.defaults().spaceRight(rightSpacing.toFloat())
        return build(table, *actors)
    }

    private fun build(target: VisTable, vararg actors: Actor): VisTable {
        for (actor in actors) target.add(actor)
        return target
    }
}