package ch.fhnw.deardevbackend.controller;

import ch.fhnw.deardevbackend.entities.OverallHappinessSurvey;
import ch.fhnw.deardevbackend.services.OverallHappinessSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/overall-happiness")
public class OverallHappinessSurveyController {
    @Autowired
    private OverallHappinessSurveyService service;

    @PostMapping
    public OverallHappinessSurvey save(@RequestBody OverallHappinessSurvey survey) {
        return service.save(survey);
    }

    @GetMapping("/{userId}")
    public List<OverallHappinessSurvey> getAllByUserId(@PathVariable Integer userId) {
        return service.getAllByUserId(userId);
    }

    @GetMapping("/{userId}/average-score")
    public Double getAverageScoreByUserId(@PathVariable Integer userId) {
        return service.getAverageScoreByUserId(userId);
    }
}
