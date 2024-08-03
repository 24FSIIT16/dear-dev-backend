package ch.fhnw.deardevbackend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateSprintDTO {
    private String sprintName;
    private String sprintGoal;
    private LocalDate startDate;
    private LocalDate endDate;
}
