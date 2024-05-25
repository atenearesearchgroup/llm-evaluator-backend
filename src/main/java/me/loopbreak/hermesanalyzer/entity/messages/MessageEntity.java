package me.loopbreak.hermesanalyzer.entity.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "message")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "message_type",
        discriminatorType = DiscriminatorType.STRING)
public abstract class MessageEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp timestamp;
    private String content;

    @ManyToOne
//    @JoinColumns({
//            @JoinColumn(name = "prompt_iteration_id", referencedColumnName = "id"),
//            @JoinColumn(name = "prompt_iteration_iteration", referencedColumnName = "iteration")
//    })
    @JsonIgnoreProperties("messages")
    @JoinColumn(name = "prompt_iteration_id")
    private PromptIterationEntity promptIteration;

    public MessageEntity() {
    }

    public MessageEntity(String content, Timestamp timestamp, PromptIterationEntity promptIteration) {
        this.content = content;
        this.timestamp = timestamp;
        this.promptIteration = promptIteration;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }

    public PromptIterationEntity getPromptIteration() {
        return promptIteration;
    }

    abstract String getType();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageEntity that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public abstract MessageEntity clone(PromptIterationEntity promptIterationEntity);
}
