package at.hannibal2.skyhanni.test

import at.hannibal2.skyhanni.features.misc.items.EstimatedItemValueTooltip
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ItemPriceTooltipTest {
    @Test
    fun `bazaar items include buy sell and stack totals`() {
        val lines = EstimatedItemValueTooltip.marketPriceLines(64, 10.0, 8.0, null, 2.0)
        assertEquals(3, lines.size)
        assertTrue(lines[0].contains("Bazaar Buy: §610 coins"))
        assertTrue(lines[0].contains("stack: §6640"))
        assertTrue(lines[1].contains("Bazaar Sell: §68 coins"))
        assertTrue(lines[1].contains("stack: §6512"))
        assertTrue(lines[2].contains("NPC Sell: §62 coins"))
    }

    @Test
    fun `auction items include lowest bin without bazaar data`() {
        val lines = EstimatedItemValueTooltip.marketPriceLines(1, null, null, 500.0, null)
        assertEquals(listOf("§eLowest BIN: §6500 coins"), lines)
    }

    @Test
    fun `unavailable and invalid prices never display as zero`() {
        assertTrue(EstimatedItemValueTooltip.marketPriceLines(1, null, 0.0, -1.0, Double.NaN).isEmpty())
        assertTrue(EstimatedItemValueTooltip.marketPriceLines(1, Double.POSITIVE_INFINITY, null, null, null).isEmpty())
    }
}
