package ch.fhnw.deardevbackend.dto.insights;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmotionInsightDTO {
    private Integer emotionId;
    private String emotionName;
    private Long userCount;
    private Long teamCount;
}
