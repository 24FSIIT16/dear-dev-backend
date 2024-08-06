package ch.fhnw.deardevbackend.dto;

import ch.fhnw.deardevbackend.entities.WorkKind;
import lombok.Data;

@Data
public class WorkKindAndTeamDTO {
    private WorkKind workKind;
    private String teamName;
}
