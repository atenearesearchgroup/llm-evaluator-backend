package me.loopbreak.hermesanalyzer.objects.draft;

import me.loopbreak.hermesanalyzer.objects.models.Model;

import java.util.List;

public class EvaluationInstance {
    private String id;
    private String intentId;
    private String systemPrompt;
    private List<Draft> drafts;
    private Model model;
    private EvaluationSettings evaluationSettings;

    public EvaluationInstance(String id, String intentId, List<Draft> drafts, Model model, EvaluationSettings evaluationSettings) {
        this.id = id;
        this.intentId = intentId;
        this.systemPrompt = model.getModelSettings().getSystemPrompt();
        this.drafts = drafts;
        this.model = model;
        this.evaluationSettings = evaluationSettings;
    }

    public String getId() {
        return id;
    }

    public String getIntentId() {
        return intentId;
    }

    public String getSystemPrompt() {
        return systemPrompt;
    }

    public List<Draft> getDrafts() {
        return drafts;
    }

    public Draft startDraft() {
        Draft draft = new Draft(drafts.size());
        drafts.add(draft);
        return draft;
    }

    public Model getModel() {
        return model;
    }

    public EvaluationSettings getEvaluationSettings() {
        return evaluationSettings;
    }
}
