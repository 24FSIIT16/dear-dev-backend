package ch.fhnw.deardevbackend.controller;

import ch.fhnw.deardevbackend.entities.WorkKind;
import ch.fhnw.deardevbackend.services.WorkKindService;
import ch.fhnw.deardevbackend.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/workkinds")
public class WorkKindController {

    @Autowired
    private WorkKindService workKindService;

    @Autowired
    private TeamService teamService;


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkKind>> getAllWorkKindsByUserAndTeam(@PathVariable int userId) {
        List<Integer> teamIds = teamService.getTeamIdsForUser(userId);
        List<WorkKind> workKinds = workKindService.getWorkKindsForTeams(teamIds);
        return ResponseEntity.ok(workKinds);
    }
}
