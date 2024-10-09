package me.loopbreak.hermesanalyzer.objects.platform;

import me.loopbreak.hermesanalyzer.exceptions.PlatformNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlatformProvider {

    private final Map<String, Platform> registry = new HashMap<>();

    @Autowired
    public PlatformProvider(List<Platform> platforms) {
        loadDefaults(platforms);
    }

    private void loadDefaults(List<Platform> platforms) {
        registry.clear();

        for (Platform platform : platforms) {
            try {
                registry.put(platform.getName().toLowerCase(), platform);
            } catch (Exception exception) {
                System.out.println("Failed to register default platform " + platform.getName());
                exception.printStackTrace();
            }
        }
    }


    public void registerPlatform(String name, Platform platform) {
        name = name.toLowerCase();

        if (registry.containsKey(name)) {
            throw new IllegalArgumentException("Platform with name " + name + " already exists");
        }

        registry.put(name, platform);
    }

    public Platform getProvider(String identifier) {
        String lowerCase = identifier.toLowerCase();
        if (!registry.containsKey(lowerCase)) {
            throw new PlatformNotFoundException(identifier);
        }

        return registry.get(lowerCase);
    }

    public Platform getProvider(DefaultPlatforms platform) {
        return getProvider(platform.name().toLowerCase());
    }

    public <T extends Platform> T getProvider(@Nonnull Class<T> clazz) {
        for (Platform platform : registry.values()) {
            if (clazz.isInstance(platform)) {
                return clazz.cast(platform);
            }
        }

        throw new PlatformNotFoundException(clazz.getSimpleName());
    }

}
