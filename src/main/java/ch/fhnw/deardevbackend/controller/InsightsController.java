package ch.fhnw.deardevbackend.controller;

import ch.fhnw.deardevbackend.dto.*;
import ch.fhnw.deardevbackend.services.InsightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/insights")
public class InsightsController {

    @Autowired
    private InsightsService insightsService;

    @GetMapping("happiness/team-vs-personal/{userId}")
    public List<HappinessInsightsChartDTO> getDailyAverages(@PathVariable Integer userId) {
        return insightsService.getDailyAveragesByUserId(userId);
    }

}
