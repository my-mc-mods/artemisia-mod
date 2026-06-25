package dev.aika.artemisia.neoforge;

import dev.aika.artemisia.ArtemisiaCommon;
import dev.aika.artemisia.Constants;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class ArtemisiaNeoForge {
    public ArtemisiaNeoForge(IEventBus ignoredModBus) {
        ArtemisiaCommon.init();
    }
}