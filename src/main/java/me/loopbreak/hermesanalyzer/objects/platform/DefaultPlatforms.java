package me.loopbreak.hermesanalyzer.objects.platform;

import me.loopbreak.hermesanalyzer.objects.platform.providers.*;

import java.util.function.Supplier;

public enum DefaultPlatforms {
    OLLAMA(OllamaProvider::new),
    OPENAI(OpenAIProvider::new),
    MISTRAL(MistralProvider::new),
    HUGGINGFACE(HuggingFaceProvider::new),
    REPLICATE(ReplicateProvider::new),
    VERTEXAI(VertexAIProvider::new),
    DEFAULT(DefaultProvider::new);

    private final Supplier<Platform> platformSupplier;

    DefaultPlatforms(Supplier<Platform> platformSupplier) {
        this.platformSupplier = platformSupplier;
    }

    public Platform getPlatform() {
        return platformSupplier.get();
    }
}
