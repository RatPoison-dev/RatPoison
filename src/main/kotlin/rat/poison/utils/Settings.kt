package rat.poison.utils

class Settings : MutableMap<String, Any?> {
    private val savedValues = mutableMapOf<String, String>()

    override fun get(key: String): String {
        if (savedValues.containsKey(key)) {
            return savedValues[key].toString()
        }
        return ""
    }

    override fun put(key: String, value: Any?): Any? {
        return savedValues.put(key, value.toString())
    }

    override fun containsValue(value: Any?): Boolean {
        return savedValues.containsValue(value)
    }
    override val entries: MutableSet<MutableMap.MutableEntry<String, Any?>>
        get() = TODO("entries")
    override val keys: MutableSet<String>
        get() = savedValues.keys
    override val size: Int
        get() = savedValues.size
    override val values: MutableCollection<Any?>
        get() = mutableListOf()
    override fun clear() {}
    override fun putAll(from: Map<out String, Any?>) {}
    override fun remove(key: String): Any? {return false}
    override fun isEmpty(): Boolean {return false}
    override fun containsKey(key: String): Boolean {return false}
}