package ch.fhnw.deardevbackend.controller;

import ch.fhnw.deardevbackend.dto.CreateTeamDTO;
import ch.fhnw.deardevbackend.entities.Team;
import ch.fhnw.deardevbackend.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/team/create")
    public ResponseEntity<Team> createTeam(@RequestBody CreateTeamDTO request) {
        Team createdTeam = teamService.createTeam(request);
        return ResponseEntity.ok().body(createdTeam);
    }
}
