package ch.fhnw.deardevbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {
    private LocalDate lastSubmissionDateOfHappiness;
    private LocalDate activeSprintEndDate;
    private List<String> mostTrackedEmotions;
    private String mostTrackedWorkKind;
    private Integer numberOfDaysWithHappinessSurvey;
    private Integer numberOfHappinessSurveysToday;
    private Integer numberOfTeams;
    private Integer numberOfTeamMembers;
    private Double averageHappinessScore;
}
