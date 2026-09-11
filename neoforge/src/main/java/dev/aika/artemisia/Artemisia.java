package dev.aika.artemisia;

import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public final class Artemisia {
    public Artemisia() {
        ArtemisiaCommon.init();
    }
}