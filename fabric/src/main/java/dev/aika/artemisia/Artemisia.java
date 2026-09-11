package dev.aika.artemisia;

import net.fabricmc.api.ModInitializer;

public class Artemisia implements ModInitializer {
    @Override
    public void onInitialize() {
        ArtemisiaCommon.init();
    }
}