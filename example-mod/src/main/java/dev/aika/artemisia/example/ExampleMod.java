package dev.aika.artemisia.example;

import dev.aika.artemisia.platform.PlatformHelper;
import dev.aika.artemisia.shadowed.taffy.style.*;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(ExampleMod.MOD_ID)
public final class ExampleMod {
    public static final String MOD_ID = "examplemod";
    public static final String MOD_NAME = "ExampleMod";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public ExampleMod() {
        LOG.info("mod {} is loaded: {}", MOD_ID, PlatformHelper.get().isModLoaded(MOD_ID));
        LOG.info("isDevelopmentEnvironment: {}", PlatformHelper.get().isDevelopmentEnvironment());
    }
}