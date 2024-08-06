package ch.fhnw.deardevbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeamWithSprintsDTO {
    private Integer id;
    private String name;
    private List<SprintDTO> sprints;
}
