package me.loopbreak.hermesanalyzer.objects.evaluator.dto;

import java.util.ArrayList;
import java.util.List;

public class ModelElementCategory {
    private String _id;
    private String name;
    private List<String> marks = new ArrayList<>();
    private List<MissedModelElement> missedModelElements = new ArrayList<>();
    private double maxPoints;

    // Getters and Setters
    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMarks() {
        return marks;
    }

    public void setMarks(List<String> marks) {
        this.marks = marks;
    }

    public List<MissedModelElement> getMissedModelElements() {
        return missedModelElements;
    }

    public void setMissedModelElements(List<MissedModelElement> missedModelElements) {
        this.missedModelElements = missedModelElements;
    }

    public double getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(double maxPoints) {
        this.maxPoints = maxPoints;
    }
}
