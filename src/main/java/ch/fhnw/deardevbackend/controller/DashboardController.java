package ch.fhnw.deardevbackend.controller;

import ch.fhnw.deardevbackend.dto.DashboardDTO;
import ch.fhnw.deardevbackend.dto.SubmitEmotionSurveyDTO;
import ch.fhnw.deardevbackend.dto.SubmitHappinessSurveyDTO;
import ch.fhnw.deardevbackend.dto.SubmitWorkKindSurveyDTO;
import ch.fhnw.deardevbackend.entities.EmotionSurvey;
import ch.fhnw.deardevbackend.entities.HappinessSurvey;
import ch.fhnw.deardevbackend.entities.User;
import ch.fhnw.deardevbackend.entities.WorkKindSurvey;
import ch.fhnw.deardevbackend.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/data")
    public ResponseEntity<DashboardDTO> getDashboardData() {
        Integer userId = getCurrentUserFromContext().getId();
        DashboardDTO dashboardDTO = dashboardService.getDashboardData(userId);
        return ResponseEntity.ok().body(dashboardDTO);
    }

    @PostMapping("/survey/happiness")
    public ResponseEntity<HappinessSurvey> submitHappinessSurvey(@RequestBody SubmitHappinessSurveyDTO request) {
        HappinessSurvey data = dashboardService.saveHappinessSurvey(request);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping("survey/workkind")
    public ResponseEntity<WorkKindSurvey> submitWorkKindSurvey(@RequestBody SubmitWorkKindSurveyDTO request) {
        WorkKindSurvey data = dashboardService.saveWorkKindSurvey(request);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping("survey/emotion")
    public ResponseEntity<EmotionSurvey> submitEmotionSurvey(@RequestBody SubmitEmotionSurveyDTO request) {
        EmotionSurvey data = dashboardService.saveEmotionSurvey(request);
        return ResponseEntity.ok().body(data);
    }

    private User getCurrentUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
