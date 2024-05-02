package me.loopbreak.hermesanalyzer.services.configuration;

public abstract class AbstractProviderService<T, V> {

    private T options;

    protected AbstractProviderService(T options) {
        this.options = options;
    }

    abstract public V getApi();

    public T getOptions() {
        return options;
    }


}
