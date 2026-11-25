package at.hannibal2.skyhanni.config

import at.hannibal2.skyhanni.utils.compat.InputCompat
import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorKeybind

object InputBindingMigration {
    fun migrate(json: JsonObject) {
        if ((json.get("inputBindingVersion")?.asInt ?: 0) >= 1) return
        // The first 26.3 port updated lastMinecraftVersion without migrating bindings.
        // A GLFW-only modifier or button zero identifies that still-legacy configuration.
        val wasSdl = json.get("lastMinecraftVersion")?.asString?.startsWith("26.3") == true &&
            !hasLegacyBindings(json, SkyHanniConfig::class.java)
        migrateFields(json, SkyHanniConfig::class.java, wasSdl)
        json.addProperty("inputBindingVersion", 1)
    }

    private fun hasLegacyBindings(json: JsonObject, type: Class<*>): Boolean = type.declaredFields.any { field ->
        if (!field.isAnnotationPresent(Expose::class.java)) return@any false
        val value = json.get(field.name) ?: return@any false
        if (field.isAnnotationPresent(ConfigEditorKeybind::class.java) && value.isJsonPrimitive) {
            value.asInt == 0 || value.asInt in 340..348
        } else value.isJsonObject && field.type.name.startsWith("at.hannibal2.skyhanni.config.") &&
            hasLegacyBindings(value.asJsonObject, field.type)
    }

    private fun migrateFields(json: JsonObject, type: Class<*>, wasSdl: Boolean) {
        for (field in type.declaredFields) {
            if (!field.isAnnotationPresent(Expose::class.java)) continue
            val value = json.get(field.name) ?: continue
            if (field.isAnnotationPresent(ConfigEditorKeybind::class.java) && value.isJsonPrimitive) {
                val old = value.asInt
                val migrated = if (wasSdl) {
                    // Old SkyHanni treated 0..5 as mouse buttons, including SDL A and B.
                    if (old in 1..5) InputCompat.MOUSE_OFFSET + old else old
                } else legacyGlfwBinding(old)
                json.addProperty(field.name, migrated)
            } else if (value.isJsonObject && field.type.name.startsWith("at.hannibal2.skyhanni.config.")) {
                migrateFields(value.asJsonObject, field.type, wasSdl)
            }
        }
    }

    /** Physical GLFW key positions to SDL scancodes; no native window is needed. */
    internal fun legacyGlfwBinding(key: Int): Int = when (key) {
        -1 -> -1
        0 -> InputCompat.MOUSE_OFFSET + 1
        1 -> InputCompat.MOUSE_OFFSET + 3
        2 -> InputCompat.MOUSE_OFFSET + 2
        in 3..7 -> InputCompat.MOUSE_OFFSET + key + 1
        in 65..90 -> key - 65 + 4
        in 49..57 -> key - 49 + 30
        48 -> 39
        32 -> 44
        39 -> 52
        44 -> 54
        45 -> 45
        46 -> 55
        47 -> 56
        59 -> 51
        61 -> 46
        91 -> 47
        92 -> 49
        93 -> 48
        96 -> 53
        161 -> 100
        162 -> 50
        256 -> 41
        257 -> 40
        258 -> 43
        259 -> 42
        260 -> 73
        261 -> 76
        262 -> 79
        263 -> 80
        264 -> 81
        265 -> 82
        266 -> 75
        267 -> 78
        268 -> 74
        269 -> 77
        280 -> 57
        281 -> 71
        282 -> 83
        283 -> 70
        284 -> 72
        in 290..301 -> key - 290 + 58
        in 302..313 -> key - 302 + 104
        320 -> 98
        in 321..329 -> key - 321 + 89
        330 -> 99
        331 -> 84
        332 -> 85
        333 -> 86
        334 -> 87
        335 -> 88
        336 -> 103
        340 -> 225
        341 -> 224
        342 -> 226
        343 -> 227
        344 -> 229
        345 -> 228
        346 -> 230
        347 -> 231
        348 -> 101
        else -> -1
    }
}
