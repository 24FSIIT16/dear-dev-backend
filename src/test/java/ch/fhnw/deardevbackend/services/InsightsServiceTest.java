package ch.fhnw.deardevbackend.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ch.fhnw.deardevbackend.dto.insights.*;
import ch.fhnw.deardevbackend.entities.SprintConfig;
import ch.fhnw.deardevbackend.mapper.HappinessInsightMapper;
import ch.fhnw.deardevbackend.mapper.WorkKindInsightMapper;
import ch.fhnw.deardevbackend.repositories.InsightsRepository;
import ch.fhnw.deardevbackend.repositories.HappinessSurveyRepository;
import ch.fhnw.deardevbackend.repositories.SprintConfigRepository;
import ch.fhnw.deardevbackend.repositories.WorkKindSurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class InsightsServiceTest {

    @InjectMocks
    private InsightsService insightsService;

    @Mock
    private InsightsRepository insightsRepository;

    @Mock
    private HappinessSurveyRepository happinessSurveyRepository;

    @Mock
    private WorkKindSurveyRepository workKindSurveyRepository;

    @Mock
    private HappinessInsightMapper happinessInsightMapper;

    @Mock
    private WorkKindInsightMapper workKindInsightMapper;

    @Mock
    private SprintConfigRepository sprintConfigRepository;

    private Integer userId;
    private Integer teamId;
    private Integer sprintId;
    private SprintConfig sprintConfig;
    List<EmotionInsightDTO> userEmotionInsights = Arrays.asList(
            new EmotionInsightDTO(1, "Happy", 5L, 0L)
    );
    List<EmotionInsightDTO> teamEmotionInsights = Arrays.asList(
            new EmotionInsightDTO(1, "Happy", 0L, 10L)
    );

    @BeforeEach
    void setUp() {
        userId = 1;
        teamId = 1;
        sprintId = 1;

        sprintConfig = new SprintConfig();
        sprintConfig.setStartDate(LocalDateTime.now().minusDays(14).toLocalDate());
        sprintConfig.setEndDate(LocalDateTime.now().toLocalDate());
    }

    @Test
    void getInsightsByTeamAndSprint() {
        when(sprintConfigRepository.findById(sprintId)).thenReturn(Optional.of(sprintConfig));
        when(happinessSurveyRepository.findDailyAveragesByUserIdAndDateRange(eq(userId), any(), any()))
                .thenReturn(Collections.singletonList(new Object[]{"2024-07-27", 5.0}));
        when(insightsRepository.findTeamDailyAveragesAndDateRange(eq(teamId), any(), any()))
                .thenReturn(Collections.singletonList(new Object[]{"2024-07-27", 4.0}));

        when(happinessInsightMapper.toDTO("2024-07-27", 5.0, 4.0))
                .thenReturn(new HappinessInsightDTO("2024-07-27", 5, 4));

        InsightDTO result = insightsService.getInsightsByTeamAndSprint(userId, teamId, sprintId);

        assertNotNull(result);
        assertNotNull(result.getHappinessInsights());
        assertEquals(1, result.getHappinessInsights().size());
        verify(happinessSurveyRepository, times(1)).findDailyAveragesByUserIdAndDateRange(eq(userId), any(), any());
        verify(insightsRepository, times(1)).findTeamDailyAveragesAndDateRange(eq(teamId), any(), any());
    }

    @Test
    void getHappinessInsightsByTeam() {
        when(sprintConfigRepository.findById(sprintId)).thenReturn(Optional.of(sprintConfig));
        when(happinessSurveyRepository.findDailyAveragesByUserIdAndDateRange(eq(userId), any(), any()))
                .thenReturn(Collections.singletonList(new Object[]{"2024-07-27", 5.0}));
        when(insightsRepository.findTeamDailyAveragesAndDateRange(eq(teamId), any(), any()))
                .thenReturn(Collections.singletonList(new Object[]{"2024-07-27", 4.0}));

        when(happinessInsightMapper.toDTO("2024-07-27", 5.0, 4.0))
                .thenReturn(new HappinessInsightDTO("2024-07-27", 5, 5));

        List<HappinessInsightDTO> result = insightsService.getHappinessInsightsByTeam(userId, teamId, sprintId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(happinessSurveyRepository, times(1)).findDailyAveragesByUserIdAndDateRange(eq(userId), any(), any());
        verify(insightsRepository, times(1)).findTeamDailyAveragesAndDateRange(eq(teamId), any(), any());
    }

    @Test
    void getWorkKindInsightsByUserAndTeam() {
        when(sprintConfigRepository.findById(sprintId)).thenReturn(Optional.of(sprintConfig));
        when(insightsRepository.findTopWorkKindsByUserAndDateRange(eq(userId), eq(teamId), any(), any()))
                .thenReturn(Collections.singletonList(new Object[]{1, "Development", 4.5, 10L}));
        when(insightsRepository.findTopWorkKindsByTeamAndDateRange(eq(teamId), any(), any()))
                .thenReturn(Collections.singletonList(new Object[]{1, "Development", 4.0, 8L}));

        when(workKindInsightMapper.toUserDTO(1, "Development", 4, 10))
                .thenReturn(new WorkKindInsightDTO(1, "Development", 4, 10, 0, 0));
        when(workKindInsightMapper.toTeamDTO(1, "Development", 4, 8))
                .thenReturn(new WorkKindInsightDTO(1, "Development", 0, 0, 4, 8));

        List<WorkKindInsightDTO> result = insightsService.getWorkKindInsightsByUserAndTeam(userId, teamId, sprintId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(insightsRepository, times(1)).findTopWorkKindsByUserAndDateRange(eq(userId), eq(teamId), any(), any());
        verify(insightsRepository, times(1)).findTopWorkKindsByTeamAndDateRange(eq(teamId), any(), any());
    }

    @Test
    void getEmotionInsightsByUserAndTeam() {
        when(sprintConfigRepository.findById(sprintId)).thenReturn(Optional.of(sprintConfig));
        when(insightsRepository.findTopEmotionsByUserAndDateRange(eq(userId), any(), any()))
                .thenReturn(Collections.singletonList(new Object[]{1, "Happy", 5L}));
        when(insightsRepository.findTopEmotionsByTeamAndDateRange(eq(teamId), any(), any()))
                .thenReturn(Collections.singletonList(new Object[]{1, "Happy", 10L}));

        List<EmotionInsightDTO> result = insightsService.getEmotionInsightsByUserAndTeam(userId, teamId, sprintId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(insightsRepository, times(1)).findTopEmotionsByUserAndDateRange(eq(userId), any(), any());
        verify(insightsRepository, times(1)).findTopEmotionsByTeamAndDateRange(eq(teamId), any(), any());
    }

    @Test
    void calculateAverageHappinessPerWorkKindCount() {
        when(sprintConfigRepository.findById(sprintId)).thenReturn(Optional.of(sprintConfig));

        LocalDate mockDate = LocalDate.of(2024, 7, 27);

        when(insightsRepository.findWorkKindCountPerDayForUserWithDateRange(eq(userId), eq(teamId), any(), any()))
                .thenReturn(Collections.singletonList(new Object[]{mockDate, 3}));
        when(insightsRepository.findTeamWorkKindCountPerDayWithDateRange(eq(teamId), any(), any()))
                .thenReturn(Collections.singletonList(new Object[]{mockDate, 3, 5.0}));

        when(happinessSurveyRepository.findAverageScoreByUserIdAndDate(eq(userId), eq(mockDate)))
                .thenReturn(5.0);

        List<WorkKindCountPerDayInsightDTO> result = insightsService.calculateAverageHappinessPerWorkKindCount(userId, sprintId, teamId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(3, result.get(0).getWorkKindCount());
        assertEquals(5, result.get(0).getUserAverageHappiness());
        assertEquals(5, result.get(0).getTeamAverageHappiness());

        verify(insightsRepository, times(1)).findWorkKindCountPerDayForUserWithDateRange(eq(userId), eq(teamId), any(), any());
        verify(insightsRepository, times(1)).findTeamWorkKindCountPerDayWithDateRange(eq(teamId), any(), any());
        verify(happinessSurveyRepository, times(1)).findAverageScoreByUserIdAndDate(eq(userId), eq(mockDate));
    }

}
