package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.entities.WorkKind;
import ch.fhnw.deardevbackend.repositories.WorkKindRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkKindService {

    @Autowired
    private final WorkKindRepository workKindRepository;

    public List<WorkKind> getWorkKindsForTeams(List<Integer> teamIds) {
        return workKindRepository.findByTeamIdOrNoTeam(teamIds);
    }
}