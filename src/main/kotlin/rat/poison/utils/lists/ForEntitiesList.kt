package rat.poison.utils.lists

import rat.poison.game.EntityContext

/**
 * Because current implementations of concurrent lists are unreliable
 *
 * @author SPRAVEDLIVO
 */

class ForEntitiesList {
    val cachedValues = mutableListOf<EntityContext>()
    private val addTasks = mutableListOf<EntityContext>()
    var iterating = false

    fun firstOrNull(): EntityContext? = cachedValues.firstOrNull()

    operator fun get(idx: Int): EntityContext = cachedValues[idx]

    fun getOrNull(idx: Int): EntityContext? {
        if (idx < cachedValues.size) {
            return this[idx]
        }
        return null
    }

    fun contains(context: EntityContext): Boolean = cachedValues.contains(context)

    fun clear() {
        while (iterating) {
            Thread.yield()
        }

        cachedValues.clear()
    }

    fun completeTasks() {
        for (i in addTasks) {
            if (!contains(i)) {
                cachedValues.add(i)
            }
        }

        addTasks.clear()
    }

    fun add(context: EntityContext) {
        if (!iterating) {
            cachedValues.add(context)
        } else {
            addTasks.add(context)
        }
    }
}