package at.hannibal2.skyhanni.features.misc.items

import at.hannibal2.skyhanni.SkyHanniMod
import at.hannibal2.skyhanni.api.event.HandleEvent
import at.hannibal2.skyhanni.events.minecraft.ToolTipTextEvent
import at.hannibal2.skyhanni.events.minecraft.add
import at.hannibal2.skyhanni.skyhannimodule.SkyHanniModule
import at.hannibal2.skyhanni.features.inventory.bazaar.BazaarApi.getBazaarData
import at.hannibal2.skyhanni.utils.ItemPriceUtils.getLowestBinOrNull
import at.hannibal2.skyhanni.utils.ItemPriceUtils.getNpcPriceOrNull
import at.hannibal2.skyhanni.utils.ItemUtils.getInternalNameOrNull
import at.hannibal2.skyhanni.utils.NumberUtil.addSeparators

@SkyHanniModule
object EstimatedItemValueTooltip {

    @HandleEvent(onlyOnSkyblock = true)
    fun onTooltip(event: ToolTipTextEvent) {
        val config = SkyHanniMod.feature.inventory.estimatedItemValues
        if (!config.showTooltip && !config.showMarketPrices) return
        val internalName = event.itemStack.getInternalNameOrNull() ?: return

        if (config.showMarketPrices) {
            val bazaar = internalName.getBazaarData()
            val prices = marketPriceLines(
                event.itemStack.count,
                bazaar?.instantBuyPrice,
                bazaar?.instantSellPrice,
                if (bazaar == null) internalName.getLowestBinOrNull() else null,
                internalName.getNpcPriceOrNull(),
            )
            if (prices.isNotEmpty()) {
                event.toolTip.add("")
                prices.forEach { event.toolTip.add(it) }
            }
        }

        if (!config.showTooltip) return

        val total = EstimatedItemValueCalculator.getTotalPrice(event.itemStack, ignoreBasePrice = true) ?: return
        event.toolTip.add("§e§lEstimated Value: §6§l${total.addSeparators()} coins")
    }

    internal fun marketPriceLines(
        count: Int,
        bazaarBuy: Double?,
        bazaarSell: Double?,
        lowestBin: Double?,
        npcSell: Double?,
    ): List<String> = buildList {
        for ((label, price) in listOf(
            "Bazaar Buy" to bazaarBuy,
            "Bazaar Sell" to bazaarSell,
            "Lowest BIN" to lowestBin,
            "NPC Sell" to npcSell,
        )) {
            if (price == null || !price.isFinite() || price <= 0) continue
            val stack = if (count > 1) " §7(stack: §6${(price * count).addSeparators()}§7)" else ""
            add("§e$label: §6${price.addSeparators()} coins$stack")
        }
    }
}
