package ch.fhnw.deardevbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class InsightDTO {
    private List<HappinessInsightDTO> happinessInsights;
    private List<WorkKindInsightDTO> workKindInsights;
}
