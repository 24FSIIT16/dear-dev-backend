package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.annotations.ValidateUserIdParam;
import ch.fhnw.deardevbackend.dto.*;
import ch.fhnw.deardevbackend.mapper.HappinessInsightMapper;
import ch.fhnw.deardevbackend.mapper.WorkKindInsightMapper;
import ch.fhnw.deardevbackend.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;

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

        double userAverageHappiness = calculateAverageHappiness(happinessInsights, true);
        double teamAverageHappiness = calculateAverageHappiness(happinessInsights, false);

        return new InsightDTO(happinessInsights, workKindInsights, userAverageHappiness, teamAverageHappiness);
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
                .sorted(Comparator.comparing(h -> LocalDate.parse(h.getDay(), DateTimeFormatter.ISO_DATE)))
                .collect(Collectors.toList());

    }

    private double calculateAverageHappiness(List<HappinessInsightDTO> insights, boolean isUser) {
        return insights.stream()
                .mapToDouble(insight -> isUser ? insight.getUserAverage() : insight.getTeamAverage())
                .average()
                .orElse(0.0);
    }

    // todo later asap structure is defined
    private WorkKindInsightDTO findMatchingWorkKindInsight(HappinessInsightDTO happinessInsightDTO, List<WorkKindInsightDTO> workKindInsights) {
        return workKindInsights.stream()
                // .filter(workKindInsightDTO -> workKindInsightDTO.getTeamId().equals(happinessInsightDTO.getTeamId()))
                .findFirst()
                .orElse(null);
    }


    // todo remove
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
