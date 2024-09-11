package me.loopbreak.hermesanalyzer.objects.grader.dto;

public class MissedModelElement {
    private String _id;
    private String missedElementName;
    private CompensationMark compensationMark;

    // Getters and Setters
    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getMissedElementName() {
        return missedElementName;
    }

    public void setMissedElementName(String missedElementName) {
        this.missedElementName = missedElementName;
    }

    public CompensationMark getCompensationMark() {
        return compensationMark;
    }

    public void setCompensationMark(CompensationMark compensationMark) {
        this.compensationMark = compensationMark;
    }
}


