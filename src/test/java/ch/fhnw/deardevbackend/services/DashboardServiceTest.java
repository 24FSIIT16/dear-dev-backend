package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.controller.exceptions.YappiException;
import ch.fhnw.deardevbackend.dto.DashboardDTO;
import ch.fhnw.deardevbackend.entities.WorkKind;
import ch.fhnw.deardevbackend.repositories.HappinessSurveyRepository;
import ch.fhnw.deardevbackend.repositories.WorkKindRepository;
import ch.fhnw.deardevbackend.repositories.WorkKindSurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DashboardServiceTest {

    @Mock
    private HappinessSurveyRepository happinessSurveyRepository;

    @Mock
    private WorkKindSurveyRepository workKindSurveyRepository;

    @Mock
    private WorkKindRepository workKindRepository;

    @InjectMocks
    private DashboardService dashboardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getAverageScoreByUserId_NoSurveys() {
        int userId = 1;
        when(happinessSurveyRepository.findDailyAveragesByUserId(userId)).thenReturn(Collections.emptyList());

        Integer averageScore = dashboardService.getAverageScoreByUserId(userId);
        assertNull(averageScore);
    }

    @Test
    void getAverageScoreByUserId_WithSurveys() {
        int userId = 1;
        when(happinessSurveyRepository.findDailyAveragesByUserId(userId)).thenReturn(List.of(
                new Object[]{1, 5.0},
                new Object[]{2, 0.0},
                new Object[]{1, 15.0},
                new Object[]{2, 15.4}
        ));

        Integer averageScore = dashboardService.getAverageScoreByUserId(userId);
        assertEquals(9, averageScore);
    }

    @Test
    void getDashboardDataByUserId_NoSurveys() {
        int userId = 1;
        when(workKindSurveyRepository.findWorkKindCountByUserId(userId)).thenReturn(Collections.emptyList());
        when(happinessSurveyRepository.findDailyAveragesByUserId(userId)).thenReturn(Collections.emptyList());

        DashboardDTO dashboardDTO = dashboardService.getDashboardDataByUserId(userId);
        assertNull(dashboardDTO.getMostVotedWorkKind().getWorkKindId());
        assertNull(dashboardDTO.getMostVotedWorkKind().getWorkKindName());
        assertNull(dashboardDTO.getMostVotedWorkKind().getVoteCount());
        assertNull(dashboardDTO.getAverageScore());
    }

    @Test
    void getDashboardDataByUserId_WithSurveys() {
        int userId = 1;
        when(happinessSurveyRepository.findDailyAveragesByUserId(userId)).thenReturn(List.of(
                new Object[]{1, 5.0},
                new Object[]{2, 7.2}
        ));

        when(workKindSurveyRepository.findWorkKindCountByUserId(userId)).thenReturn(List.of(
                new Object[]{1, 5L},
                new Object[]{5, 7L}
        ));

        when(workKindRepository.findById(1)).thenReturn(Optional.of(new WorkKind(55, "Development", null)));
        when(workKindSurveyRepository.findAverageHappinessScoreByWorkKindIdAndUserId(1, 1)).thenReturn(Optional.of(10));

        DashboardDTO dashboardDTO = dashboardService.getDashboardDataByUserId(userId);
        assertEquals(1, dashboardDTO.getMostVotedWorkKind().getWorkKindId());
        assertEquals("Development", dashboardDTO.getMostVotedWorkKind().getWorkKindName());
        assertEquals(5L, dashboardDTO.getMostVotedWorkKind().getVoteCount());
        assertEquals(6, dashboardDTO.getAverageScore());
        assertEquals(10, dashboardDTO.getMostVotedWorkKind().getHappinessScore());
    }

    @Test
    void getDashboardDataByUserId_WithWorkKindSurveysOnly() {
        int userId = 1;
        when(workKindSurveyRepository.findWorkKindCountByUserId(userId)).thenReturn(List.of(
                new Object[]{1, 5L},
                new Object[]{2, 3L},
                new Object[]{4, 2L},
                new Object[]{1, 8L}
        ));
        when(workKindRepository.findById(1)).thenReturn(Optional.of(new WorkKind(1, "Development", null)));
        when(happinessSurveyRepository.findDailyAveragesByUserId(userId)).thenReturn(List.of());
        when(workKindSurveyRepository.findAverageHappinessScoreByWorkKindIdAndUserId(1, 1)).thenReturn(Optional.of(10));

        DashboardDTO dashboardDTO = dashboardService.getDashboardDataByUserId(userId);
        assertEquals(1, dashboardDTO.getMostVotedWorkKind().getWorkKindId());
        assertEquals("Development", dashboardDTO.getMostVotedWorkKind().getWorkKindName());
        assertEquals(5L, dashboardDTO.getMostVotedWorkKind().getVoteCount());
        assertNull(dashboardDTO.getAverageScore());
        assertEquals(10, dashboardDTO.getMostVotedWorkKind().getHappinessScore());
    }

    @Test
    void getDashboardDataByUserId_WithHappinessSurveysOnly() {
        int userId = 1;
        when(workKindSurveyRepository.findWorkKindCountByUserId(userId)).thenReturn(List.of());
        when(happinessSurveyRepository.findDailyAveragesByUserId(userId)).thenReturn(List.of(
                new Object[]{1, 5.0},
                new Object[]{2, 10.0}
        ));

        DashboardDTO dashboardDTO = dashboardService.getDashboardDataByUserId(userId);
        assertNull(dashboardDTO.getMostVotedWorkKind().getWorkKindId());
        assertNull(dashboardDTO.getMostVotedWorkKind().getWorkKindName());
        assertNull(dashboardDTO.getMostVotedWorkKind().getVoteCount());
        assertEquals(8, dashboardDTO.getAverageScore());
    }

    @Test
    void getDashboardDataByUserId_InvalidUserId() {
        int userId = -1;
        when(workKindSurveyRepository.findWorkKindCountByUserId(userId)).thenReturn(List.of());
        when(happinessSurveyRepository.findDailyAveragesByUserId(userId)).thenReturn(List.of());

        DashboardDTO dashboardDTO = dashboardService.getDashboardDataByUserId(userId);
        assertNull(dashboardDTO.getMostVotedWorkKind().getWorkKindId());
        assertNull(dashboardDTO.getMostVotedWorkKind().getWorkKindName());
        assertNull(dashboardDTO.getMostVotedWorkKind().getVoteCount());
        assertNull(dashboardDTO.getAverageScore());
        assertNull(dashboardDTO.getMostVotedWorkKind().getHappinessScore());
    }

    @Test
    void getDashboardDataByUserId_MultipleUsers() {
        int userId1 = 1;
        int userId2 = 2;
        when(workKindSurveyRepository.findWorkKindCountByUserId(userId1)).thenReturn(List.of(
                new Object[]{1, 8L},
                new Object[]{2, 3L},
                new Object[]{4, 2L}
        ));
        when(workKindRepository.findById(1)).thenReturn(Optional.of(new WorkKind(1, "Development", null)));
        when(happinessSurveyRepository.findDailyAveragesByUserId(userId1)).thenReturn(List.of(
                new Object[]{1, 5.0},
                new Object[]{2, 10.0}
        ));
        when(workKindSurveyRepository.findAverageHappinessScoreByWorkKindIdAndUserId(1, 1)).thenReturn(Optional.of(10));
        when(workKindSurveyRepository.findWorkKindCountByUserId(userId2)).thenReturn(List.of(
                new Object[]{2, 7L},
                new Object[]{7, 3L},
                new Object[]{3, 2L}
        ));
        when(workKindRepository.findById(2)).thenReturn(Optional.of(new WorkKind(2, "Testing", null)));
        when(happinessSurveyRepository.findDailyAveragesByUserId(userId2)).thenReturn(List.of(
                new Object[]{1, 3.0},
                new Object[]{2, 7.0}
        ));
        when(workKindSurveyRepository.findAverageHappinessScoreByWorkKindIdAndUserId(2, 2)).thenReturn(Optional.of(9));

        DashboardDTO dashboardDTO1 = dashboardService.getDashboardDataByUserId(userId1);
        assertEquals(1, dashboardDTO1.getMostVotedWorkKind().getWorkKindId());
        assertEquals("Development", dashboardDTO1.getMostVotedWorkKind().getWorkKindName());
        assertEquals(8, dashboardDTO1.getMostVotedWorkKind().getVoteCount());
        assertEquals(8, dashboardDTO1.getAverageScore());
        assertEquals(10, dashboardDTO1.getMostVotedWorkKind().getHappinessScore());

        DashboardDTO dashboardDTO2 = dashboardService.getDashboardDataByUserId(userId2);
        assertEquals(2, dashboardDTO2.getMostVotedWorkKind().getWorkKindId());
        assertEquals("Testing", dashboardDTO2.getMostVotedWorkKind().getWorkKindName());
        assertEquals(7, dashboardDTO2.getMostVotedWorkKind().getVoteCount());
        assertEquals(5, dashboardDTO2.getAverageScore());
        assertEquals(9, dashboardDTO2.getMostVotedWorkKind().getHappinessScore());
    }

    @Test
    void getAverageScoreByUserId_Exception() {
        int userId = 1;
        when(happinessSurveyRepository.findDailyAveragesByUserId(userId)).thenThrow(new RuntimeException("Database error"));

        YappiException exception = assertThrows(YappiException.class, () -> dashboardService.getAverageScoreByUserId(userId));
        assertEquals("Error calculating average score for user ID: 1", exception.getMessage());
    }

    @Test
    void getDashboardDataByUserId_Exception() {
        int userId = 1;
        when(workKindSurveyRepository.findWorkKindCountByUserId(userId)).thenThrow(new RuntimeException("Database error"));

        YappiException exception = assertThrows(YappiException.class, () -> dashboardService.getDashboardDataByUserId(userId));
        assertEquals("Error fetching dashboard data for user ID: 1", exception.getMessage());
    }
}
