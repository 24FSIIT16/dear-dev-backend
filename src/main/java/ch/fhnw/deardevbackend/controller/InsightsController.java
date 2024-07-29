package ch.fhnw.deardevbackend.controller;

import ch.fhnw.deardevbackend.dto.*;
import ch.fhnw.deardevbackend.services.InsightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/insights")
public class InsightsController {

    @Autowired
    private InsightsService insightsService;

    // Line Chart: overall happiness team vs personal
    @GetMapping("/happiness/{userId}/team/{teamId}/sprint/{sprint}")
    public ResponseEntity<List<HappinessInsightDTO>> getHappinessInsightsByTeam(
            @PathVariable Integer userId, @PathVariable Integer teamId, @PathVariable String sprint) {
        try {
            List<HappinessInsightDTO> insights = insightsService.getHappinessInsightsByTeam(userId, teamId, sprint);
            return ResponseEntity.ok(insights);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Bar Chart: workkind team vs personal
    @GetMapping("/workkind/{userId}/team/{teamId}/sprint/{sprint}")
    public List<TeamWorkKindInsightDTO> getWorkKindHappinessByUserId(@PathVariable Integer userId, @PathVariable Integer teamId, @PathVariable String sprint) {
        //        return insightsService.getWorkKindHappinessByUserId(userId);
        return List.of();
    }

    // Line Chart/Area Chart: velocity of the sprint vs happiness vs workkind
    // no timestamp
    // y: workkind, velocity
    // x: days

    // Radar Chart
    // Top 10 Emotions Personal & Team

    // Radar Chart
    // Top 10 Workkinds Personal & Team

}
