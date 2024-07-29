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

    // Overall
    @GetMapping("/{userId}/team/{teamId}/sprint/{sprint}")
    public ResponseEntity<List<InsightDTO>> getInsightsByTeam(
            @PathVariable Integer userId,
            @PathVariable Integer teamId,
            @PathVariable String sprint) {
        List<InsightDTO> insights = insightsService.getInsightsByTeamAndSprint(userId, teamId, sprint);
        return ResponseEntity.ok(insights);
    }

    // happiness team vs personal
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

}
