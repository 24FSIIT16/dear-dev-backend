package ch.fhnw.deardevbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {
    private WorkKindDTO mostVotedWorkKind;
    private Integer averageScore;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WorkKindDTO {
        private Integer workKindId;
        private String workKindName;
        private Long voteCount;
    }
}
