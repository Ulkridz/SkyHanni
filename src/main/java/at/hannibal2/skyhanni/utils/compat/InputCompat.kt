package at.hannibal2.skyhanni.utils.compat

import com.mojang.blaze3d.platform.InputConstants

/** Keeps mouse bindings separate from SDL keyboard scancodes (A is 4, B is 5). */
object InputCompat {
    const val MOUSE_OFFSET = 1000

    fun mouseBinding(button: Int): Int {
        //? if >= 26.3 {
        /*return MOUSE_OFFSET + button
        *///?} else {
        return button
        //?}
    }

    fun mouseButton(binding: Int): Int? {
        //? if >= 26.3 {
        /*return (binding - MOUSE_OFFSET).takeIf { it in 1..8 }
        *///?} else {
        return binding.takeIf { it in 0..7 }
        //?}
    }

    fun binding(key: InputConstants.Key): Int =
        if (key.type == InputConstants.Type.MOUSE) mouseBinding(key.value) else key.value
}
