package dev.aika.artemisia.example.client.components;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.FittingMultiLineTextWidget;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.util.CommonColors;
import org.jspecify.annotations.NonNull;
import org.lwjgl.glfw.GLFW;

public class UiMultiLineTextWidget extends FittingMultiLineTextWidget {
    public UiMultiLineTextWidget(Component message, Font font) {
        super(0, 0, 0, 0, message, font);
    }

    @Override
    public void extractWidgetRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractWidgetRenderState(graphics, mouseX, mouseY, a);
    }

    @Override
    protected void extractScrollbar(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        super.extractScrollbar(graphics, mouseX, mouseY);
        final int x = scrollBarX();
        final int y = getY();
        final int width = scrollbarWidth();
        final int height = getHeight();
        final int color = isFocused() ? CommonColors.WHITE : ARGB.color(204, CommonColors.TEXT_GRAY);

        graphics.fill(x, y, x + width, y + 1, color);
        graphics.fill(x, y + height - 1, x + width, y + height, color);
        graphics.fill(x + width - 1, y + 1, x + width, y + height - 1, color);
    }

    @Override
    public boolean keyPressed(@NonNull KeyEvent event) {
        return switch (event.input()) {
            case GLFW.GLFW_KEY_HOME -> {
                this.setScrollAmount(0);
                yield true;
            }
            case GLFW.GLFW_KEY_END -> {
                this.setScrollAmount(this.maxScrollAmount());
                yield true;
            }
            default -> super.keyPressed(event);
        };
    }
}