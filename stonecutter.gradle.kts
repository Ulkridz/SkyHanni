plugins {
    alias(libs.plugins.loom) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.detekt) apply false
    id("dev.kikugie.stonecutter")
}

val projectVersion = providers.fileContents(layout.projectDirectory.file("buildSrc/PROJECT_VERSION")).asText.map(String::trim)

allprojects {
    group = "at.hannibal2.skyhanni"

    /**
     * The version of the project.
     * Stable version
     * Beta version
     * Bugfix version
     */
    version = projectVersion.get()

    repositories {
        mavenCentral()
        mavenLocal()

        // Fabric
        exclusiveContent {
            forRepository {
                maven("https://maven.fabricmc.net")
            }
            filter {
                includeGroupAndSubgroups("net.fabricmc")
            }
        }

        // Mixin
        exclusiveContent {
            forRepository {
                maven("https://repo.spongepowered.org/repository/maven-public")
            }
            filter {
                includeGroup("org.spongepowered")
            }
        }

        // DevAuth
        exclusiveContent {
            forRepository {
                maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
            }
            filter {
                includeGroup("me.djtheredstoner")
            }
        }

        // libautoupdate
        exclusiveContent {
            forRepository {
                maven("https://repo.nea.moe/releases")
            }
            filter {
                includeGroup("moe.nea")
            }
        }

        // MoulConfig and a few Detekt rules
        exclusiveContent {
            forRepositories(
                repositories.mavenLocal(),
                repositories.maven("https://maven.notenoughupdates.org/releases"),
            )
            filter {
                includeGroupAndSubgroups("org.notenoughupdates")
            }
        }

        // Hypixel Mod API
        exclusiveContent {
            forRepository {
                maven("https://repo.hypixel.net/repository/Hypixel")
            }
            filter {
                includeGroup("net.hypixel")
            }
        }

        // Modrinth
        exclusiveContent {
            forRepository {
                maven("https://api.modrinth.com/maven")
            }
            filter {
                includeGroup("maven.modrinth")
            }
        }

        // REI for compat plugin
        exclusiveContent {
            forRepository {
                maven("https://maven.shedaniel.me")
            }
            filter {
                includeGroup("dev.architectury")
                includeGroupAndSubgroups("me.shedaniel")
            }
        }

        exclusiveContent {
            forRepositories(
                repositories.maven("https://maven.azureaaron.net/releases"),
            )
            filter {
                includeGroupAndSubgroups("net.azureaaron")
            }
        }
    }
}

stonecutter active "26.2"

stonecutter handlers {
    configure("fsh", "vsh") {
        commenter = line("//")
    }
}

stonecutter parameters {
    replacements {
        string(current.parsed >= "26.3") {
            replace("setTooltipForNextFrame(Lnet/minecraft/client/gui/Font;Ljava/util/List;Ljava/util/Optional;IILnet/minecraft/resources/Identifier;)V", "setTooltipForNextFrame(Lnet/minecraft/client/gui/Font;Ljava/util/List;Ljava/util/Optional;IILnet/minecraft/resources/Identifier;Z)V")
            replace("Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;ILnet/minecraft/client/renderer/feature/ModelFeatureRenderer\$CrumblingOverlay;)V", "Lnet/minecraft/client/renderer/texture/UvMapping;I)V")
            replace("IIILnet/minecraft/client/renderer/feature/ModelFeatureRenderer\$CrumblingOverlay;)V", "III)V")
            replace("com/mojang/blaze3d/textures/GpuSampler", "com/mojang/renderpearl/api/textures/GpuSampler")
            replace("EnderMan", "Enderman")
            replace("entityTranslucentCullItemTarget", "entityTranslucentCull")
            replace("armorTranslucent", "entityTranslucent")
            replace("withBindGroupLayout(BindGroupLayouts.MATRICES_PROJECTION)", "withBindGroupLayout(BindGroupLayouts.DYNAMIC_TRANSFORMS).withBindGroupLayout(BindGroupLayouts.PROJECTION)")
            replace("packet.maxSpeed", "packet.xMaxSpeed")
            replace("packet.isOverrideLimiter", "packet.overrideLimiter")
            replace("Lnet/minecraft/world/level/block/entity/SignText;", "Lnet/minecraft/world/level/block/entity/SignText\$Mutable;")
            replace("import org.lwjgl.glfw.GLFW", "// Minecraft input constants replace GLFW on SDL.")
            replace("GLFW.GLFW_KEY_1", "com.mojang.blaze3d.platform.InputConstants.KEY_1")
            replace("GLFW.GLFW_KEY_2", "com.mojang.blaze3d.platform.InputConstants.KEY_2")
            replace("GLFW.GLFW_KEY_3", "com.mojang.blaze3d.platform.InputConstants.KEY_3")
            replace("GLFW.GLFW_KEY_4", "com.mojang.blaze3d.platform.InputConstants.KEY_4")
            replace("GLFW.GLFW_KEY_5", "com.mojang.blaze3d.platform.InputConstants.KEY_5")
            replace("GLFW.GLFW_KEY_6", "com.mojang.blaze3d.platform.InputConstants.KEY_6")
            replace("GLFW.GLFW_KEY_7", "com.mojang.blaze3d.platform.InputConstants.KEY_7")
            replace("GLFW.GLFW_KEY_8", "com.mojang.blaze3d.platform.InputConstants.KEY_8")
            replace("GLFW.GLFW_KEY_9", "com.mojang.blaze3d.platform.InputConstants.KEY_9")
            replace("GLFW.GLFW_KEY_A", "com.mojang.blaze3d.platform.InputConstants.KEY_A")
            replace("GLFW.GLFW_KEY_BACKSPACE", "com.mojang.blaze3d.platform.InputConstants.KEY_BACKSPACE")
            replace("GLFW.GLFW_KEY_C", "com.mojang.blaze3d.platform.InputConstants.KEY_C")
            replace("GLFW.GLFW_KEY_D", "com.mojang.blaze3d.platform.InputConstants.KEY_D")
            replace("GLFW.GLFW_KEY_DELETE", "com.mojang.blaze3d.platform.InputConstants.KEY_DELETE")
            replace("GLFW.GLFW_KEY_DOWN", "com.mojang.blaze3d.platform.InputConstants.KEY_DOWN")
            replace("GLFW.GLFW_KEY_ENTER", "com.mojang.blaze3d.platform.InputConstants.KEY_RETURN")
            replace("GLFW.GLFW_KEY_EQUAL", "com.mojang.blaze3d.platform.InputConstants.KEY_EQUALS")
            replace("GLFW.GLFW_KEY_ESCAPE", "com.mojang.blaze3d.platform.InputConstants.KEY_ESCAPE")
            replace("GLFW.GLFW_KEY_F", "com.mojang.blaze3d.platform.InputConstants.KEY_F")
            replace("GLFW.GLFW_KEY_G", "com.mojang.blaze3d.platform.InputConstants.KEY_G")
            replace("GLFW.GLFW_KEY_H", "com.mojang.blaze3d.platform.InputConstants.KEY_H")
            replace("GLFW.GLFW_KEY_I", "com.mojang.blaze3d.platform.InputConstants.KEY_I")
            replace("GLFW.GLFW_KEY_K", "com.mojang.blaze3d.platform.InputConstants.KEY_K")
            replace("GLFW.GLFW_KEY_KP_ADD", "com.mojang.blaze3d.platform.InputConstants.KEY_ADD")
            replace("GLFW.GLFW_KEY_KP_ENTER", "com.mojang.blaze3d.platform.InputConstants.KEY_NUMPADENTER")
            replace("GLFW.GLFW_KEY_KP_SUBTRACT", "org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_KP_MINUS")
            replace("GLFW.GLFW_KEY_LEFT", "com.mojang.blaze3d.platform.InputConstants.KEY_LEFT")
            replace("GLFW.GLFW_KEY_LEFT_ALT", "com.mojang.blaze3d.platform.InputConstants.KEY_LALT")
            replace("GLFW.GLFW_KEY_LEFT_CONTROL", "com.mojang.blaze3d.platform.InputConstants.KEY_LCONTROL")
            replace("GLFW.GLFW_KEY_LEFT_SHIFT", "com.mojang.blaze3d.platform.InputConstants.KEY_LSHIFT")
            replace("GLFW.GLFW_KEY_LEFT_SUPER", "com.mojang.blaze3d.platform.InputConstants.KEY_LGUI")
            replace("GLFW.GLFW_KEY_M", "com.mojang.blaze3d.platform.InputConstants.KEY_M")
            replace("GLFW.GLFW_KEY_MINUS", "com.mojang.blaze3d.platform.InputConstants.KEY_MINUS")
            replace("GLFW.GLFW_KEY_N", "com.mojang.blaze3d.platform.InputConstants.KEY_N")
            replace("GLFW.GLFW_KEY_O", "com.mojang.blaze3d.platform.InputConstants.KEY_O")
            replace("GLFW.GLFW_KEY_R", "com.mojang.blaze3d.platform.InputConstants.KEY_R")
            replace("GLFW.GLFW_KEY_RIGHT", "com.mojang.blaze3d.platform.InputConstants.KEY_RIGHT")
            replace("GLFW.GLFW_KEY_RIGHT_ALT", "com.mojang.blaze3d.platform.InputConstants.KEY_RALT")
            replace("GLFW.GLFW_KEY_RIGHT_CONTROL", "com.mojang.blaze3d.platform.InputConstants.KEY_RCONTROL")
            replace("GLFW.GLFW_KEY_RIGHT_SHIFT", "com.mojang.blaze3d.platform.InputConstants.KEY_RSHIFT")
            replace("GLFW.GLFW_KEY_RIGHT_SUPER", "com.mojang.blaze3d.platform.InputConstants.KEY_RGUI")
            replace("GLFW.GLFW_KEY_S", "com.mojang.blaze3d.platform.InputConstants.KEY_S")
            replace("GLFW.GLFW_KEY_SPACE", "com.mojang.blaze3d.platform.InputConstants.KEY_SPACE")
            replace("GLFW.GLFW_KEY_TAB", "com.mojang.blaze3d.platform.InputConstants.KEY_TAB")
            replace("GLFW.GLFW_KEY_UNKNOWN", "-1")
            replace("GLFW.GLFW_KEY_UP", "com.mojang.blaze3d.platform.InputConstants.KEY_UP")
            replace("GLFW.GLFW_KEY_V", "com.mojang.blaze3d.platform.InputConstants.KEY_V")
            replace("GLFW.GLFW_KEY_W", "com.mojang.blaze3d.platform.InputConstants.KEY_W")
            replace("GLFW.GLFW_KEY_X", "com.mojang.blaze3d.platform.InputConstants.KEY_X")
            replace("GLFW.GLFW_KEY_Y", "com.mojang.blaze3d.platform.InputConstants.KEY_Y")
            replace("GLFW.GLFW_KEY_Z", "com.mojang.blaze3d.platform.InputConstants.KEY_Z")
            replace("GLFW.GLFW_MOUSE_BUTTON_LEFT", "com.mojang.blaze3d.platform.InputConstants.MOUSE_BUTTON_LEFT")
            replace("GLFW.GLFW_MOUSE_BUTTON_MIDDLE", "com.mojang.blaze3d.platform.InputConstants.MOUSE_BUTTON_MIDDLE")
            replace("GLFW.GLFW_MOUSE_BUTTON_RIGHT", "com.mojang.blaze3d.platform.InputConstants.MOUSE_BUTTON_RIGHT")
            replace("com.mojang.blaze3d.buffers.GpuBuffer", "com.mojang.renderpearl.api.buffers.GpuBuffer")
            replace("com/mojang/blaze3d/buffers/GpuBuffer", "com/mojang/renderpearl/api/buffers/GpuBuffer")
            replace("com.mojang.blaze3d.buffers.GpuBufferSlice", "com.mojang.renderpearl.api.buffers.GpuBufferSlice")
            replace("com/mojang/blaze3d/buffers/GpuBufferSlice", "com/mojang/renderpearl/api/buffers/GpuBufferSlice")
            replace("com.mojang.blaze3d.GpuFormat", "com.mojang.renderpearl.api.GpuFormat")
            replace("com/mojang/blaze3d/GpuFormat", "com/mojang/renderpearl/api/GpuFormat")
            replace("com.mojang.blaze3d.IndexType", "com.mojang.renderpearl.api.pipeline.IndexType")
            replace("com/mojang/blaze3d/IndexType", "com/mojang/renderpearl/api/pipeline/IndexType")
            replace("com.mojang.blaze3d.pipeline.BindGroupLayout", "com.mojang.renderpearl.api.pipeline.BindGroupLayout")
            replace("com/mojang/blaze3d/pipeline/BindGroupLayout", "com/mojang/renderpearl/api/pipeline/BindGroupLayout")
            replace("com.mojang.blaze3d.pipeline.BlendFunction", "com.mojang.renderpearl.api.pipeline.BlendFunction")
            replace("com/mojang/blaze3d/pipeline/BlendFunction", "com/mojang/renderpearl/api/pipeline/BlendFunction")
            replace("com.mojang.blaze3d.pipeline.ColorTargetState", "com.mojang.renderpearl.api.pipeline.ColorTargetState")
            replace("com/mojang/blaze3d/pipeline/ColorTargetState", "com/mojang/renderpearl/api/pipeline/ColorTargetState")
            replace("com.mojang.blaze3d.pipeline.DepthStencilState", "com.mojang.renderpearl.api.pipeline.DepthStencilState")
            replace("com/mojang/blaze3d/pipeline/DepthStencilState", "com/mojang/renderpearl/api/pipeline/DepthStencilState")
            replace("com.mojang.blaze3d.pipeline.RenderPipeline", "com.mojang.renderpearl.api.pipeline.RenderPipeline")
            replace("com/mojang/blaze3d/pipeline/RenderPipeline", "com/mojang/renderpearl/api/pipeline/RenderPipeline")
            replace("com.mojang.blaze3d.platform.CompareOp", "com.mojang.renderpearl.api.pipeline.CompareOp")
            replace("com/mojang/blaze3d/platform/CompareOp", "com/mojang/renderpearl/api/pipeline/CompareOp")
            replace("com.mojang.blaze3d.PrimitiveTopology", "com.mojang.renderpearl.api.pipeline.PrimitiveTopology")
            replace("com/mojang/blaze3d/PrimitiveTopology", "com/mojang/renderpearl/api/pipeline/PrimitiveTopology")
            replace("com.mojang.blaze3d.shaders.UniformType", "com.mojang.renderpearl.api.pipeline.UniformType")
            replace("com/mojang/blaze3d/shaders/UniformType", "com/mojang/renderpearl/api/pipeline/UniformType")
            replace("com.mojang.blaze3d.systems.GpuDevice", "com.mojang.renderpearl.api.device.GpuDevice")
            replace("com/mojang/blaze3d/systems/GpuDevice", "com/mojang/renderpearl/api/device/GpuDevice")
            replace("com.mojang.blaze3d.systems.RenderPass", "com.mojang.renderpearl.api.commands.RenderPass")
            replace("com/mojang/blaze3d/systems/RenderPass", "com/mojang/renderpearl/api/commands/RenderPass")
            replace("com.mojang.blaze3d.textures.FilterMode", "com.mojang.renderpearl.api.textures.FilterMode")
            replace("com/mojang/blaze3d/textures/FilterMode", "com/mojang/renderpearl/api/textures/FilterMode")
            replace("com.mojang.blaze3d.textures.GpuTexture", "com.mojang.renderpearl.api.textures.GpuTexture")
            replace("com/mojang/blaze3d/textures/GpuTexture", "com/mojang/renderpearl/api/textures/GpuTexture")
            replace("com.mojang.blaze3d.textures.GpuTextureView", "com.mojang.renderpearl.api.textures.GpuTextureView")
            replace("com/mojang/blaze3d/textures/GpuTextureView", "com/mojang/renderpearl/api/textures/GpuTextureView")
            replace("com.mojang.blaze3d.vertex.VertexFormat", "com.mojang.renderpearl.api.vertex.VertexFormat")
            replace("com/mojang/blaze3d/vertex/VertexFormat", "com/mojang/renderpearl/api/vertex/VertexFormat")
            replace("com.mojang.blaze3d.vertex.VertexFormatElement", "com.mojang.renderpearl.api.vertex.VertexFormatElement")
            replace("com/mojang/blaze3d/vertex/VertexFormatElement", "com/mojang/renderpearl/api/vertex/VertexFormatElement")
        }
        string(current.parsed < "26.2") {
            replace("net.minecraft.world.entity.monster.cubemob.MagmaCube", "net.minecraft.world.entity.monster.MagmaCube")
            replace("net.minecraft.world.entity.monster.cubemob.Slime", "net.minecraft.world.entity.monster.Slime")

            val dyeColors = mapOf(
                "black" to "BLACK",
                "blue" to "BLUE",
                "brown" to "BROWN",
                "cyan" to "CYAN",
                "gray" to "GRAY",
                "green" to "GREEN",
                "lightBlue" to "LIGHT_BLUE",
                "lightGray" to "LIGHT_GRAY",
                "lime" to "LIME",
                "magenta" to "MAGENTA",
                "orange" to "ORANGE",
                "pink" to "PINK",
                "purple" to "PURPLE",
                "red" to "RED",
                "white" to "WHITE",
                "yellow" to "YELLOW",
            )
            dyeColors.forEach { (lower, upper) ->
                replace("DYE.$lower()", "${upper}_DYE")
                replace("WOOL.$lower()", "${upper}_WOOL")
                replace("STAINED_GLASS.$lower()", "${upper}_STAINED_GLASS")
                replace("STAINED_GLASS_PANE.$lower()", "${upper}_STAINED_GLASS_PANE")
                replace("DYED_TERRACOTTA.$lower()", "${upper}_TERRACOTTA")
            }
        }
    }

    filters.include("**/*.fsh", "**/*.vsh")
}
