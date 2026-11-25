package at.hannibal2.skyhanni.test

import at.hannibal2.skyhanni.features.chat.CopyChat
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CopyChatMouseButtonTest {
    @Test
    fun `normal click does not copy chat`() {
        // Native event values, rather than deriving expectations from the implementation.
        //? if >= 26.3 {
        /*assertFalse(CopyChat.isCopyButton(1))
        *///?} else {
        assertFalse(CopyChat.isCopyButton(0))
        //?}
    }

    @Test
    fun `right click copies chat`() {
        //? if >= 26.3 {
        /*assertTrue(CopyChat.isCopyButton(3))
        *///?} else {
        assertTrue(CopyChat.isCopyButton(1))
        //?}
    }

    @Test
    fun `middle and side clicks do not copy chat`() {
        assertFalse(CopyChat.isCopyButton(2))
        assertFalse(CopyChat.isCopyButton(4))
        assertFalse(CopyChat.isCopyButton(5))
    }
}
