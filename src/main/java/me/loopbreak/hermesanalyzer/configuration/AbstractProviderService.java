package me.loopbreak.hermesanalyzer.configuration;

public class AbstractProviderService<T> {

    private T options;

    public AbstractProviderService(T options) {
        this.options = options;
    }

    public T getOptions() {
        return options;
    }

}
