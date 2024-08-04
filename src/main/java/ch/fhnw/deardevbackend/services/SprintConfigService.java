package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.controller.exceptions.YappiException;
import ch.fhnw.deardevbackend.dto.CreateSprintDTO;
import ch.fhnw.deardevbackend.entities.SprintConfig;
import ch.fhnw.deardevbackend.mapper.SprintConfigMapper;
import ch.fhnw.deardevbackend.repositories.SprintConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SprintConfigService {

    @Autowired
    private SprintConfigRepository sprintConfigRepository;
    @Autowired
    private SprintConfigMapper sprintConfigMapper;

    public List<SprintConfig> getSprintsByCreatedBy(Integer userId) {
        return sprintConfigRepository.findAllByCreatedBy(userId);
    }

    public SprintConfig getSprintById(Integer sprintId) {
        return sprintConfigRepository.findById(sprintId).orElseThrow(() -> new YappiException("Sprint not found with id: " + sprintId));
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

}
