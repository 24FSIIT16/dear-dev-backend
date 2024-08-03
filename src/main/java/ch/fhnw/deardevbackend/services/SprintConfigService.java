package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.entities.SprintConfig;
import ch.fhnw.deardevbackend.repositories.SprintConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SprintConfigService {

    @Autowired
    private SprintConfigRepository sprintConfigRepository;

    public List<SprintConfig> getSprintsByCreatedBy(Integer userId) {
        return sprintConfigRepository.findAllByCreatedBy(userId);
    }

    public SprintConfig createSprint(SprintConfig sprintConfig) {
        return sprintConfigRepository.save(sprintConfig);
    }

}
