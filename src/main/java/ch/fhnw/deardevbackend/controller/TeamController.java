package ch.fhnw.deardevbackend.controller;

import ch.fhnw.deardevbackend.controller.exceptions.ErrorResponse;
import ch.fhnw.deardevbackend.controller.exceptions.YappiException;
import ch.fhnw.deardevbackend.dto.CreateTeamDTO;
import ch.fhnw.deardevbackend.dto.JoinTeamDTO;
import ch.fhnw.deardevbackend.entities.Team;
import ch.fhnw.deardevbackend.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/create")
    public ResponseEntity<Team> createTeam(@RequestBody CreateTeamDTO request) {
        Team createdTeam = teamService.createTeam(request);
        return ResponseEntity.ok().body(createdTeam);
    }

    @PostMapping("/join")
    public ResponseEntity<Team> joinTeam(@RequestBody JoinTeamDTO request) {
        Team team = teamService.joinTeam(request);
        return ResponseEntity.ok().body(team);
    }

    @ExceptionHandler(YappiException.class)
    public ResponseEntity<ErrorResponse> handleYappiException(YappiException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }
}
