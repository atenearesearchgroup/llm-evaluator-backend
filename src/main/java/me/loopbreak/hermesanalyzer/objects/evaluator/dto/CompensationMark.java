package me.loopbreak.hermesanalyzer.objects.evaluator.dto;

public class CompensationMark {
    private String _id;
    private double points;
    private String comment;

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
}
