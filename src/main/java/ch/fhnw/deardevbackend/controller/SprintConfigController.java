package ch.fhnw.deardevbackend.controller;

import ch.fhnw.deardevbackend.dto.CreateSprintDTO;
import ch.fhnw.deardevbackend.entities.SprintConfig;
import ch.fhnw.deardevbackend.entities.User;
import ch.fhnw.deardevbackend.mapper.SprintConfigMapper;
import ch.fhnw.deardevbackend.services.SprintConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @PostMapping("/create")
    public ResponseEntity<SprintConfig> createSprint(@RequestBody CreateSprintDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        SprintConfig sprintConfig = SprintConfigMapper.INSTANCE.toEntity(request);
        sprintConfig.setCreatedBy(user.getId());
        sprintConfig.setCreatedAt(LocalDateTime.now());

        SprintConfig createdSprint = sprintConfigService.createSprint(sprintConfig);
        return ResponseEntity.ok().body(createdSprint);
    }

    @PutMapping("/update/{sprintId}")
    public ResponseEntity<SprintConfig> updateSprintConfig(@PathVariable Integer sprintId, @RequestBody CreateSprintDTO request) {
        SprintConfig updatedSprint = sprintConfigService.updateSprint(sprintId, request);
        return ResponseEntity.ok().body(updatedSprint);
    }
}
