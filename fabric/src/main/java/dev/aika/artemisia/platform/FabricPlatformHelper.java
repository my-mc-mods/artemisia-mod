package dev.aika.artemisia.platform;

import dev.aika.artemisia.mod.EnvType;
import dev.aika.artemisia.mod.ModPlatform;
import lombok.NonNull;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return ModPlatform.FABRIC;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public @NotNull Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public @NonNull EnvType getEnvType() {
        return FabricLoader.getInstance().getEnvironmentType() == net.fabricmc.api.EnvType.CLIENT
                ? EnvType.CLIENT
                : EnvType.DEDICATED_SERVER;
    }
}