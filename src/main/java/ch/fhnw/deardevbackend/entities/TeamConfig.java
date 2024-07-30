package ch.fhnw.deardevbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "team_config")
@Builder
@Data
public class TeamConfig {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @ElementCollection
    @CollectionTable(name = "team_config_work_kinds", joinColumns = @JoinColumn(name = "team_config_id"))
    @Column(name = "work_kind_id")
    private List<Integer> workKindIds;

    @Transient
    private List<WorkKind> workKinds;

    @Column(name = "happiness_survey")
    private Boolean happinessSurvey;

    @Column(name = "work_kind_survey")
    private Boolean workKindSurvey;

    @Column(name = "emotion_survey")
    private Boolean emotionSurvey;
}
