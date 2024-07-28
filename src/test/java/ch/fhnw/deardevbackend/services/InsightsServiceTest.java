package ch.fhnw.deardevbackend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import ch.fhnw.deardevbackend.dto.HappinessInsightDTO;
import ch.fhnw.deardevbackend.dto.TeamHappinessInsightDTO;
import ch.fhnw.deardevbackend.dto.TeamWorkKindInsightDTO;
import ch.fhnw.deardevbackend.dto.WorkKindInsightDTO;
import ch.fhnw.deardevbackend.mapper.HappinessInsightMapper;
import ch.fhnw.deardevbackend.repositories.InsightsRepository;
import ch.fhnw.deardevbackend.repositories.HappinessSurveyRepository;
import ch.fhnw.deardevbackend.repositories.TeamMemberRepository;
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
    private TeamMemberRepository teamMemberRepository;

    @Mock
    private HappinessInsightMapper happinessInsightMapper;

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
    void getDailyAveragesByUserId_success() {
        Integer teamId = 1;
        List<Integer> singleTeamId = List.of(teamId);

        when(teamMemberRepository.findTeamIdByUserId(userId)).thenReturn(singleTeamId);
        when(happinessSurveyRepository.findDailyAveragesByUserId(userId)).thenReturn(userAverages);
        when(insightsRepository.findTeamDailyAveragesExcludingUser(userId)).thenReturn(teamAverages);

        when(happinessInsightMapper.toDTO("2024-07-27", 5.0, 4.0)).thenReturn(new HappinessInsightDTO("2024-07-27", 5.0, 4.0));
        when(happinessInsightMapper.toDTO("2024-07-28", 5.0, 2.0)).thenReturn(new HappinessInsightDTO("2024-07-28", 5.0, 2.0));
        when(happinessInsightMapper.toDTO("2024-07-29", 6.0, 3.0)).thenReturn(new HappinessInsightDTO("2024-07-29", 6.0, 3.0));

        List<TeamHappinessInsightDTO> result = insightsService.getDailyAveragesByUserId(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(teamId, result.getFirst().getTeamId());
        assertEquals(3, result.getFirst().getInsights().size());
        assertEquals("2024-07-27", result.getFirst().getInsights().getFirst().getDay());
        assertEquals(5.0, result.getFirst().getInsights().getFirst().getUserAverage());
        assertEquals(4.0, result.getFirst().getInsights().getFirst().getTeamAverage());
        assertEquals("2024-07-28", result.getFirst().getInsights().get(1).getDay());
        assertEquals(5.0, result.getFirst().getInsights().get(1).getUserAverage());
        assertEquals(2.0, result.getFirst().getInsights().get(1).getTeamAverage());
        assertEquals("2024-07-29", result.getFirst().getInsights().get(2).getDay());
        assertEquals(6.0, result.getFirst().getInsights().get(2).getUserAverage());
        assertEquals(3.0, result.getFirst().getInsights().get(2).getTeamAverage());

        verify(teamMemberRepository, times(1)).findTeamIdByUserId(userId);
        verify(happinessSurveyRepository, times(1)).findDailyAveragesByUserId(userId);
        verify(insightsRepository, times(1)).findTeamDailyAveragesExcludingUser(userId);
        verify(happinessInsightMapper, times(1)).toDTO("2024-07-27", 5.0, 4.0);
        verify(happinessInsightMapper, times(1)).toDTO("2024-07-28", 5.0, 2.0);
        verify(happinessInsightMapper, times(1)).toDTO("2024-07-29", 6.0, 3.0);
    }

    @Test
    void getDailyAveragesByUserId_multipleTeams_success() {
        List<Integer> teamIds = Arrays.asList(1, 2);

        when(teamMemberRepository.findTeamIdByUserId(userId)).thenReturn(teamIds);
        when(happinessSurveyRepository.findDailyAveragesByUserId(userId)).thenReturn(userAverages);
        when(insightsRepository.findTeamDailyAveragesExcludingUser(userId)).thenReturn(teamAverages);

        when(happinessInsightMapper.toDTO("2024-07-27", 5.0, 4.0)).thenReturn(new HappinessInsightDTO("2024-07-27", 5.0, 4.0));
        when(happinessInsightMapper.toDTO("2024-07-28", 5.0, 2.0)).thenReturn(new HappinessInsightDTO("2024-07-28", 5.0, 2.0));
        when(happinessInsightMapper.toDTO("2024-07-29", 6.0, 3.0)).thenReturn(new HappinessInsightDTO("2024-07-29", 6.0, 3.0));

        List<TeamHappinessInsightDTO> result = insightsService.getDailyAveragesByUserId(userId);

        assertNotNull(result);
        assertEquals(2, result.size());

        // Team 1 Assertions
        assertEquals(1, result.getFirst().getTeamId());
        assertEquals(3, result.getFirst().getInsights().size());
        assertEquals("2024-07-27", result.getFirst().getInsights().getFirst().getDay());
        assertEquals(5.0, result.getFirst().getInsights().getFirst().getUserAverage());
        assertEquals(4.0, result.getFirst().getInsights().getFirst().getTeamAverage());
        assertEquals("2024-07-28", result.getFirst().getInsights().get(1).getDay());
        assertEquals(5.0, result.getFirst().getInsights().get(1).getUserAverage());
        assertEquals(2.0, result.getFirst().getInsights().get(1).getTeamAverage());
        assertEquals("2024-07-29", result.getFirst().getInsights().get(2).getDay());
        assertEquals(6.0, result.getFirst().getInsights().get(2).getUserAverage());
        assertEquals(3.0, result.getFirst().getInsights().get(2).getTeamAverage());

        // Team 2 Assertions
        assertEquals(2, result.get(1).getTeamId());
        assertEquals(3, result.get(1).getInsights().size());
        assertEquals("2024-07-27", result.get(1).getInsights().getFirst().getDay());
        assertEquals(5.0, result.get(1).getInsights().getFirst().getUserAverage());
        assertEquals(4.0, result.get(1).getInsights().getFirst().getTeamAverage());
        assertEquals("2024-07-28", result.get(1).getInsights().get(1).getDay());
        assertEquals(5.0, result.get(1).getInsights().get(1).getUserAverage());
        assertEquals(2.0, result.get(1).getInsights().get(1).getTeamAverage());
        assertEquals("2024-07-29", result.get(1).getInsights().get(2).getDay());
        assertEquals(6.0, result.get(1).getInsights().get(2).getUserAverage());
        assertEquals(3.0, result.get(1).getInsights().get(2).getTeamAverage());

        verify(teamMemberRepository, times(1)).findTeamIdByUserId(userId);
        verify(happinessSurveyRepository, times(2)).findDailyAveragesByUserId(userId);
        verify(insightsRepository, times(2)).findTeamDailyAveragesExcludingUser(userId);
        verify(happinessInsightMapper, times(2)).toDTO("2024-07-27", 5.0, 4.0);
        verify(happinessInsightMapper, times(2)).toDTO("2024-07-28", 5.0, 2.0);
        verify(happinessInsightMapper, times(2)).toDTO("2024-07-29", 6.0, 3.0);
    }

    @Test
    void getWorkKindHappinessByUserId_success() {
        when(insightsRepository.findWorkKindHappinessByUserId(userId)).thenReturn(workKindHappinessData);

        List<TeamWorkKindInsightDTO> result = insightsService.getWorkKindHappinessByUserId(userId);

        assertEquals(2, result.size());

        List<WorkKindInsightDTO> team1Insights = Arrays.asList(workKindInsightDTO1, workKindInsightDTO2);
        List<WorkKindInsightDTO> team2Insights = Arrays.asList(workKindInsightDTO3, workKindInsightDTO4);

        assertEquals(new TeamWorkKindInsightDTO(1, team1Insights), result.getFirst());
        assertEquals(new TeamWorkKindInsightDTO(2, team2Insights), result.get(1));
    }
}
