package at.hannibal2.skyhanni.test

import at.hannibal2.skyhanni.config.InputBindingMigration
import at.hannibal2.skyhanni.utils.compat.InputCompat
import com.google.gson.JsonParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class InputCompatibilityTest {
    @Test
    fun `SDL letter keys cannot be mistaken for mouse buttons`() {
        //? if >= 26.3 {
        /*for (key in 4..29) assertNull(InputCompat.mouseButton(key))
        for (button in 1..8) assertEquals(button, InputCompat.mouseButton(InputCompat.mouseBinding(button)))
        *///?}
    }

    @Test
    fun `legacy bindings preserve physical keyboard and mouse controls`() {
        assertEquals(4, InputBindingMigration.legacyGlfwBinding(65))
        assertEquals(43, InputBindingMigration.legacyGlfwBinding(258))
        assertEquals(225, InputBindingMigration.legacyGlfwBinding(340))
        assertEquals(1001, InputBindingMigration.legacyGlfwBinding(0))
        assertEquals(1003, InputBindingMigration.legacyGlfwBinding(1))
        assertEquals(1002, InputBindingMigration.legacyGlfwBinding(2))
        assertEquals(-1, InputBindingMigration.legacyGlfwBinding(-1))
    }

    @Test
    fun `migration only changes keybind fields and runs once`() {
        val json = JsonParser.parseString("""{
            "lastMinecraftVersion": "26.2",
            "inventory": {"estimatedItemValues": {"hotkey": 65, "hoverDelay": 65}}
        }""").asJsonObject
        InputBindingMigration.migrate(json)
        val config = json.getAsJsonObject("inventory").getAsJsonObject("estimatedItemValues")
        assertEquals(4, config.get("hotkey").asInt)
        assertEquals(65, config.get("hoverDelay").asInt)
        InputBindingMigration.migrate(json)
        assertEquals(4, config.get("hotkey").asInt)
    }

    @Test
    fun `existing SDL mouse binding is separated without changing keyboard`() {
        val json = JsonParser.parseString("""{
            "lastMinecraftVersion": "26.3",
            "inventory": {"estimatedItemValues": {"hotkey": 3}},
            "garden": {"keyBind": {"forward": 26}}
        }""").asJsonObject
        InputBindingMigration.migrate(json)
        assertEquals(1003, json.getAsJsonObject("inventory").getAsJsonObject("estimatedItemValues").get("hotkey").asInt)
        assertEquals(26, json.getAsJsonObject("garden").getAsJsonObject("keyBind").get("forward").asInt)
    }

    @Test
    fun `first port legacy bindings are migrated despite saved 26_3 version`() {
        val json = JsonParser.parseString("""{
            "lastMinecraftVersion": "26.3",
            "garden": {"keyBind": {"forward": 87, "sneak": 340, "attack": 0, "useItem": 1}}
        }""").asJsonObject
        InputBindingMigration.migrate(json)
        val keys = json.getAsJsonObject("garden").getAsJsonObject("keyBind")
        assertEquals(26, keys.get("forward").asInt)
        assertEquals(225, keys.get("sneak").asInt)
        assertEquals(1001, keys.get("attack").asInt)
        assertEquals(1003, keys.get("useItem").asInt)
    }
}
