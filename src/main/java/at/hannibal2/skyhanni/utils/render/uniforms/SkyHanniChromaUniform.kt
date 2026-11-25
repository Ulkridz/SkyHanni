package at.hannibal2.skyhanni.utils.render.uniforms

import com.mojang.blaze3d.buffers.GpuBufferSlice
import com.mojang.blaze3d.buffers.Std140Builder
import com.mojang.blaze3d.buffers.Std140SizeCalculator
//? if >= 26.3 {
/*import com.mojang.renderpearl.api.buffers.GpuBuffer
import net.minecraft.client.renderer.DynamicGpuDataStorage
import net.minecraft.client.renderer.DynamicGpuDataStorageMapped
*///?} else {
import net.minecraft.client.renderer.DynamicUniformStorage
//?}
import java.nio.ByteBuffer

class SkyHanniChromaUniform : AutoCloseable {
    private val uniformSize = Std140SizeCalculator().putFloat().putFloat().putFloat().putInt().get()

    //? if >= 26.3 {
    /*val storage = DynamicGpuDataStorageMapped<UniformValue>("SkyHanni Chroma UBO", uniformSize, GpuBuffer.USAGE_UNIFORM, 2)
    *///?} else {
    val storage = DynamicUniformStorage<UniformValue>("SkyHanni Chroma UBO", uniformSize, 2)
    //?}

    fun writeWith(
        chromaSize: Float,
        timeOffset: Float,
        saturation: Float,
        forwardDirection: Int,
    ): GpuBufferSlice {
        //~ if >= 26.3 'writeUniform' -> 'writeData'
        return storage.writeUniform(
            UniformValue(chromaSize, timeOffset, saturation, forwardDirection),
        )
    }

    // Imperative to clear DynamicUniformStorage every frame.
    // Handled in MixinRenderSystem.
    fun clear() {
        storage.endFrame()
    }

    override fun close() {
        storage.close()
    }

    data class UniformValue(
        val chromaSize: Float,
        val timeOffset: Float,
        val saturation: Float,
        val forwardDirection: Int,
    //~ if >= 26.3 'DynamicUniformStorage.DynamicUniform' -> 'DynamicGpuDataStorage.DynamicGpuData'
    ) : DynamicUniformStorage.DynamicUniform {
        override fun write(buffer: ByteBuffer) {
            Std140Builder.intoBuffer(buffer)
                .putFloat(chromaSize)
                .putFloat(timeOffset)
                .putFloat(saturation)
                .putInt(forwardDirection)
        }
    }
}
