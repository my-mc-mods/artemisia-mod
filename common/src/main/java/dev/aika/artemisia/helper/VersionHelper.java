package dev.aika.artemisia.helper;

import org.jetbrains.annotations.NotNull;
import org.semver4j.Semver;

public class VersionHelper  {
    private static final VersionHelper INSTANCE = new VersionHelper();

    private VersionHelper() {
    }

    public static VersionHelper get() {
        return INSTANCE;
    }

    public boolean satisfies(@NotNull String version, @NotNull String versionRange) {
        return new Semver(version).satisfies(versionRange);
    }
}