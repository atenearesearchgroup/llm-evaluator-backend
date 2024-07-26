package me.loopbreak.hermesanalyzer.objects.evaluator.dto;

public class Mark {
    private String _id;
    private double points;
    private String comment;
    private String modelElementCategory;
    private String modelElementName;

    // Getters and Setters
    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getModelElementCategory() {
        return modelElementCategory;
    }

    public void setModelElementCategory(String modelElementCategory) {
        this.modelElementCategory = modelElementCategory;
    }

    public String getModelElementName() {
        return modelElementName;
    }

    public void setModelElementName(String modelElementName) {
        this.modelElementName = modelElementName;
    }
}
