package at.hannibal2.skyhanni.test

import at.hannibal2.skyhanni.events.chat.TabCompletionEvent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class TabCompletionTest {
    @Test
    fun `unknown server command completes only the argument`() {
        val input = "/warp hu"
        val event = TabCompletionEvent.fromInput(input, input.length, emptyList())
        event.addSuggestion("hub")
        assertEquals("/warp hub", event.buildSuggestions(1)!!.list.single().apply(input))
    }

    @Test
    fun `completion in middle uses cursor prefix and preserves suffix`() {
        val input = "/gfs enc 64"
        val event = TabCompletionEvent.fromInput(input, 8, emptyList())
        assertEquals("/gfs enc", event.leftOfCursor)
        assertEquals(input, event.fullText)
        assertEquals("gfs", event.command)
        event.addSuggestion("enchanted_diamond")
        assertEquals("/gfs enchanted_diamond 64", event.buildSuggestions(1)!!.list.single().apply(input))
    }

    @Test
    fun `no matching custom completion leaves vanilla in control`() {
        val event = TabCompletionEvent.fromInput("/warp x", 7, listOf("existing"))
        event.addSuggestion("hub")
        assertNull(event.buildSuggestions(1))
    }

    @Test
    fun `root completion keeps existing suggestions`() {
        val event = TabCompletionEvent.fromInput("/wa", 3, listOf("warp"))
        event.addSuggestionUnchecked("/warphome")
        assertEquals(listOf("/warp", "/warphome"), event.buildSuggestions(1)!!.list.map { it.apply("/wa") })
    }
}
