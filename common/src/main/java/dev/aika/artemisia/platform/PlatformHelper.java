package dev.aika.artemisia.platform;

import dev.aika.artemisia.helper.ServiceHelper;

public class PlatformHelper {
    private static final IPlatformHelper INSTANCE = ServiceHelper.load(IPlatformHelper.class);

    public static IPlatformHelper get() {
        return INSTANCE;
    }
}