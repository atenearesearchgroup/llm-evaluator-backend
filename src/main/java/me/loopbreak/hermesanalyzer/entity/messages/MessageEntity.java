package me.loopbreak.hermesanalyzer.entity.messages;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "message_type",
discriminatorType = DiscriminatorType.STRING)
public class MessageEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private Timestamp timestamp;
    private String content;

    @ManyToOne
    @JoinColumn(name = "prompt_iteration_id")
    private PromptIterationEntity promptIteration;

    public MessageEntity() {
    }

    public MessageEntity(String content, Timestamp timestamp) {
        this.content = content;
        this.timestamp = timestamp;
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
}
