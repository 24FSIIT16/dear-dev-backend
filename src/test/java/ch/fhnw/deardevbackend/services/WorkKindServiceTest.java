package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.entities.WorkKind;
import ch.fhnw.deardevbackend.repositories.WorkKindRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class WorkKindServiceTest {

    @Mock
    private WorkKindRepository workKindRepository;

    @InjectMocks
    private WorkKindService workKindService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getWorkKindsForTeams() {
        List<Integer> teamIds = Arrays.asList(1, 2, 3);
        List<WorkKind> workKinds = Arrays.asList(
                new WorkKind(1, "Development", 1),
                new WorkKind(2, "Testing", 2),
                new WorkKind(3, "Management", null)
        );
        when(workKindRepository.findByTeamIdsOrNoTeam(teamIds)).thenReturn(workKinds);

        List<WorkKind> result = workKindService.getWorkKindsForTeams(teamIds);

        assertEquals(3, result.size());
        assertEquals("Development", result.get(0).getName());
        assertEquals("Testing", result.get(1).getName());
        assertEquals("Management", result.get(2).getName());
    }

    @Test
    void getWorkKindsForTeams_EmptyList() {
        List<Integer> teamIds = Arrays.asList(1, 2, 3);
        when(workKindRepository.findByTeamIdsOrNoTeam(teamIds)).thenReturn(List.of());

        List<WorkKind> result = workKindService.getWorkKindsForTeams(teamIds);

        assertEquals(0, result.size());
    }

    @Test
    void getWorkKindsForTeams_NullList() {
        when(workKindRepository.findByTeamIdsOrNoTeam(null)).thenReturn(List.of());

        List<WorkKind> result = workKindService.getWorkKindsForTeams(null);

        assertEquals(0, result.size());
    }
}