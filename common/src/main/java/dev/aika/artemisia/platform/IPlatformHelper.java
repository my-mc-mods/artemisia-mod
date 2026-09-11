package dev.aika.artemisia.platform;

import dev.aika.artemisia.mod.EnvType;
import net.minecraft.DetectedVersion;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public interface IPlatformHelper {
    String getPlatformName();

    boolean isModLoaded(String modId);

    boolean isDevelopmentEnvironment();

    @NotNull Path getConfigDir();

    @NotNull EnvType getEnvType();

    default String getMinecraftVersion() {
        return DetectedVersion.tryDetectVersion().name();
    }
}