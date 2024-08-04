package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.controller.exceptions.YappiException;
import ch.fhnw.deardevbackend.dto.CreateSprintDTO;
import ch.fhnw.deardevbackend.dto.SprintsAndTeamsDTO;
import ch.fhnw.deardevbackend.entities.SprintConfig;
import ch.fhnw.deardevbackend.entities.Team;
import ch.fhnw.deardevbackend.mapper.SprintConfigMapper;
import ch.fhnw.deardevbackend.repositories.SprintConfigRepository;
import ch.fhnw.deardevbackend.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SprintConfigService {

    @Autowired
    private SprintConfigRepository sprintConfigRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private SprintConfigMapper sprintConfigMapper;

    public List<SprintConfig> getSprintsByCreatedBy(Integer userId) {
        return sprintConfigRepository.findAllByCreatedBy(userId);
    }

    public SprintConfig getSprintById(Integer sprintId) {
        return sprintConfigRepository.findById(sprintId).orElseThrow(() -> new YappiException("Sprint not found with id: " + sprintId));
    }

    public SprintsAndTeamsDTO getSprintsAndTeams(Integer userId) {
        List<Team> teams = teamRepository.findActiveTeamsByUserId(userId);
        List<SprintConfig> sprints = sprintConfigRepository.findByCreatedByAndActiveFalseAndStartDateAfter(userId, LocalDate.now());

        return new SprintsAndTeamsDTO(teams, sprints);
    }

    public SprintConfig createSprint(SprintConfig sprintConfig) {
        return sprintConfigRepository.save(sprintConfig);
    }

    @Transactional
    public SprintConfig updateSprint(Integer id, CreateSprintDTO dto) {
        Optional<SprintConfig> optionalSprintConfig = sprintConfigRepository.findById(id);

        if (optionalSprintConfig.isPresent()) {
            SprintConfig existingSprintConfig = optionalSprintConfig.get();
            sprintConfigMapper.updateSprintFromDTO(dto, existingSprintConfig);
            return sprintConfigRepository.save(existingSprintConfig);
        } else {
            throw new YappiException("Sprint not found with id: " + id);
        }
    }

    @Transactional
    public void startSprint(Integer teamId, Integer sprintId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new YappiException("Team not found with id: " + teamId));
        team.setCurrentSprintId(sprintId);
        teamRepository.save(team);

        SprintConfig sprint = sprintConfigRepository.findById(sprintId).orElseThrow(() -> new YappiException("Sprint not found with id: " + sprintId));
        sprint.setActive(true);
        sprintConfigRepository.save(sprint);
    }
}
