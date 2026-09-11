package dev.aika.artemisia.client.screen;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

@SuppressWarnings("unused")
public class EasyScreen extends Screen {
    private final Screen lastScreen;

    protected EasyScreen(Screen lastScreen, Component title) {
        super(title);
        this.lastScreen = lastScreen;
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(lastScreen);
    }

    protected void onClose(Button button) {
        this.onClose();
    }
}