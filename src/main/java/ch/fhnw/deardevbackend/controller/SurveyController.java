package ch.fhnw.deardevbackend.controller;

import ch.fhnw.deardevbackend.dto.SubmitHappinessSurveyDTO;
import ch.fhnw.deardevbackend.entities.HappinessSurvey;
import ch.fhnw.deardevbackend.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/survey")
public class SurveyController {
    @Autowired
    private SurveyService service;

    @PostMapping("/happiness")
    public ResponseEntity<HappinessSurvey> submitHappinessSurvey(@RequestBody SubmitHappinessSurveyDTO request) {
        HappinessSurvey data = service.save(request);
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/happiness/{userId}")
    public List<HappinessSurvey> getAllByUserId(@PathVariable Integer userId) {
        return service.getAllByUserId(userId);
    }

    @GetMapping("happiness/{userId}/average")
    public Double getAverageScoreByUserId(@PathVariable Integer userId) {
        return service.getAverageScoreByUserId(userId);
    }
}