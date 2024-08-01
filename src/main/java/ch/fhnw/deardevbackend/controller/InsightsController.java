package ch.fhnw.deardevbackend.controller;

import ch.fhnw.deardevbackend.dto.*;
import ch.fhnw.deardevbackend.services.InsightsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/v1/insights")
public class InsightsController {

    @Autowired
    private InsightsService insightsService;


    @GetMapping("/{userId}/team/{teamId}/sprint/{sprint}")
    public ResponseEntity<InsightDTO> getInsightsByTeamAndSprint(@PathVariable Integer userId,
                                                                 @PathVariable Integer teamId,
                                                                 @PathVariable String sprint) {
        InsightDTO insights = insightsService.getInsightsByTeamAndSprint(userId, teamId, sprint);
        return ResponseEntity.ok(insights);
    }

}
