package ch.fhnw.deardevbackend.controller;

import ch.fhnw.deardevbackend.dto.ActiveSprintsDTO;
import ch.fhnw.deardevbackend.dto.CreateSprintDTO;
import ch.fhnw.deardevbackend.dto.SprintIdAndTeamIdDTO;
import ch.fhnw.deardevbackend.dto.SprintsAndTeamsDTO;
import ch.fhnw.deardevbackend.entities.SprintConfig;
import ch.fhnw.deardevbackend.entities.User;
import ch.fhnw.deardevbackend.services.SprintConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/sprint")
public class SprintConfigController {

    @Autowired
    private SprintConfigService sprintConfigService;

    @GetMapping("/createdBy/{userId}")
    public List<SprintConfig> getSprintsByCreatedBy(@PathVariable Integer userId) {
        return sprintConfigService.getSprintsByCreatedBy(userId);
    }

    @GetMapping("/{sprintId}")
    public ResponseEntity<SprintConfig> getSprintById(@PathVariable Integer sprintId) {
        SprintConfig sprintConfig = sprintConfigService.getSprintById(sprintId);
        return ResponseEntity.ok().body(sprintConfig);
    }

    @GetMapping("/sprints-and-teams")
    public ResponseEntity<SprintsAndTeamsDTO> getSprintsAndTeams() {
        Integer userId = getCurrentUserFromContext().getId();
        SprintsAndTeamsDTO sprintsAndTeams = sprintConfigService.getSprintsAndTeams(userId);
        return ResponseEntity.ok().body(sprintsAndTeams);
    }

    @GetMapping("/active")
    public ResponseEntity<List<ActiveSprintsDTO>> getActiveSprints() {
        Integer userId = getCurrentUserFromContext().getId();
        List<ActiveSprintsDTO> activeSprints = sprintConfigService.getActiveSprints(userId);
        return ResponseEntity.ok().body(activeSprints);
    }

    @PostMapping("/create")
    public ResponseEntity<SprintConfig> createSprint(@RequestBody CreateSprintDTO request) {
        Integer userId = getCurrentUserFromContext().getId();
        SprintConfig createdSprint = sprintConfigService.createSprint(request, userId);
        return ResponseEntity.ok().body(createdSprint);
    }

    @PutMapping("/update/{sprintId}")
    public ResponseEntity<SprintConfig> updateSprintConfig(@PathVariable Integer sprintId, @RequestBody CreateSprintDTO request) {
        SprintConfig updatedSprint = sprintConfigService.updateSprint(sprintId, request);
        return ResponseEntity.ok().body(updatedSprint);
    }

    @PutMapping("/complete/{sprintId}")
    public ResponseEntity<String> completeSprint(@PathVariable Integer sprintId) {
        try {
            sprintConfigService.completeSprint(sprintId);
            return ResponseEntity.ok().body("Sprint completed successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/start")
    public ResponseEntity<String> startSprint(@RequestBody SprintIdAndTeamIdDTO request) {
        try {
            sprintConfigService.startSprint(request.getTeamId(), request.getSprintId());
            return ResponseEntity.ok().body("Sprint started successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private User getCurrentUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
