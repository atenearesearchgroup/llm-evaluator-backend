package me.loopbreak.hermesanalyzer.entity.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import me.loopbreak.hermesanalyzer.entity.ChatEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "prompt_iteration")
//@IdClass(PromptIterationEntity.PromptIterationId.class)
public class PromptIterationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    //    @Id
    private int iteration;

    @JsonIgnoreProperties("promptIterations")
    @ManyToOne
    private ChatEntity chat;

    @JsonIgnoreProperties("promptIteration")
    @OneToMany(mappedBy = "promptIteration", cascade = CascadeType.ALL)
    private List<MessageEntity> messages;

    public PromptIterationEntity() {
    }

    public PromptIterationEntity(String type, ChatEntity chat, int iteration) {
        this.type = type;
        this.chat = chat;
        this.iteration = iteration;
        this.messages = new ArrayList<>();
    }

    private PromptIterationEntity(ChatEntity chatEntity, PromptIterationEntity promptIteration) {
        this.type = promptIteration.getType();
        this.chat = chatEntity;
        this.iteration = promptIteration.getIteration();
        this.messages = new ArrayList<>();
        promptIteration.getMessages().forEach(message -> this.messages.add(message.clone(this)));
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public ChatEntity getChat() {
        return chat;
    }

    public int getIteration() {
        return iteration;
    }

    public List<MessageEntity> getMessages() {
        return messages;
    }

    public PromptIterationEntity clone(ChatEntity chatEntity) {
        return new PromptIterationEntity(chatEntity, this);
    }

    public static class PromptIterationId implements Serializable {
        private long id;
        private int iteration;

        public PromptIterationId() {
        }

        public PromptIterationId(long id, int iteration) {
            this.id = id;
            this.iteration = iteration;
        }

        public long getId() {
            return id;
        }

        public int getIteration() {
            return iteration;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PromptIterationId that)) return false;
            return getId() == that.getId() && getIteration() == that.getIteration();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getIteration());
        }
    }
}
