package ch.fhnw.deardevbackend.controller;

import ch.fhnw.deardevbackend.dto.DashboardDTO;
import ch.fhnw.deardevbackend.dto.SubmitEmotionSurveyDTO;
import ch.fhnw.deardevbackend.dto.SubmitHappinessSurveyDTO;
import ch.fhnw.deardevbackend.dto.SubmitWorkKindSurveyDTO;
import ch.fhnw.deardevbackend.entities.EmotionSurvey;
import ch.fhnw.deardevbackend.entities.HappinessSurvey;
import ch.fhnw.deardevbackend.entities.WorkKindSurvey;
import ch.fhnw.deardevbackend.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    // todo ensure only logged in user can post his entries

    @GetMapping("/{userId}")
    public ResponseEntity<DashboardDTO> getDashboardDataByUserId(@PathVariable Integer userId) {
        DashboardDTO dashboardDTO = dashboardService.getDashboardDataByUserId(userId);
        return ResponseEntity.ok().body(dashboardDTO);
    }

    @GetMapping("/happiness/average/{userId}")
    public ResponseEntity<Integer> getAverageScoreByUserId(@PathVariable Integer userId) {
        Integer averageScore = dashboardService.getAverageScoreByUserId(userId);
        return ResponseEntity.ok().body(averageScore);
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
}
