package at.hannibal2.skyhanni.mixins.transformers;

//? if >= 26.2 {
import at.hannibal2.skyhanni.mixins.hooks.GuiRendererHook;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.IndexType;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.systems.RenderPass;
import net.minecraft.client.renderer.rendertype.PreparedRenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PreparedRenderType.class)
public abstract class MixinPreparedRenderType {
    @Inject(
        //~ if >= 26.3 'drawFromBuffer(Lcom/mojang/blaze3d/buffers/GpuBuffer;Lcom/mojang/blaze3d/buffers/GpuBuffer;Lcom/mojang/blaze3d/IndexType;III)V' -> 'draw'
        method = "drawFromBuffer(Lcom/mojang/blaze3d/buffers/GpuBuffer;Lcom/mojang/blaze3d/buffers/GpuBuffer;Lcom/mojang/blaze3d/IndexType;III)V",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/blaze3d/systems/RenderPass;setUniform(Ljava/lang/String;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;)V",
            shift = At.Shift.AFTER
        )
    )
    private void bindSkyHanniChromaUniform(
        //? if >= 26.3 {
        /*net.minecraft.client.renderer.StagedVertexBuffer.ExecuteInfo executeInfo,
        RenderPass renderPass,
        com.mojang.renderpearl.api.pipeline.RenderPipeline pipeline,
        CallbackInfo ci
        *///?} else {
        GpuBuffer vertexBuffer,
        GpuBuffer indexBuffer,
        IndexType indexType,
        int baseVertex,
        int firstIndex,
        int indexCount,
        CallbackInfo ci,
        @Local RenderPass renderPass
        //?}
    ) {
        PreparedRenderType renderType = (PreparedRenderType) (Object) this;
        GuiRendererHook.INSTANCE.insertChromaSetUniform(renderPass, renderType.pipeline());
    }
}
//?}
