package at.hannibal2.skyhanni.config

import at.hannibal2.skyhanni.utils.ConfigUtils.asStructuredText
import at.hannibal2.skyhanni.utils.KeyboardManager
import at.hannibal2.skyhanni.utils.compat.InputCompat
import com.mojang.blaze3d.platform.InputConstants
import io.github.notenoughupdates.moulconfig.gui.GuiComponent
import io.github.notenoughupdates.moulconfig.gui.GuiImmediateContext
import io.github.notenoughupdates.moulconfig.gui.KeyboardEvent
import io.github.notenoughupdates.moulconfig.gui.MouseEvent
import io.github.notenoughupdates.moulconfig.gui.editors.GuiOptionEditorKeybind
import io.github.notenoughupdates.moulconfig.processor.ProcessedOption

/** Distinguishes SDL keyboard scancodes from mouse buttons in saved bindings. */
class GuiOptionEditorInputBinding(option: ProcessedOption, defaultKey: Int) : GuiOptionEditorKeybind(option, defaultKey) {
    private var editing = false
    private val bindingComponent = wrapComponent(object : GuiComponent() {
        override fun getWidth() = 120
        override fun getHeight() = 24

        override fun render(context: GuiImmediateContext) {
            val renderer = context.renderContext
            val font = renderer.minecraft.defaultFontRenderer
            renderer.drawColoredRect(0f, 2f, 78f, 22f, 0xff333333.toInt())
            renderer.drawColoredRect(82f, 2f, 120f, 22f, 0xff333333.toInt())
            val label = if (editing) "Press a key..." else KeyboardManager.getKeyName(option.get() as Int)
            renderer.drawStringCenteredScaledMaxWidth(label.asStructuredText(), font, 39f, 8f, true, 74, -1)
            renderer.drawString(font, "Reset".asStructuredText(), 86, 8, -1, true)
        }

        override fun mouseEvent(event: MouseEvent, context: GuiImmediateContext): Boolean {
            val click = event as? MouseEvent.Click ?: return false
            if (!click.mouseState) return false
            if (editing) {
                option.set(InputCompat.mouseBinding(click.mouseButton))
                editing = false
                return true
            }
            if (click.mouseButton != InputConstants.MOUSE_BUTTON_LEFT || !context.isHovered) return false
            if (context.mouseX >= 82) option.set(defaultKey) else editing = true
            return true
        }

        override fun keyboardEvent(event: KeyboardEvent, context: GuiImmediateContext): Boolean {
            val key = event as? KeyboardEvent.KeyPressed ?: return false
            if (!editing || !key.pressed) return false
            option.set(if (key.keycode == InputConstants.KEY_ESCAPE) -1 else key.keycode)
            editing = false
            return true
        }

        override fun onLostFocus() {
            editing = false
        }
    })

    override fun getDelegate(): GuiComponent = bindingComponent
}
