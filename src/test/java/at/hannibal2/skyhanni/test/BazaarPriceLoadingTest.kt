package at.hannibal2.skyhanni.test

import at.hannibal2.skyhanni.config.ConfigManager
import at.hannibal2.skyhanni.data.bazaar.BazaarApiResponseJson
import at.hannibal2.skyhanni.data.bazaar.HypixelBazaarFetcher
import at.hannibal2.skyhanni.features.misc.items.EstimatedItemValueTooltip
import at.hannibal2.skyhanni.utils.NeuInternalName.Companion.toInternalName
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BazaarPriceLoadingTest {
    private fun response(quickStatus: String = """"buyPrice":1359.25,"sellPrice":1274.5"""): BazaarApiResponseJson =
        ConfigManager.gson.fromJson("""{
          "success":true,"lastUpdated":1795576192624,"products":{
            "ENCHANTED_DIAMOND":{
              "product_id":"ENCHANTED_DIAMOND",
              "quick_status":{$quickStatus},
              "buy_summary":[{"amount":165,"pricePerUnit":1358.1,"orders":1},
                             {"amount":496,"pricePerUnit":1358.2,"orders":1}],
              "sell_summary":[{"amount":100,"pricePerUnit":1274.5,"orders":1}]
            }
          }
        }""", BazaarApiResponseJson::class.java)

    @Test
    fun `API prices reach tooltip without a Minecraft item or NEU repo`() {
        val prices = HypixelBazaarFetcher.process(response().products, { it.toInternalName() }, { it.asString() })
        val product = prices.getValue("ENCHANTED_DIAMOND".toInternalName())
        assertEquals(1358.1, product.instantBuyPrice)
        assertEquals(1274.5, product.instantSellPrice)
        val tooltip = EstimatedItemValueTooltip.marketPriceLines(1, product.instantBuyPrice, product.instantSellPrice, null, null)
        assertEquals(2, tooltip.size)
        assertTrue(tooltip[0].contains("Bazaar Buy"))
        assertTrue(tooltip[1].contains("Bazaar Sell"))
    }

    @Test
    fun `valid order book survives empty quick status`() {
        val prices = HypixelBazaarFetcher.process(response(""""buyPrice":0,"sellPrice":0""").products,
            { it.toInternalName() }, { it.asString() })
        assertEquals(1, prices.size)
        assertEquals(1358.1, prices.values.single().instantBuyPrice)
    }
}
