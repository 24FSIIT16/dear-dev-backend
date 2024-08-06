package ch.fhnw.deardevbackend.dto.insights;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkKindInsightDTO {
    private Integer workKindId;
    private String workKindName;
    private Integer userAverage;
    private Integer userCount;
    private Integer teamAverage;
    private Integer teamCount;
}
