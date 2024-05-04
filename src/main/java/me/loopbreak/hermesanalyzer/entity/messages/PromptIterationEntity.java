package me.loopbreak.hermesanalyzer.entity.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import me.loopbreak.hermesanalyzer.entity.DraftEntity;

import java.io.Serializable;
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
    private DraftEntity draft;

    @JsonIgnoreProperties("promptIteration")
    @OneToMany(mappedBy = "promptIteration")
    private List<MessageEntity> messages;

    public PromptIterationEntity() {
    }

    public PromptIterationEntity(String type, DraftEntity draft, int iteration) {
        this.type = type;
        this.draft = draft;
        this.iteration = iteration;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public DraftEntity getDraft() {
        return draft;
    }

    public int getIteration() {
        return iteration;
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
