package ch.fhnw.deardevbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TeamHappinessInsightDTO {
    private Integer teamId;
    private List<HappinessInsightDTO> insights; // todo rename happinessInsights
}
