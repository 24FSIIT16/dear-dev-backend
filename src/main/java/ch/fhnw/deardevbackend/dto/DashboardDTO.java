package ch.fhnw.deardevbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {
    private MostVotedWorkKindDTO mostVotedWorkKind;
    private Integer averageScore;


}
