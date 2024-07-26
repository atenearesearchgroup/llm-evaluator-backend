package me.loopbreak.hermesanalyzer.objects.evaluator.dto;

import java.util.List;

public class MarkValue {
    private String _id;
    private double points;
    private String comment;
    private String modelElementCategory;
    private List<FeatureMark> featureMarks;

    public double getPointsSum() {
        return points + (featureMarks == null ? 0 : featureMarks.stream().mapToDouble(feature -> feature.getMark().getPoints()).sum());
    }

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

    public List<FeatureMark> getFeatureMarks() {
        return featureMarks;
    }

    public void setFeatureMarks(List<FeatureMark> featureMarks) {
        this.featureMarks = featureMarks;
    }
}
