package ch.fhnw.deardevbackend.services;

import static org.junit.jupiter.api.Assertions.*;
import ch.fhnw.deardevbackend.dto.HappinessInsightDTO;
import ch.fhnw.deardevbackend.dto.TeamHappinessInsightDTO;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InsightsServiceTest {

    @Mock
    private InsightsRepository insightsRepository;

    @Mock
    private HappinessSurveyRepository happinessSurveyRepository;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @Mock
    private HappinessInsightMapper happinessInsightMapper;

    @InjectMocks
    private InsightsService insightsService;

    private Integer userId;
    private Integer teamId;
    private List<Integer> teamIds;
    private List<Object[]> userAverages;
    private List<Object[]> teamAverages;

    @BeforeEach
    void setUp() {
        userId = 1;
        teamId = 1;
        teamIds = Arrays.asList(1, 2);

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

        when(happinessInsightMapper.toDTO("2024-07-27", 5.0, 4.0)).thenReturn(new HappinessInsightDTO("2024-07-27", 5.0, 4.0));
        when(happinessInsightMapper.toDTO("2024-07-28", 5.0, 2.0)).thenReturn(new HappinessInsightDTO("2024-07-28", 5.0, 2.0));
        when(happinessInsightMapper.toDTO("2024-07-29", 6.0, 3.0)).thenReturn(new HappinessInsightDTO("2024-07-29", 6.0, 3.0));

    }

    @Test
    void getDailyAveragesByUserId_success() {
        List<Integer> singleTeamId = List.of(teamId);

        when(teamMemberRepository.findTeamIdByUserId(userId)).thenReturn(singleTeamId);
        when(happinessSurveyRepository.findDailyAveragesByUserId(userId)).thenReturn(userAverages);
        when(insightsRepository.findTeamDailyAveragesExcludingUser(userId)).thenReturn(teamAverages);

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
        when(teamMemberRepository.findTeamIdByUserId(userId)).thenReturn(teamIds);
        when(happinessSurveyRepository.findDailyAveragesByUserId(userId)).thenReturn(userAverages);
        when(insightsRepository.findTeamDailyAveragesExcludingUser(userId)).thenReturn(teamAverages);

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

}
