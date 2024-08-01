package ch.fhnw.deardevbackend.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ch.fhnw.deardevbackend.dto.insights.HappinessInsightDTO;
import ch.fhnw.deardevbackend.dto.insights.InsightDTO;
import ch.fhnw.deardevbackend.dto.insights.WorkKindInsightDTO;
import ch.fhnw.deardevbackend.mapper.HappinessInsightMapper;
import ch.fhnw.deardevbackend.mapper.WorkKindInsightMapper;
import ch.fhnw.deardevbackend.repositories.InsightsRepository;
import ch.fhnw.deardevbackend.repositories.HappinessSurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class InsightsServiceTest {

    @InjectMocks
    private InsightsService insightsService;

    @Mock
    private InsightsRepository insightsRepository;

    @Mock
    private HappinessSurveyRepository happinessSurveyRepository;

    @Mock
    private HappinessInsightMapper happinessInsightMapper;

    @Mock
    private WorkKindInsightMapper workKindInsightMapper;

    private Integer userId;
    private List<Object[]> userAverages;
    private List<Object[]> teamAverages;

    private List<Object[]> userWorkKinds;
    private List<Object[]> teamWorkKinds;
    private WorkKindInsightDTO workKindInsightDTO1;
    private WorkKindInsightDTO workKindInsightDTO2;
    private WorkKindInsightDTO workKindInsightDTO3;
    private WorkKindInsightDTO workKindInsightDTO4;

    @BeforeEach
    void setUp() {
        userId = 1;

        userAverages = Arrays.asList(
                new Object[]{"2024-07-27", 5.0},
                new Object[]{"2024-07-28", 5.0},
                new Object[]{"2024-07-29", 6.0}
        );
        teamAverages = Arrays.asList(
                new Object[]{"2024-07-27", 4.0},
                new Object[]{"2024-07-28", 2.0},
                new Object[]{"2024-07-29", 3.0}
        );

        userWorkKinds = Arrays.asList(
                new Object[]{1, "Development", 4.5, 10L},
                new Object[]{2, "Testing", 3.5, 5L}
        );
        teamWorkKinds = Arrays.asList(
                new Object[]{1, "Development", 4.0, 8L},
                new Object[]{2, "Testing", 3.0, 6L}
        );

        workKindInsightDTO1 = new WorkKindInsightDTO(1, "Development", 4.5, 10L, 0.0, 0L);
        workKindInsightDTO2 = new WorkKindInsightDTO(2, "Testing", 3.5, 5L, 0.0, 0L);
        workKindInsightDTO3 = new WorkKindInsightDTO(1, "Development", 0.0, 0L, 4.0, 8L);
        workKindInsightDTO4 = new WorkKindInsightDTO(2, "Testing", 0.0, 0L, 3.0, 6L);
    }

    @Test
    void getInsightsByTeamAndSprint() {
        Integer teamId = 1;
        String sprint = "current";

        when(happinessSurveyRepository.findDailyAveragesByUserIdAndDateRange(eq(userId), any(), any())).thenReturn(userAverages);
        when(insightsRepository.findTeamDailyAveragesExcludingUserAndDateRange(eq(teamId), eq(userId), any(), any())).thenReturn(teamAverages);
        when(happinessInsightMapper.toDTO("2024-07-27", 5.0, 4.0)).thenReturn(new HappinessInsightDTO("2024-07-27", 5.0, 4.0));
        when(happinessInsightMapper.toDTO("2024-07-28", 5.0, 2.0)).thenReturn(new HappinessInsightDTO("2024-07-28", 5.0, 2.0));
        when(happinessInsightMapper.toDTO("2024-07-29", 6.0, 3.0)).thenReturn(new HappinessInsightDTO("2024-07-29", 6.0, 3.0));

        when(insightsRepository.findTopWorkKindsByUserAndDateRange(eq(userId), any(), any())).thenReturn(userWorkKinds);
        when(insightsRepository.findTopWorkKindsByTeamAndDateRange(eq(teamId), eq(userId), any(), any())).thenReturn(teamWorkKinds);
        when(workKindInsightMapper.toUserDTO(1, "Development", 4.5, 10L)).thenReturn(workKindInsightDTO1);
        when(workKindInsightMapper.toUserDTO(2, "Testing", 3.5, 5L)).thenReturn(workKindInsightDTO2);
        when(workKindInsightMapper.toTeamDTO(1, "Development", 4.0, 8L)).thenReturn(workKindInsightDTO3);
        when(workKindInsightMapper.toTeamDTO(2, "Testing", 3.0, 6L)).thenReturn(workKindInsightDTO4);

        InsightDTO result = insightsService.getInsightsByTeamAndSprint(userId, teamId, sprint);

        assertNotNull(result);
        assertNotNull(result.getHappinessInsights());
        assertNotNull(result.getWorkKindInsights());
        assertEquals(3, result.getHappinessInsights().size());
        assertEquals(2, result.getWorkKindInsights().size());

        verify(happinessSurveyRepository, times(1)).findDailyAveragesByUserIdAndDateRange(eq(userId), any(), any());
        verify(insightsRepository, times(1)).findTeamDailyAveragesExcludingUserAndDateRange(eq(teamId), eq(userId), any(), any());
        verify(happinessInsightMapper, times(3)).toDTO(anyString(), anyDouble(), anyDouble());

        verify(insightsRepository, times(1)).findTopWorkKindsByUserAndDateRange(eq(userId), any(), any());
        verify(insightsRepository, times(1)).findTopWorkKindsByTeamAndDateRange(eq(teamId), eq(userId), any(), any());
        verify(workKindInsightMapper, times(2)).toUserDTO(anyInt(), anyString(), anyDouble(), anyLong());
        verify(workKindInsightMapper, times(2)).toTeamDTO(anyInt(), anyString(), anyDouble(), anyLong());
    }


    @Test
    void getHappinessInsightsByTeam() {
        Integer teamId = 1;
        String sprint = "current";

        when(happinessSurveyRepository.findDailyAveragesByUserIdAndDateRange(eq(userId), any(), any())).thenReturn(userAverages);
        when(insightsRepository.findTeamDailyAveragesExcludingUserAndDateRange(eq(teamId), eq(userId), any(), any())).thenReturn(teamAverages);
        when(happinessInsightMapper.toDTO("2024-07-27", 5.0, 4.0)).thenReturn(new HappinessInsightDTO("2024-07-27", 5.0, 4.0));
        when(happinessInsightMapper.toDTO("2024-07-28", 5.0, 2.0)).thenReturn(new HappinessInsightDTO("2024-07-28", 5.0, 2.0));
        when(happinessInsightMapper.toDTO("2024-07-29", 6.0, 3.0)).thenReturn(new HappinessInsightDTO("2024-07-29", 6.0, 3.0));

        List<HappinessInsightDTO> result = insightsService.getHappinessInsightsByTeam(userId, teamId, sprint);

        assertNotNull(result);
        assertEquals(3, result.size());

        assertEquals("2024-07-27", result.getFirst().getDay());
        assertEquals(5.0, result.getFirst().getUserAverage());
        assertEquals(4.0, result.getFirst().getTeamAverage());

        assertEquals("2024-07-28", result.get(1).getDay());
        assertEquals(5.0, result.get(1).getUserAverage());
        assertEquals(2.0, result.get(1).getTeamAverage());

        assertEquals("2024-07-29", result.get(2).getDay());
        assertEquals(6.0, result.get(2).getUserAverage());
        assertEquals(3.0, result.get(2).getTeamAverage());

        verify(happinessSurveyRepository, times(1)).findDailyAveragesByUserIdAndDateRange(eq(userId), any(), any());
        verify(insightsRepository, times(1)).findTeamDailyAveragesExcludingUserAndDateRange(eq(teamId), eq(userId), any(), any());
        verify(happinessInsightMapper, times(3)).toDTO(anyString(), anyDouble(), anyDouble());
    }

    @Test
    void getInsightsByTeamAndSprint_noData() {
        Integer teamId = 1;
        String sprint = "current";

        when(happinessSurveyRepository.findDailyAveragesByUserIdAndDateRange(eq(userId), any(), any())).thenReturn(Collections.emptyList());
        when(insightsRepository.findTeamDailyAveragesExcludingUserAndDateRange(eq(teamId), eq(userId), any(), any())).thenReturn(Collections.emptyList());
        when(insightsRepository.findTopWorkKindsByUserAndDateRange(eq(userId), any(), any())).thenReturn(Collections.emptyList());
        when(insightsRepository.findTopWorkKindsByTeamAndDateRange(eq(teamId), eq(userId), any(), any())).thenReturn(Collections.emptyList());

        InsightDTO result = insightsService.getInsightsByTeamAndSprint(userId, teamId, sprint);

        assertNotNull(result);
        assertTrue(result.getHappinessInsights().isEmpty());
        assertTrue(result.getWorkKindInsights().isEmpty());

        verify(happinessSurveyRepository, times(1)).findDailyAveragesByUserIdAndDateRange(eq(userId), any(), any());
        verify(insightsRepository, times(1)).findTeamDailyAveragesExcludingUserAndDateRange(eq(teamId), eq(userId), any(), any());
        verify(insightsRepository, times(1)).findTopWorkKindsByUserAndDateRange(eq(userId), any(), any());
        verify(insightsRepository, times(1)).findTopWorkKindsByTeamAndDateRange(eq(teamId), eq(userId), any(), any());
    }

    @Test
    void getInsightsByTeamAndSprint_multipleTeams() {
        Integer teamId1 = 1;
        Integer teamId2 = 2;
        String sprint = "current";

        List<Object[]> userAveragesTeam1 = Arrays.asList(
                new Object[]{"2024-07-27", 5.0},
                new Object[]{"2024-07-28", 5.0},
                new Object[]{"2024-07-29", 6.0}
        );

        List<Object[]> teamAveragesTeam1 = Arrays.asList(
                new Object[]{"2024-07-27", 4.0},
                new Object[]{"2024-07-28", 2.0},
                new Object[]{"2024-07-29", 3.0}
        );

        List<Object[]> userAveragesTeam2 = Arrays.asList(
                new Object[]{"2024-07-27", 6.0},
                new Object[]{"2024-07-28", 7.0},
                new Object[]{"2024-07-29", 5.5}
        );

        List<Object[]> teamAveragesTeam2 = Arrays.asList(
                new Object[]{"2024-07-27", 5.5},
                new Object[]{"2024-07-28", 6.0},
                new Object[]{"2024-07-29", 4.0}
        );

        // Setup mocks for team 1
        when(happinessSurveyRepository.findDailyAveragesByUserIdAndDateRange(eq(userId), any(), any()))
                .thenReturn(userAveragesTeam1);
        when(insightsRepository.findTeamDailyAveragesExcludingUserAndDateRange(eq(teamId1), eq(userId), any(), any()))
                .thenReturn(teamAveragesTeam1);
        when(happinessInsightMapper.toDTO("2024-07-27", 5.0, 4.0))
                .thenReturn(new HappinessInsightDTO("2024-07-27", 5.0, 4.0));
        when(happinessInsightMapper.toDTO("2024-07-28", 5.0, 2.0))
                .thenReturn(new HappinessInsightDTO("2024-07-28", 5.0, 2.0));
        when(happinessInsightMapper.toDTO("2024-07-29", 6.0, 3.0))
                .thenReturn(new HappinessInsightDTO("2024-07-29", 6.0, 3.0));

        // Execute the service method for team 1
        InsightDTO resultTeam1 = insightsService.getInsightsByTeamAndSprint(userId, teamId1, sprint);

        // Verify the results for team 1
        assertNotNull(resultTeam1);
        assertEquals(3, resultTeam1.getHappinessInsights().size());

        verify(happinessSurveyRepository, times(1)).findDailyAveragesByUserIdAndDateRange(eq(userId), any(), any());
        verify(insightsRepository, times(1)).findTeamDailyAveragesExcludingUserAndDateRange(eq(teamId1), eq(userId), any(), any());
        verify(happinessInsightMapper, times(3)).toDTO(anyString(), anyDouble(), anyDouble());

        // Reset mocks for team 2
        reset(happinessSurveyRepository, insightsRepository, happinessInsightMapper);

        // Setup mocks for team 2
        when(happinessSurveyRepository.findDailyAveragesByUserIdAndDateRange(eq(userId), any(), any()))
                .thenReturn(userAveragesTeam2);
        when(insightsRepository.findTeamDailyAveragesExcludingUserAndDateRange(eq(teamId2), eq(userId), any(), any()))
                .thenReturn(teamAveragesTeam2);
        when(happinessInsightMapper.toDTO("2024-07-27", 6.0, 5.5))
                .thenReturn(new HappinessInsightDTO("2024-07-27", 6.0, 5.5));
        when(happinessInsightMapper.toDTO("2024-07-28", 7.0, 6.0))
                .thenReturn(new HappinessInsightDTO("2024-07-28", 7.0, 6.0));
        when(happinessInsightMapper.toDTO("2024-07-29", 5.5, 4.0))
                .thenReturn(new HappinessInsightDTO("2024-07-29", 5.5, 4.0));

        // Execute the service method for team 2
        InsightDTO resultTeam2 = insightsService.getInsightsByTeamAndSprint(userId, teamId2, sprint);

        // Verify the results for team 2
        assertNotNull(resultTeam2);
        assertEquals(3, resultTeam2.getHappinessInsights().size());

        verify(happinessSurveyRepository, times(1)).findDailyAveragesByUserIdAndDateRange(eq(userId), any(), any());
        verify(insightsRepository, times(1)).findTeamDailyAveragesExcludingUserAndDateRange(eq(teamId2), eq(userId), any(), any());
        verify(happinessInsightMapper, times(3)).toDTO(anyString(), anyDouble(), anyDouble());
    }


    @Test
    void getInsightsByTeamAndSprint_emptyTeamInsights() {
        Integer teamId = 1;
        String sprint = "current";

        when(happinessSurveyRepository.findDailyAveragesByUserIdAndDateRange(eq(userId), any(), any())).thenReturn(userAverages);
        when(insightsRepository.findTeamDailyAveragesExcludingUserAndDateRange(eq(teamId), eq(userId), any(), any())).thenReturn(teamAverages);

        when(happinessInsightMapper.toDTO("2024-07-27", 5.0, 4.0)).thenReturn(new HappinessInsightDTO("2024-07-27", 5.0, 4.0));
        when(happinessInsightMapper.toDTO("2024-07-28", 5.0, 2.0)).thenReturn(new HappinessInsightDTO("2024-07-28", 5.0, 2.0));
        when(happinessInsightMapper.toDTO("2024-07-29", 6.0, 3.0)).thenReturn(new HappinessInsightDTO("2024-07-29", 6.0, 3.0));

        InsightDTO result = insightsService.getInsightsByTeamAndSprint(userId, teamId, sprint);

        System.out.println("Happiness Insights size: " + result.getHappinessInsights().size());
        result.getHappinessInsights().forEach(h -> System.out.println(h.getDay() + ": " + h.getUserAverage() + ", " + h.getTeamAverage()));

        assertNotNull(result);
        assertEquals(3, result.getHappinessInsights().size());

        verify(happinessSurveyRepository, times(1)).findDailyAveragesByUserIdAndDateRange(eq(userId), any(), any());
        verify(insightsRepository, times(1)).findTeamDailyAveragesExcludingUserAndDateRange(eq(teamId), eq(userId), any(), any());
        verify(happinessInsightMapper, times(3)).toDTO(anyString(), anyDouble(), anyDouble());
    }

}
