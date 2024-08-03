package ch.fhnw.deardevbackend.controller;

import ch.fhnw.deardevbackend.dto.CreateSprintDTO;
import ch.fhnw.deardevbackend.entities.SprintConfig;
import ch.fhnw.deardevbackend.entities.User;
import ch.fhnw.deardevbackend.mapper.SpringConfigMapper;
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

    @PostMapping("/create")
    public ResponseEntity<SprintConfig> createSprint(@RequestBody CreateSprintDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        
        SprintConfig sprintConfig = SpringConfigMapper.INSTANCE.toEntity(request);
        sprintConfig.setCreatedBy(user.getId());

        SprintConfig createdSprint = sprintConfigService.createSprint(sprintConfig);
        return ResponseEntity.ok().body(createdSprint);
    }
}
