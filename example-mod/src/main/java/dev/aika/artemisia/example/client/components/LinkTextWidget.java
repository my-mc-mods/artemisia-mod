package dev.aika.artemisia.example.client.components;

import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import org.jspecify.annotations.NonNull;

import java.net.URI;

@SuppressWarnings("unused")
@Accessors(chain = true)
public class LinkTextWidget extends StringWidget {
    @Setter
    private OnPress onPress;
    @SuppressWarnings("FieldCanBeLocal")
    @Setter
    private int outlinePadding = 3;

    public LinkTextWidget(Component message, Font font) {
        super(
                message.copy().withStyle(style -> style.withUnderlined(true)),
                font
        );
        this.active = true;
    }

    public LinkTextWidget(Component message, Font font, int color) {
        super(
                message.copy().withStyle(style -> style.withUnderlined(true).withColor(color)),
                font
        );
        this.active = true;
    }

    @Override
    public void extractWidgetRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractWidgetRenderState(graphics, mouseX, mouseY, a);
        handleCursor(graphics);

        if (isFocused()) {
            graphics.outline(getX() - outlinePadding, getY() - outlinePadding,
                    getWidth() + outlinePadding * 2, getHeight() + outlinePadding * 2,
                    CommonColors.WHITE);
        }
    }

    public interface OnPress {
        void onPress();
    }

    @Override
    public void onClick(@NonNull MouseButtonEvent event, boolean doubleClick) {
        if (this.onPress != null) this.onPress.onPress();
    }

    public static OnPress confirmLink(Screen parentScreen, URI uri, boolean trusted) {
        return () -> ConfirmLinkScreen.confirmLinkNow(parentScreen, uri, trusted);
    }

    public static OnPress confirmLink(Screen parentScreen, URI uri) {
        return confirmLink(parentScreen, uri, true);
    }

    @Override
    public boolean keyPressed(@NonNull KeyEvent event) {
        if (!this.isActive()) return false;
        else if (event.isSelection()) {
            this.playDownSound(Minecraft.getInstance().getSoundManager());
            this.onPress.onPress();
            return true;
        }
        return false;
    }
}