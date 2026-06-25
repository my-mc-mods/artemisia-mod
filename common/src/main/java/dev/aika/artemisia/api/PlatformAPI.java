package dev.aika.artemisia.api;

import dev.architectury.injectables.annotations.ExpectPlatform;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.minecraft.DetectedVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.apache.maven.artifact.versioning.VersionRange;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

@SuppressWarnings("unused")
@UtilityClass
public final class PlatformAPI {
    @ExpectPlatform
    public @NotNull String getCurrentTarget() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public boolean isModLoaded(String modId) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public boolean isDevelopmentEnvironment() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public @NotNull Path getConfigDir() {
        throw new AssertionError();
    }

    public Environment getEnvironment() {
        return isDevelopmentEnvironment() ? Environment.DEV : Environment.PROD;
    }

    @SneakyThrows
    public boolean isContainsVersion(String versionRange, String version) {
        return VersionRange.createFromVersionSpec(versionRange).containsVersion(new DefaultArtifactVersion(version));
    }

    public String getMinecraftVersion() {
        return DetectedVersion.tryDetectVersion().name();
    }
}