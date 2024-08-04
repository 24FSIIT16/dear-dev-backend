package ch.fhnw.deardevbackend.dto.insights;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkKindCountPerDayInsightDTO {
    private Integer workKindCount;
    private Double userAverageHappiness;
//    private Double teamAverageHappiness;
}
