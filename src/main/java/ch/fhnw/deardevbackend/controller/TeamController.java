package ch.fhnw.deardevbackend.controller;

import ch.fhnw.deardevbackend.controller.exceptions.ErrorResponse;
import ch.fhnw.deardevbackend.controller.exceptions.YappiException;
import ch.fhnw.deardevbackend.dto.*;
import ch.fhnw.deardevbackend.entities.Team;
import ch.fhnw.deardevbackend.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/user/{userId}")
    public List<TeamAndRoleDTO> getTeamsAndRoleByUserId(@PathVariable Integer userId) {
        return teamService.getTeamsAndRoleByUserId(userId);
    }

    @PostMapping("/create")
    public ResponseEntity<Team> createTeam(@RequestBody CreateTeamDTO request) {
        Team createdTeam = teamService.createTeam(request);
        return ResponseEntity.ok().body(createdTeam);
    }

    @GetMapping("/{teamId}/config")
    public ResponseEntity<TeamConfigDTO> getTeamConfigByTeamId(@PathVariable Integer teamId) {
        TeamConfigDTO config = teamService.getTeamConfigByTeamId(teamId);
        return ResponseEntity.ok().body(config);
    }

    @PutMapping("/{teamId}/config")
    public ResponseEntity<TeamConfigDTO> updateTeamConfig(@PathVariable Integer teamId, @RequestBody TeamConfigDTO request) {
        TeamConfigDTO updatedConfig = teamService.updateTeamConfig(teamId, request);
        return ResponseEntity.ok().body(updatedConfig);
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

    @GetMapping("/user/{userId}/sprints")
    public ResponseEntity<List<TeamWithSprintsDTO>> getTeamsAndSprintsForUser(@PathVariable Integer userId) {
        List<TeamWithSprintsDTO> teamsWithSprints = teamService.getTeamsAndSprintsForUser(userId);
        return ResponseEntity.ok(teamsWithSprints);
    }
}
