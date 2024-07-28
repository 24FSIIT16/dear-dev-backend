package ch.fhnw.deardevbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TeamWorkKindInsightDTO {
    private Integer teamId;
    private List<WorkKindInsightDTO> workKindInsights;
}
