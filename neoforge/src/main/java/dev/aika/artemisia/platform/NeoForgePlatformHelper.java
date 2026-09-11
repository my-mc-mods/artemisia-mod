package dev.aika.artemisia.platform;

import dev.aika.artemisia.mod.EnvType;
import dev.aika.artemisia.mod.ModPlatform;
import lombok.NonNull;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class NeoForgePlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return ModPlatform.NEOFORGE;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.getCurrent().isProduction();
    }

    @Override
    public @NotNull Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public @NonNull EnvType getEnvType() {
        return FMLLoader.getCurrent().getDist().isClient() ? EnvType.CLIENT : EnvType.DEDICATED_SERVER;
    }
}