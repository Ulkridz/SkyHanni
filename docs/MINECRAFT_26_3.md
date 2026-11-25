# Minecraft 26.3 build

This fork adds a Fabric build target for Minecraft 26.3. Java 25 is required.
The source remains on Stonecutter's 26.2 version; 26.3 changes are conditional.

```powershell
.\gradlew.bat :26.3:build
```

The packaged mod is written to `build/libs/SkyHanni-9.5.0-mc26.3.jar`.
Install Fabric Loader 0.20.1 or newer, Fabric API 0.167.0+26.3 or newer,
Fabric Language Kotlin 1.14.2+kotlin.2.4.21 or newer, and Hypixel Mod API
1.0.2+build.1+mc26.1 (whose published compatibility includes 26.3).
Render Chest 2.0.0-beta.2+26.3 is bundled. MoulConfig 4.8.0 is shaded.

## Validation and scope

`:26.3:build` passes all 138 tests (including mouse-button regression tests), including the Fabric mixin audit,
and validates the class tweaker. No tests or mod modules were excluded to
make this build pass. All 993 SkyHanni modules are generated.

This is a compilation port, not a gameplay-tested release. No interactive
Minecraft or Hypixel session was launched. In particular, existing saved
key bindings have not been migrated from GLFW to SDL. SDL scancodes overlap
some mouse-button values in the inherited combined input representation;
keyboard/mouse binding behavior needs a separate runtime compatibility pass.
The custom shaders and item rendering also require in-game visual testing.

API references used for the port:

- [Fabric 26.3 changes](https://fabricmc.net/2026/09/15/263.html)
- [Minecraft 26.2 to 26.3 migration primer](https://docs.neoforged.net/primer/docs/26.3/)
