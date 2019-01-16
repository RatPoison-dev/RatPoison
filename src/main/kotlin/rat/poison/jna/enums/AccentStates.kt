////Courtesy of Mr Noad

package rat.poison.jna.enums

import rat.poison.utils.extensions.EnumLookUpWithDefault

enum class AccentStates {
    ACCENT_DISABLED,
    ACCENT_ENABLE_GRADIENT,
    ACCENT_ENABLE_TRANSPARENTGRADIENT,
    ACCENT_ENABLE_BLURBEHIND,
    ACCENT_ENABLE_ACRYLIC,
    ACCENT_INVALID_STATE;

    companion object :
            EnumLookUpWithDefault<AccentStates>(
                    values().associateBy(
                            AccentStates::ordinal
                    ), ACCENT_INVALID_STATE
            )
}