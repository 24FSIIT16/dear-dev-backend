package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.entities.WorkKind;
import ch.fhnw.deardevbackend.repositories.WorkKindRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkKindService {

    private static final Logger logger = LoggerFactory.getLogger(WorkKindService.class);

    @Autowired
    private final WorkKindRepository workKindRepository;

    public List<WorkKind> getWorkKindsForTeams(List<Integer> teamIds) {
        logger.info("Fetching WorkKinds for teamIds: {}", teamIds);
        return workKindRepository.findByTeamIdsOrNoTeam(teamIds);
    }
}
