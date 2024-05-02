package me.loopbreak.hermesanalyzer.entity.messages;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class PromptIterationEntity {

    @Id
    private Long id;

    private String type;
    private int iteration;

    @OneToMany(mappedBy = "promptIteration")
    private List<MessageEntity> message;

    public PromptIterationEntity() {
    }

    public PromptIterationEntity(String type, int iteration) {
        this.type = type;
        this.iteration = iteration;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getIteration() {
        return iteration;
    }
}
