package me.loopbreak.hermesanalyzer.objects.platform;

import me.loopbreak.hermesanalyzer.exceptions.PlatformNotFoundException;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class PlatformProvider {

    private static Map<String, Platform> registry = getDefaults();

    private static Map<String, Platform> getDefaults() {
        Map<String, Platform> defaults = new HashMap<>();

        for (DefaultPlatforms value : DefaultPlatforms.values()) {
            defaults.put(value.name().toLowerCase(), value.getPlatform());
        }

        return defaults;
    }

    public static void registerPlatform(String name, Platform platform) {
        name = name.toLowerCase();

        if (registry.containsKey(name)) {
            throw new IllegalArgumentException("Platform with name " + name + " already exists");
        }

        registry.put(name, platform);
    }

    public static Platform getProvider(String identifier) {
        String lowerCase = identifier.toLowerCase();
        if (!registry.containsKey(lowerCase)) {
            throw new PlatformNotFoundException(identifier);
        }

        return registry.get(lowerCase);
    }

    public static Platform getProvider(DefaultPlatforms platform) {
        return getProvider(platform.name().toLowerCase());
    }

    public static <T extends Platform> T getProvider(@Nonnull Class<T> clazz) {
        for (Platform platform : registry.values()) {
            if (clazz.isInstance(platform)) {
                return clazz.cast(platform);
            }
        }

        throw new PlatformNotFoundException(clazz.getSimpleName());
    }

}
