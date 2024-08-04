package ch.fhnw.deardevbackend.dto;

import ch.fhnw.deardevbackend.entities.SprintConfig;
import ch.fhnw.deardevbackend.entities.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SprintsAndTeamsDTO {
    private List<Team> teams;
    private List<SprintConfig> sprints;
}
