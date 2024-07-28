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

    @GetMapping("happiness/team-vs-personal/{userId}")
    public List<TeamHappinessInsightDTO> getDailyAverages(@PathVariable Integer userId) {
        return insightsService.getDailyAveragesByUserId(userId);
    }

    @GetMapping("/happiness/{userId}/team/{teamId}")
    public ResponseEntity<List<HappinessInsightDTO>> getHappinessInsightsByTeam(
            @PathVariable Integer userId, @PathVariable Integer teamId) {
        try {
            List<HappinessInsightDTO> insights = insightsService.getHappinessInsightsByTeam(userId, teamId);
            return ResponseEntity.ok(insights);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/workkind/team-vs-personal/{userId}")
    public List<TeamWorkKindInsightDTO> getWorkKindHappinessByUserId(@PathVariable  Integer userId) {
        return insightsService.getWorkKindHappinessByUserId(userId);
    }

}
