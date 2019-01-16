////Courtesy of Mr Noad

package rat.poison.interfaces

interface IOverlayListener {
    fun onTargetAppWindowClosed(overlay: IOverlay)
    fun onAfterInit(overlay: IOverlay)
    fun onActive(overlay: IOverlay)
    fun onPassive(overlay: IOverlay)
    fun onBackground(overlay: IOverlay)
    fun onForeground(overlay: IOverlay)
    fun onBoundsChange(overlay: IOverlay, x: Int, y: Int, width: Int, height: Int)
}