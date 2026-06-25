package dev.aika.artemisia.api.fabric;

import dev.aika.artemisia.config.ModPlatform;
import lombok.experimental.UtilityClass;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

@SuppressWarnings("unused")
@UtilityClass
public final class PlatformAPIImpl {
    public @NotNull String getCurrentTarget() {
        return ModPlatform.FABRIC;
    }

    public boolean isModLoaded(@NotNull String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    public @NotNull Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir();
    }
}