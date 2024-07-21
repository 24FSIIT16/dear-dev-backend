package ch.fhnw.deardevbackend.dto;

import ch.fhnw.deardevbackend.entities.Team;
import ch.fhnw.deardevbackend.entities.TeamMemberWithUser;
import lombok.Data;

import java.util.List;

@Data
public class TeamWithMembersDTO {
    private Team team;
    private List<TeamMemberWithUser> members;
    private Boolean isAdmin;
}
