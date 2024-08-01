package ch.fhnw.deardevbackend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    private List<Object[]> workKindHappinessData;
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

        workKindHappinessData = Arrays.asList(
                new Object[]{1, 1, "Development", 4.5, 10L},
                new Object[]{1, 2, "Testing", 3.5, 5L},
                new Object[]{2, 1, "Development", 4.0, 8L},
                new Object[]{2, 2, "Testing", 3.0, 6L}
        );

        workKindInsightDTO1 = new WorkKindInsightDTO(1, 1, "Development", 4.5, 10L);
        workKindInsightDTO2 = new WorkKindInsightDTO(1, 2, "Testing", 3.5, 5L);
        workKindInsightDTO3 = new WorkKindInsightDTO(2, 1, "Development", 4.0, 8L);
        workKindInsightDTO4 = new WorkKindInsightDTO(2, 2, "Testing", 3.0, 6L);
    }

    @Test
    void getInsightsByTeamAndSprint_success() {
        Integer teamId = 1;
        String sprint = "current";

        when(happinessSurveyRepository.findDailyAveragesByUserIdAndDateRange(eq(userId), any(), any())).thenReturn(userAverages);
        when(insightsRepository.findTeamDailyAveragesExcludingUserAndDateRange(eq(teamId), eq(userId), any(), any())).thenReturn(teamAverages);
        when(happinessInsightMapper.toDTO("2024-07-27", 5.0, 4.0)).thenReturn(new HappinessInsightDTO("2024-07-27", 5.0, 4.0));
        when(happinessInsightMapper.toDTO("2024-07-28", 5.0, 2.0)).thenReturn(new HappinessInsightDTO("2024-07-28", 5.0, 2.0));
        when(happinessInsightMapper.toDTO("2024-07-29", 6.0, 3.0)).thenReturn(new HappinessInsightDTO("2024-07-29", 6.0, 3.0));

        when(insightsRepository.findWorkKindHappinessByUserId(userId)).thenReturn(workKindHappinessData);
        when(workKindInsightMapper.toDTO(1, 1, "Development", 4.5, 10L)).thenReturn(workKindInsightDTO1);
        when(workKindInsightMapper.toDTO(1, 2, "Testing", 3.5, 5L)).thenReturn(workKindInsightDTO2);
        when(workKindInsightMapper.toDTO(2, 1, "Development", 4.0, 8L)).thenReturn(workKindInsightDTO3);
        when(workKindInsightMapper.toDTO(2, 2, "Testing", 3.0, 6L)).thenReturn(workKindInsightDTO4);

        InsightDTO result = insightsService.getInsightsByTeamAndSprint(userId, teamId, sprint);

        assertNotNull(result);
        assertNotNull(result.getHappinessInsights());
        assertNotNull(result.getWorkKindInsights());
        assertEquals(3, result.getHappinessInsights().size());
        assertEquals(4, result.getWorkKindInsights().size());

        verify(happinessSurveyRepository, times(1)).findDailyAveragesByUserIdAndDateRange(eq(userId), any(), any());
        verify(insightsRepository, times(1)).findTeamDailyAveragesExcludingUserAndDateRange(eq(teamId), eq(userId), any(), any());
        verify(happinessInsightMapper, times(3)).toDTO(anyString(), anyDouble(), anyDouble());

        verify(insightsRepository, times(1)).findWorkKindHappinessByUserId(userId);
        verify(workKindInsightMapper, times(4)).toDTO(anyInt(), anyInt(), anyString(), anyDouble(), anyLong());
    }

    @Test
    void getHappinessInsightsByTeam_success() {
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


}
