package rat.poison.game.netvars

data class ClassOffset(var className: String = "", var variableName: String = "", var offset: Long = -1L) {
    fun set(className: String, variableName: String, offset: Long): ClassOffset {
        this.className = className
        this.variableName = variableName
        this.offset = offset
        return this
    }
}