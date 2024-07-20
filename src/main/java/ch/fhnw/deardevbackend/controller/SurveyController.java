package ch.fhnw.deardevbackend.controller;

import ch.fhnw.deardevbackend.dto.SubmitHappinessSurveyDTO;
import ch.fhnw.deardevbackend.dto.SubmitWorkKindSurveyDTO;
import ch.fhnw.deardevbackend.entities.HappinessSurvey;
import ch.fhnw.deardevbackend.entities.WorkKindSurvey;
import ch.fhnw.deardevbackend.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/survey")
public class SurveyController {
    @Autowired
    private SurveyService surveyService;

    // todo ensure only logged in user can post his entries

    @PostMapping("/happiness")
    public ResponseEntity<HappinessSurvey> submitHappinessSurvey(@RequestBody SubmitHappinessSurveyDTO request) {
        HappinessSurvey data = surveyService.save(request);
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/happiness/average/{userId}")
    public ResponseEntity<Integer> getAverageScoreByUserId(@PathVariable int userId) {
        Integer averageScore = surveyService.getAverageScoreByUserId(userId);
        return ResponseEntity.ok().body(averageScore);
    }

    @PostMapping("/workkind")
    public ResponseEntity<WorkKindSurvey> submitWorkKindSurvey(@RequestBody SubmitWorkKindSurveyDTO request) {
        WorkKindSurvey data = surveyService.save(request);
        return ResponseEntity.ok().body(data);
    }
}
