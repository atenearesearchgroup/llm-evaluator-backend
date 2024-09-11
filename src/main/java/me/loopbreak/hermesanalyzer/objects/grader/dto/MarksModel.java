package me.loopbreak.hermesanalyzer.objects.grader.dto;

import java.util.List;

public class MarksModel {
    private String eClass;
    private String _id;
    private List<ModelElementCategory> modelElementCategories;
    private List<MarksMapEntry> marksMap;
    private double maxPoints;

//    public MarksCalculator getCalculator() {
//        return MarksCalculator.of(this);
//    }
//
//    public ErrorClassifier getErrorClassifier() {
//        return ErrorClassifier.of(this);
//    }

    // Getters and Setters
    public String geteClass() {
        return eClass;
    }

    public void seteClass(String eClass) {
        this.eClass = eClass;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public List<ModelElementCategory> getModelElementCategories() {
        return modelElementCategories;
    }

    public void setModelElementCategories(List<ModelElementCategory> modelElementCategories) {
        this.modelElementCategories = modelElementCategories;
    }

    public List<MarksMapEntry> getMarksMap() {
        return marksMap;
    }

    public void setMarksMap(List<MarksMapEntry> marksMap) {
        this.marksMap = marksMap;
    }

    public double getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(double maxPoints) {
        this.maxPoints = maxPoints;
    }
}
