package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.dto.WorkKindAndTeamDTO;
import ch.fhnw.deardevbackend.entities.Team;
import ch.fhnw.deardevbackend.entities.WorkKind;
import ch.fhnw.deardevbackend.mapper.WorkKindAndTeamMapper;
import ch.fhnw.deardevbackend.repositories.TeamRepository;
import ch.fhnw.deardevbackend.repositories.WorkKindRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkKindService {


    @Autowired
    private final WorkKindRepository workKindRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private WorkKindAndTeamMapper workKindAndTeamMapper;

    public List<WorkKind> getWorkKindsForTeams(List<Integer> teamIds) {
        return workKindRepository.findByTeamIdsOrNoTeam(teamIds);
    }

    public List<WorkKindAndTeamDTO> getWorkKindsAndTeams(Integer userId) {
        List<Team> teams = teamRepository.findActiveTeamsByUserId(userId);
        List<WorkKindAndTeamDTO> workKindAndTeams = new ArrayList<>();

        for (Team team : teams) {
            List<WorkKind> workKinds = workKindRepository.findByTeamId(team.getId());
            for (WorkKind workKind : workKinds) {
                workKindAndTeams.add(workKindAndTeamMapper.toDTO(workKind, team.getName()));
            }
        }

        return workKindAndTeams;
    }
}
