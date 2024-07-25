package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.dto.*;
import ch.fhnw.deardevbackend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsightsService {

    @Autowired
    private InsightsRepository insightsRepository;
    private HappinessSurveyRepository happinessSurveyRepository;

    // todo add user authorization
    // todo add yappi exception handling
    @Transactional(readOnly = true)
    public List<HappinessInsightsChartDTO> getDailyAveragesByUserId(Integer userId) {
        List<Object[]> userAverages = happinessSurveyRepository.findDailyAveragesByUserId(userId);
        List<Object[]> teamAverages = insightsRepository.findTeamDailyAveragesExcludingUser(userId);

        return userAverages.stream().map(userAvg -> {
            String day = (String) userAvg[0].toString();
            double userAverage = (Double) userAvg[1];

            double teamAverage = teamAverages.stream()
                    .filter(teamAvg -> teamAvg[0].toString().equals(day))
                    .map(teamAvg -> (Double) teamAvg[1])
                    .findFirst()
                    .orElse(0.0);

            return new HappinessInsightsChartDTO(day, userAverage, teamAverage);
        }).collect(Collectors.toList());
    }
 
}
