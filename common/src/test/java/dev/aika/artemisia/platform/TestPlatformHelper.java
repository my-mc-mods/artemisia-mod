package dev.aika.artemisia.platform;

import dev.aika.artemisia.mod.EnvType;
import dev.aika.artemisia.mod.ModPlatform;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class TestPlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return ModPlatform.FABRIC;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return false;
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return true;
    }

    @Override
    public @NotNull Path getConfigDir() {
        return Path.of("/dev/null");
    }

    @Override
    public @NotNull EnvType getEnvType() {
        return EnvType.CLIENT;
    }

    @Override
    public @NotNull String getMinecraftVersion() {
        return "26.1.2";
    }
}