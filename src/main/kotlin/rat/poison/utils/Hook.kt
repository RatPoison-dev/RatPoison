package rat.poison.utils

class Hook(val clauseDefault: Boolean, val durationDefault: Long,
           val predicate: () -> Boolean) {
	
	inline operator fun invoke(clause: Boolean = clauseDefault,
	                           duration: Long = durationDefault,
	                           crossinline body: () -> Unit) {
		if (!clause) HighPriority.every(duration) {
			if (predicate()) body()
		} else if (predicate()) body()
	}
	
}

fun hook(durationDefault: Long = 8, clauseDefault: Boolean = false, predicate: () -> Boolean)
		= Hook(clauseDefault, durationDefault, predicate)
