package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.annotations.ValidateUserIdParam;
import ch.fhnw.deardevbackend.dto.insights.*;
import ch.fhnw.deardevbackend.entities.SprintConfig;
import ch.fhnw.deardevbackend.mapper.HappinessInsightMapper;
import ch.fhnw.deardevbackend.mapper.WorkKindInsightMapper;
import ch.fhnw.deardevbackend.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;

@Service
public class InsightsService {

    private final InsightsRepository insightsRepository;
    private final HappinessSurveyRepository happinessSurveyRepository;
    private final HappinessInsightMapper happinessInsightMapper;
    private final WorkKindInsightMapper workKindInsightMapper;
    private final SprintConfigRepository sprintConfigRepository;


    public InsightsService(InsightsRepository insightsRepository,
                           HappinessSurveyRepository happinessSurveyRepository,
                           HappinessInsightMapper happinessInsightMapper,
                           WorkKindInsightMapper workKindInsightMapper,
                           SprintConfigRepository sprintConfigRepository) {

        this.insightsRepository = insightsRepository;
        this.happinessSurveyRepository = happinessSurveyRepository;
        this.happinessInsightMapper = happinessInsightMapper;
        this.workKindInsightMapper = workKindInsightMapper;
        this.sprintConfigRepository = sprintConfigRepository;
    }

    @Transactional(readOnly = true)
    public InsightDTO getInsightsByTeamAndSprint(@ValidateUserIdParam Integer userId, Integer teamId, Integer sprintId) {
        List<HappinessInsightDTO> happinessInsights = getHappinessInsightsByTeam(userId, teamId, sprintId);
        List<WorkKindInsightDTO> workKindInsights = getWorkKindInsightsByUserAndTeam(userId, teamId, sprintId);
        List<EmotionInsightDTO> emotionInsights = getEmotionInsightsByUserAndTeam(userId, teamId, sprintId);
        List<WorkKindCountPerDayInsightDTO> workKindCountPerDayInsights = calculateAverageHappinessPerWorkKindCount(userId, sprintId, teamId);

        double userAverageHappiness = calculateAverageHappiness(happinessInsights, true);
        double teamAverageHappiness = calculateAverageHappiness(happinessInsights, false);

        return new InsightDTO(happinessInsights, workKindInsights, emotionInsights, workKindCountPerDayInsights, userAverageHappiness, teamAverageHappiness);
    }


    ///// Happiness insights

    @Transactional(readOnly = true)
    public List<HappinessInsightDTO> getHappinessInsightsByTeam(@ValidateUserIdParam Integer userId, Integer teamId, Integer sprintId) {
        List<Object[]> userAverages;
        List<Object[]> teamAverages;

        if (sprintId == 0) {
            userAverages = happinessSurveyRepository.findDailyAveragesByUserId(userId);
            teamAverages = insightsRepository.findTeamDailyAverages(teamId);
        } else {
            // todo make sure the sprint belongs to the team
            SprintConfig sprintConfig = sprintConfigRepository.findById(sprintId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid sprint ID: " + sprintId));

            LocalDateTime startDate = sprintConfig.getStartDate().atStartOfDay();
            LocalDateTime endDate = sprintConfig.getEndDate().atTime(23, 59, 59);

            userAverages = happinessSurveyRepository.findDailyAveragesByUserIdAndDateRange(userId, startDate, endDate);
            teamAverages = insightsRepository.findTeamDailyAveragesAndDateRange(teamId, startDate, endDate);
        }

        Map<String, Double> teamAveragesMap = teamAverages.stream()
                .collect(Collectors.toMap(
                        row -> row[0].toString(),
                        row -> ((Number) row[1]).doubleValue()
                ));

        return userAverages.stream()
                .map(userAvg -> {
                    String day = userAvg[0].toString();
                    int userAverage = ((Number) userAvg[1]).intValue();  // Convert user average to Integer
                    int teamAverage = teamAveragesMap.getOrDefault(day, 0.0).intValue();  // Convert team average to Integer
                    return happinessInsightMapper.toDTO(day, (double) userAverage, (double) teamAverage);
                })
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(h -> LocalDate.parse(h.getDay(), DateTimeFormatter.ISO_DATE)))
                .collect(Collectors.toList());
    }

    private double calculateAverageHappiness(List<HappinessInsightDTO> insights, boolean isUser) {
        return insights.stream()
                .mapToDouble(insight -> isUser ? insight.getUserAverage() : insight.getTeamAverage())
                .average()
                .orElse(0.0);
    }

    ///// Workkind insights

    @Transactional(readOnly = true)
    public List<WorkKindInsightDTO> getWorkKindInsightsByUserAndTeam(@ValidateUserIdParam Integer userId, Integer teamId, Integer sprintId) {
        List<Object[]> userWorkKinds;
        List<Object[]> teamWorkKinds;

        if (sprintId == 0) {
            userWorkKinds = insightsRepository.findTopWorkKindsByUser(userId, teamId);
            teamWorkKinds = insightsRepository.findTopWorkKindsByTeam(teamId);
        } else {
            SprintConfig sprintConfig = sprintConfigRepository.findById(sprintId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid sprint ID: " + sprintId));

            LocalDateTime startDate = sprintConfig.getStartDate().atStartOfDay();
            LocalDateTime endDate = sprintConfig.getEndDate().atTime(23, 59, 59);

            userWorkKinds = insightsRepository.findTopWorkKindsByUserAndDateRange(userId, teamId, startDate, endDate);
            teamWorkKinds = insightsRepository.findTopWorkKindsByTeamAndDateRange(teamId, startDate, endDate);
        }

        List<WorkKindInsightDTO> userWorkKindInsights = userWorkKinds.stream()
                .map(workKind -> workKindInsightMapper.toUserDTO(
                        (Integer) workKind[0],
                        (String) workKind[1],
                        ((Number) workKind[2]).intValue(),
                        ((Number) workKind[3]).intValue()))
                .collect(Collectors.toList());

        List<WorkKindInsightDTO> teamWorkKindInsights = teamWorkKinds.stream()
                .map(workKind -> workKindInsightMapper.toTeamDTO(
                        (Integer) workKind[0],
                        (String) workKind[1],
                        ((Number) workKind[2]).intValue(),
                        ((Number) workKind[3]).intValue()))
                .collect(Collectors.toList());

        return mergeUserAndTeamWorkKindInsights(userWorkKindInsights, teamWorkKindInsights);
    }

    private List<WorkKindInsightDTO> mergeUserAndTeamWorkKindInsights(List<WorkKindInsightDTO> userWorkKindInsights, List<WorkKindInsightDTO> teamWorkKindInsights) {
        Map<Integer, WorkKindInsightDTO> merged = new HashMap<>();

        for (WorkKindInsightDTO userInsight : userWorkKindInsights) {
            if (userInsight != null) {
                merged.put(userInsight.getWorkKindId(), userInsight);
            }
        }

        for (WorkKindInsightDTO teamInsight : teamWorkKindInsights) {
            if (teamInsight != null) {
                merged.merge(teamInsight.getWorkKindId(), teamInsight, (userDto, teamDto) -> {
                    return new WorkKindInsightDTO(
                            userDto.getWorkKindId(),
                            userDto.getWorkKindName(),
                            userDto.getUserAverage() != null ? userDto.getUserAverage() : 0,
                            userDto.getUserCount() != null ? userDto.getUserCount() : 0,
                            teamDto.getTeamAverage() != null ? teamDto.getTeamAverage() : 0,
                            teamDto.getTeamCount() != null ? teamDto.getTeamCount() : 0
                    );
                });
            }
        }

        return merged.values().stream()
                .sorted(Comparator.comparingLong((WorkKindInsightDTO dto) -> dto.getUserCount() != null ? dto.getUserCount() : 0L).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }


    ///// Emotion insights

    @Transactional(readOnly = true)
    public List<EmotionInsightDTO> getEmotionInsightsByUserAndTeam(Integer userId, Integer teamId, Integer sprintId) {
        List<Object[]> userEmotions;
        List<Object[]> teamEmotions;

        if (sprintId == 0) {
            userEmotions = insightsRepository.findTopEmotionsByUser(userId);
            teamEmotions = insightsRepository.findTopEmotionsByTeam(teamId);
        } else {
            SprintConfig sprintConfig = sprintConfigRepository.findById(sprintId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid sprint ID: " + sprintId));

            LocalDateTime startDate = sprintConfig.getStartDate().atStartOfDay();
            LocalDateTime endDate = sprintConfig.getEndDate().atTime(23, 59, 59);

            userEmotions = insightsRepository.findTopEmotionsByUserAndDateRange(userId, startDate, endDate);
            teamEmotions = insightsRepository.findTopEmotionsByTeamAndDateRange(teamId, startDate, endDate);
        }

        List<EmotionInsightDTO> userEmotionInsights = userEmotions.stream()
                .map(emotion -> new EmotionInsightDTO(
                        (Integer) emotion[0],
                        (String) emotion[1],
                        (Long) emotion[2],
                        0L))
                .collect(Collectors.toList());

        List<EmotionInsightDTO> teamEmotionInsights = teamEmotions.stream()
                .map(emotion -> new EmotionInsightDTO(
                        (Integer) emotion[0],
                        (String) emotion[1],
                        0L,
                        (Long) emotion[2]))
                .collect(Collectors.toList());

        return mergeUserAndTeamEmotionInsights(userEmotionInsights, teamEmotionInsights);
    }

    private List<EmotionInsightDTO> mergeUserAndTeamEmotionInsights(List<EmotionInsightDTO> userEmotionInsights, List<EmotionInsightDTO> teamEmotionInsights) {
        Map<Integer, EmotionInsightDTO> merged = new HashMap<>();

        for (EmotionInsightDTO userInsight : userEmotionInsights) {
            merged.put(userInsight.getEmotionId(), userInsight);
        }

        for (EmotionInsightDTO teamInsight : teamEmotionInsights) {
            merged.merge(teamInsight.getEmotionId(), teamInsight, (userDto, teamDto) -> new EmotionInsightDTO(
                    userDto.getEmotionId(),
                    userDto.getEmotionName(),
                    userDto.getUserCount(),
                    teamDto.getTeamCount()
            ));
        }

        return merged.values().stream()
                .sorted(Comparator.comparingLong((EmotionInsightDTO dto) -> dto.getUserCount() != null ? dto.getUserCount() : 0L).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }


    /////  Workkind count per day vs. Average happiness

    public List<WorkKindCountPerDayInsightDTO> calculateAverageHappinessPerWorkKindCount(Integer userId, Integer sprintId, Integer teamId) {
        List<Object[]> workKindData;
        List<Object[]> teamWorkKindData;

        if (sprintId == 0) {
            workKindData = insightsRepository.findWorkKindCountPerDayForUserNoDateRange(userId, teamId);
            teamWorkKindData = insightsRepository.findTeamWorkKindCountPerDayNoDateRange(teamId);
        } else {
            SprintConfig sprintConfig = sprintConfigRepository.findById(sprintId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid sprint ID: " + sprintId));

            LocalDateTime startDate = sprintConfig.getStartDate().atStartOfDay();
            LocalDateTime endDate = sprintConfig.getEndDate().atTime(23, 59, 59);

            workKindData = insightsRepository.findWorkKindCountPerDayForUserWithDateRange(userId, teamId, startDate, endDate);
            teamWorkKindData = insightsRepository.findTeamWorkKindCountPerDayWithDateRange(teamId, startDate, endDate);
        }

        Map<Integer, List<Double>> userHappinessScoresMap = new HashMap<>();
        Map<Integer, Double> teamHappinessScoresMap = new HashMap<>();

        for (Object[] entry : workKindData) {
            Object dateObj = entry[0];
            int workKindCount = ((Number) entry[1]).intValue();

            LocalDate dateTime = convertToLocalDate(dateObj);

            Double averageHappinessScore = happinessSurveyRepository.findAverageScoreByUserIdAndDate(userId, dateTime);

            if (averageHappinessScore != null) {
                userHappinessScoresMap.computeIfAbsent(Math.toIntExact(workKindCount), k -> new ArrayList<>()).add(averageHappinessScore);
            }
        }
        // Calculate daily average happiness scores for the team
        for (Object[] entry : teamWorkKindData) {
            Integer workKindCount = ((Number) entry[1]).intValue();
            Double teamAverageHappiness = ((Number) entry[2]).doubleValue();
            teamHappinessScoresMap.put(workKindCount, teamAverageHappiness);
        }

        List<WorkKindCountPerDayInsightDTO> result = new ArrayList<>();
        for (Map.Entry<Integer, List<Double>> entry : userHappinessScoresMap.entrySet()) {
            Integer workKindCount = entry.getKey();
            List<Double> scores = entry.getValue();

            Integer userAverageHappiness = (int) Math.round(scores.stream().mapToDouble(Double::doubleValue).average().orElse(0.0));
            Integer teamAverageHappiness = (int) Math.round(teamHappinessScoresMap.getOrDefault(workKindCount, 0.0));

            WorkKindCountPerDayInsightDTO dto = new WorkKindCountPerDayInsightDTO(workKindCount, userAverageHappiness, teamAverageHappiness);
            result.add(dto);
        }
        return result;
    }

    private LocalDate convertToLocalDate(Object dateObj) {
        return switch (dateObj) {
            case LocalDate localDate -> localDate;
            case LocalDateTime localDateTime -> localDateTime.toLocalDate();
            case java.sql.Date date -> date.toLocalDate();
            case java.sql.Timestamp timestamp -> timestamp.toLocalDateTime().toLocalDate();
            case Instant instant -> instant.atZone(ZoneId.systemDefault()).toLocalDate();
            case null, default -> {
                assert dateObj != null;
                throw new IllegalArgumentException("Unsupported date type: " + dateObj.getClass().getName());
            }
        };
    }


}
