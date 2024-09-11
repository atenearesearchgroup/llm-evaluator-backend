package me.loopbreak.hermesanalyzer.objects.grader.dto;

public class FeatureMark {
    private String _id;
    private Mark mark;
    private String featureName;
    private String referencedObjectName;

    // Getters and Setters
    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getReferencedObjectName() {
        return referencedObjectName;
    }

    public void setReferencedObjectName(String referencedObjectName) {
        this.referencedObjectName = referencedObjectName;
    }
}
