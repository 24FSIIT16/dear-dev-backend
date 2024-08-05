package ch.fhnw.deardevbackend.dto;

import ch.fhnw.deardevbackend.entities.SprintConfig;
import lombok.Data;

@Data
public class ActiveSprintsDTO {
    private String teamName;
    private SprintConfig sprint;
}
