package rat.poison.interfaces

interface IKeyProcessorListener {
    fun onPress(keycode: Int, type: String)
}