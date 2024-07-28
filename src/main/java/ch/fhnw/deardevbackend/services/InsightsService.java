package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.controller.exceptions.YappiException;
import ch.fhnw.deardevbackend.dto.*;
import ch.fhnw.deardevbackend.mapper.HappinessInsightMapper;
import ch.fhnw.deardevbackend.mapper.WorkKindInsightMapper;
import ch.fhnw.deardevbackend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsightsService {

    @Autowired
    private InsightsRepository insightsRepository;

    @Autowired
    private HappinessSurveyRepository happinessSurveyRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private HappinessInsightMapper happinessInsightMapper;

    @Autowired
    private final WorkKindInsightMapper workKindInsightMapper = WorkKindInsightMapper.INSTANCE;

    // todo add user authorization
    @Transactional(readOnly = true)
    public List<TeamHappinessInsightDTO> getDailyAveragesByUserId(Integer userId) {
        try {
            List<Integer> teamIds = teamMemberRepository.findTeamIdByUserId(userId);

            return teamIds.stream().map(teamId -> {
                List<Object[]> userAverages = happinessSurveyRepository.findDailyAveragesByUserId(userId);
                List<Object[]> teamAverages = insightsRepository.findTeamDailyAveragesExcludingUser(userId);

                List<HappinessInsightDTO> insights = userAverages.stream().map(userAvg -> {
                    String day = userAvg[0].toString();
                    double userAverage = (Double) userAvg[1];

                    double teamAverage = teamAverages.stream()
                            .filter(teamAvg -> teamAvg[0].toString().equals(day))
                            .map(teamAvg -> (Double) teamAvg[1])
                            .findFirst()
                            .orElse(0.0);

                    return happinessInsightMapper.toDTO(day, userAverage, teamAverage);
                }).collect(Collectors.toList());

                return new TeamHappinessInsightDTO(teamId, insights);
            }).collect(Collectors.toList());

        } catch (Exception e) {
            throw new YappiException("Error fetching happiness insight data  " +  e);
        }
    }

    @Transactional(readOnly = true)
    public List<TeamWorkKindInsightDTO> getWorkKindHappinessByUserId(Integer userId) {
        List<Object[]> results = insightsRepository.findWorkKindHappinessByUserId(userId);

        Map<Integer, List<WorkKindInsightDTO>> groupedByTeam = results.stream()
                .map(workKindInsightMapper::toDTO)
                .collect(Collectors.groupingBy(WorkKindInsightDTO::getTeamId));

        return groupedByTeam.entrySet().stream()
                .map(entry -> new TeamWorkKindInsightDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
