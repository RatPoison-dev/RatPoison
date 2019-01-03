package rat.poison.utils

class Hook(val clauseDefault: Boolean, val durationDefault: Int,
           val predicate: () -> Boolean) {
	
	inline operator fun invoke(clause: Boolean = clauseDefault,
	                           duration: Int = durationDefault,
	                           crossinline body: () -> Unit) {
		if (!clause) every(duration) {
			if (predicate()) body()
		} else if (predicate()) body()
	}
	
}

fun hook(durationDefault: Int = 8, clauseDefault: Boolean = false, predicate: () -> Boolean)
		= Hook(clauseDefault, durationDefault, predicate)
