package dev.aika.artemisia.fabric;

import dev.aika.artemisia.ArtemisiaCommon;
import net.fabricmc.api.ModInitializer;

public class ArtemisiaFabric implements ModInitializer {
    @Override public void onInitialize() {
        ArtemisiaCommon.init();
    }
}