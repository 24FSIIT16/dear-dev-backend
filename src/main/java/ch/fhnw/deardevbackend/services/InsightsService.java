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
public class InsightsService {

    private final InsightsRepository insightsRepository;
    private final HappinessSurveyRepository happinessSurveyRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final HappinessInsightMapper happinessInsightMapper;
    private final WorkKindInsightMapper workKindInsightMapper;


    public InsightsService(InsightsRepository insightsRepository,
                           HappinessSurveyRepository happinessSurveyRepository,
                           TeamMemberRepository teamMemberRepository,
                           HappinessInsightMapper happinessInsightMapper,
                           WorkKindInsightMapper workKindInsightMapper) {
        this.insightsRepository = insightsRepository;
        this.happinessSurveyRepository = happinessSurveyRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.happinessInsightMapper = happinessInsightMapper;
        this.workKindInsightMapper = workKindInsightMapper;
    }

    @Transactional(readOnly = true)
    public List<TeamHappinessInsightDTO> getDailyAveragesByUserId(Integer userId) {
        List<Integer> teamIds = teamMemberRepository.findTeamIdByUserId(userId);

        return teamIds.stream().map(teamId -> {
            List<Object[]> userAverages = happinessSurveyRepository.findDailyAveragesByUserId(userId);
            List<Object[]> teamAverages = insightsRepository.findTeamDailyAveragesExcludingUser(teamId, userId);

            List<HappinessInsightDTO> insights = userAverages.stream().map(userAvg -> {
                String day = userAvg[0].toString();
                double userAverage = ((Number) userAvg[1]).doubleValue();

                double teamAverage = teamAverages.stream()
                        .filter(teamAvg -> teamAvg[0].toString().equals(day))
                        .map(teamAvg -> ((Number) teamAvg[1]).doubleValue())
                        .findFirst()
                        .orElse(0.0);

                return happinessInsightMapper.toDTO(day, userAverage, teamAverage);
            }).collect(Collectors.toList());

            return new TeamHappinessInsightDTO(teamId, insights);
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<HappinessInsightDTO> getHappinessInsightsByTeam(Integer userId, Integer teamId) {
        List<Object[]> userAverages = happinessSurveyRepository.findDailyAveragesByUserId(userId);
        List<Object[]> teamAverages = insightsRepository.findTeamDailyAveragesExcludingUser(teamId, userId);

        Map<String, Double> teamAveragesMap = teamAverages.stream()
                .collect(Collectors.toMap(
                        row -> row[0].toString(),
                        row -> ((Number) row[1]).doubleValue()
                ));

        return userAverages.stream()
                .map(userAvg -> {
                    String day = userAvg[0].toString();
                    double userAverage = ((Number) userAvg[1]).doubleValue();
                    double teamAverage = teamAveragesMap.getOrDefault(day, 0.0);
                    return happinessInsightMapper.toDTO(day, userAverage, teamAverage);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TeamWorkKindInsightDTO> getWorkKindHappinessByUserId(Integer userId) {
        List<Object[]> results = insightsRepository.findWorkKindHappinessByUserId(userId);

        Map<Integer, List<WorkKindInsightDTO>> groupedByTeam = results.stream()
                .map(row -> workKindInsightMapper.toDTO(
                        (Integer) row[0],
                        (Integer) row[1],
                        (String) row[2],
                        (Double) row[3],
                        (Long) row[4]
                ))
                .collect(Collectors.groupingBy(WorkKindInsightDTO::getTeamId));

        return groupedByTeam.entrySet().stream()
                .map(entry -> new TeamWorkKindInsightDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
