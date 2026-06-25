package dev.aika.artemisia.api.neoforge;

import dev.aika.artemisia.config.ModPlatform;
import lombok.experimental.UtilityClass;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

@SuppressWarnings("unused")
@UtilityClass
public final class PlatformAPIImpl {
    public @NotNull String getCurrentTarget() {
        return ModPlatform.NEOFORGE;
    }

    public boolean isModLoaded(@NotNull String modId) {
        return FMLLoader.getCurrent().getLoadingModList().getModFileById(modId) != null;
    }

    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.getCurrent().isProduction();
    }

    public @NotNull Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }
}