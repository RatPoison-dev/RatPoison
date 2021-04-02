package rat.poison.game

data class Color(var red: Int = 0, var green: Int = 0, var blue: Int = 0, var alpha: Double = 0.6) {
    fun set(r: Int, g: Int, b: Int, a: Double): Color {
        this.red = r
        this.green = g
        this.blue = b
        this.alpha = a
        return this
    }
}