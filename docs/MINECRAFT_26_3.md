# Minecraft 26.3 build

This fork adds a Fabric build target for Minecraft 26.3. Java 25 is required.
The source remains on Stonecutter's 26.2 version; 26.3 changes are conditional.

```powershell
.\gradlew.bat :26.3:build
```

The packaged mod is written to `build/libs/SkyHanni-9.5.0.jar`.
Install Fabric Loader 0.20.1 or newer, Fabric API 0.167.0+26.3 or newer,
Fabric Language Kotlin 1.14.2+kotlin.2.4.21 or newer, and Hypixel Mod API
1.0.2+build.1+mc26.1 (whose published compatibility includes 26.3).
Render Chest 2.0.0-beta.2+26.3 is bundled. MoulConfig 4.8.0 is shaded.

## Validation and scope

`:26.3:build` passes all 150 tests (including input migration, price-tooltip and command-completion regression tests), including the Fabric mixin audit,
and validates the class tweaker. No tests or mod modules were excluded to
make this build pass. All 993 SkyHanni modules are generated.

This remains a candidate build, not a fully gameplay-validated release.
An isolated production client was launched with the distributable jar, loaded
SkyHanni and its mixins, created its SDL/OpenGL window and began resource loading.
It then exited with native Windows status 0xC0000409, without a Java crash report.
The cause has not been established. Interactive checks were interrupted by the
user stopping computer control with Escape; no new in-game checks are claimed.
Custom shaders, item rendering and live Hypixel behavior remain unverified.

## Input compatibility audit

Mouse bindings now occupy a separate range from SDL keyboard scancodes, including
side buttons. The keybind editor and displayed key names use that distinction.
Existing GLFW configurations are migrated once, including early port configurations
whose Minecraft version was already updated but whose old bindings remained.
An old configuration containing button zero or GLFW modifier keys is identified as
legacy; mixed manually edited configurations may still need individual rebinding.
For old SDL configurations, values 1..5 preserve their previous mouse behavior;
ambiguous A/B bindings must be reassigned in the new editor.

Mouse release sends a release event instead of another press, custom screen dragging
uses absolute coordinates, and the update button uses the current native left button.
Vanilla key mappings retain their input type when used by custom Garden bindings.

`./gradlew :26.3:prodClient` now loads the shaded distributable and runtime mods in
an isolated run directory with DevAuth disabled. It does not use a user's account.

## Item tooltip prices

Market prices are enabled by default under Estimated Item Value → Market Prices on Tooltip.
Tooltips show Bazaar instant buy/sell, Lowest BIN for non-Bazaar items, and NPC sell prices
when available, with stack totals for multiple items. Missing or invalid prices are omitted.
These use the existing background price caches; no network requests run while rendering tooltips.
The separate Show on Tooltip setting still controls the estimated value including upgrades.
The generic market price lines were previously absent, and estimated values deliberately
omitted items without upgrades. Price rendering still needs verification in a live SkyBlock session.

## Command completion

Custom completions now receive the text before the cursor in the correct field,
preserving trailing text when editing a command. Argument suggestions replace only
the last word even when the client dispatcher does not recognize a server command.
Tests cover these cases, retaining existing root suggestions and falling back to
vanilla when no custom suggestion matches. This does not validate live server
suggestions or physical Tab key handling in a running client.

API references used for the port:

- [Fabric 26.3 changes](https://fabricmc.net/2026/09/15/263.html)
- [Minecraft 26.2 to 26.3 migration primer](https://docs.neoforged.net/primer/docs/26.3/)
