package ch.fhnw.deardevbackend.controller;

import ch.fhnw.deardevbackend.dto.TeamWithMembersDTO;
import ch.fhnw.deardevbackend.services.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/team-member")
public class TeamMemberController {

    @Autowired
    private TeamMemberService teamMemberService;

    @GetMapping("/{teamId}")
    public TeamWithMembersDTO getTeamMembersByTeamId(@PathVariable Integer teamId) {
        return teamMemberService.getTeamMembersByTeamId(teamId);
    }
}
