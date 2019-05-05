package rat.poison.interfaces

import com.badlogic.gdx.files.FileHandle

interface ISettings : MutableMap<String, Any?> {
    fun loadSettings(fileHandle: FileHandle): ISettings
    fun saveSettings(fileHandle: FileHandle): ISettings
    fun revertSettings(): ISettings
    fun revertSettingsToDefaults(): ISettings
    fun isChangesRequireRestart(): Boolean
    fun isSettingChangesPending(): Boolean

    fun addSettingListener(listener: ISettingListener)
    fun removeSettingListener(listener: ISettingListener)

    fun initSetting(name: String, defaultValue: Any? = null, requireRestart: Boolean = false): ISettings

    interface ISettingListener {
        fun onChange(settingName: String, oldValue: Any?, newValue: Any?)
        fun onLoad(fileHandle: FileHandle)
        fun onSave(fileHandle: FileHandle)
        fun onRevert()
        fun onRevertToDefault()
    }
}