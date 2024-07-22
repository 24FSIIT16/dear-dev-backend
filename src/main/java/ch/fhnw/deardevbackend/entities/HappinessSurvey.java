package ch.fhnw.deardevbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "happiness_survey")
@Builder
@Data
public class HappinessSurvey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "submitted", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private String submitted;

    @Column(name = "score")
    private Integer score;





}
