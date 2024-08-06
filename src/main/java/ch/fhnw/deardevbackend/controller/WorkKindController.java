package ch.fhnw.deardevbackend.controller;

import ch.fhnw.deardevbackend.dto.WorkKindAndTeamDTO;
import ch.fhnw.deardevbackend.entities.User;
import ch.fhnw.deardevbackend.entities.WorkKind;
import ch.fhnw.deardevbackend.services.TeamService;
import ch.fhnw.deardevbackend.services.WorkKindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<List<WorkKind>> getAllWorkKindsByUserAndTeam(@PathVariable Integer userId) {
        List<Integer> teamIds = teamService.getTeamIdsForUser(userId);
        List<WorkKind> workKinds = workKindService.getWorkKindsForTeams(teamIds);
        return ResponseEntity.ok(workKinds);
    }

    @GetMapping("/team")
    public ResponseEntity<List<WorkKindAndTeamDTO>> getWorkKindsAndTeams() {
        Integer userId = getCurrentUser().getId();
        List<WorkKindAndTeamDTO> workKindAndTeams = workKindService.getWorkKindsAndTeams(userId);
        return ResponseEntity.ok(workKindAndTeams);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
