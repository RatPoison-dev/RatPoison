////Courtesy of Mr Noad

package rat.poison.jna.enums

import rat.poison.utils.extensions.EnumLookUpWithDefault

enum class WindowCompositionAttributes(val id: Int) {
    WCA_UNKNOWN(0),
    WCA_ACCENT_POLICY(19);

    companion object :
        EnumLookUpWithDefault<WindowCompositionAttributes>(
                values().associateBy(
                        WindowCompositionAttributes::id
                ), WCA_UNKNOWN
        )
}