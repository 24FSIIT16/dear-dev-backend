package ch.fhnw.deardevbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "work_kind_happiness_survey")
@Builder
@Data
public class EmotionHappinessSurvey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "submitted")
    private String submitted;

    @Column(name = "score")
    private Integer score;

    @Column(name = "emotion_id")
    private Integer emotionId;





}
