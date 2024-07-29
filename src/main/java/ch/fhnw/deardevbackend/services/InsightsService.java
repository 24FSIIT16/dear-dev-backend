package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.annotations.ValidateUserIdParam;
import ch.fhnw.deardevbackend.dto.*;
import ch.fhnw.deardevbackend.mapper.HappinessInsightMapper;
import ch.fhnw.deardevbackend.mapper.WorkKindInsightMapper;
import ch.fhnw.deardevbackend.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
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
    public InsightDTO getInsightsByTeamAndSprint(@ValidateUserIdParam Integer userId, Integer teamId, String sprint) {
        List<HappinessInsightDTO> happinessInsights = getHappinessInsightsByTeam(userId, teamId, sprint);
        List<WorkKindInsightDTO> workKindInsights = getWorkKindInsightsByUserId(userId);

        return new InsightDTO(happinessInsights, workKindInsights);
    }

    // todo later asap structure is defined
    private WorkKindInsightDTO findMatchingWorkKindInsight(HappinessInsightDTO happinessInsightDTO, List<WorkKindInsightDTO> workKindInsights) {
        return workKindInsights.stream()
               // .filter(workKindInsightDTO -> workKindInsightDTO.getTeamId().equals(happinessInsightDTO.getTeamId()))
                .findFirst()
                .orElse(null);
    }


    @Transactional(readOnly = true)
    public List<TeamHappinessInsightDTO> getDailyAveragesByUserId(@ValidateUserIdParam Integer userId) {
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
    public List<HappinessInsightDTO> getHappinessInsightsByTeam(@ValidateUserIdParam Integer userId, Integer teamId, String sprint) {
        LocalDateTime startDate = null;
        LocalDateTime endDate = LocalDateTime.now();


        switch (sprint.toLowerCase()) {
            case "current":
                startDate = LocalDateTime.now().minusWeeks(3);
                break;
            case "last":
                startDate = LocalDateTime.now().minusWeeks(6);
                endDate = LocalDateTime.now().minusWeeks(3);
                break;
            case "none":
            default:
                startDate = null;
                endDate = null;
                break;
        }

        List<Object[]> userAverages;
        List<Object[]> teamAverages;

        if (startDate != null) {
            userAverages = happinessSurveyRepository.findDailyAveragesByUserIdAndDateRange(userId, startDate, endDate);
            teamAverages = insightsRepository.findTeamDailyAveragesExcludingUserAndDateRange(teamId, userId, startDate, endDate);


        } else {
            userAverages = happinessSurveyRepository.findDailyAveragesByUserId(userId);
            teamAverages = insightsRepository.findTeamDailyAveragesExcludingUser(teamId, userId);
        }

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
    public List<WorkKindInsightDTO> getWorkKindInsightsByUserId(@ValidateUserIdParam Integer userId) {
        List<Object[]> results = insightsRepository.findWorkKindHappinessByUserId(userId);

        return results.stream()
                .map(row -> workKindInsightMapper.toDTO(
                        (Integer) row[0],
                        (Integer) row[1],
                        (String) row[2],
                        (Double) row[3],
                        (Long) row[4]
                ))
                .collect(Collectors.toList());
    }
}
