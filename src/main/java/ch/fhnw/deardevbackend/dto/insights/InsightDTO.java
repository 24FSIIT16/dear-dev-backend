package ch.fhnw.deardevbackend.dto.insights;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class InsightDTO {
    private List<HappinessInsightDTO> happinessInsights;
    private List<WorkKindInsightDTO> workKindInsights;
    private List<EmotionInsightDTO> emotionInsights;
    private double userAverageHappiness;
    private double teamAverageHappiness;
}
