package rat.poison.utils

import com.badlogic.gdx.files.FileHandle
import rat.poison.interfaces.ISettings

//Remove ISettings

class Settings : ISettings {
    private val defaultValues = mutableMapOf<String, String>()
    private val currentlySavedValues = mutableMapOf<String, String>()
    private val pendingToSaveValues = mutableMapOf<String, String>()

    override fun get(key: String): String? {
        if (pendingToSaveValues.containsKey(key)) {
            return pendingToSaveValues[key]
        }
        if (currentlySavedValues.containsKey(key)) {
            return currentlySavedValues[key]
        }
        if (defaultValues.containsKey(key)) {
            return defaultValues[key]
        }
        return null
    }

    override fun put(key: String, value: Any?): Any? {
        return pendingToSaveValues.put(key, value.toString())
    }

    override fun containsValue(value: Any?): Boolean {
        TODO("containsValue")
    }
    override val entries: MutableSet<MutableMap.MutableEntry<String, Any?>>
        get() = TODO("entries")
    override val keys: MutableSet<String>
        get() = TODO("keys")
    override val size: Int
        get() = TODO("size")
    override val values: MutableCollection<Any?>
        get() = TODO("values")
    override fun clear() {}
    override fun putAll(from: Map<out String, Any?>) {}
    override fun remove(key: String): Any? {return false}
    override fun isEmpty(): Boolean {return false}
    override fun addSettingListener(listener: ISettings.ISettingListener) {}
    override fun removeSettingListener(listener: ISettings.ISettingListener) {}
    override fun initSetting(name: String, defaultValue: Any?, requireRestart: Boolean): ISettings {return this}
    override fun loadSettings(fileHandle: FileHandle): ISettings {return this}
    override fun saveSettings(fileHandle: FileHandle): ISettings {return this}
    override fun revertSettings(): ISettings {return this}
    override fun isChangesRequireRestart(): Boolean = false
    override fun isSettingChangesPending() = false
    override fun revertSettingsToDefaults(): ISettings {return this}
    override fun containsKey(key: String): Boolean {return false}
}