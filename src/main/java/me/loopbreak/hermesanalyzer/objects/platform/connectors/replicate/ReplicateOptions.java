package me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate;

public class ReplicateOptions {
    private boolean official = false;
    private String modelName = null;
    private String modelOwner = null;
    private String version = null;


    public boolean official() {
        return official;
    }

    public void setOfficial(boolean official) {
        this.official = official;
    }

    public String modelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String modelOwner() {
        return modelOwner;
    }

    public void setModelOwner(String modelOwner) {
        this.modelOwner = modelOwner;
    }

    public String version() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
