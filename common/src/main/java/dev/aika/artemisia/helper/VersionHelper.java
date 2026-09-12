package dev.aika.artemisia.helper;

import org.jetbrains.annotations.NotNull;
import org.semver4j.Semver;

import java.util.Objects;

public class VersionHelper {
    private static final VersionHelper INSTANCE = new VersionHelper();

    private VersionHelper() {
    }

    public static VersionHelper get() {
        return INSTANCE;
    }

    public boolean satisfies(@NotNull String version, @NotNull String versionRange) {
        return Objects.requireNonNull(Semver.coerce(version)).satisfies(versionRange);
    }
}