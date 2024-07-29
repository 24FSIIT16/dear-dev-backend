package ch.fhnw.deardevbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InsightDTO {
    HappinessInsightDTO happinessInsightDTO;
    WorkKindInsightDTO workKindInsightDTO;
}
