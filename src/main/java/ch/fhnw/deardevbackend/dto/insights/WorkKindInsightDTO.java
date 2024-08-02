package ch.fhnw.deardevbackend.dto.insights;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkKindInsightDTO {
    private Integer workKindId;
    private String workKindName;
    private Double userAverage;
    private Long userCount;
    private Double teamAverage;
    private Long teamCount;
}
