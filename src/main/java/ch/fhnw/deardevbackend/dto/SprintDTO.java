package ch.fhnw.deardevbackend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SprintDTO {
    private Integer id;
    private String sprintName;
    private String sprintGoal;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
