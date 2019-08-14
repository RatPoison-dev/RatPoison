package rat.poison.utils.collections

@Suppress("UNCHECKED_CAST")
class ListContainer<E>(capacity: Int) {
	
	private val lists = CacheableList<CacheableList<E>>(capacity)
	
	fun addList(list: CacheableList<E>) = lists.add(list)
	
	fun clear() = lists.clear()
	
	fun empty() = lists.size() == 0
	
	internal inline fun forEach(crossinline action: (E) -> Boolean): Boolean {
		return lists.forEach {
			it.forEach { e ->
				action(e)
			}
		}
	}
	
	fun <E> firstOrNull() = lists[0][0] as E
	
}

internal inline operator fun <E> ListContainer<E>.invoke(crossinline action: (E) -> Boolean) = forEach { action(it) }