package ch.fhnw.deardevbackend.dto.insights;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkKindInsightDTO {
    private Integer teamId;
    private Integer workKindId;
    private String workKindName;
    private Double averageHappiness;
    private Long totalCount;
}
