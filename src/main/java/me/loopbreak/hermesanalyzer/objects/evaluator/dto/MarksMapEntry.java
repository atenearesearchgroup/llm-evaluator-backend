package me.loopbreak.hermesanalyzer.objects.evaluator.dto;

public class MarksMapEntry {
    private String _id;
    private String key;
    private MarkValue value;

    // Getters and Setters
    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MarkValue getValue() {
        return value;
    }

    public void setValue(MarkValue value) {
        this.value = value;
    }
}
