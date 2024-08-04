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
            Optional<SprintConfig> sprintConfigOptional = sprintConfigRepository.findById(sprintId);
            SprintConfig sprintConfig = sprintConfigOptional.get();

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
                    double userAverage = ((Number) userAvg[1]).doubleValue();
                    double teamAverage = teamAveragesMap.getOrDefault(day, 0.0);
                    return happinessInsightMapper.toDTO(day, userAverage, teamAverage);
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
            Optional<SprintConfig> sprintConfigOptional = sprintConfigRepository.findById(sprintId);
            SprintConfig sprintConfig = sprintConfigOptional.get();

            LocalDateTime startDate = sprintConfig.getStartDate().atStartOfDay();
            LocalDateTime endDate = sprintConfig.getEndDate().atTime(23, 59, 59);

            userWorkKinds = insightsRepository.findTopWorkKindsByUserAndDateRange(userId, teamId, startDate, endDate);
            teamWorkKinds = insightsRepository.findTopWorkKindsByTeamAndDateRange(teamId, startDate, endDate);
        }

        List<WorkKindInsightDTO> userWorkKindInsights = userWorkKinds.stream()
                .map(workKind -> workKindInsightMapper.toUserDTO(
                        (Integer) workKind[0],
                        (String) workKind[1],
                        (Double) workKind[2],
                        (Long) workKind[3]))
                .collect(Collectors.toList());

        List<WorkKindInsightDTO> teamWorkKindInsights = teamWorkKinds.stream()
                .map(workKind -> workKindInsightMapper.toTeamDTO(
                        (Integer) workKind[0],
                        (String) workKind[1],
                        (Double) workKind[2],
                        (Long) workKind[3]))
                .collect(Collectors.toList());

        // Merge user and team insights into a single list
        return mergeUserAndTeamWorkKindInsights(userWorkKindInsights, teamWorkKindInsights);
    }

    // Top 5 work kinds by user and team
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
                            userDto.getUserAverage() != null ? userDto.getUserAverage() : 0.0,
                            userDto.getUserCount() != null ? userDto.getUserCount() : 0L,
                            teamDto.getTeamAverage() != null ? teamDto.getTeamAverage() : 0.0,
                            teamDto.getTeamCount() != null ? teamDto.getTeamCount() : 0L
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
            Optional<SprintConfig> sprintConfigOptional = sprintConfigRepository.findById(sprintId);
            SprintConfig sprintConfig = sprintConfigOptional.get();

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
            workKindData = insightsRepository.findWorkKindCountPerDayForUserNoDateRange(userId);
            teamWorkKindData = insightsRepository.findTeamWorkKindCountPerDayNoDateRange(teamId);
        } else {
            Optional<SprintConfig> sprintConfigOptional = sprintConfigRepository.findById(sprintId);
            SprintConfig sprintConfig = sprintConfigOptional.get();

            LocalDateTime startDate = sprintConfig.getStartDate().atStartOfDay();
            LocalDateTime endDate = sprintConfig.getEndDate().atTime(23, 59, 59);

            workKindData = insightsRepository.findWorkKindCountPerDayForUserWithDateRange(userId, startDate, endDate);
            teamWorkKindData = insightsRepository.findTeamWorkKindCountPerDayWithDateRange(teamId, startDate, endDate);
        }

        // Map to store workKindCount -> list of daily average happiness scores
        Map<Integer, List<Double>> userHappinessScoresMap = new HashMap<>();
        Map<Integer, Double> teamHappinessScoresMap = new HashMap<>();


        // Calculate daily average happiness scores
        for (Object[] entry : workKindData) {
            Object dateObj = entry[0];
            Long workKindCount = ((Number) entry[1]).longValue();

            LocalDate dateTime = convertToLocalDate(dateObj);

            // Get the average happiness score for the given day
            Double averageHappinessScore = happinessSurveyRepository.findAverageScoreByUserIdAndDate(userId, dateTime);

            if (averageHappinessScore != null) {
                // Add the score to the map
                userHappinessScoresMap.computeIfAbsent(Math.toIntExact(workKindCount), k -> new ArrayList<>()).add(averageHappinessScore);
            }
        }
        // Calculate daily average happiness scores for the team
        for (Object[] entry : teamWorkKindData) {
            Long workKindCount = ((Number) entry[1]).longValue();
            Double teamAverageHappiness = ((Number) entry[2]).doubleValue();
            teamHappinessScoresMap.put(Math.toIntExact(workKindCount), teamAverageHappiness);
        }

        // Calculate the average happiness score for all days with the same work kind count
        List<WorkKindCountPerDayInsightDTO> result = new ArrayList<>();
        for (Map.Entry<Integer, List<Double>> entry : userHappinessScoresMap.entrySet()) {
            Integer workKindCount = entry.getKey();
            List<Double> scores = entry.getValue();

            double userAverageHappiness = scores.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            double teamAverageHappiness = teamHappinessScoresMap.getOrDefault(workKindCount, 0.0);

            // Create DTO
            WorkKindCountPerDayInsightDTO dto = new WorkKindCountPerDayInsightDTO(workKindCount, userAverageHappiness, teamAverageHappiness);
            result.add(dto);
        }

        return result;
    }

    // convert date object to LocalDate
    private LocalDate convertToLocalDate(Object dateObj) {
        if (dateObj instanceof LocalDate) {
            return (LocalDate) dateObj;
        } else if (dateObj instanceof LocalDateTime) {
            return ((LocalDateTime) dateObj).toLocalDate();
        } else if (dateObj instanceof java.sql.Date) {
            return ((java.sql.Date) dateObj).toLocalDate();
        } else if (dateObj instanceof java.sql.Timestamp) {
            return ((java.sql.Timestamp) dateObj).toLocalDateTime().toLocalDate();
        } else if (dateObj instanceof Instant) {
            return ((Instant) dateObj).atZone(ZoneId.systemDefault()).toLocalDate();
        } else {
            throw new IllegalArgumentException("Unsupported date type: " + dateObj.getClass().getName());
        }
    }


}
