package rat.poison.ui

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.DragListener
import rat.poison.DEFAULT_OWEAPON_STR
import rat.poison.curSettings
import rat.poison.utils.generalUtil.pull

fun Actor.changed(callback: (ChangeListener.ChangeEvent, Actor) -> Any?): ChangeListener {
    val listener = object : ChangeListener() {
        override fun changed(event: ChangeEvent?, actor: Actor?) {
            callback.invoke(event!!, actor!!)
        }
    }
    this.addListener(listener)
    return listener
}

fun Actor.dragged(callback: (InputEvent, Float, Float, Int) -> Any?): DragListener {
    val listener = object : DragListener() {
        override fun drag(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            super.drag(event, x, y, pointer)
            callback.invoke(event!!, x, y, pointer)
        }
    }
    this.addListener(listener)
    return listener
}

fun Actor.realPos(): Vector2 { //fuck nigga gon add me up
    val v2 = Vector2()

    var parent: Actor = this

    while (parent.parent != null) {
        v2.x += parent.x
        v2.y += parent.y

        parent = parent.parent
    }

    return v2
}

fun splitOverrideString(curWep: String): MutableList<String> {
    var tStr = curWep
    tStr = tStr.replace("oWeapon(", "").replace(")", "")
    val tSA = tStr.split(", ")

    return tSA as MutableList<String>
}

fun getOverrideVar(curWep: String, index: Int): String {
    val tSA = splitOverrideString(curSettings[curWep])
    if (tSA.size <= index) {
        return splitOverrideString(DEFAULT_OWEAPON_STR).pull(index)
    }

    return tSA.pull(index)
}

fun getOverrideVarIndex(curWep: String, varName: String): Int {
    val tSA = splitOverrideString(curWep)

    var idx = -1
    for (i in tSA.indices) {
        if (tSA[i].split("=")[0] == varName) {
            idx = i
        }
    }
    return idx
}

fun setOverrideVar(curWep: String, index: Int, value: Any) {
    val tSA = splitOverrideString(curSettings[curWep])
    tSA[index] = tSA[index].split("=".toRegex(), 2)[0] + "=" + value.toString()
    var newWep = tSA.toString()
    newWep = "oWeapon("+newWep.substring(1, newWep.length - 1)+")"
    curSettings[curWep] = newWep
}