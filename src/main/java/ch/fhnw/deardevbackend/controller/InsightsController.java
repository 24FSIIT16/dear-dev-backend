package ch.fhnw.deardevbackend.controller;

import ch.fhnw.deardevbackend.dto.insights.InsightDTO;
import ch.fhnw.deardevbackend.services.InsightsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/v1/insights")
public class InsightsController {

    @Autowired
    private InsightsService insightsService;


    @GetMapping("/{userId}/team/{teamId}/sprint/{sprintId}")
    public ResponseEntity<InsightDTO> getInsightsByTeamAndSprint(@PathVariable Integer userId,
                                                                 @PathVariable Integer teamId,
                                                                 @PathVariable Integer sprintId) {
        InsightDTO insights = insightsService.getInsightsByTeamAndSprint(userId, teamId, sprintId);
        return ResponseEntity.ok(insights);
    }

}
